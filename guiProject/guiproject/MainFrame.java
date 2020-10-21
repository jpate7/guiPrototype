package guiproject;


import java.awt.Dimension;
import java.awt.FlowLayout;  
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComboBox;  
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



public class MainFrame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame main = new JFrame("Contract Tracer");
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setSize(700, 500);
		main.setMaximumSize(new Dimension(720,530));
		main.setMinimumSize(new Dimension(700,440));
		
		MainPanel panel = new MainPanel();
		
		main.getContentPane().add(panel);
		main.setVisible(true);
		main.pack();
		main.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				//outputs the saved and edited data to a text document;
				panel.doClose();
			}
		});

		
	}

}
