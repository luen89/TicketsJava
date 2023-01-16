/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;
import javax.swing.*;

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
           smNpiezas = new SpinnerNumberModel(0,0,100,1);
            sNpiezas = new JSpinner(smNpiezas);
            lbNPiezas = new JLabel("Numero de Piezas");
            lbAcero= new JLabel("       Acero");
            lbpeso= new JLabel("        Peso en Kg");
            cbAcero = new JComboBox(acero);
            txtPeso = new JTextField(" ", 8);
            
            
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
