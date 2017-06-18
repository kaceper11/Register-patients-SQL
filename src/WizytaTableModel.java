import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;


public class WizytaTableModel extends AbstractTableModel{

    private TableModel modelWizyta;

    public static WizytaTableModel createModel(TableModel modelWizyta)
    {
        WizytaTableModel wizytaModel = new WizytaTableModel();
        wizytaModel.modelWizyta = modelWizyta;
        return wizytaModel;
    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public int getRowCount() {
        return modelWizyta.getRowCount();
    }

    @Override
    public int getColumnCount() {
        return modelWizyta.getColumnCount();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        switch (columnIndex)
        {
            case 1:
                return BazaDanych.convertLongToDateFormat(Long.parseLong(modelWizyta.getValueAt(rowIndex, 1).toString()));
            case 2:
                int temp = Integer.parseInt(modelWizyta.getValueAt(rowIndex, 2).toString());
                if (temp>=3)
                    return Zabieg.wezNumerZabiegu(temp);
                else
                    return Badanie.wezNumerBadania(temp);
        }

        return modelWizyta.getValueAt(rowIndex, columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return modelWizyta.getColumnName(column);
    }
}
