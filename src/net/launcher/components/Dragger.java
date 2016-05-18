package net.launcher.components;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Dragger extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private int x = 0;
	private int y = 0;
	
	public Dragger()
	{
		setOpaque(false);
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(0, 10, 0, 10));
		addMouseMotionListener(new MouseMotionAdapter()
		{
			public void mouseDragged(MouseEvent e)
			{
				Frame.main.setLocation(e.getX() + Frame.main.getX() - x, e.getY() + Frame.main.getY() - y);
			}
		});
		addMouseListener(new MouseListener()
		{
			public void mousePressed(MouseEvent e)
			{
				x = e.getX();
				y = e.getY();
			}
			public void mouseClicked(MouseEvent event){}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0)  {}
			public void mouseReleased(MouseEvent arg0){}
		});
	}
}