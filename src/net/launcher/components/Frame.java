package net.launcher.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.ServerSocket;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import net.launcher.run.Settings;
import net.launcher.theme.Message;
import net.launcher.utils.BaseUtils;
import net.launcher.utils.GuardUtils;
import net.launcher.utils.ImageUtils;
import net.launcher.utils.ThemeUtils;
import net.launcher.utils.ThreadUtils;
import static net.launcher.utils.BaseUtils.*;

import com.sun.awt.AWTUtilities;

public class Frame extends JFrame implements ActionListener, FocusListener
{
	boolean b1 = false;
	boolean b2 = true;
	private static final long serialVersionUID = 1L;
	private static final Component Frame = null;
	public static String token = "null";
	public static boolean savetoken = false;
	
	public static Frame main;
    public Panel panel = new Panel(0);
	public Dragger dragger = new Dragger();
	public Title title = new Title();
	public static Button toGame = new Button(Message.Game);
	public static Button toAuth = new Button(Message.Auth);
	public static Button toLogout = new Button(Message.Logout);
	public static Button toPersonal = new Button(Message.Personal);
	public Button toOptions = new Button(Message.Options);
    public static Button toRegister = new Button(Message.Register);
	public JTextPane browser = new JTextPane();
	public JTextPane personalBrowser = new JTextPane();
	public JScrollPane bpane = new JScrollPane(browser);
	public JScrollPane personalBpane = new JScrollPane(personalBrowser);
	public static Textfield login = new Textfield();
	public static Passfield password = new Passfield();
	public Combobox servers = new Combobox(getServerNames(), 0);
	public Serverbar serverbar = new Serverbar();

	public LinkLabel[] links = new LinkLabel[Settings.links.length];

	public Dragbutton hide = new Dragbutton();
	public Dragbutton close = new Dragbutton();

	public Button update_exe = new Button(Message.update_exe);
	public Button update_jar = new Button(Message.update_jar);
	public Button update_no = new Button(Message.update_no);

	public Checkbox loadnews = new Checkbox(Message.loadnews);
    public Checkbox Music = new Checkbox(Message.Music);
	public Checkbox updatepr = new Checkbox(Message.updatepr);
	public Checkbox cleanDir = new Checkbox(Message.cleanDir);
	public Checkbox fullscreen = new Checkbox(Message.fullscreen);
	public Textfield memory = new Textfield();
                        
                        
    public Textfield loginReg = new Textfield();
    public Passfield passwordReg = new Passfield();
    public Passfield password2Reg = new Passfield();
    public Textfield mailReg = new Textfield();
    public Button okreg = new Button(Message.register);
    public Button closereg = new Button(Message.closereg);
                        
	public Button options_close = new Button(Message.options_close);

	public Button buyCloak = new Button(Message.buyCloak);
	public Button changeSkin = new Button(Message.changeSkin);
	public Textfield vaucher = new Textfield();
	public Button vaucherButton = new Button(Message.vaucherButton);
	public Button buyVaucher = new Button(Message.buyVaucher);
	public Textfield exchangeFrom = new Textfield();
	public Textfield exchangeTo = new Textfield();
	public Button exchangeButton= new Button(Message.exchangeButton);
	public Button buyVip = new Button(BaseUtils.empty);
	public Button buyPremium = new Button(BaseUtils.empty);
	public Button buyUnban = new Button(Message.buyUnban);
	public Button toGamePersonal = new Button(Message.GamePersonal);

	public Frame()
	{			
	    try {
	          ServerSocket socket = new ServerSocket(Integer.parseInt("65534"));
	          Socket soc = new Socket(socket);
	          soc.start();
	    } catch (IOException var2) {
	    	JOptionPane.showMessageDialog(Frame, "Запуск второй копии лаунчера невозможен!", "Лаунчер уже запущен", javax.swing.JOptionPane.ERROR_MESSAGE);
			try{
                Class<?> af = Class.forName("java.lang.Shutdown");
                Method m = af.getDeclaredMethod("halt0", int.class);
                m.setAccessible(true);
                m.invoke(null, 1);
            } catch (Exception e) { }
	    }
		
		//Подготовка окна
		setIconImage(BaseUtils.getLocalImage("favicon"));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(Color.DARK_GRAY);
		setForeground(Color.DARK_GRAY);
		setLayout(new BorderLayout());
		setUndecorated(Settings.customframe && BaseUtils.getPlatform() != 0);
		if(isUndecorated())
		AWTUtilities.setWindowOpaque(this, false);
		setResizable(false);

		for(int i = 0; i < links.length; i++)
		{
			String[] s = Settings.links[i].split("::");
			links[i] = new LinkLabel(s[0], s[1]);
			links[i].setEnabled(BaseUtils.checkLink(s[1]));
		}

		try
		{
			ThemeUtils.updateStyle(this);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		//Добавление слушателей
		toGame.addActionListener(this);
		toAuth.addActionListener(this);
		toLogout.addActionListener(this);
		toPersonal.addActionListener(this);
		toPersonal.setVisible(Settings.usePersonal);
		toOptions.addActionListener(this);
        toRegister.addActionListener(this);
		login.setText(Message.Login);
		login.addActionListener(this);
		login.addFocusListener(this);
		password.setEchoChar('*');
		passwordReg.setEchoChar('*');
		password2Reg.setEchoChar('*');
		password.addActionListener(this);
		password.addFocusListener(this);
		Focus.setInitialFocus(this, password);
		String pass = getPropertyString("password");
		if(pass == null || pass.equals("-"))
		{
			b1 = true;
			b2 = false;
		}
		login.setVisible(true);
		password.setVisible(b1);
		toGame.setVisible(b2);
		toPersonal.setVisible(b2 && Settings.usePersonal);
		toAuth.setVisible(b1);
		toLogout.setVisible(b2);
		toRegister.setVisible(b1);
		if(toGame.isVisible())
		{
			token = "token";
		}
		
		login.setEditable(b1);
		bpane.setOpaque(false);
		bpane.getViewport().setOpaque(false);
		if (Settings.drawTracers) {
			bpane.setBorder(BorderFactory.createLineBorder(Color.black));
		} else {
			bpane.setBorder(null);
		}

		personalBpane.setOpaque(false);
		personalBpane.getViewport().setOpaque(false);
		personalBpane.setBorder(null);

		personalBrowser.setOpaque(false);
		personalBrowser.setBorder(null);
		personalBrowser.setContentType("text/html");
		personalBrowser.setEditable(false);
		personalBrowser.setFocusable(false);
		personalBrowser.addHyperlinkListener(new HyperlinkListener()
		{
			public void hyperlinkUpdate(HyperlinkEvent e)
			{
				if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
				{
					openURL(e.getURL().toString());
				}
			}
		});

		browser.setOpaque(false);
		browser.setBorder(null);
		browser.setContentType("text/html");
		browser.setEditable(false);
		browser.setFocusable(false);
		browser.addHyperlinkListener(new HyperlinkListener()
		{
			public void hyperlinkUpdate(HyperlinkEvent e)
			{
				if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
				{
					if(Settings.useStandartWB) openURL(e.getURL().toString());
					else ThreadUtils.updateNewsPage(e.getURL().toString());
				}
			}
		});
		hide.addActionListener(this);
		close.addActionListener(this);
                
		update_exe.addActionListener(this);
		update_jar.addActionListener(this);
		update_no.addActionListener(this);
		servers.addMouseListener(new MouseListener()
		{
			public void mouseReleased(MouseEvent e){}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e)  {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e)
			{
				if(servers.getPressed() || e.getButton() != MouseEvent.BUTTON1) return;

				ThreadUtils.pollSelectedServer();
				setProperty("server", servers.getSelectedIndex());
			}
		});

		options_close.addActionListener(this);
        closereg.addActionListener(this);
        okreg.addActionListener(this);
		loadnews.addActionListener(this);
        Music.addActionListener(this);
		fullscreen.addActionListener(this);

		buyCloak.addActionListener(this);
		changeSkin.addActionListener(this);
		vaucherButton.addActionListener(this);
		buyVaucher.addActionListener(this);
		exchangeButton.addActionListener(this);
		buyVip.addActionListener(this);
		buyPremium.addActionListener(this);
		buyUnban.addActionListener(this);
		toGamePersonal.addActionListener(this);

		login.setText(getPropertyString("login"));
		servers.setSelectedIndex(getPropertyInt("server"));

		exchangeFrom.getDocument().addDocumentListener(new DocumentListener()
		{
			public void changedUpdate(DocumentEvent e)
			{
				warn();
			}
			public void removeUpdate(DocumentEvent e)
			{
				warn();
			}
			public void insertUpdate(DocumentEvent e)
			{
				warn();
			}

			public void warn()
			{
				try
				{
					int i = Integer.parseInt(exchangeFrom.getText());
					exchangeTo.setText(String.valueOf((long)i * (long)panel.pc.exchangeRate) + Message.exchange);
				} catch(Exception e){ exchangeTo.setText(Message.exchangeTo); }
			}
		});

		addAuthComp();
		addFrameComp();
		add(panel, BorderLayout.CENTER);

		pack();
		setLocationRelativeTo(null);
		validate();
		repaint();
		setVisible(true);
	}

	public void addFrameComp()
	{
		if(Settings.customframe)
		{
			panel.add(hide);
			panel.add(close);
			panel.add(dragger);
			panel.add(title);
		}
	}

	public void setAuthComp()
	{
		panel.type = 0;
		panel.timer.stop();
		panel.removeAll();
		addAuthComp();
		addFrameComp();
		repaint();
	}

	/** Добавление элементов авторизации*/
	public void addAuthComp()
	{
		panel.add(servers);
		panel.add(serverbar);
		for(LinkLabel link : links) panel.add(link);
		panel.add(toGame);
		panel.add(toAuth);
		panel.add(toLogout);
		panel.add(toPersonal);
		panel.add(toOptions);
        panel.add(toRegister);
		panel.add(login);
		panel.add(password);
		panel.add(bpane);
	}

	//Старт программы
	public static void start()
	{
		Thread ch = new Thread(new Runnable() {
		@Override
		public void run() {
			while (true) {
				GuardUtils.check();
				 try {
						Thread.sleep(30000);
				 } catch (InterruptedException e) {
						e.printStackTrace();
				 }
		    	 }
		    }
		});
		ch.start();
		try
		{
			send("****launcher****");
			try
			{
				send("Setting new LaF...");
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch(Exception e)
			{
				send("Fail setting LaF");
			}
			send("Running debug methods...");

			new Runnable()
			{
				public void run()
				{
					Settings.onStart();
				}
			}.run();

			main = new Frame();

			ThreadUtils.updateNewsPage(buildUrl("news.php"));
			ThreadUtils.pollSelectedServer();
			try
			{
				main.memory.setText(String.valueOf(getPropertyInt("memory", Settings.defaultmemory)));
				main.fullscreen.setSelected(getPropertyBoolean("fullscreen"));
				main.loadnews.setSelected(getPropertyBoolean("loadnews", true));
                main.Music.setSelected(getPropertyBoolean("Music", true));
			} catch(Exception e){}
		} catch(Exception e)
		{
			throwException(e, main);
		}
	}

	public static String jar;
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == hide) setExtendedState(ICONIFIED);
		if(e.getSource() == close || e.getSource() == update_no) System.exit(0);

		if(e.getSource() == update_exe)
		{
			jar = ".exe";
			new Thread() { public void run() { try
			{
				panel.type = 8;
				update_exe.setEnabled(false);
				update_no.setText(Message.update_no2);
				panel.repaint();
				BaseUtils.updateLauncher();
			} catch(Exception e1)
			{
				e1.printStackTrace();
				send("Error updating launcher!");
				update_no.setText(Message.update_no);
				update_exe.setEnabled(true);
				panel.type = 9;
				panel.repaint();
			}}}.start();
		}
		
		if(e.getSource() == update_jar)
		{
			jar = ".jar";
			new Thread() { public void run() { try
			{
				panel.type = 8;
				update_jar.setEnabled(false);
				update_no.setText(Message.update_no2);
				panel.repaint();
				BaseUtils.updateLauncher();
			} catch(Exception e1)
			{
				e1.printStackTrace();
				send("Error updating launcher!");
				update_no.setText(Message.update_no);
				update_jar.setEnabled(true);
				panel.type = 9;
				panel.repaint();
			}}}.start();
		}

		if(e.getSource() == toLogout)
		{
			setProperty("password", "-");
			setProperty("login", "");
			password.setVisible(true);
			toGame.setVisible(false);
			toPersonal.setVisible(false);
			toAuth.setVisible(true);
			toLogout.setVisible(false);
			toRegister.setVisible(true);
			token = "null";
			login.setEditable(true);
			login.setText(Message.Login);
			password.setText("");
			repaint();
		}

		if(e.getSource() == login || e.getSource() == password || e.getSource() == toGame || e.getSource() == toAuth || e.getSource() == toPersonal || e.getSource() == toGamePersonal)
		{
			boolean personal = false;
			if(e.getSource() == toPersonal) personal = true;
			setProperty("login", login.getText());
			setProperty("server", servers.getSelectedIndex());
			panel.remove(hide);
			panel.remove(close);
			BufferedImage screen = ImageUtils.sceenComponent(panel);
			panel.removeAll();
			panel.setAuthState(screen);
			ThreadUtils.auth(personal);
			addFrameComp();
		}

		if(e.getSource() == toOptions)
		{
			setOptions();
		}

		if(e.getSource() == toRegister)
		{
			setRegister();
		}             
                
		if(e.getSource() == options_close)
		{
			if(!memory.getText().equals(getPropertyString("memory")))
			{
				try
				{
					int i = Integer.parseInt(memory.getText());
					setProperty("memory", i);
				} catch(Exception e1){}
				restart();
			}
			setAuthComp();
		}

		if(e.getSource() == fullscreen || e.getSource() == loadnews || e.getSource() == Music)
		{
			setProperty("fullscreen", fullscreen.isSelected());
			setProperty("loadnews",   loadnews.isSelected());
            setProperty("Music",   Music.isSelected());
		}

		if(e.getSource() == buyCloak)
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(new SkinFilter(1));
			chooser.setAcceptAllFileFilterUsed(false);
			int i = chooser.showDialog(main, Message.buyCloak);

			if(i == JFileChooser.APPROVE_OPTION)
			{
				setLoading();
				ThreadUtils.upload(chooser.getSelectedFile(), 1);
			}
		}

		if(e.getSource() == changeSkin)
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(new SkinFilter(0));
			chooser.setAcceptAllFileFilterUsed(false);
			int i = chooser.showDialog(main, Message.changeSkin);

			if(i == JFileChooser.APPROVE_OPTION)
			{
				setLoading();
				ThreadUtils.upload(chooser.getSelectedFile(), 0);
			}
		}

		if(e.getSource() == vaucherButton)
		{
			setLoading();
			ThreadUtils.vaucher(vaucher.getText());
		}

		if(e.getSource() == okreg)
		{
                   setLoading();
                   ThreadUtils.register(loginReg.getText(), passwordReg.getText(), password2Reg.getText(), mailReg.getText());
		}      
		if(e.getSource() == closereg)
		{
			setAuthComp();
		}                
		if(e.getSource() == buyVaucher){
			openURL(Settings.buyVauncherLink);
		}

		if(e.getSource() == exchangeButton)
		{
			setLoading();
			ThreadUtils.exchange(exchangeFrom.getText());
		}

		if(e.getSource() == buyVip)
		{
			setLoading();
			ThreadUtils.buyVip(0);
		}

		if(e.getSource() == buyPremium)
		{
			setLoading();
			ThreadUtils.buyVip(1);
		}

		if(e.getSource() == buyUnban)
		{
			setLoading();
			ThreadUtils.unban();
		}
	}

	public void focusGained(FocusEvent e)
	{
		if(e.getSource() == login && login.getText().equals(Message.Login)) login.setText(empty);
	}

	public void focusLost(FocusEvent e)
	{
		if(e.getSource() == login && login.getText().equals(empty)) login.setText(Message.Login);
	}

	public void setUpdateComp(String version)
	{
		panel.removeAll();
		panel.setUpdateState(version);
		panel.add(update_exe);
		panel.add(update_jar);
		panel.add(update_no);
		addFrameComp();
		repaint();
	}

	public void setUpdateState()
	{
		panel.removeAll();
		panel.setUpdateStateMC();
		addFrameComp();
		repaint();
	}

	public void setRegister()
	{
		if(Settings.useRegister) {
		panel.remove(hide);
		panel.remove(close);
		BufferedImage screen = ImageUtils.sceenComponent(panel);
		panel.removeAll();
		panel.setRegister(screen);
		panel.add(loginReg);
        panel.add(passwordReg);
        panel.add(password2Reg);
        panel.add(mailReg);
        panel.add(okreg);
		panel.add(closereg);
		addFrameComp();
		repaint();
		} else {
			BaseUtils.openURL(Settings.RegisterUrl);
			repaint();
		}
	}

	public void setOptions()
	{
		panel.remove(hide);
		panel.remove(close);
		BufferedImage screen = ImageUtils.sceenComponent(panel);
		panel.removeAll();
		panel.setOptions(screen);
		panel.add(loadnews);
        panel.add(Music);
		panel.add(updatepr);
		panel.add(cleanDir);
		panel.add(fullscreen);
		panel.add(memory);
		panel.add(options_close);
		addFrameComp();
		repaint();
	}        
        
	public void setPersonal(PersonalContainer pc)
	{
		panel.removeAll();

		if(pc.canUploadCloak) panel.add(buyCloak);
		if(pc.canUploadSkin) panel.add(changeSkin);
		if(pc.canActivateVaucher)
		{
			panel.add(vaucher);
			panel.add(vaucherButton);
			panel.add(buyVaucher);
		}

		if(pc.canExchangeMoney)
		{
			panel.add(exchangeFrom);
			panel.add(exchangeTo);
			panel.add(exchangeButton);
		}

		if(pc.canBuyVip) panel.add(buyVip);
		if(pc.canBuyPremium) panel.add(buyPremium);

		if(pc.canBuyUnban) panel.add(buyUnban);

		buyVip.setText(Message.buyVip);
		buyVip.setEnabled(true);

		buyPremium.setText(Message.buyPremium);
		buyPremium.setEnabled(true);

		if(pc.ugroup.equals("Banned"))
		{
			buyPremium.setEnabled(false);
			buyVip.setEnabled(false);
		} else if(pc.ugroup.equals("VIP"))
		{
			buyVip.setText(Message.buyVipN);
			buyPremium.setEnabled(false);
			buyUnban.setEnabled(false);
		} else if(pc.ugroup.equals("Premium"))
		{
			buyPremium.setText(Message.buyPremiumN);
			buyVip.setEnabled(false);
			buyUnban.setEnabled(false);
		} else if(pc.ugroup.equals("User"))
		{
			buyUnban.setEnabled(false);
		}

		panel.add(toGamePersonal);

		panel.setPersonalState(pc);
		addFrameComp();
		repaint();
	}

	public void setLoading()
	{
		panel.remove(hide);
		panel.remove(close);
		BufferedImage screen = ImageUtils.sceenComponent(panel);
		panel.removeAll();
		panel.setLoadingState(screen, Message.Loading);
		addFrameComp();
	}

	public void setError(String s)
	{
		panel.removeAll();
		panel.setErrorState(s);
		addFrameComp();
	}
}