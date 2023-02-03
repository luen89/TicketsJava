
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
    private JPanel subpanelTicket,subpanelImagenBoton,spBoton;
    private JScrollPane scrollTicket;
    GestorArchivos fileGestor;
    JTextField verOrden;
    JButton boton;
    Ticket ticket;
    String listaArticulos = "";
    private static final DecimalFormat df = new DecimalFormat("0.00");
    double sumaP = 0.0;
    double sumaT = 0.0;

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
        
        subpanelImagenBoton = new JPanel( new GridLayout(2,0));
        sustitoImagen = new JLabel("Aqui va la Imagen");

        spBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        boton = new JButton("Imprimir y Registrar");
        boton.addActionListener(this);
        this.ticketTextArea = new JTextArea();
        ticketTextArea.setBackground(Color.WHITE);
        ticketTextArea.setForeground(Color.BLACK);
        ticketTextArea.setAlignmentX(JTextArea.LEFT_ALIGNMENT);
        ticketTextArea.setEditable(false);

        spBoton.add(boton);
        subpanelTicket.add(ticketTextArea);
        subpanelImagenBoton.add(sustitoImagen);
        subpanelImagenBoton.add(spBoton);

        String ticketHeader = "\n"
                + "\n"
                + "  AGUILA TRATAMIENTOS TERMICOS Y SERVICIOS  \n"
                + "\n"
                + "Calle 21 de Marzo #7-A, Col.San Jose el Conde"
                + "                Puebla, Puebla \n"
                + "============================================\n"
                + "Ticket # {{ticket}} \n"
                + "{{fecha}} \n\n"
                + "Detalle de producto\n"
                + "============================================\n"
                + "NP\tServ\tAcero\tDur\tPeso\tCosto \n"
                + "{{items}}\n"
                + "============================================\n"
                + "Total PAV:\t $ {{totalP}} \n"
                + "Cargo Min Pav:\t $ {{impP}} \n"
                + "Total TEMP:\t $ {{totalT}} \n"
                + "Cargo Min Temp:\t $ {{impT}} \n"
                + "{{IVA}}  \n"
                + "COSTO TOTAL: $ {{total}} \n"
                + "============================================\n"
                + "  \n"
                + "  \n"
                + "  \n"
                + "       ________________________________\n"
                + "                    FIRMA\n"
                + "============================================\n"
                + "        GRACIAS POR SU PREFERENCIA...       \n"
                + "           ******::::::::*******"
                + "\n"
                + "\n";

        String ticketModificado = ticketHeader.replace("{{total}}", ticket.costoTotal + "");

        ticket.servicios.forEach(elemento -> {
            double costoP = (elemento.getKilos() * 39);
            double costoT = (elemento.getKilos() * 95);
            switch (elemento.getServicioAbreviado()) {
                case "PAV":
                    sumaP += costoP;
                    listaArticulos = listaArticulos + "\n" +
                            elemento.getPiezas() + "\t" +
                            elemento.getServicioAbreviado() + "\t" +
                            elemento.getAceroAbreviado() + "\t" +
                            elemento.getDureza() + "\t" +
                            df.format(elemento.getKilos()) + "\t" +
                            "$" + df.format(costoP) + "\n";
                    break;
                case "TEMP":
                    sumaT += costoT;
                    listaArticulos = listaArticulos + "\n" +
                            elemento.getPiezas() + "\t" +
                            elemento.getServicioAbreviado() + "\t" +
                            elemento.getAceroAbreviado() + "\t" +
                            elemento.getDureza() + "\t" +
                            df.format(elemento.getKilos()) + "\t" +
                            "$" + df.format(costoT) + "\n";
                    break;
                case "PA_TE":
                    sumaP += costoP;
                    sumaT += costoT;
                    listaArticulos = listaArticulos + "\n" +
                            elemento.getPiezas() + "\t" +
                            elemento.getServicioAbreviado() + "\t" +
                            elemento.getAceroAbreviado() + "\t" +
                            elemento.getDureza() + "\t" +
                            df.format(elemento.getKilos()) + "\t" +
                            "$" + df.format(costoP) + "\n" +
                            "\t\t\t\t\t$" + df.format(costoT) + "\n";
                    break;
                default:
                    listaArticulos = listaArticulos + "\n" +
                            elemento.getPiezas() + "\t" +
                            elemento.getServicioAbreviado() + "\t" +
                            elemento.getAcero() + "\t" +
                            df.format(elemento.getKilos()) + "\n";
            }

        });

        SimpleDateFormat formatoFecha = new SimpleDateFormat("EEEE dd 'de' MMMM 'del' yyyy', a las' hh:mm:ss",
                new Locale("es"));

        String fechaFinal = primeraMayuscula(formatoFecha.format(ticket.today).toString());

        ticketModificado = ticketModificado.replace("{{fecha}}", fechaFinal);
        ticketModificado = ticketModificado.replace("{{ticket}}", String.format("%04d", ticket.nOrden) + "");
        ticketModificado = ticketModificado.replace("{{items}}", listaArticulos);
        ticketModificado = ticketModificado.replace("{{totalP}}", df.format(sumaP) + "");
        ticketModificado = ticketModificado.replace("{{impP}}", df.format(ticket.costoPavonado - sumaP) + "");
        ticketModificado = ticketModificado.replace("{{totalT}}", df.format(sumaT) + "");
        ticketModificado = ticketModificado.replace("{{impT}}", df.format(ticket.costoTemplado - sumaT) + "");
        ticketModificado = ticketModificado.replace("{{total}}", (ticket.costoTotal) + "");
        if (ticket.iva) {
            ticketModificado = ticketModificado.replace("{{IVA}}",
                    "IVA:\t\t $ " + df.format(((ticket.costoTotal - (ticket.costoTotal * 0.16)) * 0.16)));
        } else {
            ticketModificado = ticketModificado.replace("{{IVA}}", "\n");
        }

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
                .EstablecerAlineacion(ConectorPlugin.ALINEACION_CENTRO)
                .CargarImagenLocalEImprimir("C:/Users/Aguila_logo.png",0,216)
                .EstablecerAlineacion(ConectorPlugin.ALINEACION_IZQUIERDA)
                .Feed(1)
                .EscribirTexto(ticketTextArea.getText())
                .Corte(1)
                .Pulso(48, 60, 120);
        try {
            conectorPlugin.imprimirEn("Termico2");
            System.out.println("Impreso correctamente");
        } catch (Exception e) {
            System.out.println("Error imprimiendo: " + e.getMessage());
        }
    }

}