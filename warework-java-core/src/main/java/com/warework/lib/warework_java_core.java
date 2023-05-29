package com.warework.lib;

import com.warework.core.ArtifactMetadata;

/**
 * Version information for Warework components.<br>
 * 
 * @author Jose Schiaffino
 */
public final class warework_java_core implements ArtifactMetadata {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Warework component version.
	private static final String VERSION = "3.0.0";

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the artifact version.
	 * 
	 * @return Version of the Warework component.<br>
	 * <br>
	 */
	public String getVersion() {
		return VERSION;
	}

}
