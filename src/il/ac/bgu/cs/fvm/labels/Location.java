package il.ac.bgu.cs.fvm.labels;

public class Location extends LabeledElement {

	public Location(String aLabel) {
		super(aLabel);
	}

	@Override
	public String toString() {
		return "[Location: " + getLabel() + "]";
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
		return ((obj instanceof Location) && ((Location) obj).getLabel().equals(getLabel()));
	}

}
