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
	
	private JList<String> nameList;
	private JList idList;
	//private JList contactList;
	DefaultListModel listModel;
	
	private JScrollPane scrollRightPane;
	private JScrollPane listScrollPane;
	private JScrollPane contactScrollPane;
	
	
	private JLabel idLabel;
	private JLabel nameLabel;
	private JLabel phoneLabel;
	private JLabel statusLabel;
	private JLabel contactLabel;
	
	private JTextField idText;
	private JTextField nameText;
	private JTextField phoneNumberText;
	private JTextField statusText;
	
	private JTextArea contactArea;
	
	private JComboBox contactBox;
	
	private JMenuBar menuBar;
	private JMenu Menu;
	
	private JButton btnEditInfo;
	private JButton btnAddContactInfo;
	private JButton btnCancel;
	private JButton btnSave;
	
	//private JOptionPane addContactWindow;
	private JPanel contactPanel;
	private JOptionPane contactOption;
	
	private Person original;
	private Person addedContact;
	private Person addedPerson;
	
	private boolean cValue;
	private boolean pValue;

	private int checker;
	private int listIndex = 0;

	
	//private JMenu menu;


	
	/**
	 * @wbp.parser.entryPoint
	 */
	public MainPanel()
	{
		super();
		original = null;
		this.setPreferredSize(new Dimension(MAIN_X,MAIN_Y));
		
;		guiData = new DataManager();
		guiData.readFrom("input.txt");
		
		this.setBackground(Color.WHITE);
		
		
		//configure the left panel
		leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.setBackground(Color.WHITE);
		
		//configure the list of the nameList
		listModel = new DefaultListModel<String>();
		DefaultListModel<String> ids = new DefaultListModel<String>();
		//DefaultListModel<String> contacts = new DefaultListModel<String>();
		ArrayList<Person> data = guiData.getAllTracers();
		for(Person p: data)
		{
			listModel.addElement(p.getId() +", "+ p.getName());
		}
		//idList = new JList(ids);
		nameList = new JList(listModel);
	

		
        
	
		
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
		

	
		
		
		
	

		contactBox = new JComboBox();
		//fills the combo box of all the ids
		//fillContactBox(nameList.getSelectedValue().toString());
		//contactBox.addActionListener(new contactBoxSelectListener());	//adds the contactArea Text based on the selected item
		
		
	
	
		
		
		contactBox.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		contactBox.setBounds(5, 174, 171, 27);
		

		
		
		contactScrollPane = new JScrollPane();
		contactScrollPane.setBounds(10, 213, 200, 143);
		
		contactArea = new JTextArea();
		contactScrollPane.setViewportView(contactArea);
		//fills the textArea of the corresponding contact in the comboBox
		
		
		
		//adds the necessary elements to the right Viewing panels, which in in a scrollRight Pane
		addToViewRightPanel();
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
		rightPanel.setVisible(true);

		//add the two panes to this JPanel
		dataSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		dataSplit.setBackground(Color.RED);
		dataSplit.setDividerSize(8);
		dataSplit.setDividerLocation(MAIN_X/3);
		dataSplit.setPreferredSize(new Dimension(MAIN_X,MAIN_Y));
		this.setLocation(new Point(0,0));
		this.setPreferredSize(dataSplit.getPreferredSize());
		this.add(dataSplit);
		
		
		//establish the starting info on the rightPanel
		fillInfo(data.get(0).getId());
		fillContactBox(data.get(0).getId());
		contactArea.setText(guiData.getTracer(contactBox.getItemAt(0).toString()).getPersonInfo());
		idText.setEditable(false);
		nameText.setEditable(false);
		statusText.setEditable(false);
		phoneNumberText.setEditable(false);
		contactArea.setEditable(false);
		
		//fill all the right panel based on selectedItem from nameList
		//nameList.addListSelectionListener(new nameListSelectListener());
		
		//adds the contactArea Text based on the selected item
		contactBox.addActionListener(new contactBoxSelectListener());	
	
		//implements actionListener for Edit Info button to valid edits
		btnEditInfo.addActionListener(new btnEditInfoListener());
		
		//implements actionListener for Save button to save edits
		btnSave.addActionListener(new btnSaveListener());

		//implements actionListener for Cancel button to revert edits
		btnCancel.addActionListener(new btnCancelListener());
		
		//implements actionListener for the Add New Contact button to add a new contact to a tracer
		btnAddContactInfo.addActionListener(new btnAddContactInfo());
	
		checker  = 0;
		listIndex = 0;
		
		nameList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				fillInfo(nameList.getSelectedValue().toString());
				listIndex = nameList.getSelectedIndex();
				rightPanel.setVisible(true);
				idText.setEditable(false);
				nameText.setEditable(false);
				statusText.setEditable(false);
				phoneNumberText.setEditable(false);
				contactArea.setEditable(false);
			}
		});
		
	
	}
	
	//--------------------------------------FIRING LISTENERS-------------------------------------------------------------------------
	
	//ActionListener for the "Add new Contact" Button
	private class btnAddContactInfo implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			contactOption = new JOptionPane();
			contactPanel = new JPanel();
			JTextField contactName = new JTextField("Name");
			JTextField contactNumber = new JTextField("Number");
			JTextField contactStatus = new JTextField("Status");
			JTextField contactId = new JTextField("Id");
			
			JLabel nameOption = new JLabel("Name: ");
			nameOption.setLabelFor(contactName);
			JLabel numberOption = new JLabel("Number: ");
			numberOption.setLabelFor(contactName);
			JLabel statusOption = new JLabel("Status: ");
			statusOption.setLabelFor(contactStatus);
			JLabel IdOption = new JLabel("ID: ");
			IdOption.setLabelFor(contactId);
			
			Object[] fields = {
					nameOption, contactName,
					numberOption, contactNumber,
					statusOption, contactStatus,
					IdOption, contactId
			};
			
			/*contactPanel.add(nameOption);
			contactPanel.add(contactName);
			contactPanel.add(Box.createHorizontalStrut(20));
			contactPanel.add(numberOption);
			contactPanel.add(contactNumber);
			contactPanel.add(Box.createVerticalStrut(20));
			contactPanel.add(statusOption);
			contactPanel.add(contactStatus);
			contactPanel.add(Box.createVerticalStrut(20));
			contactPanel.add(IdOption);
			contactPanel.add(contactId);*/
			
			int result = JOptionPane.showConfirmDialog(null, fields,"Please Enter Contact Info", JOptionPane.OK_CANCEL_OPTION);
			
			if(result == JOptionPane.OK_OPTION)
			{//if added contact is valid, add that contact to the current tracer and create a new tracer with contact info
				//System.out.println(contactName.getText().toString());
				
				Person anotherTracer = new Person();
				anotherTracer.setName(contactName.getText().toString());
				anotherTracer.setNumber(contactNumber.getText().toString());
				anotherTracer.setStatus(contactStatus.getText().toString());
				anotherTracer.setId(contactId.getText().toString());
				//System.out.println(anotherTracer);
				//update contactBox with new id but not save
				guiData.addTracer(anotherTracer);
				contactArea.setText("Added Contact: " + anotherTracer.getPersonInfo());
				setAddedContact(anotherTracer);
				
			}
			
			
			
			
		}
	}
	
	
	//ActionListener for the "Cancel" Button---------------------------------------------------------------------
	private class btnCancelListener implements ActionListener
	{
	
		public void actionPerformed(ActionEvent e)
		{
			idText.setText(getOriginalPerson().getId());
			nameText.setText(getOriginalPerson().getName());
			statusText.setText(getOriginalPerson().getStatus());
			phoneNumberText.setText(getOriginalPerson().getNumber());
			idText.setEditable(false);
			nameText.setEditable(false);
			statusText.setEditable(false);
			phoneNumberText.setEditable(false);
			if(getAddedContactSet())
			{
				guiData.removeTracer(addedContact);
				isAddedContactSet(false);
			}
			
		
			//fillContactBox(original.getId());
			contactArea.setText(guiData.getTracer(contactBox.getItemAt(0).toString()).getPersonInfo());
			contactArea.setEditable(false);
			
			btnCancel.setVisible(false);
			btnSave.setVisible(false);
			btnEditInfo.setVisible(true);
			btnAddContactInfo.setVisible(false);
			nameList.enable();
			
			
		}
	}
	
	
	//ActionListener for the "Save" JButton---------------------------------------------------------------------
	private class btnSaveListener implements ActionListener
	{
		private void updateList()
		{//update the list with info on the new person
			/*DefaultListModel<String> names = new DefaultListModel<String>();
			ArrayList<Person> data = guiData.getAllTracers();
			for(Person p: data) 
			{
				names.addElement(p.getId() +", "+ p.getName());
			}
			
			nameList = new JList(names);*/
			
			/*DefaultListModel listModel = (DefaultListModel)nameList.getModel();
	        listModel.clear();
	        ArrayList<Person> data = guiData.getAllTracers();
			for(Person p: data) 
			{
				listModel.addElement(p.getId() +", "+ p.getName());
			}
			nameList.updateUI();
			nameList = new JList(listModel);*/
			
			
			//listModel.clear();
			listModel.setElementAt(getOriginalPerson().getId() +", "+ getOriginalPerson().getName(), listIndex);
			nameList.setModel(listModel);
			
			
			
			
			
		}
		
		
		
		public void actionPerformed(ActionEvent e)
		{
			
			Person new_P = new Person();
			new_P.setId(idText.getText().toString());
			new_P.setName(nameText.getText().toString());
			new_P.setStatus(statusText.getText().toString());
			new_P.setNumber(phoneNumberText.getText().toString());
			
			
			ArrayList<String> c = guiData.getAllContactsOf(getOriginalPerson().getId());
			//System.out.println(c);
			if(getAddedContactSet())
			{
				c.add(getAddedContact().getId());
				isAddedContactSet(false);
			
			}
			
			contactBox.setModel(new DefaultComboBoxModel(c.toArray()));
			contactArea.setText(guiData.getTracer(c.get(0)).getPersonInfo());
			for(int i = 0; i < contactBox.getItemCount(); i++)
			{
				new_P.addContactID(contactBox.getItemAt(i).toString());
				
			}
			
			if(getOriginalPerson().getId().equals(new_P.getId()))
			{
				guiData.updateTracer(new_P);
				setOriginalPerson(new_P);
			}
			else if(!(getOriginalPerson().getId().equals(new_P.getId()) && guiData.containsTracer(guiData.findPerson(new_P.getId()))))
			{
				JOptionPane errorPopUp = new JOptionPane("Error");
				errorPopUp.showMessageDialog(null, "This ID is already taken!");
				errorPopUp.setOptionType(errorPopUp.DEFAULT_OPTION);
				idText.setText(getOriginalPerson().getId().toString());
			}
			else
			{
				guiData.addTracer(new_P);
				guiData.removeTracer(getOriginalPerson());
				setOriginalPerson(new_P);
			}
			btnCancel.setVisible(false);
			btnSave.setVisible(false);
			btnEditInfo.setVisible(true);
			btnAddContactInfo.setVisible(false);
			nameList.enable();
			updateList();
		}
	}
	
	//ActionListener for the "Edit Info" JButton-------------------------------------------------------------
	private class btnEditInfoListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			btnEditInfo.setVisible(false);
			btnSave.setVisible(true);
			btnCancel.setVisible(true);
			btnAddContactInfo.setVisible(true);
			idText.setEditable(true);
			nameText.setEditable(true);
			statusText.setEditable(true);
			phoneNumberText.setEditable(true);
			//contactArea.setEditable(true);
			nameList.disable();
		}
	}
	
	//ListSelectionListener for the "nameList" JLIst on the left panel---------------------------------------------------------------
	private class nameListSelectListener implements ListSelectionListener
	{
		
		public void valueChanged(ListSelectionEvent e) 
		{//is not used 
			fillInfo(nameList.getSelectedValue().toString());
			listIndex = nameList.getSelectedIndex();
			rightPanel.setVisible(true);
			idText.setEditable(false);
			nameText.setEditable(false);
			statusText.setEditable(false);
			phoneNumberText.setEditable(false);
			contactArea.setEditable(false);
			
			
			
	
		}
	}
	//-------------------------------------
	
	
	//ActionListener for the "contactBox" JComboBox-----------------------------------------------------------------------------------
	private class contactBoxSelectListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			contactArea.setText(guiData.findPerson(contactBox.getSelectedItem().toString()).getPersonInfo());
		}
	}
	
	
	
	//---------------------CURRENT PERSON HELPER FUNCTIONS------------------------------------------------------------------------------------
	

	private void setAddedContact(Person added)
	{
		if(!(added == null))
		{
			addedContact = added;
			isAddedContactSet(true);
		}
		else
		{
			System.out.println("adding null reference");
		}
	}
	
	public Person getAddedContact()
	{
		if(getAddedContactSet())
			return addedContact;
		else
		{
			System.out.println("Error");
			return null;
		}
	}
	
	private void isAddedContactSet(boolean val) {
		cValue = val;
	}
	
	private boolean getAddedContactSet()
	{
		return cValue;
	}
	
	
	private void setOriginalPerson(Person old)
	{
		if(!(old == null))
		{
			original = old;
			isOriginalSet(true);
		}
		else
		{
			System.out.println("adding null reference");
		}
	}
	
	public Person getOriginalPerson()
	{
		if(getOriginalSet())
			return original;
		else
		{
			System.out.println("Error");
			return null;
		}
	}
	private void isOriginalSet(boolean val) {
		pValue = val;
	}
	private boolean getOriginalSet()
	{
		return pValue;
	}
	
	//----------------------------------FIRING HELPERS--------------------------------
	
	public void fillContactBox(String selectedValue)
	{
		String tempId = getSelectedID(selectedValue);
		ArrayList<String> c = guiData.getAllContactsOf(tempId.toString());
		contactBox.setModel(new DefaultComboBoxModel(c.toArray()));
		contactArea.setText(guiData.getTracer(c.get(0)).getPersonInfo());
		
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
	
	private void fillInfo(String selectedValue) //nameList method to fill info, parameter taken is id,name
	{
		String idVal = getSelectedID(selectedValue);
		idText.setText(idVal);
		nameText.setText(guiData.getTracer(idVal).getName());
		statusText.setText(guiData.getTracer(idVal).getStatus());
		phoneNumberText.setText(guiData.getTracer(idVal).getNumber());
		fillContactBox(selectedValue);
		Person original = guiData.getTracer(idVal);
		setOriginalPerson(original);
	}
	
	//-------------------------------CONSTRUT FILLERS---------------------------------------
	private void addToViewRightPanel()
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
		idText = new JTextField();
		idText.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		idText.setText("000000");
		idText.setEditable(true);
		idText.setForeground(Color.BLACK);
		//idText.setBackground(Color.blue);
		idText.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
		idText.setLayout(null);
		idText.setVisible(true);
		idText.setBounds(new Rectangle(200,30));
		idText.setLocation(new Point(30,10));
		idLabel.setLabelFor(idText);
		
		nameText = new JTextField();
		nameText.setText("Default");
		nameText.setEditable(true);
		nameText.setForeground(Color.BLACK);
		//nameText.setBackground(Color.YELLOW);
		nameText.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
		nameText.setLayout(null);
		nameText.setBounds(new Rectangle(64, 56, 189, 20));
		nameText.setLocation(new Point(64,58));
		nameLabel.setLabelFor(nameText);
		
		phoneNumberText = new JTextField();
		phoneNumberText.setText("0");
		phoneNumberText.setEditable(true);
		phoneNumberText.setForeground(Color.BLACK);
		//phoneNumberText.setBackground(Color.green);
		phoneNumberText.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
		phoneNumberText.setLayout(null);
		phoneNumberText.setBounds(new Rectangle(113, 92, 177, 20));
		phoneNumberText.setLocation(new Point(110,92));
		phoneLabel.setLabelFor(phoneNumberText);

		
		statusText = new JTextField();
		statusText.setText("Default");
		statusText.setEditable(true);
		statusText.setForeground(Color.BLACK);
		//statusText.setBackground(Color.green);
		statusText.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
		statusText.setLayout(null);
		statusText.setBounds(new Rectangle(50, 124, 164, 20));
		statusText.setLocation(new Point(64,124));
		statusLabel.setLabelFor(statusText);
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
}
