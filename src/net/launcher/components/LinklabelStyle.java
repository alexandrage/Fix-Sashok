package net.launcher.components;

import java.awt.Color;

import net.launcher.utils.BaseUtils;

public class LinklabelStyle
{
	public int x = 0;
	public int y = 0;
	public int margin = 0;
	public String fontName = BaseUtils.empty;
	public float fontSize = 1F;
	public Color idleColor;
	public Color activeColor;
	
	public LinklabelStyle(int x, int y, int margin, String fontName, float fontSize, Color idleColor, Color activeColor)
	{
		this.x = x;
		this.y = y;
		this.fontName = fontName;
		this.fontSize = fontSize;
		this.idleColor = idleColor;
		this.activeColor = activeColor;
	}
	
	public void apply(LinkLabel link)
	{
		link.setBounds(x, y, 0, 0);
		link.idleColor = idleColor;
		link.activeColor = activeColor;
		link.setFont(BaseUtils.getFont(fontName, fontSize));
	}
}