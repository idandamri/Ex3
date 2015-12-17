package il.ac.bgu.cs.fvm.tests;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import il.ac.bgu.cs.fvm.Ex3FacadeImpl;
import il.ac.bgu.cs.fvm.examples.PetersonProgramGraphBuilder;
import il.ac.bgu.cs.fvm.examples.SemaphoreBasedMutualExclusionBuilder;
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

public class ProgramGraphProductTest {

	Ex3FacadeImpl Ex3FacadeImpl = new Ex3FacadeImpl();
	
	@SuppressWarnings("serial")
	@Test
	// See Figure 2.9 and Figure 2.10
	public void peterson() throws Exception {
		ProgramGraph pg1 = PetersonProgramGraphBuilder.build(1);
		ProgramGraph pg2 = PetersonProgramGraphBuilder.build(2);

		ProgramGraph pg = Ex3FacadeImpl.interleave(pg1, pg2);

		ProgramGraph expectedPG = expectedPG();
		assertEquals(expectedPG.getInitialLocations(), pg.getInitialLocations());
		assertEquals(expectedPG.getLocations(), pg.getLocations());
		assertEquals(expectedPG.getTransitions(), pg.getTransitions());

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
		ts.setName("Peterson");

		TransitionSystem expected = expectedTS();

		assertEquals(expected.getInitialStates(), ts.getInitialStates());
		assertEquals(expected.getActions(), ts.getActions());
		assertEquals(expected.getAtomicPropositions(), ts.getAtomicPropositions());
		assertEquals(expected.getStates(), ts.getStates());
		assertEquals(expected.getLabelingFunction(), ts.getLabelingFunction());
		assertEquals(expected.getName(), ts.getName());
		assertEquals(expected.getTransitions(), ts.getTransitions());

	}
	
	// See Page 43 Figure 2.6
	@SuppressWarnings("serial")
	@Test
	public void semaphorebased() throws Exception {
		ProgramGraph pg1 = SemaphoreBasedMutualExclusionBuilder.build(1);
		ProgramGraph pg2 = SemaphoreBasedMutualExclusionBuilder.build(2);

		ProgramGraph pg = Ex3FacadeImpl.interleave(pg1, pg2);

		ProgramGraph expectedPG = expectedPGSem();
		assertEquals(expectedPG.getInitialLocations(), pg.getInitialLocations());
		assertEquals(expectedPG.getLocations(), pg.getLocations());
		assertEquals(expectedPG.getTransitions(), pg.getTransitions());

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

		TransitionSystem expected = expectedTSSem();
		assertEquals(expected.getInitialStates(), ts.getInitialStates());
		assertEquals(expected.getActions(), ts.getActions());
		assertEquals(expected.getAtomicPropositions(), ts.getAtomicPropositions());
		assertEquals(expected.getStates(), ts.getStates());
		assertEquals(expected.getLabelingFunction(), ts.getLabelingFunction());
		assertEquals(expected.getName(), ts.getName());
		assertEquals(expected.getTransitions(), ts.getTransitions());

	}

	ProgramGraph expectedPGSem() throws FVMException {
		ProgramGraph pg = Ex3FacadeImpl.createProgramGraph();
		Location wt1_cr2 = new Location("wait1,crit2");
		Location wt1_wt2 = new Location("wait1,wait2");
		Location cr1_wt2 = new Location("crit1,wait2");
		Location nc1_cr2 = new Location("noncrit1,crit2");
		Location nc1_wt2 = new Location("noncrit1,wait2");
		Location nc1_nc2 = new Location("noncrit1,noncrit2");
		Location wt1_nc2 = new Location("wait1,noncrit2");
		Location cr1_cr2 = new Location("crit1,crit2");
		Location cr1_nc2 = new Location("crit1,noncrit2");

		pg.addLocation(wt1_cr2);
		pg.addLocation(wt1_wt2);
		pg.addLocation(cr1_wt2);
		pg.addLocation(nc1_cr2);
		pg.addLocation(nc1_wt2);
		pg.addLocation(nc1_nc2);
		pg.addLocation(wt1_nc2);
		pg.addLocation(cr1_cr2);
		pg.addLocation(cr1_nc2);

		pg.addInitialLocation(nc1_nc2);

		pg.addTransition(new PGTransition(nc1_wt2, "y>0", "y:=y-1", nc1_cr2));
		pg.addTransition(new PGTransition(wt1_wt2, "y>0", "y:=y-1", wt1_cr2));
		pg.addTransition(new PGTransition(nc1_cr2, "true", "y:=y+1", nc1_nc2));
		pg.addTransition(new PGTransition(wt1_nc2, "true", "", wt1_wt2));
		pg.addTransition(new PGTransition(nc1_cr2, "true", "", wt1_cr2));
		pg.addTransition(new PGTransition(cr1_nc2, "true", "", cr1_wt2));
		pg.addTransition(new PGTransition(nc1_wt2, "true", "", wt1_wt2));
		pg.addTransition(new PGTransition(wt1_cr2, "true", "y:=y+1", wt1_nc2));
		pg.addTransition(new PGTransition(cr1_cr2, "true", "y:=y+1", cr1_nc2));
		pg.addTransition(new PGTransition(nc1_nc2, "true", "", nc1_wt2));
		pg.addTransition(new PGTransition(wt1_cr2, "y>0", "y:=y-1", cr1_cr2));
		pg.addTransition(new PGTransition(wt1_nc2, "y>0", "y:=y-1", cr1_nc2));
		pg.addTransition(new PGTransition(cr1_nc2, "true", "y:=y+1", nc1_nc2));
		pg.addTransition(new PGTransition(cr1_wt2, "y>0", "y:=y-1", cr1_cr2));
		pg.addTransition(new PGTransition(cr1_wt2, "true", "y:=y+1", nc1_wt2));
		pg.addTransition(new PGTransition(cr1_cr2, "true", "y:=y+1", nc1_cr2));
		pg.addTransition(new PGTransition(wt1_wt2, "y>0", "y:=y-1", cr1_wt2));
		pg.addTransition(new PGTransition(nc1_nc2, "true", "", wt1_nc2));

		return pg;
	}

	TransitionSystem expectedTSSem() throws FVMException {
		TransitionSystem ts = Ex3FacadeImpl.createTransitionSystem();
		State nc1_nc2_1 = new State("[location=noncrit1,noncrit2, eval={y=1}]");
		State wt1_nc2_1 = new State("[location=wait1,noncrit2, eval={y=1}]");
		State nc1_cr2_0 = new State("[location=noncrit1,crit2, eval={y=0}]");
		State wt1_cr2_0 = new State("[location=wait1,crit2, eval={y=0}]");

		State nc1_wt2_1 = new State("[location=noncrit1,wait2, eval={y=1}]");
		State wt1_wt2_1 = new State("[location=wait1,wait2, eval={y=1}]");
		State cr1_nc2_0 = new State("[location=crit1,noncrit2, eval={y=0}]");
		State cr1_wt2_0 = new State("[location=crit1,wait2, eval={y=0}]");

		ts.addState(nc1_nc2_1);
		ts.addState(wt1_nc2_1);
		ts.addState(cr1_wt2_0);
		ts.addState(nc1_cr2_0);
		ts.addState(wt1_wt2_1);
		ts.addState(cr1_nc2_0);
		ts.addState(nc1_wt2_1);
		ts.addState(wt1_cr2_0);

		ts.addInitialState(nc1_nc2_1);

		ts.addAction(new Action(""));
		ts.addAction(new Action("y:=y-1"));
		ts.addAction(new Action("y:=y+1"));

		ts.addTransition(new Transition(wt1_wt2_1, new Action("y:=y-1"), cr1_wt2_0));
		ts.addTransition(new Transition(nc1_nc2_1, new Action(""), wt1_nc2_1));
		ts.addTransition(new Transition(nc1_cr2_0, new Action("y:=y+1"), nc1_nc2_1));
		ts.addTransition(new Transition(nc1_nc2_1, new Action(""), nc1_wt2_1));
		ts.addTransition(new Transition(wt1_nc2_1, new Action(""), wt1_wt2_1));
		ts.addTransition(new Transition(wt1_wt2_1, new Action("y:=y-1"), wt1_cr2_0));
		ts.addTransition(new Transition(cr1_nc2_0, new Action(""), cr1_wt2_0));
		ts.addTransition(new Transition(wt1_nc2_1, new Action("y:=y-1"), cr1_nc2_0));
		ts.addTransition(new Transition(nc1_wt2_1, new Action("y:=y-1"), nc1_cr2_0));
		ts.addTransition(new Transition(cr1_nc2_0, new Action("y:=y+1"), nc1_nc2_1));
		ts.addTransition(new Transition(nc1_cr2_0, new Action(""), wt1_cr2_0));
		ts.addTransition(new Transition(wt1_cr2_0, new Action("y:=y+1"), wt1_nc2_1));
		ts.addTransition(new Transition(nc1_wt2_1, new Action(""), wt1_wt2_1));
		ts.addTransition(new Transition(cr1_wt2_0, new Action("y:=y+1"), nc1_wt2_1));

		ts.addAtomicProposition("y = 0");
		ts.addAtomicProposition("y = 1");

		ts.addLabel(cr1_wt2_0, "y = 0");
		ts.addLabel(nc1_cr2_0, "y = 0");
		ts.addLabel(wt1_nc2_1, "y = 1");
		ts.addLabel(nc1_nc2_1, "y = 1");
		ts.addLabel(wt1_wt2_1, "y = 1");
		ts.addLabel(cr1_nc2_0, "y = 0");
		ts.addLabel(wt1_cr2_0, "y = 0");
		ts.addLabel(nc1_wt2_1, "y = 1");

		return ts;
	}

	ProgramGraph expectedPG() throws FVMException {

		ProgramGraph pg = Ex3FacadeImpl.createProgramGraph();

		Location cr1_nc2 = new Location("crit1,noncrit2");
		Location wt1_wt2 = new Location("wait1,wait2");
		Location cr1_wt2 = new Location("crit1,wait2");
		Location nc1_wt2 = new Location("noncrit1,wait2");
		Location nc1_nc2 = new Location("noncrit1,noncrit2");
		Location wt1_nc2 = new Location("wait1,noncrit2");
		Location cr1_cr2 = new Location("crit1,crit2");
		Location wt1_cr2 = new Location("wait1,crit2");
		Location nc1_cr2 = new Location("noncrit1,crit2");

		pg.addLocation(cr1_nc2);
		pg.addLocation(wt1_wt2);
		pg.addLocation(cr1_wt2);
		pg.addLocation(nc1_wt2);
		pg.addLocation(nc1_nc2);
		pg.addLocation(wt1_nc2);
		pg.addLocation(nc1_cr2);
		pg.addLocation(wt1_cr2);
		pg.addLocation(cr1_cr2);

		pg.addInitialLocation(nc1_nc2);

		pg.addTransition(new PGTransition(nc1_cr2, "true", "atomic{b1:=1;x:=2}", wt1_cr2));
		pg.addTransition(new PGTransition(cr1_nc2, "true", "atomic{b2:=1;x:=1}", cr1_wt2));
		pg.addTransition(new PGTransition(cr1_wt2, "true", "b1:=0", nc1_wt2));
		pg.addTransition(new PGTransition(wt1_nc2, "true", "atomic{b2:=1;x:=1}", wt1_wt2));
		pg.addTransition(new PGTransition(nc1_nc2, "true", "atomic{b1:=1;x:=2}", wt1_nc2));
		pg.addTransition(new PGTransition(cr1_cr2, "true", "b2:=0", cr1_nc2));
		pg.addTransition(new PGTransition(nc1_cr2, "true", "b2:=0", nc1_nc2));
		pg.addTransition(new PGTransition(cr1_cr2, "true", "b1:=0", nc1_cr2));
		pg.addTransition(new PGTransition(nc1_wt2, "true", "atomic{b1:=1;x:=2}", wt1_wt2));
		pg.addTransition(new PGTransition(wt1_wt2, "x==2 || b1==0", "", wt1_cr2));
		pg.addTransition(new PGTransition(cr1_nc2, "true", "b1:=0", nc1_nc2));
		pg.addTransition(new PGTransition(nc1_nc2, "true", "atomic{b2:=1;x:=1}", nc1_wt2));
		pg.addTransition(new PGTransition(wt1_cr2, "true", "b2:=0", wt1_nc2));
		pg.addTransition(new PGTransition(nc1_wt2, "x==2 || b1==0", "", nc1_cr2));
		pg.addTransition(new PGTransition(wt1_nc2, "x==1 || b2==0", "", cr1_nc2));
		pg.addTransition(new PGTransition(wt1_wt2, "x==1 || b2==0", "", cr1_wt2));
		pg.addTransition(new PGTransition(wt1_cr2, "x==1 || b2==0", "", cr1_cr2));
		pg.addTransition(new PGTransition(cr1_wt2, "x==2 || b1==0", "", cr1_cr2));

		return pg;
	}

	TransitionSystem expectedTS() throws FVMException {

		TransitionSystem ts = Ex3FacadeImpl.createTransitionSystem();

		ts.setName("Peterson");

		State n1_n2_2 = new State("[location=noncrit1,noncrit2, eval={x=2, b2=0, b1=0}]");
		State w1_w2_2 = new State("[location=wait1,wait2, eval={x=2, b2=1, b1=1}]");
		State w1_c2_2 = new State("[location=wait1,crit2, eval={x=2, b2=1, b1=1}]");
		State n1_n2_1 = new State("[location=noncrit1,noncrit2, eval={x=1, b2=0, b1=0}]");
		State w1_w2_1 = new State("[location=wait1,wait2, eval={x=1, b2=1, b1=1}]");
		State c1_w2_1 = new State("[location=crit1,wait2, eval={x=1, b2=1, b1=1}]");
		State n1_c2_1 = new State("[location=noncrit1,crit2, eval={x=1, b2=1, b1=0}]");
		State n1_w2_1 = new State("[location=noncrit1,wait2, eval={x=1, b2=1, b1=0}]");
		State w1_n2_2 = new State("[location=wait1,noncrit2, eval={x=2, b2=0, b1=1}]");
		State c1_n2_2 = new State("[location=crit1,noncrit2, eval={x=2, b2=0, b1=1}]");

		ts.addState(n1_n2_2);
		ts.addState(w1_w2_2);
		ts.addState(w1_c2_2);
		ts.addState(n1_n2_1);
		ts.addState(w1_w2_1);
		ts.addState(c1_w2_1);
		ts.addState(n1_c2_1);
		ts.addState(n1_w2_1);
		ts.addState(w1_n2_2);
		ts.addState(c1_n2_2);

		ts.addInitialState(n1_n2_2);
		ts.addInitialState(n1_n2_1);

		Action set_b1_false = new Action("b1:=0");
		Action set_b1_false_and_x_2 = new Action("atomic{b1:=1;x:=2}");
		Action nothing = new Action("");
		Action set_b2_false_and_x_1 = new Action("atomic{b2:=1;x:=1}");
		Action set_b2_false = new Action("b2:=0");

		ts.addAction(set_b1_false);
		ts.addAction(set_b1_false_and_x_2);
		ts.addAction(nothing);
		ts.addAction(set_b2_false_and_x_1);
		ts.addAction(set_b2_false);

		ts.addTransition(new Transition(c1_w2_1, set_b1_false, n1_w2_1));
		ts.addTransition(new Transition(n1_c2_1, set_b2_false, n1_n2_1));
		ts.addTransition(new Transition(n1_w2_1, set_b1_false_and_x_2, w1_w2_2));
		ts.addTransition(new Transition(w1_n2_2, nothing, c1_n2_2));
		ts.addTransition(new Transition(w1_n2_2, set_b2_false_and_x_1, w1_w2_1));
		ts.addTransition(new Transition(n1_w2_1, nothing, n1_c2_1));
		ts.addTransition(new Transition(c1_n2_2, set_b2_false_and_x_1, c1_w2_1));
		ts.addTransition(new Transition(w1_w2_1, nothing, c1_w2_1));
		ts.addTransition(new Transition(w1_c2_2, set_b2_false, w1_n2_2));
		ts.addTransition(new Transition(n1_n2_1, set_b1_false_and_x_2, w1_n2_2));
		ts.addTransition(new Transition(n1_n2_1, set_b2_false_and_x_1, n1_w2_1));
		ts.addTransition(new Transition(c1_n2_2, set_b1_false, n1_n2_2));
		ts.addTransition(new Transition(n1_c2_1, set_b1_false_and_x_2, w1_c2_2));
		ts.addTransition(new Transition(w1_w2_2, nothing, w1_c2_2));
		ts.addTransition(new Transition(n1_n2_2, set_b1_false_and_x_2, w1_n2_2));
		ts.addTransition(new Transition(n1_n2_2, set_b2_false_and_x_1, n1_w2_1));

		ts.addAtomicProposition("b1 = 0");
		ts.addAtomicProposition("b2 = 0");
		ts.addAtomicProposition("b2 = 1");
		ts.addAtomicProposition("b1 = 1");
		ts.addAtomicProposition("x = 1");
		ts.addAtomicProposition("x = 2");

		ts.addLabel(n1_n2_2, "b1 = 0");
		ts.addLabel(n1_n2_2, "b2 = 0");
		ts.addLabel(n1_n2_2, "x = 2");
		ts.addLabel(w1_w2_2, "b2 = 1");
		ts.addLabel(w1_w2_2, "b1 = 1");
		ts.addLabel(w1_w2_2, "x = 2");
		ts.addLabel(w1_c2_2, "b2 = 1");
		ts.addLabel(w1_c2_2, "b1 = 1");
		ts.addLabel(w1_c2_2, "x = 2");
		ts.addLabel(n1_n2_1, "b1 = 0");
		ts.addLabel(n1_n2_1, "b2 = 0");
		ts.addLabel(n1_n2_1, "x = 1");
		ts.addLabel(c1_w2_1, "b2 = 1");
		ts.addLabel(c1_w2_1, "b1 = 1");
		ts.addLabel(c1_w2_1, "x = 1");
		ts.addLabel(w1_w2_1, "b2 = 1");
		ts.addLabel(w1_w2_1, "b1 = 1");
		ts.addLabel(w1_w2_1, "x = 1");
		ts.addLabel(n1_c2_1, "b1 = 0");
		ts.addLabel(n1_c2_1, "b2 = 1");
		ts.addLabel(n1_c2_1, "x = 1");
		ts.addLabel(n1_w2_1, "b1 = 0");
		ts.addLabel(n1_w2_1, "b2 = 1");
		ts.addLabel(n1_w2_1, "x = 1");
		ts.addLabel(w1_n2_2, "b2 = 0");
		ts.addLabel(w1_n2_2, "b1 = 1");
		ts.addLabel(w1_n2_2, "x = 2");
		ts.addLabel(c1_n2_2, "b2 = 0");
		ts.addLabel(c1_n2_2, "b1 = 1");
		ts.addLabel(c1_n2_2, "x = 2");

		return ts;

	}

}
