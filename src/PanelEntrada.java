
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
public class PanelEntrada extends JPanel implements ActionListener{
    private JLabel  lbTotPiezas, lbTotKg, lbRD,lbGD, lbImagen;
    private JButton btImprimir, btPago, btplus, btless;
    private JPanel pDatos, pEncabezado, pTotales, pImprimir,
    pTemplado,pIncrementoT,pListTemplado;
    private JTextField  txtTpiezas, txtTkg;
    private JScrollPane scrollT;
    private ArrayList<PiezaForm> pformsP, pformsT;
    private Calendar cal = Calendar.getInstance();
    private Date initDate;
    private JSpinner jspReceptionDate , jspGiveDate;

    TicketPreview2 tPreview2;

    //prueba
    
    public PanelEntrada(){   
        
        initComponents();
    }

    private void initComponents() {
        /*****Configuracion de los Spinners de Fecha */
        initDate = cal.getTime();
        cal.add(Calendar.YEAR,-100);
        Date earliestDate = cal.getTime();
        cal.add(Calendar.YEAR, 200);
        Date latestDate = cal.getTime();
        SpinnerDateModel spdm = new SpinnerDateModel(initDate,earliestDate,latestDate,Calendar.DAY_OF_MONTH);
        SpinnerDateModel spdm2 = new SpinnerDateModel(initDate,earliestDate,latestDate,Calendar.DAY_OF_MONTH);
        jspReceptionDate = new JSpinner(spdm);
        jspGiveDate = new JSpinner(spdm2);
        
        /*Construccion de la lista de Piezas de Pavoneo */

        pformsP = new ArrayList<>();
        pformsP.add(new PiezaForm());
        pformsP.get(0).preDisplay();

        /*Construccion de la lista de Piezas de Templado */
        pListTemplado = new JPanel();
        pListTemplado.setLayout(new BoxLayout(pListTemplado,BoxLayout.Y_AXIS));
        pformsT = new ArrayList<>();
        pformsT.add(new PiezaForm());
        pformsT.get(0).preDisplay();
        pListTemplado.add(pformsT.get(0));
        scrollT = new JScrollPane(pListTemplado);
        
        //************Etiquetas del Total de Piezas******************************
        lbTotPiezas = new JLabel("Total Piezas:");
        lbTotKg = new JLabel("Total Kg:");
        lbRD=new JLabel("Fecha de Recepcion");
        lbGD=new JLabel("Fecha de Entrega");
        lbRD.setForeground(Color.white);
        lbGD.setForeground(Color.white);
        txtTkg = new JTextField(" ", 8);
        txtTpiezas=new JTextField("Suma de piezas",8);
        txtTpiezas.setEditable(false);

        /* botones de Imprimir y Pagar */
        btImprimir = new JButton("Imprimir");
        btImprimir.addActionListener(this);
        btPago=new JButton("Pagar");
        btPago.addActionListener(this);

        /* Botones de mas y menos */
        btplus = new JButton("  +  ");
        btplus.addActionListener(this);
        btless = new JButton("  -  ");
        btless.addActionListener(this);
        
        //***************PANELES DE DATOS*************************

        /*Panel de los botones mas o menos Templado */
        pIncrementoT = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pIncrementoT.add(btplus);
        pIncrementoT.add(btless);

        /*Panel de los totales de Piezas */
        pTotales=new JPanel(new FlowLayout(FlowLayout.LEFT));
        pTotales.add(lbTotPiezas);
        pTotales.add(txtTpiezas);
        pTotales.add(lbTotKg);
        pTotales.add(txtTkg);

        //***************Panel De Encabezado*************************
        pEncabezado = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lbImagen=new JLabel();
        lbImagen.setPreferredSize(new Dimension(750,100));
        ImageIcon fot = new ImageIcon("src/Imagenes/Aguila_banner.png");
        //lbImagen.setIcon(new ImageIcon("src/Imagenes/imagen.png"));
        Icon icono = new ImageIcon(fot.getImage().getScaledInstance(750, 100, Image.SCALE_DEFAULT));
        lbImagen.setIcon(icono);
        pEncabezado.add(lbImagen);
        pEncabezado.add(lbRD);
        pEncabezado.add(jspReceptionDate);
        pEncabezado.add(lbGD);
        pEncabezado.add(jspGiveDate);
        pEncabezado.setBackground(Color.black);
        repaint();

        //*****************Panel del Templado**************************
        pTemplado=new JPanel();
        pTemplado.setLayout(new BoxLayout(pTemplado,BoxLayout.Y_AXIS));
        pTemplado.add(pIncrementoT);
        pTemplado.add(scrollT);
       
        
        pDatos=new JPanel();
        pDatos.setLayout(new BoxLayout(pDatos,BoxLayout.Y_AXIS));        
        pDatos.add(pTotales);
        
        
        pImprimir=new JPanel(new FlowLayout(FlowLayout.LEFT));
        pImprimir.add(btImprimir);
        //**********************************************************
        Font a=new Font("Calibri",1,14);
        Border bordeEntrada= new TitledBorder(new EtchedBorder(Color.white,Color.white), "Inicio",1,2,a, Color.white);
        
        pEncabezado.setBorder(bordeEntrada);               

        Border bordePane3= new TitledBorder(new EtchedBorder(), "Templado");
        pTemplado.setBorder(bordePane3);
        
        Border bordePanel2= new TitledBorder(new EtchedBorder(), "Total");
        pDatos.setBorder(bordePanel2);
        
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        add(pEncabezado);
        add(pTemplado);
        add(pDatos);
        add(pImprimir);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Agregar nuevo elemento
        if(e.getSource()==btplus){
            pformsP.add(new PiezaForm());
            pformsP.get(pformsP.size()-1).preDisplay();
            pListTemplado.add(pformsP.get(pformsP.size()-1));
            this.updateUI();
        }   
        //Quitar elemento
        if(e.getSource()==btless){
            pformsP.remove(pformsP.size()-1);
            pListTemplado.removeAll();
                pformsP.forEach((form) -> {
            pListTemplado.add(form);
        });
            this.updateUI();
        }
        if(e.getSource()==btImprimir){
            tPreview2 = new TicketPreview2();
            System.out.println("Entré");
            tPreview2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            tPreview2.setVisible(true);
        }
    }
    
    public String getTotalPiezas(){
        return txtTpiezas.getText();
    }
    
    public void setControlador(){
    }
    
    
    public String getTotalPeso(){
        return txtTkg.getText();
    }
}
