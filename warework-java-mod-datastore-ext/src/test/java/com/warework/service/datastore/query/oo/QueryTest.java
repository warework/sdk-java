package com.warework.service.datastore.query.oo;

import com.warework.core.model.Scope;
import com.warework.core.scope.AbstractModDatastoreExtTestCase;
import com.warework.core.scope.ScopeFacade;
import com.warework.core.scope.ScopeL1Constants;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class QueryTest extends AbstractModDatastoreExtTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testQuery1() {
		try {

			//
			final Scope config = new Scope("test");
			{
				config.setInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER, QueryTest.class);
			}

			//
			final ScopeFacade system = create(config);

			//
			Query<ScopeFacade> query = new Query<ScopeFacade>(system, ScopeFacade.class);

			// WHERE
			{

				//
				final Where where = query.getWhere(true);

				//
				final And and = where.createAnd();
				{

					//
					final Or or1 = where.createOr();
					{
						or1.add(where.createEqualToValue("user.name", "john"));
						or1.add(where.createEqualToValue("user.adress", "Ducket Rd."));
					}

					//
					final Or or2 = where.createOr();
					{
						or2.add(where.createEqualToValue("user.name", "john"));
						or2.add(where.createEqualToValue("user.adress", "Ducket Rd."));
					}

					//
					and.add(or1);
					and.add(or2);

				}

				//
				where.setExpression(and);

			}

		} catch (final Exception e) {
			fail();
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testQueryOposite1() {
		try {

			//
			final Scope config = new Scope("test");
			{
				config.setInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER, QueryTest.class);
			}

			//
			final ScopeFacade system = create(config);

			//
			final Query<ScopeFacade> query = new Query<ScopeFacade>(system, ScopeFacade.class);

			// WHERE
			{

				//
				final Where where = query.getWhere(true);

				//
				AbstractOperandExpression expr = (AbstractOperandExpression) where.createEqualToValue("name", "john");
				if (!expr.getOperator().equals(Operator.EQUAL_TO)) {
					fail();
				}

				//
				where.setExpression(expr.getOposite());

			}

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testQueryOposite2() {

		//
		try {

			//
			final Scope config = new Scope("test");
			{
				config.setInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER, QueryTest.class);
			}

			//
			final ScopeFacade system = create(config);

			//
			final Query<ScopeFacade> query = new Query<ScopeFacade>(system, ScopeFacade.class);

			// WHERE
			{

				//
				final Where where = query.getWhere(true);

				//
				final And and = where.createAnd();
				{

					//
					final Or or1 = where.createOr();
					{
						or1.add(where.createGreaterThanOrEqualToValue("age", new Integer(18)));
						or1.add(where.createLessThanOrEqualToValue("age", new Integer(31)));
					}

					//
					final Or or2 = where.createOr();
					{
						or2.add(where.createEqualToValue("user.name", "john"));
						or2.add(where.createNotEqualToValue("user.adress", "Ducket Rd."));
					}

					//
					and.add(or1);
					and.add(or2);

				}

				//
				where.setExpression(and.getOposite());

			}

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testQueryOposite3() {
		try {

			//
			final Scope config = new Scope("test");
			{
				config.setInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER, QueryTest.class);
			}

			//
			final ScopeFacade system = create(config);

			//
			final Query<ScopeFacade> query = new Query<ScopeFacade>(system, ScopeFacade.class);

			// WHERE
			{

				//
				final Where where = query.getWhere(true);

				//
				final AbstractOperandExpression expr = (AbstractOperandExpression) where.createEqualToValue("name",
						"john");

				//
				where.setExpression(where.createNot(expr).getOposite());

			}

		} catch (final Exception e) {
			fail();
		}
	}

}
