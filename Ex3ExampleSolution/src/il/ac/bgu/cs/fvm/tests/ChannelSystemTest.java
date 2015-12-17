package il.ac.bgu.cs.fvm.tests;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.junit.Test;

import il.ac.bgu.cs.fvm.Ex3FacadeImpl;
import il.ac.bgu.cs.fvm.channelsystem.ChannelSystem;
import il.ac.bgu.cs.fvm.examples.AlternatingBitProtocolBuilder;
import il.ac.bgu.cs.fvm.labels.Action;
import il.ac.bgu.cs.fvm.labels.LabeledElement;
import il.ac.bgu.cs.fvm.labels.State;
import il.ac.bgu.cs.fvm.programgraph.ProgramGraph;
import il.ac.bgu.cs.fvm.transitionsystem.TransitionSystem;

public class ChannelSystemTest {

	@Test
	@SuppressWarnings("serial")
	public void debug() throws Exception {

		Ex3FacadeImpl Ex3FacadeImpl = new Ex3FacadeImpl();

		TransitionSystem ts = Ex3FacadeImpl.transitionSystemFromChannelSystem(AlternatingBitProtocolBuilder.build());

		assertTrue(Ex3FacadeImpl.isInitialExecutionFragment(ts, //
				new Vector<LabeledElement>() {
					{
						add(new State("[location=snd_msg(0),off,wait(0), eval={}]"));
						add(new Action("C!0"));
						add(new State("[location=set_tmr(0),off,wait(0), eval={C=[0]}]"));
						add(new Action("_tmr_on!|_tmr_on?"));
						add(new State("[location=wait(0),on,wait(0), eval={C=[0]}]"));
						add(new Action("_timeout?|_timeout!"));
						add(new State("[location=snd_msg(0),off,wait(0), eval={C=[0]}]"));
						add(new Action("C!0"));
						add(new State("[location=set_tmr(0),off,wait(0), eval={C=[0, 0]}]"));
						add(new Action("C?y"));
						add(new State("[location=set_tmr(0),off,pr_msg(0), eval={y=0, C=[0]}]"));
						add(new Action(""));
						add(new State("[location=set_tmr(0),off,snd_ack(0), eval={y=0, C=[0]}]"));
						add(new Action("D!0"));
						add(new State("[location=set_tmr(0),off,wait(1), eval={y=0, C=[0], D=[0]}]"));
						add(new Action("C?y"));
						add(new State("[location=set_tmr(0),off,pr_msg(1), eval={y=0, C=[], D=[0]}]"));
						add(new Action(""));
						add(new State("[location=set_tmr(0),off,wait(1), eval={y=0, C=[], D=[0]}]"));

					}
				}));

	}

	@Test
	@SuppressWarnings("serial")
	public void different_pg_order() throws Exception {

		Ex3FacadeImpl Ex3FacadeImpl = new Ex3FacadeImpl();

		List<ProgramGraph> pgs = AlternatingBitProtocolBuilder.build().getProgramGraphs();

		TransitionSystem tss = Ex3FacadeImpl.transitionSystemFromChannelSystem(new ChannelSystem( //
				Arrays.asList(pgs.get(2), pgs.get(1), pgs.get(0)) //
		));

		assertTrue(Ex3FacadeImpl.isInitialExecutionFragment(tss, //
				new Vector<LabeledElement>() {
					{
						add(new State("[location=wait(0),off,snd_msg(0), eval={}]"));
						add(new Action("C!0"));
						add(new State("[location=wait(0),off,set_tmr(0), eval={C=[0]}]"));
						add(new Action("_tmr_on?|_tmr_on!"));
						add(new State("[location=wait(0),on,wait(0), eval={C=[0]}]"));
						add(new Action("_timeout!|_timeout?"));
						add(new State("[location=wait(0),off,snd_msg(0), eval={C=[0]}]"));
						add(new Action("C!0"));
						add(new State("[location=wait(0),off,set_tmr(0), eval={C=[0, 0]}]"));
						add(new Action("C?y"));
						add(new State("[location=pr_msg(0),off,set_tmr(0), eval={y=0, C=[0]}]"));
						add(new Action(""));
						add(new State("[location=snd_ack(0),off,set_tmr(0), eval={y=0, C=[0]}]"));
						add(new Action("D!0"));
						add(new State("[location=wait(1),off,set_tmr(0), eval={y=0, C=[0], D=[0]}]"));
						add(new Action("C?y"));
						add(new State("[location=pr_msg(1),off,set_tmr(0), eval={y=0, C=[], D=[0]}]"));
						add(new Action(""));
						add(new State("[location=wait(1),off,set_tmr(0), eval={y=0, C=[], D=[0]}]"));

					}
				}));

	}

}
