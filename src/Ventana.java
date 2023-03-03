
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
    ControladorPE controlPE;
    PanelRegistros panelRegistros;
    ServicioPanel panelServicios;
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
        arrayServicios.add(new Servicio(0,"Pavonado limpio",220.0,280.0,39.0,3,8));
        arrayServicios.add(new Servicio(1,"Pavonado Sucio",220.0,280.0,45.0,3,8));
        arrayServicios.add(new Servicio(2,"Temple Normal",290.0,290.0,95.0,0,4));
        arrayServicios.add(new Servicio(3,"Temple Doble",310.0,310.0,95.0,0,4));
        arrayAceros.add(new Acero(0,"Acero0"));
        arrayAceros.add(new Acero(1,"Acero1"));
        arrayAceros.add(new Acero(2,"Acero2"));
        arrayAceros.add(new Acero(3,"Acero3"));



        initComponents();
        ImageIcon icono = new ImageIcon("src/Imagenes/Aguila_logo.png"); 
        this.setIconImage(icono.getImage()); 
        
    }

    private void initComponents() {
        controlPE= new ControladorPE(arrayServicios, arrayAceros);
        panelE=new PanelEntrada(ga, controlPE);
        panelRegistros = new PanelRegistros(ga);
        panelServicios = new ServicioPanel(ga, this);
        //panel1=new PanelPrueba();
        pestañas=new JTabbedPane();
        
        pestañas.add("Recibo", panelE);
        pestañas.add("Registros", panelRegistros);
        pestañas.add("Servicios", panelServicios);     
        
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
