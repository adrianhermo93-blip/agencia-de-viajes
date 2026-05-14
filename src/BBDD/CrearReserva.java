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
 * Clase VentanaReserva
 * ----------------------
 * Esta ventana sirve para CREAR o MODIFICAR una reserva.
 *
 * - Si se abre con el constructor normal → Crear nueva reserva
 * - Si se abre con el constructor con parámetros → Modificar reserva
 *
 * Los campos se rellenan automáticamente cuando venimos desde LeerReserva.
 */

public class CrearReserva extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    // Campos de texto donde el usuario escribe los datos
    protected JTextField textDestino;
    protected JTextField textFecha;
    protected JTextField textPresupuesto;

    /**
     * Método main para ejecutar esta ventana de forma independiente
     */
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

    /**
     * Constructor PRINCIPAL → Crear nueva reserva
     */
    public CrearReserva() {

        setTitle("Crear nueva reserva");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(200, 200, 450, 350);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Título
        JLabel lblTitulo = new JLabel("Nueva Reserva");
        lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 22));
        lblTitulo.setBounds(130, 10, 200, 30);
        contentPane.add(lblTitulo);

        // Etiqueta destino
        JLabel lblDestino = new JLabel("Destino:");
        lblDestino.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblDestino.setBounds(40, 70, 100, 20);
        contentPane.add(lblDestino);

        // Campo destino
        textDestino = new JTextField();
        textDestino.setBounds(160, 70, 180, 20);
        contentPane.add(textDestino);

        // Etiqueta fecha
        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblFecha.setBounds(40, 110, 100, 20);
        contentPane.add(lblFecha);

        // Campo fecha
        textFecha = new JTextField();
        textFecha.setBounds(160, 110, 180, 20);
        contentPane.add(textFecha);

        // Etiqueta presupuesto
        JLabel lblPresupuesto = new JLabel("Presupuesto:");
        lblPresupuesto.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblPresupuesto.setBounds(40, 150, 120, 20);
        contentPane.add(lblPresupuesto);

        // Campo presupuesto
        textPresupuesto = new JTextField();
        textPresupuesto.setBounds(160, 150, 180, 20);
        contentPane.add(textPresupuesto);

        // Botón para crear o modificar
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(140, 220, 150, 25);
        contentPane.add(btnGuardar);

        /*
         * Acción del botón Guardar
         * --------------------------
         * De momento solo muestra un mensaje.
         * Más adelante añadiremos INSERT o UPDATE según corresponda.
         */
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String destino = textDestino.getText();
                String fecha = textFecha.getText();
                String presupuesto = textPresupuesto.getText();

                if (destino.isEmpty() || fecha.isEmpty() || presupuesto.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Rellena todos los campos");
                    return;
                }

                JOptionPane.showMessageDialog(null,
                        "Datos guardados:\nDestino: " + destino +
                                "\nFecha: " + fecha +
                                "\nPresupuesto: " + presupuesto);
            }
        });
    }

    /**
     * Constructor SECUNDARIO → Modificar reserva
     * Recibe los datos desde LeerReserva y los coloca en los campos.
     */
    public CrearReserva(String destino, String fecha, String presupuesto) {

        // Llamo al constructor principal para crear la ventana
        this();

        // Cambio el título para que quede claro que estamos editando
        setTitle("Modificar reserva");

        // Relleno los campos con los datos de la reserva seleccionada
        textDestino.setText(destino);
        textFecha.setText(fecha);
        textPresupuesto.setText(presupuesto);
    }
}