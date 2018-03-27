package scriptNodes;




import org.tbot.internal.handlers.LogHandler;
import org.tbot.methods.Bank;
import org.tbot.methods.Mouse;
import org.tbot.methods.Random;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Inventory;

import main.MainHandler;
import control.Node;

public class BankString extends Node{
		private boolean valid = true;
	
	@Override
	public boolean validate() {
		return MainHandler.refilString && this.valid;
	}
	
	

	@Override
	public void execute() {
		String items[] = {"Bow string", MainHandler.getBow()};
		Time.sleep(200, 300);
		if(Bank.isOpen()){
			if(!Bank.containsOneOf(items)){LogHandler.log("There isn't any more itens avaliable at Bank."); MainHandler.refilString = false; this.valid = false;} 
			Time.sleep(500, 600);
			if(Inventory.getEmptySlots() <= 28 && (Inventory.getCount("Bow string") != 14 && Inventory.getCount(MainHandler.getBow()) != 14)){
				if(Bank.depositAll()){
					Time.sleep(100, 200);
				}
			}else{
				if(Bank.depositAllExcept(items)){
					Time.sleep(100, 200);
				}
			}
			if(Inventory.getCount(MainHandler.getBow()) != 14) {
				if(Bank.withdraw(MainHandler.getBow(), 14)){
					Time.sleep(100,200);
				}
			}
			Mouse.move(Mouse.getX()+Random.nextInt(-15, 30), Mouse.getY()+Random.nextInt(-15, 30));
			if(Inventory.getCount("Bow string") != 14) {
				if(Bank.withdraw("Bow string", 14)){
					Time.sleep(100, 200);
				}
			}
			if(Inventory.getCount("Bow string") > 1 && Inventory.getCount(MainHandler.getBow()) > 1){
				if(Bank.close()){
					Time.sleep(100, 200);
					MainHandler.refilString = false;
				}
			}
			
		}else{
			if(Inventory.hasItemSelected()){
				Time.sleep(500, 600);
				Inventory.deselectItem();
				Time.sleep(200, 300);
				Bank.openNearestBank();
			}else{
				Time.sleep(300, 500);
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
		return "Banking bows";
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
