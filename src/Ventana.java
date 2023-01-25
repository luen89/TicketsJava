
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
