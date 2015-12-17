package il.ac.bgu.cs.fvm.tests;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import il.ac.bgu.cs.fvm.Ex3FacadeImpl;
import il.ac.bgu.cs.fvm.examples.BookingSystemBuilder;
import il.ac.bgu.cs.fvm.examples.MutualExclusionUsingArbiter;
import il.ac.bgu.cs.fvm.labels.Action;
import il.ac.bgu.cs.fvm.labels.State;
import il.ac.bgu.cs.fvm.transitionsystem.Transition;
import il.ac.bgu.cs.fvm.transitionsystem.TransitionSystem;

public class TransitionSystemsProductTests {
	Ex3FacadeImpl Ex3FacadeImpl = new Ex3FacadeImpl();
	
	@Test
	// See page 52, Example 2.9 in the book
	public void booking() {
		TransitionSystem bcr = BookingSystemBuilder.buildBCR(); 
		TransitionSystem bp = BookingSystemBuilder.buildBP();
		TransitionSystem printer = BookingSystemBuilder.buildPrinter();

		Set<Action> hs1 = new HashSet<Action>();
		hs1.add(new Action("store"));
		TransitionSystem ts1 = Ex3FacadeImpl.interleave(bcr, bp, hs1);

		Set<Action> hs2 = new HashSet<Action>();
		hs2.add(new Action("prt_cmd"));
		TransitionSystem ts = Ex3FacadeImpl.interleave(ts1, printer, hs2);

		TransitionSystem expected = expetedBooking();

		assertEquals(expected.getInitialStates(), ts.getInitialStates());
		assertEquals(expected.getStates(), ts.getStates());
		assertEquals(expected.getTransitions(), ts.getTransitions());
		assertEquals(expected.getActions(), ts.getActions());
		assertEquals(expected.getAtomicPropositions(), ts.getAtomicPropositions());
		assertEquals(expected.getLabelingFunction(), ts.getLabelingFunction());

	}

	// See page 52 in the book
	TransitionSystem expetedBooking() {
		TransitionSystem ts = Ex3FacadeImpl.createTransitionSystem();

		State s001 = new State("0,0,1");
		State s010 = new State("0,1,0");
		State s000 = new State("0,0,0");
		State s011 = new State("0,1,1");
		State s111 = new State("1,1,1");
		State s101 = new State("1,0,1");
		State s110 = new State("1,1,0");
		State s100 = new State("1,0,0");

		ts.addState(s001);
		ts.addState(s010);
		ts.addState(s000);
		ts.addState(s011);
		ts.addState(s111);
		ts.addState(s101);
		ts.addState(s110);
		ts.addState(s100);

		ts.addInitialState(s000);

		Action print = new Action("print");
		Action scan = new Action("scan");
		Action stor = new Action("store");
		Action prt_cmd = new Action("prt_cmd");

		ts.addAction(print);
		ts.addAction(scan);
		ts.addAction(stor);
		ts.addAction(prt_cmd);

		ts.addTransition(new Transition(s100, stor, s010));
		ts.addTransition(new Transition(s101, stor, s011));
		ts.addTransition(new Transition(s010, prt_cmd, s001));
		ts.addTransition(new Transition(s110, prt_cmd, s101));
		ts.addTransition(new Transition(s000, scan, s100));
		ts.addTransition(new Transition(s001, scan, s101));
		ts.addTransition(new Transition(s010, scan, s110));
		ts.addTransition(new Transition(s011, scan, s111));
		ts.addTransition(new Transition(s001, print, s000));
		ts.addTransition(new Transition(s011, print, s010));
		ts.addTransition(new Transition(s101, print, s100));
		ts.addTransition(new Transition(s111, print, s110));

		return ts;
	}

	@Test
	// See page 37, Figure 2.4 in the book
	public void trafficLight() {
		TransitionSystem ts1 = Ex3FacadeImpl.createTransitionSystem();

		State red = new State("Red");
		State green = new State("Green");

		ts1.addState(red);
		ts1.addState(green);

		ts1.addInitialState(red);

		Action go1 = new Action("go1");
		ts1.addAction(go1);

		ts1.addAtomicProposition("tl1-is-red");
		ts1.addLabel(red, "tl1-is-red");

		ts1.addTransition(new Transition(red, go1, green));
		ts1.addTransition(new Transition(green, go1, red));

		TransitionSystem ts2 = Ex3FacadeImpl.createTransitionSystem();
		ts2.addState(red);
		ts2.addState(green);

		ts2.addInitialState(red);

		Action go2 = new Action("go2");
		ts2.addAction(go2);

		ts2.addAtomicProposition("tl2-is-red");
		ts2.addLabel(red, "tl2-is-red");

		ts2.addTransition(new Transition(red, go2, green));
		ts2.addTransition(new Transition(green, go2, red));

		TransitionSystem ts = Ex3FacadeImpl.interleave(ts1, ts2);

		TransitionSystem expected = expected();

		assertEquals(expected.getActions(), ts.getActions());
		assertEquals(expected.getAtomicPropositions(), ts.getAtomicPropositions());
		assertEquals(expected.getInitialStates(), ts.getInitialStates());
		assertEquals(expected.getLabelingFunction(), ts.getLabelingFunction());
		assertEquals(expected.getStates(), ts.getStates());
		assertEquals(expected.getTransitions(), ts.getTransitions());
	}

	TransitionSystem expected() {
		TransitionSystem ts = Ex3FacadeImpl.createTransitionSystem();

		State gg = new State("Green,Green");
		State rr = new State("Red,Red");
		State gr = new State("Green,Red");
		State rg = new State("Red,Green");

		ts.addState(gg);
		ts.addState(rr);
		ts.addState(gr);
		ts.addState(rg);

		ts.addInitialState(rr);

		Action go1 = new Action("go1");
		Action go2 = new Action("go2");

		ts.addAction(go1);
		ts.addAction(go2);

		ts.addTransition(new Transition(rr, go1, gr));
		ts.addTransition(new Transition(gr, go2, gg));
		ts.addTransition(new Transition(rg, go1, gg));
		ts.addTransition(new Transition(rr, go2, rg));
		ts.addTransition(new Transition(gg, go1, rg));
		ts.addTransition(new Transition(rg, go2, rr));
		ts.addTransition(new Transition(gg, go2, gr));
		ts.addTransition(new Transition(gr, go1, rr));

		ts.addAtomicProposition("tl2-is-red");
		ts.addAtomicProposition("tl1-is-red");

		ts.addLabel(rr, "tl2-is-red");
		ts.addLabel(rr, "tl1-is-red");
		ts.addLabel(gr, "tl2-is-red");
		ts.addLabel(rg, "tl1-is-red");

		return ts;
	}

	// See Figure 2.12 on page 51
	@Test
	public void arbiter() throws Exception {
		TransitionSystem p1 = MutualExclusionUsingArbiter.buildP();
		TransitionSystem p2 = MutualExclusionUsingArbiter.buildP();
		TransitionSystem arb = MutualExclusionUsingArbiter.buildArbiter();

		TransitionSystem ts1 = Ex3FacadeImpl.interleave(p1, p2);

		Set<Action> h = new HashSet<Action>();
		h.add(new Action("request"));
		h.add(new Action("release"));

		TransitionSystem ts = Ex3FacadeImpl.interleave(ts1, arb, h);

		TransitionSystem expected = expectedArb();

		assertEquals(expected.getInitialStates(), ts.getInitialStates());
		assertEquals(expected.getStates(), ts.getStates());
		assertEquals(expected.getTransitions(), ts.getTransitions());
		assertEquals(expected.getActions(), ts.getActions());
		assertEquals(expected.getAtomicPropositions(), ts.getAtomicPropositions());
		assertEquals(expected.getLabelingFunction(), ts.getLabelingFunction());

	}

	TransitionSystem expectedArb() {
		TransitionSystem ts = Ex3FacadeImpl.createTransitionSystem();

		State nc_nc_u = new State("noncrit,noncrit,unlock");
		State cr_nc_l = new State("crit,noncrit,lock");
		State nc_cr_l = new State("noncrit,crit,lock");

		ts.addState(nc_nc_u);
		ts.addState(nc_cr_l);
		ts.addState(cr_nc_l);

		ts.addInitialState(nc_nc_u);

		Action req = new Action("request");
		Action rel = new Action("release");

		ts.addAction(req);
		ts.addAction(rel);

		ts.addTransition(new Transition(cr_nc_l, rel, nc_nc_u));
		ts.addTransition(new Transition(nc_cr_l, rel, nc_nc_u));
		ts.addTransition(new Transition(nc_nc_u, req, nc_cr_l));
		ts.addTransition(new Transition(nc_nc_u, req, cr_nc_l));

		return ts;
	}

}
