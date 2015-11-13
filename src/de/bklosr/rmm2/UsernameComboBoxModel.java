package de.bklosr.rmm2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import de.bklosr.rmm2.otherPrefs.RMM2UserNameStore;

public class UsernameComboBoxModel implements ComboBoxModel<String>,
		PropertyChangeListener {

	private Object selectedItem;

	private List<ListDataListener> ldl;

	public UsernameComboBoxModel() {
		ldl = new LinkedList<ListDataListener>();
		RMM2UserNameStore.store.addPropertyChangeListener(this);
	}

	@Override
	public int getSize() {
		return RMM2UserNameStore.store.getUserNames().length;
	}

	@Override
	public String getElementAt(int index) {
		return RMM2UserNameStore.store.getUserNames()[index];
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		ldl.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		ldl.remove(l);
	}

	private void fireIntervalAdded(ListDataEvent e) {
		for (ListDataListener l : ldl) {
			l.intervalAdded(e);
		}
	}

	private void fireIntervalRemoved(ListDataEvent e) {
		for (ListDataListener l : ldl) {
			l.intervalAdded(e);
		}
	}

	private void fireListReplaced(int oldSize, int newSize) {
		for (ListDataListener l : ldl) {
			l.intervalRemoved(new ListDataEvent(this,
					ListDataEvent.INTERVAL_REMOVED, 0, oldSize));
			l.intervalAdded(new ListDataEvent(this,
					ListDataEvent.INTERVAL_ADDED, 0, newSize));
		}
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selectedItem = anItem;
	}

	@Override
	public Object getSelectedItem() {
		return selectedItem;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("userNames")) {
			fireListReplaced(((String[]) evt.getOldValue()).length,
					((String[]) evt.getNewValue()).length);
		}
	}

}
