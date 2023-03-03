
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

// import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import java.util.List;

/**
 *
 * @author Zyngy Coding
 */

public class PanelRegistros extends JPanel implements ActionListener {    
    private JTable tabla;
    private JLabel lbFiltrar, lbFiltrarCBs;
    private JScrollPane spTabla;
    private GestorArchivos gestor;
    private JTextField txtFiltrar;
    private JPanel pFiltros, pCbs, pTxts;
    private JComboBox<String> cbClientes, cbStatusEntrega, cbStatusPago;
    private String filtrarClientes[] = {};
    private String filtrarStatusPago[] = {};
    private String filtrarStatusEntrega[] = {};
    private JButton btnActualizar;
    private TableRowSorter<EntradaRegistroModel> sorter;
    private EntradaRegistroModel modelo;
    
    public PanelRegistros(GestorArchivos ga) {
        gestor=ga;
        initComponents();
    }

    private void initComponents() {                
        // Creación de Tabla
        ArrayList<EntradaRegistro> registros = gestor.leerArchivo();
        modelo = new EntradaRegistroModel(registros);
        sorter = new TableRowSorter<EntradaRegistroModel>(modelo);
        
        tabla = new JTable(modelo);
        // tabla.setDefaultRenderer(Object.class, new RenderButton());

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                JTable target = (JTable) me.getSource();

                // System.out.println("Selected Row in view: " + target.getSelectedRow());                
                // System.out.println("Selected Row in rs: " + tabla.convertRowIndexToModel(target.getSelectedRow()));
                // int row = target.getSelectedRow(); // select a row
                int row = tabla.convertRowIndexToModel(target.getSelectedRow()); // select a row
                int column = target.getSelectedColumn(); // select a column
                if(column != 6)
                    return;
                
                TicketEntrega ticket = gestor.readFileOrder(Integer.parseInt(modelo.getValueAt(row, 0).toString()));
                VentanaDetalles vDetalles = new VentanaDetalles(ticket, gestor);
                System.out.println("Entré");
                vDetalles.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                vDetalles.setVisible(true);
                
                // if (me.getClickCount() == 2) { // to detect doble click events
                //     JTable target = (JTable) me.getSource();
                //     int row = target.getSelectedRow(); // select a row
                //     int column = target.getSelectedColumn(); // select a column
                //     JOptionPane.showMessageDialog(null, tabla.getValueAt(row, 0)); // get the value of a row and column.
                // }
            }
        });

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tabla.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tabla.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tabla.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tabla.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tabla.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);

        // tabla.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        // tabla.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JTextField(), modelo));
        
        tabla.setRowSorter(sorter);
        sorter.setComparator(2, new Comparator<String>() {            
            @Override
            public int compare(String name1, String name2) {
                double d1 = Double.parseDouble(name1.replaceAll(",", "").substring(1));
                double d2 = Double.parseDouble(name2.replaceAll(",", "").substring(1));
                return Double.compare(d1, d2);
            }
        });
        sorter.setRowFilter(null);


        // Creacion de los campos para filtrar
        lbFiltrar = new JLabel("Filtrar:");
        lbFiltrarCBs = new JLabel("Filtrar por:");
        txtFiltrar = new JTextField("", 8);
        txtFiltrar.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                validate(e);
            }
            public void insertUpdate(DocumentEvent e) {
                validate(e);
            }
            public void removeUpdate(DocumentEvent e) {
                validate(e);
            }

            private void validate(DocumentEvent e) {
                filtrarTabla();
            }

        });

        ArrayList<String> clientes = new ArrayList<>();
        clientes.add("Cliente");
        for (int i = 0; i < registros.size(); i++) {
            if (!clientes.contains(registros.get(i).getNombreCliente())) {
                clientes.add(registros.get(i).getNombreCliente());
            }
        }
        filtrarClientes = (String[]) clientes.toArray(new String[clientes.size()]);
        cbClientes = new JComboBox<>(filtrarClientes);
        
        ArrayList<String> statusPago = new ArrayList<>();
        statusPago.add("Status de Pago");
        for (int i = 0; i < registros.size(); i++) {
            if (!statusPago.contains(registros.get(i).getStatusPago())) {
                statusPago.add(registros.get(i).getStatusPago());
            }
        }
        filtrarStatusPago = (String[]) statusPago.toArray(new String[statusPago.size()]);
        cbStatusPago = new JComboBox<>(filtrarStatusPago);

        ArrayList<String> statusEntrega = new ArrayList<>();
        statusEntrega.add("Status de Entrega");
        for (int i = 0; i < registros.size(); i++) {
            if (!statusEntrega.contains(registros.get(i).getStatusEntrega())) {
                statusEntrega.add(registros.get(i).getStatusEntrega());
            }
        }
        filtrarStatusEntrega = (String[]) statusEntrega.toArray(new String[statusEntrega.size()]); 
        cbStatusEntrega = new JComboBox<>(filtrarStatusEntrega);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(this);

        // AutoCompleteDecorator.decorate(cbClientes);
        cbClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Entra cambiar elemento de filtro");
                filtrarTabla();
            }
        });
        cbStatusEntrega.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Entra cambiar elemento de filtro");
                filtrarTabla();
            }
        });
        cbStatusPago.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Entra cambiar elemento de filtro");
                filtrarTabla();
            }
        });

        pCbs = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pCbs.add(lbFiltrarCBs);
        pCbs.add(cbClientes);
        pCbs.add(cbStatusPago);
        pCbs.add(cbStatusEntrega);

        pTxts = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pTxts.add(btnActualizar);
        pTxts.add(lbFiltrar);
        pTxts.add(txtFiltrar);
        
        pFiltros = new JPanel();
        pFiltros.setLayout (new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1.0;

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0; // El área de texto empieza en la columna cero.
        constraints.gridy = 0; // El área de texto empieza en la fila cero
        constraints.gridwidth = 1; // El área de texto ocupa dos columnas.
        constraints.gridheight = 1; // El área de texto ocupa 2 filas.
        pFiltros.add(pCbs, constraints);

        constraints.gridx = 1; // El área de texto empieza en la columna cero.
        constraints.gridy = 0; // El área de texto empieza en la fila cero
        constraints.gridwidth = 1; // El área de texto ocupa dos columnas.
        constraints.gridheight = 1; // El área de texto ocupa 2 filas.
        pFiltros.add(pTxts, constraints);
        
        spTabla = new JScrollPane(tabla); 
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(pFiltros);
        add(spTabla);
    }

    public void refreshDisplay(){
        this.updateUI();
    }

    private void filtrarTabla(){
        List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>();

        if (cbClientes.getSelectedIndex() != 0) {
            filters.add(RowFilter.regexFilter(cbClientes.getSelectedItem().toString(), 1));
        }

        if(cbStatusPago.getSelectedIndex() != 0){
            filters.add(RowFilter.regexFilter(cbStatusPago.getSelectedItem().toString(), 3));
        }

        if (cbStatusEntrega.getSelectedIndex() != 0) {            
            filters.add(RowFilter.regexFilter(cbStatusEntrega.getSelectedItem().toString(), 4));
        }

        String text = txtFiltrar.getText();
        if (text.length() != 0) {
            filters.add(RowFilter.regexFilter(text));
        }

        sorter.setRowFilter(RowFilter.andFilter(filters));
    }

    public void actualizarTabla(){
        modelo.setRegistros(gestor.leerArchivo());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        System.out.println("Action performed");
        modelo.setRegistros(gestor.leerArchivo());
    }
   
}
