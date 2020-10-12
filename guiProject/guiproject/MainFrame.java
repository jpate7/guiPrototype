package guiproject;


import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainFrame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame main = new JFrame("Contract Tracer");
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setSize(800, 600);
		main.getContentPane().add(new MainPanel());
		main.setVisible(true);
		main.pack();
	}

}
