/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author luen8
 */
public class PiezaForm extends JPanel{
       private JLabel lbNPiezas, lbAcero , lbpeso;
       private String acero[] = {"Selecciona tipo", "Suave", "Semisuave", "Semiduro","Duro","Extraduro"};
       private SpinnerModel smNpiezas;
       private JSpinner sNpiezas;
       private JComboBox cbAcero;
       private JTextField txtPeso;
       
       public PiezaForm(){
           super(new FlowLayout(FlowLayout.LEFT));
           smNpiezas = new SpinnerNumberModel(0,0,100,1);
            sNpiezas = new JSpinner(smNpiezas);
            lbNPiezas = new JLabel("Numero de Piezas");
            lbAcero= new JLabel("       Acero");
            lbpeso= new JLabel("        Peso en Kg");
            cbAcero = new JComboBox(acero);
            txtPeso = new JTextField(" ", 8);
            
            
       }
       public void preDisplay(){
            this.add(lbNPiezas);
            this.add(sNpiezas);
            this.add(lbAcero);
            this.add(cbAcero);
            this.add(lbpeso);
            this.add(txtPeso);
       }
    
}
