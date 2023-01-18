/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 *
 * @author luen89
 */

public class PiezaForm extends JPanel{
       private JLabel lbNPiezas, lbAcero , lbpeso;
       private String Servicio[] = {"Selecciona Servicio","Pavonado","Templado","Templado y Pavonado"};
       private String acero[] = {"Selecciona tipo", "4140", "8620", "1045","Colled Rolled","Q1","D2"};
       private SpinnerModel smNpiezas;
       private JSpinner sNpiezas;
       private JComboBox cbServicios, cbAcero;
       private JTextField txtPeso;
       private Elemento elemento;


       public PiezaForm(){// Aqui
           super(new FlowLayout(FlowLayout.LEFT));
           cbServicios = new JComboBox(Servicio);
           cbServicios.addActionListener(new ActionListener () {                         
               @Override
               public void actionPerformed(ActionEvent e) {                                       
                    cbAcero.setSelectedItem(0);
                    switch(cbServicios.getSelectedIndex()){
                         case 1:
                              cbAcero.setModel(new DefaultComboBoxModel<>( acero ));
                              cbAcero.setEnabled(true);
                              break;
                         case 2, 3:
                              cbAcero.setModel(new DefaultComboBoxModel<>( 
                                   Arrays.stream(acero).filter(x -> x != "Colled Rolled").toArray()
                              ));
                              cbAcero.setEnabled(true);
                              break;
                         default:
                              cbAcero.setEnabled(false);                         
                    }
               }
           });
           smNpiezas = new SpinnerNumberModel(0,0,100,1);
            sNpiezas = new JSpinner(smNpiezas);
            lbNPiezas = new JLabel("Numero de Piezas");
            lbAcero= new JLabel("       Acero");
            lbpeso= new JLabel("        Peso en Kg");
            cbAcero = new JComboBox(acero);
            txtPeso = new JTextField(" ", 8);
            cbAcero.setEnabled(false);
            
       }
       public void preDisplay(){
          this.add(cbServicios);
            this.add(lbNPiezas);
            this.add(sNpiezas);
            this.add(lbAcero);
            this.add(cbAcero);
            this.add(lbpeso);
            this.add(txtPeso);
       }


          public void sumaKilos(Double kilos){
               //
          }
          public void sumaPieza(Integer piezas){
               //
          }
    
}
