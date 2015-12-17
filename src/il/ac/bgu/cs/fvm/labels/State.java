package il.ac.bgu.cs.fvm.labels;

/**
 * A state of an automaton.
 * 
 */
public final class State extends LabeledElement {

	public State(String aLabel) {
		super(aLabel);
	}

	@Override
	public String toString() {
		return "[State " + getLabel() + "]";
	}

	@Override
	public int hashCode() {
		return getLabel().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		return ((obj instanceof State) && ((State) obj).getLabel().equals(getLabel()));
	}

}
