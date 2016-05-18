package net.launcher.components;

import java.awt.image.BufferedImage;

import net.launcher.utils.BaseUtils;

public class DragbuttonStyle
{
	public int hx = 0;
	public int hy = 0;
	public int hw = 0;
	public int hh = 0;
	public int cx = 0;
	public int cy = 0;
	public int cw = 0;
	public int ch = 0;
	public boolean visible = false;
	public BufferedImage texture;
	
	public DragbuttonStyle(int hx, int hy, int hw, int hh, int cx, int cy, int cw, int ch, String texture, boolean visible)
	{
		this.hx = hx;
		this.hy = hy;
		this.hw = hw;
		this.hh = hh;
		
		this.cx = cx;
		this.cy = cy;
		this.cw = cw;
		this.ch = ch;
		
		this.visible = visible;
		this.texture = BaseUtils.getLocalImage(texture);
	}
	
	public void apply(Dragbutton hide, Dragbutton close)
	{
		hide.setVisible(visible);
		hide.setBounds(hx, hy, hw, hh);
		
		close.setVisible(visible);
		close.setBounds(cx, cy, cw, ch);
		
		hide.img1 = texture.getSubimage(0, 0, texture.getWidth() / 3, texture.getHeight() / 2);
		hide.img2 = texture.getSubimage(texture.getWidth() / 3, 0, texture.getWidth() / 3, texture.getHeight() / 2);
		hide.img3 = texture.getSubimage(texture.getWidth() / 3 * 2, 0, texture.getWidth() / 3, texture.getHeight() / 2);
		
		close.img1 = texture.getSubimage(0, texture.getHeight() / 2, texture.getWidth() / 3, texture.getHeight() / 2);
		close.img2 = texture.getSubimage(texture.getWidth() / 3, texture.getHeight() / 2, texture.getWidth() / 3, texture.getHeight() / 2);
		close.img3 = texture.getSubimage(texture.getWidth() / 3 * 2, texture.getHeight() / 2, texture.getWidth() / 3, texture.getHeight() / 2);
	}
}