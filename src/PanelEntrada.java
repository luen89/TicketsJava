
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.*;
import javax.xml.validation.Validator;

/**
 *
 * @author Luis Enrique Pérez González
 */

public class PanelEntrada extends JPanel implements ActionListener {
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
    // prueba
    private int contador =0, ban=0;
    private ArrayList<JButton> listaBotones = new ArrayList<JButton>();
    

    public PanelEntrada() {

        initComponents();
    }

    private void initComponents() {
        /*****  Configuracion de los Spinners de Fecha */
        initDate = cal.getTime();
        cal.add(Calendar.YEAR,  -100);
        Date earliestDate = cal.getTime();
        cal.add(Calendar.YEAR, 200);
        Date latestDate = cal.getTime();
        SpinnerDateModel spdm = new SpinnerDateModel(initDate,  earliestDate,  latestDate,  Calendar.DAY_OF_MONTH);
        SpinnerDateModel spdm2 = new SpinnerDateModel(initDate,  earliestDate,  latestDate,  Calendar.DAY_OF_MONTH);
        jspReceptionDate = new JSpinner(spdm);
        jspGiveDate = new JSpinner(spdm2);

        /* Construccion de la lista de Piezas de Pavoneo */

        /* Construccion de la lista de Piezas de Pavoneo */

        // pformsP = new ArrayList<>();
        //cocnstrutor de contador
        // pformsP.add(new PiezaForm(contador));
        // pformsT.get(0).preDisplay();


        /*  Construccion de la lista de Piezas de Templado */
        pListTemplado = new JPanel();
        pListTemplado.setLayout(new BoxLayout(pListTemplado,  BoxLayout.Y_AXIS));
        pformsT = new ArrayList<>();
        scrollT = new JScrollPane(pListTemplado);
        
        PiezaForm pieza = new PiezaForm();
        
        pformsT.add(pieza);
        pieza.preDisplay();
        pListTemplado.add(pieza);
        pieza.getBotonEliminar().addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                pListTemplado.remove(pieza);
                pformsT.remove(pieza);
                refreshDisplay();
            }
        });


        
        //  ************Etiquetas del Total de Piezas******************************
        lbTotPiezas = new JLabel("Total Piezas:");
        lbTotKg = new JLabel("Total Kg:");
        lbRD  =  new JLabel("Fecha de Recepcion");
        lbGD  =  new JLabel("Fecha de Entrega");
        lbRD.setForeground(Color.white);
        lbGD.setForeground(Color.white);
        txtTkg = new JTextField(" ", 8);
        txtTpiezas  =  new JTextField("Suma de piezas",  8);
        txtTpiezas.setEditable(false);

        /* botones de Imprimir y Pagar */
        btImprimir = new JButton("Imprimir");
        btImprimir.addActionListener(this);

        iva = new JCheckBox("¿Requiere factura?");

        btPago  =  new JButton("Pagar");
        btPago.addActionListener(this);

        /* Botones de mas y menos */
        btplus = new JButton("Agregar elemento");
        btplus.addActionListener(this);

        // ***************PANELES DE DATOS*************************

        // ***************PANELES DE DATOS*************************
        /*  Panel de los botones mas o menos Templado */
        pIncrementoT = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pIncrementoT.add(btplus);

        /*  Panel de los totales de Piezas */
        pTotales  =  new JPanel(new FlowLayout(FlowLayout.LEFT));
        pTotales.add(lbTotPiezas);
        pTotales.add(txtTpiezas);
        pTotales.add(lbTotKg);
        pTotales.add(txtTkg);

        //  ***************Panel De Encabezado*************************
        pEncabezado = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lbImagen  =  new JLabel();
        lbImagen.setPreferredSize(new Dimension(750,  100));
        ImageIcon fot = new ImageIcon("src/Imagenes/Aguila_banner.png");
        //  lbImagen.setIcon(new ImageIcon("src/Imagenes/imagen.png"));
        Icon icono = new ImageIcon(fot.getImage().getScaledInstance(750, 100, Image.SCALE_DEFAULT));
        lbImagen.setIcon(icono);
        pEncabezado.add(lbImagen);
        pEncabezado.add(lbRD);
        pEncabezado.add(jspReceptionDate);
        pEncabezado.add(lbGD);
        pEncabezado.add(jspGiveDate);
        pEncabezado.setBackground(Color.black);
        repaint();

        //  *****************Panel del Templado**************************
        pTemplado  =  new JPanel();
        pTemplado.setLayout(new BoxLayout(pTemplado,  BoxLayout.Y_AXIS));
        pTemplado.add(pIncrementoT);
        pTemplado.add(scrollT);

        pDatos = new JPanel();
        pDatos.setLayout(new BoxLayout(pDatos, BoxLayout.Y_AXIS));

        pDatos = new JPanel();
        pDatos.setLayout(new BoxLayout(pDatos, BoxLayout.Y_AXIS));
        pDatos.add(pTotales);

        pImprimir = new JPanel(new FlowLayout(FlowLayout.LEFT));

        pImprimir = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pImprimir.add(btImprimir);
        pImprimir.add(iva);
        // **********************************************************
        Font a = new Font("Calibri", 1, 14);
        Border bordeEntrada = new TitledBorder(new EtchedBorder(Color.white, Color.white), "Inicio", 1, 2, a,
                Color.white);

        pEncabezado.setBorder(bordeEntrada);

        Border bordePane3  = new TitledBorder(new EtchedBorder(), "Servicios");
        pTemplado.setBorder(bordePane3);

        Border bordePanel2 = new TitledBorder(new EtchedBorder(), "Total");
        pDatos.setBorder(bordePanel2);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(pEncabezado);
        add(pTemplado);
        add(pDatos);
        add(pImprimir);

        /*for(int i=0; i < pformsP.size();i++){
            listaBotones.add(pformsP.get(i).getBtnX());
            listaBotones.get(i).addActionListener(this);
        }*/
    }

    public void refreshDisplay(){
        this.updateUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //  Agregar nuevo elemento
        int valor;

        

        if  (e.getSource()  ==  btplus)  {
            PiezaForm pieza = new PiezaForm();
            pformsT.add(pieza);
            pieza.preDisplay();
            pieza.getBotonEliminar().addActionListener(new ActionListener() {
               @Override

                public void actionPerformed(ActionEvent e) {
                    pListTemplado.remove(pieza);
                    pformsT.remove(pieza);
                    refreshDisplay();
                }
          });
            pListTemplado.add(pformsT.get(pformsT.size()  -  1));

            this.updateUI();
            System.out.println("Presionaste mas");
        }

        if (e.getSource() == btImprimir) {
            condiciones();
            if(contador >=1){
                contador--;
            }
        }




        /*
        //Eliminar elemento especifico 
        System.out.println("Lista es: "+ listaBotones.size());
        //System.out.println("Get:"+e.getSource()+"\nBoton X: "+pformsP.get(0).getBtnX());
        if (e.getSource() == listaBotones.get(0)) {
            System.out.println("Borrando seleccionado");
        }
        for(int i= 0; i>contador; i++){
            System.out.println("MENSAJE 123");
            if (0 == pformsP.get(0).boton()) {
                System.out.println("webfalsidbflafnño");
            }
        }
        */
        //if(pformsP.get(contador).boton() <=contador){
            //pformsP.remove(pformsP.size() - 1)
        //}
    }

    public void recibir(int id){
        System.out.println("Imprimiendo desde panel entrada: "+id);
    }

    public void condiciones() {
        double contadorPavonado = 0;
        double contadorTemplado = 0;

        double costoPavonado = 0.0;
        double costoTemplado = 0.0;

        double costoTotal = 0.0;

        for (int i = 0; i < pformsT.size(); i++) {
            System.out.println("Servicio: " + pformsT.get(i).getElemento().getServicio());

            if (pformsT.get(i).getElemento().getServicio().equals("Pavonado")) {
                contadorPavonado += pformsT.get(i).getElemento().getKilos();
            } else if (pformsT.get(i).getElemento().getServicio().equals("Templado")) {
                contadorTemplado += pformsT.get(i).getElemento().getKilos();
            } else if (pformsT.get(i).getElemento().getServicio().equals("Templado y Pavonado")) {
                contadorPavonado += pformsT.get(i).getElemento().getKilos();
                contadorTemplado += pformsT.get(i).getElemento().getKilos();
            }
        }

        System.out.println("Kilos pavonado: " + contadorPavonado);
        System.out.println("Kilos templado: " + contadorTemplado);

        if (contadorPavonado > 0 && contadorPavonado > 8) {
            costoPavonado += contadorPavonado * 39;
        } else if(contadorPavonado > 0 && contadorPavonado <= 8) {
            costoPavonado += 280;
        }

        if (contadorTemplado > 0 && contadorTemplado > 4) {
            costoTemplado += contadorTemplado * 95;
        } else if(contadorTemplado > 0 && contadorTemplado <= 4) {
            costoTemplado += 310;
        }

        System.out.println("Costo pavonado: " + costoPavonado);
        System.out.println("Costo templado: " + costoTemplado);

        costoTotal = costoPavonado + costoTemplado;

        if(iva.isSelected()){
            costoTotal += costoTotal * 0.16;
        }

        System.out.println("Costo total: " + costoTotal);
    }

    public String getTotalPiezas() {
        return txtTpiezas.getText();
    }

    public void setControlador() {
    }

    public String getTotalPeso() {
        return txtTkg.getText();
    }

    
}
