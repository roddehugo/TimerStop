import javax.swing.JOptionPane;


/**
 * @file Test.java
 * @package 
 * @project TimeStop
 * @date 4 janv. 2010
 * @user hugo
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	String os = System.getProperty("os.name").toUpperCase();
	JOptionPane.showMessageDialog(null, os,"OS", JOptionPane.INFORMATION_MESSAGE);
	}
}
