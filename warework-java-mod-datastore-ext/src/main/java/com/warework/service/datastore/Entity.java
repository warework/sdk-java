package com.warework.service.datastore;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies a single data structure in a Data Store (for example: a table in a
 * Relational Database).<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {

	/**
	 * Name of the entity (for example: name of the table in a Relational
	 * Database).
	 * 
	 * @return Name of the entity.
	 */
	String name();

}
