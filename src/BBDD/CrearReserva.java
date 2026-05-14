package BBDD;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/*
 * Clase CrearReserva
 * ----------------------
 * Sirve para CREAR o EDITAR una reserva.
 * Si viene sin parámetros → crear
 * Si viene con parámetros → editar
 */

public class CrearReserva extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    protected JTextField textDestino;
    protected JTextField textFecha;
    protected JTextField textPresupuesto;

    private Integer idReservaEditar = null; // si es null → crear

    public ConexionMySQL conexion = new ConexionMySQL("root", "", "agencia-viajes");

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CrearReserva frame = new CrearReserva();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // -----------------------------
    // CONSTRUCTOR PARA CREAR
    // -----------------------------
    public CrearReserva() {

        setTitle("Crear nueva reserva");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(200, 200, 450, 350);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitulo = new JLabel("Nueva Reserva");
        lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 22));
        lblTitulo.setBounds(130, 10, 200, 30);
        contentPane.add(lblTitulo);

        JLabel lblDestino = new JLabel("Destino:");
        lblDestino.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblDestino.setBounds(40, 70, 100, 20);
        contentPane.add(lblDestino);

        textDestino = new JTextField();
        textDestino.setBounds(160, 70, 180, 20);
        contentPane.add(textDestino);

        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblFecha.setBounds(40, 110, 100, 20);
        contentPane.add(lblFecha);

        textFecha = new JTextField();
        textFecha.setBounds(160, 110, 180, 20);
        contentPane.add(textFecha);

        JLabel lblPresupuesto = new JLabel("Presupuesto:");
        lblPresupuesto.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblPresupuesto.setBounds(40, 150, 120, 20);
        contentPane.add(lblPresupuesto);

        textPresupuesto = new JTextField();
        textPresupuesto.setBounds(160, 150, 180, 20);
        contentPane.add(textPresupuesto);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(140, 220, 150, 25);
        contentPane.add(btnGuardar);

        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarReserva();
            }
        });
    }

    // -----------------------------
    // CONSTRUCTOR PARA EDITAR
    // -----------------------------
    public CrearReserva(int id, String destino, String fecha, String presupuesto) {

        this(); // cargo la ventana normal

        setTitle("Modificar reserva");

        idReservaEditar = id;

        textDestino.setText(destino);
        textFecha.setText(fecha);
        textPresupuesto.setText(presupuesto);
    }

    // -----------------------------
    // MÉTODO GUARDAR (INSERT O UPDATE)
    // -----------------------------
    private void guardarReserva() {

        String destino = textDestino.getText();
        String fecha = textFecha.getText();
        String presupuestoStr = textPresupuesto.getText();

        if (destino.isEmpty() || fecha.isEmpty() || presupuestoStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Rellena todos los campos");
            return;
        }

        int presupuesto;

        try {
            presupuesto = Integer.parseInt(presupuestoStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El presupuesto debe ser un número");
            return;
        }

        try {
            conexion.conectar();

            if (idReservaEditar == null) {
                // INSERT
                String sql = "INSERT INTO reserva (id_usuario, destino, fecha, presupuesto) "
                        + "VALUES (1, '" + destino + "', '" + fecha + "', " + presupuesto + ")";
                conexion.ejecutarInsertDeleteUpdate(sql);

            } else {
                // UPDATE
                String sql = "UPDATE reserva SET destino='" + destino + "', fecha='" + fecha
                        + "', presupuesto=" + presupuesto
                        + " WHERE id_reserva=" + idReservaEditar;
                conexion.ejecutarInsertDeleteUpdate(sql);
            }

            conexion.desconectar();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JOptionPane.showMessageDialog(null, "Reserva guardada correctamente");

        dispose(); // cierro la ventana
    }
}