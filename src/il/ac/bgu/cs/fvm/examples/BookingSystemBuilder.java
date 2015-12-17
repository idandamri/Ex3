package il.ac.bgu.cs.fvm.examples;

import il.ac.bgu.cs.fvm.Ex3FacadeImpl;
import il.ac.bgu.cs.fvm.labels.Action;
import il.ac.bgu.cs.fvm.labels.State;
import il.ac.bgu.cs.fvm.transitionsystem.Transition;
import il.ac.bgu.cs.fvm.transitionsystem.TransitionSystem;

// Example 2.29 in the book
public class BookingSystemBuilder {
	static Ex3FacadeImpl Ex3FacadeImpl = new Ex3FacadeImpl();
	
	static public TransitionSystem buildBCR() {
		TransitionSystem ts = Ex3FacadeImpl.createTransitionSystem();

		ts.setName("BCR");

		State state0 = new State("0");
		ts.addState(state0);
		State state1 = new State("1");
		ts.addState(state1);
		ts.addInitialState(state0);

		Action scan = new Action("scan");
		ts.addAction(scan);
		Action store = new Action("store");
		ts.addAction(store);

		ts.addTransition(new Transition(state0, scan, state1));
		ts.addTransition(new Transition(state1, store, state0));

		return ts;
	}

	static public TransitionSystem buildBP() {
		TransitionSystem ts = Ex3FacadeImpl.createTransitionSystem();

		ts.setName("BP");

		State state0 = new State("0");
		ts.addState(state0);
		State state1 = new State("1");
		ts.addState(state1);
		ts.addInitialState(state0);

		Action prt_cmd = new Action("prt_cmd");
		ts.addAction(prt_cmd);
		Action store = new Action("store");
		ts.addAction(store);

		ts.addTransition(new Transition(state0, store, state1));
		ts.addTransition(new Transition(state1, prt_cmd, state0));

		return ts;
	}

	static public TransitionSystem buildPrinter() {
		TransitionSystem ts = Ex3FacadeImpl.createTransitionSystem();

		ts.setName("Printer");

		State state0 = new State("0");
		State state1 = new State("1");
		ts.addState(state0);
		ts.addState(state1);

		ts.addInitialState(state0);

		Action prt_cmd = new Action("prt_cmd");
		Action print = new Action("print");
		ts.addAction(prt_cmd);
		ts.addAction(print);

		ts.addTransition(new Transition(state0, prt_cmd, state1));
		ts.addTransition(new Transition(state1, print, state0));

		return ts;
	}

}
