package guiproject;

import java.awt.*;
import java.text.*;
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
	private JPanel viewRightPanel;
	
	private DefaultListModel names;
	private DefaultListModel ids;
	private JList nameList;
	private JList idList;
	
	private JScrollPane scrollRightPane;
	private JScrollPane listScrollPane;
	
	
	private JLabel idLabel;
	private JLabel nameLabel;
	private JLabel phoneLabel;
	private JLabel statusLabel;
	private JLabel contactLabel;
	
	private JFormattedTextField idText;
	private JFormattedTextField nameText;
	private JFormattedTextField phoneNumberText;
	private JFormattedTextField statusText;
	
	private JTextArea contactArea;

	
	//private JMenu menu;


	
	/**
	 * @wbp.parser.entryPoint
	 */
	public MainPanel()
	{
		super(new BorderLayout());
		this.setPreferredSize(new Dimension(800,600));
		
;		guiData = new DataManager();
		guiData.readFrom("input.txt");
		
		this.setBackground(Color.WHITE);
		
		
		//configure the left panel
		leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.setBackground(Color.BLACK);
		
		//configure the list of the nameList 
		names = new DefaultListModel();
		ids = new DefaultListModel();
		Person[] data = guiData.getAllTracers();
		for(int i = 0; i < guiData.getSize(); i++)
		{
			names.addElement(data[i].getName());
			ids.addElement(data[i].getId());
		}
		idList = new JList(ids);
		nameList = new JList(names);
        
	
		
		//configure the Jlist of the left panel
		listScrollPane = new JScrollPane();
        listScrollPane.setPreferredSize(new Dimension(400,600));
        listScrollPane.setBackground(Color.BLUE);
		nameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		nameList.setSelectedIndex(0);
		nameList.setVisibleRowCount(10);
		nameList.setVisible(true);
		nameList.setLayoutOrientation(JList.VERTICAL);
		listScrollPane.add(nameList);
		//listScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		listScrollPane.setViewportView(nameList);
		leftPanel.add(listScrollPane, BorderLayout.CENTER);
		
		
		//configure the rightPanel
		rightPanel = new JPanel();
		rightPanel.setBackground(Color.GREEN);
		rightPanel.setLayout(new BorderLayout());
		
		//configure the Jlabels on the right
		idLabel = new JLabel("ID: ");
		idLabel.setBounds(10, 10, 50, 30);
		nameLabel = new JLabel("Name: ");
		nameLabel.setBounds(10, 40, 50, 30);
		phoneLabel = new JLabel("Phone Number: ");
		phoneLabel.setBounds(10, 70, 100, 30);
		statusLabel = new JLabel("Status: ");
		statusLabel.setBounds(10, 100, 50, 30);
		contactLabel = new JLabel("ID of Contacts: ");
		contactLabel.setBounds(10, 130, 100, 50);
		
		//set up JFormattedTextFields
		NumberFormat integerFieldFormatter = NumberFormat.getIntegerInstance();
		integerFieldFormatter.setMaximumFractionDigits(0);
		integerFieldFormatter.setGroupingUsed(false);
		idText = new JFormattedTextField(integerFieldFormatter);
		idText.setValue(121);
		idText.setEditable(true);
		idText.setForeground(Color.WHITE);
		idText.setBackground(Color.blue);
		idText.setLayout(null);
		idText.setVisible(true);
		idText.setBounds(new Rectangle(200,30));
		idText.setLocation(new Point(30,10));
		idLabel.setLabelFor(idText);
		
		nameText = new JFormattedTextField();
		nameText.setValue("Default");
		nameText.setEditable(true);
		nameText.setForeground(Color.WHITE);
		nameText.setBackground(Color.YELLOW);
		nameText.setLayout(null);
		nameText.setBounds(new Rectangle(200,30));
		nameText.setLocation(new Point(50,40));
		nameLabel.setLabelFor(nameText);
		
		
		/*private JFormattedTextField phoneNumberText;
		private JFormattedTextField statusText;*/
		
		
		//set up viewRightPanel
		viewRightPanel = new JPanel();
		viewRightPanel.setLayout(null);
		viewRightPanel.setPreferredSize(new Dimension(400,600));
		viewRightPanel.setBackground(Color.white);
		viewRightPanel.add(idLabel);
		viewRightPanel.add(idText);
		viewRightPanel.add(nameLabel);
		viewRightPanel.add(nameText);
		viewRightPanel.add(statusLabel);
		viewRightPanel.add(phoneLabel);
		viewRightPanel.add(contactLabel);
		
		
		
		
		
		
		
		
		
		scrollRightPane = new JScrollPane();
		scrollRightPane.add(viewRightPanel);
		scrollRightPane.setBackground(Color.WHITE);
		scrollRightPane.setPreferredSize(new Dimension(400, 600));
		scrollRightPane.setViewportView(viewRightPanel);
		rightPanel.add(scrollRightPane, BorderLayout.CENTER);
		rightPanel.setPreferredSize(new Dimension(400,600));
		rightPanel.setVisible(true);
		
		//add the two panes to this JPanel
		dataSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		dataSplit.setBackground(Color.RED);
		dataSplit.setDividerSize(8);
		dataSplit.setDividerLocation(400);
		dataSplit.setPreferredSize(new Dimension(800,600));
		this.add(dataSplit, BorderLayout.CENTER);
		
		

	
		
	
	}
	
}
