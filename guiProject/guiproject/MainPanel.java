package guiproject;

import java.awt.*;
import java.text.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.util.*;

public class MainPanel extends JPanel 
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
	private JButton btnDeleteThisCon;
	
	
	private JPanel deleteTPanel;
	private JOptionPane contactOption;
	private JOptionPane addTracerOption;
	private JOptionPane deleteTracerOption;
	
	private Person original;
	private Person addedContact;
	private Person addedPerson;
	private Person deletedContact;
	
	private JRadioButton NotInfectedRadio;
	private JRadioButton InfectedRadio;
	private JRadioButton PendingRadio;
	
	private boolean cValue;
	private boolean pValue;
	private boolean dValue;

	private int listIndex = 0;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	


	



	
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
		contactBox.setBounds(5, 185, 171, 27);
		

		
		
		contactScrollPane = new JScrollPane();
		contactScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		contactScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
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
	
		
		//set up the buttons(Edit info, Cancel, Save, Add New Contact, Safe Radio, Pending Radio, Infected Radio)
		setUpPanelBtn();

		
		
		rightPanel.setPreferredSize(scrollRightPane.getSize());
		rightPanel.add(scrollRightPane);
		rightPanel.setVisible(true);

		//add the two panes to this JPanel
		dataSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		dataSplit.setBackground(new Color(255, 240, 245));
		dataSplit.setDividerSize(7);
		dataSplit.setDividerLocation(MAIN_X/3);
		
		dataSplit.setPreferredSize(new Dimension(MAIN_X,MAIN_Y));
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
		btnDeleteThisCon.setVisible(false);
		
		
		
		
		//fill all the right panel based on selectedItem from nameList
		//nameList.addListSelectionListener(new nameListSelectListener());
		nameList.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
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
		
		//implements the "Delete This Contact" button on the rightPanel
		btnDeleteThisCon.addActionListener(new btnDeleteThisConListener());
		
		
		RadioGroupButtonListener listenToRadioGroup= new RadioGroupButtonListener();
		//implements the "Pending Radio" Radio button to set status from group
		PendingRadio.addActionListener(listenToRadioGroup);
		
		//implements the "Pending Radio" Radio button to set status from group
		InfectedRadio.addActionListener(listenToRadioGroup);
		
		//implements the "Pending Radio" Radio button to set status from group
		NotInfectedRadio.addActionListener(listenToRadioGroup);
		
		
		
	
	}
	
	
	
	//--------------------------------------FIRING LISTENERS-------------------------------------------------------------------------------------------------------------------------------
	
	//implements the listner for the "Delete this Contact" Item
	private class btnDeleteThisConListener implements ActionListener
	{

		
		public void actionPerformed(ActionEvent e)
		{
//			first, use a JOptionPane to ask if they want to delete the tracer
			
			String[] customOption = {
					"Yes, Delete Contact","No, Go Back"
			};
			int result = JOptionPane.showOptionDialog(null, "Are you sure you want to delete this Contact and discard all unsaved changes", "Confirm Delete",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, customOption, customOption[0]);
			
			if(result == JOptionPane.YES_OPTION)
			{
				Person toRemove = guiData.findPerson(contactBox.getSelectedItem().toString());
				setDeletedContact(toRemove);
				guiData.removeContact(getOriginalPerson(), toRemove.getId());
				fillContactBox(getOriginalPerson().getId());
				nameList.enable();
			}
			btnAddContactInfo.setVisible(false);
			btnEditInfo.setVisible(true);
			btnSave.setVisible(false);
			btnCancel.setVisible(false);
			btnDeleteThisCon.setVisible(false);
		}
	}
	
	
	//implements the listener for the "Exit" menu Item--------------------------------------
	private class MenuExitListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{//immediately exits program without saving
			System.exit(0);
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
				if(jfc.getSelectedFile().getName().equals("output.txt"))
				{
					JOptionPane.showMessageDialog(null, "output.txt is a write-only file, cannot be read");
					return;
				}
				
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
				doClose();
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
			JLabel IdOption = new JLabel("Enter 6-Digit ID: ");
			IdOption.setLabelFor(tracerId);
			deleteTPanel.add(IdOption);
			deleteTPanel.add(tracerId);
			
			//custom fields
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
					if(tempResult == JOptionPane.YES_OPTION)//ask user input
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
			//while id is not valid and the lenght is not 6, and the user has not hit yes
			
					
			
			
		
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
			JLabel numberOption = new JLabel("Number: ");
			JLabel extraNumLabel = new JLabel("Enter 10-Digit Phone Number, ex: xxxxxx2345");
			numberOption.setLabelFor(tracerName);
			JLabel statusOption = new JLabel("Status: ");
			JLabel extraStatLabel = new JLabel("Enter -1 for Not Infected; 0 for Pending; 1 for Infected");
			statusOption.setLabelFor(tracerStatus);
			JLabel IdOption = new JLabel("ID: ");
			JLabel extraIdLabel = new JLabel("Enter 6-Digit ID, ex: xxx123");
			IdOption.setLabelFor(tracerId);
			
			//custom fields on dialog box
			Object[] fields = {
					nameOption, tracerName,
					numberOption, extraNumLabel,tracerNumber,
					statusOption, extraStatLabel, tracerStatus,
					IdOption, extraIdLabel,tracerId
			};

			int tempResult = 0, tempId;
			boolean idExam = false, numExam = false, failedStatus = false;
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
								
								String temp = tracerStatus.getText().toString();
								failedStatus = false;
								if(temp.equals("1"))
								{
									addedTracer.setStatus("Infected");
								}
								else if(temp.equals("0"))
								{
									addedTracer.setStatus("Pending");
								}
								else if(temp.equals("-1"))
								{
									addedTracer.setStatus("Not Infected");
								}
								else
								{
									failedStatus = true;
									throw new Exception();
								}
								
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
					JOptionPane.showMessageDialog(null, "Error has occured, Please enter a 6-Digit ID \nand a 10-Digit Phone Number\nand a valid Status identifier(-1,0,1)");
				}
			}while((idExam || numExam || failedStatus) && (tempResult == JOptionPane.OK_OPTION));
			


			
		}
	}
	
	//implements the JRadioButton "Infected" actionListener-------------------------------------------------------------------
	private class RadioGroupButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			statusText.setEditable(true);
			 JRadioButton button = (JRadioButton) e.getSource();
			 
		        if (button == InfectedRadio) {
		 
		            // option InfectedRadio is selected
		        	statusText.setText(button.getText().toString());
		        	
		 
		        } else if (button == NotInfectedRadio) {
		 
		            // option NotInfectedRadio is selected
		        	statusText.setText(button.getText().toString());
		 
		        } else if (button == PendingRadio) {
		 
		            // option PendingRadio is selected
		        	statusText.setText(button.getText().toString());
		        }
		        statusText.setEditable(false);
			
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
			JLabel numberOption = new JLabel("Number: ");
			numberOption.setLabelFor(contactName);
			JLabel statusOption = new JLabel("Status: ");
			JLabel extraLabel = new JLabel("Enter -1 for Not Infected; 0 for Pending; 1 for Infected");
			statusOption.setLabelFor(contactStatus);
			JLabel IdOption = new JLabel("ID: ");
			IdOption.setLabelFor(contactId);
			JLabel extraNumLabel = new JLabel("Enter 10-Digit Phone Number, ex: xxxxxx2345");
			JLabel extraIdLabel = new JLabel("Enter 6-Digit ID, ex: xxx123");
			
			//custom fields for dialog box
			Object[] fields = {
					nameOption, contactName,
					numberOption, extraNumLabel,contactNumber,
					statusOption, extraLabel, contactStatus,
					IdOption, extraIdLabel,contactId
			};
			
			int tempResult = 0, tempId;
			boolean idExam = false, numExam = false, failedStatus = false;
			
			//validate dialog box
			do
			{
				try
				{
					tempResult = JOptionPane.showConfirmDialog(null, fields,"Please Enter Contact Info", JOptionPane.OK_CANCEL_OPTION);
					
					if(tempResult == JOptionPane.OK_OPTION)
					{//if added contact is valid, add that contact to the current tracer and create a new tracer with contact info
						//System.out.println(contactName.getText().toString());
						//parse fields and check for validation
						tempId = Integer.parseInt(contactId.getText().toString());
						//if exam is true, failed input, so validate
						idExam = (tempId > 999999 || tempId < 000000 || (contactId.getText().toString().length() != 6));
						numExam = InvalidPhoneNumber(contactNumber.getText().toString());
						if(idExam || numExam)
							throw new Exception();
						else
						{
							//if the person isn't already a tracer
							if(!(guiData.containsContact(getOriginalPerson(), contactId.getText().toString())))	//if added contact exist or not
							{
								Person anotherTracer = new Person();
								anotherTracer.setName(contactName.getText().toString());
								anotherTracer.setNumber(contactNumber.getText().toString());
								
								String temp = contactStatus.getText().toString();
								failedStatus = false;
								if(temp.equals("1"))
								{
									anotherTracer.setStatus("Infected");
								}
								else if(temp.equals("0"))
								{
									anotherTracer.setStatus("Pending");
								}
								else if(temp.equals("-1"))
								{
									anotherTracer.setStatus("Not Infected");
								}
								else
								{
									failedStatus = true;
									throw new Exception();
								}
								anotherTracer.setId(contactId.getText().toString());
								//update contactBox with new id but not save
								guiData.addTracer(anotherTracer);
								contactArea.setText("Added Contact: " + anotherTracer.getPersonInfo());
								setAddedContact(anotherTracer);
							}
							else
								JOptionPane.showMessageDialog(null,"ID: " + contactId.getText() +  " already exists as a Tracer");
						}
						
					}
					else
						tempResult = JOptionPane.CANCEL_OPTION;
				}
				catch(Exception j)
				{
					JOptionPane.showMessageDialog(null, "Error has occured, Please enter a 6-Digit ID \nand a 10-Digit Phone Number\nand a valid Status identifier(-1,0,1)");
				}
			}while((idExam || numExam || failedStatus) && (tempResult == JOptionPane.OK_OPTION));
	
	
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
			resetStatustoOriginal();
			
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
			btnDeleteThisCon.setVisible(false);
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
					resetStatustoOriginal();
				}
			}while(numExam||idExam);
			
			
			
			
			//-------------------------
			
			Person new_P = new Person();
			new_P.setId(idText.getText().toString());
			new_P.setName(nameText.getText().toString());
			
			
			statusText.setText(buttonGroup.getSelection().getActionCommand());
			new_P.setStatus(statusText.getText().toString());
			new_P.setNumber(phoneNumberText.getText().toString());
			
			//get all the contacts of original person
			ArrayList<String> c = guiData.getAllContactsOf(getOriginalPerson().getId());
			if(getAddedContactSet())
			{
				//add contact to list, if set
				c.add(getAddedContact().getId());
				guiData.addTracer(getAddedContact());
				updatenameList();
				isAddedContactSet(false);
			
			}
			//set the model and add
			contactBox.setModel(new DefaultComboBoxModel(c.toArray()));
			contactArea.setText(guiData.getTracer(c.get(0)).getPersonInfo());
			for(int i = 0; i < contactBox.getItemCount(); i++)
			{
				new_P.addContactID(contactBox.getItemAt(i).toString());
				
			}
			
			//update person info if id is same
			if(getOriginalPerson().getId().equals(new_P.getId()))
			{
				guiData.updateTracer(new_P);
				setOriginalPerson(new_P);
			}
			//if id isn't same, and is not it data, error
			else if(!(getOriginalPerson().getId().equals(new_P.getId()) && guiData.containsTracer(guiData.findPerson(new_P.getId()))))
			{
				JOptionPane errorPopUp = new JOptionPane("Error");
				errorPopUp.showMessageDialog(null, "This ID is already taken!");
				errorPopUp.setOptionType(errorPopUp.DEFAULT_OPTION);
				idText.setText(getOriginalPerson().getId().toString());
			}
			else
			{//add the new tracer
				guiData.addTracer(new_P);
				guiData.removeTracer(getOriginalPerson());
				setOriginalPerson(new_P);
			}
			btnCancel.setVisible(false);
			btnSave.setVisible(false);
			btnEditInfo.setVisible(true);
			btnAddContactInfo.setVisible(false);
			btnDeleteTracer.setVisible(true);
			btnDeleteThisCon.setVisible(false);
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
			btnDeleteThisCon.setVisible(true);
			idText.setEditable(true);
			nameText.setEditable(true);
			//statusText.setEditable(true);
			phoneNumberText.setEditable(true);
			PendingRadio.setEnabled(true);
			InfectedRadio.setEnabled(true);
			NotInfectedRadio.setEnabled(true);
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
			btnDeleteThisCon.setVisible(false);
			
			
			
	
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
	

	private void setDeletedContact(Person added)
	{
		if(!(added == null))
		{
			deletedContact = added;
			isdeletedContactSet(true);
		}
		else
		{
			System.out.println("assigning null reference");
		}
	}
	
	public Person getDeletedContact()
	{
		if(getAddedContactSet())
			return deletedContact;
		else
		{
			System.out.println("Error");
			return null;
		}
	}
	
	private void isdeletedContactSet(boolean val) {
		dValue = val;
	}
	
	private boolean getdeletedContactSet()
	{
		return dValue;
	}

	//--------
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
	
	public void doClose()
	{//closes the file and saves the data
		guiData.doGuiWrite(guiData.getReadFileName());
		guiData.writeFile();
		JOptionPane.showMessageDialog(null, "Data is saved in the "+guiData.getReadFileName()+" file and output.txt file");
		System.exit(0);
	}
	
	
	private void resetStatustoOriginal()
	{//set the radio buttons to original set
		if(getOriginalSet())
		{
			if(getOriginalPerson().getStatus().equals(PendingRadio.getActionCommand()))
			{
				PendingRadio.setEnabled(true);
				PendingRadio.setSelected(true);
				InfectedRadio.setEnabled(false);
				NotInfectedRadio.setEnabled(false);
			}
			else if(getOriginalPerson().getStatus().equals(InfectedRadio.getActionCommand()))
			{
				InfectedRadio.setEnabled(true);
				InfectedRadio.setSelected(true);
				NotInfectedRadio.setEnabled(false);
				PendingRadio.setEnabled(false);
			}
			else if(getOriginalPerson().getStatus().equals(NotInfectedRadio.getActionCommand()))
			{
				NotInfectedRadio.setEnabled(true);
				NotInfectedRadio.setSelected(true);
				PendingRadio.setEnabled(false);
				InfectedRadio.setEnabled(false);
			}
			else
				System.out.println("Error!");
		}
	}
	
	//validates the phone number and contains the pattern for the phone number
	private boolean InvalidPhoneNumber(String target) {
	    Pattern pattern = Pattern.compile("^\\d{10}$");
	    Matcher matcher = pattern.matcher(target);
	    return !(matcher.matches());	//return true if validation fails

	  }
	
	//disables the text fields
	private void disableTextFields()
	{
		idText.setEditable(false);
		nameText.setEditable(false);
		statusText.setEditable(false);
		phoneNumberText.setEditable(false);
		contactArea.setEditable(false);
	}
	//fills the contactBox drop down list
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
			contactArea.setText(guiData.getTracer(c.get(0)).getPersonInfo());
		}
		
	}
	
	//gets the valid id from the string on the list
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
	
	//fills the info on the right based on the chosen person from list
	private void fillInfo(String selectedValue) //nameList method to fill info, parameter taken is id,name
	{
		String idVal = getSelectedID(selectedValue);
		idText.setText(idVal);
		Person origin = guiData.findPerson(idVal);
		nameText.setText(origin.getName());
		statusText.setText(setRadioStatus(origin.getStatus()));
		phoneNumberText.setText(origin.getNumber());
		fillContactBox(idVal);
		setOriginalPerson(origin);
	}
	
	//sets the status of the radio buttons
	private String setRadioStatus(String target)
	{
		if(target.toLowerCase().equals("not infected"))
		{
			NotInfectedRadio.setEnabled(true);
			NotInfectedRadio.setSelected(true);
			InfectedRadio.setEnabled(false);
			PendingRadio.setEnabled(false);
		}
		else if(target.toLowerCase().equals("infected"))
		{
			InfectedRadio.setEnabled(true);
			InfectedRadio.setSelected(true);
			PendingRadio.setEnabled(false);
			NotInfectedRadio.setEnabled(false);
		}
		else if(target.toLowerCase().equals("pending"))
		{
			PendingRadio.setEnabled(true);
			PendingRadio.setSelected(true);
			InfectedRadio.setEnabled(false);
			NotInfectedRadio.setEnabled(false);
		}
		else
			System.out.println("error,setRadioStatus");
		return buttonGroup.getSelection().getActionCommand().toString();
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
		statusLabel.setBounds(10, 119, 95, 30);
		contactLabel = new JLabel("ID of Contacts: ");
		contactLabel.setBounds(10, 167, 100, 20);
		
		//set up JFormattedTextFields
		//NumberFormat integerFieldFormatter = NumberFormat.getInt95erI20tance();
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
		statusText.setBounds(new Rectangle(64, 124, 200, 20));
		statusText.setLocation(new Point(64,124));
		statusLabel.setLabelFor(statusText);
	}
	
	private void setUpPanelBtn()
	{
		btnEditInfo.setBackground(new Color(0, 255, 255));
		btnEditInfo.setBounds(30, 370, 117, 29);
		viewRightPanel.add(btnEditInfo);
		
		btnAddContactInfo = new JButton("Add New Contact");
		btnAddContactInfo.setBounds(170, 185, 134, 29);
		viewRightPanel.add(btnAddContactInfo);
		btnAddContactInfo.setVisible(false);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(10, 370, 86, 29);
		viewRightPanel.add(btnCancel);
		btnCancel.setVisible(false);
		
		btnSave = new JButton("Save");
		btnSave.setBounds(102, 370, 86, 29);
		viewRightPanel.add(btnSave);
		scrollRightPane.setPreferredSize(viewRightPanel.getSize());
		btnSave.setVisible(false);
		
		btnDeleteThisCon = new JButton("Delete This Contact");
		btnDeleteThisCon.setBounds(302, 185, 153, 29);
		viewRightPanel.add(btnDeleteThisCon);
		
		btnDeleteTracer = new JButton("Delete Tracer");
		btnDeleteTracer.setBounds(213, 370, 117, 29);
		viewRightPanel.add(btnDeleteTracer);
		btnDeleteTracer.setVisible(true);
		
		NotInfectedRadio = new JRadioButton("Not Infected");
		NotInfectedRadio.setActionCommand("Not Infected");
		buttonGroup.add(NotInfectedRadio);
		NotInfectedRadio.setBounds(158, 143, 110, 23);
		viewRightPanel.add(NotInfectedRadio);
		
		PendingRadio = new JRadioButton("Pending");
		PendingRadio.setActionCommand("Pending");
		PendingRadio.setSelected(true);
		buttonGroup.add(PendingRadio);
		PendingRadio.setBounds(268, 122, 86, 23);
		viewRightPanel.add(PendingRadio);
		
		InfectedRadio = new JRadioButton("Infected");
		InfectedRadio.setActionCommand("Infected");
		InfectedRadio.setSelected(true);
		buttonGroup.add(InfectedRadio);
		InfectedRadio.setBounds(64, 143, 86, 23);
		viewRightPanel.add(InfectedRadio);
		
		
	}
}
