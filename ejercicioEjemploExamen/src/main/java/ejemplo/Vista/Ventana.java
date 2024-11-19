package ejemplo.Vista;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Ventana extends JFrame {

	private static final long serialVersionUID = 1L;
	public JPanel contentPane;
	public JButton btnExcelBBDD;
	public JButton btnBBDD_XML;
	public JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana frame = new Ventana();
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
	public Ventana() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 593, 386);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnExcelBBDD = new JButton("Excel a BBDD");
		btnExcelBBDD.setBounds(178, 125, 211, 44);
		contentPane.add(btnExcelBBDD);

		btnBBDD_XML = new JButton("BBDD a XML");

		btnBBDD_XML.setBounds(178, 215, 211, 44);
		contentPane.add(btnBBDD_XML);

		lblNewLabel = new JLabel("Conectando a la base de datos...");
		lblNewLabel.setBounds(10, 11, 329, 24);
		contentPane.add(lblNewLabel);

		setVisible(true);

	}

}
