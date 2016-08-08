package net.launcher.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.Timer;

import net.launcher.run.Settings;
import net.launcher.theme.ErrorTheme;
import net.launcher.theme.Message;
import net.launcher.theme.OptionsTheme;
import net.launcher.theme.UpdateTheme;
import net.launcher.utils.BaseUtils;
import net.launcher.utils.ThreadUtils;
import net.launcher.utils.UpdaterThread;
import static net.launcher.components.Files.*;
import static net.launcher.theme.LoginTheme.*;
import static net.launcher.theme.OptionsTheme.*;
import static net.launcher.theme.RegTheme.*;
import static net.launcher.theme.PersonalTheme.*;
import static net.launcher.theme.UpdaterTheme.*;
import static net.launcher.theme.DraggerTheme.*;
import static net.launcher.utils.ImageUtils.*;

public class Panel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	public static BufferedImage background = BaseUtils.getLocalImage("background");
	public static BufferedImage background_personal = BaseUtils.getLocalImage("background_personal");
	public static BufferedImage background_dialog = BaseUtils.getLocalImage("background_dialog");
	public static BufferedImage background_download = BaseUtils.getLocalImage("background_download");
	public static BufferedImage bar = BaseUtils.getLocalImage("bar");
	public static BufferedImage bar_label = BaseUtils.getLocalImage("bar_label");
	public static BufferedImage extpanel = BaseUtils.getLocalImage("extpanel");

	public int type;
	public BufferedImage tmpImage;
	public String tmpString = BaseUtils.empty;
	public Color tmpColor;
	public Timer timer;
	public PersonalContainer pc;
	
	private int tindex = 0;
	
	public Panel(final int type)
	{
		setOpaque(false);
		setLayout(null);
		setDoubleBuffered(true);
		setBorder(null);
		setFocusable(false);
		this.type = type;
	}
	
	public void paintComponent(final Graphics gmain)
	{
		Graphics2D g = (Graphics2D) gmain;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		try
		{
			g.setColor(tmpColor);
		} catch (Exception e) {}
		if(type == 0) g.drawImage(background, 0, 0, getWidth(), getHeight(), null); else
		if(type == 1)
		{
			g.drawImage(tmpImage, 0, 0, getWidth(), getHeight(), null);
			g.drawImage(getByIndex(wait, 128, tindex), getWidth() / 2 - 64, getHeight() / 2 - 64, 128, 128, null);
			g.drawString(tmpString, getWidth() / 2 - g.getFontMetrics().stringWidth(tmpString) / 2, getHeight() / 2 + 80);
		} else if(type == 2 || type == 8 || type == 9)
		{
			g.setFont(g.getFont().deriveFont(fonttitlesize));
			g.drawImage(background_dialog, 0, 0, getWidth(), getHeight(), null);
			g.drawString(Message.update, getWidth() / 2 - g.getFontMetrics().stringWidth(Message.update) / 2, UpdateTheme.stringsY);
			g.setFont(g.getFont().deriveFont(fontbasesize));
			g.drawString(Message.str1, UpdateTheme.stringsX, UpdateTheme.stringsY + 20);
			g.drawString(Message.str2, UpdateTheme.stringsX, UpdateTheme.stringsY + 40);
			g.drawString(Message.str3, UpdateTheme.stringsX, UpdateTheme.stringsY + 60);
			g.drawString(Message.str4, UpdateTheme.stringsX, UpdateTheme.stringsY + 80);
			g.drawString(Message.str5, UpdateTheme.stringsX, UpdateTheme.stringsY + 100);
			g.drawString(Message.str6.replace("%%", Settings.masterVersion), UpdateTheme.stringsX, UpdateTheme.stringsY + 120);
			g.drawString(Message.str7.replace("%%", tmpString), UpdateTheme.stringsX, UpdateTheme.stringsY + 140);
			if(type == 8 || type == 9)
			{
				g.setColor(Color.RED);
				g.drawString(type == 8 ? Message.str8 : Message.str9, UpdateTheme.stringsX, UpdateTheme.stringsY + 160);
			}
		} else if(type == 3)
		{
			g.setFont(g.getFont().deriveFont(fonttitlesize));
			g.setColor(Color.BLACK);
			g.drawImage(background_dialog, 0, 0, getWidth(), getHeight(), null);
			g.drawString(Message.messerr, getWidth() / 2 - g.getFontMetrics().stringWidth(Message.messerr) / 2, ErrorTheme.stringsY);
			g.setFont(g.getFont().deriveFont(fontbasesize));
			g.drawString(Message.err1, ErrorTheme.stringsX, ErrorTheme.stringsY + 20);
			g.setFont(g.getFont().deriveFont(12F));
			for(int i = 0; i < tmpString.split("<:>").length; i++)
			g.drawString(Message.err2.replace("%%", tmpString.split("<:>")[i]), ErrorTheme.stringsX, ErrorTheme.stringsY + 40 + (20 * i));
		} else if(type == 4)
		{	
			g.drawImage(background_download, 0, 0, getWidth(), getHeight(), null);
			UpdaterThread t = ThreadUtils.updaterThread;
			
			int leftTime = 0;
	    try	{	leftTime = (int) ((t.totalsize - t.currentsize) / (t.downloadspeed * 100)); } catch(Exception e){}
			
			g.setFont(updaterDesc.font);
			g.setColor(updaterDesc.color);
			
			g.drawString(Message.currentfile.replace("%%", t.currentfile.substring(t.currentfile.lastIndexOf(File.separator)+1)), stringsX, stringsY);
			g.drawString(Message.totalsize.replace("%%", Long.toString(t.totalsize)), stringsX, stringsY + 20);
			g.drawString(Message.currentsize.replace("%%", Long.toString(t.currentsize)), stringsX, stringsY + 40);
			g.drawString(Message.downloadspeed.replace("%%", Long.toString(t.downloadspeed)), stringsX, stringsY + 60);
			g.drawString(Message.McDir.replace("%%", BaseUtils.getMcDir().getAbsolutePath().substring(BaseUtils.getMcDir().getAbsolutePath().lastIndexOf(File.separator)+1)), stringsX, stringsY + 80);
			g.drawString(Message.state.replace("%%", t.state), stringsX, stringsY + 100);
			g.drawString(Message.leftTime.replace("%%", Long.toString(leftTime)), stringsX, stringsY + 120);
			
			if(t.error) return;
			BufferedImage img = genButton(loadbarW, loadbarH, bar);
			try
			{
				int percentw = (int)(t.procents * loadbarW / 100);
				g.drawImage(img.getSubimage(0, 0, percentw, loadbarH), loadbarX, loadbarY, null);
				g.drawImage(bar_label, (loadbarX + percentw) - (bar_label.getWidth() / 2)-10, loadbarY - bar_label.getHeight(), null);
				g.drawString(t.procents + "%", (loadbarX + percentw) - (g.getFontMetrics().stringWidth(t.procents + "%") / 2), loadbarY - (bar_label.getHeight() / 2));
			} catch(Exception e){}
		} else if(type == 5)
		{
			g.drawImage(tmpImage, 0, 0, getWidth(), getHeight(), null);
			g.drawImage(genPanel(panelOpt.w, panelOpt.h, extpanel), panelOpt.x, panelOpt.y, panelOpt.w, panelOpt.h, null);
			g.setFont(g.getFont().deriveFont(fonttitlesize));
			g.setColor(OptionsTheme.memory.textColor);
			g.drawString(Message.options, titleX, titleY);
			g.setFont(g.getFont().deriveFont(fontbasesize));
			g.drawString(Message.memory, memory.x, memory.y - 5);
		} else if(type == 55)
		{
			g.drawImage(tmpImage, 0, 0, getWidth(), getHeight(), null);
			g.drawImage(genPanel(panelOpt.w, panelOpt.h, extpanel), panelOpt.x, panelOpt.y, panelOpt.w, panelOpt.h, null);
			g.setFont(g.getFont().deriveFont(fonttitlesize));
			g.setColor(OptionsTheme.memory.textColor);
			g.drawString(Message.register, titleRegX, titleRegY);
                        
            /***************************************************************/
			g.setFont(g.getFont().deriveFont(fontbasesize));
            String textloginReg1 = Message.textloginReg1;
			g.drawString(textloginReg1, textloginReg.x-(g.getFontMetrics().stringWidth(textloginReg1)), textloginReg.y + 18);
            String textpasswordReg1 = Message.textpasswordReg1;
			g.drawString(textpasswordReg1, textpasswordReg.x-(g.getFontMetrics().stringWidth(textpasswordReg1)), textpasswordReg.y + 18);
            String textpassword2Reg1 = Message.textpassword2Reg1;
			g.drawString(textpassword2Reg1, textpassword2Reg.x-(g.getFontMetrics().stringWidth(textpassword2Reg1)), textpassword2Reg.y + 18);
            String textmailReg1 = Message.textmailReg1;
			g.drawString(textmailReg1, textmailReg.x-(g.getFontMetrics().stringWidth(textmailReg1)), textmailReg.y + 18);
		} else if(type == 6)
		{
			g.drawImage(background_personal, 0, 0, getWidth(), getHeight(), null);
			g.drawImage(pc.skin, skinX, skinY, skinW, skinH, null);
		    if (Settings.drawTracers)
		    {
		    	g.setColor(Color.GREEN);
		    	g.drawRect(skinX, skinY, skinW - 1, skinH - 1);
		    }
			g.drawImage(pc.cloak, cloakX, cloakY, cloakW, cloakH, null);
		    if (Settings.drawTracers)
		    {
		    	g.setColor(Color.GREEN);
		    	g.drawRect(cloakX, cloakY, cloakW - 1, cloakH - 1);
		    }
			
			String ugroupLBL = pc.ugroup.equals("User") ? Message.user : pc.ugroup.equals("VIP") ? Message.vip : pc.ugroup.equals("Banned") ? Message.ban : Message.prem;
			g.setColor(ugroup.color);
			g.setFont(BaseUtils.getFont(ugroup.fontName, ugroup.fontSize));
			g.drawString(ugroupLBL, ugroup.x + (ugroup.w / 2 - g.getFontMetrics().stringWidth(ugroupLBL) / 2), ugroup.y + g.getFontMetrics().getHeight());
			
			if(pc.canUploadCloak)
			{
				g.setColor(cloakPrice.color);
				g.setFont(BaseUtils.getFont(cloakPrice.fontName, cloakPrice.fontSize));
				
				String cloakPriceSTR = Message.realmoney.replace("%%", Long.toString(pc.cloakPrice));
				
				g.drawString(cloakPriceSTR, cloakPrice.x - g.getFontMetrics().stringWidth(cloakPriceSTR), cloakPrice.y + g.getFontMetrics().getHeight());
			}
			
			String iconmoney = Message.iconmoney.replace("%%", Double.toString(pc.iconmoney));
			g.setColor(iConomy.color);
			g.setFont(BaseUtils.getFont(iConomy.fontName, iConomy.fontSize));
			g.drawString(iconmoney, iConomy.x - g.getFontMetrics().stringWidth(iconmoney), iConomy.y + g.getFontMetrics().getHeight());
			
			String realmoneySTR = Message.realmoney.replace("%%", Long.toString(pc.realmoney));
			g.setColor(realmoney.color);
			g.setFont(BaseUtils.getFont(realmoney.fontName, realmoney.fontSize));
			g.drawString(realmoneySTR, realmoney.x - g.getFontMetrics().stringWidth(realmoneySTR), realmoney.y + g.getFontMetrics().getHeight());
			
			g.setColor(prices.color);
			g.setFont(BaseUtils.getFont(prices.fontName, prices.fontSize));
			
			int j = 0;
			if(pc.canBuyVip)
			{
				g.drawString(Message.vipPrice.replace("%%", Long.toString(pc.vipPrice)), prices.x, prices.y + g.getFontMetrics().getHeight() * (j + 1)); j++;
			}
			
			if(pc.canBuyPremium)
			{
				g.drawString(Message.premiumPrice.replace("%%", Long.toString(pc.premiumPrice)), prices.x, prices.y + g.getFontMetrics().getHeight() * (j + 1)); j++;
			}
			
			if(pc.canBuyUnban)
			{
				g.drawString(Message.unbanPrice.replace("%%", Long.toString(pc.unbanPrice)), prices.x, prices.y + g.getFontMetrics().getHeight() * (j + 1)); j++;
			}
			
			if(pc.canExchangeMoney)
			{
				g.drawString(Message.exchangeRate.replace("%%", Long.toString(pc.exchangeRate)), prices.x, prices.y + g.getFontMetrics().getHeight() * (j + 1)); j++;
			}
			
			if(!pc.dateofexpire.contains("01.01.1970"))
			{
				g.drawString(Message.dateofexpire.replace("%%", pc.dateofexpire), prices.x, prices.y + g.getFontMetrics().getHeight() * (j + 1)); j++;
			}
			
			if(pc.jobexp != -1 && pc.joblvl != -1)
			{
				g.drawString(Message.jobname.replace("%%", pc.jobname), prices.x, prices.y + g.getFontMetrics().getHeight() * (j + 1)); j++;
				g.drawString(Message.joblvl.replace("%%", Integer.toString(pc.joblvl)), prices.x, prices.y + g.getFontMetrics().getHeight() * (j + 1)); j++;
				g.drawString(Message.jobexp.replace("%%", Integer.toString(pc.jobexp)), prices.x, prices.y + g.getFontMetrics().getHeight() * (j + 1)); j++;
			}
			
		} else if(type == 7)
		{
			g.setFont(g.getFont().deriveFont(fonttitlesize));
			g.drawImage(background_dialog, 0, 0, getWidth(), getHeight(), null);
		}
		
		if(Settings.drawTracers)
		{
			g.setColor(Color.ORANGE);
			g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		}
	}
	
	public void setAuthState(BufferedImage screen)
	{
		reset();
		tmpImage = screen;
		tmpString = Message.tmpString;
		tmpColor = Color.WHITE;
		type = 1;
		timer = new Timer(50, new ActionListener()
		{
			boolean used = false;
			
			public void actionPerformed(ActionEvent e)
			{
				tindex++;
				if(!used)
				{
					if(tindex > 10) used = true;
					tmpImage.getGraphics().drawImage(getByIndex(colors, 1, 0), 0, dragger.h, getWidth(), getHeight() - dragger.h, null);
				}
				if(tindex == 12) tindex = 0;
				repaint();
			}
		});
		timer.start();
	}
	
	public void reset()
	{
		if(timer != null) timer.stop();
		timer = null;
		tmpImage = null;
		tmpColor = Color.WHITE;
		tmpString = null;
		tindex = 0;
	}

	public void setUpdateState(String version)
	{
		reset();
		tmpString = version;
		type = 2;
	}
	
	public void setUpdateStateMC()
	{
		reset();
		type = 4;
		timer = new Timer(50, new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				repaint();
			}
		});
		timer.start();
	}

	public void setRegister(BufferedImage screen)
	{
		reset();
		tmpImage = screen;
		type = 55;
		timer = new Timer(50, new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				tindex++;
				if(tindex > 10) timer.stop();
				tmpImage.getGraphics().drawImage(getByIndex(colors, 1, 0), 0, dragger.h, getWidth(), getHeight() - dragger.h, null);
				repaint();
			}
		});
		timer.start();
	}        
        
	public void setOptions(BufferedImage screen)
	{
		reset();
		tmpImage = screen;
		type = 5;
		timer = new Timer(50, new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				tindex++;
				if(tindex > 10) timer.stop();
				tmpImage.getGraphics().drawImage(getByIndex(colors, 1, 0), 0, dragger.h, getWidth(), getHeight() - dragger.h, null);
				repaint();
			}
		});
		timer.start();
	}
	
	public void setPersonalState(PersonalContainer pc)
	{
		reset();
		this.pc = pc;
		type = 6;
	}
	
	public void setLoadingState(BufferedImage screen, String s)
	{
		reset();
		tmpImage = screen;
		tmpString = s;
		tmpColor = Color.WHITE;
		type = 1;
		timer = new Timer(50, new ActionListener()
		{
			boolean used = false;
			
			public void actionPerformed(ActionEvent e)
			{
				tindex++;
				if(!used)
				{
					if(tindex > 10) used = true;
					tmpImage.getGraphics().drawImage(getByIndex(colors, 1, 0), 0, dragger.h, getWidth(), getHeight() - dragger.h, null);
				}
				if(tindex == 12) tindex = 0;
				repaint();
			}
		});
		timer.start();
	}

	public void setErrorState(String s)
	{
		reset();
		type = 3;
		tmpString = s;
		repaint();
	}
}