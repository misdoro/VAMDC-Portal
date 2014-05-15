package org.vamdc.portal.registry;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.async.Asynchronous;
import org.jboss.seam.core.ResourceLoader;
import org.vamdc.portal.RedirectPage;
import org.vamdc.portal.exception.PortalHttpException;

@Name("nodespecies")
@Scope(ScopeType.CONVERSATION)
public class NodeSpecies {
	// node id
    private String ivoaId = "";
    //tap request parameters
    private String query = "sync?LANG=VSS2&REQUEST=doQuery&FORMAT=XSAMS&QUERY=Select+Species";
    //contains printable results
    private String formattedResult= "";
    //true if node results are valid
    private Boolean displayable = false;
    //message describing request status
    private String message = "Please wait for the document to get ready";
    //xsl stylesheet name
    private String xslFile = "getspecies.xsl";
    
    public NodeSpecies(){
    }
       
    public void setIvoaId(String id){
    	this.ivoaId = id;
    }
    
    public String getIvoaId(){
    	return this.ivoaId;
    }
    
    @Begin
    public String displaySpecies(String ivoaId){
    	setIvoaId(ivoaId);
    	return RedirectPage.SPECIES;
    }
    
    public String getFormattedResult(){
		return this.formattedResult; 
    }  
    
    public boolean isDisplayable(){
    	return this.displayable;
    }
 
    public String getMessage(){
    	return this.message;
    }
    
    @Asynchronous 
    public void querySpecies(String ivoaId, SpeciesResult resultBean){    	
    	RegistryFacade rf = new RegistryFacade();
    	URL node = rf.getVamdcTapURL(ivoaId);
    	resultBean.setMessage("Timeout : query execution was too long.");
    	try {
			URL url = new URL(node.toExternalForm()+query);
			this.formattedResult = formatRequestResult(url);
			this.message = "";
			this.displayable = true;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			this.message = "Incorrect service URL";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.message = "Error while proceeding request";
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			this.message = "XSAMS file could not be parsed.";
		} catch (PortalHttpException e) {
			// TODO Auto-generated catch block
			if(e.getCode() == 204) {
				this.message = "Request returned an empty response";
			} else if(e.getCode() >= 400){
				this.message = "Node returned an error while proceeding request ( " +e.getCode() +" ) ";
			}
		}finally{
			resultBean.setMessage(this.message);
			resultBean.setFormattedResult(this.formattedResult);
			resultBean.setReady(true);			
		} 
    }
    
    /**
     * convert XSAMS file contained at URL into HTML
     * @param url
     * @return
     * @throws IOException
     * @throws TransformerException
     */
    private String formatRequestResult(URL url) throws IOException, TransformerException, PortalHttpException{
    	HttpURLConnection c = (HttpURLConnection)url.openConnection();
    	if(c.getResponseCode() == 200){
			c.setConnectTimeout(3000);
			c.setReadTimeout(5000);
			
			Source xmlSource = new StreamSource(new InputStreamReader(c.getInputStream()));		
			Source xsltSource = new StreamSource(ResourceLoader.instance().getResourceAsStream(this.xslFile));
			
			TransformerFactory factory = TransformerFactory.newInstance();
	        Transformer transformer = factory.newTransformer(xsltSource);
	        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
	        transformer.transform(xmlSource, new StreamResult(bOut));   
	        bOut.close();
	        
	        return bOut.toString();
    	}else{
    		throw new PortalHttpException("Http error while executing TAP request", c.getResponseCode());
    	}
    }
}
