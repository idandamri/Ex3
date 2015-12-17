package il.ac.bgu.cs.fvm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import il.ac.bgu.cs.fvm.labels.Location;
import il.ac.bgu.cs.fvm.programgraph.PGTransition;
import il.ac.bgu.cs.fvm.programgraph.ProgramGraph;

public class ProgramGraphImp implements ProgramGraph {
	private HashSet<PGTransition> transitions;
	private HashSet<Location> locations;
	private HashSet<Location> initiailLocations;
	private Set<List<String>> initilization;
	private String name;



	public ProgramGraphImp() {
		transitions = new HashSet<PGTransition>();
		locations = new HashSet<Location> ();
		initiailLocations = new HashSet<Location>();
		initilization = new HashSet<List<String>>();
		this.name= "DEFAULT";
	}

	@Override
	public void addInitalization(List<String> init) {
		initilization.add(init);
	}

	@Override
	public void addInitialLocation(Location location) {
		initiailLocations.add(location);

	}

	@Override
	public void addLocation(Location l) {
		locations.add(l);
	}

	@Override
	public void addTransition(PGTransition t) {
		transitions.add(t);
	}

	@Override
	public Set<List<String>> getInitalizations() {
		return initilization;
	}

	@Override
	public Set<Location> getInitialLocations() {
		return new HashSet<Location>(initiailLocations);
	}

	@Override
	public Set<Location> getLocations() {
		return new HashSet<Location>(locations);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public HashSet<PGTransition> getTransitions() {
		return new HashSet<PGTransition>(transitions);
	}

	public void removeLocation(String l) {
		locations.remove(new Location(l));
	}

	@Override
	public void removeTransition(PGTransition t) {
		transitions.remove(t);
	}

	@Override
	public void setName(String name) {
		this.name=name;
	}

	@Override
	public void removeLocation(Location l) {
		
		locations.remove(l);
	}

}
