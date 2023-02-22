
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.*;
import java.lang.InterruptedException;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Luis Enrique Pérez González
 */
public class TicketPreview extends JFrame implements ActionListener {
    public PanelEntrada panelA;
    JLabel sustitoImagen;
    JTextArea ticketTextArea;
    private JPanel subpanelTicket,subpanelImagenBoton,spBoton, spBtnFoto;
    private JScrollPane scrollTicket;
    GestorArchivos fileGestor;
    JTextField verOrden;
    JButton boton, btnFoto;
    Ticket ticket;
    String listaArticulos = "";
    private static final DecimalFormat df = new DecimalFormat("0.00");
    VentanaFoto vFoto;


    public TicketPreview(Ticket ticket, GestorArchivos gestor , PanelEntrada panelA) {
        this.panelA=panelA;
        this.fileGestor = gestor;
        this.ticket = ticket;
        this.setLayout(new GridLayout(0, 2));

        setTitle("Ticket Preview");
        setSize(820, 560);
        this.setResizable(true);
        setLocationRelativeTo(null);
        initComponents();
        ImageIcon icono = new ImageIcon("src/Imagenes/Aguila_logo.png");
        this.setIconImage(icono.getImage());
    }

    private void initComponents() {
        subpanelTicket = new JPanel(new FlowLayout(FlowLayout.CENTER));
        subpanelTicket.setAlignmentX(CENTER_ALIGNMENT);

        scrollTicket = new JScrollPane(subpanelTicket);
        scrollTicket.getVerticalScrollBar().setUnitIncrement(16);
        
        subpanelImagenBoton = new JPanel( new GridLayout(3,0));
        sustitoImagen = new JLabel("Aqui va la Imagen");

        spBtnFoto = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnFoto = new JButton("Tomar Foto");
        btnFoto.addActionListener(this);
        
        spBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        boton = new JButton("Imprimir y Registrar");
        boton.addActionListener(this);
        this.ticketTextArea = new JTextArea();
        ticketTextArea.setBackground(Color.WHITE);
        ticketTextArea.setForeground(Color.BLACK);
        ticketTextArea.setAlignmentX(JTextArea.LEFT_ALIGNMENT);
        ticketTextArea.setEditable(false);

        spBtnFoto.add(btnFoto);
        spBoton.add(boton);
        subpanelTicket.add(ticketTextArea);
        subpanelImagenBoton.add(sustitoImagen);
        subpanelImagenBoton.add(spBtnFoto);
        subpanelImagenBoton.add(spBoton);

        String ticketHeader = "\n"
                + "\n"
                + "  AGUILA TRATAMIENTOS TERMICOS Y SERVICIOS  \n"
                + "\n"
                + "Calle 21 de Marzo #7-A, Col.San Jose el Conde"
                + "                Puebla, Puebla \n"
                + "============================================\n"
                + "Ticket # {{ticket}} \n"
                + "{{fecha}} \n"
                + "Cliente: {{nombre}} \n\n"
                + "Detalle de producto\n"
                + "============================================\n"
                + "NP\tServ\tAcero\tDur\tPeso\tCosto \n"
                + "{{items}}\n"
                + "============================================\n"
                + "SUBTOTAL:\t\t $ {{subtotal}}\n"
                + "{{IVA}}  \n"
                + "COSTO TOTAL:\t $ {{total}} \n"
                + "============================================\n"
                + "  \n"
                + "  \n"
                + "  \n"
                + "       ________________________________\n"
                + "             Firma de quien entrega\n\n\n"
                + "       ________________________________\n"
                + "             Firma de quien recibe\n"
                + "============================================\n"
                + "        GRACIAS POR SU PREFERENCIA...       \n"
                + "           ******::::::::*******"
                + "\n"
                + "\n";

        String ticketModificado = ticketHeader;
        
        ticket.servicios.forEach(elemento -> {
            listaArticulos=listaArticulos
                            +elemento.getPiezas()+"\t"
                            +elemento.servArray.get(0).nameAbr+"\t"
                            +elemento.getAcero()+"\t"
                            +elemento.getDureza()+"\t"
                            +elemento.getKilos()+"\t"
                            +elemento.getPrecioCustom(0)+"\n";
                            ticket.subtotal=ticket.subtotal+elemento.getPrecioCustom(0);
                            for(int i=1;i<elemento.precioCustom.size();i++){
                                listaArticulos=listaArticulos+"\t"
                                    +elemento.servArray.get(i).nameAbr+"\t"
                                    +"\t"
                                    +"\t"
                                    +"\t"
                                    +elemento.getPrecioCustom(i)+"\n";
                                    ticket.subtotal=ticket.subtotal+elemento.getPrecioCustom(i);
                            }
                            
        });

        SimpleDateFormat formatoFecha = new SimpleDateFormat("EEEE dd 'de' MMMM 'del' yyyy', a las' hh:mm:ss",
                new Locale("es"));

        String fechaFinal = primeraMayuscula(formatoFecha.format(ticket.today).toString());

        ticketModificado = ticketModificado.replace("{{nombre}}", ticket.nameCliente);
        ticketModificado = ticketModificado.replace("{{fecha}}", fechaFinal);
        ticketModificado = ticketModificado.replace("{{ticket}}", String.format("%04d", ticket.nOrden) + "");
        ticketModificado = ticketModificado.replace("{{items}}", listaArticulos);
        ticketModificado = ticketModificado.replace("{{subtotal}}", (ticket.subtotal) + "");      
        if (ticket.iva) {
            ticketModificado = ticketModificado.replace("{{IVA}}",
                    "IVA:\t\t $ " + df.format(((ticket.subtotal  * 0.16))));
            ticket.costoTotal=ticket.subtotal+(ticket.subtotal* 0.16);
        } else {
            ticketModificado = ticketModificado.replace("{{IVA}}", "\n");
            ticket.costoTotal=ticket.subtotal;
        }
        ticketModificado = ticketModificado.replace("{{total}}", (ticket.costoTotal) + "");
        ticketTextArea.setText(ticketModificado);

        this.add(scrollTicket);
        this.add(subpanelImagenBoton);
    }

    public static final String primeraMayuscula(String str) {
        if (str == null || str.length() == 0)
            return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public String getTicketString() {
        return ticketTextArea.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boton) {
            try{
            llamarImpresora();
            
             //Registra la Orden en el Registro General
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyy");
            String fecha = formatoFecha.format(panelA.jspGiveDate.getValue());
            fileGestor.writeFile(new EntradaRegistro(panelA.txtnOrden.getText(), panelA.txtCliente.getText(), ticket.costoTotal,
                    "POR PAGAR", "POR ENTREGAR", fecha));

            //Crea el registro Particular de la Orden
            fileGestor.writeFileOrder(ticket);

            //Incrementa el numero de Orden
            try {
                fileGestor.incremetNumOrden();
                panelA.txtnOrden.setText(String.format("%04d", fileGestor.getNumOrden()));
            } catch (Exception excp) {
            }
            
            //Reinicia la gui para la nueva Orden
            panelA.removeAll();
            panelA.initComponents();
            panelA.updateUI();
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Error en algun campo");   
            }     
        }

        if (e.getSource() == btnFoto){
            llamarFoto();
        }
       
        // TODO Auto-generated method stub

    }

    public void llamarImpresora() throws Exception  {
        //Funcion expermiental falta analizar @ZingyArtist
        try {
            String[] impresoras = ConectorPlugin.obtenerImpresoras();
            System.out.println("Lista de impresoras:");
            for (String impresora : impresoras) {
                System.out.printf("'%s'\n", impresora);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error obteniendo impresoras: " + e.getMessage());
        }
        // Aquí tu serial en caso de tener uno
        final String serial = "";
        ConectorPlugin conectorPlugin = new ConectorPlugin(ConectorPlugin.URL_PLUGIN_POR_DEFECTO, serial);
        conectorPlugin.Iniciar()
                .DeshabilitarElModoDeCaracteresChinos()
                .Corte(1)
                .EstablecerAlineacion(ConectorPlugin.ALINEACION_CENTRO)
                .CargarImagenLocalEImprimir("C:/Users/Aguila_logo.png",0,216)
                .EstablecerAlineacion(ConectorPlugin.ALINEACION_IZQUIERDA) 
                .Feed(1)
                .EscribirTexto(ticketTextArea.getText())
                .Corte(1)
                .EstablecerAlineacion(ConectorPlugin.ALINEACION_CENTRO)
                .CargarImagenLocalEImprimir("C:/Users/Aguila_logo.png",0,216)
                .EstablecerAlineacion(ConectorPlugin.ALINEACION_IZQUIERDA)
                .Feed(1)
                .EscribirTexto(ticketTextArea.getText())
                .Corte(1)
                .Pulso(48, 60, 120);
        try {
            conectorPlugin.imprimirEn("Termico");
            System.out.println("Impreso correctamente");
        } catch (Exception e) {
            System.out.println("Error imprimiendo: " + e.getMessage());
        }
    }

    public void llamarFoto(){
        System.out.println("Voy a abrir la camara papi");
        //"Main" del archivo VentanaFoto
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaFoto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaFoto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaFoto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaFoto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaFoto().setVisible(true);
            }
        });
    }

}