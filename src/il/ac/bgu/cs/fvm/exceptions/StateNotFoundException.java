package il.ac.bgu.cs.fvm.exceptions;

import il.ac.bgu.cs.fvm.labels.State;

@SuppressWarnings("serial")
public class StateNotFoundException extends FVMException {
	State state;

	public StateNotFoundException(State s) {
		super("An asttempt to compute post for an invalid state (" + s + ")");
		this.state = s;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StateNotFoundException other = (StateNotFoundException) obj;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}

}
