package il.ac.bgu.cs.fvm.circuits;

import java.util.List;

/**
 * The circuit on page 27, Figure 2.2
 */
public interface Circuit {

	int getNumberOfInputPorts();

	int getNumberOfRegiters();

	int getNumberOfOutputPorts();

	public List<Boolean> updateRegisters(List<Boolean> registers, List<Boolean> inputs);

	public List<Boolean> computeOutputs(List<Boolean> registers, List<Boolean> inputs);

}