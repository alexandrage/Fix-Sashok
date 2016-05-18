package net.launcher.components;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Focus extends WindowAdapter{
	  Component initComp;

	  Focus(Component c) {
	    initComp = c;
	  }

	 public void windowOpened(WindowEvent e) {
	    initComp.requestFocus();
	    e.getWindow().removeWindowListener(this);
     }
	 
	  public static void setInitialFocus(Window w, Component c) {
		  w.addWindowListener(new Focus(c));
	  }
}
