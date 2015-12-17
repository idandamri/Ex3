package il.ac.bgu.cs.fvm.examples;

import il.ac.bgu.cs.fvm.Ex3FacadeImpl;
import il.ac.bgu.cs.fvm.labels.Action;
import il.ac.bgu.cs.fvm.labels.State;
import il.ac.bgu.cs.fvm.transitionsystem.Transition;
import il.ac.bgu.cs.fvm.transitionsystem.TransitionSystem;

public class VendingMachineBuilder {
	static Ex3FacadeImpl Ex3FacadeImpl = new Ex3FacadeImpl();
	
	public TransitionSystem build() {
		State pay = new State("pay");
		State soda = new State("soda");
		State beer = new State("beer");
		State select = new State("select");

		TransitionSystem vendingMachine = Ex3FacadeImpl.createTransitionSystem();

		vendingMachine.addState(pay);
		vendingMachine.addState(soda);
		vendingMachine.addState(select);
		vendingMachine.addState(beer);

		vendingMachine.addInitialState(pay);

		Action insertCoin = new Action("insert coin");
		Action getSoda = new Action("get beer");
		Action getBeer = new Action("get soda");
		Action tau = new Action("tau");
		
		vendingMachine.addAction(insertCoin);
		vendingMachine.addAction(getBeer);
		vendingMachine.addAction(getSoda);
		vendingMachine.addAction(tau);

		vendingMachine.addTransition(new Transition(pay, insertCoin, select));
		vendingMachine.addTransition(new Transition(select, tau, soda));
		vendingMachine.addTransition(new Transition(select, tau, beer));
		vendingMachine.addTransition(new Transition(soda, getSoda, pay));
		vendingMachine.addTransition(new Transition(beer, getBeer, pay));

		vendingMachine.addAtomicProposition("paid");
		vendingMachine.addAtomicProposition("drink");

		vendingMachine.addLabel(soda, "paid");
		vendingMachine.addLabel(beer, "paid");
		vendingMachine.addLabel(select, "paid");
		vendingMachine.addLabel(soda, "drink");
		vendingMachine.addLabel(beer, "drink");

		return vendingMachine;

	}

}
