package com.warework.service.workflow.flowbase;

/**
 * Flowbase functions.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public interface FlowbaseFunction {

	/**
	 * Function version in <code>x.y.z</code> format. By default it returns
	 * <code>null</code> but subclasses can override this method to provide an
	 * specific version.
	 * 
	 * @return Version of the function.<br>
	 * <br>
	 */
	String getVersion();
	
}
