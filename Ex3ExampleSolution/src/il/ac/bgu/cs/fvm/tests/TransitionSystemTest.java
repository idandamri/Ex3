package il.ac.bgu.cs.fvm.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import il.ac.bgu.cs.fvm.Ex3FacadeImpl;
import il.ac.bgu.cs.fvm.exceptions.DeletionOfAttachedActionException;
import il.ac.bgu.cs.fvm.exceptions.DeletionOfAttachedAtomicPropositionException;
import il.ac.bgu.cs.fvm.exceptions.DeletionOfAttachedStateException;
import il.ac.bgu.cs.fvm.exceptions.FVMException;
import il.ac.bgu.cs.fvm.exceptions.InvalidInitialStateException;
import il.ac.bgu.cs.fvm.exceptions.InvalidTransitionException;
import il.ac.bgu.cs.fvm.exceptions.TransitionSystemPart;
import il.ac.bgu.cs.fvm.labels.Action;
import il.ac.bgu.cs.fvm.labels.State;
import il.ac.bgu.cs.fvm.transitionsystem.Transition;
import il.ac.bgu.cs.fvm.transitionsystem.TransitionSystem;

public class TransitionSystemTest {

	TSTestUtils			b;
	TransitionSystem	ts;

	Ex3FacadeImpl		Ex3FacadeImpl	= new Ex3FacadeImpl();

	@Before
	public void before() {
		b = new TSTestUtils();
		ts = Ex3FacadeImpl.createTransitionSystem();
	}

	@Test(expected = InvalidInitialStateException.class)
	public void initialStateMustBeInStates() throws Exception {
		ts.addState(b.state("s1"));
		ts.addState(b.state("s2"));
		ts.addInitialState(b.state("s3"));
	}

	public void initialStateMustBeInStatesValid() throws Exception {
		ts.addState(b.state("s1"));
		ts.addState(b.state("s2"));
		ts.addInitialState(b.state("s1"));
	}

	@Test(expected = DeletionOfAttachedStateException.class)
	public void initialStateCantBeRemoved() throws Exception {
		ts.addState(b.state("s1"));
		ts.addInitialState(b.state("s1"));
		ts.removeState(b.state("s1"));
	}

	@Test
	public void initialStateCanBeRemovedAfterCleaning() throws Exception {
		ts.addState(b.state("s1"));
		ts.addInitialState(b.state("s1"));
		ts.removeInitialState(b.state("s1"));
		ts.removeState(b.state("s1"));
	}

	@Test(expected = DeletionOfAttachedAtomicPropositionException.class)
	public void usedLabelCantBeRemoved() throws Exception {
		ts.addState(b.state("s1"));
		ts.addAtomicProposition("q");
		ts.addLabel(b.state("s1"), "q");
		ts.removeAtomicProposition("q");
	}

	@Test
	public void usedLabelCanBeRemovedAfterCleaning() throws Exception {
		ts.addState(b.state("s1"));
		ts.addAtomicProposition("q");
		ts.addLabel(b.state("s1"), "q");
		ts.removeLabel(b.state("s1"), "q");
		ts.removeAtomicProposition("q");
	}

	@Test(expected = DeletionOfAttachedStateException.class)
	public void labeledStateCantBeRemoved() throws Exception {
		ts.addState(b.state("s1"));
		ts.addAtomicProposition("p");
		ts.addLabel(b.state("s1"), "p");
		ts.removeState(b.state("s1"));
	}

	@Test
	public void labeledStateCanBeRemovedAfterCleaning() throws Exception {
		ts.addState(b.state("s1"));
		ts.addAtomicProposition("p");
		ts.addLabel(b.state("s1"), "p");
		ts.removeLabel(b.state("s1"), "p");
		ts.removeState(b.state("s1"));
	}

	@Test
	public void addValidTransition() throws Exception {
		ts.addState(b.state("s1"));
		ts.addState(b.state("s2"));
		ts.addAction(b.action("a1"));
		ts.addTransition(b.transition("s1", "a1", "s2"));
	}

	@Test(expected = InvalidTransitionException.class)
	public void addInvalidTransition_fromState() throws Exception {
		ts.addState(b.state("s1"));
		ts.addState(b.state("s2"));
		ts.addAction(b.action("a1"));
		ts.addTransition(b.transition("s3", "a1", "s2"));
	}

	@Test(expected = InvalidTransitionException.class)
	public void addInvalidTransition_toState() throws Exception {
		ts.addState(b.state("s1"));
		ts.addState(b.state("s2"));
		ts.addAction(b.action("a1"));
		ts.addTransition(b.transition("s1", "a1", "s3"));
	}

	@Test(expected = InvalidTransitionException.class)
	public void addInvalidTransition_action() throws Exception {
		ts.addState(b.state("s1"));
		ts.addState(b.state("s2"));
		ts.addAction(b.action("a1"));
		ts.addTransition(b.transition("s1", "aXX", "s2"));
	}

	@Test(expected = DeletionOfAttachedStateException.class)
	public void cannotRemoveStateInTransition() throws Exception {
		ts.addState(b.state("s1"));
		ts.addState(b.state("s2"));
		ts.addAction(b.action("a1"));
		ts.addTransition(b.transition("s1", "a1", "s2"));
		ts.removeState(b.state("s1"));
	}

	@Test(expected = DeletionOfAttachedActionException.class)
	public void cannotRemoveActionInTransition() throws Exception {
		ts.addState(b.state("s1"));
		ts.addState(b.state("s2"));
		ts.addAction(b.action("a1"));
		ts.addTransition(b.transition("s1", "a1", "s2"));
		ts.removeAction(b.action("a1"));
	}

	@Test
	public void checkExeceptionParametersInAlongerScenario() throws Exception {
		ts.setName("TS1");
		ts.addState(new State("s1"));
		ts.addState(new State("s2"));
		ts.addAction(new Action("a1"));
		ts.addAction(new Action("a2"));
		ts.addInitialState(new State("s1"));
		try {
			ts.addInitialState(new State("s3"));
			fail("Expected an IllegalOperationException because s3 is not a state");
		} catch (FVMException ex) {
			assertEquals(new InvalidInitialStateException(new State("s3")), ex);
		}

		try {
			ts.removeState(new State("s1"));
			fail("Expected an IllegalOperationException because s1 is initial");
		} catch (FVMException ex) {
			assertEquals(new DeletionOfAttachedStateException(new State("s1"), TransitionSystemPart.INITIAL_STATES), ex);
		}

		ts.addState(new State("s3"));
		ts.removeState(new State("s3"));

		ts.addAction(new Action("a3"));
		ts.removeAction(new Action("a3"));

		ts.addAtomicProposition("p");
		ts.addAtomicProposition("q");
		ts.addLabel(new State("s2"), "p");
		ts.addLabel(new State("s1"), "q");
		ts.addLabel(new State("s1"), "p");

		try {
			ts.removeAtomicProposition("q");
			fail("Expected an exception because there is a state labeled by q");
		} catch (FVMException ex) {
			assertEquals(new DeletionOfAttachedAtomicPropositionException("q", TransitionSystemPart.LABELING_FUNCTION), ex);
		}

		try {
			ts.removeState(new State("s2"));
			fail("Expected an exception because s2 is labeled");
		} catch (FVMException ex) {
			assertEquals(new DeletionOfAttachedStateException(new State("s2"), TransitionSystemPart.LABELING_FUNCTION), ex);
		}

		ts.removeLabel(new State("s2"), "p");

		ts.addTransition(new Transition(new State("s1"), new Action("a1"), new State("s2")));
		ts.addTransition(new Transition(new State("s1"), new Action("a1"), new State("s2")));

		Transition t = new Transition(new State("s1"), new Action("a1"), new State("s3"));
		try {
			ts.addTransition(t);
			fail("Expected an exception because s3 is not a state");
		} catch (FVMException ex) {
			assertEquals(new InvalidTransitionException(t), ex);
		}

		t = new Transition(new State("s1"), new Action("a3"), new State("s2"));
		try {
			ts.addTransition(t);
			fail("Expected an exception because a3 is not an action");
		} catch (FVMException ex) {
			assertEquals(new InvalidTransitionException(t), ex);
		}

		try {
			ts.removeState(new State("s2"));
			fail("Expected an exception because s2 is in a transition");
		} catch (FVMException ex) {
			assertEquals(new DeletionOfAttachedStateException(new State("s2"), TransitionSystemPart.TRANSITIONS), ex);
		}

		try {
			ts.removeAction(new Action("a1"));
			fail("Expected an IllegalOperationException because a1 is in a transition");
		} catch (FVMException ex) {
			assertEquals(new DeletionOfAttachedActionException(new Action("a1"), TransitionSystemPart.TRANSITIONS), ex);
		}
	}
}
