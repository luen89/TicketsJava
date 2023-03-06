import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class SubPiezaForm extends JPanel implements ChangeListener,ActionListener{
    private PiezaForm padre;
    private JLabel  lbPrecioCustom;
    private String namesServicio[];
    public SubElemento subelemento;
    public JSpinner sPrecioCustom;
    private SpinnerModel smPrecioCustom;
    private JComboBox<String> cbServicios;
    private JButton btnX;

    public SubPiezaForm(PiezaForm padre, String[] namesServicio , SubElemento subelemento){
        super(new FlowLayout(FlowLayout.LEFT));
        this.padre=padre;
        this.subelemento=subelemento;
        this.namesServicio=namesServicio;

        cbServicios = new JComboBox<String>(namesServicio);
        cbServicios.addActionListener(this);

        lbPrecioCustom =  new JLabel(" Costo");
        smPrecioCustom = new SpinnerNumberModel(0.0,0.0,null,0.1);
        sPrecioCustom = new JSpinner(smPrecioCustom);
        sPrecioCustom.setEnabled(false);
        sPrecioCustom.addChangeListener(this);
        
        btnX = new JButton("X");

        btnX.addActionListener(this);

        btnX.setBackground(new Color(255,0,0));
        btnX.setForeground(new Color(255,255,255));
        
        this.add(cbServicios);
        this.add(lbPrecioCustom);
        this.add(sPrecioCustom);
        this.add(btnX); 
    }

    public double obtenerCosto(){
        double aux=subelemento.calcularCosto();
        sPrecioCustom.setValue(aux);
        return aux;
    }


    @Override
    public void stateChanged(ChangeEvent e) {
        subelemento.setCosto(Double.parseDouble(sPrecioCustom.getValue().toString()));
        padre.padre.calcularTotal();
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnX ){
            subelemento.removerDeMatriz();
            subelemento.padre.removeSubElemento(subelemento);
            padre.subpiezasArray.remove(this);
            padre.panelSubServicios.remove(this);
            padre.padre.updateUI();
            if(padre.padre.control.getAutoCalculo()){padre.calcularCostosLista();}
        }
        
        if(e.getSource()==cbServicios){
            subelemento.removerDeMatriz();
            subelemento.setServicio(padre.padre.control.getServicioFromGeneralArray(cbServicios.getSelectedIndex()));
            subelemento.agregarAMatriz();
            //subelemento.calcularCosto();
            //sPrecioCustom.setValue(subelemento.getCosto());
            padre.padre.control.matrix.printMatriz();
            if(padre.padre.control.getAutoCalculo()){padre.calcularCostosLista();}
        }
        
    }
}
