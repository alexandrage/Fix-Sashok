package net.launcher.components;

import java.awt.Color;
import java.awt.image.BufferedImage;

import net.launcher.utils.BaseUtils;

public class ComboboxStyle
{
	public int x = 0;
	public int y = 0;
	public int w = 0;
	public int h = 0;
	public String fontName;
	public float fontSize = 1F;
	public Color color;
	public boolean visible = false;
	public Align align;
	public BufferedImage texture;
	
	public ComboboxStyle(int x, int y, int w, int h, String fontName, String texture, float fontSize, Color color, boolean visible, Align align)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.fontName = fontName;
		this.fontSize = fontSize;
		this.color = color;
		this.visible = visible;
		this.align = align;
		this.texture = BaseUtils.getLocalImage(texture);
	}
	
	public void apply(Combobox combo)
	{
		combo.setVisible(visible);
		combo.setBounds(x, y, w, h);
		combo.setForeground(color);
		combo.setFont(BaseUtils.getFont(fontName, fontSize));

		int comboboxh = texture.getHeight() / 7;
		int comboboxw = texture.getWidth();
		combo.defaultTX = texture.getSubimage(0, 0, comboboxw, comboboxh);
		combo.rolloverTX = texture.getSubimage(0, comboboxh, comboboxw, comboboxh);
		combo.pressedTX = texture.getSubimage(0, comboboxh * 2, comboboxw, comboboxh);
		combo.selectedTX = texture.getSubimage(0, comboboxh * 6, comboboxw, comboboxh);
		combo.panelTX = texture.getSubimage(0, comboboxh * 3, comboboxw, comboboxh * 3);
		
		combo.initialy = y;
	}
}