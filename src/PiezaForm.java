
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
     PanelEntrada padre;

     //Etiquetas
     private JLabel lbNPiezas, lbAcero, lbpeso, lbDureza, lbDescripcion, lbPrecioCustom;
     //Paneles
     private JPanel panelPrincipal;
     public JPanel panelSubServicios;
     //Array de String del contenido de los Combobox
     
     //Spinners de Numero de Piezas,Kilos y Precio
     private SpinnerModel smNpiezas;
     private SpinnerModel smPrecioCustom;
     private SpinnerModel smNkilos;

     private JSpinner sNpiezas;
     private JSpinner sNKilos;
     private JSpinner sPrecioCustom;
     
     //ComboBox
     private JComboBox<String> cbServicios, cbAcero;

     public Elemento elemento;
     private JTextField tfDureza, tfDesc;
     //private ArrayList<PiezaForm> lista;
     public ArrayList<SubPiezaForm> subpiezasArray = new ArrayList<SubPiezaForm>();
     //Pruebas
     private JButton btnX;
     private JButton btnplus;
//     PanelEntrada panel= new PanelEntrada();

     
     public PiezaForm(PanelEntrada padre) {// Constructor
          super(new GridLayout(0,1));
          this.padre=padre;
          panelPrincipal = new JPanel(new FlowLayout(FlowLayout.LEFT));
          panelSubServicios = new JPanel(new GridLayout(0,1));


          // Crea Objeto Elemento
          elemento=padre.control.createElemento();
          /*elemento = new Elemento(padre.control,padre.control.getAceroFromGeneralArray(0).name, padre.control.getServicioFromGeneralArray(0).name, 0, 0.0,"","",0);
          elemento.servicioObjeto=padre.control.getServicioFromGeneralArray(0);
          elemento.setAceroObject(padre.control.getAceroFromGeneralArray(0));*/

          // Inicializa la ComboBox de Servicios
          cbServicios = new JComboBox<>(padre.control.getServiciosNames());
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
          cbAcero = new JComboBox<>(padre.control.getAcerosNames());
          cbAcero.addActionListener(this);
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

    

          //Incializa el Spinner de Costo Personalizado
          smPrecioCustom = new SpinnerNumberModel(0.0,0.0,null,0.1);
          sPrecioCustom = new JSpinner(smPrecioCustom);
          sPrecioCustom.setEnabled(false);
          sPrecioCustom.addChangeListener(this);

          //Inicializar el textField
          //dureza
          tfDureza = new JTextField(7);
          tfDureza.addFocusListener(this);
          //descripcion
          tfDesc = new JTextField(17);
          tfDesc.addFocusListener(this);

          //Boton de Agregar Servicio Extra
          btnplus = new JButton("+");
          btnplus.setBackground(new Color(0,0,255));
          btnplus.setForeground(new Color(255,255,255));
          btnplus.addActionListener(this);
          
          //Boton Eliminar
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



     public double obtenerCostos(){
               double total=elemento.calcularCosto();
               sPrecioCustom.setValue(total);
               for (SubPiezaForm sub : subpiezasArray){total+=sub.obtenerCosto();}
               return total;
     }

     public void calcularCostosLista(){
          double total=0.0;
          for(PiezaForm p : padre.itemsPiezasArray){
               total+=p.obtenerCostos();
          }
          padre.control.setTotal(total);
          padre.jspPrecioCustom.setValue(total);
     }

     public void setEnablePrecioCustoms(boolean flag){
          this.sPrecioCustom.setEnabled(flag);
          for(SubPiezaForm s : subpiezasArray)
               s.sPrecioCustom.setEnabled(flag);

     }

     @Override
     public void stateChanged(ChangeEvent e) {
          if (e.getSource() == sNpiezas) {elemento.setPiezas(Integer.parseInt(sNpiezas.getValue().toString()));}

          if (e.getSource() == sNKilos) {
               elemento.removerTodoDeMatriz();
               elemento.setKilos(Double.parseDouble(sNKilos.getValue().toString()));
               elemento.agregarTodoAMatriz();
               padre.control.matrix.printMatriz();
               if(padre.control.getAutoCalculo()){calcularCostosLista();}
          }

          if(e.getSource() == sPrecioCustom){elemento.setPrecioCustom(Double.parseDouble(sPrecioCustom.getValue().toString()));}

          System.out.println("Acero: "+elemento.getAcero());
          System.out.println("Piezas: "+elemento.getPiezas());
          System.out.println("Kilos: "+elemento.getKilos());
          System.out.println("Servicio: "+elemento.getServicio());
     }

     public Elemento getElemento(){return elemento;}

     @Override
     public void focusGained(FocusEvent e) { /* TODO Auto-generated method stub*/ }

     @Override
     public void focusLost(FocusEvent e) {
          elemento.setDureza(tfDureza.getText());
          System.out.println(elemento.getDureza());

          elemento.setDescripcion(tfDesc.getText());
          System.out.println(elemento.getDescripcion());
     }

     @Override
     public void actionPerformed(ActionEvent e) {
          
          if (e.getSource() == btnX){
               System.out.println("Voy a eliminar la pieza");
                    elemento.removerTodoDeMatriz();
                    padre.control.removeElemento(elemento);
                    System.out.println("Borre los datos de la Matriz");
                    calcularCostosLista();
                    
                    padre.subpanelListaOrdenes.remove(this);
                    padre.itemsPiezasArray.remove(this);
                    padre.refreshDisplay();
          }

          if (e.getSource() == btnplus){
               SubElemento subelemento = new SubElemento(elemento, padre.control.getServicioFromGeneralArray(0),0.0);
               SubPiezaForm subpieza = new SubPiezaForm(this, padre.control.getServiciosNames(), subelemento);
               elemento.subelementoArray.add(subelemento);
               subpiezasArray.add(subpieza);

               subelemento.agregarAMatriz();
               if(padre.control.getAutoCalculo()){calcularCostosLista();}
               panelSubServicios.add(subpieza);
               this.updateUI();
          }
          if(e.getSource() == cbServicios){
               elemento.removerDeMatriz();
               elemento.servicioObjeto=padre.control.getServicioFromGeneralArray(cbServicios.getSelectedIndex());
               elemento.setServicio(elemento.servicioObjeto.name);
               elemento.agregarAMatriz();
               padre.control.matrix.printMatriz();
               if(padre.control.getAutoCalculo()){calcularCostosLista();}
          }

          if(e.getSource()== cbAcero){
               elemento.removerTodoDeMatriz();

               elemento.setAcero(cbAcero.getSelectedItem().toString());
               elemento.setAceroObject(padre.control.getAceroFromGeneralArray(cbAcero.getSelectedIndex()));

               elemento.agregarTodoAMatriz();

               padre.control.matrix.printMatriz();
               if(padre.control.getAutoCalculo()){calcularCostosLista();}
          }
          
     }

}
