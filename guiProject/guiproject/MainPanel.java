package guiproject;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainPanel extends JPanel
{
	private DataManager guiData;
	//A split pane(names - info)
	//a menu
	private JSplitPane dataSplit;
	private JPanel leftPanel;
	private JPanel rightPanel;
	
	private DefaultListModel names;
	private DefaultListModel ids;
	private JList nameList;
	private JList idList;
	
	//private JMenu menu;


	
	/**
	 * @wbp.parser.entryPoint
	 */
	public MainPanel()
	{
		guiData = new DataManager();
		guiData.readFrom("input.txt");
		
		this.setSize(400,400);
		this.setBackground(Color.WHITE);
		leftPanel = new JPanel();
		leftPanel.setForeground(Color.BLACK);
		
		names = new DefaultListModel();
		ids = new DefaultListModel();
		Person[] data = guiData.getAllTracers();
		for(int i = 0; i < guiData.getSize(); i++)
		{
			names.addElement(data[i].getName());
			ids.addElement(data[i].getId());
		}
		
		nameList = new JList(names);
		idList = new JList(ids);
		nameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        nameList.setSelectedIndex(0);
        nameList.setVisibleRowCount(10);
        nameList.setVisible(true);
        JScrollPane listScrollPane = new JScrollPane(nameList);
 
		
		
		
		
		
		
		leftPanel.setSize(200, 200);
		leftPanel.add(listScrollPane);
		
		
		
		
		
		rightPanel = new JPanel();
		rightPanel.setForeground(Color.GRAY);

		
		
		
		
		
		
		dataSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		this.add(dataSplit);
		
		

	
		
	
	}
	
}
