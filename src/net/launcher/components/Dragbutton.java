package net.launcher.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import net.launcher.MusPlay;
import net.launcher.run.Settings;

public class Dragbutton extends JButton implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;

	public BufferedImage img1 = (BufferedImage) createImage(1, 1);
	public BufferedImage img2 = (BufferedImage) createImage(1, 1);
	public BufferedImage img3 = (BufferedImage) createImage(1, 1);
    private boolean entered = false;
    private boolean pressed = false;
	
	public Dragbutton() {
		addMouseListener(this);
		addMouseMotionListener(this);
		setBorderPainted(false);
		setContentAreaFilled(false);
		setFocusPainted(false);
		setOpaque(false);
		setFocusable(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	protected void paintComponent(Graphics maing) {
		Graphics2D g = (Graphics2D) maing.create();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(Settings.drawTracers)
		{
			g.setColor(Color.CYAN);
			g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		}
		
		if (this.entered && !this.pressed) {{}
			g.drawImage(img2, 0, 0, getWidth(), getHeight(), null);
		}
		if (!this.entered) {
			g.drawImage(img1, 0, 0, getWidth(), getHeight(), null);
		}
		if ((this.pressed) && (this.entered)) {
			this.entered = false;
			try {
				new MusPlay("click.mp3");
			} catch(Exception e) {}
			g.drawImage(img3, 0, 0, getWidth(), getHeight(), null);
			this.pressed = false;
		}
		
		g.dispose();
		super.paintComponent(maing);
	}
	
	  public void mouseDragged(MouseEvent e) {}
	  public void mouseMoved(MouseEvent e) {}
	  public void mouseClicked(MouseEvent e) {}
	  public void mousePressed(MouseEvent e) { 
		  this.pressed = (!this.pressed);
	      repaint(); 
	  } 
	  public void mouseReleased(MouseEvent e) {
	  }
	  public void mouseEntered(MouseEvent e) {
		  this.entered = true;
	      repaint();
	  }
	  public void mouseExited(MouseEvent e) {
		  this.entered = false;
	      repaint();
	  }
}