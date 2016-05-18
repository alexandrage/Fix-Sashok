package net.launcher.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JCheckBox;

import net.launcher.run.Settings;

public class Checkbox extends JCheckBox
{
	private static final long serialVersionUID = 1L;
	
	public BufferedImage defaultTX;
	public BufferedImage rolloverTX;
	public BufferedImage selectedTX;
	public BufferedImage selectedRolloverTX;

	public Checkbox(String string)
	{
		super(string);
		setOpaque(false);
		setFocusable(false);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(Settings.drawTracers)
		{
			g.setColor(Color.BLUE);
			g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		}
	}
}