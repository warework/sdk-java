package com.warework.core.service.client.model;

import java.util.ArrayList;
import java.util.List;

import com.warework.core.model.Client;
import com.warework.core.scope.AbstractCoreTestCase;
import com.warework.core.util.bean.Parameter;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class ClientTest extends AbstractCoreTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testClient1() {

		//
		final Client bean = new Client();

		//
		bean.setName("sample1");
		bean.setConnector("sample2");

		//
		final Parameter param1 = new Parameter("p1n", "p1v");
		final Parameter param2 = new Parameter("p2n", "p2v");

		//
		final List<Parameter> parameters = new ArrayList<Parameter>();
		{
			parameters.add(param1);
			parameters.add(param2);
		}

		//
		bean.setParameters(parameters);

		//
		if (!bean.toString().equals("name=sample1 ; connector-type=sample2 ; parameters=[p1n=p1v;p2n=p2v]")) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testClient2() {

		//
		final Client bean = new Client();

		//
		if (!bean.toString().equals("name=null ; connector-type=null ; parameters=[]")) {
			fail();
		}

	}

}
