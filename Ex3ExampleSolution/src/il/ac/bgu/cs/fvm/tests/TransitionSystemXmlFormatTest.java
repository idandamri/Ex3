package il.ac.bgu.cs.fvm.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.StringReader;
import java.io.StringWriter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import il.ac.bgu.cs.fvm.Ex3FacadeImpl;
import il.ac.bgu.cs.fvm.labels.Action;
import il.ac.bgu.cs.fvm.labels.State;
import il.ac.bgu.cs.fvm.transitionsystem.StAXTransitionSystemXmlFormat;
import il.ac.bgu.cs.fvm.transitionsystem.Transition;
import il.ac.bgu.cs.fvm.transitionsystem.TransitionSystem;
import il.ac.bgu.cs.fvm.transitionsystem.TransitionSystemXmlFormat;

/**
 *
 * @author michael
 */
public class TransitionSystemXmlFormatTest {

	Ex3FacadeImpl Ex3FacadeImpl = new Ex3FacadeImpl();
	
	public TransitionSystemXmlFormatTest() {
	}

	@BeforeClass
	public static void setUpClass() {
	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	@Test
	public void roundTripTest() throws Exception {
		TransitionSystem originalTs = new TSTestUtils().simpleTransitionSystem();

		TransitionSystemXmlFormat sut = new StAXTransitionSystemXmlFormat();

		StringWriter wrt = new StringWriter();
		sut.write(originalTs, wrt);
		String out = wrt.toString();

		try (StringReader rdr = new StringReader(out)) {
			TransitionSystem actualTs = sut.read(rdr);
			assertEquals(originalTs, actualTs);

		} catch (Exception e) {
			fail("Roundtrip failed: " + e.getMessage());
		}
	}

	@Test
	public void readXMLFromEditor() throws Exception {
		// @formatter:off
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-16\"?>\n" + 
				"<transitionSystem>\n" + 
				"	<states>\n" + 
				"		<state sId=\"pay\" loc=\"-203.99999999999991 158\"/>\n" + 
				"		<state sId=\"soda\" loc=\"-469.00000000000017 398.9999999999994\"/>\n" + 
				"		<state sId=\"beer\" loc=\"34 410.00000000000017\"/>\n" + 
				"		<state sId=\"select\" loc=\"-210 410\"/>\n" + 
				"	</states>\n" + 
				"	<atomicPropositions>\n" + 
				"		<atomicProposition apId=\"paid\">paid</atomicProposition>\n" + 
				"		<atomicProposition apId=\"drink\">drink</atomicProposition>\n" + 
				"	</atomicPropositions>\n" + 
				"	<initialStates>\n" + 
				"		<initialState state=\"pay\"/>\n" + 
				"	</initialStates>\n" + 
				"	<labelingFunction>\n" + 
				"		<entry state=\"soda\">\n" + 
				"			<label atomicProposition=\"paid\"/>\n" + 
				"			<label atomicProposition=\"drink\"/>\n" + 
				"		</entry>\n" + 
				"		<entry state=\"beer\">\n" + 
				"			<label atomicProposition=\"paid\"/>\n" + 
				"			<label atomicProposition=\"drink\"/>\n" + 
				"		</entry>\n" + 
				"		<entry state=\"select\">\n" + 
				"			<label atomicProposition=\"paid\"/>\n" + 
				"		</entry>\n" + 
				"	</labelingFunction>\n" + 
				"	<actions>\n" + 
				"		<action aId=\"insert_coin\"/>\n" + 
				"		<action aId=\"get_beer\"/>\n" + 
				"		<action aId=\"get_soda\"/>\n" + 
				"		<action aId=\"tau\"/>\n" + 
				"	</actions>\n" + 
				"	<transitions>\n" + 
				"		<transition action=\"insert_coin\" from=\"pay\" to=\"select\" points=\"-175.23667435672436,201.07753579891087,-174.88957938625063,267.95762829981703,-181.94286868163016,338.6132307596229,-179.1848458142604,410\"/>\n" + 
				"		<transition action=\"get_beer\" from=\"beer\" to=\"pay\" points=\"39.54858993156202,410.00000000000017,-29.62726251797896,348.1547464029482,-96.69595095703073,277.8638202354877,-159.19240587863584,200.6985014050712\"/>\n" + 
				"		<transition action=\"get_soda\" from=\"soda\" to=\"pay\" points=\"-423.9102127082417,398.9999999999994,-353.39198195550375,325.55978858371764,-279.2473364952663,259.56442229402296,-199.70579107678077,199.230448893665\"/>\n" + 
				"		<transition action=\"tau\" from=\"select\" to=\"soda\" points=\"-210.00000000000003,427.41213592233003,-281,424,-347,423,-414.0000000000001,419.4195767195762\"/>\n" + 
				"		<transition action=\"tau\" from=\"select\" to=\"beer\" points=\"-146.00000000000003,429.3192307692307,-87,430,-32,426,33.99999999999999,428.09354838709686\"/>\n" + 
				"	</transitions>\n" + 
				"</transitionSystem>\n" + 
				"" + 
				"  "; 
		// @formatter:on

		TransitionSystemXmlFormat sut = new StAXTransitionSystemXmlFormat();
		TransitionSystem ts = sut.read(new StringReader(xml));

		TransitionSystem expTs = Ex3FacadeImpl.createTransitionSystem();
		expTs.setName("null");
		expTs.addState(new State("select"));
		expTs.addState(new State("pay"));
		expTs.addState(new State("soda"));
		expTs.addState(new State("beer"));
		expTs.addInitialState(new State("pay"));
		expTs.addAction(new Action("get_beer"));
		expTs.addAction(new Action("get_soda"));
		expTs.addAction(new Action("insert_coin"));
		expTs.addAction(new Action("tau"));
		expTs.addTransition(new Transition(new State("pay"), new Action("insert_coin"), new State("select")));
		expTs.addTransition(new Transition(new State("beer"), new Action("get_beer"), new State("pay")));
		expTs.addTransition(new Transition(new State("soda"), new Action("get_soda"), new State("pay")));
		expTs.addTransition(new Transition(new State("select"), new Action("tau"), new State("soda")));
		expTs.addTransition(new Transition(new State("select"), new Action("tau"), new State("beer")));

		String paid = "paid";
		String drink = "drink";

		expTs.addAtomicProposition(paid);
		expTs.addAtomicProposition(drink);
		expTs.addLabel(new State("select"), paid);
		expTs.addLabel(new State("soda"), paid);
		expTs.addLabel(new State("soda"), drink);
		expTs.addLabel(new State("beer"), paid);
		expTs.addLabel(new State("beer"), drink);

		assertEquals(expTs.getActions(), ts.getActions());
		assertEquals(expTs.getAtomicPropositions(), ts.getAtomicPropositions());
		assertEquals(expTs.getInitialStates(), ts.getInitialStates());
		assertEquals(expTs.getLabelingFunction(), ts.getLabelingFunction());
		assertEquals(expTs.getStates(), ts.getStates());
		assertEquals(expTs.getTransitions(), ts.getTransitions());

	}

}
