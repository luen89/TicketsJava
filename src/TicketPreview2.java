
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.*;

/**
 *
 * @author Luis Enrique Pérez González
 */
public class TicketPreview2 extends JFrame implements ActionListener{

    JTextArea ticketTextArea;
    Ticket ticket;
    String listaArticulos = "";
    private static final DecimalFormat df = new DecimalFormat("0.00");
    double sumaP = 0.0;
    double sumaT = 0.0;
    
    public TicketPreview2(Ticket ticket){
        this.ticket = ticket;
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
       
        setTitle("Ticket Preview");
        setSize(820,560);
        this.setResizable(true);
        setLocationRelativeTo(null);
        initComponents();
        ImageIcon icono = new ImageIcon("src/Imagenes/pavonado_logo.png"); 
        this.setIconImage(icono.getImage());       
    }

    private void initComponents() {
        this.ticketTextArea = new JTextArea();
        ticketTextArea.setAlignmentX(JTextArea.LEFT_ALIGNMENT); 
        ticketTextArea.setEditable(false);
        
        String ticketHeader = 
        "\n           "
        +"\n           "
        +"\n           "
        + "           AGUILA TRATAMIENTOS TERMICOS Y SERVICIOS       \n"
        + "                         {{sucursal}}                     \n"
        + "    Calle 21 de Marzo #7-A, Col.San Jose el Conde, Puebla, Puebla \n"
        + "    ======================================================\n"
        + "    Ticket # {{ticket}} \n"
        + "    {{fecha}} \n\n"
        + "    Detalle de producto                                   \n"
        + "    ======================================================\n"
        + "    NPiezas\tServicio\tAcero\tPeso\tCosto\n"
        + "    {{items}}\n"
        + "    ======================================================\n"
        + "    Total PAV:\t\t $ {{totalP}} \n"
        + "    Cargo Min Pav:\t $ {{impP}} \n"
        + "    Total TEMP:\t\t $ {{totalT}} \n"
        + "    Cargo Min Temp:\t $ {{impT}} \n"
        + "    {{IVA}}  \n"        
        + "    COSTO TOTAL: $ {{total}} \n"
        + "    ======================================================\n"
        + "    \n"
        + "    \n"
        + "    \n"
        + "    \t________________________________\n"
        + "    \t\tFIRMA\n"
        + "    ======================================================\n"
        + "                   GRACIAS POR SU PREFERENCIA...          \n"
        + "                      ******::::::::*******"
        + "\n           "
        + "\n           "
        + "\n           "
        + "\n           "
        + "\n           "
        + "\n           ";

        String ticketModificado = ticketHeader.replace("{{total}}", ticket.costoTotal+"");
        

        ticket.pformsT.forEach(elemento -> {
            double costoP=(elemento.getKilos()*39);
            double costoT=(elemento.getKilos()*95);
            switch(elemento.getServicioAbreviado()){
                case "PAV" :
                            sumaP+=costoP;
                            listaArticulos = listaArticulos+"\n    "+
                            elemento.getPiezas()+"\t"+
                            elemento.getServicioAbreviado()+"\t"+
                            elemento.getAcero()+"\t"+
                            df.format(elemento.getKilos())+"\t"+
                            "$"+df.format(costoP)+"\n";
                            break;
                case "TEMP" :
                            sumaT+=costoT;
                            listaArticulos = listaArticulos+"\n    "+
                            elemento.getPiezas()+"\t"+
                            elemento.getServicioAbreviado()+"\t"+
                            elemento.getAcero()+"\t"+
                            df.format(elemento.getKilos())+"\t"+
                            "$"+df.format(costoT)+"\n";
                            break;
                case "PA_TE" :
                            sumaP+=costoP;
                            sumaT+=costoT;
                            listaArticulos = listaArticulos+"\n    "+
                            elemento.getPiezas()+"\t"+
                            elemento.getServicioAbreviado()+"\t"+
                            elemento.getAcero()+"\t"+
                            df.format(elemento.getKilos())+"\t"+
                            "$"+df.format(costoP)+"\n"+
                            "\t\t\t\t$"+df.format(costoT)+"\n";
                            break;
                default :   listaArticulos = listaArticulos+"\n    "+
                            elemento.getPiezas()+"\t"+
                            elemento.getServicioAbreviado()+"\t"+
                            elemento.getAcero()+"\t"+
                             df.format(elemento.getKilos())+"\n";
                }
                
        });
        ticketModificado = ticketModificado.replace("{{fecha}}", ticket.today.toString());   
        ticketModificado = ticketModificado.replace("{{items}}", listaArticulos);
        ticketModificado = ticketModificado.replace("{{totalP}}", df.format(sumaP)+"");
        ticketModificado = ticketModificado.replace("{{impP}}", df.format(ticket.costoPavonado-sumaP)+"");
        ticketModificado = ticketModificado.replace("{{totalT}}", df.format(sumaT)+"");
        ticketModificado = ticketModificado.replace("{{impT}}", df.format(ticket.costoTemplado-sumaT)+"");
        ticketModificado = ticketModificado.replace("{{total}}", (ticket.costoTotal)+"");
        if(ticket.iva){ticketModificado = ticketModificado.replace("{{IVA}}", "IVA:\t\t $ "+((ticket.costoTotal-(ticket.costoTotal*0.16))*0.16));}
        else{ticketModificado = ticketModificado.replace("{{IVA}}", "\n");}
        
        ticketTextArea.setText(ticketModificado);

        this.add(ticketTextArea);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }
    
    
}