package org.vamdc.portal.async;

public interface FutureTask {
	public void process();	
	public boolean isDone();	
	public boolean isProcessing();	
	public boolean isOk();
	public boolean isErrorHappened();
	public String getError();
}
