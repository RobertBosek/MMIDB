import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
					
					 int[] home = {10, 10};
				     String url = "jdbc:mysql://132.199.139.24:3306/mmdb17_robertbosek?user=r.bosek&password=mmdb";
				     db = new DBAdministration(url, home);
				        
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
				pickUpAddress[0]=Integer.parseInt(pickUpStreet.getText().toString());
				pickUpAddress[1]=Integer.parseInt(pickUpAvenue.getText().toString());
				destinationAddress[0] = Integer.parseInt(destinationStreet.getText().toString());
				destinationAddress[1] = Integer.parseInt(destinationAvenue.getText().toString());
				jobID = (db.insertJob(pickUpAddress, destinationAddress)); //Auftragsnummer
				}
		});
		
		//Fahrer einfügen
		JButton btnInsertDriver = new JButton("Fahrer eintragen");
		btnInsertDriver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String[] driverName = new String[3];
				driverName [0] = prenameDriver.getText();
				driverName [1] = surnameDriver.getText();
				
				int driverID = Integer.parseInt(db.insertDriver(driverName)); //DRIVERID ZEIGEN
				db.getAddressID(home);
			}
		});
		
		JButton btnUpdateDriver = new JButton("Fahrer \u00E4ndern");
		btnUpdateDriver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int idToUpdate = Integer.parseInt(idUpdateDriver.getText().toString());
				String[] driverNameUpdate = new String[3];
				driverNameUpdate [0] = prenameUpdate.getText();
				driverNameUpdate [1] = surnameUpdate.getText();
				db.updateDriver(idToUpdate, driverNameUpdate);
				
			}
		});
		
		//nächsten Auftrag abrufen
		JButton btnGetNextAssignment = new JButton("n\u00E4chsten Auftrag abrufen");
		btnGetNextAssignment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int driverIDGetNextJob = Integer.parseInt(idDriverGetNextAssignment.getText().toString());
				db.getJob(driverIDGetNextJob);
				lblJobID.setText(jobID);
			}
		});
		
		//Auftrag als erledigt markieren
		JButton btnAssignmentDelivered = new JButton("Auftrag erledigt");
		btnAssignmentDelivered.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int jobDoneDriverID = Integer.parseInt(idDriverJobDone.getText().toString());
				db.finishedJob(jobDoneDriverID);
				}
		});
		
		prenameDriver = new HintTextField("Prename");
		prenameDriver.setColumns(10);
		
		surnameDriver = new HintTextField("Surname");
		surnameDriver.setColumns(10);
		
		idUpdateDriver = new HintTextField("id");
		idUpdateDriver.setColumns(10);
		
		prenameUpdate = new HintTextField("New prename");
		prenameUpdate.setColumns(10);
		
		surnameUpdate = new HintTextField("New surname");
		surnameUpdate.setColumns(10);
		
		idDriverJobDone = new HintTextField("id");
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
		
		idDriverGetNextAssignment = new HintTextField("id");
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
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnUpdateDriver, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(prenameDriver, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(surnameDriver, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnInsertDriver, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(pickUpStreet, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(pickUpAvenue, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(destinationStreet, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(destinationAvenue, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnNewAssignment, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblFahrer, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNchstenAuftrag, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(idDriverGetNextAssignment, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnGetNextAssignment, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblJobID))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblAuftragErledigt, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(idDriverJobDone, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
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
					.addContainerGap(101, Short.MAX_VALUE))
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
						.addComponent(pickUpAvenue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(destinationStreet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(destinationAvenue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
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
						.addComponent(btnGetNextAssignment)
						.addComponent(lblJobID))
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
