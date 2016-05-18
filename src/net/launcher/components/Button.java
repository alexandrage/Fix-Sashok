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
import net.launcher.utils.ImageUtils;

public class Button extends JButton implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;
    private boolean entered = false;
    private boolean pressed = false;
    public BufferedImage defaultTX;
    public BufferedImage rolloverTX;
    public BufferedImage pressedTX;
    public BufferedImage lockedTX;

public Button(String text) {
	addMouseListener(this);
	addMouseMotionListener(this);
	setText(text);
	setBorderPainted(false);
	setContentAreaFilled(false);
	setFocusPainted(false);
	setOpaque(false);
	setFocusable(false);
	setCursor(Cursor.getPredefinedCursor(12));
}

protected void paintComponent(Graphics maing) {
	Graphics2D g = (Graphics2D)maing.create();
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	int w = getWidth();
	int h = getHeight();

	if (!isEnabled()) {
		g.drawImage(ImageUtils.genButton(w, h, this.lockedTX), 0, 0, w, h, null);
	}
	if (this.entered && !this.pressed) {
		g.drawImage(ImageUtils.genButton(w, h, this.rolloverTX), 0, 0, w, h, null);
	}
	if (!this.entered) {
		g.drawImage(ImageUtils.genButton(w, h, this.defaultTX), 0, 0, w, h, null);
	}
	if ((this.pressed) && (this.entered)) {
		this.entered = false;
		try {
			new MusPlay("click.mp3");
		} catch(Exception e) {}
		g.drawImage(ImageUtils.genButton(w, h, this.pressedTX), 0, 0, w, h, null);
		this.pressed = false;
	}

    if (Settings.drawTracers)
    {
    	g.setColor(Color.RED);
    	g.drawRect(0, 0, w - 1, h - 1);
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