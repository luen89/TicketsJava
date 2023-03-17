import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

class ServiciosDetallesModel extends AbstractTableModel 
{
    private ArrayList<ElementoEntrega> registros;
    private String columnas[] = {"Servicio", "Descripcion", "Piezas", "Kg", "Acero", "Dureza", "Piezas Entregadas"};
    private boolean editable[] = {false, false, false, false, false, false, true};
    NumberFormat formatter = NumberFormat.getCurrencyInstance();

    public ServiciosDetallesModel(ArrayList<ElementoEntrega>  registros) {
        this.registros = registros;
    }
    
    public int getRowCount() {
        return registros.size();
    }

    public int getColumnCount() {
        return 7;
    }

    public Object getValueAt (int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0: return registros.get(rowIndex).getServicio();
            case 1: return registros.get(rowIndex).getDescripcion();
            case 2: return registros.get(rowIndex).getPiezas();
            case 3: return String.format("%,.2f", registros.get(rowIndex).getKilos());
            case 4: return registros.get(rowIndex).getAcero();
            case 5: return registros.get(rowIndex).getDureza();
            case 6: return registros.get(rowIndex).getPiezasEntregadas();
        }
        return columnIndex;
    }

    public void setValueAt (Object value, int rowIndex, int columnIndex) {
        if(columnIndex != 6)
            return;
            
        int max = registros.get(rowIndex).getPiezas();
        try {
            int val = Integer.parseInt(value.toString());
            if(val > max){
                val = max;
            }
            if(val < 0){
                val = 0;
            }
            registros.get(rowIndex).setPiezasEntregadas(val);
        } catch (Exception e) {
            System.out.println("Error al convertir a entero");
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public Class getColumnClass (int columIndex) {
        return "String".getClass();
    }

    public String getColumnName (int columnIndex) {
        return columnas[columnIndex];
    }

    public boolean isCellEditable (int rowIndex, int columnIndex) {
        return editable[columnIndex];
    }

    public void setRegistros(ArrayList<ElementoEntrega> registros) {
        this.registros = registros;
        fireTableDataChanged();
    }

    public void actualizarTabla() {
        fireTableDataChanged();
    }
}