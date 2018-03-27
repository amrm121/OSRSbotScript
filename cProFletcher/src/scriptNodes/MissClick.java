package scriptNodes;

import control.Node;

public class MissClick extends Node{

	@Override
	public boolean validate() {
		return false;
	}

	@Override
	public void execute() {

		
	}

	@Override
	public int priority() {
		return 105;
	}

	@Override
	public String getName() {
		return "Miss clicking";
	}

	@Override
	public void countM(int x) {
		
	}

	@Override
	public int getCount() {
		return 1;
	}

}
