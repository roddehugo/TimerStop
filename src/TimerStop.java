import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;
import javax.swing.*;
/*
 * Ecrit par Sarathai � 2008
 */


public class TimerStop implements ActionListener, ChangeListener, FocusListener
{
	JFrame fr;					// Variables
	JPanel panel, panel2, panel3, comp, filp, minp, butp, optp, quip;
	JTextField tfm, tff;
	JFileChooser jfc;
	JCheckBox close, exit, defaultPrg, other;
	ButtonGroup grb, grb2;
	JButton ok, st, quit, suppr;
	JLabel tim, file, min, line;
	int minute = 0, seconde = 0, i;
	ActionListener tache_timer;
	Timer timer1;
	JMenuBar menu;
	JMenu partie, help,config;
	JMenuItem quitter, play, stop, appos, aide,del,modify;
	String commande, user,s,commandedef,commande2, prg, texte="";
	File f = new File(System.getProperty("user.home")+ System.getProperty("file.separator")+ ".defaultplayer (used by TimerStop).txt");



	public TimerStop()
	{
		if(!f.exists()) {
			setDefaultPlayer();
		}
		fr = new JFrame();
		fr.setVisible(true);					// Instance de la JFrame
		fr.setTitle("TimerStop v2.0");
		fr.setSize(575,620);
		fr.setLocationRelativeTo(null);
		fr.setResizable(true);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menu = new JMenuBar();			// Le menu et les ActionListeners

		partie = new JMenu("Menu");

		play = new JMenuItem("Démarrer");
		play.addActionListener(this);

		stop = new JMenuItem("Arréter");
		stop.addActionListener(this);

		quitter = new JMenuItem("Quitter");
		quitter.addActionListener(this);
		
		config = new JMenu("Options");
		
		del = new JMenuItem("Supprimer le lecteur par défaut");
		del.addActionListener(this);
		
		modify = new JMenuItem("Modifier le lecteur par défaut");
		modify.addActionListener(this);
		
		help = new JMenu("?");

		appos = new JMenuItem("A Propos");
		appos.addActionListener(this);

		aide = new JMenuItem("Aide");
		aide.addActionListener(this);


		menu.add(partie);
		partie.add(play);
		partie.add(stop);
		partie.addSeparator();
		partie.add(quitter);
		menu.add(config);
		config.add(modify);
		config.add(del);
		menu.add(help);
		help.add(aide);
		help.addSeparator();
		help.add(appos);
		fr.setJMenuBar(menu);

		// raccourcis clavier

		play.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.getKeyText(KeyEvent.VK_F1)));  
		stop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.getKeyText(KeyEvent.VK_F2)));
		aide.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.getKeyText(KeyEvent.VK_F3)));                 
		appos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.getKeyText(KeyEvent.VK_F4)));
		quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.getKeyText(KeyEvent.VK_F5)));
		
		s = defaultPlayer();
		
		fr.setContentPane(buildContentPane());
		fr.getRootPane().setDefaultButton(ok);
		fr.setVisible(true);
	}




	private JPanel buildContentPane()		// Contenu de la JFrame
	{
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.white);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		panel2 = new JPanel();
		panel2.setLayout(new BorderLayout());

		panel3 = new JPanel();
		panel3.setLayout(new BorderLayout());

		comp = new JPanel();
		comp.setLayout(new FlowLayout());
		comp.setBackground(Color.white);
		comp.setBorder(BorderFactory.createTitledBorder("Compte à rebours"));

		filp = new JPanel();
		filp.setBackground(Color.white);
		filp.setLayout(new FlowLayout());
		filp.setBorder(BorderFactory.createTitledBorder("Processus à fermer"));

		minp = new JPanel();
		minp.setBackground(Color.white);
		minp.setLayout(new FlowLayout());
		minp.setBorder(BorderFactory.createTitledBorder("Nombre de minutes"));

		butp = new JPanel();
		butp.setBackground(Color.white);
		butp.setLayout(new FlowLayout());


		optp = new JPanel();
		optp.setBackground(Color.white);
		optp.setLayout(new GridLayout(5,1));
		optp.setBorder(BorderFactory.createTitledBorder("Options"));

		quip = new JPanel();
		quip.setBackground(Color.white);
		quip.setLayout(new FlowLayout());

		tfm = new JTextField(10);
		tfm.addFocusListener(this);

		tff = new JTextField(28);
		tff.setText("Tappez ici le nom du processus à fermer (ex: itunes, eclipse) sans majuscule, pour connaître le nom, faites Ctrl+Alt+Supr, puis onglet processus");
		tff.addFocusListener(this);
		quit = new JButton("Quitter");
		quit.addActionListener(this);

		ok = new JButton("Démarrer");
		ok.addActionListener(this);

		suppr = new JButton("Supprimer");
		suppr.addActionListener(this);

		st = new JButton("Arréter");
		st.addActionListener(this);

		tim = new JLabel("" ,SwingConstants.CENTER);
		tim.setText("00:00");

		file = new JLabel("Processus : ");

		min = new JLabel("Minutes : ");

		line = new JLabel("-------------------------------------------------------------------------------------------------------");

		close = new JCheckBox("Fermer cette fenêtre à la fin du temps", true);
		close.setBackground(Color.white);

		exit = new JCheckBox("Eteindre l'ordinateur à la fin du temps");
		exit.setBackground(Color.white);

		defaultPrg = new JCheckBox("Fermer "+s+" (->"+f.toString()+")");
		defaultPrg.setBackground(Color.white);
		defaultPrg.addChangeListener(this);

		other = new JCheckBox("Fermer le processus entré", true);
		other.setBackground(Color.white);
		other.addChangeListener(this);

		grb = new ButtonGroup();
		grb.add(close);
		grb.add(exit);

		grb2 = new ButtonGroup();
		grb2.add(other);
		grb2.add(defaultPrg);

		panel.add(panel2, BorderLayout.NORTH);
		panel2.add(filp, BorderLayout.NORTH);
		filp.add(file);
		filp.add(tff);
		panel2.add(minp, BorderLayout.CENTER);
		minp.add(min);
		minp.add(tfm);
		panel2.add(comp, BorderLayout.SOUTH);
		comp.add(tim);
		panel.add(panel3, BorderLayout.CENTER);
		panel3.add(optp, BorderLayout.NORTH);
		optp.add(other);
		optp.add(defaultPrg);
		optp.add(line);
		optp.add(close);
		optp.add(exit);
		panel3.add(butp, BorderLayout.CENTER);
		butp.add(ok);
		butp.add(st);
		butp.add(suppr);
		panel.add(quip, BorderLayout.SOUTH);
		quip.add(quit);
		
		return panel;
	}

	public void actionPerformed(ActionEvent e) 		// Recupere les ActionListeners du menu et des boutons
	{
		Object src = e.getSource();
		if(ok == src || play == src)
		{
			validerReponse();
			rebours();
			timer1.start();
			fr.getRootPane().setDefaultButton(st);
			ok.setEnabled(false);
			play.setEnabled(false);
			close.setEnabled(false);
			exit.setEnabled(false);
			other.setEnabled(false);
			defaultPrg.setEnabled(false);

		}
		else if(stop == src || st == src)
		{
			tim.setText("00:00");
			fr.getRootPane().setDefaultButton(ok);
			timer1.stop();
			close.setEnabled(true);
			other.setEnabled(true);
			defaultPrg.setEnabled(true);
			exit.setEnabled(true);
			ok.setEnabled(true);
			play.setEnabled(true);

		}else if (suppr == src )	// Pour supprimer le fichier du lecteur par défaut, et le demander ensuite
		{
			int i = JOptionPane.showConfirmDialog(fr,"Supprimer "+ System.getProperty("user.home")+"/.defaultplayer ?","Supprimer",  JOptionPane.YES_NO_OPTION);

			if (i == JOptionPane.YES_OPTION)
			{
				f.delete();
				defaultPlayer(); 
			}
		}
		else if(aide == src)
		{
			JOptionPane.showMessageDialog(null," Remplissez les champs"
					+ "\n souhaités pour le compte à"
					+ "\n rebours et cliquez sur \"Démarrer\".",
					"Aide", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(appos == src)
		{
			JOptionPane.showMessageDialog(null, " Ce programme a été développé par Sarathai"
					+ "\n Copyright 2008",
					"A Propos", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(quitter == src || quit == src)
		{
			System.exit(0);
		}else if(src == del) {
			if(f.exists()) {
				f.delete();
			}
		}else if(src == modify) {
			if(f.exists()){
				setDefaultPlayer();
			}
		}

	}

	private void setDefaultPlayer() {
		String str = JOptionPane.showInputDialog(fr, "Enter le nom du programme à fermer par défaut", "Programme par défaut", JOptionPane.QUESTION_MESSAGE);
		if(str != "" || str != null) {
			try {
				BufferedWriter bfw = new BufferedWriter(new FileWriter(f));
				bfw.write(str);
				bfw.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

	public void rebours()		// Methode du compte à rebours
	{
		System.out.println(System.getProperty("os.name").toUpperCase());
		if (System.getProperty("os.name").toUpperCase().equals("WINDOWS XP")) {
			commande = "tskill "+prg;
			commande2 = "shutdown -s -t 5";
			commandedef = "tskill "+s;
		}else if (System.getProperty("os.name").toUpperCase().equals("LINUX")) {
			commande = "killall "+prg;
			commande2 = "sudo halt";
			commandedef = "killall "+s;
		}else if(System.getProperty("os.name").toUpperCase().equals("WINDOWS VISTA") || System.getProperty("os.name").toUpperCase().equals("WINDOWS SEVEN")) {
			commande = "taskkill /IM "+prg+" /T";
			commande2 = "shutdown -s -t 5";
			commandedef = "taskkill /IM "+s+" /T";
		}
		minute = i;
		seconde = 0;
		prg = tff.getText();
		tache_timer= new ActionListener()	
		{
			public void actionPerformed(ActionEvent e1) {
				seconde--;					// Pour le Timer
				if(seconde==-1){
					seconde=59;
					minute--;
				}
				if(minute==-1){
					timer1.stop();
					if(defaultPrg.isSelected()){
						try {
							Runtime.getRuntime().exec(commandedef); // Commande Dos simplement fermer le prog par défaut
							//en cours apres le timer
						} catch (IOException t) {  } 
					}else if(other.isSelected()){
						try {
							Runtime.getRuntime().exec(commande); // Commande Dos simplement fermer un prog choisi
							//en cours apres le timer
						} catch (IOException t) {  } 
					}	

					if (close.isSelected()){
						System.exit(0);		// Ferme le programme
					}
					if(exit.isSelected()){
						try {
							Runtime.getRuntime().exec(commande2);	// Eteind le PC commande DOS
						} catch (IOException t) {  } 
					}
				}
				tim.setText(""+minute+":"+seconde);  
			}
		};

		timer1= new Timer(1000,tache_timer);	//Le fameux Timer(le Préccciieuuuxx...)

	}
	
	public void validerReponse()		// Pour valider l'entier du timer
	{
		try
		{
			i = Integer.parseInt(tfm.getText());
		}
		catch (NumberFormatException f)
		{
			JOptionPane.showMessageDialog(null, "Veuillez entrer un entier de chiffre.", "Erreur", JOptionPane.ERROR_MESSAGE);
			timer1.stop();
		}	
	}

	public void stateChanged(ChangeEvent e)	// pour les changements du processus � fermer
	{
		if(defaultPrg.isSelected())
		{
			tff.setEnabled(false);
		}
		else if(other.isSelected())
		{
			tff.setEnabled(true);
		}
	}

	/**
	 * @param property
	 */
	public String defaultPlayer() // Pour ajouter le lecteur par défaut dans un fichier
	{		
		//  n'ayant pas trouver comment récupérer le nom de lecteur depuis le systeme
		String tmp = "Erreur";
		if ( f.exists() ) {		// si le fichier existe
			try
			{
				BufferedReader bfreader = new BufferedReader(new FileReader(f));

				tmp = bfreader.readLine().toLowerCase(); 	// on le lit et on prend la seule ligne existante, qui contient le nom
				bfreader.close();	
			}catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		return tmp;
	}
	
	public void focusGained(FocusEvent e) // Pour gérer les focus sur les champs de textes, focus on, on prend tout
	{									//  plus facile d'éditer
		((JTextComponent) e.getSource()).selectAll();
	}

	public void focusLost(FocusEvent e) 	// de même pour focus off
	{
		((JTextComponent) e.getSource()).setCaretPosition(0);
	}

	public static void main(String[]args)	// Methode Main
	{
		String lookAndFeelName = UIManager.getSystemLookAndFeelClassName();

		try {

			UIManager.setLookAndFeel(lookAndFeelName);
		} 
		catch (ClassNotFoundException e) {} 
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		catch (UnsupportedLookAndFeelException e) {}

		new TimerStop();
	}


}
