
import javax.swing.*;

/**
 *
 * @author Compa's Team
 */
public class Ventana extends JFrame{
    PanelEntrada panelE;
    JTabbedPane pestañas;
    //Controlador miControlador;
    
    public Ventana(){
        setTitle("Pavonado");
        setSize(820,560);
        this.setResizable(true);
        setLocationRelativeTo(null);
        initComponents();
        ImageIcon icono = new ImageIcon("src/Imagenes/pavonado_logo.png"); 
        this.setIconImage(icono.getImage()); 
    }

    private void initComponents() {
        panelE=new PanelEntrada();
        //panel1=new PanelPrueba();
        pestañas=new JTabbedPane();
        
        pestañas.add("Recibo", panelE);
        
        
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
