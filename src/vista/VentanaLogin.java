package vista;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.ActionListener;

public class VentanaLogin extends JFrame {
	private JPasswordField passwordField;
	private JTextField textFieldUsuario;
	private JButton btnLogin;

	public VentanaLogin(String titulo) {
		super(titulo);
		configInicial();
		inicializarComponentes();
	}

	private void configInicial() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setSize(960, 540);
		setLocationRelativeTo(null);
	}

	private void inicializarComponentes() {

		// Footer
		JPanel pnlFooter = new JPanel();
		pnlFooter.setBackground(new Color(72, 119, 109));
		pnlFooter.setBounds(0, 481, 944, 20);
		getContentPane().add(pnlFooter);

		// Copyright
		JLabel lblNewLabel_1 = new JLabel("© 2026 Payo-Vallecano, Inc. Todos los derechos reservados");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 10));
		pnlFooter.add(lblNewLabel_1);

		// TITULO
		JLabel lblTitulo = new JLabel("INICIAR SESIÓN");
		lblTitulo.setBounds(27, 63, 298, 40);
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 34));
		getContentPane().add(lblTitulo);

		// BARRA HORIZONTAL (decoracion del fondo)
		JPanel pnlBarraHorizontal = new JPanel();
		pnlBarraHorizontal.setForeground(new Color(196, 204, 203));
		pnlBarraHorizontal.setBackground(new Color(196, 204, 203));
		pnlBarraHorizontal.setBounds(0, 109, 944, 282);
		getContentPane().add(pnlBarraHorizontal);
		pnlBarraHorizontal.setLayout(null);

		// PETICION PARAMETROS INICIO DE SESIÓN
		JLabel lblUsuario = new JLabel("Usuario (Id):");
		lblUsuario.setBounds(330, 89, 170, 40);
		pnlBarraHorizontal.add(lblUsuario);
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 22));

		textFieldUsuario = new JTextField();
		textFieldUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldUsuario.setBackground(new Color(255, 255, 255));
		textFieldUsuario.setBounds(510, 89, 216, 40);
		pnlBarraHorizontal.add(textFieldUsuario);
		textFieldUsuario.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldUsuario.setColumns(10);

		JLabel lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setBounds(330, 159, 170, 40);
		pnlBarraHorizontal.add(lblContraseña);
		lblContraseña.setFont(new Font("Tahoma", Font.PLAIN, 22));

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		passwordField.setBounds(510, 159, 216, 40);
		pnlBarraHorizontal.add(passwordField);
		passwordField.setHorizontalAlignment(SwingConstants.LEFT);

		// BOTÓN DE ACCESO
		btnLogin = new JButton("Acceder");
		btnLogin.setBounds(788, 159, 111, 40);
		pnlBarraHorizontal.add(btnLogin);
		btnLogin.setBackground(new Color(165, 191, 201));
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		JLabel lblLogo = new JLabel("");
		ImageIcon iconoLogo1 = new ImageIcon("img\\logo.png");
		java.awt.Image imgLogo1 = iconoLogo1.getImage().getScaledInstance(180, 180, java.awt.Image.SCALE_SMOOTH);

		lblLogo.setIcon(new ImageIcon(imgLogo1));
		ImageIcon iconoLogo = new ImageIcon("img\\logo.png");
		java.awt.Image imgLogo = iconoLogo.getImage().getScaledInstance(180, 180, java.awt.Image.SCALE_SMOOTH);
		lblLogo.setIcon(new ImageIcon(imgLogo));
		lblLogo.setBounds(37, 51, 180, 180);
		pnlBarraHorizontal.add(lblLogo);

		// IMAGEN DE FONDO
		JLabel lblFondo = new JLabel("");
		// Cambiamos la ruta de Diego por la ruta relativa del proyecto
		// Quitamos el getClass().getResource y ponemos la ruta directa desde la raíz
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpeg"));
		lblFondo.setBounds(0, 0, 944, 501);
		getContentPane().add(lblFondo);

		this.getRootPane().setDefaultButton(btnLogin);
	}

	public void setControlador(ActionListener c) {
		// Hacemos este bucle para asegurarnos de que el boton nos manda a donde
		// queremos, ya que se han eliminado los escuchadores que no nos sirven
		// Es por control
		for (ActionListener a : btnLogin.getActionListeners()) {
			btnLogin.removeActionListener(a);
		}
		// Una vez vacio, añadimos el que si nos interesa
		btnLogin.addActionListener(c);
	}

	public String getContrasena() {
		return new String(passwordField.getPassword());
	}

	public String getUsuario() {
		return textFieldUsuario.getText();
	}
}
