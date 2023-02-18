import javax.swing.*;
import java.awt.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class SubPiezaForm extends JPanel implements ChangeListener{
    private JLabel  lbPrecioCustom;
    private String namesServicio[];
    private Elemento elemento; 
    public JSpinner sPrecioCustom;
    private SpinnerModel smPrecioCustom;
    private JComboBox<String> cbServicios;
    private JButton btnX;

    public SubPiezaForm(String[] namesServicio , Elemento elemento){
        super(new FlowLayout(FlowLayout.LEFT));
        this.namesServicio=namesServicio;
        this.elemento = elemento;
        cbServicios = new JComboBox<String>(namesServicio);
        lbPrecioCustom =  new JLabel(" Costo");
        smPrecioCustom = new SpinnerNumberModel(0.0,0.0,null,0.1);
        sPrecioCustom = new JSpinner(smPrecioCustom);
        sPrecioCustom.addChangeListener(this);
        
        btnX = new JButton("X");
        btnX.setBackground(new Color(255,0,0));
        btnX.setForeground(new Color(255,255,255));
        
        this.add(cbServicios);
        this.add(lbPrecioCustom);
        this.add(sPrecioCustom);
        this.add(btnX); 
    }
       
    public JButton getBotonEliminar(){
        return this.btnX;
   }

   public JComboBox getCBservicios(){
        return cbServicios;
   }

    @Override
    public void stateChanged(ChangeEvent e) {
        // TODO Auto-generated method stub
        
    }
}
