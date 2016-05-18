package net.launcher.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPasswordField;

import net.launcher.run.Settings;
import net.launcher.utils.ImageUtils;

public class Passfield extends JPasswordField
{
	private static final long serialVersionUID = 1L;
	
	public BufferedImage texture;
	
	public Passfield()
	{
		setOpaque(false);
	}

	protected void paintComponent(Graphics maing)
	{
		Graphics2D g = (Graphics2D) maing.create();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(ImageUtils.genButton(getWidth(), getHeight(), texture), 0, 0, getWidth(), getHeight(), null);
		if(Settings.drawTracers)
		{
			g.setColor(Color.PINK);
			g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		}
		g.dispose();
		super.paintComponent(maing);
	}
}