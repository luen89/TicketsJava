import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

class ServiciosDetallesModel extends AbstractTableModel 
{
    private ArrayList<Elemento> registros;
    private String columnas[] = {"Servicio", "Piezas", "Kg", "Acero", "Dureza", "Piezas Entregadas"};
    private boolean editable[] = {false, false, false, false, false, true};
    NumberFormat formatter = NumberFormat.getCurrencyInstance();

    public ServiciosDetallesModel(ArrayList<Elemento>  registros) {
        this.registros = registros;
    }
    
    public int getRowCount() {
        return registros.size();
    }

    public int getColumnCount() {
        return 6;
    }

    public Object getValueAt (int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0: return registros.get(rowIndex).getServicio();
            case 1: return registros.get(rowIndex).getPiezas();
            case 2: return String.format("%,.2f", registros.get(rowIndex).getKilos());
            case 3: return registros.get(rowIndex).getAcero();
            case 4: return registros.get(rowIndex).getDureza();
            case 5: return registros.get(rowIndex).getPiezasEntregadas();
        }
        return columnIndex;
    }

    public void setValueAt (Object value, int rowIndex, int columnIndex) {
        if(columnIndex != 5)
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

    public void setRegistros(ArrayList<Elemento> registros) {
        this.registros = registros;
        fireTableDataChanged();
    }

    public void actualizarTabla() {
        fireTableDataChanged();
    }
}