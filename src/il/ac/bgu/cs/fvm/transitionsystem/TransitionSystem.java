package il.ac.bgu.cs.fvm.transitionsystem;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import il.ac.bgu.cs.fvm.exceptions.FVMException;
import il.ac.bgu.cs.fvm.labels.Action;
import il.ac.bgu.cs.fvm.labels.State;

/**
 * Interface of a transition system, as defined in page 20 of the book.
 * 
 * When implementing this interface, you <em>must</em> implement
 * {@code euqals()} and {@code hashCode()}. The equality tests should match any
 * object whose class implements this interface. This is similar to the face
 * that a {@link TreeSet} and a {@link HashSet} can be equal, even though they
 * do not have the same concrete class.
 * 
 */
public interface TransitionSystem {

	/**
	 * Get the name of the transitions system.
	 * 
	 * @return The name of the transition system.
	 */
	String getName();

	/**
	 * Set the name of the transition system.
	 * 
	 * @param name
	 *            A new for the transition system.
	 */
	void setName(String name);

	/**
	 * Add an action.
	 * 
	 * @param action
	 *            A name for the new action.
	 */
	void addAction(Action action);

	/**
	 * Add an atomic proposition. Does nothing if the proposition already
	 * exists.
	 * 
	 * @param p
	 *            The name of the new atomic proposition.
	 */
	void addAtomicProposition(String p);

	/**
	 * Add an initial state.
	 * 
	 * @param state
	 *            A state to add to the set of initial states.
	 * @throws FVMException
	 *             If the state is not in the set of states.
	 */
	void addInitialState(State state) throws FVMException;

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
	void addLabel(State s, String l) throws FVMException;

	/**
	 * Ass a state.
	 * 
	 * @param state
	 *            A name for the new state.
	 * 
	 */
	void addState(State state);

	/**
	 * Add a transition.
	 * 
	 * @param t
	 *            The transition to add.
	 * @throws FVMException
	 *             If the states and the actions do not exist.
	 */
	void addTransition(Transition t) throws FVMException;

	/**
	 * Get the actions.
	 * 
	 * @return A copy of the set of actions.
	 */

	Set<Action> getActions();

	/**
	 * Get the the atomic propositions.
	 * 
	 * @return The set of atomic propositions.
	 */
	Set<String> getAtomicPropositions();

	/**
	 * Get the initial states.
	 * 
	 * @return The set of initial states.
	 */
	Set<State> getInitialStates();

	/**
	 * Get the labeling function.
	 * 
	 * @return The set of maps representing the labeling function.
	 */
	Map<State, Set<String>> getLabelingFunction();

	/**
	 * Get the states.
	 * 
	 * @return The set of states.
	 */
	Set<State> getStates();

	/**
	 * Get the transitions.
	 * 
	 * @return The set of the transitions.
	 */
	Set<Transition> getTransitions();

	/**
	 * Remove an action.
	 * 
	 * @param action
	 *            The name of the action to remove.
	 * @throws FVMException
	 *             If the action in use by a transition.
	 */
	void removeAction(Action action) throws FVMException;

	/**
	 * Remove an atomic proposition.
	 * 
	 * @param p
	 *            The name of the proposition to remove.
	 * @throws FVMException
	 *             If the proposition is used as label of a state.
	 */
	void removeAtomicProposition(String p) throws FVMException;

	/**
	 * Remove a state from the set of initial states.
	 * 
	 * @param state
	 *            The name of the state to remove.
	 */
	void removeInitialState(State state);

	/**
	 * atomic proposition, the method returns without changing anything.
	 * 
	 * @param s
	 *            A state.
	 * @param l
	 *            An atomic proposition
	 */
	void removeLabel(State s, String l);

	/**
	 * Remove a state.
	 * 
	 * @param state
	 *            The name of the state to remove.
	 * @throws FVMException
	 *             If the state is in use by a transition, is labeled, or is in
	 *             the set of initial states.
	 */
	void removeState(State state) throws FVMException;

	/**
	 * Remove a transition.
	 * 
	 * @param t
	 *            The transition to remove.
	 */
	void removeTransition(Transition t);

}