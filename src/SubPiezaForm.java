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


    @Override
    public void stateChanged(ChangeEvent e) {
        subelemento.setCosto(Double.parseDouble(sPrecioCustom.getValue().toString()));
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnX ){
            padre.padre.control.matrix.matriz[subelemento.getServicio().id][padre.elemento.getAceroObject().id]-=padre.elemento.getKilos();
            subelemento.padre.removeSubElemento(subelemento);
            padre.subpiezasArray.remove(this);
            padre.panelSubServicios.remove(this);
            padre.padre.updateUI();
            if(padre.padre.control.getAutoCalculo()){padre.calcularCostosLista();}
        }
        
        if(e.getSource()==cbServicios){
            padre.padre.control.matrix.matriz[subelemento.getServicio().id][padre.elemento.getAceroObject().id]-=padre.elemento.getKilos();
            subelemento.setServicio(padre.padre.control.arrayServicios.get(cbServicios.getSelectedIndex()));
            padre.padre.control.matrix.matriz[subelemento.getServicio().id][padre.elemento.getAceroObject().id]+=padre.elemento.getKilos();
            sPrecioCustom.setValue(subelemento.getServicio().obtenerCosto(padre.padre.control.matrix.matriz[subelemento.getServicio().id][padre.elemento.getAceroObject().id], padre.elemento.getKilos()));
            padre.padre.control.matrix.printMatriz();
            if(padre.padre.control.getAutoCalculo()){padre.calcularCostosLista();}
        }
        
    }
}
