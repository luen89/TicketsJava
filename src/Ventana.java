
import javax.swing.*;

/**
 *
 * @author Compa's Team
 */
public class Ventana extends JFrame{
    PanelEntrada panelE;
    PanelRegistros panelRegistros;
    JTabbedPane pestañas;
    GestorArchivos ga;
    //Controlador miControlador;
    
    public Ventana(GestorArchivos ga){
        setTitle("Pavonado");
        setSize(820,560);
        this.setResizable(true);
        setLocationRelativeTo(null);
        this.ga = ga;
        initComponents();
        ImageIcon icono = new ImageIcon("src/Imagenes/pavonado_logo.png"); 
        this.setIconImage(icono.getImage()); 
        
    }

    private void initComponents() {
        panelE=new PanelEntrada(ga);
        panelRegistros = new PanelRegistros(ga);
        //panel1=new PanelPrueba();
        pestañas=new JTabbedPane();
        
        pestañas.add("Recibo", panelE);
        pestañas.add("Registros", panelRegistros);        
        
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
