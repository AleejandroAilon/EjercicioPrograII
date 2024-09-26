import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Main extends JFrame {
    private JTextField codigoField, nombreField, descripcionField, cantidadIngresadaField, cantidadSalidaField, fechaVencimientoField;
    private JButton agregarButton, mostrarButton;
    private Inventario inventario;

    public Main() {
        inventario = new Inventario();

        // Configuración de la ventana
        setTitle("Control de Inventario de Productos Perecederos");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(174, 63, 65));  // Fondo oscuro

        // Título
        JLabel titulo = new JLabel("Inventario de Productos Perecederos");
        titulo.setBounds(50, 10, 400, 30);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);  // Color de texto
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(titulo);

        // Campo de Código
        JLabel codigoLabel = new JLabel("Código:");
        configurarLabel(codigoLabel, 10, 60);
        codigoField = new JTextField();
        configurarCampoTexto(codigoField, 145, 60);

        // Campo de Nombre
        JLabel nombreLabel = new JLabel("Nombre:");
        configurarLabel(nombreLabel, 10, 100);
        nombreField = new JTextField();
        configurarCampoTexto(nombreField, 145, 100);

        // Campo de Descripción
        JLabel descripcionLabel = new JLabel("Descripción:");
        configurarLabel(descripcionLabel, 10, 140);
        descripcionField = new JTextField();
        configurarCampoTexto(descripcionField, 145, 140);

        // Campo de Fecha de Vencimiento
        JLabel fechaVencimientoLabel = new JLabel("Fecha Vencimiento:");
        configurarLabel(fechaVencimientoLabel, 10, 180);
        fechaVencimientoField = new JTextField();
        configurarCampoTexto(fechaVencimientoField, 145, 180);

        // Campo de Cantidad Ingresada
        JLabel cantidadIngresadaLabel = new JLabel("Cantidad Ingresada:");
        configurarLabel(cantidadIngresadaLabel, 10, 220);
        cantidadIngresadaField = new JTextField();
        configurarCampoTexto(cantidadIngresadaField, 145, 220);

        // Campo de Cantidad Salida
        JLabel cantidadSalidaLabel = new JLabel("Cantidad Salida:");
        configurarLabel(cantidadSalidaLabel, 10, 260);
        cantidadSalidaField = new JTextField();
        configurarCampoTexto(cantidadSalidaField, 145, 260);

        // Botón Agregar Producto
        agregarButton = new JButton("Agregar Producto");
        configurarBoton(agregarButton, 50, 320);
        agregarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });

        // Botón Mostrar Inventario
        mostrarButton = new JButton("Mostrar Inventario");
        configurarBoton(mostrarButton, 260, 320);
        mostrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarInventario();
            }
        });

        // Agregamos los botones
        add(agregarButton);
        add(mostrarButton);
    }

    private void configurarLabel(JLabel label, int x, int y) {
        label.setBounds(x, y, 200, 25);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.WHITE);  // Color del texto
        add(label);
    }

    private void configurarCampoTexto(JTextField campo, int x, int y) {
        campo.setBounds(x, y, 200, 25);
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        add(campo);
    }

    private void configurarBoton(JButton boton, int x, int y) {
        boton.setBounds(x, y, 160, 30);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBackground(new Color(101, 146, 126));  // Azul claro
        boton.setForeground(Color.WHITE);  // Texto blanco
        boton.setFocusPainted(false);
        add(boton);
    }

    private void agregarProducto() {
        try {
            String codigo = codigoField.getText();
            String nombre = nombreField.getText();
            String descripcion = descripcionField.getText();

            // Parsear la fecha de vencimiento
            LocalDate fechaVencimiento;
            try {
                fechaVencimiento = LocalDate.parse(fechaVencimientoField.getText());
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use el formato YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int cantidadIngresada = Integer.parseInt(cantidadIngresadaField.getText());
            int cantidadSalida = Integer.parseInt(cantidadSalidaField.getText());

            Producto producto = new Producto(codigo, nombre, descripcion, fechaVencimiento, LocalDate.now(), null, cantidadIngresada, cantidadSalida);
            inventario.agregarProducto(producto);

            JOptionPane.showMessageDialog(this, "Producto agregado correctamente.");

            // Limpiar los campos de texto
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        codigoField.setText("");
        nombreField.setText("");
        descripcionField.setText("");
        fechaVencimientoField.setText("");
        cantidadIngresadaField.setText("");
        cantidadSalidaField.setText("");
    }

    private void mostrarInventario() {
        ArrayList<Producto> productos = inventario.getProductos();
        StringBuilder inventarioTexto = new StringBuilder();

        if (productos.isEmpty()) {
            inventarioTexto.append("No hay productos en el inventario.");
        } else {
            for (Producto producto : productos) {
                inventarioTexto.append(producto.toString()).append("\n");
            }
        }

        JOptionPane.showMessageDialog(this, inventarioTexto.toString(), "Inventario", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        Main frame = new Main();
        frame.setVisible(true);
    }
}

class Producto {
    private String codigo;
    private String nombre;
    private String descripcion;
    private LocalDate fechaVencimiento;
    private LocalDate fechaIngreso;
    private LocalDate fechaEgreso;
    private int cantidadIngresada;
    private int cantidadSalida;

    public Producto(String codigo, String nombre, String descripcion, LocalDate fechaVencimiento,
                    LocalDate fechaIngreso, LocalDate fechaEgreso, int cantidadIngresada, int cantidadSalida) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaVencimiento = fechaVencimiento;
        this.fechaIngreso = fechaIngreso;
        this.fechaEgreso = fechaEgreso;
        this.cantidadIngresada = cantidadIngresada;
        this.cantidadSalida = cantidadSalida;
    }

    public int calcularCantidadDisponible() {
        return cantidadIngresada - cantidadSalida;
    }

    public String getCodigo() {
        return codigo;
    }

    @Override
    public String toString() {
        return "Producto: " + nombre + " | Código: " + codigo + " | Cantidad Disponible: " + calcularCantidadDisponible();
    }
}

class Inventario {
    private ArrayList<Producto> productos;

    public Inventario() {
        productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }
}
