package il.ac.bgu.cs.fvm.programgraph;

import il.ac.bgu.cs.fvm.labels.Location;

// ------------------------------------------------------------------------------------
// Program Graph Transitions
// ------------------------------------------------------------------------------------
public class PGTransition {
	Location from;
	String condition;
	String action;
	Location to;

	/**
	 * Default constructor.
	 */
	public PGTransition() {
	}

	/**
	 * A constructor that takes all the fields.
	 * 
	 * @param from
	 *            The source of the transition. Should be a name of a location.
	 * @param condition
	 *            The condition on the transition.
	 * 
	 * @param action
	 *            The action on the transition.
	 * @param to
	 *            The destination of the transition. Should be a name of a
	 *            location.
	 */
	public PGTransition(Location from, String condition, String action, Location to) {
		super();
		this.from = from;
		this.condition = condition;
		this.action = action;
		this.to = to;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PGTransition other = (PGTransition) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (condition == null) {
			if (other.condition != null)
				return false;
		} else if (!condition.equals(other.condition))
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}

	/**
	 * Get the action that triggers the transition.
	 * 
	 * @return The name of the action on the transition.
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Get the condition string.
	 * 
	 * @return A string representing the condition for this transition.
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * Get the source of the transition.
	 * 
	 * @return The name of the state from which the transition starts.
	 */
	public Location getFrom() {
		return from;
	}

	/**
	 * Get the destination of the transition.
	 * 
	 * @return The name of the state to which the transition goes.
	 */
	public Location getTo() {
		return to;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((condition == null) ? 0 : condition.hashCode());
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}

	/**
	 * Get the action that triggers the transition.
	 * 
	 * @param action
	 *            The name of the action on the transition.
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * Get the condition string.
	 * 
	 * @param condition
	 *            A string representing the condition for this transition.
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * Set the source of the transition.
	 * 
	 * @param from
	 *            The name of the state from which the transition starts.
	 */
	public void setFrom(Location from) {
		this.from = from;
	}

	/**
	 * Get the destination of the transition.
	 * 
	 * @param to
	 *            The name of the state to which the transition goes.
	 */
	public void setTo(Location to) {
		this.to = to;
	}

	@Override
	public String toString() {
		//return action;
		// TODO: Define a superclass for state representation that contains a
		// getActionName method instead of using toString.
		
		return "PGTransition [from=" + from + ", condition=" + condition + ", action =" + action + ", to=" + to + "]";
	}

}