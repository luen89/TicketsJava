// import javax.swing.JFrame;

// public class TicketPreview extends JFrame{
//     public TicketPreview(){
//         this.setSize(WIDTH, HEIGHT);
//     }
// }

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;

public class TicketPreview implements ActionListener {
    public void showPreview(JButton previewButton) {
        previewButton.addActionListener(e -> {
            //Ticket ticket = new Ticket();

            // Crear la tabla que muestra los items
            String[] columnNames = {"Nombre", "Precio"};
            // Object[][] data = new Object[ticket.getItems().size()][2];
            // for (int i = 0; i < ticket.getItems().size(); i++) {
            //     data[i][0] = ticket.getItems().get(i).getName();
            //     data[i][1] = ticket.getItems().get(i).getPrice();
            // }

            String contentTicket = "    Pavonado {{sucursal}}\n"
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
            + "\n           ";
            Object[][] data = new Object[2][2];
            JTable itemsTable = new JTable(data, columnNames);

            // logo
            JLabel lbImagen = new JLabel();
            lbImagen.setPreferredSize(new Dimension(750,100));
            ImageIcon logo = new ImageIcon("src/Imagenes/Aguila_banner.png");
            Icon icono = new ImageIcon(logo.getImage().getScaledInstance(800, 100, Image.SCALE_DEFAULT));
            lbImagen.setIcon(icono);

            // Crear el label del precio total
            JLabel totalPriceLabel = new JLabel(contentTicket/*"Total: $100000" /*+ ticket.getTotalPrice()*/);
            JLabel headerLabel = new JLabel("    Pavonado {{sucursal}}", JLabel.CENTER);
            JLabel direccLabel = new JLabel("    {{direccion}}", JLabel.CENTER);
            JLabel dividerLabel = new JLabel("    =========================================", JLabel.CENTER);
            JLabel ticketNumberLabel = new JLabel("    Ticket # {{ticket}}", JLabel.CENTER);
            JLabel dateLabel = new JLabel("    {{fecha}} {{hora}}", JLabel.CENTER);
            JLabel descriptionLabel = new JLabel("    Descripcion              Importe", JLabel.CENTER);
            JLabel itemsLabel = new JLabel("     {{items}}", JLabel.CENTER);
            JLabel costoLabel = new JLabel("    COSTO: ${{total}}", JLabel.CENTER);
            JLabel recibidoLabel = new JLabel("    RECIBIDO: ${{recibo}}    |    CAMBIO: ${{change}}\n", JLabel.CENTER);
            JLabel restanteLabel = new JLabel("    RESTANTE: ${{rest}}", JLabel.CENTER);
            JLabel graciasLabel = new JLabel("    GRACIAS POR SU PREFERENCIA...", JLabel.CENTER);
            JLabel endLabel = new JLabel("                ******::::::::*******", JLabel.CENTER);
            // Crear main frame
            JFrame previewFrame = new JFrame("Ticket Preview");
            JPanel mainPanel = new JPanel(new GridLayout(0,1));
            
            previewFrame.setLayout(new FlowLayout());
            previewFrame.add(lbImagen);
            previewFrame.add(headerLabel);
            previewFrame.add(direccLabel);
            previewFrame.add(dividerLabel);
            previewFrame.add(ticketNumberLabel);
            previewFrame.add(dateLabel);
            previewFrame.add(descriptionLabel);
            previewFrame.add(dividerLabel);
            previewFrame.add(itemsLabel);
            previewFrame.add(dividerLabel);
            previewFrame.add(costoLabel);
            previewFrame.add(recibidoLabel);
            previewFrame.add(restanteLabel);
            previewFrame.add(dividerLabel);
            previewFrame.add(graciasLabel);
            previewFrame.add(endLabel);
            previewFrame.add(itemsTable);
            previewFrame.setSize(800, 600);
            previewFrame.setVisible(true);

            JButton btImprimir = new JButton("Imprimir");
            btImprimir.addActionListener(this);
            JPanel pImprimir = new JPanel(new FlowLayout(FlowLayout.LEFT));
            pImprimir.add(btImprimir);
            previewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }
}
