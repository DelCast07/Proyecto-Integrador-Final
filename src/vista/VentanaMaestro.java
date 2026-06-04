package vista;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import controlador.ControladorMenuMaestro;
import modelo.Modelo;

import javax.swing.JButton;

public class VentanaMaestro extends JFrame {
	
	private JButton btnCitas;
	private JButton btnTrajes;
	private JButton btnTalleres;
	private JButton btnClientes;
	private JButton btnCerrarSesion;
	private String rangoUsuario;
	private int idUsuario;
	private JButton btnEmpleados;

	public VentanaMaestro(String titulo, String rango, int id) {
		super(titulo);
		this.rangoUsuario = rango;
		this.idUsuario = id;
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

		JLabel lblCopyright = new JLabel("© 2026 Payo-Vallecano, Inc. Todos los derechos reservados");
		lblCopyright.setHorizontalAlignment(SwingConstants.CENTER);
		lblCopyright.setForeground(Color.WHITE);
		lblCopyright.setFont(new Font("Verdana", Font.PLAIN, 10));
		pnlFooter.add(lblCopyright);

		JPanel pnlBarraHorizontal = new JPanel();
		pnlBarraHorizontal.setBackground(new Color(196, 204, 203));
		pnlBarraHorizontal.setBounds(0, 111, 944, 282);
		pnlBarraHorizontal.setLayout(null);
		getContentPane().add(pnlBarraHorizontal);

		btnCerrarSesion = new JButton("Cerrar sesión");
		btnCerrarSesion.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnCerrarSesion.setBackground(new Color(165, 191, 201));
		btnCerrarSesion.setBounds(787, 68, 135, 30);
		getContentPane().add(btnCerrarSesion);
		
		JLabel lblTitulo = new JLabel("Menú Maestro");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 34));
		lblTitulo.setBounds(22, 63, 298, 40);
		getContentPane().add(lblTitulo);
		
		modelo.Modelo m = new modelo.Modelo();
		String nombreUsuario = m.obtenerNombreEmpleado(idUsuario);
		
		JLabel lblUsuarioLogueado = new JLabel(nombreUsuario + " (" + rangoUsuario + ")");
		lblUsuarioLogueado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsuarioLogueado.setFont(new Font("Tahoma", Font.ITALIC, 18));
		lblUsuarioLogueado.setForeground(new Color(68, 68, 68));
		lblUsuarioLogueado.setBounds(622, 13, 310, 30);
		getContentPane().add(lblUsuarioLogueado);

		btnTalleres = new JButton("Talleres");
		btnTalleres.setIcon(new ImageIcon(new ImageIcon("img\\talleres.png").getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
		btnTalleres.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnTalleres.setHorizontalTextPosition(SwingConstants.CENTER);
		btnTalleres.setBackground(new Color(165, 191, 201));
		btnTalleres.setBounds(300, 96, 110, 90);
		pnlBarraHorizontal.add(btnTalleres);

		btnClientes = new JButton("Clientes");
		btnClientes.setIcon(new ImageIcon(new ImageIcon("img\\clientes.png").getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
		btnClientes.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnClientes.setHorizontalTextPosition(SwingConstants.CENTER);
		btnClientes.setBackground(new Color(165, 191, 201));
		btnClientes.setBounds(420, 96, 110, 90);
		pnlBarraHorizontal.add(btnClientes);

		btnCitas = new JButton("Citas");
		btnCitas.setIcon(new ImageIcon(new ImageIcon("img\\citas.png").getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
		btnCitas.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnCitas.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCitas.setBackground(new Color(165, 191, 201));
		btnCitas.setBounds(180, 96, 110, 90);
		pnlBarraHorizontal.add(btnCitas);

		btnTrajes = new JButton("Trajes");
		btnTrajes.setIcon(new ImageIcon(new ImageIcon("img\\trajes.png").getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
		btnTrajes.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnTrajes.setHorizontalTextPosition(SwingConstants.CENTER);
		btnTrajes.setBackground(new Color(165, 191, 201));
		btnTrajes.setBounds(540, 96, 110, 90);
		pnlBarraHorizontal.add(btnTrajes);
		
		btnEmpleados = new JButton("Empleados");
		btnEmpleados.setIcon(new ImageIcon(new ImageIcon("img\\empleado.png").getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
		btnEmpleados.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnEmpleados.setHorizontalTextPosition(SwingConstants.CENTER);
		btnEmpleados.setBackground(new Color(165, 191, 201));
		btnEmpleados.setBounds(660, 96, 110, 90);
		pnlBarraHorizontal.add(btnEmpleados);
		
		JLabel lblFondo = new JLabel(new ImageIcon("img\\fondo.jpeg"));
		lblFondo.setBounds(0, 0, 944, 501);
		getContentPane().add(lblFondo);
	}

	// ActionListener no está genérico para evitar errores de casteo
	public void setControlador(ControladorMenuMaestro c) {
	    btnCitas.addActionListener(c);
	    btnTalleres.addActionListener(c);
	    btnClientes.addActionListener(c);
	    btnTrajes.addActionListener(c);
	    btnCerrarSesion.addActionListener(c);
	    btnEmpleados.addActionListener(c);
	}
	
	public JButton getBtnCitas() { return btnCitas; }
	public JButton getBtnTrajes() { return btnTrajes; }
	public JButton getBtnTalleres() { return btnTalleres; }
	public JButton getBtnClientes() { return btnClientes; }
	public JButton getBtnCerrarSesion() { return btnCerrarSesion; }
    public String getRangoUsuario() { return rangoUsuario; }
    public JButton getBtnEmpleados() { return btnEmpleados; }
    public int getIdUsuario() { return idUsuario; }
}