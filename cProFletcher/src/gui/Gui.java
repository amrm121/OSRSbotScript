package gui;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JLabel;

import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.MainHandler;
import scriptNodes.*;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JRadioButton;

import control.Controller;
import control.Node;

import java.awt.Color;
import java.net.URL;

import javax.swing.ImageIcon;


public class Gui extends JFrame implements ListSelectionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Controller control = new Controller();
	private JPanel contentPane;
	private JTextField sAmount;
	private List task;
	private int pr = 99;
	JButton clearT, addTask, removeTask;
	ListSelectionModel listSelectionModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 *
	 */
	public Image getImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch(Exception e) {
            System.err.println(e);
            return null;
        }
    }
	public Gui() {
		setTitle("Cheddar's Pro Fletcher");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 378, 317);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		final Choice logs = new Choice();
		logs.add("Logs");
		logs.add("Oak logs");	
		logs.add("Willow logs");
		logs.add("Maple logs");
		logs.add("Yew logs");
		logs.add("Magic logs");
		logs.setBounds(10, 60, 98, 23);
		contentPane.add(logs);
		
		JButton levelup = new JButton("");
		ImageIcon imgrL = new ImageIcon(getImage("http://i.imgur.com/lePQT90.png"));
		levelup.setIcon(imgrL);
		levelup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				levelUp();
			}
		});
		levelup.setBounds(144, 260, 110, 29);
		contentPane.add(levelup);
		
		final Choice bows = new Choice();
		bows.add("shortbow (u)");
		bows.add("longbow (u)");
		bows.add("shortbow");
		bows.add("longbow");
		bows.setBounds(10, 100, 98, 23);
		contentPane.add(bows);
		
		sAmount = new JTextField();
		sAmount.setText("0");
		sAmount.setBounds(19, 162, 98, 23);
		contentPane.add(sAmount);
		sAmount.setColumns(10);
		
		final JRadioButton level = new JRadioButton("");
		level.setEnabled(false);
		level.setBounds(13, 128, 14, 14);
		contentPane.add(level);
		
		final JRadioButton amount = new JRadioButton("");
		amount.setSelected(true);
		amount.setBounds(13, 144, 14, 14);
		contentPane.add(amount);
		
		ButtonGroup group = new ButtonGroup();
		group.add(amount);
		group.add(level);
		
		addTask = new JButton("");
		ImageIcon imgT = new ImageIcon(getImage("http://i.imgur.com/xFW9kkw.png"));
		addTask.setIcon(imgT);
		addTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTask(bows.getSelectedItem(), logs.getSelectedItem(), amount.isSelected());
			}
		});
		addTask.setBounds(10, 197, 110, 29);
		contentPane.add(addTask);
		
		removeTask = new JButton("");
		ImageIcon imgrT = new ImageIcon(getImage("http://i.imgur.com/1FN4vTl.png"));
		removeTask.setIcon(imgrT);
		removeTask.setEnabled(false);
		removeTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeTask();
			}
		});
		removeTask.setBounds(10, 228, 110, 29);
		contentPane.add(removeTask);
		
		clearT = new JButton("");
		ImageIcon imgC = new ImageIcon(getImage("http://i.imgur.com/gFukI3e.png"));
		clearT.setIcon(imgC);
		clearT.setEnabled(false);
		clearT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearT();
			}
		});
		clearT.setBounds(10, 259, 110, 29);
		contentPane.add(clearT);
		task = new List();
		task.setBackground(Color.DARK_GRAY);
		task.setBounds(123, 42, 250, 217);
		contentPane.add(task);
		
		JButton btnStart = new JButton("");
		ImageIcon imgS = new ImageIcon(getImage("http://i.imgur.com/zOysJIL.png"));
		btnStart.setIcon(imgS);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String a = task.getItem(0);
				int ind = a.indexOf(',');
				int inda = a.lastIndexOf(',');
				String log = a.substring(1, ind);
				String bow = a.substring(ind+2, inda);
				 MainHandler.setStart(true);
		         MainHandler.guiEx();
		         MainHandler.actualLog = log;
		         MainHandler.actualBow = log + " " + bow;
		         start();
			}
		});
		btnStart.setBounds(255, 260, 110, 29);
		contentPane.add(btnStart);
		
		JLabel lblT = new JLabel("");
		ImageIcon imgL = new ImageIcon(getImage("http://i.imgur.com/84fB5zg.png"));
		lblT.setIcon(imgL);
		lblT.setEnabled(false);
		lblT.setBounds(0, 0, 378, 294);
		contentPane.add(lblT);
	}
	public void levelUp(){
		task.add("[Logs, Arrow shaft, Amount: 1170]");
		task.add("[Logs, shortbow (u), Amount: 154]");
		task.add("[Logs, longbow (u), Amount: 340]");
		task.add("[Oak logs, shortbow (u), Amount: 205]");
		task.add("[Oak logs, longbow (u), Amount: 583]");
		task.add("[Willow logs, shortbow (u), Amount: 445]");
		task.add("[Willow logs, longbow (u), Amount: 3125]");
		attB();
		JOptionPane.showMessageDialog(this, "Be sure you have all the required logs in your bank");
		
	}
	public void start(){ 
		for(int i = 0; i < task.getItemCount(); i++){
			String a = task.getItem(i);
			int ind = a.indexOf(',');
			int inda = a.lastIndexOf(',');
			int indc = a.lastIndexOf(':');
			String log = a.substring(1, ind);
			String bow = a.substring(ind+2, inda);
			int am = Integer.parseInt(a.substring(indc+2, a.length()-1));
			makeNode(bow, log, am);
			MainHandler.finalItens+=am;
		}
		Node bankN = new BankNode();
		Node rand = new RandomNode();
		Node bankS = new BankString();
		control.addNodes(bankN, bankS, rand);
		String a = task.getItem(0);
		int ind = a.indexOf(',');
		int inda = a.lastIndexOf(',');
		String log = a.substring(1, ind);
		String bow = a.substring(ind+2, inda);
		if(bow.equals("shortbow") || bow.equals("longbow")) {MainHandler.oS = true; bow = bow + " (u)";}
		MainHandler.actualBow = bow;
		MainHandler.actualLog = log;
		Node bankC = new CloseBank();
		control.addNodes(bankC);
		MainHandler.setControl(control);
		MainHandler.setStart(true);
		MainHandler.guiEx();
	}
	private void makeNode(String bow, String log, int am){
		if(log.equals("Logs")){
			if(bow.equals("shortbow") || bow.equals("longbow")){ //String
				Node a = new StringBow(am, bow);
				control.addNodes(a);
			}else if(bow.equals("Arrow shaft")){
				Node a = new FletchNode(am, 1, log, pr--);
				control.addNodes(a);
			}else if(bow.equals("shortbow (u)")){
				Node a = new FletchNode(am, 2, log, pr--);
				control.addNodes(a);
			}else if(bow.equals("longbow (u)")){
				Node a = new FletchNode(am, 3, log, pr--);
				control.addNodes(a);
			}
		}else{
			if(bow.equals("shortbow") || bow.equals("longbow")){ //String
				Node a = new StringBow(am, bow);
				control.addNodes(a);
			}else if(bow.equals("Arrow Shafts")){
				Node a = new FletchNode(am, 1, log ,pr--);
				control.addNodes(a);
			}else if(bow.equals("shortbow (u)")){
				Node a = new FletchNode(am, 1, log, pr--);
				control.addNodes(a);
			}else if(bow.equals("longbow (u)")){
				Node a = new FletchNode(am, 2, log, pr--);
				control.addNodes(a);
			}
		}
		
	}
	private void attB(){
		if (task.getItemCount() == 0) {
            clearT.setEnabled(false);
            removeTask.setEnabled(false);

        } else {
        	clearT.setEnabled(true);
            removeTask.setEnabled(true);
        }
	}
	public void addTask(String bow, String log, boolean opt){
		if(!opt){ //amount end
			task.add("[" + log + ", " + bow +", Until Level: " + sAmount.getText()+"]");
		}else{ //level end
			task.add("[" + log + ", " + bow +", Amount: " + sAmount.getText()+"]");
		}
		attB();
	}
	public void removeTask(){
		int selected[] = new int[3000], index = task.getItemCount();
		selected = task.getSelectedIndexes();
		if(task.getSelectedIndex() == -1) JOptionPane.showMessageDialog(this, "Select tasks to remove.");
		else{
			for(int i = 0; i < index; i++){
				task.remove(selected[i]);
			}
			attB();
		}	
	}
	public void clearT(){
		task.removeAll();
		attB();
	}
	public void valueChanged(ListSelectionEvent e) {
		
		
	}
}
