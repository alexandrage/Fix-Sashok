package net.launcher.components;

import java.awt.image.BufferedImage;

import net.launcher.utils.BaseUtils;

public class PersonalContainer
{
	public BufferedImage skin;
	public BufferedImage cloak;
	
	public boolean canUploadSkin	= false;
	public boolean canUploadCloak	= false;
	public boolean canBuyVip		= false;
	public boolean canBuyPremium	= false;
	public boolean canBuyUnban		= false;
	public boolean canActivateVaucher=false;
	public boolean canExchangeMoney = false;
	
	public String  ugroup			="User";
	public String  dateofexpire		= BaseUtils.empty;
	
	public int 	   cloakPrice		= 0;
	public int 	   vipPrice			= 0;
	public int 	   premiumPrice		= 0;
	public int 	   unbanPrice		= 0;
	public int	   exchangeRate		= 0;
	
	public double  iconmoney		= 0.0D;
	public int	   realmoney		= 0;
	
	public String  jobname			= "Безработный"; 
	public int	   joblvl			= 0;
	public int 	   jobexp			= 0;
	
	
	public PersonalContainer(String[] s, BufferedImage skinImage, BufferedImage cloakImage)
	{
		String rights 	  = s[0];
		canUploadSkin	  = rights.charAt(0) == '1';
		canUploadCloak	  = rights.charAt(1) == '1';
		canBuyVip		  = rights.charAt(2) == '1';
		canBuyPremium	  = rights.charAt(3) == '1';
		canBuyUnban		  = rights.charAt(4) == '1';
		canActivateVaucher= rights.charAt(5) == '1';
		canExchangeMoney  = rights.charAt(6) == '1';
		iconmoney		  = Double.parseDouble(s[1] + "D");
		try
		{
			realmoney		  = Integer.parseInt(s[2]);
		} catch(Exception e){ realmoney = 0; }
		cloakPrice		  = Integer.parseInt(s[3]);
		vipPrice		  = Integer.parseInt(s[4]);
		premiumPrice	  = Integer.parseInt(s[5]);
		unbanPrice		  = Integer.parseInt(s[6]);
		exchangeRate	  = Integer.parseInt(s[7]);
		ugroup			  = s[8];
		dateofexpire	  = BaseUtils.unix2hrd(Long.parseLong(s[9]));
		
		jobname = s[10];
		joblvl = Integer.parseInt(s[11]);
		jobexp = Integer.parseInt(s[12]);
		
		skin = skinImage;
		cloak = cloakImage;
	}
}