package BBDD;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class Reservas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	//CREO EL OBJETO QUE GESTIONA LA CONEXION CON LA BASE DE DATOS Y ESTARÁ DISPONIBLE PARA TODO EL CÓDIGO
	public ConexionMySQL conexion=new ConexionMySQL("root","","persona");  

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Reservas() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Etiqueta del título
		JLabel lbl_Titulo = new JLabel("¡Bienvenido!");
		lbl_Titulo.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lbl_Titulo.setBounds(151, 10, 145, 22);
		contentPane.add(lbl_Titulo);
		
		//Botón
		JButton btn_Insertar = new JButton("Log in");
		btn_Insertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					conexion.conectar();//Conecta con la BD
					String nombre=text_Usuario.getText(); // Lee el texto de la casilla nombre del formulario
					int	numero=Integer.parseInt(text_Pass.getText());//Pasa a entero el texto leido de la casilla número
					String sentencia="INSERT INTO USUARIOS (NOMBRE,EDAD) VALUES ('"+nombre+"',"+numero+")";
					conexion.ejecutarInsertDeleteUpdate(sentencia);//Manda la orden a la base de datos
					conexion.desconectar();//Desconecta de la base de datos
					
					//Llamo a un aviso
					String mensaje = "Usuario introducido correctamente";
					JOptionPane.showMessageDialog(null, mensaje);
					
					
					//Dejo los campos vacios para el próximo usuario
					text_Usuario.setText("");
					text_Pass.setText("");
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_Insertar.setBounds(174, 205, 84, 20);
		contentPane.add(btn_Insertar);

	}
}
