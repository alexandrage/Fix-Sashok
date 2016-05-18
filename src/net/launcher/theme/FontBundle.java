package net.launcher.theme;

import java.awt.Color;
import java.awt.Font;

import net.launcher.utils.BaseUtils;

public class FontBundle
{
	public Font font;
	public Color color;
	
	public FontBundle(String name, float size, Color color)
	{
		this.font = BaseUtils.getFont(name, size);
		this.color = color;
	}
}
