package scriptNodes;


import org.tbot.methods.Bank;

import control.Node;

public class CloseBank extends Node{
	
	@Override
	public boolean validate() {
		return Bank.isOpen();
	}

	@Override
	public void execute() {
		Bank.close();
	}

	@Override
	public int priority() {
		return 100;
	}

	@Override
	public String getName() {
		return "Closing bank.";
	}
	
	@Override
	public void countM(int x) {
		
	}

	@Override
	public int getCount() {
		return 1;
	}


}
