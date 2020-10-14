package guiproject;

import java.awt.*;
import java.text.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.util.ArrayList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;


public class MainPanel extends JPanel /*implements ActionListener*/
{
	final int MAIN_X = 700, MAIN_Y = 500, ID_LEN = 6;
	private DataManager guiData;
	//A split pane(names - info)
	//a menu
	private JSplitPane dataSplit;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel viewRightPanel;
	
	private DefaultListModel<String> names;
	private DefaultListModel<String> ids;
	private DefaultListModel<String> contacts;
	private JList nameList;
	private JList idList;
	//private JList contactList;
	
	private JScrollPane scrollRightPane;
	private JScrollPane listScrollPane;
	private JScrollPane contactScrollPane;
	
	
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
	
	private JComboBox contactBox;
	
	private JMenuBar menuBar;
	private JMenu Menu;
	
	private JButton btnEditInfo;
	private JButton btnAddContactInfo;
	private JButton btnCancel;
	private JButton btnSave;


	
	//private JMenu menu;


	
	/**
	 * @wbp.parser.entryPoint
	 */
	public MainPanel()
	{
		super();
		this.setPreferredSize(new Dimension(MAIN_X,MAIN_Y));
		
;		guiData = new DataManager();
		guiData.readFrom("input.txt");
		
		this.setBackground(Color.WHITE);
		
		
		//configure the left panel
		leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.setBackground(Color.WHITE);
		
		//configure the list of the nameList 
		names = new DefaultListModel<String>();
		ids = new DefaultListModel<String>();
		contacts = new DefaultListModel<String>();
		Person[] data = guiData.getAllTracers();
		for(int i = 0; i < guiData.getSize(); i++)
		{
			names.addElement(data[i].getId() +", "+ data[i].getName());
			
		}
		idList = new JList(ids);
		nameList = new JList(names);
		
        
	
		
		//configure the Jlist of the left panel
		listScrollPane = new JScrollPane();
        listScrollPane.setPreferredSize(new Dimension((MAIN_X/2),MAIN_Y));
		nameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		nameList.setSelectedIndex(0);
		nameList.setVisibleRowCount(10);
		nameList.setVisible(true);
		
		
		//initalize the menu 
		menuBar = new JMenuBar();
		leftPanel.add(menuBar, BorderLayout.NORTH);
		menuBar.setForeground(Color.BLACK);
		menuBar.setBackground(Color.white);
		
		Menu = new JMenu("Menu");
		Menu.setBackground(Color.RED);
		Menu.setForeground(Color.WHITE);
		menuBar.add(Menu);
		
		
		nameList.setLayoutOrientation(JList.VERTICAL);
		listScrollPane.add(nameList);
		//listScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		listScrollPane.setViewportView(nameList);
		leftPanel.add(listScrollPane, BorderLayout.CENTER);
		
		
		//configure the rightPanel
		rightPanel = new JPanel();
		rightPanel.setBackground(Color.GREEN);
		rightPanel.setLayout(new BorderLayout());
		
		//configure the Jlabels and Jtext(layouts, name, postion, background,etc.) on the right
		positionLabels_N_Text();
		/*idLabel = new JLabel("ID: ");
		idLabel.setBounds(10, 10, 50, 30);
		nameLabel = new JLabel("Name: ");
		nameLabel.setBounds(10, 52, 50, 30);
		phoneLabel = new JLabel("Phone Number: ");
		phoneLabel.setBounds(10, 94, 100, 13);
		statusLabel = new JLabel("Status: ");
		statusLabel.setBounds(10, 119, 47, 30);
		contactLabel = new JLabel("ID of Contacts: ");
		contactLabel.setBounds(10, 154, 100, 20);
		
		//set up JFormattedTextFields
		/*NumberFormat integerFieldFormatter = NumberFormat.getIntegerInstance();
		integerFieldFormatter.setMaximumFractionDigits(0);
		integerFieldFormatter.setGroupingUsed(false);
		idText = new JFormattedTextField(integerFieldFormatter);
		idText.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		idText.setValue(121);
		idText.setEditable(true);
		idText.setForeground(Color.BLACK);
		//idText.setBackground(Color.blue);
		idText.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
		idText.setLayout(null);
		idText.setVisible(true);
		idText.setBounds(new Rectangle(200,30));
		idText.setLocation(new Point(30,10));
		idLabel.setLabelFor(idText);
		
		nameText = new JFormattedTextField();
		nameText.setValue("Default");
		nameText.setEditable(true);
		nameText.setForeground(Color.BLACK);
		//nameText.setBackground(Color.YELLOW);
		nameText.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
		nameText.setLayout(null);
		nameText.setBounds(new Rectangle(64, 56, 189, 20));
		nameText.setLocation(new Point(64,58));
		nameLabel.setLabelFor(nameText);
		
		phoneNumberText = new JFormattedTextField(integerFieldFormatter);
		phoneNumberText.setValue(0);
		phoneNumberText.setEditable(true);
		phoneNumberText.setForeground(Color.BLACK);
		//phoneNumberText.setBackground(Color.green);
		phoneNumberText.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
		phoneNumberText.setLayout(null);
		phoneNumberText.setBounds(new Rectangle(113, 92, 177, 20));
		phoneNumberText.setLocation(new Point(110,92));
		phoneLabel.setLabelFor(phoneNumberText);

		
		statusText = new JFormattedTextField();
		statusText.setValue("Default");
		statusText.setEditable(true);
		statusText.setForeground(Color.BLACK);
		//statusText.setBackground(Color.green);
		statusText.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
		statusText.setLayout(null);
		statusText.setBounds(new Rectangle(50, 124, 164, 20));
		statusText.setLocation(new Point(64,124));
		statusLabel.setLabelFor(statusText);*/
		
		//fill all the right panel based on selectedItem from nameList
		nameList.addListSelectionListener(new nameSelectListener());
	
		
		
		
	

		contactBox = new JComboBox();
		//fills the combo box of all the ids
		fillContactBox(nameList.getSelectedValue().toString());
		contactBox.addActionListener(new contactBoxListener());	//adds the contactArea Text based on the selected item
		
		
	
	
		
		
		contactBox.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		contactBox.setBounds(5, 174, 171, 27);
		

		
		
		contactScrollPane = new JScrollPane();
		contactScrollPane.setBounds(10, 213, 200, 143);
		
		contactArea = new JTextArea();
		contactScrollPane.setViewportView(contactArea);
		//fills the textArea of the corresponding contact in the comboBox
		
		
		
		//adds the necessary elements to the right Viewing panels, which in in a scrollRight Pane
		setViewRightPanel();
		/*//set up viewRightPanel by adding all the elements
		viewRightPanel = new JPanel();
		viewRightPanel.setLayout(null);
		viewRightPanel.setBackground(Color.white);
		viewRightPanel.add(idLabel);
		viewRightPanel.add(idText);
		viewRightPanel.add(nameLabel);
		viewRightPanel.add(nameText);
		viewRightPanel.add(statusLabel);
		viewRightPanel.add(statusText);
		viewRightPanel.add(phoneLabel);
		viewRightPanel.add(phoneNumberText);
		viewRightPanel.add(contactLabel);
		viewRightPanel.add(contactBox);
		viewRightPanel.add(contactScrollPane);*/
	
		
		
		
		
		
		//add the viewRightPanel to the RightScrollPane
		scrollRightPane = new JScrollPane();
		scrollRightPane.add(viewRightPanel);
		scrollRightPane.setBackground(Color.WHITE);
		scrollRightPane.setViewportView(viewRightPanel);
		
		btnEditInfo = new JButton("Edit Info");
		//implements actionListener for Edit Info
		btnEditInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnEditInfo.setVisible(false);
				btnSave.setVisible(true);
				btnCancel.setVisible(true);
				btnAddContactInfo.setVisible(true);
				idText.setEditable(true);
				nameText.setEditable(true);
				statusText.setEditable(true);
				phoneNumberText.setEditable(true);
				contactArea.setEditable(true);
			}
		});
		
		//set up the buttons(Edit info, Cancel, Save, Add New Contact)
		setUpPanelBtn();
		/*btnEditInfo.setBackground(Color.WHITE);
		btnEditInfo.setBounds(30, 368, 117, 29);
		viewRightPanel.add(btnEditInfo);
		
		btnAddContactInfo = new JButton("Add New Contact");
		btnAddContactInfo.setBounds(170, 174, 134, 29);
		viewRightPanel.add(btnAddContactInfo);
		btnAddContactInfo.setVisible(false);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(10, 368, 86, 29);
		viewRightPanel.add(btnCancel);
		btnCancel.setVisible(false);
		
		btnSave = new JButton("Save");
		btnSave.setBounds(102, 368, 86, 29);
		viewRightPanel.add(btnSave);
		scrollRightPane.setPreferredSize(viewRightPanel.getSize());
		btnSave.setVisible(false);*/
		
		
		rightPanel.setPreferredSize(scrollRightPane.getSize());
		rightPanel.add(scrollRightPane);
		rightPanel.setVisible(false);

		//add the two panes to this JPanel
		dataSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		dataSplit.setBackground(Color.RED);
		dataSplit.setDividerSize(8);
		dataSplit.setDividerLocation(MAIN_X/2);
		dataSplit.setPreferredSize(new Dimension(MAIN_X,MAIN_Y));
		this.setLocation(new Point(0,0));
		this.setPreferredSize(dataSplit.getPreferredSize());
		this.add(dataSplit);
	
		


	
		
	
	}
	
	private class nameSelectListener implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e) 
		{
			
			fillInfo(nameList.getSelectedValue().toString());
			rightPanel.setVisible(true);
			dataSplit.setDividerLocation(MAIN_X/3);
			idText.setEditable(false);
			nameText.setEditable(false);
			statusText.setEditable(false);
			phoneNumberText.setEditable(false);
			contactArea.setText("Please Pick a Contact to get info");
			contactArea.setEditable(false);
			
	
		}
	}
	private class contactBoxListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			fillContactArea(contactBox.getSelectedItem().toString());
		}
	}
	
	private void setUpPanelBtn()
	{
		btnEditInfo.setBackground(Color.WHITE);
		btnEditInfo.setBounds(30, 368, 117, 29);
		viewRightPanel.add(btnEditInfo);
		
		btnAddContactInfo = new JButton("Add New Contact");
		btnAddContactInfo.setBounds(170, 174, 134, 29);
		viewRightPanel.add(btnAddContactInfo);
		btnAddContactInfo.setVisible(false);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(10, 368, 86, 29);
		viewRightPanel.add(btnCancel);
		btnCancel.setVisible(false);
		
		btnSave = new JButton("Save");
		btnSave.setBounds(102, 368, 86, 29);
		viewRightPanel.add(btnSave);
		scrollRightPane.setPreferredSize(viewRightPanel.getSize());
		btnSave.setVisible(false);
	}
	

	
	public void fillContactBox(String selectedValue)
	{
		String temp = getSelectedID(selectedValue);
		ArrayList<String> c = guiData.getAllContactsOf(temp.toString());
		contactBox.setModel(new DefaultComboBoxModel(c.toArray()));
		
	}
	
	private String getSelectedID(String selected)
	{
		StringBuilder temp = new StringBuilder();
		int Id_len = ID_LEN;
		for(int i = 0; i < Id_len; i++)	//id length is varies on program
		{
			temp.append(selected.charAt(i));
		}
		return temp.toString();
	}
	
	private void fillContactArea(String selectedItem)
	{
		contactArea.setText(guiData.findPerson(selectedItem).getPersonInfo());
	}
	
	private void fillInfo(String selectedValue) //nameList method to fill info, parameter taken is id,name
	{
		String idVal = getSelectedID(selectedValue);
		idText.setText(idVal);
		nameText.setText(guiData.getTracer(idVal).getName());
		statusText.setText(guiData.getTracer(idVal).getStatus());
		phoneNumberText.setText(guiData.getTracer(idVal).getNumber());
	}
	
	private void setViewRightPanel()
	{
		//set up viewRightPanel by adding all the elements
		viewRightPanel = new JPanel();
		viewRightPanel.setLayout(null);
		viewRightPanel.setBackground(Color.white);
		viewRightPanel.add(idLabel);
		viewRightPanel.add(idText);
		viewRightPanel.add(nameLabel);
		viewRightPanel.add(nameText);
		viewRightPanel.add(statusLabel);
		viewRightPanel.add(statusText);
		viewRightPanel.add(phoneLabel);
		viewRightPanel.add(phoneNumberText);
		viewRightPanel.add(contactLabel);
		viewRightPanel.add(contactBox);
		viewRightPanel.add(contactScrollPane);
	}
	
	private void positionLabels_N_Text()
	{//sets the location, background, names, and layouts
		idLabel = new JLabel("ID: ");
		idLabel.setBounds(10, 10, 50, 30);
		nameLabel = new JLabel("Name: ");
		nameLabel.setBounds(10, 52, 50, 30);
		phoneLabel = new JLabel("Phone Number: ");
		phoneLabel.setBounds(10, 94, 100, 13);
		statusLabel = new JLabel("Status: ");
		statusLabel.setBounds(10, 119, 47, 30);
		contactLabel = new JLabel("ID of Contacts: ");
		contactLabel.setBounds(10, 154, 100, 20);
		
		//set up JFormattedTextFields
		NumberFormat integerFieldFormatter = NumberFormat.getIntegerInstance();
		integerFieldFormatter.setMaximumFractionDigits(0);
		integerFieldFormatter.setGroupingUsed(false);
		idText = new JFormattedTextField(integerFieldFormatter);
		idText.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		idText.setValue(121);
		idText.setEditable(true);
		idText.setForeground(Color.BLACK);
		//idText.setBackground(Color.blue);
		idText.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
		idText.setLayout(null);
		idText.setVisible(true);
		idText.setBounds(new Rectangle(200,30));
		idText.setLocation(new Point(30,10));
		idLabel.setLabelFor(idText);
		
		nameText = new JFormattedTextField();
		nameText.setValue("Default");
		nameText.setEditable(true);
		nameText.setForeground(Color.BLACK);
		//nameText.setBackground(Color.YELLOW);
		nameText.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
		nameText.setLayout(null);
		nameText.setBounds(new Rectangle(64, 56, 189, 20));
		nameText.setLocation(new Point(64,58));
		nameLabel.setLabelFor(nameText);
		
		phoneNumberText = new JFormattedTextField(integerFieldFormatter);
		phoneNumberText.setValue(0);
		phoneNumberText.setEditable(true);
		phoneNumberText.setForeground(Color.BLACK);
		//phoneNumberText.setBackground(Color.green);
		phoneNumberText.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
		phoneNumberText.setLayout(null);
		phoneNumberText.setBounds(new Rectangle(113, 92, 177, 20));
		phoneNumberText.setLocation(new Point(110,92));
		phoneLabel.setLabelFor(phoneNumberText);

		
		statusText = new JFormattedTextField();
		statusText.setValue("Default");
		statusText.setEditable(true);
		statusText.setForeground(Color.BLACK);
		//statusText.setBackground(Color.green);
		statusText.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
		statusText.setLayout(null);
		statusText.setBounds(new Rectangle(50, 124, 164, 20));
		statusText.setLocation(new Point(64,124));
		statusLabel.setLabelFor(statusText);
	}
}
