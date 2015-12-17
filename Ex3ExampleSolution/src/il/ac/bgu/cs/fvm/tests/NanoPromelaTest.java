package il.ac.bgu.cs.fvm.tests;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import il.ac.bgu.cs.fvm.Ex3FacadeImpl;
import il.ac.bgu.cs.fvm.examples.VendingmachineInNanopromela;
import il.ac.bgu.cs.fvm.labels.Action;
import il.ac.bgu.cs.fvm.labels.Location;
import il.ac.bgu.cs.fvm.labels.State;
import il.ac.bgu.cs.fvm.programgraph.ActionDef;
import il.ac.bgu.cs.fvm.programgraph.ConditionDef;
import il.ac.bgu.cs.fvm.programgraph.PGTransition;
import il.ac.bgu.cs.fvm.programgraph.ParserBasedActDef;
import il.ac.bgu.cs.fvm.programgraph.ParserBasedCondDef;
import il.ac.bgu.cs.fvm.programgraph.ProgramGraph;
import il.ac.bgu.cs.fvm.transitionsystem.Transition;
import il.ac.bgu.cs.fvm.transitionsystem.TransitionSystem;

public class NanoPromelaTest {

	Ex3FacadeImpl fvmFacadeImpl = new Ex3FacadeImpl();

	@Test
	public void test1() throws Exception {
		String filename = "bin\\il\\ac\\bgu\\cs\\fvm\\nanopromela\\tst1.np";
		ProgramGraph pg = fvmFacadeImpl.programGraphFromNanoPromela(filename);

		ProgramGraph exp = expected1();
////
////		System.out.println("exp "+exp.getTransitions());
////		System.out.println("cur "+pg.getTransitions());
//
//		 HashSet<PGTransition> diff = exp.getTransitions();
//		diff.removeAll(pg.getTransitions());
//		System.out.println("miss :"+diff);
//		
		
		
		assertEquals(pg.getLocations(), exp.getLocations());
		assertEquals(pg.getInitialLocations(), exp.getInitialLocations());
		assertEquals(pg.getTransitions(), exp.getTransitions());
	}

	@Test
	public void test2() throws Exception {
		String filename = "bin\\il\\ac\\bgu\\cs\\fvm\\nanopromela\\tst2.np";
		ProgramGraph pg = fvmFacadeImpl.programGraphFromNanoPromela(filename);

		ProgramGraph exp = expected2();
//
//		System.out.println("exp "+exp.getTransitions());
//		System.out.println("cur "+pg.getTransitions());
//
//		 HashSet<PGTransition> diff = pg.getTransitions();
//		diff.removeAll(exp.getTransitions());
//		System.out.println("miss :"+diff);
//		
		
		assertEquals(pg.getLocations(), exp.getLocations());
		assertEquals(pg.getInitialLocations(), exp.getInitialLocations());
		assertEquals(pg.getTransitions(), exp.getTransitions());
	}

	@Test
	public void test3() throws Exception {
		String filename = "bin\\il\\ac\\bgu\\cs\\fvm\\nanopromela\\tst3.np";
		ProgramGraph pg = fvmFacadeImpl.programGraphFromNanoPromela(filename);

		ProgramGraph exp = expected3();
//
//		
//		System.out.println("exp "+exp.getTransitions());
//		System.out.println("cur "+pg.getTransitions());
//
//		 HashSet<PGTransition> diff = pg.getTransitions();
//		diff.removeAll(exp.getTransitions());
//		System.out.println("miss :"+diff);
//		
		
		
		
		assertEquals(pg.getLocations(), exp.getLocations());
		assertEquals(pg.getInitialLocations(), exp.getInitialLocations());
		assertEquals(pg.getTransitions(), exp.getTransitions());
	
	
	}

	private ProgramGraph expected1() {
		ProgramGraph pg = fvmFacadeImpl.createProgramGraph();
		Location l0 = new Location("[if::a==c->bb:=1::a==b->if::x!=y->do::x<3->x:=x+1odfi;y:=9fi]");
		Location l1 = new Location("[do::x<3->x:=x+1od;y:=9]");
		Location l2 = new Location("[y:=9]");
		Location exit = new Location("[]");

		pg.addLocation(l0);
		pg.addLocation(l1);
		pg.addLocation(l2);
		pg.addLocation(exit);

		pg.addInitialLocation(l0);

		pg.addTransition(new PGTransition(l0, "(a==c)", "bb:=1", exit));
		pg.addTransition(new PGTransition(l0, "(a==b) && ((x!=y) && (!((x<3))))", "", l2));
		pg.addTransition(new PGTransition(l1, "!((x<3))", "", l2));
		pg.addTransition(new PGTransition(l0, "(a==b) && ((x!=y) && ((x<3)))", "x:=x+1", l1));
		pg.addTransition(new PGTransition(l1, "(x<3)", "x:=x+1", l1));
		pg.addTransition(new PGTransition(l2, "", "y:=9", exit));

		return pg;
	}

	private ProgramGraph expected2() {

		ProgramGraph pg = fvmFacadeImpl.createProgramGraph();
		Location l0 = new Location("[if::x>1->C?x;D!5;do::x>1->C?x;y:=1;do::r>3->r:=1;D!rodod;ppp:=2fi]");
		Location l1 = new Location("[D!5;do::x>1->C?x;y:=1;do::r>3->r:=1;D!rodod;ppp:=2]");
		Location l2 = new Location("[y:=1;do::r>3->r:=1;D!rod;do::x>1->C?x;y:=1;do::r>3->r:=1;D!rodod;ppp:=2]");
		Location l3 = new Location("[D!r;do::r>3->r:=1;D!rod;do::x>1->C?x;y:=1;do::r>3->r:=1;D!rodod;ppp:=2]");
		Location l4 = new Location("[do::r>3->r:=1;D!rod;do::x>1->C?x;y:=1;do::r>3->r:=1;D!rodod;ppp:=2]");
		Location l5 = new Location("[ppp:=2]");
		Location l6 = new Location("[do::x>1->C?x;y:=1;do::r>3->r:=1;D!rodod;ppp:=2]");
		Location exit = new Location("[]");

		pg.addLocation(l0);
		pg.addLocation(l1);
		pg.addLocation(l2);
		pg.addLocation(l3);
		pg.addLocation(l4);
		pg.addLocation(l5);
		pg.addLocation(l6);
		pg.addLocation(exit);

		pg.addInitialLocation(l0);

		pg.addTransition(new PGTransition(l6, "!((x>1))", "", l5));
		pg.addTransition(new PGTransition(l5, "", "ppp:=2", exit));
		pg.addTransition(new PGTransition(l3, "", "D!r", l4));
		pg.addTransition(new PGTransition(l4, "!((r>3))", "", l6));
		pg.addTransition(new PGTransition(l0, "(x>1)", "C?x", l1));
		pg.addTransition(new PGTransition(l1, "", "D!5", l6));
		pg.addTransition(new PGTransition(l2, "", "y:=1", l4));
		pg.addTransition(new PGTransition(l6, "(x>1)", "C?x", l2));
		pg.addTransition(new PGTransition(l4, "(r>3)", "r:=1", l3));

		return pg;
	}

	private ProgramGraph expected3() {
		ProgramGraph pg = fvmFacadeImpl.createProgramGraph();

		Location l0 = new Location("[if::x<3->do::x<4->x:=5;x:=6od;x:=7fi]");
		Location l1 = new Location("[do::x<4->x:=5;x:=6od;x:=7]");
		Location l2 = new Location("[x:=6;do::x<4->x:=5;x:=6od;x:=7]");
		Location l3 = new Location("[x:=7]");
		Location exit = new Location("[]");

		pg.addLocation(l0);
		pg.addLocation(l1);
		pg.addLocation(l2);
		pg.addLocation(l3);
		pg.addLocation(exit);

		pg.addInitialLocation(l0);

		pg.addTransition(new PGTransition(l0, "(x<3) && ((x<4))", "x:=5", l2));
		pg.addTransition(new PGTransition(l3, "", "x:=7", exit));
		pg.addTransition(new PGTransition(l1, "!((x<4))", "", l3));
		pg.addTransition(new PGTransition(l0, "(x<3) && (!((x<4)))", "", l3));
		pg.addTransition(new PGTransition(l1, "(x<4)", "x:=5", l2));
		pg.addTransition(new PGTransition(l2, "", "x:=6", l1));

		return pg;
	}

	@SuppressWarnings("serial")
	@Test
	public void soda() throws Exception {
		ProgramGraph pg = VendingmachineInNanopromela.build();

		ProgramGraph exp = expected4();

		assertEquals(pg.getLocations(), exp.getLocations());
		assertEquals(pg.getInitialLocations(), exp.getInitialLocations());
		assertEquals(pg.getTransitions(), exp.getTransitions());

		Set<ActionDef> ad = new HashSet<ActionDef>() {
			{
				add(new ParserBasedActDef());
			}
		};

		Set<ConditionDef> cd = new HashSet<ConditionDef>() {
			{
				add(new ParserBasedCondDef());
			}
		};

		TransitionSystem ts = fvmFacadeImpl.transitionSystemFromProgramGraph(pg, ad, cd);

		TransitionSystem expected = expected4TS();
		assertEquals(expected.getInitialStates(), ts.getInitialStates());
		assertEquals(expected.getStates(), ts.getStates());
		assertEquals(expected.getTransitions(), ts.getTransitions());
		assertEquals(expected.getActions(), ts.getActions());
		assertEquals(expected.getAtomicPropositions(), ts.getAtomicPropositions());
		assertEquals(expected.getLabelingFunction(), ts.getLabelingFunction());
	}

	private ProgramGraph expected4() {

		ProgramGraph pg = fvmFacadeImpl.createProgramGraph();

		Location dostmt = new Location("[do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od]");
		Location ifstmt = new Location("[if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi;do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od]");
		Location exit = new Location("[]");

		pg.addLocation(dostmt);
		pg.addLocation(ifstmt);
		pg.addLocation(exit);

		pg.addInitialLocation(dostmt);

		pg.addTransition(new PGTransition(ifstmt, "((nsoda==0)&&(nbeer==0))", "skip", dostmt));
		pg.addTransition(new PGTransition(ifstmt, "(nbeer>0)", "nbeer:=nbeer-1", dostmt));
		pg.addTransition(new PGTransition(dostmt, "(true)", "skip", ifstmt));
		pg.addTransition(new PGTransition(dostmt, "(true)", "atomic{nbeer:=3;nsoda:=3}", dostmt));
		pg.addTransition(new PGTransition(dostmt, "!((true)||(true))", "", exit));
		pg.addTransition(new PGTransition(ifstmt, "(nsoda>0)", "nsoda:=nsoda-1", dostmt));
		return pg;
	}

	TransitionSystem expected4TS() {
		TransitionSystem ts = fvmFacadeImpl.createTransitionSystem();

		State s0 = new State("[location=[do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=3, nsoda=0}]");
		State s1 = new State("[location=[do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=3, nsoda=1}]");
		State s2 = new State("[location=[if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi;do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=1, nsoda=3}]");
		State s3 = new State("[location=[do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=0, nsoda=0}]");
		State s4 = new State("[location=[do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={}]");
		State s5 = new State("[location=[do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=0, nsoda=2}]");
		State s6 = new State("[location=[if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi;do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=1, nsoda=1}]");
		State s7 = new State("[location=[if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi;do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=1, nsoda=2}]");
		State s8 = new State("[location=[do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=0, nsoda=1}]");
		State s9 = new State("[location=[do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=0, nsoda=3}]");
		State s10 = new State("[location=[do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=2, nsoda=0}]");
		State s11 = new State("[location=[if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi;do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=1, nsoda=0}]");
		State s12 = new State("[location=[do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=1, nsoda=3}]");
		State s13 = new State("[location=[if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi;do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=0, nsoda=1}]");
		State s14 = new State("[location=[if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi;do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=0, nsoda=0}]");
		State s15 = new State("[location=[do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=1, nsoda=2}]");
		State s16 = new State("[location=[if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi;do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=0, nsoda=3}]");
		State s17 = new State("[location=[do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=1, nsoda=1}]");
		State s18 = new State("[location=[if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi;do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=0, nsoda=2}]");
		State s19 = new State("[location=[do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=1, nsoda=0}]");
		State s20 = new State("[location=[if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi;do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=2, nsoda=0}]");
		State s21 = new State("[location=[if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi;do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=2, nsoda=1}]");
		State s22 = new State("[location=[do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=2, nsoda=2}]");
		State s23 = new State("[location=[do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=2, nsoda=1}]");
		State s24 = new State("[location=[if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi;do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=3, nsoda=3}]");
		State s25 = new State("[location=[do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=2, nsoda=3}]");
		State s27 = new State("[location=[if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi;do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=3, nsoda=0}]");
		State s28 = new State("[location=[if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi;do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=3, nsoda=2}]");
		State s29 = new State("[location=[if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi;do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=3, nsoda=1}]");
		State s30 = new State("[location=[if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi;do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=2, nsoda=2}]");
		State s31 = new State("[location=[if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi;do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=2, nsoda=3}]");
		State s34 = new State("[location=[if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi;do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={}]");
		State s32 = new State("[location=[do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=3, nsoda=2}]");
		State s33 = new State("[location=[do::true->skip;if::nsoda>0->nsoda:=nsoda-1::nbeer>0->nbeer:=nbeer-1::(nsoda==0)&&(nbeer==0)->skipfi::true->atomic{nbeer:=3;nsoda:=3}od], eval={nbeer=3, nsoda=3}]");

		ts.addState(s0);
		ts.addState(s1);
		ts.addState(s2);
		ts.addState(s3);
		ts.addState(s4);
		ts.addState(s5);
		ts.addState(s6);
		ts.addState(s7);
		ts.addState(s8);
		ts.addState(s9);
		ts.addState(s10);
		ts.addState(s11);
		ts.addState(s12);
		ts.addState(s13);
		ts.addState(s14);
		ts.addState(s15);
		ts.addState(s16);
		ts.addState(s17);
		ts.addState(s18);
		ts.addState(s19);
		ts.addState(s20);
		ts.addState(s21);
		ts.addState(s22);
		ts.addState(s23);
		ts.addState(s24);
		ts.addState(s25);
		ts.addState(s27);
		ts.addState(s28);
		ts.addState(s29);
		ts.addState(s30);
		ts.addState(s31);
		ts.addState(s34);
		ts.addState(s32);
		ts.addState(s33);

		ts.addInitialState(s4);

		Action sget = new Action("nsoda:=nsoda-1");
		Action skip = new Action("skip");
		Action bget = new Action("nbeer:=nbeer-1");
		Action refill = new Action("atomic{nbeer:=3;nsoda:=3}");

		ts.addAction(sget);
		ts.addAction(skip);
		ts.addAction(refill);
		ts.addAction(bget);

		ts.addTransition(new Transition(s5, refill, s33));
		ts.addTransition(new Transition(s22, refill, s33));
		ts.addTransition(new Transition(s15, refill, s33));
		ts.addTransition(new Transition(s5, skip, s18));
		ts.addTransition(new Transition(s7, bget, s5));
		ts.addTransition(new Transition(s32, skip, s28));
		ts.addTransition(new Transition(s24, bget, s25));
		ts.addTransition(new Transition(s7, sget, s17));
		ts.addTransition(new Transition(s19, skip, s11));
		ts.addTransition(new Transition(s24, sget, s32));
		ts.addTransition(new Transition(s21, bget, s17));
		ts.addTransition(new Transition(s16, sget, s5));
		ts.addTransition(new Transition(s1, refill, s33));
		ts.addTransition(new Transition(s25, skip, s31));
		ts.addTransition(new Transition(s17, refill, s33));
		ts.addTransition(new Transition(s8, refill, s33));
		ts.addTransition(new Transition(s23, refill, s33));
		ts.addTransition(new Transition(s21, sget, s10));
		ts.addTransition(new Transition(s8, skip, s13));
		ts.addTransition(new Transition(s6, bget, s8));
		ts.addTransition(new Transition(s33, skip, s24));
		ts.addTransition(new Transition(s27, bget, s10));
		ts.addTransition(new Transition(s17, skip, s6));
		ts.addTransition(new Transition(s6, sget, s19));
		ts.addTransition(new Transition(s34, skip, s4));
		ts.addTransition(new Transition(s32, refill, s33));
		ts.addTransition(new Transition(s20, bget, s19));
		ts.addTransition(new Transition(s10, skip, s20));
		ts.addTransition(new Transition(s18, sget, s8));
		ts.addTransition(new Transition(s14, skip, s3));
		ts.addTransition(new Transition(s19, refill, s33));
		ts.addTransition(new Transition(s11, bget, s3));
		ts.addTransition(new Transition(s10, refill, s33));
		ts.addTransition(new Transition(s3, refill, s33));
		ts.addTransition(new Transition(s30, sget, s23));
		ts.addTransition(new Transition(s3, skip, s14));
		ts.addTransition(new Transition(s0, skip, s27));
		ts.addTransition(new Transition(s29, bget, s23));
		ts.addTransition(new Transition(s15, skip, s7));
		ts.addTransition(new Transition(s4, refill, s33));
		ts.addTransition(new Transition(s33, refill, s33));
		ts.addTransition(new Transition(s29, sget, s0));
		ts.addTransition(new Transition(s31, bget, s12));
		ts.addTransition(new Transition(s13, sget, s3));
		ts.addTransition(new Transition(s23, skip, s21));
		ts.addTransition(new Transition(s12, refill, s33));
		ts.addTransition(new Transition(s9, skip, s16));
		ts.addTransition(new Transition(s4, skip, s34));
		ts.addTransition(new Transition(s25, refill, s33));
		ts.addTransition(new Transition(s31, sget, s22));
		ts.addTransition(new Transition(s2, bget, s9));
		ts.addTransition(new Transition(s1, skip, s29));
		ts.addTransition(new Transition(s9, refill, s33));
		ts.addTransition(new Transition(s28, bget, s22));
		ts.addTransition(new Transition(s2, sget, s15));
		ts.addTransition(new Transition(s12, skip, s2));
		ts.addTransition(new Transition(s28, sget, s1));
		ts.addTransition(new Transition(s30, bget, s15));
		ts.addTransition(new Transition(s0, refill, s33));
		ts.addTransition(new Transition(s22, skip, s30));

		ts.addAtomicProposition("nbeer = 3");
		ts.addAtomicProposition("nbeer = 2");
		ts.addAtomicProposition("nbeer = 1");
		ts.addAtomicProposition("nbeer = 0");
		ts.addAtomicProposition("nsoda = 1");
		ts.addAtomicProposition("nsoda = 0");
		ts.addAtomicProposition("nsoda = 3");
		ts.addAtomicProposition("nsoda = 2");

		ts.addLabel(s0, "nbeer = 3");
		ts.addLabel(s0, "nsoda = 0");
		ts.addLabel(s1, "nbeer = 3");
		ts.addLabel(s1, "nsoda = 1");
		ts.addLabel(s2, "nbeer = 1");
		ts.addLabel(s2, "nsoda = 3");
		ts.addLabel(s3, "nbeer = 0");
		ts.addLabel(s3, "nsoda = 0");
		ts.addLabel(s6, "nbeer = 1");
		ts.addLabel(s6, "nsoda = 1");
		ts.addLabel(s5, "nbeer = 0");
		ts.addLabel(s5, "nsoda = 2");
		ts.addLabel(s7, "nbeer = 1");
		ts.addLabel(s7, "nsoda = 2");
		ts.addLabel(s8, "nbeer = 0");
		ts.addLabel(s8, "nsoda = 1");
		ts.addLabel(s9, "nbeer = 0");
		ts.addLabel(s9, "nsoda = 3");
		ts.addLabel(s10, "nbeer = 2");
		ts.addLabel(s10, "nsoda = 0");
		ts.addLabel(s11, "nbeer = 1");
		ts.addLabel(s11, "nsoda = 0");
		ts.addLabel(s14, "nbeer = 0");
		ts.addLabel(s14, "nsoda = 0");
		ts.addLabel(s12, "nbeer = 1");
		ts.addLabel(s12, "nsoda = 3");
		ts.addLabel(s13, "nbeer = 0");
		ts.addLabel(s13, "nsoda = 1");
		ts.addLabel(s15, "nbeer = 1");
		ts.addLabel(s15, "nsoda = 2");
		ts.addLabel(s17, "nbeer = 1");
		ts.addLabel(s17, "nsoda = 1");
		ts.addLabel(s16, "nbeer = 0");
		ts.addLabel(s16, "nsoda = 3");
		ts.addLabel(s18, "nbeer = 0");
		ts.addLabel(s18, "nsoda = 2");
		ts.addLabel(s19, "nbeer = 1");
		ts.addLabel(s19, "nsoda = 0");
		ts.addLabel(s20, "nbeer = 2");
		ts.addLabel(s20, "nsoda = 0");
		ts.addLabel(s21, "nbeer = 2");
		ts.addLabel(s21, "nsoda = 1");
		ts.addLabel(s22, "nbeer = 2");
		ts.addLabel(s22, "nsoda = 2");
		ts.addLabel(s23, "nbeer = 2");
		ts.addLabel(s23, "nsoda = 1");
		ts.addLabel(s24, "nbeer = 3");
		ts.addLabel(s24, "nsoda = 3");
		ts.addLabel(s25, "nbeer = 2");
		ts.addLabel(s25, "nsoda = 3");
		ts.addLabel(s27, "nbeer = 3");
		ts.addLabel(s27, "nsoda = 0");
		ts.addLabel(s28, "nbeer = 3");
		ts.addLabel(s28, "nsoda = 2");
		ts.addLabel(s29, "nbeer = 3");
		ts.addLabel(s29, "nsoda = 1");
		ts.addLabel(s30, "nbeer = 2");
		ts.addLabel(s30, "nsoda = 2");
		ts.addLabel(s31, "nbeer = 2");
		ts.addLabel(s31, "nsoda = 3");
		ts.addLabel(s32, "nbeer = 3");
		ts.addLabel(s32, "nsoda = 2");
		ts.addLabel(s33, "nbeer = 3");
		ts.addLabel(s33, "nsoda = 3");

		return ts;
	}

	@SuppressWarnings("serial")
	@Test
	public void loop() throws Exception {
		ProgramGraph pg = fvmFacadeImpl.programGraphFromNanoPromelaString(//
				"do :: x > 1 -> y := x + y \n" + //
						"	:: y < x -> x:=0; y:=x \n" + //
						"od");

		ProgramGraph exp = expectedloop();

//		System.out.println("exp"+exp.getTransitions());
//		System.out.println("cur"+pg.getTransitions());
//
//		System.out.println("exp"+exp.getLocations());
//		System.out.println("cur"+pg.getLocations());

		
		
		assertEquals(pg.getLocations(), exp.getLocations());
		assertEquals(pg.getInitialLocations(), exp.getInitialLocations());
		assertEquals(pg.getTransitions(), exp.getTransitions());

		Set<ActionDef> ad = new HashSet<ActionDef>() {
			{
				add(new ParserBasedActDef());
			}
		};
		Set<ConditionDef> cd = new HashSet<ConditionDef>() {
			{
				add(new ParserBasedCondDef());
			}
		};

		TransitionSystem ts = fvmFacadeImpl.transitionSystemFromProgramGraph(pg, ad, cd);

		TransitionSystem expected = expectedloopTS();
		assertEquals(expected.getInitialStates(), ts.getInitialStates());
		assertEquals(expected.getStates(), ts.getStates());
		assertEquals(expected.getTransitions(), ts.getTransitions());
		assertEquals(expected.getActions(), ts.getActions());
		assertEquals(expected.getAtomicPropositions(), ts.getAtomicPropositions());
		assertEquals(expected.getLabelingFunction(), ts.getLabelingFunction());

	}

	private ProgramGraph expectedloop() {

		ProgramGraph pg = fvmFacadeImpl.createProgramGraph();
		Location loop = new Location("[do::x>1->y:=x+y::y<x->x:=0;y:=xod]");
		Location ass = new Location("[y:=x;do::x>1->y:=x+y::y<x->x:=0;y:=xod]");
		Location exit = new Location("[]");

		pg.addLocation(ass);
		pg.addLocation(loop);
		pg.addLocation(exit);

		pg.addInitialLocation(loop);

		pg.addTransition(new PGTransition(loop, "(x>1)", "y:=x+y", loop));
		pg.addTransition(new PGTransition(loop, "(y<x)", "x:=0", ass));
		pg.addTransition(new PGTransition(ass, "", "y:=x", loop));
		pg.addTransition(new PGTransition(loop, "!((x>1)||(y<x))", "", exit));

		return pg;
	}

	private TransitionSystem expectedloopTS() {

		TransitionSystem ts = fvmFacadeImpl.createTransitionSystem();

		State s1 = new State("[location=[], eval={}]");
		State s0 = new State("[location=[do::x>1->y:=x+y::y<x->x:=0;y:=xod], eval={}]");

		ts.addState(s1);
		ts.addState(s0);

		ts.addInitialState(s0);

		Action id = new Action("");
		ts.addAction(id);

		ts.addTransition(new Transition(s0, id, s1));

		return ts;
	}
}