
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.*;

/**
 *
 * @author Luis Enrique Pérez González
 */
public class TicketPreview2 extends JFrame implements ActionListener{

    JTextArea ticketTextArea;

    public TicketPreview2(){
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
        ticketTextArea.setAlignmentX(JTextArea.CENTER_ALIGNMENT); 
        ticketTextArea.setEditable(false);
        ticketTextArea.setText("    Pavonado {{sucursal}}\n"
        + "    {{direccion}}\n"
        + "    =========================================\n"
        + "    Ticket # {{ticket}}\n"
        + "    {{fecha}} {{hora}}\n"
        + "    Descripcion              Importe\n"
        + "    =========================================\n"
        + "     {{items}}\n"
        + "    =========================================\n"
        + "    COSTO: ${{total}}\n"
        + "    RECIBIDO: ${{recibo}}    |    CAMBIO: ${{change}}\n"
        + "    RESTANTE: ${{rest}}\n"
        + "    =========================================\n"
        + "    GRACIAS POR SU PREFERENCIA...\n"
        + "                ******::::::::*******"
        + "\n           "
        + "\n           "
        + "\n           "
        + "\n           "
        + "\n           "
        + "\n           ");

        this.add(ticketTextArea);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }
    
    
}
