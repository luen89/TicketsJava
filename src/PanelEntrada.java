import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Luis Enrique Pérez González
 */

public class PanelEntrada extends JPanel implements ActionListener, ChangeListener {
    private JLabel lbRD, lbGD, lbImagen, lbnOrden, lbCliente, lbPrecioTotalCustom;
    private JButton btImprimir, btAgregar;
    private JPanel panelEncabezado, subpanelEncabezadoDatos, pImprimir,
            panelDetalleOrden, pIncrementoOrdenes;
    public JPanel subpanelListaOrdenes, pPrecioTotalCustom;
    public JTextField txtnOrden, txtCliente;
    private JScrollPane scrollOrdenesPanel;
    public ArrayList<PiezaForm> itemsPiezasArray;
   
    private Date initDate;

    public JSpinner jspReceptionDate, jspGiveDate, jspPrecioCustom;
    public SpinnerModel smPrecioCustom;
    private JCheckBox iva, autoCalculo;
    TicketPreview tPreview;
    private GestorArchivos fileGestor;
    public ControladorPE control;
    

    public PanelEntrada(GestorArchivos fileGestor, ControladorPE control) {
        this.fileGestor = fileGestor;
        this.control=control;
        initComponents();
    }

    public void initComponents() {

        /* CREACION DE FUENTE */
        Font a = new Font("Calibri", 1, 14);

        /* PANEL DE ENCABEZADO */
        // *************** Inicializacion de Panel De Encabezado y subpanel
        // Datos*************************
        panelEncabezado = new JPanel(new GridLayout(0, 2));
        subpanelEncabezadoDatos = new JPanel(new GridLayout(0, 2));

        // ****************Etiqueta de Imagen */
        lbImagen = new JLabel();
        lbImagen.setPreferredSize(new Dimension(400, 250));
        ImageIcon fot = new ImageIcon("src/Imagenes/Aguila_banner.png");
        Icon icono = new ImageIcon(fot.getImage().getScaledInstance(400, 250, Image.SCALE_SMOOTH));
        lbImagen.setIcon(icono);
        lbImagen.setHorizontalAlignment(JLabel.CENTER);
        ;

        // ************Etiquetas de la Fecha y el Cliente ******************************
        lbRD = new JLabel("Fecha de Recepcion");
        lbGD = new JLabel("Fecha de Entrega");
        lbCliente = new JLabel("Cliente:");
        lbRD.setForeground(Color.black);
        lbGD.setForeground(Color.black);
        lbCliente.setForeground(Color.black);
        txtCliente = new JTextField("", 15);

        /** Configuracion de Etiqueta y Campo de Texto de numero de Orden */
        lbnOrden = new JLabel("Numero de Orden");
        lbnOrden.setForeground(Color.black);
        txtnOrden = new JTextField("", 3);
        txtnOrden.setEditable(false); // Inhabilitar edicion de caja
        try {
            txtnOrden.setText(String.format("%04d", fileGestor.getNumOrden()));
        } // Obtener numero de archivo
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        /***** Configuracion de los Spinners de Fecha */
        Calendar cal = Calendar.getInstance();
        initDate = cal.getTime();
        cal.add(Calendar.YEAR, -100);
        Date earliestDate = cal.getTime();
        cal.add(Calendar.YEAR, 200);
        Date latestDate = cal.getTime();
        SpinnerDateModel spdm = new SpinnerDateModel(initDate, earliestDate, latestDate, Calendar.DAY_OF_MONTH);
        jspReceptionDate = new JSpinner(spdm);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(jspReceptionDate, "dd/MM/yyyy");
        jspReceptionDate.setEditor(editor);
        SpinnerDateModel spdm2 = new SpinnerDateModel(initDate, earliestDate, latestDate, Calendar.DAY_OF_MONTH);
        jspGiveDate = new JSpinner(spdm2);
        JSpinner.DateEditor editor2 = new JSpinner.DateEditor(jspGiveDate, "dd/MM/yyyy");
        jspGiveDate.setEditor(editor2);

        /**************** Adicion de Elementos a Panel Encabezado */
        panelEncabezado.add(subpanelEncabezadoDatos);
        panelEncabezado.add(lbImagen);
        subpanelEncabezadoDatos.add(lbRD);
        subpanelEncabezadoDatos.add(jspReceptionDate);
        subpanelEncabezadoDatos.add(lbGD);
        subpanelEncabezadoDatos.add(jspGiveDate);
        subpanelEncabezadoDatos.add(lbnOrden);
        subpanelEncabezadoDatos.add(txtnOrden);
        subpanelEncabezadoDatos.add(lbCliente);
        subpanelEncabezadoDatos.add(txtCliente);

        /* Creacion del Borde del Panel */
        Border bordeEntrada = new TitledBorder(new EtchedBorder(Color.orange, Color.orange), "Inicio", 1, 2, a,
                Color.black);
        panelEncabezado.setBorder(bordeEntrada);
        repaint();

        /* PANEL DETALLE DE ORDEN */
        /* Inicializacion de panel de detalle de orden */
        panelDetalleOrden = new JPanel();
        panelDetalleOrden.setLayout(new BoxLayout(panelDetalleOrden, BoxLayout.X_AXIS));
        panelDetalleOrden.setAlignmentX(CENTER_ALIGNMENT);

        /* Inicializacion del subpanel de La lista de Ordenes */
        subpanelListaOrdenes = new JPanel();
        subpanelListaOrdenes.setLayout(new BoxLayout(subpanelListaOrdenes, BoxLayout.Y_AXIS));
        /* Inicializacion de Scroll de Ordenes */
        scrollOrdenesPanel = new JScrollPane(subpanelListaOrdenes);
        scrollOrdenesPanel.getVerticalScrollBar().setUnitIncrement(16);
        /* Inicializacion del primer formulario de Orden */
        itemsPiezasArray = new ArrayList<PiezaForm>();
        PiezaForm pieza = new PiezaForm(this);
        itemsPiezasArray.add(pieza);
        pieza.preDisplay();

        /* Inicializacion del subpanel de Incremento de Ordenes */
        pIncrementoOrdenes = new JPanel(new GridLayout(0,1));
        /* Inicializacion Boton de Agregar */
        autoCalculo = new JCheckBox("AutoCalcular");
        autoCalculo.setSelected(true);
        autoCalculo.addActionListener(this);
        btAgregar = new JButton("Agregar elemento");
        btAgregar.addActionListener(this);

        /* Conformacion de subpaneles con sus repectivos elementos y panel final */
        subpanelListaOrdenes.add(pieza);
        
        pIncrementoOrdenes.add(btAgregar);
        pIncrementoOrdenes.add(autoCalculo);
        panelDetalleOrden.add(pIncrementoOrdenes);
        panelDetalleOrden.add(scrollOrdenesPanel);

        /* Creacion de Borde del panel */
        Border bordePanel3 = new TitledBorder(new EtchedBorder(), "Detalle de Orden");
        panelDetalleOrden.setBorder(bordePanel3);

        /* PANEL DE IMPRIMIR */
        /* Inicializacion de panel imprimir */
        lbPrecioTotalCustom = new JLabel("\t    Total Venta");
        Font f =lbPrecioTotalCustom.getFont();
        lbPrecioTotalCustom.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        smPrecioCustom = new SpinnerNumberModel(0.0, 0.0, null, 0.1);

        jspPrecioCustom = new JSpinner(smPrecioCustom);
        jspPrecioCustom.setEnabled(false);
        jspPrecioCustom.addChangeListener(this);
        pImprimir = new JPanel(new FlowLayout(FlowLayout.LEFT));

        /* botones de Imprimir */
        btImprimir = new JButton("Imprimir");
        btImprimir.addActionListener(this);

        /* Checkbox del IVA */
        iva = new JCheckBox("¿Requiere factura?");

        /* Adicion de Elementos al panel */
        pImprimir.add(btImprimir);
        pImprimir.add(iva);
        pImprimir.add(lbPrecioTotalCustom);
        pImprimir.add(jspPrecioCustom);

        /* Creacion del Borde del panel */
        Border bordePanel2 = new TitledBorder(new EtchedBorder(), "Detalles Adicionales");
        pImprimir.setBorder(bordePanel2);

        /* Configuracion y adicion de elementos al Frame */
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(panelEncabezado);
        add(panelDetalleOrden);
        add(pImprimir);
    }

    public void refreshDisplay() {
        this.updateUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /* Evento a Boton Agregar nuevo elemento */
        if (e.getSource() == btAgregar) {
            // Crea e inicializa un nuevo formulario
            PiezaForm pieza = new PiezaForm(this);
            itemsPiezasArray.add(pieza);
            pieza.preDisplay();
            // Se agrega a la lista de Ordenes
            subpanelListaOrdenes.add(itemsPiezasArray.get(itemsPiezasArray.size() - 1));
            // Se actualiza la vista
            this.updateUI();
            // System.out.println("Presionaste mas");
        }

        if (e.getSource() == btImprimir) {
            // Obtiene el Ticket Logico
            Ticket ticketsito = new Ticket(Integer.parseInt(txtnOrden.getText()), txtCliente.getText().toUpperCase(),control.getTotal(), initDate,
            iva.isSelected(), 0.0, new ArrayList<Elemento>());
            for (int i = 0; i < itemsPiezasArray.size(); i++) {ticketsito.servicios.add(itemsPiezasArray.get(i).getElemento());}
            /*if (contador >= 1) {
                contador--;
            }*/
            try {
                ticketsito.validarTicket();
                // Crea la previsualizacion del ticket
                tPreview = new TicketPreview(ticketsito, fileGestor, this);
                // System.out.println("Entré");
                tPreview.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                tPreview.setVisible(true);
            } catch (ExcepcionVacio ex) {
                JOptionPane.showMessageDialog(null, "Campos Incorrectos");
            }

        }

        if (e.getSource() == autoCalculo) {
            if(autoCalculo.isSelected()){
                control.setAutoCalculo(true);
                jspPrecioCustom.setEnabled(false);
                double taux=0.0;
                for(PiezaForm p : itemsPiezasArray){
                    p.setEnablePrecioCustoms(false);
                    taux+=p.obtenerCostos();
                }
                jspPrecioCustom.setValue(taux);
                control.setTotal(taux);
            }
            else{
                control.setAutoCalculo(false);
                jspPrecioCustom.setEnabled(true);
                for(PiezaForm p : itemsPiezasArray){
                    p.setEnablePrecioCustoms(true);
                }

            }
        }

    }

    public void recibir(int id) {
        System.out.println("Imprimiendo desde panel entrada: " + id);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource()==jspPrecioCustom){
            control.setTotal((Double) jspPrecioCustom.getValue());
        }
    }

    /*public Ticket condiciones() {
        // Funcion de Jeatog yo ahi no le muevo XD @ZingyArtist
        double contadorPavonado = 0;
        double contadorTemplado = 0;

        double costoPavonado = 0.0;
        double costoTemplado = 0.0;

        double sumaCostosCustom = 0.0;

        double costoTotal = 0.0;
        Ticket ticket = new Ticket(Integer.parseInt(txtnOrden.getText()), txtCliente.getText(), 0, 0, 0, 0, 0, initDate,
                iva.isSelected(), 0.0, new ArrayList<Elemento>());
        for (int i = 0; i < itemsPiezasArray.size(); i++) {
            System.out.println("Servicio: " + itemsPiezasArray.get(i).getElemento().getServicio());

            if (itemsPiezasArray.get(i).getElemento().getServicio().equals("Pavonado")) {
                if (!(itemsPiezasArray.get(i).getElemento().getPrecioCustom() > 0.0)) {
                    contadorPavonado += itemsPiezasArray.get(i).getElemento().getKilos();
                } else {
                    sumaCostosCustom += itemsPiezasArray.get(i).getElemento().getPrecioCustom();
                }

            } else if (itemsPiezasArray.get(i).getElemento().getServicio().equals("Templado")) {

                if (!(itemsPiezasArray.get(i).getElemento().getPrecioCustom() > 0.0)) {
                    contadorTemplado += itemsPiezasArray.get(i).getElemento().getKilos();
                } else {
                    sumaCostosCustom += itemsPiezasArray.get(i).getElemento().getPrecioCustom();
                }

            } else if (itemsPiezasArray.get(i).getElemento().getServicio().equals("Templado y Pavonado")) {

                if (!(itemsPiezasArray.get(i).getElemento().getPrecioCustom() > 0.0)) {
                    contadorPavonado += itemsPiezasArray.get(i).getElemento().getKilos();
                    contadorTemplado += itemsPiezasArray.get(i).getElemento().getKilos();
                } else {
                    sumaCostosCustom += itemsPiezasArray.get(i).getElemento().getPrecioCustom();
                }

            }
            ticket.servicios.add(itemsPiezasArray.get(i).getElemento());
        }

        System.out.println("Kilos pavonado: " + contadorPavonado);
        System.out.println("Kilos templado: " + contadorTemplado);

        if (contadorPavonado > 0 && contadorPavonado > 8) {
            costoPavonado += contadorPavonado * 39;
        } else if (contadorPavonado > 0 && contadorPavonado <= 8) {
            costoPavonado += 280;
        }

        if (contadorTemplado > 0 && contadorTemplado > 4) {
            costoTemplado += contadorTemplado * 95;
        } else if (contadorTemplado > 0 && contadorTemplado <= 4) {
            costoTemplado += 310;
        }

        System.out.println("Costo pavonado: " + costoPavonado);
        System.out.println("Costo templado: " + costoTemplado);

        costoTotal = costoPavonado + costoTemplado;
        costoTotal += sumaCostosCustom;

        ticket.subtotal = costoTotal;

        if (iva.isSelected()) {
            costoTotal += costoTotal * 0.16;
        }

        System.out.println("Costo total: " + costoTotal);
        ticket.contadorPavonado = contadorPavonado;
        ticket.contadorTemplado = contadorTemplado;
        ticket.costoPavonado = costoPavonado;
        ticket.costoTemplado = costoTemplado;
        ticket.costoTotal = costoTotal;

        return ticket;
    }

    public String getNombreCliente() {
        return txtCliente.getText();
    }
*/
}
