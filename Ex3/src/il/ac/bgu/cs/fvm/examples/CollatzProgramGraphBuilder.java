package il.ac.bgu.cs.fvm.examples;

import static java.util.Arrays.asList;

import il.ac.bgu.cs.fvm.Ex3FacadeImpl;
import il.ac.bgu.cs.fvm.labels.Location;
import il.ac.bgu.cs.fvm.programgraph.PGTransition;
import il.ac.bgu.cs.fvm.programgraph.ProgramGraph;

public class CollatzProgramGraphBuilder {
	static Ex3FacadeImpl Ex3FacadeImpl = new Ex3FacadeImpl();
	
	public static ProgramGraph build() {
		ProgramGraph pg = Ex3FacadeImpl.createProgramGraph();

		Location running = new Location("running");
		Location finished = new Location("finished");

		pg.addLocation(running);
		pg.addLocation(finished);

		pg.addInitialLocation(running);

		pg.addTransition(new PGTransition(running, "x % 2 == 1 && x != 1", "x:= (3 * x) + 1", running));
		pg.addTransition(new PGTransition(running, "x % 2 == 0", "x:= x / 2", running));
		pg.addTransition(new PGTransition(running, "x == 1", "", finished));

		pg.addInitalization(asList("x:=6"));

		return pg;
	}

}
