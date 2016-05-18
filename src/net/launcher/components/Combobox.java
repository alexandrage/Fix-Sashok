package net.launcher.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

import net.launcher.MusPlay;
import net.launcher.run.Settings;
import net.launcher.utils.BaseUtils;
import static net.launcher.utils.ImageUtils.*;

public class Combobox extends JComponent implements MouseListener, MouseMotionListener
{
	private static final long serialVersionUID = 1L;
	public final String[] 	elements;
	public int initialy		= 0;
	
	private boolean entered = false;
	private boolean pressed = false;
	private int x 			= 0;
	private int y 			= 0;
	private int selected	= 0;
	
	public BufferedImage defaultTX;
	public BufferedImage rolloverTX;
	public BufferedImage pressedTX;
	public BufferedImage selectedTX;
	public BufferedImage panelTX;

	public Combobox(String[] elements, int y)
	{
		super();
		this.elements = elements;
		initialy = y;
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void paintComponent(Graphics gmain)
	{
		Graphics2D g = (Graphics2D) gmain;
		int w = getWidth();
		if(pressed)
		{
			g.drawImage(genButton(w, pressedTX.getHeight(), pressedTX), 0, getHeight() - pressedTX.getHeight(), w, pressedTX.getHeight(), null);
			int righth = pressedTX.getHeight() * (elements.length + 1);
			int righty = (initialy + pressedTX.getHeight()) - righth;
			
			if(getY() != righty || getHeight() != righth)
			{
				setLocation(getX(), righty);
				setSize(getWidth(), righth);
				y = getHeight();
				return;
			}
			
			g.drawImage(genPanel(w, getHeight() - pressedTX.getHeight(), panelTX), 0, 0, w, getHeight() - pressedTX.getHeight(), null);
			if(entered && y / pressedTX.getHeight() < elements.length)
			{
				g.drawImage(genButton(w, selectedTX.getHeight(), selectedTX), 0,
					y / pressedTX.getHeight() * pressedTX.getHeight()
				, w, pressedTX.getHeight(), null);
			}
			
			for(int i = 0; i < elements.length; i++)
			{
				g.drawString(elements[i], 5, selectedTX.getHeight() * (i+1) - (g.getFontMetrics().getHeight() / 2));
			}
			
			g.drawString(elements[selected], 5, selectedTX.getHeight() * (elements.length + 1) - (g.getFontMetrics().getHeight() / 2));
		} else if(entered)
		{
			int righth = pressedTX.getHeight();
			if(getY() != initialy || getHeight() != righth)
			{
				setLocation(getX(), initialy);
				setSize(getWidth(), righth);
				return;
			}
			g.drawImage(genButton(w, rolloverTX.getHeight(), rolloverTX), 0, 0, w, rolloverTX.getHeight(), null);
			g.drawString(elements[selected], 5, rolloverTX.getHeight() - (g.getFontMetrics().getHeight() / 2));
		} else
		{
			int righth = pressedTX.getHeight();
			if(getY() != initialy || getHeight() != righth)
			{
				setLocation(getX(), initialy);
				setSize(getWidth(), righth);
				return;
			}
			g.drawImage(genButton(w, defaultTX.getHeight(), defaultTX), 0, 0, w, defaultTX.getHeight(), null);
			g.drawString(elements[selected], 5, rolloverTX.getHeight() - (g.getFontMetrics().getHeight() / 2));
		}
		if(Settings.drawTracers)
		{
			g.setColor(Color.GREEN);
			g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		}
		g.dispose();
	}

	public void mouseClicked(MouseEvent e)
	{
		if(e.getButton() != MouseEvent.BUTTON1) return;
		if(pressed && y / pressedTX.getHeight() < elements.length)
		{
			selected = y / pressedTX.getHeight();
			entered = BaseUtils.contains(x, y, getX(), getY(), getWidth(), getHeight());
		}
		try {
			new MusPlay("click.mp3");
		} catch(Exception e1) {}
		pressed = !pressed;
		repaint();
	}

	public void mouseEntered(MouseEvent e)
	{
		entered = true;
		repaint();
	}

	public void mouseExited(MouseEvent e)
	{
		entered = false;
		repaint();
	}

	public void mousePressed(MouseEvent e)  {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseDragged(MouseEvent e)	{}
	
	public void mouseMoved(MouseEvent e)
	{
		y = e.getY();
		repaint();
	}
	
	public int getSelectedIndex()
	{
		return selected;
	}
	
	public String getSelected()
	{
		try
		{
			return elements[selected];
		} catch (Exception e)
		{
			return elements[0];
		}
	}
	
	public boolean setSelectedIndex(int i)
	{
		if(elements.length <= i) return false;
		selected = i;
		repaint();
		return true;
	}
	
	public boolean getPressed()
	{
		return pressed;
	}
}