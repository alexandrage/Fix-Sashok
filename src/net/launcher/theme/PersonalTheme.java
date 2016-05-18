/* Данный класс отвечает за хранение координат всех компонентов личного кабинета лаунчера */

package net.launcher.theme;

import java.awt.Color;

import javax.swing.border.EmptyBorder;

import net.launcher.components.Align;
import net.launcher.components.ButtonStyle;
import net.launcher.components.ComponentStyle;
import net.launcher.components.TextfieldStyle;

public class PersonalTheme
{
	public static ComponentStyle	ugroup		= new ComponentStyle(20, 300, 140, -1, "font", 16F, Color.WHITE, true);
	
	public static int	  skinX			= 26;
	public static int	  skinY			= 170;
	public static int	  skinW			= 64;
	public static int	  skinH			= 128;
	public static int	  cloakX		= 110;
	public static int	  cloakY		= 170;
	public static int	  cloakW		= 40;
	public static int	  cloakH		= 60;
	
	public static ComponentStyle cloakPrice	  = new ComponentStyle(160, 250, -1, -1, "font", 14F, Color.GREEN, true);
	public static ComponentStyle iConomy	  = new ComponentStyle(280, 300, -1, -1, "font", 18F, Color.GREEN, true);
	public static ComponentStyle realmoney	  = new ComponentStyle(420, 300, -1, -1, "font", 18F, Color.GREEN, true);
	public static ComponentStyle prices		  = new ComponentStyle(190, 160, -1, -1, "font", 16F, Color.WHITE, true);
	
	public static ButtonStyle	 buyCloak	  = new ButtonStyle(180, 390, 140, 47, "font", "button", 16F, Color.decode("0xd4dc7b"), true, Align.CENTER);
	public static ButtonStyle	 changeskin	  = new ButtonStyle(180, 440, 140, 47, "font", "button", 16F, Color.decode("0xd4dc7b"), true, Align.CENTER);
	public static ButtonStyle	 buyVip		  = new ButtonStyle(20, 340, 140, 47, "font", "button", 16F, Color.decode("0xd4dc7b"), true, Align.CENTER);
	public static ButtonStyle	 buyPremium	  = new ButtonStyle(20, 390, 140, 47, "font", "button", 16F, Color.decode("0xd4dc7b"), true, Align.CENTER);
	public static ButtonStyle	 buyUnban	  = new ButtonStyle(20, 440, 140, 47, "font", "button", 16F, Color.decode("0xd4dc7b"), true, Align.CENTER);
	
	public static TextfieldStyle vaucher	  = new TextfieldStyle(340, 340, 140, 38, "textfield", "font", 16F, Color.WHITE, Color.WHITE, new EmptyBorder(0, 10, 0, 10));
	public static ButtonStyle	 vaucherButton= new ButtonStyle(340, 390, 140, 47, "font", "button", 16F, Color.decode("0xd4dc7b"), true, Align.CENTER);
	public static ButtonStyle	 buyVaucher	  = new ButtonStyle(340, 440, 140, 47, "font", "button", 16F, Color.decode("0xd4dc7b"), true, Align.CENTER);
	
	public static TextfieldStyle exchangeFrom = new TextfieldStyle(20, 495, 140, 38, "textfield", "font", 16F, Color.WHITE, Color.WHITE, new EmptyBorder(0, 10, 0, 10));
	public static TextfieldStyle exchangeTo	  = new TextfieldStyle(180, 495, 140, 38, "textfield", "font", 16F, Color.WHITE, Color.WHITE, new EmptyBorder(0, 10, 0, 10));
	public static ButtonStyle	 exchangeBtn  = new ButtonStyle(340, 490, 140, 47, "font", "button", 16F, Color.decode("0xd4dc7b"), true, Align.CENTER);
	
	public static ButtonStyle	 toGamePSL    = new ButtonStyle(180, 340, 140, 47, "font", "button", 16F, Color.decode("0xd4dc7b"), true, Align.CENTER);
}