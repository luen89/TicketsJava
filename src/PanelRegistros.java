
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.validation.Validator;

/**
 *
 * @author Zyngy Coding
 */

public class PanelRegistros extends JPanel implements ActionListener {    
    private JTable tabla;

    private JLabel lbTotPiezas, lbTotKg, lbRD, lbGD, lbImagen;
    private JButton btImprimir, btPago, btplus;
    private JPanel pDatos, pEncabezado, pTotales, pImprimir,
            pTemplado, pIncrementoT, pListTemplado;
    private JTextField txtTpiezas, txtTkg;
    private JScrollPane scrollT;
    private ArrayList<PiezaForm> pformsP, pformsT;
    private Calendar cal = Calendar.getInstance();
    private Date initDate;

    private JSpinner jspReceptionDate, jspGiveDate;
    private JCheckBox iva;
    TicketPreview2 tPreview2;
    // prueba
    private int contador =0, ban=0;
    private ArrayList<JButton> listaBotones = new ArrayList<JButton>();
    private JScrollPane sp;
    private GestorArchivos gestor;
    
    public PanelRegistros(GestorArchivos ga) {
        gestor=ga;
        initComponents();
    }

    private void initComponents() {
        // Creación de Tabla
        ArrayList<EntradaRegistro> registros = gestor.leerArchivo();
        String data[][] = new String[registros.size()][6];
        for(int i = 0; i< registros.size(); i++){
            EntradaRegistro er = registros.get(i);
            data[i] = new String[6];
            data[i][0] = er.Folio;
            data[i][1] = er.NombreCliente;
            data[i][2] = "$" + er.Monto;
            data[i][3] = er.StatusPago;
            data[i][4] = er.StatusEntrega;
            data[i][5] = er.Fecha;
        }
        // String data[][]={ {"101","Amit","670000", "Status", "Status", "Fecha"},    
        //                 {"102","Amit","670000", "Status", "Status", "Fecha"},    
        //                 {"103","Amit","670000", "Status", "Status", "Fecha"}};    
        String column[] = {"Folio", "Cliente", "Monto", "Status de Pago", "Status de Entrega", "Fecha"};
        
        this.tabla = new JTable (data, column);

        /*****  Configuracion de los Spinners de Fecha */
        // initDate = cal.getTime();
        // cal.add(Calendar.YEAR,  -100);
        // Date earliestDate = cal.getTime();
        // cal.add(Calendar.YEAR, 200);
        // Date latestDate = cal.getTime();
        // SpinnerDateModel spdm = new SpinnerDateModel(initDate,  earliestDate,  latestDate,  Calendar.DAY_OF_MONTH);
        // SpinnerDateModel spdm2 = new SpinnerDateModel(initDate,  earliestDate,  latestDate,  Calendar.DAY_OF_MONTH);
        // jspReceptionDate = new JSpinner(spdm);
        // jspGiveDate = new JSpinner(spdm2);

        /* Construccion de la lista de Piezas de Pavoneo */

        /* Construccion de la lista de Piezas de Pavoneo */

        // pformsP = new ArrayList<>();
        //cocnstrutor de contador
        // pformsP.add(new PiezaForm(contador));
        // pformsT.get(0).preDisplay();


        /*  Construccion de la lista de Piezas de Templado */
        // pListTemplado = new JPanel();
        // pListTemplado.setLayout(new BoxLayout(pListTemplado,  BoxLayout.Y_AXIS));
        // pformsT = new ArrayList<>();
        // scrollT = new JScrollPane(pListTemplado);
        
        // PiezaForm pieza = new PiezaForm();
        
        // pformsT.add(pieza);
        // pieza.preDisplay();
        // pListTemplado.add(pieza);
        // pieza.getBotonEliminar().addActionListener(new ActionListener() {
        //     @Override

        //     public void actionPerformed(ActionEvent e) {
        //         pListTemplado.remove(pieza);
        //         pformsT.remove(pieza);
        //         refreshDisplay();
        //     }
        // });


        
        //  ************Etiquetas del Total de Piezas******************************
        // lbTotPiezas = new JLabel("Total Piezas:");
        // lbTotKg = new JLabel("Total Kg:");
        // lbRD  =  new JLabel("Fecha de Recepcion");
        // lbGD  =  new JLabel("Fecha de Entrega");
        // lbRD.setForeground(Color.white);
        // lbGD.setForeground(Color.white);
        // txtTkg = new JTextField(" ", 8);
        // txtTpiezas  =  new JTextField("Suma de piezas",  8);
        // txtTpiezas.setEditable(false);

        // /* botones de Imprimir y Pagar */
        // btImprimir = new JButton("Imprimir");
        // btImprimir.addActionListener(this);

        // iva = new JCheckBox("¿Requiere factura?");

        // btPago  =  new JButton("Pagar");
        // btPago.addActionListener(this);

        // /* Botones de mas y menos */
        // btplus = new JButton("Agregar elemento");
        // btplus.addActionListener(this);

        // // ***************PANELES DE DATOS*************************

        // // ***************PANELES DE DATOS*************************
        // /*  Panel de los botones mas o menos Templado */
        // pIncrementoT = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // pIncrementoT.add(btplus);

        // /*  Panel de los totales de Piezas */
        // pTotales  =  new JPanel(new FlowLayout(FlowLayout.LEFT));
        // pTotales.add(lbTotPiezas);
        // pTotales.add(txtTpiezas);
        // pTotales.add(lbTotKg);
        // pTotales.add(txtTkg);

        // //  ***************Panel De Encabezado*************************
        // pEncabezado = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // lbImagen  =  new JLabel();
        // lbImagen.setPreferredSize(new Dimension(750,  100));
        // ImageIcon fot = new ImageIcon("src/Imagenes/Aguila_banner.png");
        // //  lbImagen.setIcon(new ImageIcon("src/Imagenes/imagen.png"));
        // Icon icono = new ImageIcon(fot.getImage().getScaledInstance(750, 100, Image.SCALE_DEFAULT));
        // lbImagen.setIcon(icono);
        // pEncabezado.add(lbImagen);
        // pEncabezado.add(lbRD);
        // pEncabezado.add(jspReceptionDate);
        // pEncabezado.add(lbGD);
        // pEncabezado.add(jspGiveDate);
        // pEncabezado.setBackground(Color.black);
        // repaint();

        // //  *****************Panel del Templado**************************
        // pTemplado  =  new JPanel();
        // pTemplado.setLayout(new BoxLayout(pTemplado,  BoxLayout.Y_AXIS));
        // pTemplado.add(pIncrementoT);
        // pTemplado.add(scrollT);

        // pDatos = new JPanel();
        // pDatos.setLayout(new BoxLayout(pDatos, BoxLayout.Y_AXIS));

        // pDatos = new JPanel();
        // pDatos.setLayout(new BoxLayout(pDatos, BoxLayout.Y_AXIS));
        // pDatos.add(pTotales);

        // pImprimir = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // pImprimir = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // pImprimir.add(btImprimir);
        // pImprimir.add(iva);
        // // **********************************************************
        // Font a = new Font("Calibri", 1, 14);
        // Border bordeEntrada = new TitledBorder(new EtchedBorder(Color.white, Color.white), "Inicio", 1, 2, a,
        //         Color.white);

        // pEncabezado.setBorder(bordeEntrada);

        // Border bordePane3  = new TitledBorder(new EtchedBorder(), "Servicios");
        // pTemplado.setBorder(bordePane3);

        // Border bordePanel2 = new TitledBorder(new EtchedBorder(), "Total");
        // pDatos.setBorder(bordePanel2);

        // this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        sp = new JScrollPane(this.tabla); 
        add(sp);
        // add(pTemplado);
        //add(pDatos);
        // add(pImprimir);
    }

    public void refreshDisplay(){
        this.updateUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //  Agregar nuevo elemento
        // int valor;

        // if  (e.getSource()  ==  btplus)  {
        //     PiezaForm pieza = new PiezaForm();
        //     pformsT.add(pieza);
        //     pieza.preDisplay();
        //     pieza.getBotonEliminar().addActionListener(new ActionListener() {
        //        @Override

        //         public void actionPerformed(ActionEvent e) {
        //             pListTemplado.remove(pieza);
        //             pformsT.remove(pieza);
        //             refreshDisplay();
        //         }
        //   });
        //     pListTemplado.add(pformsT.get(pformsT.size()  -  1));

        //     this.updateUI();
        //     System.out.println("Presionaste mas");
        // }

        // if (e.getSource() == btImprimir) {
        //     Ticket ticketsito = condiciones();
        //     if(contador >=1){
        //         contador--;
        //     }
        //     tPreview2 = new TicketPreview2(ticketsito);
        //     System.out.println("Entré");
        //     tPreview2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //     tPreview2.setVisible(true);            
        // }

    }
   
}
