import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.imageio.ImageIO;

// import javafx.scene.input.KeyEvent;
// import javafx.scene.paint.Color;

/**
 *
 * @author Zyngy Coding
 */
public class VentanaDetalles extends JFrame implements ActionListener{
    private final String[] _STATUS_PAGO = {"PAGADO", "POR PAGAR"};
    private final String[] _STATUS_ENTREGA = {"ENTREGADO", "ENTREGA PARCIAL", "POR ENTREGAR"};
    
    private TicketEntrega ticket;
    private JPanel pPrincipal, pDatosGenerales, pPreviewImagen, pCostosYOpciones, pSuperior, pInferior;
    private JScrollPane spTabla;
    private JTable tabla;
    private ServiciosDetallesModel modelo;
    private JTextField txtCostoTotal, txtMontoPagado, txtMontoRestante, txtStatusPago, txtStatusEntrega;
    private JButton btnGuardar, btnEntregar, btnPagar, btnVerImagen ,btnReimprimir;
    private JLabel lbImage, lbCambiosSinGuardar;
    private String nombreFoto;
    private int statusEntrega, statusPago;
    private boolean existeFoto;
    private GestorArchivos gestor;
    
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
        obtenerStatus();
        JLabel lbOrden = new JLabel(" Orden: ");
        JTextField txtOrden = new JTextField(String.valueOf(ticket.nOrden));
        txtOrden.setEditable(false);

        JLabel lbFecha = new JLabel(" Fecha: ");
        JTextField txtFecha = new JTextField(formatDate(ticket.today));
        txtFecha.setEditable(false);

        JLabel lbCliente = new JLabel(" Cliente: ");
        JTextField txtCliente = new JTextField(ticket.nameCliente);
        txtCliente.setEditable(false);

        JLabel lbStatusPago = new JLabel(" Status Pago: ");
        txtStatusPago = new JTextField();
        txtStatusPago.setEditable(false);

        JLabel lbStatusEntrega = new JLabel(" Status Entrega: ");
        txtStatusEntrega = new JTextField();
        txtStatusEntrega.setEditable(false);

        actualizarTxtStatus();                        
    
        pDatosGenerales = new JPanel();
        pDatosGenerales.setLayout(new GridLayout(0, 2));
        pDatosGenerales.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Datos Generales"));

        pDatosGenerales.add(lbOrden);
        pDatosGenerales.add(txtOrden);        

        pDatosGenerales.add(lbFecha);
        pDatosGenerales.add(txtFecha);

        pDatosGenerales.add(lbCliente);
        pDatosGenerales.add(txtCliente);

        pDatosGenerales.add(lbStatusPago);
        pDatosGenerales.add(txtStatusPago);

        pDatosGenerales.add(lbStatusEntrega);
        pDatosGenerales.add(txtStatusEntrega);        
        
        construirImagen();        

        pPreviewImagen = new JPanel(new BorderLayout());
        pPreviewImagen.add(lbImage, BorderLayout.CENTER);

        pSuperior = new JPanel();
        pSuperior.setLayout(new GridLayout(0, 2));
        pSuperior.add(pDatosGenerales);
        pSuperior.add(pPreviewImagen);

        //#region Panel de Tabla
        modelo = new ServiciosDetallesModel(ticket.servicios);
        tabla = new JTable(modelo);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tabla.getColumnModel().getColumn(0).setResizable(true);
        tabla.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);        
        tabla.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tabla.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tabla.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tabla.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tabla.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);     
        tabla.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        resizeColumnWidth(tabla);
        modelo.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent evt) 
            {
                System.out.println("cambio en tabla");
                cambioEnPiezasEntregadas();
                actualizarTxtStatus();
                actualizarBtnStatus();
            }
        });

        spTabla = new JScrollPane(tabla);
        //#endregion

        //#region Panel Opciones
        btnVerImagen = new JButton("Ver Foto");
        btnVerImagen.setEnabled(existeFoto);
        btnVerImagen.addActionListener(this);

        btnPagar = new JButton("Marcar como PAGADO");
        btnPagar.addActionListener(this);

        btnEntregar = new JButton("Marcar como ENTREGADO");
        btnEntregar.addActionListener(this);

        btnReimprimir = new JButton("Reimprimir");
        btnReimprimir.addActionListener(this);


        JPanel pOpciones = new JPanel(new GridLayout(0, 2));
        pOpciones.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Opciones"));
        pOpciones.add(btnPagar);
        pOpciones.add(btnEntregar);
        pOpciones.add(btnVerImagen);
        pOpciones.add(btnReimprimir);
        actualizarBtnStatus();
        //#endregion

        //#region Panel de Costos
        JLabel lbCostoTotal = new JLabel(" Total: ");
        txtCostoTotal = new JTextField(formatDouble(ticket.costoTotal));
        txtCostoTotal.setEditable(false);

        JLabel lbMontoPagado = new JLabel(" Pagado: ");
        txtMontoPagado = new JTextField(formatDouble(ticket.montoPagado));
        
        JLabel lbMontoRestante = new JLabel(" Restante: ");
        txtMontoRestante = new JTextField(formatDouble(ticket.costoTotal - ticket.montoPagado));
        txtMontoRestante.setEditable(false);
        txtMontoPagado.addActionListener(this);
        
        JPanel pCostos = new JPanel(new GridLayout(0, 2));
        pCostos.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Costos"));
        pCostos.add(lbCostoTotal);
        pCostos.add(txtCostoTotal);
        pCostos.add(lbMontoPagado);
        pCostos.add(txtMontoPagado);
        pCostos.add(lbMontoRestante);
        pCostos.add(txtMontoRestante);
        //#endregion

        //#region Panel Costos y Opciones
        pCostosYOpciones = new JPanel(new GridLayout(0, 2));
        pCostosYOpciones.add(pCostos);
        pCostosYOpciones.add(pOpciones);
        //#endregion

        //#region Panel de Boton Guardar
        lbCambiosSinGuardar = new JLabel("¡Cambios sin guardar! ");
        lbCambiosSinGuardar.setForeground(Color.RED);
        lbCambiosSinGuardar.setVisible(false);

        btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(this);
        JPanel pBotonGuardar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pBotonGuardar.add(lbCambiosSinGuardar);
        pBotonGuardar.add(btnGuardar);
        //#endregion     
        
        pInferior = new JPanel();
        pInferior.setLayout(new BoxLayout(pInferior, BoxLayout.Y_AXIS));
        pInferior.add(pCostosYOpciones);
        pInferior.add(pBotonGuardar);

        //#region Panel Principal
        pPrincipal = new JPanel();
        pPrincipal.setLayout (new BoxLayout(pPrincipal, BoxLayout.Y_AXIS));        
        
        pPrincipal.add(pSuperior);
        pPrincipal.add(spTabla);
        pPrincipal.add(pInferior);

        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        add(pPrincipal);
        //#endregion

        this.addComponentListener(new ComponentListener() {  
            @Override
            public void componentResized(ComponentEvent e) {
                construirImagen();                
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                // TODO Auto-generated method stub
                // throw new UnsupportedOperationException("Unimplemented method 'componentMoved'");
            }

            @Override
            public void componentShown(ComponentEvent e) {
                // TODO Auto-generated method stub
                // throw new UnsupportedOperationException("Unimplemented method 'componentShown'");
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                // TODO Auto-generated method stub
                // throw new UnsupportedOperationException("Unimplemented method 'componentHidden'");
            }
        });
    }

    private void cambioEnPiezasEntregadas() {
        boolean entregado = true, entregaParcial = false;
        for (ElementoEntrega servicio : ticket.servicios) {
            entregaParcial |= (servicio.getPiezasEntregadas() > 0);
            entregado &= (servicio.getPiezasEntregadas() == servicio.getPiezas());                
        }
        statusEntrega = (entregado) ? 0 : (entregaParcial) ? 1 : 2;
    }

    private void actualizarTxtStatus() {
        txtStatusEntrega.setText(_STATUS_ENTREGA[statusEntrega]);
        txtStatusEntrega.setForeground(statusEntrega == 0 ? Color.GREEN : Color.RED);
        
        txtStatusPago.setText(_STATUS_PAGO[statusPago]);
        txtStatusPago.setForeground(statusPago == 0 ? Color.GREEN : Color.RED);
        if(lbCambiosSinGuardar != null)
            lbCambiosSinGuardar.setVisible(true);
    }

    private void actualizarBtnStatus() {
        btnEntregar.setEnabled(statusEntrega != 0);
        btnPagar.setEnabled(statusPago != 0);
    }

    private void obtenerStatus() {        
        boolean entregado = true, entregaParcial = false;
        for (ElementoEntrega servicio : ticket.servicios) {
            entregaParcial = (servicio.getPiezasEntregadas() > 0);
            entregado &= (servicio.getPiezasEntregadas() == servicio.getPiezas());                
        }
        statusEntrega = (entregado) ? 0 : (entregaParcial) ? 1 : 2;
        statusPago = (ticket.costoTotal == ticket.montoPagado) ? 0 : 1;
    }

    private void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 40; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            if(width > 300)
                width=300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    private void construirImagen() {
        try {
            nombreFoto = "src/Imagenes/ImagenesTickets/Ticket" + String.format("%04d", ticket.nOrden) + ".png";            
            File foto = new File(nombreFoto);
    
            existeFoto = foto.exists();

            if(!existeFoto){
                nombreFoto = "src/Imagenes/Aguila_logo.png";
            }

            BufferedImage bufferedImage = ImageIO.read(new File(nombreFoto));
            int heightImagen = bufferedImage.getHeight();

            double proporcion = ((double) this.getHeight() * 0.3) / (double) heightImagen;
            int widthImagenNueva = (int) (bufferedImage.getWidth() * proporcion);
            int heightImagenNueva = (int) (heightImagen * proporcion);
            Image image = bufferedImage.getScaledInstance(widthImagenNueva, heightImagenNueva, Image.SCALE_DEFAULT);
            if(lbImage == null)
                lbImage = new JLabel(new ImageIcon(image));
            else
                lbImage.setIcon(new ImageIcon(image));
        } catch (Exception e) {
            if(lbImage != null){
                lbImage = new JLabel("No se pudo cargar la imagen");
            }
            else{
                lbImage.setText("No se pudo cargar la imagen");
            }

            e.printStackTrace();
        }
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

                if(monto == ticket.costoTotal){
                    statusPago = 0;
                }
                else{
                    statusPago = 1;
                }
                txtMontoPagado.setText(formatDouble(monto));
                txtMontoRestante.setText(formatDouble(ticket.costoTotal - monto));
                actualizarTxtStatus();
                actualizarBtnStatus();
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
                if(montoPagado < 0){
                    throw new Exception();
                }
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
            
            statusPago = (ticket.costoTotal == ticket.montoPagado) ? 0 : 1;
            gestor.actualizarRegistroEnArchivo(new EntradaRegistro(String.format("%04d", ticket.nOrden), ticket.nameCliente, ticket.costoTotal,
            _STATUS_PAGO[statusPago], _STATUS_ENTREGA[statusEntrega], fecha));
            
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
            statusPago = 0;
            actualizarTxtStatus();
            actualizarBtnStatus();
        }
        // SI SE PRESIONA EL BOTON ENTREGAR
        if(e.getSource() == btnEntregar){
            // Se llenan todas las celdas de la tabla en el campo de piezas entregadas
            System.out.println("Entregar");
            for (int i = 0; i < ticket.servicios.size(); i++) {
                modelo.setValueAt(ticket.servicios.get(i).getPiezas(), i, 6);
            }        
            modelo.actualizarTabla();
        }
        if(e.getSource() == btnVerImagen && existeFoto){
            try {
                File f = new File(nombreFoto);
                Desktop dt = Desktop.getDesktop();
                dt.open(f);
                System.out.println("Se abrió la imagen");                
            } catch (Exception ex) {
                System.out.println("Error al abrir la imagen");                
            }
        }
        //SI SE PRESIONA EL BOTON REIMPRIMIR
        if(e.getSource()== btnReimprimir){
            VentanaReimpresion miVentanaReimpresion;
            try{
            String s = gestor.loadTicketFile(ticket.nOrden);
            if(s!=""){
                System.out.println("La cadena del ticket no es vacia");
                miVentanaReimpresion = new VentanaReimpresion(s);
                miVentanaReimpresion.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                miVentanaReimpresion.setVisible(true);
            }
            else{
                JOptionPane.showMessageDialog(this, "Ocurrio un error con el archivo del Ticket. \n Verique los mensajes de la terminal para de rastrear el error");
                System.out.println("Archivo Corrupto o Inexistente");
            }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ocurrio un error. \n Verique los mensajes de la terminal para de rastrear el error");
                System.out.println("Error al abrir el archivo de ticket");  
            }
        }
    }

    // function to format a double to a string with 2 decimal places
    public String formatDouble(double d) {
        return String.format("%.2f", d);
    }    
    
    private String formatDate(Date d) {        
        String strDate;

        try{
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");  
            strDate = dateFormat.format(d);
        } catch (Exception e){
            strDate = d.toString();
        }

        return strDate;
    }
}
