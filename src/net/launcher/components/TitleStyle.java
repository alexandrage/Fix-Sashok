package net.launcher.components;

import java.awt.Color;

import net.launcher.utils.BaseUtils;

public class TitleStyle {
	public int x = 0;
	public int y = 0;
	public int w = 0;
	public int h = 0;
	public String fontName = BaseUtils.empty;
	public float fontSize = 1F;
	public Color textColor;
	
	public TitleStyle(int x, int y, int w, int h, String fontName, float fontSize, Color textColor)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.fontName = fontName;
		this.fontSize = fontSize;
		this.textColor = textColor;
	}
	
	public void apply(Title title)
	{
		title.setBounds(x, y, w, h);
		title.setFont(BaseUtils.getFont(fontName, fontSize));
		title.setBackground(textColor);
		title.setForeground(textColor);
		title.tt = this;
	}
}
