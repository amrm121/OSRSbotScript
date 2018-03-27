package scriptNodes;

import main.MainHandler;

import org.tbot.internal.event.events.InventoryEvent;
import org.tbot.internal.event.listeners.InventoryListener;
import org.tbot.internal.handlers.LogHandler;
import org.tbot.methods.Bank;
import org.tbot.methods.Mouse;
import org.tbot.methods.Random;
import org.tbot.methods.Time;
import org.tbot.methods.Widgets;
import org.tbot.methods.tabs.Inventory;
import org.tbot.wrappers.Item;
import org.tbot.wrappers.WidgetChild;

import control.Chronometer;
import control.Node;
import control.StringChatInterface;

public class StringBow extends Node implements InventoryListener{
	private int count, countI, batch;
	private boolean valid = true;
	private Chronometer timer = new Chronometer();
	private String bow;
	public StringBow(int count, String bow){
		this.timer.stop();
		this.batch = 0;
		this.countI = 0;
		this.valid = true;
		this.count = count;
		this.bow = bow;
	}

	public void countM(int a){
		this.count-=a;
	}
	
	@Override
	public boolean validate() {
		return this.valid && !MainHandler.refilString;
	}

	
	@Override
	public void execute() {
		timer.start();
		boolean cont = true;
		if(!Inventory.contains("Bow string") || !Inventory.contains(MainHandler.getBow())){ MainHandler.refilString = true; cont = false; }
		WidgetChild inv;
		if(!Inventory.isOpen()){
			inv = Widgets.getWidget(548, 45);
			inv.click();
			Time.sleep(300, 360);
		}
		if((timer.getSeconds() < -4 || this.batch == 0) && cont){
			this.batch = Inventory.getCount("Bow string");
			Item a;
			Item b;
			if(Inventory.getInSlot(12).getName().equals("Bow string")){
				a = Inventory.getLast("Bow string");
				b = Inventory.getFirst(MainHandler.getBow());
			}else{
				a = Inventory.getFirst("Bow string");
				b = Inventory.getLast(MainHandler.getBow());
			}		
			Time.sleep(300, 400);
			if(!StringChatInterface.interfaceOpen()){
				boolean use = false, inter = false;
				while(!use){
					if(Bank.isOpen()) use = true;
					if(a.interact("Use")) use = true;
					Time.sleep(250,370);
				}
				while(!inter){
					if(Bank.isOpen()) inter = true;
					if(b.interact("Use " + a.getName() + " -> " + b.getName())) inter = true;
					Time.sleep(150,230);
				}
			}
			if(StringChatInterface.makeX(Random.nextInt(39, 102))){
				int r = Random.nextInt(1, 5);
				if(r == 3) Mouse.move(Mouse.getX()+Random.nextInt(-50, 40), Mouse.getY()+Random.nextInt(-50, 40));	
				timer.stop();
				timer.start();
			}
			Time.sleep(200, 300);
		}		
	}

	@Override
	public int priority() {
		return 3;
	}

	@Override
	public String getName() {
		return "Stringing " + bow;
	}

	public int getCount() {
		return this.count;
	}

	@Override
	public void itemsAdded(InventoryEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void itemsRemoved(InventoryEvent IR) {
		if(IR.getItem().getName().equals("Bow string")){
			batch--;
			countI++;
			timer.stop();
			if(batch == 0) MainHandler.refilString = true;
			if(countI == count) {LogHandler.log("Task string " + bow + " , ended."); MainHandler.refilString = false; valid = false;}
		}
		
	}

}
