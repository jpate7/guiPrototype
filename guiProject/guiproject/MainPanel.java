package guiproject;

import java.awt.*;
import java.text.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.util.*;

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
	//private JList idList;
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
	private JMenu File;
	private JMenu Options;
	private JMenuItem AddTracer;
	private JMenuItem DeleteTracer;
	private JMenuItem OpenFile;
	private JMenuItem Save_N_Close;
	private JMenuItem Exit;
	
	private JButton btnEditInfo;
	private JButton btnAddContactInfo;
	private JButton btnCancel;
	private JButton btnSave;
	private JButton btnDeleteTracer;
	
	
	private JPanel deleteTPanel;
	private JOptionPane contactOption;
	private JOptionPane addTracerOption;
	private JOptionPane deleteTracerOption;
	
	private Person original;
	private Person addedContact;
	private Person addedPerson;
	
	private boolean cValue;
	private boolean pValue;

	private int checker;
	private int listIndex = 0;

	



	
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
		this.setForeground(Color.WHITE);
		
		
		//configure the left panel
		leftPanel = new JPanel();
		leftPanel.setBackground(Color.WHITE);
		leftPanel.setLayout(new BorderLayout());
		
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
		nameList.setBackground(new Color(248, 248, 255));
	

		
        
	
		
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
		menuBar.setForeground(Color.white);
		
		
		File = new JMenu("File");
		File.setBackground(Color.white);
		File.setForeground(SystemColor.textHighlight);
		menuBar.add(File);
		OpenFile = new JMenuItem("Open Existing File");
		File.add(OpenFile);
		Save_N_Close = new JMenuItem("Save and Close Program");
		File.add(Save_N_Close);
		Exit = new JMenuItem("Exit Program");
		File.add(Exit);
		
		Options = new JMenu("Options");
		Options.setBackground(Color.white);
		menuBar.add(Options);
		AddTracer = new JMenuItem("Add New Tracer");
		Options.add(AddTracer);
		DeleteTracer = new JMenuItem("Delete Existing Tracer");
		Options.add(DeleteTracer);
		
		
		
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
	
		
		
		
		
		
		//add the viewRightPanel to the RightScrollPane
		scrollRightPane = new JScrollPane();
		scrollRightPane.add(viewRightPanel);
		scrollRightPane.setBackground(Color.WHITE);
		scrollRightPane.setViewportView(viewRightPanel);
		
		btnEditInfo = new JButton("Edit Info");
	
		
		//set up the buttons(Edit info, Cancel, Save, Add New Contact)
		setUpPanelBtn();

		
		
		rightPanel.setPreferredSize(scrollRightPane.getSize());
		rightPanel.add(scrollRightPane);
		rightPanel.setVisible(true);

		//add the two panes to this JPanel
		dataSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		dataSplit.setBackground(new Color(255, 240, 245));
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
		nameList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				nameListMouseEvent(e);
				}
		});
		
		//implements the "Add a Tracer" option of the Menu based on actionPerformed
		AddTracer.addActionListener(new MenuAddTracerListener());
		
		//implements the "Delete Tracer" option of the Menu based on actionPerformed
		DeleteTracer.addActionListener(new MenuDeleteTracerListener());
		
		//implements the "Open A File" option of the Menu based on actionPerformed
		OpenFile.addActionListener(new MenuOpenFileListener());
		
		//implements the "Save and Close" option of the Menu based on actionPerformed
		Save_N_Close.addActionListener(new MenuSave_N_CloseListener());
		
		//implements the "Exit" option of the Menu based on actionPerformed
		Exit.addActionListener(new MenuExitListener());
		
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
		
		//implements the "Delete Tracer" button on the rightPanel
		btnDeleteTracer.addActionListener(new btnDeleteTracerListener());
		
	
		checker  = 0;
		
		
	
	}
	
	
	
	//--------------------------------------FIRING LISTENERS-------------------------------------------------------------------------------------------------------------------------------
	
	//implements the listener for the "Exit" menu Item--------------------------------------
	private class MenuExitListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{//immediately exits program without saving
			System.exit(-1);
		}
	}
	
	//implements the listener for the "Open A File" menu Item---------------------------------
	private class MenuOpenFileListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{

			JFileChooser jfc = new JFileChooser("./");
			int returnValue = jfc.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) 
			{
				String selectedFile = jfc.getSelectedFile().toString();
				if(guiData.getReadFileName().equals(jfc.getSelectedFile().getName()))
					JOptionPane.showMessageDialog(null, "File is already open");
				else
				{
					guiData = new DataManager();
					guiData.readFrom(jfc.getSelectedFile().getName().toString());
					listModel.clear();
					ArrayList<Person> data = guiData.getAllTracers();
					for(Person p: data)
					{
						listModel.addElement(p.getId() +", "+ p.getName());
					}
					nameList.setModel(listModel);
					fillInfo(listModel.getElementAt(0).toString());
					
				}
			}

		}
	}
	
	//implements the listener for the "Save and Close" menu Item---------------------------------
		private class MenuSave_N_CloseListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{//outputs the saved and edited data to a text document;
				guiData.writeFile();
				JOptionPane.showMessageDialog(null, "Data is saved in the Output file");
				System.exit(-1);
			}
		}
	
	
	
	//implements the listener for the "delete tracer" button ------------------------------------------------------
	private class btnDeleteTracerListener implements ActionListener
	{
		
		public void updateList()
		{
			listModel.removeElementAt(listIndex);
			nameList.setModel(listModel);
			fillInfo(listModel.get(0).toString());
		}
		
		public void actionPerformed(ActionEvent e)
		{
			//first, use a JOptionPane to ask if they want to delete the tracer
			
			String[] customOption = {
					"Yes, Delete Tracer","No, Go Back"
			};
			int result = JOptionPane.showOptionDialog(null, "Are you sure you want to delete this Tracer", "Confirm Delete",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, customOption, customOption[0]);
			
			if(result == JOptionPane.YES_OPTION)
			{
				guiData.removeTracer(getOriginalPerson());
				guiData.deleteContactFromAllTracers(getOriginalPerson().getId());
				updateList();
			}
		}
	}
	
	
	//implements the listener for the Delete Tracer menu item------------------------------------------------------------------
	private class MenuDeleteTracerListener implements ActionListener
	{
		public void updateList(String target)
		{//update nameList after delete
					listModel.removeElement(target);
					nameList.setModel(listModel);
			
		}
		
		public void actionPerformed(ActionEvent e)
		{//delete Tracer by ID
			deleteTPanel = new JPanel();
			JTextField tracerId = new JTextField(10);
			JLabel IdOption = new JLabel("ID: Enter 6-Digit ID");
			IdOption.setLabelFor(tracerId);
			deleteTPanel.add(IdOption);
			deleteTPanel.add(tracerId);
			
			String[] customOption = {
					"Delete","Cancel"
			};
			
			int tempResult = 0, tempValid = 0;
			do
			{
				try
				{
					tempResult = JOptionPane.showOptionDialog(null, deleteTPanel,"Delete A Tracer by ID",
						JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,null,customOption,customOption[0]);
					if(tempResult == JOptionPane.YES_OPTION)
					{
						//parse int and check for validation
						tempValid = Integer.parseInt(tracerId.getText().toString());
						if( tempValid > 999999 || tempValid < 000000 || (tracerId.getText().toString().length() != 6) )
							throw new Exception();
						else
						{
							Person toDelete = guiData.findPerson(tracerId.getText().toString());
							if(toDelete != null)	//if person toDelete exist
							{
								guiData.removeTracer(toDelete);
								updateList(toDelete.getId() + ", " + toDelete.getName());
								JOptionPane.showMessageDialog(null,"ID: " + tracerId.getText() +  " is now deleted");
								guiData.deleteContactFromAllTracers(tracerId.getText().toString());
							}
							else
								JOptionPane.showMessageDialog(null,"ID: " + tracerId.getText() +  " is not a tracer and cannot be deleted");
						}
					}
					else
						tempResult = JOptionPane.CANCEL_OPTION;
						
				}
				catch(Exception j)
				{
					JOptionPane.showMessageDialog(null, "Error has occured, Please enter a 6-Digit ID");
				}
			}while((tempValid < 000000 || tempValid > 999999 || (tracerId.getText().toString().length() != 6)) && (tempResult == JOptionPane.YES_OPTION));
			
			
					
					
			/*if(result == JOptionPane.YES_OPTION)
			{
				Person toDelete = guiData.findPerson(tracerId.getText().toString());
				if(toDelete != null)	//if person toDelete exist
				{
					guiData.removeTracer(toDelete);
					updateList(toDelete.getId() + ", " + toDelete.getName());
					JOptionPane.showMessageDialog(null,"ID: " + tracerId.getText() +  " is now deleted");
					guiData.deleteContactFromAllTracers(tracerId.getText().toString());
				}
				else
					JOptionPane.showMessageDialog(null,"ID: " + tracerId.getText() +  " is not a tracer and cannot be deleted");
				
			}*/
			
			
		
		}
	}
	
	//implements the listener for the "Add A Tracer" menu Item-------------------------------------------------------------------
	private class MenuAddTracerListener implements ActionListener
	{
		
		public void updateList(Person element)
		{
			listModel.addElement(element.getId().toString()+", "+element.getName());
			nameList.setModel(listModel);
			
		}
		

		
		public void actionPerformed(ActionEvent e)
		{
			//addTracerOption = new JOptionPane("Enter Info on the New Tracer");
			//contactPanel = new JPanel();
			JTextField tracerName = new JTextField("");
			JTextField tracerNumber = new JTextField("");
			JTextField tracerStatus = new JTextField("");
			JTextField tracerId = new JTextField("");
			
			JLabel nameOption = new JLabel("Name: ");
			nameOption.setLabelFor(tracerName);
			JLabel numberOption = new JLabel("Number: Enter 10-Digit Phone Number, ex: xxxxxx2345");
			numberOption.setLabelFor(tracerName);
			JLabel statusOption = new JLabel("Status: ");
			statusOption.setLabelFor(tracerStatus);
			JLabel IdOption = new JLabel("ID: Enter 6-Digit ID, ex: xxx123");
			IdOption.setLabelFor(tracerId);
			
			Object[] fields = {
					nameOption, tracerName,
					numberOption, tracerNumber,
					statusOption, tracerStatus,
					IdOption, tracerId
			};
			
			
			int tempResult = 0, tempId;
			boolean idExam = false, numExam = false;
			do
			{
				try
				{
					tempResult = JOptionPane.showConfirmDialog(null, fields,"Please Enter Contact Info", JOptionPane.OK_CANCEL_OPTION);
					if(tempResult == JOptionPane.OK_OPTION)
					{
						//parse fields and check for validation
						tempId = Integer.parseInt(tracerId.getText().toString());
						//if exam is true, failed input, so validate
						idExam = (tempId > 999999 || tempId < 000000 || (tracerId.getText().toString().length() != 6));
						numExam = InvalidPhoneNumber(tracerNumber.getText().toString());
						if(idExam || numExam)
							throw new Exception();
						else
						{
							if(!(guiData.containsTracer(guiData.findPerson(tracerId.getText().toString()))))	//if added tracer exist or not
							{
								Person addedTracer = new Person();
								addedTracer.setName(tracerName.getText().toString());
								addedTracer.setNumber(tracerNumber.getText().toString());
								addedTracer.setStatus(tracerStatus.getText().toString());
								addedTracer.setId(tracerId.getText().toString());
								System.out.println(addedTracer);
								
								guiData.addTracer(addedTracer);
								JOptionPane.showMessageDialog(null,"ID: " + addedTracer.getId() +  " is now added as a Tracer");
								updateList(addedTracer);
							}
							else
								JOptionPane.showMessageDialog(null,"ID: " + tracerId.getText() +  " already exists as a Tracer");

						}
						
					}
					else
						tempResult = JOptionPane.CANCEL_OPTION;
				}
				catch(Exception j)
				{
					JOptionPane.showMessageDialog(null, "Error has occured, Please enter a 6-Digit ID \nand a 10-Digit Phone Number");
				}
			}while((idExam || numExam) && (tempResult == JOptionPane.OK_OPTION));
			


			
		}
	}
	
	
	
	//substitutes the calls from the MouseClicked of "nameList"----------------------------------------------------------
	private void nameListMouseEvent(MouseEvent e)
	{
		fillInfo(nameList.getSelectedValue().toString());
		listIndex = nameList.getSelectedIndex();
		rightPanel.setVisible(true);
		idText.setEditable(false);
		nameText.setEditable(false);
		statusText.setEditable(false);
		phoneNumberText.setEditable(false);
		contactArea.setEditable(false);
	}
	
	
	//ActionListener for the "Add new Contact" Button----------------------------------------------------------------------------
	private class btnAddContactInfo implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			nameList.disable();
			contactOption = new JOptionPane();
			//contactPanel = new JPanel();
			JTextField contactName = new JTextField("");
			JTextField contactNumber = new JTextField("");
			JTextField contactStatus = new JTextField("");
			JTextField contactId = new JTextField("");
			
			JLabel nameOption = new JLabel("Name: ");
			nameOption.setLabelFor(contactName);
			JLabel numberOption = new JLabel("Number: Enter a 10-Digit Number");
			numberOption.setLabelFor(contactName);
			JLabel statusOption = new JLabel("Status: ");
			statusOption.setLabelFor(contactStatus);
			JLabel IdOption = new JLabel("ID: Enter 6-Digit ID");
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
				
				if(!(guiData.containsContact(getOriginalPerson(), contactId.getText().toString())))	//if added contact exist or not
				{
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
				else
				{
					JOptionPane.showMessageDialog(null, "ID: " + contactId.getText() +  " is already added as a Contact");
				}
			}
		}
	}
		
	
	
	//ActionListener for the "Cancel" Button------------------------------------------------------------------------------
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
			btnDeleteTracer.setVisible(true);
			nameList.enable();
			
			
		}
	}
	
	
	//ActionListener for the "Save" JButton---------------------------------------------------------------------
	private class btnSaveListener implements ActionListener
	{
		private void updateList()
		{//update the list with info on the new person
			
			//listModel.clear();
			listModel.setElementAt(getOriginalPerson().getId() +", "+ getOriginalPerson().getName(), listIndex);
			nameList.setModel(listModel);
			
		}
		
		private void updatenameList()
		{//update the list with info on the new person
			
			//listModel.clear();
			listModel.addElement(getAddedContact().getId()+", "+getAddedContact().getName());
			nameList.setModel(listModel);
			
		}


		
		
		
		public void actionPerformed(ActionEvent e)
		{
			
			//------------------------
			//validate input before saving
			boolean numExam = false, idExam = false;
			do
			{
				try {
					int tempId = Integer.parseInt(idText.getText().toString());
					numExam = InvalidPhoneNumber(phoneNumberText.getText().toString());	//returns true if invalid
					idExam = (tempId > 999999 || tempId < 000000 || idText.getText().toString().length() != 6);	//returns true if id is not 6 digits
																												//or if is not in range
					if(numExam || idExam)
						throw new Exception();
				}
				catch(Exception j)
				{
					JOptionPane.showMessageDialog(null, "Cannot Save, ID must be 6-digits and Number must be 10-digits");
					//revert fields back to original Person
					idText.setText(getOriginalPerson().getId());
					nameText.setText(getOriginalPerson().getName());
					phoneNumberText.setText(getOriginalPerson().getNumber());
					statusText.setText(getOriginalPerson().getStatus());
					
				}
			}while(numExam||idExam);
			
			
			
			
			//-------------------------
			
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
				guiData.addTracer(getAddedContact());
				updatenameList();
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
			btnDeleteTracer.setVisible(true);
			disableTextFields();
			nameList.enable();
			updateList();
		}
	}
	
	//ActionListener for the "Edit Info" JButton---------------------------------------------------------------------
	private class btnEditInfoListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			nameList.disable();
			btnEditInfo.setVisible(false);
			btnDeleteTracer.setVisible(false);
			btnSave.setVisible(true);
			btnCancel.setVisible(true);
			btnAddContactInfo.setVisible(true);
			idText.setEditable(true);
			nameText.setEditable(true);
			statusText.setEditable(true);
			phoneNumberText.setEditable(true);
			//contactArea.setEditable(true);
		}
	}
	
	//ListSelectionListener for the "nameList" JLIst on the left panel----------------------------------------------------
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
	
	
	//ActionListener for the "contactBox" JComboBox-----------------------------------------------------------------------------------
	private class contactBoxSelectListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			contactArea.setText(guiData.findPerson(contactBox.getSelectedItem().toString()).getPersonInfo());
		}
	}
	//--------------------------END FIRING CLASSES--------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	
	
	
	
	//---------------------CURRENT PERSON HELPER FUNCTIONS----------------------------------------------------------------------------------------------------------------------------------------------------------------------
	

	private void setAddedContact(Person added)
	{
		if(!(added == null))
		{
			addedContact = added;
			isAddedContactSet(true);
		}
		else
		{
			System.out.println("assigning null reference");
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
			System.out.println("assigning null reference");
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
	
	//----------------------------------FIRING HELPERS--------------------------------------------------------------------------------------------------------------------------
	
	private boolean InvalidPhoneNumber(String target) {
	    Pattern pattern = Pattern.compile("^\\d{10}$");
	    Matcher matcher = pattern.matcher(target);
	    return !(matcher.matches());	//return true if validation fails

	  }
	
	private void disableTextFields()
	{
		idText.setEditable(false);
		nameText.setEditable(false);
		statusText.setEditable(false);
		phoneNumberText.setEditable(false);
		contactArea.setEditable(false);
	}
	
	public void fillContactBox(String selectedValue)
	{
		//String tempId = getSelectedID(selectedValue);
		ArrayList<String> c = guiData.getAllContactsOf(selectedValue.toString());
		if(c.size() == 0)
		{//no ids
			contactBox.setModel(new DefaultComboBoxModel());
			contactArea.setText("No Contacts available");
		}
		else
		{
			contactBox.setModel(new DefaultComboBoxModel(c.toArray()));
			//System.out.println(guiData.getTracer(c.get(0)).getPersonInfo());
			contactArea.setText(guiData.getTracer(c.get(0)).getPersonInfo());
		}
		
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
		Person origin = guiData.findPerson(idVal);
		nameText.setText(origin.getName());
		statusText.setText(origin.getStatus());
		phoneNumberText.setText(origin.getNumber());
		fillContactBox(idVal);
		setOriginalPerson(origin);
	}
	
	//-------------------------------CONSTRUTOR FILLERS------------------------------------------------------------------------------------------------------------------------------------------
	private void addToViewRightPanel()
	{
		//set up viewRightPanel by adding all the elements
		viewRightPanel = new JPanel();
		viewRightPanel.setLayout(null);
		viewRightPanel.setBackground(new Color(230, 230, 250));
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
		//NumberFormat integerFieldFormatter = NumberFormat.getIntegerInstance();
		//integerFieldFormatter.setMaximumFractionDigits(0);
		//integerFieldFormatter.setGroupingUsed(false);
		idText = new JTextField();
		//idText.setBackground(new Color(255, 255, 255));
		idText.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		idText.setText("000000");
		idText.setEditable(true);
		idText.setForeground(Color.BLACK);
		//idText.setBackground(Color.blue);
		idText.setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
		idText.setLayout(null);
		idText.setVisible(true);
		idText.setBounds(new Rectangle(200,30));
		idText.setLocation(new Point(30,10));
		idLabel.setLabelFor(idText);
		
		nameText = new JTextField();
		nameText.setText("Default");
		nameText.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		nameText.setEditable(true);
		nameText.setForeground(Color.BLACK);
		//nameText.setBackground(Color.YELLOW);
		nameText.setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
		nameText.setLayout(null);
		nameText.setBounds(new Rectangle(64, 56, 189, 20));
		nameText.setLocation(new Point(64,58));
		nameLabel.setLabelFor(nameText);
		
		phoneNumberText = new JTextField();
		phoneNumberText.setText("0");
		phoneNumberText.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		phoneNumberText.setEditable(true);
		phoneNumberText.setForeground(Color.BLACK);
		//phoneNumberText.setBackground(Color.green);
		phoneNumberText.setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
		phoneNumberText.setLayout(null);
		phoneNumberText.setBounds(new Rectangle(113, 92, 177, 20));
		phoneNumberText.setLocation(new Point(110,92));
		phoneLabel.setLabelFor(phoneNumberText);

		
		statusText = new JTextField();
		statusText.setText("Default");
		statusText.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		statusText.setEditable(true);
		statusText.setForeground(Color.BLACK);
		//statusText.setBackground(Color.green);
		statusText.setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
		statusText.setLayout(null);
		statusText.setBounds(new Rectangle(50, 124, 164, 20));
		statusText.setLocation(new Point(64,124));
		statusLabel.setLabelFor(statusText);
	}
	
	private void setUpPanelBtn()
	{
		btnEditInfo.setBackground(new Color(0, 255, 255));
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
		
		btnDeleteTracer = new JButton("Delete Tracer");
		btnDeleteTracer.setBounds(213, 368, 117, 29);
		viewRightPanel.add(btnDeleteTracer);
		btnDeleteTracer.setVisible(true);
	}
}
