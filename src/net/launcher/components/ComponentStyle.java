package net.launcher.components;

import java.awt.Color;
import javax.swing.JComponent;

import net.launcher.utils.BaseUtils;

public class ComponentStyle
{
	public int x = 0;
	public int y = 0;
	public int w = 0;
	public int h = 0;
	public String fontName = BaseUtils.empty;
	public float fontSize = 1F;
	public Color color;
	public boolean visible = false;
	
	public ComponentStyle(int x, int y, int w, int h, String fontName, float fontSize, Color color, boolean visible)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.fontName = fontName;
		this.fontSize = fontSize;
		this.color = color;
		this.visible = visible;
	}
	
	public void apply(JComponent comp)
	{
		comp.setVisible(visible);
		comp.setBounds(x, y, w, h);
		comp.setForeground(color);
		comp.setFont(BaseUtils.getFont(fontName, fontSize));
	}
}