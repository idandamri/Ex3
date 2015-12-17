package il.ac.bgu.cs.fvm.channelsystem;

import java.util.List;

import il.ac.bgu.cs.fvm.programgraph.ProgramGraph;

public class ChannelSystem {
	List<ProgramGraph> programGraphs;

	public ChannelSystem(List<ProgramGraph> programGraphs) {
		this.programGraphs = programGraphs;
	}

	/**
	 * @return the programGraphs
	 */
	public List<ProgramGraph> getProgramGraphs() {
		return programGraphs;
	}

	/**
	 * @param programGraphs
	 *            the programGraphs to set
	 */
	public void setProgramGraphs(List<ProgramGraph> programGraphs) {
		this.programGraphs = programGraphs;
	}

}
