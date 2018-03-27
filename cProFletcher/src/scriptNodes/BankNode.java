package scriptNodes;

import main.MainHandler;

import org.tbot.internal.handlers.LogHandler;
import org.tbot.methods.Bank;
import org.tbot.methods.Mouse;
import org.tbot.methods.Random;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Inventory;

import control.Node;

public class BankNode extends Node{
	private boolean valid = true;
	@Override
	public boolean validate() {
		return (MainHandler.refil && this.valid) && !MainHandler.oS;
	}
	

	@Override
	public void execute() {
		String itens[] = {"Knife", MainHandler.getLog()};
		if(Bank.isOpen()){
			if(!Bank.contains(MainHandler.getLog())){ LogHandler.log("There isn't any more logs avaliable at Bank.");  MainHandler.refil = false; this.valid = false;} 
			if(Inventory.getEmptySlots() < 28){
				if(Bank.depositAllExcept(itens)){
					Time.sleep(100, 200);
				}
			}
			if(Inventory.getCount("Knife") < 1){
				if(Bank.withdraw("Knife", 1)){
					Time.sleep(100, 200);
				}
			}
			Mouse.move(Mouse.getX()+Random.nextInt(-15, 30), Mouse.getY()+Random.nextInt(-15, 30));
			if(Inventory.getCount(MainHandler.getLog()) != 27){
				if(Bank.withdraw(MainHandler.getLog(), 27)){
					Time.sleep(100, 200);
				}
			}
			if(Inventory.getCount(MainHandler.getLog()) > 1 && Inventory.contains("Knife")){
				if(Bank.close()){
					Time.sleep(100, 200);
					MainHandler.refil = false;
				}
			}
			
		}else{
			if(Inventory.hasItemSelected()){
				Time.sleep(500, 600);
				Inventory.deselectItem();
				Time.sleep(500, 600);
				Bank.openNearestBank();
			}else{
				Time.sleep(400, 500);
				Bank.openNearestBank();
			}
		}
	}

	@Override
	public int priority() {
		return 101;
	}

	@Override
	public String getName() {
		return "Banking bows (u)";
	}
	

	@Override
	public int getCount() {
		return 1;
	}

	@Override
	public void countM(int x) {
		// TODO Auto-generated method stub
		
	}

	
}
