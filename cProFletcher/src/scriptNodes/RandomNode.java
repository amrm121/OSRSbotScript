package scriptNodes;

import java.awt.Point;

import main.MainHandler;


import org.tbot.methods.Bank;
import org.tbot.methods.Camera;
import org.tbot.methods.Mouse;
import org.tbot.methods.Random;
import org.tbot.methods.Time;
import org.tbot.methods.Widgets;
import org.tbot.wrappers.WidgetChild;

import control.Node;

public class RandomNode extends Node{
	private String random = "";
   
    private void HoverSkill(){
    	MainHandler.drawM = true;
    	random += " Hover fletching";
    	WidgetChild skillTab = Widgets.getWidget(548, 43); //Skills tab in hud
    	WidgetChild skillKind = Widgets.getWidget(320, 14); //Skill slots
    	if(!skillKind.isVisible()){
    		skillTab.click();
    		Time.sleep(100, 200);
    		Point a = skillKind.getLocation();
    		a.x+= Random.nextInt(15, 20);
    		a.y+= Random.nextInt(15, 26);
    		Mouse.move(a);
    	}else{
    		Point a = skillKind.getLocation();
    		a.x+= Random.nextInt(15, 17);
    		a.y+= Random.nextInt(15, 20);
    		Mouse.move(a);
    	}
    	Time.sleep(4100, 5000);
    	MainHandler.drawM = false;
    }
    
    private void MoveCamera(){
    	random += " Moving camera";
    	Camera.rotateRandomly();
    	Time.sleep(30, 150);
    	Camera.rotateAndTiltRandomly();
    }
    
    private void Delay(){
    	random += " Human delay";
    	Time.sleep(15000, 50000);
    }
    
    /*private void Emote(){
    	WidgetChild emoteTab = Widgets.getWidget(548, 38);
    	WidgetChild emote = Widgets.getWidget(216, 1).getChild(Random.nextInt(0, 19));
    	if(!emote.isVisible()){
    		emoteTab.click();
    	}else{
    		emote.click();
    	}
    }*/
    
	@Override
	public boolean validate() {
		return MainHandler.random && !Bank.isOpen();
	}

	@Override
	public void execute() {
		int op = MainHandler.getR();
		switch(op){
		case 1:
			HoverSkill();
			break;
		case 2:
			MoveCamera();
			break;
		case 3:
			Delay();
			break;
		case 4:
			MoveCamera();
			Time.sleep(660, 780);
			Mouse.moveRandomly();
		case 5:
			MoveCamera();
			Time.sleep(660, 780);
			HoverSkill();
		}
		MainHandler.random = false;
		random = "";
	}

	@Override
	public int priority() {
		return 105;
	}

	@Override
	public String getName() {
		
		return random;
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
