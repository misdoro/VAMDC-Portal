package org.vamdc.portal.session.consumers;

public interface Consumer {
	public void process();	
	public String getLocation();	
	public boolean isDone();
	public boolean isProcessing();
	public boolean isOk();
	public boolean isErrorHappened();
	public String getError();
}
