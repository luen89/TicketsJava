
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Luis Enrique Pérez González
 */
public class VentanaReimpresion extends JFrame implements ActionListener {
 
    JTextArea ticketTextArea;
    private JPanel subpanelTicket, subpanelBoton;
    private JScrollPane scrollTicket;
    private String content;
    JButton btnImprimir;
    private static final DecimalFormat df = new DecimalFormat("0.00");



    public VentanaReimpresion(String content) {
        this.content =  content;
        this.setLayout(new BorderLayout());
        setTitle("Ticket Preview");
        setSize(420, 660);
        this.setResizable(true);
        setLocationRelativeTo(null);
        initComponents();
        ImageIcon icono = new ImageIcon("src/Imagenes/Aguila_logo.png");
        this.setIconImage(icono.getImage());
    }

    private void initComponents() {
        subpanelTicket = new JPanel(new FlowLayout(FlowLayout.CENTER));
        subpanelTicket.setAlignmentX(CENTER_ALIGNMENT);

        subpanelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));

        scrollTicket = new JScrollPane(subpanelTicket);
        scrollTicket.getVerticalScrollBar().setUnitIncrement(16);
        
    
        btnImprimir = new JButton("Imprimir");
        btnImprimir.addActionListener(this);
        
        this.ticketTextArea = new JTextArea();
        ticketTextArea.setBackground(Color.WHITE);
        ticketTextArea.setForeground(Color.BLACK);
        ticketTextArea.setAlignmentX(JTextArea.LEFT_ALIGNMENT);
        ticketTextArea.setEditable(false);

        subpanelTicket.add(ticketTextArea);
        ticketTextArea.setText(content);

        subpanelBoton.add(btnImprimir);
        this.add(scrollTicket,BorderLayout.CENTER);
        this.add(subpanelBoton, BorderLayout.PAGE_END);
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
        if(e.getSource() == btnImprimir){
            try{llamarImpresora();}
            catch(Exception excep){}
        }
            this.dispose();     
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

}