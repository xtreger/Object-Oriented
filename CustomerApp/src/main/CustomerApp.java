package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class CustomerApp extends JFrame implements Serializable {

	// form
	private JLabel nameLabel = new JLabel("Customer Name");
	private JTextField nameField = new JTextField(10);
	private JLabel ageLabel = new JLabel("Age");
	private JTextField ageField = new JTextField(10);
	private JLabel emailLabel = new JLabel("Email");
	private JTextField emailField = new JTextField(10);
	private JButton addButton = new JButton("Add");
	private JPanel formPanel = new JPanel();

	private JMenuBar menuBar = new JMenuBar();
	private JMenu menu = new JMenu("File");
	private JMenuItem save, load;

	private CustomerModel tableModel;
	private JTable table = new JTable();
	private JScrollPane scrollPane = new JScrollPane(table);
	private ArrayList<Customer> cust = new ArrayList<Customer>();

	public CustomerApp() {

		// Setting up menu
		save = new JMenuItem("Save As");
		load = new JMenuItem("Load");
		menu.add(save);
		menu.add(load);
		menuBar.add(menu);
		// Setting up JTable
		tableModel = new CustomerModel(cust);
		table.setModel(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(300, 80));
		table.setFillsViewportHeight(true);
		// create form panel
		createFormPanel();

		// default JFileChooser to current location

		// create file save and open action listener
		save.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				File workingDirectory = new File(System.getProperty("user.dir"));
				JFileChooser jfc = new JFileChooser(workingDirectory);
				System.out.println();
				int returnValue = jfc.showSaveDialog(formPanel);

				if (returnValue == JFileChooser.APPROVE_OPTION && !cust.isEmpty()) {
					nameField.setText("");
					ageField.setText("");
					emailField.setText("");

					File selectedFile = jfc.getSelectedFile();
					ObjectOutputStream out = null;
					try {
						out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(selectedFile)));

						out.writeObject(cust);
					} catch (FileNotFoundException d) {
						d.printStackTrace();
					} catch (IOException x) {
						x.printStackTrace();
					} finally {
						try {
							out.close();
						} catch (IOException s) {
							s.printStackTrace();
						}
					}
					cust.clear();
					tableModel.fireTableDataChanged();

				} else {
					JOptionPane.showMessageDialog(formPanel, "There are no records to be addded", "Error",
							JOptionPane.WARNING_MESSAGE);
				}

			}

		});

		load.addActionListener(new ActionListener() {

			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				File workingDirectory = new File(System.getProperty("user.dir"));
				JFileChooser jfc = new JFileChooser(workingDirectory);
				
				int returnValue = jfc.showOpenDialog(formPanel);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					ObjectInputStream in = null;
					try {
						in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(selectedFile)));
						ArrayList<Customer> arr = (ArrayList<Customer>) in.readObject();
						cust.clear();
						cust.addAll(arr);
 						tableModel.fireTableDataChanged();
					} catch (EOFException ex) {
					} catch (FileNotFoundException s) {
						s.printStackTrace();
					} catch (IOException x) {
						x.printStackTrace();
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} finally {
						try {
							in.close();
						} catch (IOException s) {
							s.printStackTrace();
						}
					}
					tableModel.fireTableRowsInserted(cust.size() - 1, cust.size() - 1);
					tableModel.fireTableDataChanged();
				}

			}

		});

		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if ((!nameField.getText().trim().isEmpty()) && (!ageField.getText().trim().isEmpty())
						&& (!emailField.getText().trim().isEmpty())) {

					if (isValidEmailAddress(emailField.getText())) {
						try {
							cust.add(new Customer(nameField.getText(), Integer.valueOf(ageField.getText()),
									emailField.getText()));
							tableModel.fireTableRowsInserted(cust.size() - 1, cust.size() - 1);
						} catch (NumberFormatException ex) {
							JOptionPane.showMessageDialog(formPanel, "Age Field must contain an integer!", "Error",
									JOptionPane.WARNING_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(formPanel, "Invalid Email Address!", "Error",
								JOptionPane.WARNING_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(formPanel, "Please Fill in all Fields", "Error",
							JOptionPane.WARNING_MESSAGE);
				}
			}

		});
		//// add form panel
		add(formPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.SOUTH);

		// add menu bar

		// set Frame properties
		this.setJMenuBar(menuBar);

		this.setTitle("Customer Management Application");
		this.setVisible(true);
		this.pack();
		this.setSize(600, 300);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		;

	}

	public void createFormPanel() {
		formPanel.setLayout(new GridBagLayout());
		TitledBorder title = BorderFactory.createTitledBorder("Customer Details");
		formPanel.setBorder(title);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 0;
		c.gridy = 0;
		formPanel.add(nameLabel, c);
		c.gridx = 1;
		formPanel.add(nameField, c);
		c.gridx = 0;
		c.gridy = 1;
		formPanel.add(ageLabel, c);
		c.gridx = 1;
		formPanel.add(ageField, c);
		c.gridx = 0;
		c.gridy = 2;
		formPanel.add(emailLabel, c);
		c.gridx = 1;
		formPanel.add(emailField, c);
		;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		formPanel.add(addButton, c);
	}

	public static boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	public static void main(String[] args) throws IOException {
		new CustomerApp();

	}

}
