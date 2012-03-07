import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.Timer;

/*
 * Ecrit par Hugo Rodde 2012
 */


public class TimerStopCmd implements ActionListener{
    
    public static String[] arguments;
	int minute = 0, seconde = 0;
	String commande,commande2, prg, exit;
	ActionListener tache_timer;
	Timer timer1;

	public TimerStopCmd(){
		
        minute = validerReponse(arguments[1]);
		seconde = 0;
		prg = arguments[0];
        exit = arguments[2];
                
		System.out.println("System : "+System.getProperty("os.name").toUpperCase());
                
		if (System.getProperty("os.name").toUpperCase().equals("WINDOWS XP")) {
			commande = "tskill "+prg;
			commande2 = "shutdown -s -t 5";
		}else if (System.getProperty("os.name").toUpperCase().equals("LINUX")) {
			commande = "killall "+prg;
			commande2 = "sudo halt";
                                                      System.out.println(commande+" : "+minute);
		}else if(System.getProperty("os.name").toUpperCase().equals("WINDOWS VISTA") || System.getProperty("os.name").toUpperCase().equals("WINDOWS SEVEN")) {
			commande = "taskkill /IM "+prg+" /T";
			commande2 = "shutdown -s -t 5";
		}
        initTimer();
	}
        
	public void initTimer() {
	        System.out.println("Le Timer va se lancer...");
			timer1= new Timer(1000,this);
			timer1.start();
			while (timer1.isRunning()==true) {}
				
	  }
	
	public int validerReponse(String time)		// Pour valider l'entier du timer
	{
		try{
			return Integer.parseInt(time);
		}
		catch (NumberFormatException f){
            System.out.println("Veuillez entrer un entier de chiffre.");
            return 0;
		}	
	}

        
	public static void main(String[] args)	// Methode Main
	{
        arguments = args;
		new TimerStopCmd();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		seconde--;					// Pour le Timer
		if(seconde==-1){
			seconde=59;
			minute--;
		}
		if(minute==-1){
			timer1.stop();
			try {
				Runtime.getRuntime().exec(commande); // Commande Dos simplement fermer un prog choisi
				//en cours apres le timer
			} catch (IOException t) { System.out.println("Imposible de lancer la commande :"+commande+" Erreur : "+t); }
			if(exit.equals("1")){
				try {
					Runtime.getRuntime().exec(commande2);	// Eteind le PC commande DOS
					System.exit(0);
				} catch (IOException t) {  } 
			}
		}
		System.out.println(""+minute+":"+seconde);  
	}

}
