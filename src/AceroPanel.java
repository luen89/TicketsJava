
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.Array;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Luis Enrique Pérez González
 */
public class AceroPanel extends JPanel implements ActionListener, ChangeListener{

    JTable tabla;
    Ticket ticket;
    JPanel panel, panelIdentificador, panelNombre, panelEdicion;
    JComboBox<String> jcAcciones;

    private SpinnerNumberModel smIdentificador;
    private JSpinner sIdentificador;
    JTextField jtName, jtNameAbr;
    JLabel jlid, jlNameAbrv ,jlName,jlAccion, jlDataAcero;
    JButton jbAction;
    JScrollPane scroll;
    GestorArchivos fileGestor;
    Ventana padre;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public AceroPanel(GestorArchivos fileGestor, Ventana padre){
        this.padre=padre;
        this.fileGestor = fileGestor;
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        initComponents();
      
    }


    private void initComponents() {
        String [] columnNames ={"Identificador","id grupo", "Nombre","Nombre Abreviado"};
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

       // jtid= new JTextField(5);
        jtName= new JTextField(15);
       
        jtNameAbr= new JTextField(5);

        jlAccion = new JLabel("Accion a realizar: ");
        jbAction = new JButton("Guardar Cambios");
        jbAction.addActionListener(this);
        jlid.setVisible(false);
        smIdentificador = new SpinnerNumberModel(0, 0, padre.arrayAceros.size()-1, 1);
        sIdentificador = new JSpinner(smIdentificador);
        sIdentificador.setVisible(false);
        sIdentificador.addChangeListener(this);
        //jtid.setVisible(false);
        //jtid.addFocusListener(this);


        panelIdentificador= new JPanel(new GridLayout(0,2));
        panelIdentificador.add(jlAccion);
        panelIdentificador.add(jcAcciones);
        panelIdentificador.add(jlid);
        panelIdentificador.add(sIdentificador);
        panelIdentificador.add(jbAction);
        

        panelNombre = new JPanel(new GridLayout(0,2));

        panelNombre.add(jlName);
        panelNombre.add(jtName);
        panelNombre.add(jlNameAbrv);
        panelNombre.add(jtNameAbr);
        panelNombre.setVisible(true);

        Font a = new Font("Calibri", 1, 14);

        Border bordeIdentificador = new TitledBorder(new EtchedBorder(Color.blue, Color.blue), "Control", 1, 2, a,
        Color.black);
        panelIdentificador.setBorder(bordeIdentificador);

        Border bordeNombre = new TitledBorder(new EtchedBorder(Color.orange, Color.orange), "General", 1, 2, a,
        Color.black);
        panelNombre.setBorder(bordeNombre);

        Font b = new Font("Calibri", 1, 22);

        jlDataAcero= new JLabel("Datos del Acero");
        jlDataAcero.setFont(b);
        

        panelEdicion= new JPanel(new GridLayout(0,1));
        panelEdicion.add(panelIdentificador);
        panelEdicion.add(jlDataAcero);
        panelEdicion.add(panelNombre);
        

        tabla.setEnabled(false);
        scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(800,400));
        tabla.setFillsViewportHeight(true);
        //resizeColumnWidth(tabla);
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
            if(jcAcciones.getSelectedItem()=="Borrar"){panelNombre.setVisible(false);}
            else{panelNombre.setVisible(true);}
            
            if(jcAcciones.getSelectedItem()=="Agregar"){
                sIdentificador.setVisible(false);
                jlid.setVisible(false);
                
            }
            else{
                jlid.setVisible(true);
                sIdentificador.setVisible(true);
            }
        }

        if(e.getSource()==jbAction){
        try{
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
                smIdentificador.setMaximum(padre.arrayAceros.size()-1);
                JOptionPane.showMessageDialog(this, "Acero Agregado Correctamente");
            }    


            if(jcAcciones.getSelectedItem()=="Editar"){
                int index= Integer.parseInt(sIdentificador.getValue().toString());
                Acero acer=padre.arrayAceros.get(index);
                acer.name=jtName.getText();
                acer.nameAbrv=jtNameAbr.getText();
                
                DefaultTableModel modelaux= (DefaultTableModel) tabla.getModel();
                modelaux.removeRow(index);
                modelaux.insertRow(index, padre.arrayAceros.get(index).getData(index));
                padre.restartPanelEntrada();
                JOptionPane.showMessageDialog(this, "Acero Actualizado Correctamente");
            }

        
            if(jcAcciones.getSelectedItem()=="Borrar"){
                int index= Integer.parseInt(sIdentificador.getValue().toString());
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
                smIdentificador.setMaximum(padre.arrayAceros.size()-1);
                padre.restartPanelEntrada();
                JOptionPane.showMessageDialog(this, "Acero Borrado Correctamente");

            }

            fileGestor.escribirAceros(padre.arrayAceros);
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Ocurrio un error");
        }

        }


        
    }


    @Override
    public void stateChanged(ChangeEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource()==sIdentificador){
            if(jcAcciones.getSelectedItem()=="Editar"){
                int index= Integer.parseInt(sIdentificador.getValue().toString());
                jtName.setText(padre.arrayAceros.get(index).name);
                jtNameAbr.setText(padre.arrayAceros.get(index).nameAbrv);
            }
        }
    }
    
    
}
