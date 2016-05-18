package net.launcher.theme;

import java.awt.Color;
import javax.swing.border.EmptyBorder;

import net.launcher.components.Align;
import net.launcher.components.ButtonStyle;
import net.launcher.components.CheckboxStyle;
import net.launcher.components.ComponentStyle;
import net.launcher.components.TextfieldStyle;

public class OptionsTheme {	
	
	public static ComponentStyle    panelOpt	= new ComponentStyle(53, 128, 400, 300, "font", 16F, Color.decode("0xA67A53"), true);
	
	public static CheckboxStyle		loadnews	= new CheckboxStyle(600, 150, 300, 23, "font", "checkbox", 16F, Color.decode("0xA67A53"), true);
    public static CheckboxStyle		Music	        = new CheckboxStyle(147, 182, 300, 23, "font", "checkbox", 16F, Color.decode("0xA67A53"), true);
	public static CheckboxStyle		updatepr	= new CheckboxStyle(147, 207, 300, 23, "font", "checkbox", 16F, Color.decode("0xA67A53"), true);
	public static CheckboxStyle		cleandir	= new CheckboxStyle(147, 232, 300, 23, "font", "checkbox", 16F, Color.decode("0xA67A53"), true);
	public static CheckboxStyle		fullscrn	= new CheckboxStyle(147, 257, 300, 23, "font", "checkbox", 16F, Color.decode("0xA67A53"), true);
	public static CheckboxStyle		offline		= new CheckboxStyle(147, 282, 300, 23, "font", "checkbox", 16F, Color.decode("0xA67A53"), true);
	public static TextfieldStyle	memory		= new TextfieldStyle(147, 307, 222, 38, "textfield", "font", 16F, Color.decode("0xA67A53"), Color.decode("0xA67A53"), new EmptyBorder(0, 10, 0, 10));
	public static ButtonStyle		close		= new ButtonStyle(178, 360, 150, 47, "font", "button", 16F, Color.decode("0xd4dc7b"), true, Align.CENTER);
	
	public static FontBundle		memoryDesc	= new FontBundle("font", 16F, Color.DARK_GRAY);
	
	public static int titleX 		= 212;
	public static int titleY 		= 165;
}