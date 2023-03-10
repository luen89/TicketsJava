
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class ServicioPanel extends JPanel implements ActionListener{

    JTable tabla;
    Ticket ticket;
    JPanel panel, panelEdicion, panelValores;
    JComboBox<String> jcAcciones;

    JTextField jtid, jtGroup ,jtName, jtCostoMin, jtCostoMed, jtCostoKg, jtLimiteMin, jtLimiteMed;
    JLabel jlid, jlGroup ,jlName, jlCostoMin, jlCostoMed, jlCostoKg, jlLimiteMin, jlLimiteMed;
    JButton jbAction;
    JScrollPane scroll;
    GestorArchivos fileGestor;
    Ventana padre;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public ServicioPanel(GestorArchivos fileGestor, Ventana padre){
        this.padre=padre;
        this.fileGestor = fileGestor;
        this.setLayout(new GridLayout(0,1));
        initComponents();
      
    }

    private void initComponents() {
        String [] columnNames ={"Identificador","id grupo", "Nombre","Nombre Abrev","Costo Minimo","Costo Medio","Costo Kg","Limite Minimo","Limite Medio"};
        Object[][] data = new Object[padre.arrayServicios.size()][9];
        int i=0;
        System.out.println("Tamaño Arreglo: "+padre.arrayServicios.size());
        DefaultTableModel model = new DefaultTableModel();
        tabla = new JTable(model);

        for(i=0;i<columnNames.length;i++){
            model.addColumn(columnNames[i]);
        }
        
        
        for(i=0;i<padre.arrayServicios.size();i++){
            System.out.println("Indice: "+i);
            model.addRow(new Object[]{
                i,
                padre.arrayServicios.get(i).id,
                padre.arrayServicios.get(i).name,
                padre.arrayServicios.get(i).nameAbr,
                padre.arrayServicios.get(i).costoMin,
                padre.arrayServicios.get(i).costoMed,
                padre.arrayServicios.get(i).costoKg,
                padre.arrayServicios.get(i).limiteMinimo,
                padre.arrayServicios.get(i).limiteMedio
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
        jlGroup= new JLabel(columnNames[1]);
        jlName= new JLabel(columnNames[2]);
        jlCostoMin= new JLabel(columnNames[3]);
        jlCostoMed= new JLabel(columnNames[4]); 
        jlCostoKg= new JLabel(columnNames[5]); 
        jlLimiteMin= new JLabel(columnNames[6]); 
        jlLimiteMed= new JLabel(columnNames[7]);

        jtid= new JTextField(5);        
        jtGroup= new JTextField(5);
        jtName= new JTextField(5);
        jtCostoMin= new JTextField(5);
        jtCostoMed= new JTextField(5);
        jtCostoKg= new JTextField(5);
        jtLimiteMin= new JTextField(5);
        jtLimiteMed= new JTextField(5);

        jbAction = new JButton("Agregar");
        jbAction.addActionListener(this);
        jlid.setVisible(false);
        jtid.setVisible(false);



        panelEdicion= new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelEdicion.add(jlid);
        panelEdicion.add(jtid);
        panelEdicion.add(jcAcciones);
        panelEdicion.add(jbAction);

        panelValores = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelValores.add(jlGroup);
        panelValores.add(jtGroup);
        panelValores.add(jlName);
        panelValores.add(jtName);
        panelValores.add(jlCostoMin);
        panelValores.add(jtCostoMin);
        panelValores.add(jlCostoMed);
        panelValores.add(jtCostoMed);
        panelValores.add(jlCostoKg);
        panelValores.add(jtCostoKg);
        panelValores.add(jlLimiteMin);
        panelValores.add(jtLimiteMin);
        panelValores.add(jlLimiteMed);
        panelValores.add(jtLimiteMed);
        panelValores.setVisible(true);


        tabla.setEnabled(false);
        scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(800,400));
        tabla.setFillsViewportHeight(true);
        resizeColumnWidth(tabla);
        panel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(scroll);
        this.add(panel);
        this.add(panelEdicion);
        this.add(panelValores);

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

                padre.arrayServicios.add(new Servicio(
                    Integer.parseInt(jtGroup.getText()),
                    jtName.getText(),
                    "NEW",
                    Double.parseDouble(jtCostoMin.getText()) ,
                    Double.parseDouble(jtCostoMed.getText()) ,
                    Double.parseDouble(jtCostoKg.getText()), 
                    Double.parseDouble(jtLimiteMin.getText()),
                    Double.parseDouble(jtLimiteMed.getText())
                    ));
                

                DefaultTableModel modelaux= (DefaultTableModel) tabla.getModel();
                modelaux.addRow(padre.arrayServicios.get(padre.arrayServicios.size()-1).getData(padre.arrayServicios.size()-1));
                padre.restartPanelEntrada();

            }    

            if(jcAcciones.getSelectedItem()=="Editar"){

            }
            if(jcAcciones.getSelectedItem()=="Eliminar"){

            }
        }


        
    }
    
    
}
