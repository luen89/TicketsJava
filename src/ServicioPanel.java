
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.Array;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Luis Enrique Pérez González
 */
public class ServicioPanel extends JPanel implements ActionListener, FocusListener{

    JTable tabla;
    Ticket ticket;
    JPanel panel, panelIdentificador, panelValores, panelEdicion, panelLimites, panelCostos, panelNombre;
    JComboBox<String> jcAcciones;

    JTextField jtid, jtNameAbr ,jtName, jtCostoMin, jtCostoMed, jtCostoKg, jtLimiteMin, jtLimiteMed;
    JLabel jlid, jlNameAbrv ,jlName, jlCostoMin, jlCostoMed, jlCostoKg, jlLimiteMin, jlLimiteMed, jlAccion;
    JButton jbAction;
    JScrollPane scroll;
    GestorArchivos fileGestor;
    Ventana padre;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public ServicioPanel(GestorArchivos fileGestor, Ventana padre){
        this.padre=padre;
        this.fileGestor = fileGestor;
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        initComponents();
      
    }

    private void initComponents() {
        String [] columnNames ={"Identificador","id grupo", "Nombre","Nombre Abreviado","Costo Minimo","Costo Medio","Costo Kg","Limite Minimo","Limite Medio"};
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
        jlAccion = new JLabel("Accion a realizar: ");
        jcAcciones= new JComboBox<String>(accionesString);
        jcAcciones.addActionListener(this);
        jlid= new JLabel(columnNames[0]);
        jlName= new JLabel(columnNames[2]);
        jlNameAbrv= new JLabel(columnNames[3]);
        jlCostoMin= new JLabel("Minimo");
        jlCostoMed= new JLabel("Medio"); 
        jlCostoKg= new JLabel("Por Kilogramo"); 
        jlLimiteMin= new JLabel("Se cobra minimo si peso menor a :"); 
        jlLimiteMed= new JLabel("Se cobra medio si peso menor a :");

        jtid= new JTextField(5);        
        jtNameAbr= new JTextField(5);
        jtName= new JTextField(15);
        jtCostoMin= new JTextField(5);
        jtCostoMed= new JTextField(5);
        jtCostoKg= new JTextField(5);
        jtLimiteMin= new JTextField(5);
        jtLimiteMed= new JTextField(5);

        jbAction = new JButton("Guardar Cambios");
        jbAction.addActionListener(this);
        
        jlid.setVisible(false);
        jtid.setVisible(false);
        jtid.addFocusListener(this);


        panelIdentificador= new JPanel(new GridLayout(0,2));
        panelIdentificador.add(jlAccion);
        panelIdentificador.add(jcAcciones);
        panelIdentificador.add(jlid);
        panelIdentificador.add(jtid);
        panelIdentificador.add(jbAction);

        panelValores = new JPanel(new GridLayout(0,1));
        panelNombre = new JPanel(new GridLayout(0,2));
        panelCostos  = new JPanel(new GridLayout(0,2));
        panelLimites = new JPanel(new GridLayout(0,2));

        panelNombre.add(jlName);
        panelNombre.add(jtName);
        panelNombre.add(jlNameAbrv);
        panelNombre.add(jtNameAbr);

        panelCostos.add(jlCostoMin);
        panelCostos.add(jtCostoMin);
        panelCostos.add(jlCostoMed);
        panelCostos.add(jtCostoMed);
        panelCostos.add(jlCostoKg);
        panelCostos.add(jtCostoKg);

        panelLimites.add(jlLimiteMin);
        panelLimites.add(jtLimiteMin);
        panelLimites.add(jlLimiteMed);
        panelLimites.add(jtLimiteMed);

        Font a = new Font("Calibri", 1, 14);

        Border bordeIdentificador = new TitledBorder(new EtchedBorder(Color.orange, Color.orange), "Control", 1, 2, a,
        Color.black);
        panelIdentificador.setBorder(bordeIdentificador);

        Border bordeCostos = new TitledBorder(new EtchedBorder(Color.orange, Color.orange), "Costos", 1, 2, a,
        Color.black);
        panelCostos.setBorder(bordeCostos);

        Border bordeNombre = new TitledBorder(new EtchedBorder(Color.orange, Color.orange), "General", 1, 2, a,
        Color.black);
        panelNombre.setBorder(bordeNombre);

        Border bordeLimites = new TitledBorder(new EtchedBorder(Color.orange, Color.orange), "Limites en Kilogramos", 1, 2, a,
        Color.black);
        panelLimites.setBorder(bordeLimites);


        panelValores.setVisible(true);

        panelEdicion= new JPanel(new GridLayout(0,1));
        panelEdicion.add(panelIdentificador);
        panelEdicion.add(panelNombre);
        panelEdicion.add(panelCostos);
        panelEdicion.add(panelLimites);
        //panelEdicion.add(panelValores);


        tabla.setEnabled(false);
        scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(800,400));
        tabla.setFillsViewportHeight(true);
        resizeColumnWidth(tabla);

        panel=new JPanel(new FlowLayout(FlowLayout.LEFT));
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
            if(jcAcciones.getSelectedItem()=="Borrar"){
                panelCostos.setVisible(false);
                panelLimites.setVisible(false);
                panelNombre.setVisible(false);
            }
            else{panelCostos.setVisible(true);
                panelLimites.setVisible(true);
                panelNombre.setVisible(true);}
            
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
                int index=padre.arrayServicios.size();
                padre.arrayServicios.add(new Servicio(
                    index,
                    jtName.getText(),
                    jtNameAbr.getText(),
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
                int index= Integer.parseInt(jtid.getText());
                Servicio serv=padre.arrayServicios.get(index);
                serv.name=jtName.getText();
                serv.nameAbr=jtNameAbr.getText();
                serv.costoMin=Double.parseDouble(jtCostoMin.getText()) ;
                serv.costoMed=Double.parseDouble(jtCostoMed.getText()) ;
                serv.costoKg=Double.parseDouble(jtCostoKg.getText());
                serv.limiteMinimo= Double.parseDouble(jtLimiteMin.getText());
                serv.limiteMedio= Double.parseDouble(jtLimiteMed.getText());
                
                DefaultTableModel modelaux= (DefaultTableModel) tabla.getModel();
                modelaux.removeRow(index);
                modelaux.insertRow(index, padre.arrayServicios.get(index).getData(index));
                padre.restartPanelEntrada();
            }

        
            if(jcAcciones.getSelectedItem()=="Borrar"){
                int index= Integer.parseInt(jtid.getText());
                padre.arrayServicios.remove(index);

                DefaultTableModel modelaux= (DefaultTableModel) tabla.getModel();
                if(padre.arrayServicios.size()<=1){return;}
                
                for(int i=index;i<padre.arrayServicios.size()+1;i++){
                    System.out.println("Indice de Borrado: "+i);
                    modelaux.removeRow(index);
                }

                for(int i=index;i<padre.arrayServicios.size();i++){
                System.out.println("Indice de Agregado: "+i);
                Servicio aux = padre.arrayServicios.get(i);
                aux.id=i;
                modelaux.addRow(new Object[]{                
                    i,
                    aux.id,
                    aux.name,
                    aux.nameAbr,
                    aux.costoMin,
                    aux.costoMed,
                    aux.costoKg,
                    aux.limiteMinimo,
                    aux.limiteMedio
                });
                }
                padre.restartPanelEntrada();
            }

            fileGestor.escribirServicios(padre.arrayServicios);
            

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
                jtName.setText(padre.arrayServicios.get(index).name);
                jtNameAbr.setText(padre.arrayServicios.get(index).nameAbr);
                jtCostoMin.setText(padre.arrayServicios.get(index).costoMin+"");
                jtCostoMed.setText(padre.arrayServicios.get(index).costoMed+"");
                jtCostoKg.setText(padre.arrayServicios.get(index).costoKg+"");
                jtLimiteMin.setText(padre.arrayServicios.get(index).limiteMinimo+"");
                jtLimiteMed.setText(padre.arrayServicios.get(index).limiteMedio+"");
            }
        }
    }
    
    
}
