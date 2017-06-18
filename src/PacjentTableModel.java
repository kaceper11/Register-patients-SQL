import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;


public class PacjentTableModel extends AbstractTableModel{

    private TableModel modelPacjent;

    public static PacjentTableModel createModel(TableModel model)
    {
        PacjentTableModel pacjentModel = new PacjentTableModel();
        pacjentModel.modelPacjent = model;
        return pacjentModel;
    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public int getRowCount() {
        return modelPacjent.getRowCount();
    }

    @Override
    public int getColumnCount() {
        return modelPacjent.getColumnCount();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        switch (columnIndex)
        {
            case 4:
                return Pacjent.getInsuranceNumber(Integer.parseInt(modelPacjent.getValueAt(rowIndex, 4).toString()));
            case 5:
                return BazaDanych.convertLongToDateFormat(Long.parseLong(modelPacjent.getValueAt(rowIndex, 5).toString()));
            case 6:
                if (modelPacjent.getValueAt(rowIndex, 6) != null) {
                    return BazaDanych.convertLongToDateFormat(Long.parseLong(modelPacjent.getValueAt(rowIndex, 6).toString()));
                }
                else
                    return " ";
        }

    return modelPacjent.getValueAt(rowIndex, columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return modelPacjent.getColumnName(column);
    }


}
