package il.ac.bgu.cs.fvm.programgraph;

import java.util.Map;
import java.util.Set;

/**
 * An object the specifies the format and the effect of an action in a program
 * graph.
 */
public interface ActionDef {

	/**
	 * Test if the candidate string matches action defined by this object.
	 * 
	 * @param candidate
	 *            A string that we want to check if it matches the action
	 *            specified by this object.
	 * @return True if the string matches the action.
	 */
	public boolean isMatchingAction(String candidate);

	/**
	 * apply the effect of the action to the variables.
	 * 
	 * @param eval
	 *            The evaluation of the variables before the action.
	 * @param action
	 *            The action string.
	 * @return An evaluation of the variables after the action.
	 */
	public Map<String, Object> effect(Map<String, Object> eval, String action);

	/*
	 * A generalization of the above method to sets of definitions.
	 */
	static Map<String, Object> effect(Set<ActionDef> ads, Map<String, Object> eval, String action) {
		for (ActionDef ad : ads) {
			if (ad.isMatchingAction(action))
				return ad.effect(eval, action);
		}
		return eval;
	}

	/*
	 * A generalization of the above method to sets of definitions.
	 */
	static public boolean isMatchingAction(Set<ActionDef> ads, String candidate) {
		return ads.stream().anyMatch(ad -> ad.isMatchingAction(candidate));
	}

}
