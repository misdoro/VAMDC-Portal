package org.vamdc.portal.species;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.async.Asynchronous;
import org.jboss.seam.core.ResourceLoader;
import org.vamdc.portal.RedirectPage;
import org.vamdc.portal.Settings;
import org.vamdc.portal.exception.PortalHttpException;
import org.vamdc.portal.registry.RegistryFacade;

@Name("nodespecies")
@Scope(ScopeType.CONVERSATION)
public class NodeSpecies {
	
	@In(create=true) transient RegistryFacade registryFacade;
	// node id
    private String ivoaId = "";
    //tap request parameters
    private static String query = "sync?LANG=VSS2&REQUEST=doQuery&FORMAT=XSAMS&QUERY=Select+Species";
    //xsl stylesheet name
    private static String xslFile = "getspecies.xsl";
    
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
    	if (ivoaId!=null && ivoaId.length()>0){
    		setIvoaId(ivoaId);
    		return RedirectPage.SPECIES;
    	}else{
    		return RedirectPage.NODES;
    	}
    }
    
    @Asynchronous 
    public void querySpecies(String ivoaId, SpeciesResult resultBean){
    	Collection<URL> mirrors=registryFacade.getVamdcTapMirrors(ivoaId);
    	if (mirrors==null || mirrors.isEmpty()){
    		resultBean.setMessage("Node has no active VAMDC-TAP mirrors");
        	resultBean.setReady(true);
        	return;
    	}
    	System.out.println("### test ");
    	resultBean.setMirrorCount(mirrors.size());
    	for(URL node:mirrors){
    		resultBean.setMessage("Timeout : query execution was too long.");
    		resultBean.nextMirror();
    		URL queryUrl=node;
			System.out.println("query");
			System.out.println(query);
    		System.out.println(queryUrl);
    		try {
    			queryUrl = new URL(node.toExternalForm()+query);

    			System.out.print("queryUrl");
    			System.out.print(queryUrl);
    			resultBean.setFormattedResult( formatRequestResult(queryUrl));
    			resultBean.setMessage("");
    			resultBean.setReady(true);
    			return;
    		} catch (MalformedURLException e) {
    			// TODO Auto-generated catch block
    			resultBean.setMessage("Incorrect service URL "+queryUrl);
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			
    			e.printStackTrace();
    			resultBean.setMessage("Error while proceeding request");
    		} catch (TransformerException e) {
    			// TODO Auto-generated catch block
    			resultBean.setMessage("XSAMS file could not be parsed");
    		} catch (PortalHttpException e) {
    			// TODO Auto-generated catch block
    			if(e.getCode() == 204) {
    				resultBean.setMessage("Request returned an empty response");
    			} else if(e.getCode() >= 400){
    				resultBean.setMessage("Node returned the error " +e.getCode() +" while processing the request "+queryUrl);
    			}
    		}finally{
    			resultBean.setReady(true);
    		}
		}
    }
    
    /**
     * convert XSAMS file contained at URL into HTML
     * @param url
     * @return
     * @throws IOException
     * @throws TransformerException
     */
    private static String formatRequestResult(URL url) throws IOException, TransformerException, PortalHttpException{
    	System.out.print("###");
    	System.out.print(url);
    	HttpURLConnection c = (HttpURLConnection)url.openConnection();
    	
    	if(c.getResponseCode() == 200){
			c.setConnectTimeout(Settings.HTTP_CONNECT_TIMEOUT.getInt());
			c.setReadTimeout(Settings.HTTP_DATA_TIMEOUT.getInt());
			
			Source xmlSource = new StreamSource(new InputStreamReader(c.getInputStream()));		
			Source xsltSource = new StreamSource(ResourceLoader.instance().getResourceAsStream(NodeSpecies.xslFile));
			
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
