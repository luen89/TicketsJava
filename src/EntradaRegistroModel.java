import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

class EntradaRegistroModel extends AbstractTableModel 
{
    private ArrayList<EntradaRegistro> registros;
    private String columnas[] = {"Folio", "Cliente", "Monto", "Status de Pago", "Status de Entrega", "Fecha"};
    NumberFormat formatter = NumberFormat.getCurrencyInstance();

    public EntradaRegistroModel(ArrayList<EntradaRegistro> registros) {
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
            case 0: return registros.get(rowIndex).Folio;
            case 1: return registros.get(rowIndex).NombreCliente;
            case 2: return (formatter.format(registros.get(rowIndex).Monto));
            case 3: return registros.get(rowIndex).StatusPago;
            case 4: return registros.get(rowIndex).StatusEntrega;
            case 5: return registros.get(rowIndex).Fecha;
        }
        return columnIndex;
    }

    public Class getColumnClass (int columIndex) {
        return "String".getClass();
    }

    public String getColumnName (int columnIndex) {
        return columnas[columnIndex];
    }

    public boolean isCellEditable (int rowIndex, int columnIndex) {
        return false;
    }

    public void setRegistros(ArrayList<EntradaRegistro> registros) {
        this.registros = registros;
        fireTableDataChanged();
    }
}