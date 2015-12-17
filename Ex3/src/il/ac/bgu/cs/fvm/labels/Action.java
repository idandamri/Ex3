package il.ac.bgu.cs.fvm.labels;

import il.ac.bgu.cs.fvm.transitionsystem.TransitionSystem;

/**
 * A single action in a {@link TransitionSystem}.
 */
public final class Action extends LabeledElement {

	public Action(String aLabel) {
		super(aLabel);
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
		return ((obj instanceof Action) && ((Action) obj).getLabel().equals(getLabel()));
	}

	@Override
	public String toString() {
		return "[Action: " + getLabel() + "]";
	}

}
