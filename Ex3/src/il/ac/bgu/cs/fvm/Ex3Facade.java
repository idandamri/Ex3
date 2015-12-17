package il.ac.bgu.cs.fvm;

import java.util.List;
import java.util.Set;

import il.ac.bgu.cs.fvm.channelsystem.ChannelSystem;
import il.ac.bgu.cs.fvm.circuits.Circuit;
import il.ac.bgu.cs.fvm.labels.Action;
import il.ac.bgu.cs.fvm.labels.LabeledElement;
import il.ac.bgu.cs.fvm.labels.State;
import il.ac.bgu.cs.fvm.programgraph.ActionDef;
import il.ac.bgu.cs.fvm.programgraph.ConditionDef;
import il.ac.bgu.cs.fvm.programgraph.ProgramGraph;
import il.ac.bgu.cs.fvm.transitionsystem.TransitionSystem;

/**
 * Interface for the entry point to the solution for exercise 1.
 * 
 * More about facade: {@linkplain http://www.vincehuston.org/dp/facade.html}.
 */
public interface Ex3Facade {
	TransitionSystem createTransitionSystem();

	boolean isActionDeterministic(TransitionSystem ts);

	boolean isAPDeterministic(TransitionSystem ts);

	boolean isExecution(TransitionSystem ts, List<LabeledElement> e);

	boolean isExecutionFragment(TransitionSystem ts, List<LabeledElement> e);

	boolean isInitialExecutionFragment(TransitionSystem ts, List<LabeledElement> e);

	boolean isMaximalExecutionFragment(TransitionSystem ts, List<LabeledElement> e);

	boolean isStateTerminal(TransitionSystem ts, State s);

	Set<State> post(TransitionSystem ts, Set<State> c);

	Set<State> post(TransitionSystem ts, Set<State> c, Action a);

	Set<State> post(TransitionSystem ts, State s);

	Set<State> post(TransitionSystem ts, State s, Action a);

	Set<State> pre(TransitionSystem ts, Set<State> c);

	Set<State> pre(TransitionSystem ts, Set<State> c, Action a);

	Set<State> pre(TransitionSystem ts, State s);

	Set<State> pre(TransitionSystem ts, State s, Action a);

	Set<State> reach(TransitionSystem ts);

	
	TransitionSystem interleave(TransitionSystem ts1, TransitionSystem ts2);

	TransitionSystem interleave(TransitionSystem ts1, TransitionSystem ts2, Set<Action> handShakingActions);

	
	ProgramGraph createProgramGraph();

	ProgramGraph interleave(ProgramGraph pg1, ProgramGraph pg2);

	
	TransitionSystem transitionSystemFromCircuit(Circuit c);

	TransitionSystem transitionSystemFromProgramGraph(ProgramGraph pg, Set<ActionDef> actionDefs, Set<ConditionDef> conditionDefs);



	TransitionSystem transitionSystemFromChannelSystem(ChannelSystem cs);

	
	ProgramGraph programGraphFromNanoPromela(String filename) throws Exception;

	ProgramGraph programGraphFromNanoPromelaString(String nanopromela) throws Exception;

}
