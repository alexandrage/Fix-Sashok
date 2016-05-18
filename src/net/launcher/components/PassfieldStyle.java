package net.launcher.components;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.border.Border;

import net.launcher.utils.BaseUtils;

public class PassfieldStyle
{
	public int x = 0;
	public int y = 0;
	public int w = 0;
	public int h = 0;
	public String fontName = BaseUtils.empty;
	public String echoChar = BaseUtils.empty;
	public float fontSize = 1F;
	public Color textColor;
	public Color caretColor;
	public BufferedImage texture;
	public Border border;
	
	public PassfieldStyle(int x, int y, int w, int h, String texture, String fontName, float fontSize, Color textColor, Color caretColor, String echoChar, Border border)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.fontName = fontName;
		this.fontSize = fontSize;
		this.textColor = textColor;
		this.caretColor = caretColor;
		this.echoChar = echoChar;
		this.texture = BaseUtils.getLocalImage(texture);
		this.border = border;
	}
	
	public void apply(Passfield pass)
	{
		pass.setBounds(x, y, w, h);
		pass.texture = texture;
		pass.setFont(BaseUtils.getFont(fontName, fontSize));
		pass.setCaretColor(caretColor);
		pass.setBackground(textColor);
		pass.setForeground(textColor);
		pass.setBorder(border);
	}
}