package com.flamingOctoIronman.visualTest;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.flamingOctoIronman.DeathReason;
import com.flamingOctoIronman.FlamingOctoIronman;

public class MyWindow extends JFrame implements WindowListener {
	public MyWindow(){
		super("FLAMING OCTO IRONMAN");
		this.setSize(800, 640);
		this.requestFocusInWindow();
		this.addWindowListener(this);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//this.setExtendedState(Frame.MAXIMIZED_BOTH);  
		//this.setUndecorated(true);
		
		this.setVisible(true);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		FlamingOctoIronman.getInstance().stopGame(DeathReason.EXCEPTION);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
