import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class DBInterface {
	
	public static DBAdministration db;
	public JFrame frame;
	private JTextField prenameDriver;
	private JTextField surnameDriver;
	private JTextField idUpdateDriver;
	private JTextField prenameUpdate;
	private JTextField surnameUpdate;
	private JTextField idDriverJobDone;
	private JTextField street;
	private JTextField destinationStreet;
	private JTextField destinationAvenue;
	private int[] pickUpAddress = new int[3];
	private int[] destinationAddress = new int[3];
	private int[] home = new int[2];
	private JTextField pickUpStreet;
	private JTextField pickUpAvenue;
	private JTextField idDriverGetNextAssignment;
	 
	//Database Connection
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DBInterface window = new DBInterface();
					window.frame.setVisible(true);
					String url = "jdbc:mysql://132.199.139.24:3306/mmdb17_robertbosek?user=r.bosek&password=mmdb";
				    db = new DBAdministration(url);
					} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public DBInterface() {
		initialize();
	}
	
	//initialize the UI-Elements and 
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 497, 437);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblManager = new JLabel("Manager");
		lblManager.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JLabel lblFahrer = new JLabel("Fahrer");
		lblFahrer.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		//Input the address of the new order
		JButton btnNewAssignment = new JButton("Auftrag anlegen");
		btnNewAssignment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					pickUpAddress[0]=Integer.parseInt(pickUpStreet.getText().toString());
					pickUpAddress[1]=Integer.parseInt(pickUpAvenue.getText().toString());
					destinationAddress[0] = Integer.parseInt(destinationStreet.getText().toString());
					destinationAddress[1] = Integer.parseInt(destinationAvenue.getText().toString());
					//jobID is the number of the order
					String newOrder = db.insertJob(pickUpAddress, destinationAddress);
					JOptionPane.showMessageDialog(frame, newOrder);
				}
				catch (NumberFormatException nfe) {
					//Show a dialog if the input has been incorrect
					JOptionPane.showMessageDialog(frame, "Bitte geben Sie nur die Nummer der Strasse und der Avenue ein!");
				}
			}	
		});
		
		//Insert the name of the new driver
		JButton btnInsertDriver = new JButton("Fahrer eintragen");
		btnInsertDriver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				boolean hasNumbers = false;
				String[] driverName = new String[2];
				
				//Convert the user's input to lower case and then check if the input contains only letters
				driverName [0] = prenameDriver.getText().toLowerCase();
				driverName [1] = surnameDriver.getText().toLowerCase();

				for(int i = 0; i < driverName[0].length(); i++) {
					for(int j = 0; j < driverName[1].length(); j++) {
						if(driverName[0].charAt(i) >'a' && driverName[0].charAt(i) <'z') {
							if(driverName[1].charAt(j) >='a' && driverName[1].charAt(j) <='z'){
									hasNumbers = false;
									}
							} else {
								hasNumbers = true;
								}
						}
					}
				
				//If there is no input or if there are digits in the input, then this input is incorrect
				if(hasNumbers == true || driverName[0].equals("") || driverName [1].equals("")) {
					JOptionPane.showMessageDialog(frame, "Die Namen sollten aus Buchstaben bestehen!");
					} else { 
						db.getAddressID(home);
						String insertDriver = db.insertDriver(driverName);
						JOptionPane.showMessageDialog(frame, insertDriver);
						}
				}
			});
		
		//Change the names of a driver
		JButton btnUpdateDriver = new JButton("Fahrer \u00E4ndern");
		btnUpdateDriver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int idToUpdate = 0;
				try {
					idToUpdate = Integer.parseInt(idUpdateDriver.getText().toString());
				}
				catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(frame, "FahrerID sollte nur aus Ziffern bestehen!");
				}
				
				String[] driverNameUpdate = new String[3];
				
				//Convert the user's input to lower case and then check if the input contains only letters
				driverNameUpdate [0] = prenameUpdate.getText().toLowerCase();
				driverNameUpdate [1] = surnameUpdate.getText().toLowerCase();
				boolean hasNumbers = false;

				for(int i = 0; i < driverNameUpdate[0].length(); i++) {
					for(int j = 0; j < driverNameUpdate[1].length(); j++) {
						if(driverNameUpdate[0].charAt(i) >'a' && driverNameUpdate[0].charAt(i) <'z') {
							if(driverNameUpdate[1].charAt(j) >'a' && driverNameUpdate[1].charAt(j) <'z')
								hasNumbers = false;
								} else {
								hasNumbers = true;
								}
						}
					}
				
				//If there is no input or if there are digits in the input, then this input is incorrect
				if(hasNumbers == true || driverNameUpdate[0].equals("") || driverNameUpdate[1].equals("")) {
					//Show a dialog if the input has been incorrect
					JOptionPane.showMessageDialog(frame, "Die Namen sollten nur aus Buchstaben bestehen!");
					} else { 
						//Show a dialog with the current state of the process
						String updateDriver = db.updateDriver(idToUpdate, driverNameUpdate);
						JOptionPane.showMessageDialog(frame, updateDriver);
						}
				}
			});
		
		//Get the next Order
		JButton btnGetNextAssignment = new JButton("n\u00E4chsten Auftrag abrufen");
		btnGetNextAssignment.addActionListener(new ActionListener() {
			
		//The input (FahrerID) must contain only digits
			public void actionPerformed(ActionEvent arg0) {
				try {
					int driverIDGetNextJob = Integer.parseInt(idDriverGetNextAssignment.getText().toString());
					String getJob = db.getJob(driverIDGetNextJob);
					JOptionPane.showMessageDialog(frame, getJob);
				}
				catch (NumberFormatException nfe) {
					//Show a dialog if the input has not been correct
					JOptionPane.showMessageDialog(frame, "FahrerID sollte nur aus Ziffern bestehen!");
				}
			}
		});

		//Mark the order as delivered
		JButton btnAssignmentDelivered = new JButton("Auftrag erledigt");
		btnAssignmentDelivered.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int jobDoneDriverID = Integer.parseInt(idDriverJobDone.getText().toString());
					
					//Show a dialog with the current state of the process
					String finishedJob = db.finishedJob(jobDoneDriverID);
					JOptionPane.showMessageDialog(frame, finishedJob);
				}
				//If the input contains letters, then this is a NumberFormatException
				catch (NumberFormatException nfe) {
					//Show a dialog with the input of driver's id contains letters
					JOptionPane.showMessageDialog(frame, "FahrerID sollte nur aus Ziffern bestehen!");
				}
			}
		});
		
		prenameDriver = new HintTextField("Prename");
		prenameDriver.setColumns(10);
		
		surnameDriver = new HintTextField("Surname");
		surnameDriver.setColumns(10);
		
		idUpdateDriver = new HintTextField("FahrerID");
		idUpdateDriver.setColumns(10);
		
		prenameUpdate = new HintTextField("New prename");
		prenameUpdate.setColumns(10);
		
		surnameUpdate = new HintTextField("New surname");
		surnameUpdate.setColumns(10);
		
		idDriverJobDone = new HintTextField("FahrerID");
		idDriverJobDone.setColumns(10);
		
		street = new HintTextField("Str. Nr");
		street.setColumns(10);
		
		pickUpStreet = new HintTextField("Start Str. Nr.");
		pickUpStreet.setColumns(10);
		
		pickUpAvenue = new HintTextField("Start Av. Nr.");
		pickUpAvenue.setColumns(10);
		
		destinationStreet = new HintTextField("Ziel Str. Nr.");
		destinationStreet.setColumns(10);
		
		destinationAvenue = new HintTextField("Ziel Av. Nr.");
		destinationAvenue.setColumns(10);
		
		idDriverGetNextAssignment = new HintTextField("FahrerID");
		idDriverGetNextAssignment.setColumns(10);
		
		JLabel lblAuftragErledigt = new JLabel("Auftrag erledigt");
		
		JLabel lblNchstenAuftrag = new JLabel("n\u00E4chsten Auftrag");
		
		JLabel lblFahrerndern = new JLabel("Fahrer \u00E4ndern");
		
		JLabel lblFahrerndern_1 = new JLabel("Fahrer einf\u00FCgen");
		
		JLabel lblAuftragAnlegen = new JLabel("Auftrag anlegen");
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblFahrerndern, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(idUpdateDriver, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(prenameUpdate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(surnameUpdate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnUpdateDriver, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(pickUpStreet, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(pickUpAvenue, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(destinationStreet, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(destinationAvenue, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnNewAssignment, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(prenameDriver, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(surnameDriver, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnInsertDriver, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblFahrer, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNchstenAuftrag, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(idDriverGetNextAssignment, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnGetNextAssignment, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblAuftragErledigt, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(idDriverJobDone, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnAssignmentDelivered, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblFahrerndern_1, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(lblManager, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblAuftragAnlegen)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(25)
					.addComponent(lblManager, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblAuftragAnlegen)
					.addGap(7)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(pickUpStreet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(destinationAvenue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(pickUpAvenue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(destinationStreet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewAssignment))
					.addGap(11)
					.addComponent(lblFahrerndern_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(prenameDriver, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(surnameDriver, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnInsertDriver, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblFahrerndern)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(idUpdateDriver, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(prenameUpdate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(surnameUpdate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnUpdateDriver))
					.addGap(42)
					.addComponent(lblFahrer, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNchstenAuftrag)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(idDriverGetNextAssignment, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnGetNextAssignment))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblAuftragErledigt)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(idDriverJobDone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAssignmentDelivered))
					.addGap(42))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
