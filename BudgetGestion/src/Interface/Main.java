package Interface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Entities.User;
import Utils.Session;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(5, 5, 246, 14);
		contentPane.add(lblNewLabel);
		lblNewLabel.setText(user.getNom()+" "+ user.getPrenom());
		profil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		profil.setBounds(5, 30, 139, 31);
		contentPane.add(profil);
		
		param = new JButton("Modifier les params");
		param.setBounds(5, 72, 139, 31);
		contentPane.add(param);
		
		gererArgentEntre = new JButton("G\u00E9rer les entr\u00E9es\r\n\t d'argent");
		gererArgentEntre.setBounds(5, 114, 139, 36);
		contentPane.add(gererArgentEntre);
		
		gererArgentSortie = new JButton("Sortie d'argent");
		gererArgentSortie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		gererArgentSortie.setBounds(5, 161, 139, 36);
		contentPane.add(gererArgentSortie);
		
		btnReporting = new JButton("Reporting");
		btnReporting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnReporting.setBounds(5, 214, 139, 36);
		contentPane.add(btnReporting);
		
		btnExporter = new JButton("Exporter");
		btnExporter.setBounds(5, 265, 139, 36);
		contentPane.add(btnExporter);
		
		btnPrevisionDepense = new JButton("Prevision depense");
		btnPrevisionDepense.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPrevisionDepense.setBounds(5, 312, 139, 36);
		contentPane.add(btnPrevisionDepense);
		
		btnEpargne = new JButton("Epargne");
		btnEpargne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEpargne.setBounds(5, 359, 139, 36);
		contentPane.add(btnEpargne);
	}
}
