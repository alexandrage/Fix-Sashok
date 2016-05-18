package net.launcher.components;

import java.awt.Color;

import net.launcher.utils.BaseUtils;

public class ServerbarStyle
{
	public int x = 0;
	public int y = 0;
	public int w = 0;
	public int h = 0;
	public String fontName = BaseUtils.empty;
	public float fontSize = 1F;
	public Color textColor;
	public boolean useIcon = true;
	
	public ServerbarStyle(int x, int y, int w, int h, String fontName, float fontSize, Color textColor, boolean useIcon)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.fontName = fontName;
		this.fontSize = fontSize;
		this.textColor = textColor;
		this.useIcon = useIcon;
	}
	
	public void apply(Serverbar serverbar)
	{
		serverbar.setBounds(x, y, w, h);
		serverbar.setFont(BaseUtils.getFont(fontName, fontSize));
		serverbar.setBackground(textColor);
		serverbar.setForeground(textColor);
		serverbar.sb = this;
	}
}