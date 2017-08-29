import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.InputMismatchException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class DBInterface {

	public static DBAdministration db;
	private JFrame frame;
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
	private String jobID;
	private JTextField pickUpStreet;
	private JTextField pickUpAvenue;
	private JTextField idDriverGetNextAssignment;
	private JLabel lblJobID;
	 
	//DB Connection
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

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 497, 437);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblManager = new JLabel("Manager");
		lblManager.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JLabel lblFahrer = new JLabel("Fahrer");
		lblFahrer.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JButton btnNewAssignment = new JButton("Auftrag anlegen");
		
		btnNewAssignment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					pickUpAddress[0]=Integer.parseInt(pickUpStreet.getText().toString());
					pickUpAddress[1]=Integer.parseInt(pickUpAvenue.getText().toString());
					destinationAddress[0] = Integer.parseInt(destinationStreet.getText().toString());
					destinationAddress[1] = Integer.parseInt(destinationAvenue.getText().toString());
					//Auftragsnummer
					jobID = (db.insertJob(pickUpAddress, destinationAddress)); 
				}
				catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(frame, "Sie haben keinen numerischen Wert eingegeben!");
				}
			}	
		});
		
		//Fahrer einfügen
		JButton btnInsertDriver = new JButton("Fahrer eintragen");
		btnInsertDriver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				boolean hasNumbers = false;
				String[] driverName = new String[2];
				driverName [0] = prenameDriver.getText();
				driverName [1] = surnameDriver.getText();

				for(int i = 0; i < driverName[0].length(); i++) {
					for(int j = 0; j < driverName[1].length(); j++) {
						if(driverName[0].charAt(i) >'a' && driverName[0].charAt(i) <'z') {
							if(driverName[1].charAt(j) >'a') {
								if(driverName[1].charAt(j) <'z'){
									hasNumbers = false;
									}
								}
							}else {
								hasNumbers = true;
								}
						}
					}
				if(hasNumbers == true) {
					JOptionPane.showMessageDialog(frame, "Die Namen bestehen nur aus Buchstaben!");
					} else { 
						db.getAddressID(home);
						}
				}
			});
		
		JButton btnUpdateDriver = new JButton("Fahrer \u00E4ndern");
		btnUpdateDriver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int idToUpdate = 0;
				try {
					idToUpdate = Integer.parseInt(idUpdateDriver.getText().toString());
				}
				catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(frame, "FahrerID besteht nur aus Ziffern!");
				}
				
				String[] driverNameUpdate = new String[3];
				driverNameUpdate [0] = prenameUpdate.getText();
				driverNameUpdate [1] = surnameUpdate.getText();
				db.updateDriver(idToUpdate, driverNameUpdate);
				
			}
		});
		
		//nächsten Auftrag abrufen
		JButton btnGetNextAssignment = new JButton("n\u00E4chsten Auftrag abrufen");
		btnGetNextAssignment.addActionListener(new ActionListener() {
		//Nur Zahlen sind erlaubt
			public void actionPerformed(ActionEvent arg0) {
				try {
					int driverIDGetNextJob = Integer.parseInt(idDriverGetNextAssignment.getText().toString());
					jobID = db.getJob(driverIDGetNextJob);
					lblJobID.setText(jobID);
				}
				catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(frame, "FahrerID besteht nur aus Ziffern!");
				}
			}
		});

		//Auftrag als erledigt markieren
		JButton btnAssignmentDelivered = new JButton("Auftrag erledigt");
		btnAssignmentDelivered.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int jobDoneDriverID = Integer.parseInt(idDriverJobDone.getText().toString());
					db.finishedJob(jobDoneDriverID);
				}
				catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(frame, "Sie haben keinen numerischen Wert eingegeben!");
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
		
		lblJobID = new JLabel("Auftragsnummer");
		
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
									.addComponent(pickUpAvenue, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(destinationStreet, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
									.addGap(6)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(destinationAvenue, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnNewAssignment, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(prenameDriver, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(surnameDriver, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnInsertDriver, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED))
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
							.addComponent(btnGetNextAssignment, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
							.addGap(14)
							.addComponent(lblJobID, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE))
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
					.addContainerGap(27, GroupLayout.PREFERRED_SIZE))
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
						.addComponent(lblJobID)
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
