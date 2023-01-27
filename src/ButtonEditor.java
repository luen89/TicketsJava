import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ButtonEditor extends DefaultCellEditor{    
    protected JButton btn;
    private String lb;
    private Boolean clicked;
    private int row;
    private EntradaRegistroModel modelo; 
    
    public ButtonEditor(JTextField textField, EntradaRegistroModel model) {
        super(textField);
        this.modelo = model;

        btn = new JButton();
        // btn.setOpaque(true);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();                
            }
            
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        lb = (value != null) ? (String) value : "";
        btn.setText(lb);
        clicked = true;
        this.row = row;
        return btn;
    }

    @Override
    public Object getCellEditorValue() {
        if(clicked){
            System.out.println(row + " clicked");
            String folio = (String) modelo.getValueAt(row, 0);
            System.out.println(folio);
        }
        clicked = false;
        return new String(lb);
    }

    @Override
    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        // TODO Auto-generated method stub
        super.fireEditingStopped();
    }
}
