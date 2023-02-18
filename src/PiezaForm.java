
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


/**
 *
 * @author luen89
 */

public class PiezaForm extends JPanel implements ChangeListener, FocusListener, ActionListener {
     private JLabel lbNPiezas, lbAcero, lbpeso, lbDureza, lbDescripcion, lbPrecioCustom;
     private JPanel panelPrincipal, panelSubServicios;
     private String namesServicio[];
     private String namesAcero[]; 
     private SpinnerModel smNpiezas;
     private JSpinner sNpiezas;
     private SpinnerModel smNkilos;
     private JSpinner sNKilos;
     private SpinnerModel smNDureza;
     private JSpinner sNDureza;
     private JSpinner sPrecioCustom;
     private SpinnerModel smPrecioCustom;
     private JComboBox<String> cbServicios, cbAcero;
     private Elemento elemento;
     private JTextField tfDureza, tfDesc;
     private ArrayList<PiezaForm> lista;
     private ArrayList<SubPiezaForm> subServicios = new ArrayList<SubPiezaForm>();
     ArrayList<Servicio> todosServicios;
     ArrayList<Acero> todosAceros;
     //Pruebas
     private JButton btnX;
     private JButton btnplus;
     private int id;
     private MatrizValidacion matrix;
//     PanelEntrada panel= new PanelEntrada();

     
     public PiezaForm(String[] namesServicio, String[] namesAcero, ArrayList<Servicio> todosServicios,ArrayList<Acero> todosAceros,MatrizValidacion matrix,ArrayList<PiezaForm> lista) {// Constructor
          super(new GridLayout(0,1));
          this.lista=lista;
          this.matrix=matrix;
          this.todosServicios=todosServicios;
          this.todosAceros=todosAceros;
          panelPrincipal = new JPanel(new FlowLayout(FlowLayout.LEFT));
          panelSubServicios = new JPanel(new GridLayout(0,1));
          this.namesServicio=namesServicio;
          this.namesAcero=namesAcero;
          // Crea Objeto Elemento
          elemento = new Elemento(todosAceros.get(0).name, todosServicios.get(0).name, 0, 0.0,"","",0);
          elemento.servArray.add(todosServicios.get(0));
          elemento.setAceroObject(todosAceros.get(0));
          // Inicializa la ComboBox de Servicios
          cbServicios = new JComboBox<>(namesServicio);
          cbServicios.addActionListener(this);
          //cbServicios.addActionListener(new ActionListener() {
               /*@Override
               /*public void actionPerformed(ActionEvent e) {
                    System.out.println("Entra al action del servicio");
                    elemento.setServicio(cbServicios.getSelectedItem().toString());
                    cbAcero.setSelectedItem(0);
                    switch (cbServicios.getSelectedIndex()) {
                         case 1:
                              cbAcero.setModel(new DefaultComboBoxModel<>(namesAcero));
                              cbAcero.setEnabled(true);
                              break;
                         case 2:
                         case 3:
                              //String[] filtrado = Arrays.stream(acero).filter(x -> x != "Colled Rolled").toArray();
                              //String[] arrayFiltrado = Arrays.stream(acero).filter();
                              cbAcero.setModel(new DefaultComboBoxModel<>( Arrays.stream(namesAcero).filter(x -> x != "Colled Rolled").toArray(String[]::new) ));
                              cbAcero.setEnabled(true);
                              break;
                         default:
                              cbAcero.setEnabled(false);
                    }
               }
          });*/

          // Inicializa la ComboBox de Aceros
          cbAcero = new JComboBox<>(namesAcero);
          cbAcero.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
               removerDeMatriz(elemento.servArray.get(0).id, elemento.getAceroObject().id);
               for (SubPiezaForm sub : subServicios){removerDeMatriz(elemento.servArray.get(subServicios.indexOf(sub)+1).id, elemento.getAceroObject().id);}

               elemento.setAcero(cbAcero.getSelectedItem().toString());
               elemento.setAceroObject(todosAceros.get(cbAcero.getSelectedIndex()));

               agregarAMatriz(elemento.servArray.get(0).id, elemento.getAceroObject().id);
               for (SubPiezaForm sub : subServicios){agregarAMatriz(elemento.servArray.get(subServicios.indexOf(sub)+1).id, elemento.getAceroObject().id);}
               
               matrix.printMatriz();
               calcularCostosLista();
               }
          });
          cbAcero.setEnabled(true);

          // Inicializa las etiquetas
          lbNPiezas = new JLabel("Numero de Piezas");
          lbAcero = new JLabel(" Acero");
          lbpeso = new JLabel(" Peso en Kg");
          lbDureza = new JLabel(" Dureza");
          lbDescripcion =  new JLabel(" Descripción");
          lbPrecioCustom =  new JLabel(" Costo");

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

          //Incializa el Spinner de Costo Personalizado
          smPrecioCustom = new SpinnerNumberModel(0.0,0.0,null,0.1);
          sPrecioCustom = new JSpinner(smPrecioCustom);
          sPrecioCustom.addChangeListener(this);

          //Inicializar el textField
          //dureza
          tfDureza = new JTextField(7);
          tfDureza.addFocusListener(this);
          //descripcion
          tfDesc = new JTextField(17);
          tfDesc.addFocusListener(this);

          btnplus = new JButton("+");
          btnplus.setBackground(new Color(0,0,255));
          btnplus.setForeground(new Color(255,255,255));
          btnplus.addActionListener(this);
          
          btnX = new JButton("X");
          btnX.setBackground(new Color(255,0,0));
          btnX.setForeground(new Color(255,255,255));

     }

     public void preDisplay() {
          panelPrincipal.add(cbServicios);
          panelPrincipal.add(lbNPiezas);
          panelPrincipal.add(sNpiezas);
          panelPrincipal.add(lbAcero);
          panelPrincipal.add(cbAcero);
          panelPrincipal.add(lbpeso);
          panelPrincipal.add(sNKilos);
          
          panelPrincipal.add(lbDureza);
          panelPrincipal.add(tfDureza);

          panelPrincipal.add(lbDescripcion);
          panelPrincipal.add(tfDesc);

          panelPrincipal.add(lbPrecioCustom);
          panelPrincipal.add(sPrecioCustom);

          panelPrincipal.add(btnplus);
          panelPrincipal.add(btnX);
          this.add(panelPrincipal);
          this.add(panelSubServicios);
     }

     public void sumaKilos(Double kilos) {
          //
     }

     public void sumaPieza(Integer piezas) {
          //
     }

     public void removerDeMatriz(int indiceServicio, int indiceAcero){
          matrix.matriz[indiceServicio][indiceAcero]=  matrix.matriz[indiceServicio][indiceAcero]-elemento.getKilos();
     }

     public void removerTodoDeMatriz(){
          System.out.println("Entre a borrar todo de la matriz de validacion");
          removerDeMatriz(elemento.servArray.get(0).id, elemento.getAceroObject().id);
          for (SubPiezaForm sub : subServicios){removerDeMatriz(elemento.servArray.get(subServicios.indexOf(sub)+1).id, elemento.getAceroObject().id);}
     }

     protected void agregarAMatriz(int indiceServicio, int indiceAcero){
          matrix.matriz[indiceServicio][indiceAcero]=  matrix.matriz[indiceServicio][indiceAcero]+elemento.getKilos();
     }

     public void calcularCostos(){
               double costo=elemento.servArray.get(0).obtenerCosto( matrix.matriz[elemento.servArray.get(0).id][elemento.getAceroObject().id],elemento.getKilos());
               sPrecioCustom.setValue(costo);
               elemento.setPrecioCustom(costo, 0);
               for (SubPiezaForm sub : subServicios){
                    int index=subServicios.indexOf(sub)+1;
                    double subcosto=elemento.servArray.get(index).obtenerCosto( matrix.matriz[elemento.servArray.get(index).id][elemento.getAceroObject().id],elemento.getKilos());
                    sub.sPrecioCustom.setValue(subcosto);
                    elemento.setPrecioCustom(subcosto, index);
               }
     }

     public void calcularCostosLista(){
          for(PiezaForm p : lista){
               p.calcularCostos();
          }
     }

     @Override
     public void stateChanged(ChangeEvent e) {
          if (e.getSource() == sNpiezas) {
               elemento.setPiezas(Integer.parseInt(sNpiezas.getValue().toString()));
          }
          if (e.getSource() == sNKilos) {
               removerDeMatriz(elemento.servArray.get(0).id, elemento.getAceroObject().id);
               for (SubPiezaForm sub : subServicios){removerDeMatriz(elemento.servArray.get(subServicios.indexOf(sub)+1).id, elemento.getAceroObject().id);}

               elemento.setKilos(Double.parseDouble(sNKilos.getValue().toString()));

               agregarAMatriz(elemento.servArray.get(0).id, elemento.getAceroObject().id);
               for (SubPiezaForm sub : subServicios){agregarAMatriz(elemento.servArray.get(subServicios.indexOf(sub)+1).id, elemento.getAceroObject().id);}
               
               matrix.printMatriz();
               calcularCostosLista();
          }

          if(e.getSource() == sPrecioCustom){
               elemento.setPrecioCustom(Double.parseDouble(sPrecioCustom.getValue().toString()),0);
          }

          //elemento.setDureza(tfDureza.getText());
          //elemento.setDescripcion(tfDesc.getText());

          System.out.println("Acero: "+elemento.getAcero());
          System.out.println("Piezas: "+elemento.getPiezas());
          System.out.println("Kilos: "+elemento.getKilos());
          System.out.println("Servicio: "+elemento.getServicio());

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

     @Override
     public void actionPerformed(ActionEvent e) {
          if (e.getSource() == btnplus){
               SubPiezaForm subpieza = new SubPiezaForm(namesServicio, elemento);
               elemento.servArray.add(todosServicios.get(0));
               elemento.addPrecioCustom();
               subServicios.add(subpieza);

               matrix.matriz[elemento.servArray.get(subServicios.indexOf(subpieza)+1).id][elemento.getAceroObject().id]=matrix.matriz[elemento.servArray.get(subServicios.indexOf(subpieza)+1).id][elemento.getAceroObject().id]+elemento.getKilos();
               calcularCostosLista();
               subpieza.getBotonEliminar().addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    matrix.matriz[elemento.servArray.get(subServicios.indexOf(subpieza)+1).id][elemento.getAceroObject().id]=matrix.matriz[elemento.servArray.get(subServicios.indexOf(subpieza)+1).id][elemento.getAceroObject().id]-elemento.getKilos();
                    elemento.servArray.remove(subServicios.indexOf(subpieza)+1);
                    elemento.removePrecioCustom(subServicios.indexOf(subpieza)+1);  
                    subServicios.remove(subpieza);
                    panelSubServicios.remove(subpieza);
                    updateUI();
                    calcularCostosLista();
                    
                }
               });
               subpieza.getCBservicios().addActionListener( new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                         int index = subServicios.indexOf(subpieza);
                         matrix.matriz[elemento.servArray.get(subServicios.indexOf(subpieza)+1).id][elemento.getAceroObject().id]=matrix.matriz[elemento.servArray.get(subServicios.indexOf(subpieza)+1).id][elemento.getAceroObject().id]-elemento.getKilos();
                         elemento.servArray.set(index+1, todosServicios.get(subpieza.getCBservicios().getSelectedIndex()));
                         matrix.matriz[elemento.servArray.get(subServicios.indexOf(subpieza)+1).id][elemento.getAceroObject().id]=matrix.matriz[elemento.servArray.get(subServicios.indexOf(subpieza)+1).id][elemento.getAceroObject().id]+elemento.getKilos();
                         subpieza.sPrecioCustom.setValue(elemento.servArray.get(index+1).obtenerCosto( matrix.matriz[elemento.servArray.get(subServicios.indexOf(subpieza)+1).id][elemento.getAceroObject().id],elemento.getKilos()));
                         matrix.printMatriz();
                         calcularCostosLista();
                    }   
               });

               subpieza.sPrecioCustom.addChangeListener(new ChangeListener(){
                    @Override
                    public void stateChanged(ChangeEvent e) {
                         elemento.setPrecioCustom(Double.parseDouble(sPrecioCustom.getValue().toString()),subServicios.indexOf(subpieza)+1);   
                    }});

               panelSubServicios.add(subpieza);
               this.updateUI();
          }
          if(e.getSource() == cbServicios){
               matrix.matriz[elemento.servArray.get(0).id][elemento.getAceroObject().id]=matrix.matriz[elemento.servArray.get(0).id][elemento.getAceroObject().id]-elemento.getKilos();
               elemento.servArray.set(0,todosServicios.get(cbServicios.getSelectedIndex()));
               elemento.setServicio(elemento.servArray.get(0).name);
               matrix.matriz[elemento.servArray.get(0).id][elemento.getAceroObject().id]=matrix.matriz[elemento.servArray.get(0).id][elemento.getAceroObject().id]+elemento.getKilos();
               matrix.printMatriz();
               sPrecioCustom.setValue(elemento.servArray.get(0).obtenerCosto( matrix.matriz[elemento.servArray.get(0).id][elemento.getAceroObject().id],elemento.getKilos()));
               calcularCostosLista();
          }
          
     }

}
