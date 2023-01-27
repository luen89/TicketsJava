
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
    private JLabel lbTotPiezas, lbTotKg, lbRD, lbGD, lbImagen, lbnOrden, lbCliente;
    private JButton btImprimir, btPago, btplus;
    private JPanel pDatos, pEncabezado, pTotales, pImprimir,
            pTemplado, pIncrementoT, pListTemplado;
    private JTextField txtTpiezas, txtTkg, txtnOrden, txtCliente;
    private JScrollPane scrollT;
    private ArrayList<PiezaForm> pformsT;
    private Calendar cal = Calendar.getInstance();
    private Date initDate;

    private JSpinner jspReceptionDate, jspGiveDate;
    private JCheckBox iva;
    TicketPreview2 tPreview2;
    private GestorArchivos gestor;
    // prueba
    private int contador = 0;

    public PanelEntrada(GestorArchivos ga) {
        this.gestor = ga;
        initComponents();
    }

    private void initComponents() {
        /***** Configuracion de los Spinners de Fecha */
        lbnOrden = new JLabel("Numero de Orden");
        lbnOrden.setForeground(Color.white);
        txtnOrden = new JTextField("", 8);
        txtnOrden.setEditable(false);
        try {
            txtnOrden.setText(String.format("%04d", gestor.getNumOrden()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        initDate = cal.getTime();
        cal.add(Calendar.YEAR, -100);
        Date earliestDate = cal.getTime();
        cal.add(Calendar.YEAR, 200);
        Date latestDate = cal.getTime();
        SpinnerDateModel spdm = new SpinnerDateModel(initDate, earliestDate, latestDate, Calendar.DAY_OF_MONTH);
        SpinnerDateModel spdm2 = new SpinnerDateModel(initDate, earliestDate, latestDate, Calendar.DAY_OF_MONTH);
        jspReceptionDate = new JSpinner(spdm);
        jspGiveDate = new JSpinner(spdm2);

        /* Construccion de la lista de Piezas de Pavoneo */

        /* Construccion de la lista de Piezas de Pavoneo */

        // pformsP = new ArrayList<>();
        // cocnstrutor de contador
        // pformsP.add(new PiezaForm(contador));
        // pformsT.get(0).preDisplay();

        /* Construccion de la lista de Piezas de Templado */
        pListTemplado = new JPanel();
        pListTemplado.setLayout(new BoxLayout(pListTemplado, BoxLayout.Y_AXIS));
        pformsT = new ArrayList<>();
        scrollT = new JScrollPane(pListTemplado);

        PiezaForm pieza = new PiezaForm();

        pformsT.add(pieza);
        pieza.preDisplay();
        pListTemplado.add(pieza);
        pieza.getBotonEliminar().addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                pListTemplado.remove(pieza);
                pformsT.remove(pieza);
                refreshDisplay();
            }
        });

        // ************Etiquetas del Total de Piezas******************************
        lbTotPiezas = new JLabel("Total Piezas:");
        lbTotKg = new JLabel("Total Kg:");
        lbRD = new JLabel("Fecha de Recepcion");
        lbGD = new JLabel("Fecha de Entrega");
        lbCliente = new JLabel("Cliente:");
        lbRD.setForeground(Color.white);
        lbGD.setForeground(Color.white);
        lbCliente.setForeground(Color.white);
        txtTkg = new JTextField(" ", 6);
        txtTpiezas = new JTextField("Suma de piezas", 6);
        txtCliente = new JTextField("", 6);
        txtTpiezas.setEditable(false);

        /* botones de Imprimir y Pagar */
        btImprimir = new JButton("Imprimir");
        btImprimir.addActionListener(this);

        iva = new JCheckBox("¿Requiere factura?");

        btPago = new JButton("Pagar");
        btPago.addActionListener(this);

        /* Botones de mas y menos */
        btplus = new JButton("Agregar elemento");
        btplus.addActionListener(this);

        // ***************PANELES DE DATOS*************************

        // ***************PANELES DE DATOS*************************
        /* Panel de los botones mas o menos Templado */
        pIncrementoT = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pIncrementoT.add(btplus);

        /* Panel de los totales de Piezas */
        pTotales = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pTotales.add(lbTotPiezas);
        pTotales.add(txtTpiezas);
        pTotales.add(lbTotKg);
        pTotales.add(txtTkg);

        // ***************Panel De Encabezado*************************
        pEncabezado = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lbImagen = new JLabel();
        lbImagen.setPreferredSize(new Dimension(750, 100));
        ImageIcon fot = new ImageIcon("src/Imagenes/Aguila_banner.png");
        // lbImagen.setIcon(new ImageIcon("src/Imagenes/imagen.png"));
        Icon icono = new ImageIcon(fot.getImage().getScaledInstance(750, 100, Image.SCALE_DEFAULT));
        lbImagen.setIcon(icono);
        pEncabezado.add(lbImagen);
        pEncabezado.add(lbRD);
        pEncabezado.add(jspReceptionDate);
        pEncabezado.add(lbGD);
        pEncabezado.add(jspGiveDate);
        pEncabezado.add(lbnOrden);
        pEncabezado.add(txtnOrden);
        pEncabezado.add(lbCliente);
        pEncabezado.add(txtCliente);
        pEncabezado.setBackground(Color.black);
        repaint();

        // *****************Panel del Templado**************************
        pTemplado = new JPanel();
        pTemplado.setLayout(new BoxLayout(pTemplado, BoxLayout.Y_AXIS));
        pTemplado.add(pIncrementoT);
        pTemplado.add(scrollT);

        pDatos = new JPanel();
        pDatos.setLayout(new BoxLayout(pDatos, BoxLayout.Y_AXIS));

        pDatos = new JPanel();
        pDatos.setLayout(new BoxLayout(pDatos, BoxLayout.Y_AXIS));
        pDatos.add(pTotales);

        pImprimir = new JPanel(new FlowLayout(FlowLayout.LEFT));

        pImprimir = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pImprimir.add(btImprimir);
        pImprimir.add(iva);
        // **********************************************************
        Font a = new Font("Calibri", 1, 14);
        Border bordeEntrada = new TitledBorder(new EtchedBorder(Color.white, Color.white), "Inicio", 1, 2, a,
                Color.white);

        pEncabezado.setBorder(bordeEntrada);

        Border bordePane3 = new TitledBorder(new EtchedBorder(), "Servicios");
        pTemplado.setBorder(bordePane3);

        Border bordePanel2 = new TitledBorder(new EtchedBorder(), "Total");
        pDatos.setBorder(bordePanel2);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(pEncabezado);
        add(pTemplado);
        // add(pDatos);
        add(pImprimir);

        /*
         * for(int i=0; i < pformsP.size();i++){
         * listaBotones.add(pformsP.get(i).getBtnX());
         * listaBotones.get(i).addActionListener(this);
         * }
         */
    }

    public void refreshDisplay() {
        this.updateUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Agregar nuevo elemento
        if (e.getSource() == btplus) {
            PiezaForm pieza = new PiezaForm();
            pformsT.add(pieza);
            pieza.preDisplay();
            pieza.getBotonEliminar().addActionListener(new ActionListener() {
                @Override

                public void actionPerformed(ActionEvent e) {
                    pListTemplado.remove(pieza);
                    pformsT.remove(pieza);
                    refreshDisplay();
                }
            });
            pListTemplado.add(pformsT.get(pformsT.size() - 1));

            this.updateUI();
            System.out.println("Presionaste mas");
        }

        if (e.getSource() == btImprimir) {

            Ticket ticketsito = condiciones();
            if (contador >= 1) {
                contador--;
            }
            tPreview2 = new TicketPreview2(ticketsito, gestor);
            System.out.println("Entré");
            tPreview2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            tPreview2.setVisible(true);

            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyy");
            String fecha = formatoFecha.format(jspGiveDate.getValue());

            gestor.writeFile(new EntradaRegistro(txtnOrden.getText(), txtCliente.getText(), ticketsito.costoTotal,
                    "PAGADO", "NO ENTREGADO", fecha));
            gestor.writeFileOrder(ticketsito);
            try {
                gestor.incremetNumOrden();
                txtnOrden.setText(String.format("%04d", gestor.getNumOrden()));
            } catch (Exception excp) {
            }
            this.updateUI();
        }

    }

    public void recibir(int id) {
        System.out.println("Imprimiendo desde panel entrada: " + id);
    }

    public Ticket condiciones() {
        double contadorPavonado = 0;
        double contadorTemplado = 0;

        double costoPavonado = 0.0;
        double costoTemplado = 0.0;

        double costoTotal = 0.0;
        Ticket ticket = new Ticket(Integer.parseInt(txtnOrden.getText()),txtCliente.getText(),0, 0, 0, 0, 0, initDate, iva.isSelected(),0.0, new ArrayList<Elemento>());
        for (int i = 0; i < pformsT.size(); i++) {
            System.out.println("Servicio: " + pformsT.get(i).getElemento().getServicio());

            if (pformsT.get(i).getElemento().getServicio().equals("Pavonado")) {
                contadorPavonado += pformsT.get(i).getElemento().getKilos();
            } else if (pformsT.get(i).getElemento().getServicio().equals("Templado")) {
                contadorTemplado += pformsT.get(i).getElemento().getKilos();
            } else if (pformsT.get(i).getElemento().getServicio().equals("Templado y Pavonado")) {
                contadorPavonado += pformsT.get(i).getElemento().getKilos();
                contadorTemplado += pformsT.get(i).getElemento().getKilos();
            }
            ticket.pformsT.add(pformsT.get(i).getElemento());
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

    public void llamarImpresora() {
        try {
            String[] impresoras = ConectorPlugin.obtenerImpresoras();
            System.out.println("Lista de impresoras:");
            for (String impresora : impresoras) {
                System.out.printf("'%s'\n", impresora);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error obteniendo impresoras: " + e.getMessage());
        }

        String amongUsComoCadena = "000001111000\n000010000100\n000100011110\n000100100001\n011100100001\n010100100001\n010100100001\n010100011110\n010100000010\n011100000010\n000100111010\n000100101010\n000111101110\n000000000000\n000000000000\n000000000000\n111010101110\n100010101000\n111010101110\n001010100010\n111011101110\n000000000000\n000000000000\n000000000000";
        // Aquí tu serial en caso de tener uno
        final String serial = "";
        ConectorPlugin conectorPlugin = new ConectorPlugin(ConectorPlugin.URL_PLUGIN_POR_DEFECTO, serial);
        conectorPlugin.Iniciar()
                .DeshabilitarElModoDeCaracteresChinos()
                .EstablecerAlineacion(ConectorPlugin.ALINEACION_CENTRO)
                .DescargarImagenDeInternetEImprimir("http://assets.stickpng.com/thumbs/587e32259686194a55adab73.png", 0,
                        216)
                .Feed(1)
                .EscribirTexto("Parzibyte's blog\n")
                .EscribirTexto("Blog de un programador\n")
                .TextoSegunPaginaDeCodigos(2, "cp850", "Teléfono: 123456798\n")
                .EscribirTexto("Fecha y hora: " + "29/9/2022")
                .Feed(1)
                .EstablecerAlineacion(ConectorPlugin.ALINEACION_IZQUIERDA)
                .EscribirTexto("____________________\n")
                .TextoSegunPaginaDeCodigos(2, "cp850", "Venta de plugin para impresoras versión 3\n")
                .EstablecerAlineacion(ConectorPlugin.ALINEACION_DERECHA)
                .EscribirTexto("$25\n")
                .EscribirTexto("____________________\n")
                .EscribirTexto("TOTAL: $25\n")
                .EscribirTexto("____________________\n")
                .EstablecerAlineacion(ConectorPlugin.ALINEACION_CENTRO)
                .HabilitarCaracteresPersonalizados()
                .DefinirCaracterPersonalizado("$", amongUsComoCadena)
                .EscribirTexto("En lugar del simbolo de pesos debe aparecer un among us\n")
                .EscribirTexto("TOTAL: $25\n")
                .EstablecerEnfatizado(true)
                .EstablecerTamanoFuente(1, 1)
                .TextoSegunPaginaDeCodigos(2, "cp850", "¡Gracias por su compra!\n")
                .Feed(1)
                .ImprimirCodigoQr("https://parzibyte.me/blog", 160, ConectorPlugin.RECUPERACION_QR_MEJOR,
                        ConectorPlugin.TAMANO_IMAGEN_NORMAL)
                .Feed(1)
                .ImprimirCodigoDeBarrasCode128("parzibyte.me", 80, 192, ConectorPlugin.TAMANO_IMAGEN_NORMAL)
                .Feed(1)
                .EstablecerTamanoFuente(1, 1)
                .EscribirTexto("parzibyte.me\n")
                .Feed(3)
                .Corte(1)
                .Pulso(48, 60, 120);
        try {
            conectorPlugin.imprimirEn("PT210");
            System.out.println("Impreso correctamente");
        } catch (Exception e) {
            System.out.println("Error imprimiendo: " + e.getMessage());
        }
    }

    public String getTotalPiezas() {
        return txtTpiezas.getText();
    }

    public void setControlador() {
    }

    public String getTotalPeso() {
        return txtTkg.getText();
    }

    public String getNombreCliente() {
        return txtCliente.getText();
    }

}
