package Interface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Entities.User;
import Models.UserModel;
import Utils.Session;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main extends JFrame {

	private JPanel contentPane;
	private final JButton profil = new JButton("G\u00E9rer mon profil");
	private JButton param;
	private JButton gererArgentEntre;
	private JButton gererArgentSortie;
	private JButton btnReporting;
	private JButton btnExporter;
	private JButton btnPrevisionDepense;
	private JButton btnEpargne;
	private final Panel panel = new Panel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 626, 462);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		Session sess = Session.getInstance();
		User user = sess.us;
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(9, 10, 246, 14);
		lblNewLabel.setText(user.getNom()+" "+ user.getPrenom());
		
		param = new JButton("Modifier les params");
		param.setBounds(9, 77, 129, 31);
		
		gererArgentEntre = new JButton("G\u00E9rer les entr\u00E9es\r\n\t d'argent");
		gererArgentEntre.setBounds(9, 119, 129, 36);
		
		gererArgentSortie = new JButton("Sortie d'argent");
		gererArgentSortie.setBounds(9, 166, 129, 36);
		gererArgentSortie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btnReporting = new JButton("Reporting");
		btnReporting.setBounds(9, 216, 129, 36);
		btnReporting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btnExporter = new JButton("Exporter");
		btnExporter.setBounds(9, 266, 129, 36);
		
		btnPrevisionDepense = new JButton("Prevision depense");
		btnPrevisionDepense.setBounds(9, 313, 129, 36);
		btnPrevisionDepense.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btnEpargne = new JButton("Epargne");
		btnEpargne.setBounds(9, 360, 129, 36);
		btnEpargne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.setLayout(null);
		contentPane.add(lblNewLabel);
		contentPane.add(param);
		contentPane.add(gererArgentEntre);
		contentPane.add(gererArgentSortie);
		contentPane.add(btnReporting);
		contentPane.add(btnExporter);
		contentPane.add(btnPrevisionDepense);
		contentPane.add(btnEpargne);
		panel.setBounds(229, 104, 269, 208);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Nom :");
		lblNewLabel_1.setBounds(77, 56, 48, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Prenom :");
		lblNewLabel_1_1.setBounds(66, 76, 48, 14);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Email : ");
		lblNewLabel_1_1_1.setBounds(77, 101, 48, 14);
		panel.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Mot de passe :");
		lblNewLabel_1_1_1_1.setBounds(42, 125, 83, 14);
		panel.add(lblNewLabel_1_1_1_1);
		
		textField = new JTextField();
		textField.setBounds(135, 53, 96, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(135, 73, 96, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(135, 98, 96, 20);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(135, 122, 96, 20);
		panel.add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnEnregistrer = new JButton("Enregistrer");
		btnEnregistrer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String Nom = textField.getText().toString();
				String Prenom = textField_1.getText().toString();
				String Email = textField_2.getText().toString();
				String Mdp = textField_3.getText().toString();
				Session sess = Session.getInstance();
				User user = sess.us;
				
				user.setNom(Nom);
				user.setPrenom(Prenom);
				user.setEmail(Email);
				user.setPassword(Mdp);
				UserModel UserModele = new UserModel();
        		int test = UserModele.updateUser(user.getId(), user);
        		javax.swing.JOptionPane.showMessageDialog(null,"Enregistrement réussi ! ");
			}
		});
		btnEnregistrer.setBounds(106, 150, 89, 23);
		panel.add(btnEnregistrer);
		panel.setVisible(false);
		profil.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
			}
		});
		profil.setBounds(9, 35, 113, 23);
		contentPane.add(profil);
		profil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(true);
				Session sess = Session.getInstance();
				User user = sess.us;
				textField.setText(user.getNom());
				textField_1.setText(user.getPrenom());
				textField_2.setText(user.getEmail());
				textField_3.setText(user.getPassword());
				
			}
		});
	}
}
