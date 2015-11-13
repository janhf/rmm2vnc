package de.bklosr.rmm2;

import java.util.LinkedList;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class PreferencesTableModel implements TableModel {

	private Preferences pref = Preferences.userNodeForPackage(this.getClass());
	private List<PrefTableRow> prefData = new LinkedList<PrefTableRow>();
	private List<TableModelListener> tml = new LinkedList<TableModelListener>();

	public PreferencesTableModel() {
		try {
			for (String key : pref.keys()) {
				prefData.add(new PrefTableRow(key, pref.get(key, null)));
			}
		} catch (BackingStoreException e) {
			throw new RuntimeException("Preferences are not accesible...", e);
		}
	}

	@Override
	public int getRowCount() {
		return prefData.size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "Key";
		case 1:
			return "Value";
		default:
			return "UNKNOWN";
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return columnIndex == 0 ? prefData.get(rowIndex).getKey() : prefData
				.get(rowIndex).getValue();
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (aValue instanceof String && columnIndex == 1) {
			prefData.get(rowIndex).setValue((String) aValue);

			fireTableChangedEvent(new TableModelEvent(this, rowIndex, rowIndex,
					columnIndex, TableModelEvent.UPDATE));
		}
	}

	private void fireTableChangedEvent(TableModelEvent tableModelEvent) {
		for (TableModelListener l : tml) {
			l.tableChanged(tableModelEvent);
		}
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		tml.add(l);
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		tml.remove(l);
	}

	class PrefTableRow {
		private String key;
		private String value;

		public PrefTableRow(String key, String value) {
			super();
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
			pref.put(key, value);
		}

	}
}
