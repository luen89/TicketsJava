import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.*;

/**
 *
 * @author Zyngy Coding
 */
public class VentanaDetalles extends JFrame implements ActionListener{

    JTextArea ticketTextArea;
    Ticket ticket;
    String listaArticulos = "";
    private static final DecimalFormat df = new DecimalFormat("0.00");
    double sumaP = 0.0;
    double sumaT = 0.0;
    
    public VentanaDetalles(){
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
       
        setTitle("Detalles de Registro");
        setSize(820,560);
        this.setResizable(true);
        setLocationRelativeTo(null);
        initComponents();
        ImageIcon icono = new ImageIcon("src/Imagenes/pavonado_logo.png"); 
        this.setIconImage(icono.getImage());       
    }

    private void initComponents() {
        // TODO Añadir los elementos necesarios
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }
    
    
}
