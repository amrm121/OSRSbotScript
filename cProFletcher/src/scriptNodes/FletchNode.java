package scriptNodes;

import main.MainHandler;
import control.Chronometer;

import org.tbot.internal.event.events.InventoryEvent;
import org.tbot.internal.event.listeners.InventoryListener;
import org.tbot.internal.handlers.LogHandler;
import org.tbot.methods.Mouse;
import org.tbot.methods.Random;
import org.tbot.methods.Time;
import org.tbot.methods.Widgets;
import org.tbot.methods.tabs.Inventory;
import org.tbot.util.Condition;
import org.tbot.wrappers.Item;
import org.tbot.wrappers.WidgetChild;

import control.ChatCraftInterface;
import control.Node;

public class FletchNode extends Node implements InventoryListener{
	private int count, option, pr, countI, batch;
	private boolean valid;
	private String log;
	private Chronometer timer = new Chronometer();
	
	public FletchNode(int count, int option, String log, int pr){
		this.timer.stop();
		this.countI = 0;
		this.batch = 0;
		this.valid = true;
		this.count = count;
		this.pr = pr;
		this.option = option;
		this.log = log;
	}

	public void countM(int a){
		this.count-=a;
	}
	

	@Override
	public boolean validate() {
		return this.valid && !MainHandler.refil;
	}
	

	@Override
	public void execute() {
		timer.start();
		boolean cont = true;
		if(!Inventory.contains("Knife") || !Inventory.contains(log)) {MainHandler.refil = true; cont = false;}
		MainHandler.setActualLog(log);
		WidgetChild inv;
		if(!Inventory.isOpen()){
			inv = Widgets.getWidget(548, 45);
			inv.click();
			Time.sleep(300, 360);
		}
		if((timer.getSeconds() < -4 || this.batch == 0) && cont){
			this.batch = Inventory.getCount(log);
			final Item a = Inventory.getFirst("Knife");
			final Item b = Inventory.getFirst(log);
			if(!ChatCraftInterface.interfaceOpen()){
				if(a.interact("Use")){
					Time.sleepUntil(new Condition() {
		                @Override
		                public boolean check() {
		                    return Inventory.hasItemSelected();
		                }
		            },1000);
				}
				Time.sleep(70, 160);
				if(b.interact("Use " + a.getName() + " -> " + b.getName())){
					Time.sleep(200, 300);
					Time.sleepUntil(new Condition() {
		                @Override
		                public boolean check() {
		                    return ChatCraftInterface.interfaceOpen();
		                }
		            },1000);
				}
			}
			if(ChatCraftInterface.makeX(option, Random.nextInt(39, 102))){
				int r = Random.nextInt(1, 5);
				if(r == 3) Mouse.move(Mouse.getX()+Random.nextInt(-50, 40), Mouse.getY()+Random.nextInt(-50, 40));	
				timer.stop();
				timer.start();
			}
		}
	}

	@Override
	public int priority() {
		return pr;
	}

	@Override
	public String getName() {
		return "Fletching " + log;
	}

	public int getCount() {
		return this.count;
	}

	@Override
	public void itemsAdded(InventoryEvent arg0) {}

	@Override
	public void itemsRemoved(InventoryEvent IR) {
		if(IR.getItem().getName().equals(log)){
			timer.stop();
			batch--;
			countI++;
			if(batch == 0) MainHandler.refil = true;
			if(countI == count) {MainHandler.refil = false; LogHandler.log("Task cut unstrung " + log + ", ended.");  this.valid = false;}
		}
		
	}


}
