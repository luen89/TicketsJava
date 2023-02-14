
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.*;
import java.lang.InterruptedException;
import java.io.IOException;

/**
 *
 * @author Luis Enrique Pérez González
 */

public class PanelEntrada extends JPanel implements ActionListener {
    private JLabel lbRD, lbGD, lbImagen, lbnOrden, lbCliente, lbPrecioCustom;
    private JButton btImprimir, btAgregar;
    private JPanel panelEncabezado, subpanelEncabezadoDatos, pImprimir,
            panelDetalleOrden, pIncrementoOrdenes, subpanelListaOrdenes, pPrecioCustom;
    public JTextField txtnOrden, txtCliente;
    private JScrollPane scrollOrdenesPanel;
    private ArrayList<PiezaForm> itemsPiezasArray;

    private Date initDate;

    public JSpinner jspReceptionDate, jspGiveDate, jspPrecioCustom;
    public SpinnerModel smPrecioCustom;
    private JCheckBox iva;
    TicketPreview tPreview;
    private GestorArchivos fileGestor;
    // prueba
    private int contador = 0;

    public PanelEntrada(GestorArchivos fileGestor) {
        this.fileGestor = fileGestor;
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
        lbImagen.setPreferredSize(new Dimension(600, 200));
        ImageIcon fot = new ImageIcon("src/Imagenes/Aguila_banner.png");
        Icon icono = new ImageIcon(fot.getImage().getScaledInstance(600, 200, Image.SCALE_SMOOTH));
        lbImagen.setIcon(icono);
        lbImagen.setHorizontalAlignment(JLabel.CENTER);
        ;

        // ************Etiquetas de la Fecha y el Cliente ******************************
        lbRD = new JLabel("Fecha de Recepcion");
        lbGD = new JLabel("Fecha de Entrega");
        lbCliente = new JLabel("Cliente:");
        lbRD.setForeground(Color.white);
        lbGD.setForeground(Color.white);
        lbCliente.setForeground(Color.white);
        txtCliente = new JTextField("", 15);

        /** Configuracion de Etiqueta y Campo de Texto de numero de Orden */
        lbnOrden = new JLabel("Numero de Orden");
        lbnOrden.setForeground(Color.white);
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
        Border bordeEntrada = new TitledBorder(new EtchedBorder(Color.white, Color.white), "Inicio", 1, 2, a,
                Color.white);
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
        PiezaForm pieza = new PiezaForm();
        itemsPiezasArray.add(pieza);
        pieza.preDisplay();
        pieza.getBotonEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subpanelListaOrdenes.remove(pieza);
                itemsPiezasArray.remove(pieza);
                refreshDisplay();
            }
        });

        /* Inicializacion del subpanel de Incremento de Ordenes */
        pIncrementoOrdenes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        /* Inicializacion Boton de Agregar */
        btAgregar = new JButton("Agregar elemento");
        btAgregar.addActionListener(this);

        /* Conformacion de subpaneles con sus repectivos elementos y panel final */
        subpanelListaOrdenes.add(pieza);
        pIncrementoOrdenes.add(btAgregar);
        panelDetalleOrden.add(pIncrementoOrdenes);
        panelDetalleOrden.add(scrollOrdenesPanel);

        /* Creacion de Borde del panel */
        Border bordePanel3 = new TitledBorder(new EtchedBorder(), "Detalle de Orden");
        panelDetalleOrden.setBorder(bordePanel3);

        /* PANEL DE IMPRIMIR */
        /* Inicializacion de panel imprimir */
        lbPrecioCustom = new JLabel("Si el costo de esta orden es personalizado, agréguelo aquí. Si no, déjelo en 0:");
        smPrecioCustom = new SpinnerNumberModel(0.0, 0.0, null, 0.1);
        pImprimir = new JPanel(new FlowLayout(FlowLayout.LEFT));

        /* botones de Imprimir */
        btImprimir = new JButton("Imprimir");
        btImprimir.addActionListener(this);

        /* Checkbox del IVA */
        iva = new JCheckBox("¿Requiere factura?");

        /* Adicion de Elementos al panel */
        pImprimir.add(btImprimir);
        pImprimir.add(iva);

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
            PiezaForm pieza = new PiezaForm();
            itemsPiezasArray.add(pieza);
            pieza.preDisplay();
            // Se crea el evento para el boton eliminar
            pieza.getBotonEliminar().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    subpanelListaOrdenes.remove(pieza);
                    itemsPiezasArray.remove(pieza);
                    refreshDisplay();
                }
            });
            // Se agrega a la lista de Ordenes
            subpanelListaOrdenes.add(itemsPiezasArray.get(itemsPiezasArray.size() - 1));
            // Se actualiza la vista
            this.updateUI();
            // System.out.println("Presionaste mas");
        }

        if (e.getSource() == btImprimir) {
            // Obtiene el Ticket Logico
            Ticket ticketsito = condiciones();
            if (contador >= 1) {
                contador--;
            }
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

    }

    public void recibir(int id) {
        System.out.println("Imprimiendo desde panel entrada: " + id);
    }

    public Ticket condiciones() {
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

    /*
     * public void llamarImpresora() {
     * try {
     * String[] impresoras = ConectorPlugin.obtenerImpresoras();
     * System.out.println("Lista de impresoras:");
     * for (String impresora : impresoras) {
     * System.out.printf("'%s'\n", impresora);
     * }
     * } catch (IOException | InterruptedException e) {
     * System.out.println("Error obteniendo impresoras: " + e.getMessage());
     * }
     * 
     * String amongUsComoCadena =
     * "000001111000\n000010000100\n000100011110\n000100100001\n011100100001\n010100100001\n010100100001\n010100011110\n010100000010\n011100000010\n000100111010\n000100101010\n000111101110\n000000000000\n000000000000\n000000000000\n111010101110\n100010101000\n111010101110\n001010100010\n111011101110\n000000000000\n000000000000\n000000000000";
     * // Aquí tu serial en caso de tener uno
     * final String serial = "";
     * ConectorPlugin conectorPlugin = new
     * ConectorPlugin(ConectorPlugin.URL_PLUGIN_POR_DEFECTO, serial);
     * conectorPlugin.Iniciar()
     * .DeshabilitarElModoDeCaracteresChinos()
     * .EstablecerAlineacion(ConectorPlugin.ALINEACION_CENTRO)
     * .DescargarImagenDeInternetEImprimir(
     * "http://assets.stickpng.com/thumbs/587e32259686194a55adab73.png", 0,
     * 216)
     * .Feed(1)
     * .EscribirTexto("Parzibyte's blog\n")
     * .EscribirTexto("Blog de un programador\n")
     * .TextoSegunPaginaDeCodigos(2, "cp850", "Teléfono: 123456798\n")
     * .EscribirTexto("Fecha y hora: " + "29/9/2022")
     * .Feed(1)
     * .EstablecerAlineacion(ConectorPlugin.ALINEACION_IZQUIERDA)
     * .EscribirTexto("____________________\n")
     * .TextoSegunPaginaDeCodigos(2, "cp850",
     * "Venta de plugin para impresoras versión 3\n")
     * .EstablecerAlineacion(ConectorPlugin.ALINEACION_DERECHA)
     * .EscribirTexto("$25\n")
     * .EscribirTexto("____________________\n")
     * .EscribirTexto("TOTAL: $25\n")
     * .EscribirTexto("____________________\n")
     * .EstablecerAlineacion(ConectorPlugin.ALINEACION_CENTRO)
     * .HabilitarCaracteresPersonalizados()
     * .DefinirCaracterPersonalizado("$", amongUsComoCadena)
     * .EscribirTexto("En lugar del simbolo de pesos debe aparecer un among us\n")
     * .EscribirTexto("TOTAL: $25\n")
     * .EstablecerEnfatizado(true)
     * .EstablecerTamanoFuente(1, 1)
     * .TextoSegunPaginaDeCodigos(2, "cp850", "¡Gracias por su compra!\n")
     * .Feed(1)
     * .ImprimirCodigoQr("https://parzibyte.me/blog", 160,
     * ConectorPlugin.RECUPERACION_QR_MEJOR,
     * ConectorPlugin.TAMANO_IMAGEN_NORMAL)
     * .Feed(1)
     * .ImprimirCodigoDeBarrasCode128("parzibyte.me", 80, 192,
     * ConectorPlugin.TAMANO_IMAGEN_NORMAL)
     * .Feed(1)
     * .EstablecerTamanoFuente(1, 1)
     * .EscribirTexto("parzibyte.me\n")
     * .Feed(3)
     * .Corte(1)
     * .Pulso(48, 60, 120);
     * try {
     * conectorPlugin.imprimirEn("PT210");
     * System.out.println("Impreso correctamente");
     * } catch (Exception e) {
     * System.out.println("Error imprimiendo: " + e.getMessage());
     * }
     * }
     */

    public String getNombreCliente() {
        return txtCliente.getText();
    }

}
