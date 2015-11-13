package de.bklosr.rmm2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import nn.pp.rc.RemoteConsoleApplication;

import org.jdesktop.swingx.JXLoginPane.Status;
import org.jdesktop.swingx.JXTable;

public class Hostchooser extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private String hostname;
	private Status status;
	private JComboBox comboBox;
	private static final Preferences pref = Preferences
			.userNodeForPackage(Hostchooser.class);
	private JXTable table;
	private JTextField textFieldSessionID;
	private JPasswordField passwordField;
	private JComboBox comboBoxUsername;
	private JRadioButton rdbtnLoginWithSession;
	private JRadioButton rdbtnLoginWithUsernamepassword;
	private JLabel lblPassword;
	private JLabel lblUsername;
	private JLabel lblSessionId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Hostchooser dialog = new Hostchooser();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Hostchooser() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("KVMoIP Client");
		hostname = null;
		status = Status.CANCELLED;
		setBounds(100, 100, 439, 527);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 1.0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 1.0 };
		contentPanel.setLayout(gbl_contentPanel);
		ButtonGroup group = new ButtonGroup();
		{
			JLabel lblHostname = new JLabel("Hostname:");
			GridBagConstraints gbc_lblHostname = new GridBagConstraints();
			gbc_lblHostname.anchor = GridBagConstraints.EAST;
			gbc_lblHostname.insets = new Insets(0, 0, 5, 5);
			gbc_lblHostname.gridx = 0;
			gbc_lblHostname.gridy = 0;
			contentPanel.add(lblHostname, gbc_lblHostname);
		}
		{
			comboBox = new JComboBox();
			comboBox.setModel(new ServerComboBoxModel());
			comboBox.setSelectedItem(pref.get("host", ""));
			comboBox.setEditable(true);
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.insets = new Insets(0, 0, 5, 0);
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridx = 1;
			gbc_comboBox.gridy = 0;
			contentPanel.add(comboBox, gbc_comboBox);
		}
		JPanel panelLogin = new JPanel();
		panelLogin.setBorder(new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "Login Mode",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelLogin = new GridBagConstraints();
		gbc_panelLogin.insets = new Insets(0, 0, 5, 0);
		gbc_panelLogin.gridwidth = 2;
		gbc_panelLogin.fill = GridBagConstraints.BOTH;
		gbc_panelLogin.gridx = 0;
		gbc_panelLogin.gridy = 1;
		contentPanel.add(panelLogin, gbc_panelLogin);
		GridBagLayout gbl_panelLogin = new GridBagLayout();
		gbl_panelLogin.columnWidths = new int[] { 30, 0, 0 };
		gbl_panelLogin.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panelLogin.columnWeights = new double[] { 0.0, 0.0, 1.0 };
		gbl_panelLogin.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };
		panelLogin.setLayout(gbl_panelLogin);
		{
			rdbtnLoginWithUsernamepassword = new JRadioButton(
					"Login with Username/Password");
			GridBagConstraints gbc_rdbtnLoginWithUsernamepassword = new GridBagConstraints();
			gbc_rdbtnLoginWithUsernamepassword.anchor = GridBagConstraints.WEST;
			gbc_rdbtnLoginWithUsernamepassword.gridwidth = 3;
			gbc_rdbtnLoginWithUsernamepassword.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnLoginWithUsernamepassword.gridx = 0;
			gbc_rdbtnLoginWithUsernamepassword.gridy = 0;
			panelLogin.add(rdbtnLoginWithUsernamepassword,
					gbc_rdbtnLoginWithUsernamepassword);
			rdbtnLoginWithUsernamepassword.setSelected(true);
			rdbtnLoginWithUsernamepassword
					.setActionCommand("loginuserpassword");
			rdbtnLoginWithUsernamepassword.addActionListener(this);
			group.add(rdbtnLoginWithUsernamepassword);
		}
		{
			lblUsername = new JLabel("Username:");
			GridBagConstraints gbc_lblUsername = new GridBagConstraints();
			gbc_lblUsername.anchor = GridBagConstraints.EAST;
			gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
			gbc_lblUsername.gridx = 1;
			gbc_lblUsername.gridy = 1;
			panelLogin.add(lblUsername, gbc_lblUsername);
		}
		{
			comboBoxUsername = new JComboBox();
			comboBoxUsername.setModel(new UsernameComboBoxModel());
			comboBoxUsername.setSelectedItem(pref.get("username", ""));
			comboBoxUsername.setEditable(true);
			GridBagConstraints gbc_comboBoxUsername = new GridBagConstraints();
			gbc_comboBoxUsername.insets = new Insets(0, 0, 5, 0);
			gbc_comboBoxUsername.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBoxUsername.gridx = 2;
			gbc_comboBoxUsername.gridy = 1;
			panelLogin.add(comboBoxUsername, gbc_comboBoxUsername);
		}
		{
			lblPassword = new JLabel("Password:");
			GridBagConstraints gbc_lblPassword = new GridBagConstraints();
			gbc_lblPassword.anchor = GridBagConstraints.EAST;
			gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
			gbc_lblPassword.gridx = 1;
			gbc_lblPassword.gridy = 2;
			panelLogin.add(lblPassword, gbc_lblPassword);
		}
		{
			passwordField = new JPasswordField();
			GridBagConstraints gbc_passwordField = new GridBagConstraints();
			gbc_passwordField.insets = new Insets(0, 0, 5, 0);
			gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
			gbc_passwordField.gridx = 2;
			gbc_passwordField.gridy = 2;
			panelLogin.add(passwordField, gbc_passwordField);
		}
		{
			rdbtnLoginWithSession = new JRadioButton("Login with Session ID");
			GridBagConstraints gbc_rdbtnLoginWithSession = new GridBagConstraints();
			gbc_rdbtnLoginWithSession.anchor = GridBagConstraints.WEST;
			gbc_rdbtnLoginWithSession.gridwidth = 3;
			gbc_rdbtnLoginWithSession.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnLoginWithSession.gridx = 0;
			gbc_rdbtnLoginWithSession.gridy = 3;
			panelLogin.add(rdbtnLoginWithSession, gbc_rdbtnLoginWithSession);
			rdbtnLoginWithSession.addActionListener(this);
			rdbtnLoginWithSession.setActionCommand("loginsessionid");
			group.add(rdbtnLoginWithSession);
		}
		{
			lblSessionId = new JLabel("Session ID:");
			lblSessionId.setEnabled(false);
			GridBagConstraints gbc_lblSessionId = new GridBagConstraints();
			gbc_lblSessionId.insets = new Insets(0, 0, 5, 5);
			gbc_lblSessionId.anchor = GridBagConstraints.EAST;
			gbc_lblSessionId.gridx = 1;
			gbc_lblSessionId.gridy = 4;
			panelLogin.add(lblSessionId, gbc_lblSessionId);
		}
		{
			textFieldSessionID = new JTextField();
			textFieldSessionID.setEnabled(false);
			GridBagConstraints gbc_textFieldSessionID = new GridBagConstraints();
			gbc_textFieldSessionID.insets = new Insets(0, 0, 5, 0);
			gbc_textFieldSessionID.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldSessionID.gridx = 2;
			gbc_textFieldSessionID.gridy = 4;
			panelLogin.add(textFieldSessionID, gbc_textFieldSessionID);
			textFieldSessionID.setColumns(10);
		}
		{
			JPanel panelTable = new JPanel();
			panelTable.setBorder(new TitledBorder(null, "Other Preferences",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagConstraints gbc_panelTable = new GridBagConstraints();
			gbc_panelTable.insets = new Insets(0, 0, 5, 0);
			gbc_panelTable.fill = GridBagConstraints.BOTH;
			gbc_panelTable.gridwidth = 2;
			gbc_panelTable.gridx = 0;
			gbc_panelTable.gridy = 2;
			contentPanel.add(panelTable, gbc_panelTable);
			panelTable.setLayout(new BorderLayout(0, 0));
			{
				JScrollPane scrollPane = new JScrollPane();
				panelTable.add(scrollPane);
				{
					table = new JXTable();
					table.setModel(new PreferencesTableModel());
					scrollPane.setViewportView(table);
				}
			}
		}
		{
			{

			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(this);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(this);
				buttonPane.add(cancelButton);
			}
		}
	}

	public String getHostname() {
		return hostname;
	}

	public Status getStatus() {
		return status;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("OK")) {
			setVisible(false);
			dispose();
			hostname = (String) getComboBox().getSelectedItem();
			RMM2LoginService service = new RMM2LoginService();
			pref.put("host", hostname);
			if (getRdbtnLoginWithUsernamepassword().isSelected()) {
				try {
					service.authenticate((String) getComboBoxUsername()
							.getSelectedItem(), getPasswordField()
							.getPassword(), "");
				} catch (Exception e1) {
					throw new RuntimeException(
							"There can't be an exception...", e1);
				}
			} else if (getRdbtnLoginWithSession().isSelected()) {
				service.authenticate(getTextFieldSessionID().getText());
			}
			RemoteConsoleApplication.a(service.makeArgsArray());

			status = Status.SUCCEEDED;
		} else if (e.getActionCommand().equals("Cancel")) {
			setVisible(false);
			status = Status.CANCELLED;
		} else if (e.getActionCommand().equals("loginsessionid")
				|| e.getActionCommand().equals("loginuserpassword")) {
			if (getRdbtnLoginWithSession().isSelected()) {
				getComboBoxUsername().setEnabled(false);
				getLblUsername().setEnabled(false);
				getPasswordField().setEnabled(false);
				getLblPassword().setEnabled(false);
				getTextFieldSessionID().setEnabled(true);
				getLblSessionId().setEnabled(true);
			} else {
				getComboBoxUsername().setEnabled(true);
				getLblUsername().setEnabled(true);
				getPasswordField().setEnabled(true);
				getLblPassword().setEnabled(true);
				getTextFieldSessionID().setEnabled(false);
				getLblSessionId().setEnabled(false);
			}
		}
	}

	private JComboBox getComboBox() {
		return comboBox;
	}

	public JComboBox getComboBoxUsername() {
		return comboBoxUsername;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public JTextField getTextFieldSessionID() {
		return textFieldSessionID;
	}

	public JRadioButton getRdbtnLoginWithSession() {
		return rdbtnLoginWithSession;
	}

	public JRadioButton getRdbtnLoginWithUsernamepassword() {
		return rdbtnLoginWithUsernamepassword;
	}

	public JLabel getLblPassword() {
		return lblPassword;
	}

	public JLabel getLblUsername() {
		return lblUsername;
	}

	public JLabel getLblSessionId() {
		return lblSessionId;
	}
}
