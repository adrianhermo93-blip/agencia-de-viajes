package BBDD;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Clase Reservas
 * -------------------
 * Muestra todas las reservas en una tabla.
 * Tiene 3 botones: Crear, Modificar y Eliminar.
 * Ahora está adaptada a la tabla REAL "reserva".
 */

public class Reservas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaReservas;

	public ConexionMySQL conexion = new ConexionMySQL("root", "", "agencia-viajes");

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Reservas frame = new Reservas();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Reservas() {

		setTitle("Historial de Reservas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 700, 400);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Scroll para la tabla
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 20, 450, 300);
		contentPane.add(scrollPane);

		tablaReservas = new JTable();
		scrollPane.setViewportView(tablaReservas);

		// -----------------------------
		// BOTÓN 1: CREAR RESERVA
		// -----------------------------
		JButton btnCrear = new JButton("Crear Reserva");
		btnCrear.setBounds(500, 60, 150, 30);
		contentPane.add(btnCrear);

		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CrearReserva nueva = new CrearReserva();
				nueva.setVisible(true);
			}
		});

		// -----------------------------
		// BOTÓN 2: MODIFICAR RESERVA
		// -----------------------------
		JButton btnModificar = new JButton("Modificar Reserva");
		btnModificar.setBounds(500, 110, 150, 30);
		contentPane.add(btnModificar);

		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int fila = tablaReservas.getSelectedRow();

				if (fila == -1) {
					System.out.println("No hay fila seleccionada");
					return;
				}

				// Obtengo los datos reales
				int id = Integer.parseInt(tablaReservas.getValueAt(fila, 0).toString());
				String destino = tablaReservas.getValueAt(fila, 1).toString();
				String fecha = tablaReservas.getValueAt(fila, 2).toString();
				String presupuesto = tablaReservas.getValueAt(fila, 3).toString();

				CrearReserva editar = new CrearReserva(id, destino, fecha, presupuesto);
				editar.setVisible(true);
			}
		});

		// -----------------------------
		// BOTÓN 3: ELIMINAR RESERVA
		// -----------------------------
		JButton btnEliminar = new JButton("Eliminar Reserva");
		btnEliminar.setBounds(500, 160, 150, 30);
		contentPane.add(btnEliminar);

		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int fila = tablaReservas.getSelectedRow();

				if (fila == -1) {
					System.out.println("No hay fila seleccionada");
					return;
				}

				int id = Integer.parseInt(tablaReservas.getValueAt(fila, 0).toString());

				try {
					conexion.conectar();
					String sql = "DELETE FROM reserva WHERE id_reserva=" + id;
					conexion.ejecutarInsertDeleteUpdate(sql);
					conexion.desconectar();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}

				cargarReservas();
			}
		});

		cargarReservas();
	}

	private void cargarReservas() {

		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("ID");
		modelo.addColumn("Destino");
		modelo.addColumn("Fecha");
		modelo.addColumn("Presupuesto");

		try {
			conexion.conectar();
			String consulta = "SELECT id_reserva, destino, fecha, presupuesto FROM reserva";
			ResultSet rs = conexion.ejecutarSelect(consulta);

			while (rs.next()) {
				Object[] fila = new Object[4];
				fila[0] = rs.getInt("id_reserva");
				fila[1] = rs.getString("destino");
				fila[2] = rs.getString("fecha");
				fila[3] = rs.getInt("presupuesto");
				modelo.addRow(fila);
			}

			conexion.desconectar();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		tablaReservas.setModel(modelo);
	}
}