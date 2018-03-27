package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.imageio.ImageIO;

//topBot is deprecated, the site doesn't run anymore.
import org.tbot.bot.TBot;
import org.tbot.graphics.MouseTrail;
import org.tbot.internal.AbstractScript;
import org.tbot.internal.Manifest;
import org.tbot.internal.event.events.MessageEvent;
import org.tbot.internal.event.listeners.MessageListener;
import org.tbot.internal.event.listeners.PaintListener;
import org.tbot.internal.handlers.LogHandler;
import org.tbot.methods.Calculations;
import org.tbot.methods.Mouse;
import org.tbot.methods.Random;
import org.tbot.methods.Skills;

import gui.Gui;
import control.Controller;
import control.Node;
import control.SigSnippet;
//principal class which loads everything needed to run the script
@Manifest(name = "Cheddar's Pro Fletcher", authors = "Cheddar", description = "Task system, craft and string all longbows and shortbows! [Also auto 1-55]", category = org.tbot.internal.ScriptCategory.FLETCHING, version = 1.3)
public class MainHandler extends AbstractScript implements MessageListener, PaintListener, MouseListener{
	static Gui frame = new Gui();
	private MouseTrail mt = new MouseTrail();
	public static boolean start = false, refil = false, refilString = false, random = false, drawM = false, oS = false, randomize = false; //node control variables, like a state machine I decide wether to go based on my actual state and actions perfomed by it.
	public static  String actualLog = "a ", actualBow = "";
	private static long currentTime, startTime, stRTime, mSTime;
	private int startXP, gainedXP, iPerHour, fPerHour;
	public static int startBow = 0, startString = 0, startLevel = 0, finalLevel, finalItens, mR, nR;
	private static Controller controller = new Controller(); //node control function start, it allows the player to select tasks to do, like up from level 1-50 in fletching automatically, the resources needed for this are cointainded in my forum post.
	
	public static String getBow(){ //get the actual bow to me made, decided by the GUI
		int b = actualLog.indexOf(' ');
		String a = "";
		if(b == -1){
			a = actualLog + " " + actualBow;
		}else{
			a = actualLog.substring(0, b) + " " + actualBow;
		}
		return a;
	}
	public static int getR(){
		return Random.nextInt(1, 5);
	}
	public static String getLog(){
		return actualLog;
	}
	public static void randomMouse(){  //random mouse movement
		long millis = System.currentTimeMillis() - mSTime;
		long minute = (millis / (1000 * 60)) % 60; 
		if (minute == mR){
			Mouse.moveRandomly();
			mSTime = System.currentTimeMillis();
			mR = Random.nextInt(1, 3);
		}
	}
	public static void randomNode(){ //random node for more human behavior
		long millis = System.currentTimeMillis() - stRTime;
		long minute = (millis / (1000 * 60)) % 60;
		if (minute == nR){
			random = true;
			stRTime = System.currentTimeMillis();
			nR = Random.nextInt(4, 8);
		}
	}
	public static void setControl(Controller controller){ //defining my node controller
		MainHandler.controller = controller;
	}
	public static void setActualLog(String a){
		actualLog = a;
	}
	public static void setActualBow(String a){
		actualBow = a;
	}
	public static void guiEx(){
		frame.setVisible(false);
	}
	public static void setStart(boolean a){
		MainHandler.start = a;
	}

	@Override
	public void messageReceived(MessageEvent ME) { //console messages to help the user seeing what is happening
		 if (ME.getMessage().getText().contains("You carefully cut the wood into a longbow.")) {
			 startBow++;
        }else if(ME.getMessage().getText().contains("You carefully cut the wood into a shortbow.")){
        	startBow++;
        }else if(ME.getMessage().getText().equalsIgnoreCase("You carefully cut the wood into 15 arrow shafts.")){
        	startBow++;
        }else if(ME.getMessage().getText().contains("Congratulations, you've just advanced a Fletching level.")){
        	startLevel++;
        }else if(ME.getMessage().getText().contains("You add a string to the bow.")){
        	startString++;
        }
	}
	
	public boolean onStart(){ // THE FIRST function to be called by the bot, it starts all the variables I need
		startTime = stRTime = mSTime = System.currentTimeMillis(); 
		mR = Random.nextInt(2, 3);
		nR = Random.nextInt(4, 8);
		LogHandler.log("Moving mouse in " + mR + " minutes.");
		LogHandler.log("Next random node in " + nR + " minutes.");
		startTime = stRTime = mSTime = System.currentTimeMillis();
		Mouse.setSpeed(50);
		paintImage = getImage("http://i.imgur.com/wbi6Lb7.png");
		cur = getImage("http://i.imgur.com/PCfeJTo.png");
		startXP = Skills.getExperience(Skills.Skill.Flecthing);
		frame.setVisible(true);
		return true;
		
	}
	//Cursor settings -- 
	Image paintImage = null;
	Image cur = null;
	public Image getImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch(Exception e) {
            System.err.println(e);
            return null;
        }
    }
	//END Cursos settings -- 
	
	@Override
	public int loop() { //
		if(!start){ startTime = stRTime = mSTime = System.currentTimeMillis(); }
		randomMouse();
		randomNode();
		gainedXP = Skills.getExperience(Skills.Skill.Flecthing) - startXP;
		iPerHour = Calculations.getPerHour(startBow + startString, currentTime);
		fPerHour = Calculations.getPerHour(gainedXP, currentTime);
		Node n = controller.getCurrentNode();
		if(n != null && start){
			n.execute();
		}else if(n == null && start && ((startBow+startString)-finalItens >= 0)){
			LogHandler.log("Thank you for using Cheddar's Pro Fletcher, you got: " + gainedXP +" fletching experience.");
			TBot.getBot().getScriptHandler().stopScript();
		}
		return 100;
	}
	
	@Override
	public void onFinish(){
		long millis = System.currentTimeMillis() - startTime;
		long second = (millis / 1000);
		SigSnippet.sendSignatureData(second, startBow+startString, gainedXP, 0, 0); //SigSnippet was a RasPi server I made at home with a DB to keep saved user's time and xp gained within the use of script.
		LogHandler.log("Thank you for using Cheddar's Pro Fletcher, you got: " + gainedXP +" fletching experience.");
	}
	
	private String timeRunning(){ //the actual time running since the script has started
		long millis = currentTime = System.currentTimeMillis() - startTime;
		long second = (millis / 1000) % 60;
		long minute = (millis / (1000 * 60)) % 60;
		long hour = (millis / (1000 * 60 * 60)) % 24;
		String time = String.format("%02d:%02d:%02d", hour, minute, second);        
		return time;
	}
	
	@Override
	public void onRepaint(final Graphics g){  //Little GUI updates in runtime
		String node = "";
		Node n = controller.getCurrentNode();
		if(n != null) node = n.getName();
		else node = "Waiting.";
		g.drawImage(paintImage, 328, 337, null);
		g.setColor( Color.WHITE );       
		g.setFont(new Font("CourierNew", Font.PLAIN, 10)); 
		g.drawString( "" + timeRunning(), 417, 386);        
		g.drawString( "" + gainedXP + " ["+fPerHour+"]", 400, 407);    
		g.drawString( "  " + (startBow + startString) + " ["+iPerHour+"]", 406, 427);  
		g.drawString( "" + startLevel, 417, 447); 
		g.drawString( "" + node, 372, 467);
		mt.draw(g);
		if(drawM){
			g.drawString( "Hovering fletching", Mouse.getX(), Mouse.getY()); 
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

}
	


