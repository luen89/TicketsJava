import java.util.ArrayList;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

class EntradaRegistroModel implements TableModel
{
    private ArrayList<TableModelListener> suscriptores = new ArrayList<TableModelListener>();
    
    public Class getColumnClass (int columIndex) {
        return "String.getClass".getClass();
    }
    public int getColumnCount() {
        return 1;
    }
    public String getColumnName (int columnIndex) {
        return "";
    }
    public int getRowCount() {
        return 1;
    }
    public Object getValueAt (int rowIndex, int columnIndex) {
        return new Object();
    }
    public boolean isCellEditable (int rowIndex, int columnIndex) {
        return true;
    }
    public void setValueAt (Object aValue, int rowIndex, int columnIndex){  }

    public void addTableModelListener (TableModelListener l) {
        suscriptores.add(l);
    }
    public void removeTableModelListener (TableModelListener l) {
        suscriptores.remove(l);
    }
}