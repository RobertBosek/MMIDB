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
	private JTextField prename;
	private JTextField surname;
	private JTextField id;
	private JTextField id_update;
	private JTextField prename_update;
	private JTextField surname_update;
	private JTextField id_driver_job_done;
	private JTextField currentStreet;
	private JTextField job_done;
	private JTextField Date;
	private JTextField Clock;
	private JTextField street;
	private JTextField avenue;
	private JTextField currentAvenue;
	private JTextField prenameDriver;
	private JTextField surnameDriver;
	private JTextField idDriverJobDone;
	private JTextField destinationStreet;
	private JTextField destinationAvenue;
	int [] pickUpAddress = new int[3];
	int [] destinationAddress = new int[3];
	int driverID;
	 int[] home = new int[2];
	 
	 private JTextField pickUpStreet;
	 private JTextField pickUpAvenue;
	 
	//DB Connection
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DBInterface window = new DBInterface();
					window.frame.setVisible(true);
					
					 int[] home = {50,50};
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
		
		
		JButton btnAuftragAnlegen = new JButton("Auftrag anlegen");
		btnAuftragAnlegen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				 pickUpAddress[0]=Integer.parseInt(pickUpStreet.getText().toString());
					pickUpAddress[1]=Integer.parseInt(pickUpAvenue.getText().toString());
					System.out.println(Integer.parseInt(pickUpStreet.getText().toString())+Integer.parseInt(pickUpAvenue.getText().toString()));
					destinationAddress[0]=Integer.parseInt(destinationStreet.getText().toString());
					destinationAddress[1]=Integer.parseInt(destinationAvenue.getText().toString());
					System.out.println(Integer.parseInt(destinationStreet.getText().toString())+Integer.parseInt(destinationAvenue.getText().toString()));
					db.insertJob(pickUpAddress, destinationAddress);
				}
		});
		
		//Fahrer einfügen
		JButton btnInsertDriver = new JButton("Fahrer eintragen");
		btnInsertDriver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String[] driverName = new String[3];
				driverName [0] = prename.getText();
				driverName [1] = surname.getText();
				
				home[0] = Integer.parseInt(street.getText().toString()); 
				home[1] = Integer.parseInt(avenue.getText().toString());
				
				driverID = Integer.parseInt(db.insertDriver(driverName)); //DRIVERID ZEIGEN
				db.getAddressID(home);
				
				//Nach Eingabe der Daten, lösche die Felder
				prenameDriver.setText(null);
				surnameDriver.setText(null);
				currentStreet.setText(null);
				currentAvenue.setText(null);
			}
		});
		JButton btnUpdateDriver = new JButton("Fahrer \u00E4ndern");
		
		btnUpdateDriver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int idToUpdate = Integer.parseInt(id_update.getText().toString());
				String[] driverNameUpdate = new String[3];
				driverNameUpdate [0] = prename_update.getText();
				driverNameUpdate [1] = surname_update.getText();
				db.updateDriver(idToUpdate, driverNameUpdate);
				prename.setText(null);
				surname.setText(null);
			}
		});
		
		//nächsten Auftrag abrufen
		JButton btnGetNextAssignment = new JButton("n\u00E4chsten Auftrag abrufen");
		btnGetNextAssignment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		
		//Auftrag als erledigt markieren
		JButton btnAssignmentDelivered = new JButton("Auftrag erledigt");
		btnAssignmentDelivered.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int idDriverJobDoneInt = Integer.parseInt(idDriverJobDone.getText().toString());
				db.finishedJob(idDriverJobDoneInt);
				}
		});
		
		prename = new HintTextField("Prename");
		prename.setColumns(10);
		
		surname = new HintTextField("Surname");
		surname.setColumns(10);
		
		id_update = new HintTextField("id");
		id_update.setColumns(10);
		
		prename_update = new HintTextField("New prename");
		prename_update.setColumns(10);
		
		surname_update = new HintTextField("New surname");
		surname_update.setColumns(10);
		
		id_driver_job_done = new HintTextField("id");
		id_driver_job_done.setColumns(10);
		
		currentStreet = new HintTextField("Current Str. Nr.");
		currentStreet.setColumns(10);
		
		job_done = new JTextField();
		job_done.setColumns(10);
		
		Date = new HintTextField("Date");
		Date.setColumns(10);
		
		Clock = new HintTextField("Clock");
		Clock.setColumns(10);
		
		street = new HintTextField("Str. Nr");
		street.setColumns(10);
		
		avenue = new HintTextField("Av. Nr.");
		avenue.setColumns(10);
		
		currentAvenue = new HintTextField("Current Av. Nr.");
		currentAvenue.setColumns(10);
		
		pickUpStreet = new HintTextField("Start Str. Nr.");
		pickUpStreet.setColumns(10);
		
		pickUpAvenue = new HintTextField("Start Av. Nr.");
		pickUpAvenue.setColumns(10);
		
		HintTextField destinationStr = new HintTextField("Ziel Str. Nr.");
		destinationStr.setColumns(10);
		
		HintTextField destinationAv = new HintTextField("Ziel Av. Nr.");
		destinationAv.setColumns(10);
		
		JLabel lblAuftragErledigt = new JLabel("Auftrag erledigt");
		
		JLabel lblNchstenAuftrag = new JLabel("n\u00E4chsten Auftrag");
		
		JLabel lblFahrerndern = new JLabel("Fahrer \u00E4ndern");
		
		JLabel lblFahrerndern_1 = new JLabel("Fahrer \u00E4ndern");
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(lblManager, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblFahrerndern_1, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblFahrerndern, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(id_update, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(prename_update, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(surname_update, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnUpdateDriver, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
									.addComponent(prename, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(surname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(street, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(avenue, 0, 0, Short.MAX_VALUE))
								.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
									.addComponent(Date, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(Clock, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(pickUpStreet, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(pickUpAvenue, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(destinationStr, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(destinationAv, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnAuftragAnlegen, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnInsertDriver, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblFahrer, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNchstenAuftrag, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(currentStreet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(currentAvenue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnGetNextAssignment, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblAuftragErledigt, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(id_driver_job_done, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(job_done, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAssignmentDelivered, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(36, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(25)
					.addComponent(lblManager, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(Clock, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(pickUpStreet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(pickUpAvenue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(destinationStr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(destinationAv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(Date, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAuftragAnlegen))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblFahrerndern_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(prename, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(surname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(street, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(avenue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnInsertDriver, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblFahrerndern)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(id_update, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(prename_update, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(surname_update, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnUpdateDriver))
					.addGap(42)
					.addComponent(lblFahrer, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNchstenAuftrag)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(currentStreet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(currentAvenue, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnGetNextAssignment))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblAuftragErledigt)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(id_driver_job_done, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(job_done, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAssignmentDelivered))
					.addGap(42))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
