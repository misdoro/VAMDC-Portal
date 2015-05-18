package org.vamdc.portal.async;

import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.vamdc.portal.Settings;
import org.vamdc.portal.session.queryBuilder.QueryData;



@Name("asyncprogressbarbean")
@Scope(ScopeType.PAGE)
public class ASyncProgressBarBean {
	@In(create=true) QueryData queryData;
	/**
	 * data from Async form (email address)
	 */
	@In(value="asyncbean") ASyncBean asyncBean;
	@In("#{pdlRequest}") PdlRequest pdlRequest;
	
    private boolean started = false;
    private Long startTime;
    
    /**
     * Bean containing results
     */
    private PdlResult pdlResult = new PdlResult();
    
    public ASyncProgressBarBean(){

    }
    
    /**
     * Duration in seconds before timeout
     */
    private Long maxValue = (long) Settings.HTTP_DATA_TIMEOUT.getInt()/1000;
    
    
    public Long getMaxValue(){
    	return this.maxValue;
    }
    
    public void startProcess() {
        setStartTime(new Date().getTime());
        pdlRequest.execRequest(queryData.getQueryString(), asyncBean.getEmail(), pdlResult);

    }

    public Long getCurrentValue(){
    	
    	if(!started){    		
    		this.startProcess();    		    	
            started = true;
    	}
    	

    	if(pdlResult.isReady()){
    		return this.maxValue+1;
    	}

        if (startTime != null){        	
            return (new Date().getTime() - startTime)/1000;
        }else{
            return Long.valueOf(-1);
        }

    }
    
    public String getMessage(){
    	if(pdlResult.getMessage().equals("ok")){
    		return "The process has been started. You should receive an email shortly";
    	}else{
    		// error message
    		return  "An error occured, "+pdlResult.getMessage();
    	}
    	
    }    
    

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }
}
