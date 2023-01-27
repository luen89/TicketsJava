
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.util.ArrayList;
import java.util.Arrays;


/**
 *
 * @author luen89
 */

public class PiezaForm extends JPanel implements ChangeListener, FocusListener {
     private JLabel lbNPiezas, lbAcero, lbpeso, lbDureza, lbDescripcion;
     private String Servicio[] = { "Selecciona Servicio", "Pavonado", "Templado", "Templado y Pavonado" };
     private String acero[] = { "Selecciona tipo", "4140", "8620", "1045", "Colled Rolled", "Q1", "D2" };
     private SpinnerModel smNpiezas;
     private JSpinner sNpiezas;
     private SpinnerModel smNkilos;
     private JSpinner sNKilos;
     private SpinnerModel smNDureza;
     private JSpinner sNDureza;
     private JComboBox<String> cbServicios, cbAcero;
     private Elemento elemento;
     private JTextField tfDureza, tfDesc;

     //Pruebas
     private JButton btnX;
     private int id;
//     PanelEntrada panel= new PanelEntrada();

     
     public PiezaForm() {// Constructor
          super(new FlowLayout(FlowLayout.LEFT));
          // Crea Objeto Elemento
          elemento = new Elemento("", "", 0, 0.0,"","");

          // Inicializa la ComboBox de Servicios
          cbServicios = new JComboBox<>(Servicio);
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
                              //String[] filtrado = Arrays.stream(acero).filter(x -> x != "Colled Rolled").toArray();
                              //String[] arrayFiltrado = Arrays.stream(acero).filter();
                              cbAcero.setModel(new DefaultComboBoxModel<>( Arrays.stream(acero).filter(x -> x != "Colled Rolled").toArray(String[]::new) ));
                              cbAcero.setEnabled(true);
                              break;
                         default:
                              cbAcero.setEnabled(false);
                    }
               }
          });

          // Inicializa la ComboBox de Aceros
          cbAcero = new JComboBox<>(acero);
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
          lbDureza = new JLabel(" Dureza");
          lbDescripcion =  new JLabel(" Descripción");

          // Inicializa el Spinner de Piezas
          smNpiezas = new SpinnerNumberModel(0, 0, 100, 1);
          sNpiezas = new JSpinner(smNpiezas);
          sNpiezas.addChangeListener(this);

          // Inicializa el Spinner de Kilos
          smNkilos = new SpinnerNumberModel(0.0, 0.0, 100.0, 0.1);
          sNKilos = new JSpinner(smNkilos);
          sNKilos.addChangeListener(this);

          //Inicializa el Spinner de Dureza
          smNDureza = new SpinnerNumberModel(0.0,0.0,100.0,0.1);
          sNDureza = new JSpinner(smNDureza);
          sNDureza.addChangeListener(this);

          //Inicializar el textField
          //dureza
          tfDureza = new JTextField(7);
          tfDureza.addFocusListener(this);
          //descripcion
          tfDesc = new JTextField(7);
          tfDesc.addFocusListener(this);

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
          
          this.add(lbDureza);
          this.add(tfDureza);

          this.add(lbDescripcion);
          this.add(tfDesc);

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

          //elemento.setDureza(tfDureza.getText());
          //elemento.setDescripcion(tfDesc.getText());

          System.out.println(elemento.getAcero());
          System.out.println(elemento.getPiezas());
          System.out.println(elemento.getKilos());
          System.out.println(elemento.getServicio());

          //System.out.println(elemento.getDureza());
          //System.out.println(elemento.getDescripcion());
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

     @Override
     public void focusGained(FocusEvent e) {
          // TODO Auto-generated method stub
          
     }

     @Override
     public void focusLost(FocusEvent e) {
          // TODO Auto-generated method stub

          elemento.setDureza(tfDureza.getText());
          System.out.println(elemento.getDureza());

          elemento.setDescripcion(tfDesc.getText());
          System.out.println(elemento.getDescripcion());
     }

}
