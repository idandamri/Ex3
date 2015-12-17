package il.ac.bgu.cs.fvm.tests;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import il.ac.bgu.cs.fvm.Ex3FacadeImpl;
import il.ac.bgu.cs.fvm.labels.Action;
import il.ac.bgu.cs.fvm.labels.State;
import il.ac.bgu.cs.fvm.transitionsystem.Transition;
import il.ac.bgu.cs.fvm.transitionsystem.TransitionSystem;

/**
 * Helps building transition systems. Contains some sample transition systems
 * for us to work on.
 * 
 * @author michael
 */
public class TSTestUtils {

	private final Map<String, State>	states	= new HashMap<>();
	private final Map<String, Action>	actions	= new HashMap<>();

	public State state(String name) {
		return states.computeIfAbsent(name, n -> new State(n));
	}

	public Action action(String name) {
		return actions.computeIfAbsent(name, n -> new Action(n));
	}

	public Transition transition(State f, Action a, State t) {
		return new Transition(f, a, t);
	}

	public Transition transition(String f, String a, String t) {
		return new Transition(state(f), action(a), state(t));
	}

	/**
	 * {@code
	 *    +----------------- delta ------------------+
	 *    v                                          | 
	 *  ((a))--alpha--> (b) --beta--> (c) --gamma-> (d)
	 *   
	 * }
	 * 
	 * 
	 * @return a simple transition system.
	 */
	public TransitionSystem simpleTransitionSystem() {
		Ex3FacadeImpl Ex3FacadeImpl = new Ex3FacadeImpl();
		TransitionSystem ts = Ex3FacadeImpl.createTransitionSystem();

		ts.setName("Simple Transition System");

		ts.addState(state("a"));
		ts.addState(state("b"));
		ts.addState(state("c"));
		ts.addState(state("d"));

		ts.addAction(action("alpha"));
		ts.addAction(action("beta"));
		ts.addAction(action("gamma"));
		ts.addAction(action("delta"));

		ts.addAtomicProposition("System stable");
		ts.addAtomicProposition("System unstable");
		ts.addAtomicProposition("System stable-ish");

		ts.addInitialState(state("a"));

		ts.addTransition(transition("a", "alpha", "b"));
		ts.addTransition(transition("b", "beta", "c"));
		ts.addTransition(transition("c", "gamma", "d"));
		ts.addTransition(transition("d", "delta", "a"));

		ts.addLabel(state("a"), "System stable");
		ts.addLabel(state("b"), "System unstable");
		ts.addLabel(state("c"), "System stable-ish");
		ts.addLabel(state("d"), "System stable-ish");

		return ts;
	}

	public static String prettyPrintXml(String xml) {
		try {
			final InputSource src = new InputSource(new StringReader(xml));
			final Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src).getDocumentElement();
			final Boolean keepDeclaration = xml.startsWith("<?xml");

			final DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
			final DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
			final LSSerializer writer = impl.createLSSerializer();

			writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
			writer.getDomConfig().setParameter("xml-declaration", keepDeclaration);

			return writer.writeToString(document);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
