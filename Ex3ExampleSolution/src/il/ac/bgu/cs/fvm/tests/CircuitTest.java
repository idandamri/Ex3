package il.ac.bgu.cs.fvm.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import il.ac.bgu.cs.fvm.Ex3FacadeImpl;
import il.ac.bgu.cs.fvm.circuits.Circuit;
import il.ac.bgu.cs.fvm.examples.ExampleCircuit;
import il.ac.bgu.cs.fvm.exceptions.FVMException;
import il.ac.bgu.cs.fvm.labels.Action;
import il.ac.bgu.cs.fvm.labels.State;
import il.ac.bgu.cs.fvm.transitionsystem.Transition;
import il.ac.bgu.cs.fvm.transitionsystem.TransitionSystem;

public class CircuitTest {

	Ex3FacadeImpl Ex3FacadeImpl = new Ex3FacadeImpl();

	@Test
	public void test1() throws Exception {
		Circuit c = new ExampleCircuit();

		TransitionSystem ts = Ex3FacadeImpl.transitionSystemFromCircuit(c);

		TransitionSystem expected = expected();

		assertEquals(expected.getActions(), ts.getActions());
		assertEquals(expected.getAtomicPropositions(), ts.getAtomicPropositions());
		assertEquals(expected.getInitialStates(), ts.getInitialStates());
		assertEquals(expected.getLabelingFunction(), ts.getLabelingFunction());
		assertEquals(expected.getName(), ts.getName());
		assertEquals(expected.getStates(), ts.getStates());
		assertEquals(expected.getTransitions(), ts.getTransitions());
	}

	TransitionSystem expected() throws FVMException {
		TransitionSystem ts = Ex3FacadeImpl.createTransitionSystem();

		State tf = new State("[registers=[true], inputs=[false]]");
		State ft = new State("[registers=[false], inputs=[true]]");
		State tt = new State("[registers=[true], inputs=[true]]");
		State ff = new State("[registers=[false], inputs=[false]]");

		ts.addState(tf);
		ts.addState(ft);
		ts.addState(tt);
		ts.addState(ff);

		ts.addInitialState(ft);
		ts.addInitialState(ff);

		Action t = new Action("[true]");
		Action f = new Action("[false]");

		ts.addAction(t);
		ts.addAction(f);

		ts.addTransition(new Transition(tf, t, tt));
		ts.addTransition(new Transition(ff, f, ff));
		ts.addTransition(new Transition(tt, t, tt));
		ts.addTransition(new Transition(ft, t, tt));
		ts.addTransition(new Transition(ff, t, ft));
		ts.addTransition(new Transition(tt, f, tf));
		ts.addTransition(new Transition(ft, f, tf));
		ts.addTransition(new Transition(tf, f, tf));

		ts.addAtomicProposition("y1");
		ts.addAtomicProposition("x1");
		ts.addAtomicProposition("r1");

		ts.addLabel(tf, "r1");
		ts.addLabel(ft, "x1");
		ts.addLabel(tt, "y1");
		ts.addLabel(tt, "x1");
		ts.addLabel(tt, "r1");
		ts.addLabel(ff, "y1");

		return ts;
	}
}
