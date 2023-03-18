
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.*;
import java.lang.InterruptedException;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Luis Enrique Pérez González
 */
public class TicketPreview extends JFrame implements ActionListener {
    public PanelEntrada panelA;
    JTextArea ticketTextArea;
    private JPanel subpanelTicket,subpanelImagenBoton,spBoton, spBtnFoto;
    private JScrollPane scrollTicket;
    GestorArchivos fileGestor;
    JTextField verOrden;
    JButton btnImprimir, btnFoto, btnRegistrar, btnSiguiente;
    Ticket ticket;
    String listaArticulos = "";
    private static final DecimalFormat df = new DecimalFormat("0.00");
    VentanaFoto vFoto;
    JLabel labelHayFoto, labelHayRegistro;


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

        spBtnFoto = new JPanel(new GridLayout(0,1));
        btnFoto = new JButton("Tomar Foto");
        btnFoto.addActionListener(this);
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(this);
        btnImprimir = new JButton("Imprimir");
        btnImprimir.addActionListener(this);
        
        spBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnSiguiente = new JButton("Finalizar y Siguiente Orden");
        btnSiguiente.addActionListener(this);
        this.ticketTextArea = new JTextArea();
        ticketTextArea.setBackground(Color.WHITE);
        ticketTextArea.setForeground(Color.BLACK);
        ticketTextArea.setAlignmentX(JTextArea.LEFT_ALIGNMENT);
        ticketTextArea.setEditable(false);

        labelHayFoto = new JLabel("");
        labelHayRegistro = new JLabel("");

        spBtnFoto.add(btnFoto);
        spBtnFoto.add(labelHayFoto);
        spBtnFoto.add(btnRegistrar);
        spBtnFoto.add(labelHayRegistro);
        spBtnFoto.add(btnImprimir);

        spBoton.add(btnSiguiente);
        subpanelTicket.add(ticketTextArea);
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
                + "{{CD}}   \n"
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
                            +elemento.servicioObjeto.nameAbr+"\t"
                            +elemento.getAcero()+"\t"
                            +elemento.getDureza()+"\t"
                            +elemento.getKilos()+"\t"
                            +elemento.getPrecioCustom()+"\n";
                            ticket.subtotal=ticket.subtotal+elemento.getPrecioCustom();
                            for(int i=0;i<elemento.getSubElementoSize();i++){
                                listaArticulos=listaArticulos+"\t"
                                    +elemento.getSubElemento(i).getServicio().nameAbr+"\t"
                                    +"\t"
                                    +"\t"
                                    +"\t"
                                    +elemento.getSubElemento(i).getCosto()+"\n";
                                    ticket.subtotal=ticket.subtotal+elemento.getSubElemento(i).getCosto();
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
        if(ticket.costoTotal>ticket.subtotal){
            ticketModificado = ticketModificado.replace("{{CD}}", "CARGO:\t\t"+"$"+(ticket.costoTotal-ticket.subtotal) + "");
        }
        else{
            ticketModificado = ticketModificado.replace("{{CD}}", "DESCUENTO:\t\t"+"$"+(ticket.subtotal-ticket.costoTotal) + "");
        }      
        if (ticket.iva) {
            ticketModificado = ticketModificado.replace("{{IVA}}",
                    "IVA:\t\t $ " + df.format(((ticket.costoTotal  * 0.16))));
            ticket.costoTotal=ticket.costoTotal+(ticket.costoTotal* 0.16);
        }else{
            ticketModificado = ticketModificado.replace("{{IVA}}",
                    " ");
        }
        ticketModificado = ticketModificado.replace("{{total}}", (ticket.costoTotal) + "");
        ticketTextArea.setText(ticketModificado);

        buscarFotoOrden();
        buscarRegistro();

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
        if (e.getSource() == btnRegistrar){
             //Registra la Orden en el Registro General
             SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyy");
             String fecha = formatoFecha.format(panelA.jspGiveDate.getValue());
             fileGestor.writeFile(new EntradaRegistro(panelA.txtnOrden.getText(), panelA.txtCliente.getText(), ticket.costoTotal,
                     "POR PAGAR", "POR ENTREGAR", fecha));
 
             //Crea el registro Particular de la Orden
             fileGestor.writeFileOrder(ticket);

            buscarRegistro();
        }

        if(e.getSource() == btnImprimir){
            try{llamarImpresora();}
            catch(Exception excep){}
            
        }
        
        if (e.getSource() == btnSiguiente) {
            try{
            //Incrementa el numero de Orden
            try {
                fileGestor.incremetNumOrden();
                panelA.txtnOrden.setText(String.format("%04d", fileGestor.getNumOrden()));
            } catch (Exception excp) {
            }
            
            //Reinicia la gui para la nueva Orden
            
            panelA.removeAll();
            panelA.control.initVars();
            panelA.initComponents();
            panelA.updateUI();
            
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Error en algun campo");   
            }
            this.dispose();     
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
        
        /* Create and display the form */

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                VentanaFoto ventanaFoto = new VentanaFoto(fileGestor);
                ventanaFoto.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent e) {
                        buscarFotoOrden();
                    }
                });
            ventanaFoto.setVisible(true);
                
            }
        }); //en qué te ayudo Jon?
    }

    public void buscarFotoOrden(){
        boolean existeFoto = false;
       
        String nombreFoto = "src/Imagenes/ImagenesTickets/Ticket" + String.format("%04d", ticket.nOrden) + ".png";
        System.out.println(nombreFoto);
        File foto = new File(nombreFoto);

        existeFoto = foto.exists();
        labelHayFoto.setText(existeFoto ? "Ya existe una foto para esta orden" : "Aún no hay foto para esta orden");
        labelHayFoto.setForeground(existeFoto ? Color.GREEN : Color.RED);

        Font fuenteLabel = labelHayFoto.getFont();
        Font negritas = new Font(fuenteLabel.getName(), Font.BOLD, fuenteLabel.getSize());
        labelHayFoto.setFont(negritas);
    }

    public void buscarRegistro(){
        boolean existeRegistro = false;
        String nombreRegistro = "src/Registros/Ordenes/"+Integer.valueOf(ticket.nOrden).toString() + ".txt";
        File foto = new File(nombreRegistro);

        existeRegistro = foto.exists();
        labelHayRegistro.setText(existeRegistro ? "Esta orden ya está registrada" : "Aún no se registra esta orden");
        labelHayRegistro.setForeground(existeRegistro ? Color.GREEN : Color.RED);

        Font fuenteLabel = labelHayRegistro.getFont();
        Font negritas = new Font(fuenteLabel.getName(), Font.BOLD, fuenteLabel.getSize());
        labelHayRegistro.setFont(negritas);
    }

}