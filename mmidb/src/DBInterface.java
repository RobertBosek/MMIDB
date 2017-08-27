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
	private JTextField idDriver;
	private JTextField prenameUpdate;
	private JTextField surnameUpdate;
	private JTextField idDriverJobDone;
	private JTextField jobDone;
	private JTextField currentStreet;
	private JTextField destinationStreet;
	private JTextField destinationAvenue;
	public static int[] home = new int[2];
	private JTextField currentAvenue;
	public JTextField pickUpStreet;
	private JTextField pickUpAvenue;
	private JTextField nextJobDriverID;
	int [] pickUpAddress = new int[3];
	int [] destinationAddress = new int[3];
	int driverID;
	//DB Connection
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DBInterface window = new DBInterface();
					window.frame.setVisible(true);
					String url = "jdbc:mysql://132.199.139.24:3306/mmdb17_robertbosek?user=r.bosek&password=mmdb";
					db = new DBAdministration(url, home);
					} catch (Exception e) {
						e.printStackTrace();
						}
				}
			});
		}
	
	public DBInterface() {
		frame = new JFrame();
		frame.setBounds(100, 100, 497, 437);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblManager = new JLabel("Manager");
		lblManager.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JLabel lblFahrer = new JLabel("Fahrer");
		lblFahrer.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		//Auftrag anlegen
		JButton btnCreateOrder = new JButton("Auftrag anlegen");
		btnCreateOrder.addActionListener(new ActionListener() {
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
				driverName [0] = prenameDriver.getText();
				driverName [1] = surnameDriver.getText();
				
				home[0] = Integer.parseInt(currentStreet.getText().toString()); 
				home[1] = Integer.parseInt(currentAvenue.getText().toString());
				
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
				int idToUpdate = Integer.parseInt(idDriver.getText().toString());
				String[] driverNameUpdate = new String[3];
				driverNameUpdate [0] = prenameUpdate.getText();
				driverNameUpdate [1] = surnameUpdate.getText();
				db.updateDriver(idToUpdate, driverNameUpdate);
				prenameDriver.setText(null);
				surnameDriver.setText(null);
				}
			});
		
		//nächsten Auftrag abrufen
		JButton btnGetNextOrder = new JButton("n\u00E4chsten Auftrag abrufen");
		btnGetNextOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				}
			});
		
		//Auftrag als erledigt markieren
		JButton btnOrderDelivered = new JButton("Auftrag erledigt");
		btnOrderDelivered.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int idDriverJobDoneInt = Integer.parseInt(idDriverJobDone.getText().toString());
				db.finishedJob(idDriverJobDoneInt);
				}
			});
		
		prenameDriver = new HintTextField("Prename");
		prenameDriver.setColumns(10);
		
		surnameDriver = new HintTextField("Surname");
		surnameDriver.setColumns(10);
		
		idDriver = new HintTextField("id");
		idDriver.setColumns(10);
		
		prenameUpdate = new HintTextField("New prename");
		prenameUpdate.setColumns(10);
		
		surnameUpdate = new HintTextField("New surname");
		surnameUpdate.setColumns(10);
		
		idDriverJobDone = new HintTextField("id");
		idDriverJobDone.setColumns(10);
		
		jobDone = new JTextField();
		jobDone.setColumns(10);
		
		currentStreet = new HintTextField("Str. Nr");
		currentStreet.setColumns(10);
		
		pickUpAvenue = new HintTextField("Start Av. Nr.");
		pickUpAvenue.setColumns(10);
		
		destinationStreet = new HintTextField("Ziel Str. Nr.");
		destinationStreet.setColumns(10);
		destinationAvenue = new HintTextField("Ziel Av. Nr.");
		destinationAvenue.setColumns(10);
		JLabel lblAuftragErledigt = new JLabel("Auftrag erledigt");
		
		JLabel lblNchstenAuftrag = new JLabel("n\u00E4chsten Auftrag");
		
		JLabel lblFahrerndern = new JLabel("Fahrer \u00E4ndern");
		
		JLabel lblFahrerndern_1 = new JLabel("Fahrer \u00E4ndern");
		
		currentAvenue = new JTextField();
		currentAvenue.setColumns(10);
		
		pickUpStreet = new HintTextField("Pick up Str.");
		pickUpStreet.setColumns(10);
		
		
		
		nextJobDriverID = new HintTextField("next job");
		nextJobDriverID.setColumns(10);
		
		
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
			lblFahrer, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblAuftragErledigt, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(idDriverJobDone, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jobDone, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnOrderDelivered, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(nextJobDriverID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnGetNextOrder, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblNchstenAuftrag, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addContainerGap()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(prenameDriver, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(surnameDriver, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(currentStreet, GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE))
										.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
											.addComponent(pickUpStreet, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(pickUpAvenue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(destinationStreet, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
											.addGap(38))))
								.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
									.addContainerGap()
									.addComponent(idDriver, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(prenameUpdate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(surnameUpdate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(18)
									.addComponent(btnUpdateDriver, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(destinationAvenue, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(25)
											.addComponent(currentAvenue, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)))
									.addGap(16)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(18)
											.addComponent(btnInsertDriver, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(18)
											.addComponent(btnCreateOrder, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)))GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(25)
					.addComponent(lblManager, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(pickUpStreet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCreateOrder)
						.addComponent(destinationAvenue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(destinationStreet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(pickUpAvenue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZEdGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(prenameDriver, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(surnameDriver, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(currentStreet, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(currentAvenue, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnInsertDriver, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblFahrerndern)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(idDriver, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(prenameUpdate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(surnameUpdate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnUpdateDriver))
					.addGap(42)
					.addComponent(lblFahrer, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNchstenAuftrag)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(nextJobDriverID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnGetNextOrder))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblAuftragErledigt)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(idDriverJobDone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(jobDone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnOrderDelivered))
					.addGap(42))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
