package net.launcher.theme;

import java.awt.Color;

import javax.swing.border.EmptyBorder;

import net.launcher.components.Align;
import net.launcher.components.ButtonStyle;
import net.launcher.components.ComponentStyle;
import net.launcher.components.PassfieldStyle;
import net.launcher.components.TextfieldStyle;

public class RegTheme
{
    public static TextfieldStyle	loginReg		= new TextfieldStyle(147, 162, 220, 38, "textfield", "font", 16F, Color.decode("0xd4dc7b"), Color.WHITE, new EmptyBorder(0, 10, 0, 10));
	public static PassfieldStyle	passwordReg		= new PassfieldStyle(147, 208, 220, 38, "textfield", "font", 16F, Color.decode("0xd4dc7b"), Color.WHITE, "*", new EmptyBorder(0, 10, 0, 10));
	public static PassfieldStyle	password2Reg    = new PassfieldStyle(147, 254, 220, 38, "textfield", "font", 16F, Color.decode("0xd4dc7b"), Color.WHITE, "*", new EmptyBorder(0, 10, 0, 10));
	public static TextfieldStyle	mailReg		    = new TextfieldStyle(147, 300, 220, 38, "textfield", "font", 16F, Color.decode("0xd4dc7b"), Color.WHITE, new EmptyBorder(0, 10, 0, 10));
	
	public static ComponentStyle textloginReg		  = new ComponentStyle(145, 168, -1, -1, "font", 16F, Color.WHITE, true);
	public static ComponentStyle textpasswordReg      = new ComponentStyle(145, 212, -1, -1, "font", 16F, Color.WHITE, true);
	public static ComponentStyle textpassword2Reg     = new ComponentStyle(145, 258, -1, -1, "font", 16F, Color.WHITE, true);
	public static ComponentStyle textmailReg		  = new ComponentStyle(145, 304, -1, -1, "font", 16F, Color.WHITE, true);
	        
    public static ButtonStyle	closereg		= new ButtonStyle	(80, 360, 150, 47, "font", "button", 16F, Color.decode("0xd4dc7b"), true, Align.CENTER);
	public static ButtonStyle	okreg		    = new ButtonStyle	(280, 360, 150, 47, "font", "button", 16F, Color.decode("0xd4dc7b"), true, Align.CENTER);
	
	public static int titleRegX 		= 190;
	public static int titleRegY 		= 150;
}