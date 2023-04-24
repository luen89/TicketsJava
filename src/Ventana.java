
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Compa's Team
 */
public class Ventana extends JFrame{
     public ArrayList<Servicio> arrayServicios = new ArrayList<Servicio>();
    ArrayList<Acero> arrayAceros = new ArrayList<Acero>();
    PanelEntrada panelE;
    public ControladorPE controlPE;
    PanelRegistros panelRegistros;
    ServicioPanel panelServicios;
    AceroPanel panelAceros;
    JTabbedPane pestañas;
    GestorArchivos ga;
    //Controlador miControlador;
    
    public Ventana(GestorArchivos ga){
        setTitle("Pavonado");
        setSize(1150,560);
        this.setResizable(true);
        setLocationRelativeTo(null);
        this.ga = ga;

        //Esto se obtendra desde el gestor de archivos despues
        arrayServicios=ga.leerServicios();
        if(arrayServicios.isEmpty()){
            System.out.println("Archivo vacio o inexistente se crean servicios predefinidos"); 
            arrayServicios.add(new Servicio(0,"Pavonado limpio","PAVL",220.0,280.0,39.0,3,8));
            arrayServicios.add(new Servicio(1,"Pavonado Sucio","PAVS",220.0,280.0,45.0,3,8));
            arrayServicios.add(new Servicio(2,"Temple Normal","TEMN",290.0,0.0,95.0,4,0));
            arrayServicios.add(new Servicio(3,"Temple Doble","TEMD",310.0,0.0,95.0,4,0));
            arrayServicios.add(new Servicio(4,"Cementacion","CEM",310.0,0.0,95.0,4,0));
            arrayServicios.add(new Servicio(5,"Cementacion Especial","CEMX",310.0,0.0,120.0,4,0));
            arrayServicios.add(new Servicio(6,"Normalizado","NORM",250.0,0.0,65.0,4,0));
            arrayServicios.add(new Servicio(7,"Normalizado Especial","NORX",300.0,0.0,75.0,4,0));
            arrayServicios.add(new Servicio(8,"Bonificado","BON",310.0,0.0,90.0,4,0));
            arrayServicios.add(new Servicio(9,"Temple Regionalizado","TEMR",450.0,0.0,120.0,4,0));
            ga.escribirServicios(arrayServicios);
        }

        arrayAceros=ga.leerAceros();
        if(arrayAceros.isEmpty()){
            System.out.println("Archivo vacio o inexistente se crean aceros predefinidos"); 
            arrayAceros.add(new Acero(0,"4140"));
            arrayAceros.add(new Acero(1,"8620"));
            arrayAceros.add(new Acero(2,"1045"));
            arrayAceros.add(new Acero(3,"C.R"));
            arrayAceros.add(new Acero(4,"Q1"));
            arrayAceros.add(new Acero(5,"D2"));
            ga.escribirAceros(arrayAceros);
        }



        



        initComponents();
        ImageIcon icono = new ImageIcon("src/Imagenes/Aguila_logo.png"); 
        this.setIconImage(icono.getImage()); 
        
    }

    private void initComponents() {
        controlPE= new ControladorPE(arrayServicios, arrayAceros);
        panelE=new PanelEntrada(ga, controlPE);
        panelRegistros = new PanelRegistros(ga);
        panelServicios = new ServicioPanel(ga, this);
        panelAceros = new AceroPanel(ga, this);
        //panel1=new PanelPrueba();
        pestañas=new JTabbedPane();
        
        pestañas.add("Recibo", panelE);
        pestañas.add("Registros", panelRegistros);
        pestañas.add("Servicios", panelServicios);  
        pestañas.add("Aceros",panelAceros);   
        
        pestañas.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                if(index == 1){
                    panelRegistros.actualizarTabla();
                }
                // System.out.println("Tab changed to: " + sourceTabbedPane.getTitleAt(index));                
            }
        });
        
        add(pestañas);
    }

    public void restartPanelEntrada(){
        controlPE.initVars();
        panelE.removeAll();
        panelE.initComponents();
        panelE.updateUI();
    }
    
    /*  public void setControlador(Controlador putojulio){
        
    }
    public void setControladorEntrada(Controlador putocoria){
        panelE.setControlador(putocoria);
    }
      public void setControladorSalida(Controlador putocoria){
        panelS.setControlador(putocoria);
    }

*/
    
}
