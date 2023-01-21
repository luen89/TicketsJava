
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;

import javax.sound.sampled.SourceDataLine;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
//Action Event
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 * @author luen89
 */

public class PiezaForm extends JPanel implements ChangeListener {
     private JLabel lbNPiezas, lbAcero, lbpeso;
     private String Servicio[] = { "Selecciona Servicio", "Pavonado", "Templado", "Templado y Pavonado" };
     private String acero[] = { "Selecciona tipo", "4140", "8620", "1045", "Colled Rolled", "Q1", "D2" };
     private SpinnerModel smNpiezas;
     private JSpinner sNpiezas;
     private SpinnerModel smNkilos;
     private JSpinner sNKilos;
     private JComboBox cbServicios, cbAcero;
     private Elemento elemento;
     private JTextField txtPeso;

     //Pruebas
     private JButton btnX;
     private int id;
//     PanelEntrada panel= new PanelEntrada();

     
     public PiezaForm() {// Constructor
          super(new FlowLayout(FlowLayout.LEFT));
          // Crea Objeto Elemento
          elemento = new Elemento("", "", 0, 0.0);

          // Inicializa la ComboBox de Servicios
          cbServicios = new JComboBox(Servicio);
          cbServicios.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                    System.out.println("Entra al action del servicio");
                    elemento.setServicio(cbServicios.getSelectedItem().toString());
                    cbAcero.setSelectedItem(0);
                    switch (cbServicios.getSelectedIndex()) {
                         case 1:
                              cbAcero.setModel(new DefaultComboBoxModel<>(acero));
                              cbAcero.setEnabled(true);
                              break;
                         case 2:
                         case 3:
                              cbAcero.setModel(new DefaultComboBoxModel<>(
                                        Arrays.stream(acero).filter(x -> x != "Colled Rolled").toArray()));
                              cbAcero.setEnabled(true);
                              break;
                         default:
                              cbAcero.setEnabled(false);
                    }
               }
          });

          // Inicializa la ComboBox de Aceros
          cbAcero = new JComboBox(acero);
          cbAcero.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                    elemento.setAcero(cbAcero.getSelectedItem().toString());
               }
          });
          cbAcero.setEnabled(false);

          // Inicializa las etiquetas
          lbNPiezas = new JLabel("Numero de Piezas");
          lbAcero = new JLabel("       Acero");
          lbpeso = new JLabel("        Peso en Kg");

          // Inicializa el Spinner de Piezas
          smNpiezas = new SpinnerNumberModel(0, 0, 100, 1);
          sNpiezas = new JSpinner(smNpiezas);
          sNpiezas.addChangeListener(this);

          // Inicializa el Spinner de Kilos
          smNkilos = new SpinnerNumberModel(0.0, 0.0, 100.0, 0.1);
          sNKilos = new JSpinner(smNkilos);
          sNKilos.addChangeListener(this);

          btnX = new JButton("X");

     }

     public void preDisplay() {
          this.add(cbServicios);
          this.add(lbNPiezas);
          this.add(sNpiezas);
          this.add(lbAcero);
          this.add(cbAcero);
          this.add(lbpeso);
          this.add(sNKilos);

          this.add(btnX);
     }

     public void sumaKilos(Double kilos) {
          //
     }

     public void sumaPieza(Integer piezas) {
          //
     }

     @Override
     public void stateChanged(ChangeEvent e) {
          if (e.getSource() == sNpiezas) {
               elemento.setPiezas(Integer.parseInt(sNpiezas.getValue().toString()));
          }
          if (e.getSource() == sNKilos) {
               elemento.setKilos(Double.parseDouble(sNKilos.getValue().toString()));
          }
          System.out.println(elemento.getAcero());
          System.out.println(elemento.getPiezas());
          System.out.println(elemento.getKilos());
          System.out.println(elemento.getServicio());
     }

     public Elemento getElemento(){
          return elemento;
     }
     
     public void destroy(ArrayList<PiezaForm> lista){
          lista.remove(this.id);
     }
     public JButton getBotonEliminar(){
          return this.btnX;
     }

}
