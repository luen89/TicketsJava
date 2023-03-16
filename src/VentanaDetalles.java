import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

// import javafx.scene.input.KeyEvent;
// import javafx.scene.paint.Color;

/**
 *
 * @author Zyngy Coding
 */
public class VentanaDetalles extends JFrame implements ActionListener{

    JTextArea ticketTextArea;
    String listaArticulos = "";
    // private static final DecimalFormat df = new DecimalFormat("0.00");
    double sumaP = 0.0;
    double sumaT = 0.0;
    private TicketEntrega ticket;
    private JPanel pPrincipal, pInfoGeneral, pCliente, pCostos;
    private ServiciosDetallesModel modelo;
    private JScrollPane spTabla;
    private JTable tabla;
    private GestorArchivos gestor;
    private JTextField txtCostoTotal, txtMontoPagado, txtMontoRestante;
    private JButton btnGuardar, btnEntregar, btnPagar;
    
    public VentanaDetalles(TicketEntrega ticket, GestorArchivos gestor) {
        this.ticket = ticket;
        this.gestor = gestor;
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
       
        setTitle("Detalles de Registro #"+ticket.nOrden);
        setSize(820,560);
        this.setResizable(true);
        setLocationRelativeTo(null);
        initComponents();
        ImageIcon icono = new ImageIcon("src/Imagenes/Aguila_logo.png"); 
        this.setIconImage(icono.getImage());       
    }

    private void initComponents() {
        //#region Panel Datos Generales
        JLabel lbOrden = new JLabel("Orden:   ");
        JTextField txtOrden = new JTextField(String.valueOf(ticket.nOrden));
        txtOrden.setEditable(false);
        JLabel lbFecha = new JLabel("Fecha: ");
        JTextField txtFecha = new JTextField(ticket.today.toString());
        txtFecha.setEditable(false);
        pInfoGeneral = new JPanel();
        pInfoGeneral.setLayout (new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1.0;
        constraints.gridwidth = 1; // El área de texto ocupa dos columnas.
        constraints.gridheight = 1; // El área de texto ocupa 2 filas.
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JPanel pOrden = new JPanel();
        pOrden.setLayout(new FlowLayout(FlowLayout.LEFT));
        pOrden.add(lbOrden);
        pOrden.add(txtOrden);        

        JPanel pFecha = new JPanel();
        pFecha.setLayout(new FlowLayout(FlowLayout.LEFT));
        pFecha.add(lbFecha);
        pFecha.add(txtFecha);

        pCliente = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lbCliente = new JLabel("Cliente: ");
        JTextField txtCliente = new JTextField(ticket.nameCliente);
        txtCliente.setEditable(false);
        pCliente.add(lbCliente);
        pCliente.add(txtCliente);

        String nombreFoto = "src/Imagenes/ImagenesTickets/Ticket" + String.format("%04d", ticket.nOrden) + ".png";            
        File foto = new File(nombreFoto);

        if(!foto.exists()){
            nombreFoto = "src/Imagenes/Aguila_logo.png";
        }

        JPanel pPreviewImagen = new JPanel();
        
        ImageIcon icon = new ImageIcon(nombreFoto);
        pPreviewImagen.add(new JLabel(icon));

        constraints.gridx = 0; // El área de texto empieza en la columna cero.
        constraints.gridy = 0; // El área de texto empieza en la fila cero        
        pInfoGeneral.add(pOrden, constraints);

        constraints.gridx = 0; // El área de texto empieza en la columna cero.
        constraints.gridy = 1; // El área de texto empieza en la fila cero
        pInfoGeneral.add(pFecha, constraints);

        constraints.gridx = 0; // El área de texto empieza en la columna cero.
        constraints.gridy = 2; // El área de texto empieza en la fila cero
        pInfoGeneral.add(pCliente, constraints);

        constraints.gridx = 1; // El área de texto empieza en la columna cero.
        constraints.gridy = 0; // El área de texto empieza en la fila cero
        constraints.gridheight = 3; // El área de texto ocupa 2 filas.
        pInfoGeneral.add(pPreviewImagen, constraints);
        constraints.gridheight = 1; // El área de texto ocupa 2 filas.
        //#endregion            

        //#region Panel de Cliente
        
        //#endregion

        //#region Panel de Tabla
        modelo = new ServiciosDetallesModel(ticket.servicios);
        tabla = new JTable(modelo);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tabla.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tabla.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tabla.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tabla.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tabla.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tabla.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);     

        spTabla = new JScrollPane(tabla);
        spTabla.setPreferredSize(new Dimension(800, 200));
        //#endregion

        //#region Panel de Boton Entregar
        btnEntregar = new JButton("Entregar Piezas");
        btnEntregar.addActionListener(this);
        JPanel pBotonEntregar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pBotonEntregar.add(btnEntregar);   
        //#endregion

        //#region Panel de Costos y Pagos
        JLabel lbCostoTotal = new JLabel("Total: ");
        txtCostoTotal = new JTextField(formatDouble(ticket.costoTotal));
        txtCostoTotal.setEditable(false);
        JLabel lbMontoPagado = new JLabel("Pagado: ");
        txtMontoPagado = new JTextField(formatDouble(ticket.montoPagado));
        
        JLabel lbMontoRestante = new JLabel("Restante: ");
        txtMontoRestante = new JTextField(formatDouble(ticket.costoTotal - ticket.montoPagado));
        txtMontoRestante.setEditable(false);
        txtMontoPagado.addActionListener(this);        
        
        btnPagar = new JButton("Pagar");
        btnPagar.addActionListener(this);
        
        pCostos = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pCostos.add(lbCostoTotal);
        pCostos.add(txtCostoTotal);
        pCostos.add(lbMontoPagado);
        pCostos.add(txtMontoPagado);
        pCostos.add(lbMontoRestante);
        pCostos.add(txtMontoRestante);
        if(ticket.montoPagado < ticket.costoTotal)
            pCostos.add(btnPagar);
        //#endregion

        //#region Panel de Boton Guardar
        btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(this);
        JPanel pBotonGuardar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pBotonGuardar.add(btnGuardar);   
        //#endregion     
        
        //#region Panel Principal
        pPrincipal = new JPanel();
        pPrincipal.setLayout (new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1.0;
        c.weighty = 1.0;
        constraints.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        
        pPrincipal.add(pInfoGeneral, c);

        c.gridy = 1;
        pPrincipal.add(pCliente, c);

        c.gridy = 2;
        pPrincipal.add(spTabla, c);

        c.gridy = 4;
        pPrincipal.add(pBotonEntregar, c);
        c.gridy = 5;
        pPrincipal.add(pCostos, c);
        c.gridy = 6;
        pPrincipal.add(pBotonGuardar, c);
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        add(pPrincipal);
        //#endregion
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // SI SE PRESIONA ENTER EN EL CAMPO DE MONTO PAGADO
        if(e.getSource() == txtMontoPagado){
            System.out.println("Monto Pagado");
            // Se comprueba que el monto pagado sea válido
            try{
                double monto = Double.parseDouble(txtMontoPagado.getText());
                if(monto > ticket.costoTotal){
                    throw new Exception("Monto Pagado no puede ser mayor al Costo Total");
                }
                txtMontoPagado.setText(formatDouble(monto));
                txtMontoRestante.setText(formatDouble(ticket.costoTotal - monto));
            }
            catch(Exception ex){
                txtMontoPagado.setText(formatDouble(ticket.montoPagado));
            }
        }
        // SI SE PRESIONA EL BOTON GUARDAR
        if(e.getSource() == btnGuardar){
            System.out.println("Guardar");
            // Se comprueba que el monto pagado sea válido
            try {
                double montoPagado = Double.parseDouble(txtMontoPagado.getText());
                if (montoPagado > ticket.costoTotal) {
                    JOptionPane.showMessageDialog(this, "Monto Pagado no puede ser mayor al Costo Total");
                    return;
                }                
                ticket.montoPagado = montoPagado;

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Monto Pagado Inválido");
                return;
            }
            
            // Se actualiza el registro en el archivo general
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyy");
            String fecha = formatoFecha.format(ticket.today);
            boolean entregado = true, entregaParcial = false;
            for (ElementoEntrega servicio : ticket.servicios) {
                entregaParcial = (servicio.getPiezasEntregadas() > 0);
                entregado &= (servicio.getPiezasEntregadas() == servicio.getPiezas());                
            }
            String STATUS_ENTREGA = (entregado) ? "ENTREGADO" : (entregaParcial) ? "ENTREGA PARCIAL" : "POR ENTREGAR";
            String STATUS_PAGO = (ticket.costoTotal == ticket.montoPagado) ? "PAGADO" : "POR PAGAR";
            gestor.actualizarRegistroEnArchivo(new EntradaRegistro(String.format("%04d", ticket.nOrden), ticket.nameCliente, ticket.costoTotal,
            STATUS_PAGO, STATUS_ENTREGA, fecha));
            
            // Se actualiza el ticket en el archivo
            gestor.writeFileOrder(ticket);

            this.dispose();
        }
        // SI SE PRESIONA EL BOTON PAGAR
        if(e.getSource() == btnPagar){
            // Se llenan los campos de monto pagado y monto restante con los valores calculados
            System.out.println("Pagar");
            txtMontoPagado.setText(formatDouble(ticket.costoTotal));
            txtMontoRestante.setText("0.00");
        }
        // SI SE PRESIONA EL BOTON ENTREGAR
        if(e.getSource() == btnEntregar){
            // Se llenan todas las celdas de la tabla en el campo de piezas entregadas
            System.out.println("Entregar");
            for (int i = 0; i < ticket.servicios.size(); i++) {
                modelo.setValueAt(ticket.servicios.get(i).getPiezas(), i, 5);
            }        
            modelo.actualizarTabla();            
        }
    }

    // function to format a double to a string with 2 decimal places
    public String formatDouble(double d) {
        return String.format("%.2f", d);
    }    
    
}
