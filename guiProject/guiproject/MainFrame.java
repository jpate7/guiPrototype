//Jignesh patel
//O-O Prog. With Java

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
				//try to exit on red
				main.setDefaultCloseOperation(main.EXIT_ON_CLOSE);
				int result = JOptionPane.showConfirmDialog(null, "Do you want to save any changes made to the file?");
				if(result == JOptionPane.YES_OPTION)
				{
					//save changes
					panel.doClose();	//then close
				}
				//if cancel, dont close
				else if(result == JOptionPane.CANCEL_OPTION)
				{
					main.setDefaultCloseOperation(main.DO_NOTHING_ON_CLOSE);	//if cancel, then don't close
				}
			}
		});

		
	}

}
