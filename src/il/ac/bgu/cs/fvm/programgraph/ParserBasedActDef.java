package il.ac.bgu.cs.fvm.programgraph;

import java.util.Map;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import il.ac.bgu.cs.fvm.nanopromela.Evaluator;
import il.ac.bgu.cs.fvm.nanopromela.NanoPromelaLexer;
import il.ac.bgu.cs.fvm.nanopromela.NanoPromelaParser;
import il.ac.bgu.cs.fvm.nanopromela.NanoPromelaParser.SpecContext;
import il.ac.bgu.cs.fvm.nanopromela.NanoPromelaParser.StmtContext;

/**
 * An object that identifies and interprets the actions defined in the grammar
 * nanopromela/NanoPromela.g4
 */
public class ParserBasedActDef implements ActionDef {

	/*
	 * (non-Javadoc)
	 * 
	 * @see il.ac.bgu.cs.fvm.programgraph.ActionDef#effect(java.util.Map,
	 * java.lang.String)
	 */
	@Override
	public Map<String, Object> effect(Map<String, Object> eval, String action) {
		if (action.equals(""))
			return eval;

		return new Evaluator(eval).evaluate(parseAction(action));
	}

	/**
	 * Parse the action.
	 * 
	 * @param action
	 *            A string that represents an action
	 * @return The root of the parse tree or null, if the string cannot be
	 *         parsed.
	 */
	private StmtContext parseAction(String action) {
		NanoPromelaLexer lexer = new NanoPromelaLexer(new ANTLRInputStream(action));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		NanoPromelaParser parser = new NanoPromelaParser(tokens);

		lexer.removeErrorListeners();
		lexer.addErrorListener(new ThrowingErrorListener());

		parser.removeErrorListeners();
		parser.addErrorListener(new ThrowingErrorListener());

		try {
			SpecContext spec = parser.spec();
			StmtContext p = spec.stmt();
			return p;
		} catch (Exception ex) {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see il.ac.bgu.cs.fvm.programgraph.ActionDef#isMatchingAction(java.lang.String)
	 */
	@Override
	public boolean isMatchingAction(String action) {
		return action.equals("") || parseAction(action) != null;
	}

}
