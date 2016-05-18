package net.launcher.utils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;

public class ImageUtils
{
	public static BufferedImage genButton(int w, int h, BufferedImage img)
	{
		BufferedImage res = new BufferedImage(w, h, 2);
		BufferedImage left = img.getSubimage(0, 0, img.getWidth() / 3, img.getHeight());
		BufferedImage center = img.getSubimage(img.getWidth() / 3, 0, img.getWidth() / 3, img.getHeight());
		BufferedImage right = img.getSubimage(img.getWidth() / 3 * 2, 0, img.getWidth() / 3, img.getHeight());
		res.getGraphics().drawImage(left, 0, 0, left.getWidth(), left.getHeight(), null);
	try{res.getGraphics().drawImage(fill(center, w - left.getWidth() - right.getWidth(), h), left.getWidth(), 0, w - left.getWidth() - right.getWidth(), h, null);}catch(Exception e){}
		res.getGraphics().drawImage(right, w - right.getWidth(), 0, right.getWidth(), h, null);
		return res;
	}
	
	public static BufferedImage genPanel(int w, int h, BufferedImage img)
	{
		BufferedImage res = new BufferedImage(w, h, 2);
		int onew = img.getWidth() / 3;
		int oneh = img.getHeight() / 3;
		
		res.getGraphics().drawImage(img.getSubimage(0, 0, onew, oneh), 0, 0, onew, oneh, null);
		res.getGraphics().drawImage(img.getSubimage(onew * 2, 0, onew, oneh), w - onew, 0, onew, oneh, null);
		res.getGraphics().drawImage(img.getSubimage(0, oneh * 2, onew, oneh), 0, h - oneh, onew, oneh, null);
		res.getGraphics().drawImage(img.getSubimage(onew, oneh, onew * 2, oneh * 2), w - onew, h - oneh, onew, oneh, null);

	try{res.getGraphics().drawImage(fill(img.getSubimage(onew, 0, onew, oneh), w - onew * 2, oneh), onew, 0, w - onew * 2, oneh, null);}catch(Exception e){}
	try{res.getGraphics().drawImage(fill(img.getSubimage(0, oneh, onew, oneh), onew, h - oneh * 2), 0, oneh, onew, h - oneh * 2, null);}catch(Exception e){}
	try{res.getGraphics().drawImage(fill(img.getSubimage(onew, oneh * 2, onew, oneh), w - onew * 2, oneh), onew, h - oneh, w - onew * 2, oneh, null);}catch(Exception e){}
	try{res.getGraphics().drawImage(fill(img.getSubimage(onew * 2, oneh, onew, oneh), onew, h - oneh * 2), w - onew, oneh, onew, h - oneh * 2, null);}catch(Exception e){}
	try{res.getGraphics().drawImage(fill(img.getSubimage(onew, oneh, onew, oneh), w - onew * 2, h - oneh * 2), onew, oneh, w - onew * 2, h - oneh * 2, null);}catch(Exception e){}

		return res;
	}
	
	public static BufferedImage fill(BufferedImage texture, int w, int h)
	{
		int sizex = texture.getWidth();
		int sizey = texture.getHeight();
		BufferedImage img = new BufferedImage(w, h, 2);
		for(int x = 0;x <= w/sizex; x++) for(int y = 0; y <= h/sizey; y++) img.getGraphics().drawImage(texture,x*sizex,y*sizey,null);
		return img;
	}
	
	public static BufferedImage fillHoriz(BufferedImage texture, int w, int h)
	{
		int sizex = texture.getWidth();
		BufferedImage img = new BufferedImage(w, h, 2);
		for(int x = 0;x <= w/sizex; x++) img.getGraphics().drawImage(texture,x*sizex,0,sizex,texture.getHeight(),null);
		return img;
	}

	public static BufferedImage blurImage(BufferedImage image)
	{
		float ninth = 1.0f / 9.0f;
		float[] blurKernel = { ninth, ninth, ninth, ninth, ninth, ninth, ninth, ninth, ninth };
		Map<Key, Object> map = new HashMap<Key, Object>();
		map.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		RenderingHints hints = new RenderingHints(map);
		BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, blurKernel),
		ConvolveOp.EDGE_NO_OP, hints);
		return op.filter(image, null);
	}

	public static BufferedImage parseSkin(BufferedImage skinIMG)
	{
		BufferedImage result = new BufferedImage(128, 256, 2);
		Graphics g = result.getGraphics();
		int w = skinIMG.getWidth() / 64;
		int h = skinIMG.getHeight()/ 32;
		
		g.drawImage(skinIMG.getSubimage(w * 8,  h * 8,  w * 8, h * 8 ), 32, 0,   64, 64, null);//Голова
		g.drawImage(skinIMG.getSubimage(w * 20, h * 20, w * 8, h * 12), 32, 64,  64, 96, null);//Тело
		g.drawImage(skinIMG.getSubimage(w * 44, h * 20, w * 4, h * 12), 0 , 64,  32, 96, null);//Левая рука
		g.drawImage(skinIMG.getSubimage(w * 44, h * 20, w * 4, h * 12), 96, 64,  32, 96, null);//Правая рука
		g.drawImage(skinIMG.getSubimage(w * 4,  h * 20, w * 4, h * 12), 32, 160, 32, 96, null);//Левая нога
		g.drawImage(skinIMG.getSubimage(w * 4,  h * 20, w * 4, h * 12), 64, 160, 32, 96, null);//Правая нога
		g.drawImage(skinIMG.getSubimage(w * 40, h * 8,  w * 8, h * 8 ), 32, 0,   64, 64, null);//Голова
		
		return result;
	}
	
	public static BufferedImage sceenComponent(JComponent c)
	{
		int w = c.getWidth();
		int h = c.getHeight();
		BufferedImage img = new BufferedImage(w, h, 2);
		Graphics2D g = img.createGraphics();
		c.paint(g);
		g.dispose();
		return img;
	}
	
	public static BufferedImage getByIndex(BufferedImage all, int d, int i)
	{
		return all.getSubimage(d * i, 0, d, d);
	}

	public static BufferedImage parseCloak(BufferedImage cloakIMG)
	{
		BufferedImage result = new BufferedImage(128, 256, 2);
		Graphics g = result.getGraphics();
		int w = cloakIMG.getWidth() / 64;
		int h = cloakIMG.getHeight()/ 32;
		g.drawImage(cloakIMG.getSubimage(w, h,  w * 10, h * 16), 0, 0, 128, 256, null);
		return result;
	}
}
