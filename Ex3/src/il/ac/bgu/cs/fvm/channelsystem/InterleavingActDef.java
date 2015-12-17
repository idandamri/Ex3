package il.ac.bgu.cs.fvm.channelsystem;

import java.util.Set;

import il.ac.bgu.cs.fvm.programgraph.ActionDef;

/**
 * An interface for specification of joined actions that interleaved program
 * graphs take together (handshaking).
 */
public interface InterleavingActDef extends ActionDef {

	/**
	 * Test if a given action is a side of an interleaved one. For example, the
	 * action _C!0 cannot be performed alone as it requires, e.g., to be taken
	 * simultaneously, so {@code isOneSidedAction(0, "_C!0"} is {@code true}. 
	 * 
	 * @param side
	 *            The side of the action.
	 * @param action
	 *            The name of the action.
	 * @return True if the given action cannot be taked alone because it has to
	 *         wait for its counterpart.
	 */
	public boolean isOneSidedAction(String action);

	/**
	 * An extension of {@code isOneSidedAction} to a set of definition objects.  
	 */
	static boolean isOneSidedAction(Set<InterleavingActDef> ads, String act) {
		return ads.stream().anyMatch(ad -> ad.isOneSidedAction(act));
	}

	/**
	 * An extension of {@code isMatchingAction} to a set of definition objects.  
	 */
	static boolean isMatchingAction(Set<InterleavingActDef> ads, String act) {
		return ads.stream().anyMatch(ad -> ad.isMatchingAction(act));
	}
}
