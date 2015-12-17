package il.ac.bgu.cs.fvm.tests;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import il.ac.bgu.cs.fvm.Ex3FacadeImpl;
import il.ac.bgu.cs.fvm.labels.Action;
import il.ac.bgu.cs.fvm.labels.State;
import il.ac.bgu.cs.fvm.transitionsystem.StAXTransitionSystemXmlFormat;
import il.ac.bgu.cs.fvm.transitionsystem.TransitionSystem;
import il.ac.bgu.cs.fvm.transitionsystem.TransitionSystemXmlFormat;

public class PostTests {

	TSTestUtils b;
	TransitionSystem ts;
	
	Ex3FacadeImpl impl = new Ex3FacadeImpl();

	@Before
	public void moreTests() throws Exception {
		ts = impl.createTransitionSystem();

		String XML = "<?xml version=\"1.0\" encoding=\"UTF-16\"?>\n" + //
				"<transitionSystem>\n" + //
				"	<states>\n" + //
				"		<state sId=\"s1\" loc=\"78.61023719540495 -53.192008588785455\"/>\n" + //
				"		<state sId=\"s2\" loc=\"34.06596779386897 -190.8088961935045\"/>\n" + //
				"		<state sId=\"s3\" loc=\"210.46734719412913 65.35394592857969\"/>\n" + //
				"	</states>\n" + //
				"	<atomicPropositions>\n" + //
				"	</atomicPropositions>\n" + //
				"	<initialStates>\n" + //
				"		<initialState state=\"s1\"/>\n" + //
				"	</initialStates>\n" + //
				"	<labelingFunction>\n" + //
				"	</labelingFunction>\n" + //
				"	<actions>\n" + //
				"		<action aId=\"a1\"/>\n" + //
				"		<action aId=\"a2\"/>\n" + //
				"	</actions>\n" + //
				"	<transitions>\n" + //
				"		<transition action=\"a1\" from=\"s1\" to=\"s2\" points=\"89.42781383085367,-51.84335645004435,71.44892463084753,-83.74239841315337,60.55422326939721,-117.92248441661917,56.201091103498655,-154.2088961935045\"/>\n" + //
				"		<transition action=\"a2\" from=\"s1\" to=\"s3\" points=\"122.69412064778432,-16.745955851560964,157.78435380245216,5.17808915885543,188.74537046976334,32.706899365814245,215.23491662391874,65.35394592857969\"/>\n" + //
				"		<transition action=\"a1\" from=\"s1\" to=\"s1\" points=\"113.10787983238936,-10.527507374258235,123.10787983238936,6.793000701430536,79.36543658554146,8.087116271737283,89.36543658554146,-9.233391803951491\"/>\n" + //
				"		<transition action=\"a1\" from=\"s2\" to=\"s1\" points=\"58.22219690676721,-154.2088961935045,66.3905371331597,-118.75039267728869,156,-162,110.4231837986335,-51.75876920236699\"/>\n" + //
				"	</transitions>\n" + //
				"</transitionSystem>\n" + //
				"";

		TransitionSystemXmlFormat sut = new StAXTransitionSystemXmlFormat();
		ts = sut.read(new StringReader(XML));
	}

	@Test
	public void postTest() throws Exception {

		Set<State> expected = new HashSet<State>();
		expected.add(new State("s1"));
		expected.add(new State("s2"));

		assertEquals(expected, impl.post(ts, new State("s1"), new Action("a1")));

		expected.add(new State("s3"));
		assertEquals(expected, impl.post(ts, new State("s1")));

		expected.clear();
		expected.add(new State("s1"));
		expected.add(new State("s2"));
		assertEquals(expected, impl.pre(ts, new State("s1"), new Action("a1")));
	}

}
