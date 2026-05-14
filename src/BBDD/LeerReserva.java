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
 * Clase LeerReserva
 * -------------------
 * Esta ventana muestra todas las reservas en una tabla.
 * Antes había un menú desplegable, pero ahora lo cambiamos
 * por 3 botones: Crear, Modificar y Eliminar.
 *
 * Cada botón hace lo mismo que hacía antes la opción del menú.
 */

public class LeerReserva extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tablaReservas;

    // Nombre del usuario (lo puedes cambiar según Login)
    private String usuarioActual = "UsuarioEjemplo";

    public ConexionMySQL conexion = new ConexionMySQL("root", "", "agencia-viajes");

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LeerReserva frame = new LeerReserva();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LeerReserva() {

        setTitle("Historial de Reservas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(200, 200, 700, 400);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Etiqueta con el nombre del usuario
        JLabel lblUsuario = new JLabel("Usuario: " + usuarioActual);
        lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblUsuario.setBounds(500, 10, 180, 20);
        contentPane.add(lblUsuario);

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
                // Abre la ventana para crear una nueva reserva
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

                // Compruebo si hay una fila seleccionada
                int fila = tablaReservas.getSelectedRow();

                if (fila == -1) {
                    System.out.println("No hay fila seleccionada");
                    return;
                }

                // Obtengo los datos de la reserva seleccionada
                String destino = tablaReservas.getValueAt(fila, 1).toString();
                String fecha = tablaReservas.getValueAt(fila, 2).toString();
                String presupuesto = tablaReservas.getValueAt(fila, 3).toString();

                // Abro la ventana de crear reserva pero con los datos cargados
                CrearReserva editar = new CrearReserva(destino, fecha, presupuesto);
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

                // Compruebo si hay una fila seleccionada
                int fila = tablaReservas.getSelectedRow();

                if (fila == -1) {
                    System.out.println("No hay fila seleccionada");
                    return;
                }

                // Obtengo el ID de la reserva
                int id = Integer.parseInt(tablaReservas.getValueAt(fila, 0).toString());

                try {
                    conexion.conectar();
                    String sql = "DELETE FROM reservas WHERE id=" + id;
                    conexion.ejecutarInsertDeleteUpdate(sql);
                    conexion.desconectar();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                // Recargo la tabla después de eliminar
                cargarReservas();
            }
        });

        // Cargo las reservas al iniciar la ventana
        cargarReservas();
    }

    // Método para cargar todas las reservas en la tabla
    private void cargarReservas() {

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Destino");
        modelo.addColumn("Fecha");
        modelo.addColumn("Presupuesto");

        try {
            conexion.conectar();
            String consulta = "SELECT id, destino, fecha, presupuesto FROM reservas";
            ResultSet rs = conexion.ejecutarSelect(consulta);

            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getString("destino");
                fila[2] = rs.getString("fecha");
                fila[3] = rs.getDouble("presupuesto");
                modelo.addRow(fila);
            }

            conexion.desconectar();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        tablaReservas.setModel(modelo);
    }
}