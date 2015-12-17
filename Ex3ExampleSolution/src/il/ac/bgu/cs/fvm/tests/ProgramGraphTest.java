package il.ac.bgu.cs.fvm.tests;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import il.ac.bgu.cs.fvm.Ex3FacadeImpl;
import il.ac.bgu.cs.fvm.examples.CollatzProgramGraphBuilder;
import il.ac.bgu.cs.fvm.examples.ExtendedVendingMachineBuilder;
import il.ac.bgu.cs.fvm.exceptions.FVMException;
import il.ac.bgu.cs.fvm.labels.Action;
import il.ac.bgu.cs.fvm.labels.Location;
import il.ac.bgu.cs.fvm.labels.State;
import il.ac.bgu.cs.fvm.programgraph.ActionDef;
import il.ac.bgu.cs.fvm.programgraph.ConditionDef;
import il.ac.bgu.cs.fvm.programgraph.PGTransition;
import il.ac.bgu.cs.fvm.programgraph.ParserBasedActDef;
import il.ac.bgu.cs.fvm.programgraph.ParserBasedCondDef;
import il.ac.bgu.cs.fvm.programgraph.ProgramGraph;
import il.ac.bgu.cs.fvm.transitionsystem.Transition;
import il.ac.bgu.cs.fvm.transitionsystem.TransitionSystem;

public class ProgramGraphTest {
	Ex3FacadeImpl Ex3FacadeImpl = new Ex3FacadeImpl();
	
	@Test
	public void debug() throws Exception {
		ProgramGraph pg = Ex3FacadeImpl.createProgramGraph();

		Location l1 = new Location("L1");
		Location l2 = new Location("L2");

		pg.addLocation(l1);
		pg.addLocation(l2);

		pg.addInitialLocation(l1);

		Set<ActionDef> effect = new HashSet<>();
		effect.add(new ParserBasedActDef());

		Set<ConditionDef> cond = new HashSet<>();
		cond.add(new ParserBasedCondDef());

		pg.addTransition(new PGTransition(l1, "x>z", "x:=(x+y) % 5", l2));
		pg.addTransition(new PGTransition(l2, "", "z:=(z-y) % 5", l1));

		pg.addInitalization(asList("x:=1", "y:=2", "z:=0"));

		TransitionSystem ts = Ex3FacadeImpl.transitionSystemFromProgramGraph(pg, effect, cond);
		ts.setName("Program1");

		TransitionSystem expected = expectedForProgram1();

		assertEquals(expected.getActions(), ts.getActions());
		assertEquals(expected.getAtomicPropositions(), ts.getAtomicPropositions());
		assertEquals(expected.getInitialStates(), ts.getInitialStates());
		assertEquals(expected.getLabelingFunction(), ts.getLabelingFunction());
		assertEquals(expected.getName(), ts.getName());
		assertEquals(expected.getStates(), ts.getStates());
		assertEquals(expected.getTransitions(), ts.getTransitions());
	}

	TransitionSystem expectedForProgram1() throws FVMException {
		TransitionSystem ts = Ex3FacadeImpl.createTransitionSystem();

		ts.setName("Program1");

		State l1_1_2_0 = new State("[location=L1, eval={x=1, y=2, z=0}]");
		State l2_3_2_0 = new State("[location=L2, eval={x=3, y=2, z=0}]");
		State l1_3_2_3 = new State("[location=L1, eval={x=3, y=2, z=3}]");

		ts.addState(l1_1_2_0);
		ts.addState(l1_3_2_3);
		ts.addState(l2_3_2_0);

		ts.addInitialState(l1_1_2_0);

		Action l1_x_is_x_plus_y_l2 = new Action("x:=(x+y) % 5");
		Action l2_z_is_z_minus_y_l1 = new Action("z:=(z-y) % 5");

		ts.addAction(l1_x_is_x_plus_y_l2);
		ts.addAction(l2_z_is_z_minus_y_l1);

		ts.addTransition(new Transition(l1_1_2_0, l1_x_is_x_plus_y_l2, l2_3_2_0));
		ts.addTransition(new Transition(l2_3_2_0, l2_z_is_z_minus_y_l1, l1_3_2_3));

		ts.addAtomicProposition("y = 2");
		ts.addAtomicProposition("z = 0");
		ts.addAtomicProposition("x = 3");
		ts.addAtomicProposition("x = 1");
		ts.addAtomicProposition("z = 3");

		ts.addLabel(l1_1_2_0, "y = 2");
		ts.addLabel(l1_1_2_0, "z = 0");
		ts.addLabel(l1_1_2_0, "x = 1");
		ts.addLabel(l1_3_2_3, "y = 2");
		ts.addLabel(l1_3_2_3, "x = 3");
		ts.addLabel(l1_3_2_3, "z = 3");
		ts.addLabel(l2_3_2_0, "y = 2");
		ts.addLabel(l2_3_2_0, "z = 0");
		ts.addLabel(l2_3_2_0, "x = 3");

		return ts;
	}

	@Test
	public void soda() throws Exception {
		ProgramGraph pg = ExtendedVendingMachineBuilder.build();

		Set<ActionDef> actionDefs = ExtendedVendingMachineBuilder.getActionDefs();
		Set<ConditionDef> conditionDefs = ExtendedVendingMachineBuilder.getConditionDefs();

		TransitionSystem ts = Ex3FacadeImpl.transitionSystemFromProgramGraph(pg, actionDefs, conditionDefs);
		ts.setName("Soda");

		TransitionSystem expected = expectedForSoda();

		assertEquals(expected.getActions(), ts.getActions());
		assertEquals(expected.getAtomicPropositions(), ts.getAtomicPropositions());
		assertEquals(expected.getInitialStates(), ts.getInitialStates());
		assertEquals(expected.getLabelingFunction(), ts.getLabelingFunction());
		assertEquals(expected.getName(), ts.getName());
		assertEquals(expected.getStates(), ts.getStates());
		assertEquals(expected.getTransitions(), ts.getTransitions());
	}

	TransitionSystem expectedForSoda() throws FVMException {
		TransitionSystem ts = Ex3FacadeImpl.createTransitionSystem();

		ts.setName("Soda");

		State start_1_0 = new State("[location=start, eval={nbeer=1, nsoda=0}]");
		State select_2_0 = new State("[location=select, eval={nbeer=2, nsoda=0}]");
		State select_2_1 = new State("[location=select, eval={nbeer=2, nsoda=1}]");
		State select_2_2 = new State("[location=select, eval={nbeer=2, nsoda=2}]");
		State start_1_1 = new State("[location=start, eval={nbeer=1, nsoda=1}]");
		State start_1_2 = new State("[location=start, eval={nbeer=1, nsoda=2}]");
		State start_2_2 = new State("[location=start, eval={nbeer=2, nsoda=2}]");
		State select_0_2 = new State("[location=select, eval={nbeer=0, nsoda=2}]");
		State select_0_0 = new State("[location=select, eval={nbeer=0, nsoda=0}]");
		State select_0_1 = new State("[location=select, eval={nbeer=0, nsoda=1}]");
		State select_1_0 = new State("[location=select, eval={nbeer=1, nsoda=0}]");
		State select_1_1 = new State("[location=select, eval={nbeer=1, nsoda=1}]");
		State start_0_0 = new State("[location=start, eval={nbeer=0, nsoda=0}]");
		State select_1_2 = new State("[location=select, eval={nbeer=1, nsoda=2}]");
		State start_0_1 = new State("[location=start, eval={nbeer=0, nsoda=1}]");
		State start_2_0 = new State("[location=start, eval={nbeer=2, nsoda=0}]");
		State start_0_2 = new State("[location=start, eval={nbeer=0, nsoda=2}]");
		State start_2_1 = new State("[location=start, eval={nbeer=2, nsoda=1}]");

		ts.addState(start_1_0);
		ts.addState(select_2_0);
		ts.addState(select_2_1);
		ts.addState(select_2_2);
		ts.addState(start_1_1);
		ts.addState(start_1_2);
		ts.addState(start_2_2);
		ts.addState(select_0_2);
		ts.addState(select_0_0);
		ts.addState(select_0_1);
		ts.addState(select_1_0);
		ts.addState(select_1_1);
		ts.addState(start_0_0);
		ts.addState(select_1_2);
		ts.addState(start_0_1);
		ts.addState(start_2_0);
		ts.addState(start_0_2);
		ts.addState(start_2_1);
		ts.addInitialState(start_2_2);

		Action refill = new Action("refill");
		Action sget = new Action("sget");
		Action ret_coin = new Action("ret_coin");
		Action bget = new Action("bget");
		Action coin = new Action("coin");

		ts.addAction(refill);
		ts.addAction(sget);
		ts.addAction(ret_coin);
		ts.addAction(bget);
		ts.addAction(coin);

		ts.addTransition(new Transition(select_2_1, sget, start_2_0));
		ts.addTransition(new Transition(start_1_1, coin, select_1_1));
		ts.addTransition(new Transition(start_0_2, coin, select_0_2));
		ts.addTransition(new Transition(select_2_0, bget, start_1_0));
		ts.addTransition(new Transition(select_0_1, sget, start_0_0));
		ts.addTransition(new Transition(select_2_2, bget, start_1_2));
		ts.addTransition(new Transition(start_1_1, refill, start_2_2));
		ts.addTransition(new Transition(start_0_1, refill, start_2_2));
		ts.addTransition(new Transition(start_0_0, coin, select_0_0));
		ts.addTransition(new Transition(start_2_1, coin, select_2_1));
		ts.addTransition(new Transition(select_1_2, sget, start_1_1));
		ts.addTransition(new Transition(select_1_1, bget, start_0_1));
		ts.addTransition(new Transition(start_2_1, refill, start_2_2));
		ts.addTransition(new Transition(start_1_0, coin, select_1_0));
		ts.addTransition(new Transition(select_2_2, sget, start_2_1));
		ts.addTransition(new Transition(start_1_2, coin, select_1_2));
		ts.addTransition(new Transition(start_0_2, refill, start_2_2));
		ts.addTransition(new Transition(start_1_0, refill, start_2_2));
		ts.addTransition(new Transition(select_2_1, bget, start_1_1));
		ts.addTransition(new Transition(select_0_2, sget, start_0_1));
		ts.addTransition(new Transition(start_1_2, refill, start_2_2));
		ts.addTransition(new Transition(start_0_0, refill, start_2_2));
		ts.addTransition(new Transition(start_0_1, coin, select_0_1));
		ts.addTransition(new Transition(select_0_0, ret_coin, start_0_0));
		ts.addTransition(new Transition(start_2_0, coin, select_2_0));
		ts.addTransition(new Transition(start_2_2, coin, select_2_2));
		ts.addTransition(new Transition(select_1_1, sget, start_1_0));
		ts.addTransition(new Transition(select_1_0, bget, start_0_0));
		ts.addTransition(new Transition(select_1_2, bget, start_0_2));
		ts.addTransition(new Transition(start_2_0, refill, start_2_2));
		ts.addTransition(new Transition(start_2_2, refill, start_2_2));
		ts.addAtomicProposition("nbeer = 2");
		ts.addAtomicProposition("nbeer = 1");
		ts.addAtomicProposition("nbeer = 0");
		ts.addAtomicProposition("nsoda = 1");
		ts.addAtomicProposition("nsoda = 0");
		ts.addAtomicProposition("nsoda = 2");
		ts.addLabel(start_1_0, "nbeer = 1");
		ts.addLabel(start_1_0, "nsoda = 0");
		ts.addLabel(select_2_0, "nbeer = 2");
		ts.addLabel(select_2_0, "nsoda = 0");
		ts.addLabel(select_2_1, "nbeer = 2");
		ts.addLabel(select_2_1, "nsoda = 1");
		ts.addLabel(select_2_2, "nbeer = 2");
		ts.addLabel(select_2_2, "nsoda = 2");
		ts.addLabel(start_1_1, "nbeer = 1");
		ts.addLabel(start_1_1, "nsoda = 1");
		ts.addLabel(start_1_2, "nbeer = 1");
		ts.addLabel(start_1_2, "nsoda = 2");
		ts.addLabel(select_0_2, "nbeer = 0");
		ts.addLabel(select_0_2, "nsoda = 2");
		ts.addLabel(start_2_2, "nbeer = 2");
		ts.addLabel(start_2_2, "nsoda = 2");
		ts.addLabel(select_0_0, "nbeer = 0");
		ts.addLabel(select_0_0, "nsoda = 0");
		ts.addLabel(select_0_1, "nbeer = 0");
		ts.addLabel(select_0_1, "nsoda = 1");
		ts.addLabel(select_1_0, "nbeer = 1");
		ts.addLabel(select_1_0, "nsoda = 0");
		ts.addLabel(select_1_1, "nbeer = 1");
		ts.addLabel(select_1_1, "nsoda = 1");
		ts.addLabel(start_0_0, "nbeer = 0");
		ts.addLabel(start_0_0, "nsoda = 0");
		ts.addLabel(select_1_2, "nbeer = 1");
		ts.addLabel(select_1_2, "nsoda = 2");
		ts.addLabel(start_0_1, "nbeer = 0");
		ts.addLabel(start_0_1, "nsoda = 1");
		ts.addLabel(start_2_0, "nbeer = 2");
		ts.addLabel(start_2_0, "nsoda = 0");
		ts.addLabel(start_0_2, "nbeer = 0");
		ts.addLabel(start_0_2, "nsoda = 2");
		ts.addLabel(start_2_1, "nbeer = 2");
		ts.addLabel(start_2_1, "nsoda = 1");

		return ts;

	}

	@SuppressWarnings("serial")
	@Test
	public void collatz() throws Exception {
		ProgramGraph pg = CollatzProgramGraphBuilder.build();

		Set<ActionDef> ad = new HashSet<ActionDef>() {
			{
				add(new ParserBasedActDef());
			}
		};
		Set<ConditionDef> cd = new HashSet<ConditionDef>() {
			{
				add(new ParserBasedCondDef());
			}
		};

		TransitionSystem ts = Ex3FacadeImpl.transitionSystemFromProgramGraph(pg, ad, cd);

		TransitionSystem expected = expectedForCollatz();

		assertEquals(expected.getActions(), ts.getActions());
		assertEquals(expected.getAtomicPropositions(), ts.getAtomicPropositions());
		assertEquals(expected.getInitialStates(), ts.getInitialStates());
		assertEquals(expected.getLabelingFunction(), ts.getLabelingFunction());
		assertEquals(expected.getStates(), ts.getStates());
		assertEquals(expected.getTransitions(), ts.getTransitions());
	}

	TransitionSystem expectedForCollatz() throws FVMException {
		TransitionSystem ts = Ex3FacadeImpl.createTransitionSystem();

		State r6 = new State("[location=running, eval={x=6}]");
		State r3 = new State("[location=running, eval={x=3}]");
		State r10 = new State("[location=running, eval={x=10}]");
		State r5 = new State("[location=running, eval={x=5}]");
		State r16 = new State("[location=running, eval={x=16}]");
		State r8 = new State("[location=running, eval={x=8}]");
		State r4 = new State("[location=running, eval={x=4}]");
		State r2 = new State("[location=running, eval={x=2}]");
		State r1 = new State("[location=running, eval={x=1}]");
		State f1 = new State("[location=finished, eval={x=1}]");

		ts.addState(r6);
		ts.addState(r3);
		ts.addState(r10);
		ts.addState(r5);
		ts.addState(r16);
		ts.addState(r8);
		ts.addState(r4);
		ts.addState(r2);
		ts.addState(r1);
		ts.addState(f1);

		ts.addInitialState(r6);

		Action ntothing = new Action("");
		Action times3plus1 = new Action("x:= (3 * x) + 1");
		Action half = new Action("x:= x / 2");

		ts.addAction(ntothing);
		ts.addAction(times3plus1);
		ts.addAction(half);

		ts.addTransition(new Transition(r6, half, r3));
		ts.addTransition(new Transition(r3, times3plus1, r10));
		ts.addTransition(new Transition(r10, half, r5));
		ts.addTransition(new Transition(r5, times3plus1, r16));
		ts.addTransition(new Transition(r16, half, r8));
		ts.addTransition(new Transition(r8, half, r4));
		ts.addTransition(new Transition(r4, half, r2));
		ts.addTransition(new Transition(r2, half, r1));
		ts.addTransition(new Transition(r1, ntothing, f1));

		ts.addAtomicProposition("x = 5");
		ts.addAtomicProposition("x = 6");
		ts.addAtomicProposition("x = 3");
		ts.addAtomicProposition("x = 4");
		ts.addAtomicProposition("x = 16");
		ts.addAtomicProposition("x = 8");
		ts.addAtomicProposition("x = 1");
		ts.addAtomicProposition("x = 2");
		ts.addAtomicProposition("x = 10");

		ts.addLabel(r6, "x = 6");
		ts.addLabel(r5, "x = 5");
		ts.addLabel(r8, "x = 8");
		ts.addLabel(r16, "x = 16");
		ts.addLabel(r2, "x = 2");
		ts.addLabel(r10, "x = 10");
		ts.addLabel(r1, "x = 1");
		ts.addLabel(f1, "x = 1");
		ts.addLabel(r4, "x = 4");
		ts.addLabel(r3, "x = 3");

		return ts;
	}

}
