
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.text.DecimalFormat;

import javax.swing.*;

/**
 *
 * @author Luis Enrique Pérez González
 */
public class OrdenFileFrame extends JFrame implements ActionListener{

    JTable tabla;
    Ticket ticket;
    JPanel panel;
    JScrollPane scroll;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public OrdenFileFrame(Ticket ticket){
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
        String [] columnNames ={"#Piezas", "#Kilos","Servicio","Descripcion","Acero","Piezas Entregadas"};
        Object[][] data = new Object[ticket.pformsT.size()][6];
        int i=0;
        System.out.println("Tamaño Arreglo: "+ticket.pformsT.size());
        for(i=0;i<ticket.pformsT.size();i++){
            System.out.println("Indice: "+i);
            data[i][0]=ticket.pformsT.get(i).getPiezas();
            data[i][1]=ticket.pformsT.get(i).getKilos();
            data[i][2]=ticket.pformsT.get(i).getServicio();
            data[i][3]=ticket.pformsT.get(i).getDescripcion();
            data[i][4]=ticket.pformsT.get(i).getAcero();
            data[i][5]=ticket.pformsT.get(i).getPiezasEntregadas();
        }


        tabla = new JTable(data,columnNames);
        scroll = new JScrollPane(tabla);
        tabla.setFillsViewportHeight(true);
        panel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(scroll);
        this.add(panel);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }
    
    
}
