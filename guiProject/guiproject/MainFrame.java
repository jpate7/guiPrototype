package guiproject;


import java.awt.Dimension;
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
		main.setSize(700, 500);
		main.setMaximumSize(new Dimension(720,530));
		main.setMinimumSize(new Dimension(700,440));
		//main.getContentPane().setLayout(new FlowLayout);
		main.getContentPane().add(new MainPanel());
		main.setVisible(true);
		main.pack();
		
	}

}
