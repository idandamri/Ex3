package il.ac.bgu.cs.fvm;

import java.util.*;
import java.util.Map.Entry;

import il.ac.bgu.cs.fvm.exceptions.DeletionOfAttachedActionException;
import il.ac.bgu.cs.fvm.exceptions.DeletionOfAttachedAtomicPropositionException;
import il.ac.bgu.cs.fvm.exceptions.DeletionOfAttachedStateException;
import il.ac.bgu.cs.fvm.exceptions.FVMException;
import il.ac.bgu.cs.fvm.exceptions.InvalidInitialStateException;
import il.ac.bgu.cs.fvm.exceptions.InvalidLablingPairException;
import il.ac.bgu.cs.fvm.exceptions.InvalidTransitionException;
import il.ac.bgu.cs.fvm.exceptions.TransitionSystemPart;
import il.ac.bgu.cs.fvm.labels.Action;
import il.ac.bgu.cs.fvm.labels.State;
import il.ac.bgu.cs.fvm.transitionsystem.Transition;
import il.ac.bgu.cs.fvm.transitionsystem.TransitionSystem;


public class TransitionSystemImp implements TransitionSystem {

	private String name;
	private Set<Action> actions;
	private Set<String> ap;
	private Set<State> states;
	private Set<State> initialStates;
	private Set<Transition> transitions;
	//private State state;

	Map<State, Set<String>> lables;

	public TransitionSystemImp()
	{
		this.initialStates = new HashSet<State>();
		this.name="DEFAULT";
		actions= new HashSet<Action>();
		ap = new HashSet<String>();
		states = new HashSet<State>();
		transitions = new HashSet<Transition>();
		lables = new HashMap<State, Set<String>>();

	}
	public TransitionSystemImp(String name){
		this();
		this.name=name;

	}


	/**
	 * Get the name of the transitions system.
	 * 
	 * @return The name of the transition system.
	 */
	public String getName(){

		return name;
	}
	/**
	 * Set the name of the transition system.
	 * 
	 * @param name
	 *            A new for the transition system.
	 */
	public void setName(String name){
		this.name=name;
	}

	/**
	 * Add an action.
	 * 
	 * @param action
	 *            A name for the new action.
	 */
	public void addAction(Action action){
		actions.add(action);

	}

	/**
	 * Add an atomic proposition. Does nothing if the proposition already
	 * exists.
	 * 
	 * @param p
	 *            The name of the new atomic proposition.
	 */
	public void addAtomicProposition(String p){
		ap.add(p);
	}

	/**
	 * Add an initial state.
	 * 
	 * @param state
	 *            A state to add to the set of initial states.
	 * @throws FVMException
	 *             If the state is not in the set of states.
	 */
	public void addInitialState(State state) throws FVMException{
		if(!states.contains(state))
			throw new InvalidInitialStateException(state);
		else
			initialStates.add(state);

	}

	/**
	 * Label a state by an atomic proposition. Throws an exception if the label
	 * is not an atomic proposition. Does nothing if the sate is already labeled
	 * by the given proposition.
	 * 
	 * @param s
	 *            A state
	 * @param l
	 *            An atomic proposition.
	 * @throws FVMException
	 *             When the label is not an atomic proposition.
	 */
	public void addLabel(State s, String l) throws FVMException{
		if(!ap.contains(l))
			throw new InvalidLablingPairException(s,"the label is not an atomic proposition");

		if(lables.get(s)==null)
			lables.put(s, new HashSet<String>());

		lables.get(s).add(l);



	}

	/**
	 * Ass a state.
	 * 
	 * @param state
	 *            A name for the new state.
	 * 
	 */
	public void addState(State state) {
		states.add(state);
	}

	/**
	 * Add a transition.
	 * 
	 * @param t
	 *            The transition to add.
	 * @throws FVMException
	 *             If the states and the actions do not exist.
	 */
	public void addTransition(Transition t) throws InvalidTransitionException {

		if(!states.contains(t.getFrom()) || !states.contains(t.getTo()) || !actions.contains(t.getAction()))
			throw new InvalidTransitionException(t);
		transitions.add(t);

	}

	/**
	 * Get the actions.
	 * 
	 * @return A copy of the set of actions.
	 */

	public Set<Action> getActions() {

		return new HashSet<Action>(actions);
	}

	/**
	 * Get the the atomic propositions.
	 * 
	 * @return The set of atomic propositions.
	 */
	public Set<String> getAtomicPropositions(){
		return new HashSet<String>(ap);
	}

	/**
	 * Get the initial states.
	 * 
	 * @return The set of initial states.
	 */
	public Set<State> getInitialStates(){
		return new HashSet<State>(initialStates);

	}

	/**
	 * Get the labeling function.
	 * 
	 * @return The set of maps representing the labeling function.
	 */
	public Map<State, Set<String>> getLabelingFunction(){


		return lables;

	}

	/**
	 * Get the states.
	 * 
	 * @return The set of states.
	 */
	public Set<State> getStates(){

		return new HashSet<State>(states);
	}

	/**
	 * Get the transitions.
	 * 
	 * @return The set of the transitions.
	 */
	public Set<Transition> getTransitions(){

		return new HashSet<Transition>(transitions);
	}

	/**
	 * Remove an action.
	 * 
	 * @param action
	 *            The name of the action to remove.
	 * @throws FVMException
	 *             If the action in use by a transition.
	 */
	public void removeAction(Action action) throws FVMException{
		String acName=action.getLabel();
		Iterator<Transition> itr = transitions.iterator();
		while(itr.hasNext()){
			if(itr.next().getAction().getLabel().equals(acName))
				throw new DeletionOfAttachedActionException(action,TransitionSystemPart.TRANSITIONS);
		}
		Iterator<Action> itrr = actions.iterator();
		while(itrr.hasNext()){
			Action del=itrr.next();
			if(del.getLabel().equals(acName)){
				if(!actions.remove(del))
					System.out.println("problem to delete : "+del.getLabel());
				return;

			}

		}

	}

	/**
	 * Remove an atomic proposition.
	 * 
	 * @param p
	 *            The name of the proposition to remove.
	 * @throws FVMException
	 *             If the proposition is used as label of a state.
	 */
	public void removeAtomicProposition(String p) throws FVMException{
		//the checking
		Iterator<Entry<State, Set<String>>> it = lables.entrySet().iterator();
		while(it.hasNext()){
			if(it.next().getValue().contains(p))
				throw new DeletionOfAttachedAtomicPropositionException(p,TransitionSystemPart.LABELING_FUNCTION);//(" the proposition is used as label of a state.");

		}
		ap.remove(p);
	}

	/**
	 * Remove a state from the set of initial states.
	 * 
	 * @param state
	 *            The name of the state to remove.
	 */
	public void removeInitialState(State state){
		initialStates.remove(state);
		//return null;
	}

	/**
	 * atomic proposition, the method returns without changing anything.
	 * 
	 * @param s
	 *            A state.
	 * @param l
	 *            An atomic proposition
	 */
	public void removeLabel(State s, String l) {
		//need to check if l in atomic proposition?
		if(lables.get(s)!=null)
			lables.get(s).remove(l);
	}

	/**
	 * Remove a state.
	 * 
	 * @param state
	 *            The name of the state to remove.
	 * @throws FVMException
	 *             If the state is in use by a transition, is labeled, or is in
	 *             the set of initial states.
	 */
	public void removeState(State state) throws FVMException {
		//check use by transition
		Iterator<Transition> itr = transitions.iterator();
		
		while(itr.hasNext()){
			Transition cur = itr.next();
			if(cur.getFrom().equals(state) || cur.getTo().equals(state))
				throw new DeletionOfAttachedStateException(state,TransitionSystemPart.TRANSITIONS);//("the state is in use by a transition");
		}

		if(lables.get(state)!=null &&  !lables.get(state).isEmpty())
			throw new DeletionOfAttachedStateException(state, TransitionSystemPart.LABELING_FUNCTION);

		if(initialStates.contains(state))
			throw new DeletionOfAttachedStateException(state,TransitionSystemPart.INITIAL_STATES);

		states.remove(state);

	}

	/**
	 * Remove a transition.
	 * 
	 * @param t
	 *            The transition to remove.
	 */
	public void removeTransition(Transition t) {
		transitions.remove(t);
	}



}
