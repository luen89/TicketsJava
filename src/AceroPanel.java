
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.Array;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Luis Enrique Pérez González
 */
public class AceroPanel extends JPanel implements ActionListener, FocusListener{

    JTable tabla;
    Ticket ticket;
    JPanel panel, panelIdentificador, panelValores, panelEdicion;
    JComboBox<String> jcAcciones;

    JTextField jtid, jtNameAbr ,jtName;
    JLabel jlid, jlNameAbrv ,jlName;
    JButton jbAction;
    JScrollPane scroll;
    GestorArchivos fileGestor;
    Ventana padre;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public AceroPanel(GestorArchivos fileGestor, Ventana padre){
        this.padre=padre;
        this.fileGestor = fileGestor;
        this.setLayout(new GridLayout(0,1));
        initComponents();
      
    }

    private void initComponents() {
        String [] columnNames ={"Identificador","id grupo", "Nombre","Nombre Abrev"};
        Object[][] data = new Object[padre.arrayAceros.size()][4];
        int i=0;
        System.out.println("Tamaño Arreglo: "+padre.arrayAceros.size());
        DefaultTableModel model = new DefaultTableModel();
        tabla = new JTable(model);

        for(i=0;i<columnNames.length;i++){
            model.addColumn(columnNames[i]);
        }
        
        
        for(i=0;i<padre.arrayAceros.size();i++){
            System.out.println("Indice: "+i);
            model.addRow(new Object[]{
                i,
                padre.arrayAceros.get(i).id,
                padre.arrayAceros.get(i).name,
                padre.arrayAceros.get(i).nameAbrv,
            });
            /*data[i][0]=i;
            data[i][1]=padre.arrayServicios.get(i).id;
            data[i][2]=padre.arrayServicios.get(i).name;
            data[i][3]=padre.arrayServicios.get(i).costoMin;
            data[i][4]=padre.arrayServicios.get(i).costoMed;
            data[i][5]=padre.arrayServicios.get(i).costoKg;
            data[i][6]=padre.arrayServicios.get(i).limiteMinimo;
            data[i][7]=padre.arrayServicios.get(i).limiteMedio;
            */
        }
        String[] accionesString={"Agregar","Editar","Borrar"};
        jcAcciones= new JComboBox<String>(accionesString);
        jcAcciones.addActionListener(this);
        jlid= new JLabel(columnNames[0]);
        jlName= new JLabel(columnNames[2]);
        jlNameAbrv= new JLabel(columnNames[3]);

        jtid= new JTextField(5);
        jtName= new JTextField(15);
       
        jtNameAbr= new JTextField(5);


        jbAction = new JButton("Guardar Cambios");
        jbAction.addActionListener(this);
        jlid.setVisible(false);
        jtid.setVisible(false);
        jtid.addFocusListener(this);


        panelIdentificador= new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelIdentificador.add(jlid);
        panelIdentificador.add(jtid);
        panelIdentificador.add(jcAcciones);
        panelIdentificador.add(jbAction);

        panelValores = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelValores.add(jlName);
        panelValores.add(jtName);
        panelValores.add(jlNameAbrv);
        panelValores.add(jtNameAbr);
        panelValores.setVisible(true);

        panelEdicion= new JPanel(new GridLayout(0,1));
        panelEdicion.add(panelIdentificador);
        panelEdicion.add(panelValores);


        tabla.setEnabled(false);
        scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(800,400));
        tabla.setFillsViewportHeight(true);
        resizeColumnWidth(tabla);
        panel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(scroll);
        this.add(panel);
        this.add(panelEdicion);

    }

    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 40; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            if(width > 300)
                width=300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==jcAcciones){
            if(jcAcciones.getSelectedItem()=="Borrar"){panelValores.setVisible(false);}
            else{panelValores.setVisible(true);}
            
            if(jcAcciones.getSelectedItem()=="Agregar"){
                jtid.setVisible(false);
                jlid.setVisible(false);
                
            }
            else{
                jlid.setVisible(true);
                jtid.setVisible(true);
            }
        }

        if(e.getSource()==jbAction){
            if(jcAcciones.getSelectedItem()=="Agregar"){
                int index=padre.arrayAceros.size();
                padre.arrayAceros.add(new Acero(
                    index,
                    jtName.getText(),
                    jtNameAbr.getText()
                    ));
                

                DefaultTableModel modelaux= (DefaultTableModel) tabla.getModel();
                modelaux.addRow(padre.arrayAceros.get(padre.arrayAceros.size()-1).getData(padre.arrayAceros.size()-1));
                padre.restartPanelEntrada();
            }    


            if(jcAcciones.getSelectedItem()=="Editar"){
                int index= Integer.parseInt(jtid.getText());
                Acero acer=padre.arrayAceros.get(index);
                acer.name=jtName.getText();
                acer.nameAbrv=jtNameAbr.getText();
                
                DefaultTableModel modelaux= (DefaultTableModel) tabla.getModel();
                modelaux.removeRow(index);
                modelaux.insertRow(index, padre.arrayAceros.get(index).getData(index));
                padre.restartPanelEntrada();
            }

        
            if(jcAcciones.getSelectedItem()=="Borrar"){
                int index= Integer.parseInt(jtid.getText());
                padre.arrayAceros.remove(index);

                DefaultTableModel modelaux= (DefaultTableModel) tabla.getModel();
                if(padre.arrayAceros.size()<=1){return;}
                
                for(int i=index;i<padre.arrayAceros.size()+1;i++){
                    System.out.println("Indice de Borrado: "+i);
                    modelaux.removeRow(index);
                }

                for(int i=index;i<padre.arrayAceros.size();i++){
                System.out.println("Indice de Agregado: "+i);
                Acero aux = padre.arrayAceros.get(i);
                aux.id=i;
                modelaux.addRow(new Object[]{                
                    i,
                    aux.id,
                    aux.name,
                    aux.nameAbrv,
                });
                }
                padre.restartPanelEntrada();
            }

            fileGestor.escribirAceros(padre.arrayAceros);
            

        }


        
    }

    @Override
    public void focusGained(FocusEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'focusGained'");
    }

    @Override
    public void focusLost(FocusEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource()==jtid){
            if(jcAcciones.getSelectedItem()=="Editar"){
                int index= Integer.parseInt(jtid.getText());
                jtName.setText(padre.arrayAceros.get(index).name);
                jtNameAbr.setText(padre.arrayAceros.get(index).nameAbrv);
            }
        }
    }
    
    
}
