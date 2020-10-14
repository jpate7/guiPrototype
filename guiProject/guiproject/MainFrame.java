package guiproject;


import java.awt.FlowLayout;  
import java.awt.Panel;  
import javax.swing.JComboBox;  
import javax.swing.JFrame;  
import javax.swing.JPanel;



public class MainFrame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame main = new JFrame("Contract Tracer");
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setSize(600, 600);
		//main.getContentPane().setLayout(new FlowLayout);
		main.getContentPane().add(new MainPanel());
		main.setVisible(true);
		main.pack();
	}

}
