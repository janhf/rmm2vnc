package de.bklosr.rmm2.otherPrefs;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.prefs.Preferences;

/**
 * Saves the user names in Preferences. Because any string could be part of the
 * user name, for every user name that must be saved a new Preferences key/value
 * pair must be stored.
 * 
 */
public class RMM2ServerNameStore {
	// The key for one of the preferences
	private static final String SERVER_KEY = "servers";
	private static final String NUM_KEY = "servers.length";

	/**
	 * A name that is used when retrieving preferences. By default, the app name
	 * is "default&quot. This should be set by the application if the
	 * application wants it's own list of user names.
	 */
	private static final String DEFAULT_APP_NAME = "default";

	/**
	 * The preferences node
	 */
	private static final Preferences prefs = Preferences
			.userNodeForPackage(RMM2ServerNameStore.class);

	private PropertyChangeSupport pcs;

	public static RMM2ServerNameStore store = new RMM2ServerNameStore();

	/**
	 * Contains the user names. Since the list of user names is not frequently
	 * updated, there is no penalty in storing the values in an array.
	 */
	private String[] serverNames;

	/**
	 * Creates a new instance of DefaultUserNameStore
	 */

	private RMM2ServerNameStore() {
		pcs = new PropertyChangeSupport(this);
		serverNames = new String[0];
		loadServerNames();
	}

	/**
	 * Loads the user names from Preferences
	 */
	public void loadServerNames() {
		int n = prefs.getInt(NUM_KEY, 0);
		String[] names = new String[n];
		for (int i = 0; i < n; i++) {
			names[i] = prefs.get(SERVER_KEY + "." + i, null);
		}
		setServerNames(names);
	}

	/**
	 * Saves the user names to Preferences
	 */
	public void saveServerNames() {

		prefs.putInt(NUM_KEY, serverNames.length);
		for (int i = 0; i < serverNames.length; i++) {
			prefs.put(SERVER_KEY + "." + i, serverNames[i]);
		}

	}

	/**
	 * @inheritDoc
	 */
	public String[] getServerNames() {
		return serverNames;
	}

	/**
	 * @inheritDoc
	 */
	public void setServerNames(String[] serverNames) {
		if (this.serverNames != serverNames) {
			String[] old = this.serverNames;
			this.serverNames = serverNames == null ? new String[0]
					: serverNames;
			pcs.firePropertyChange("serverNames", old, this.serverNames);
		}
	}

	/**
	 * Add a username to the store.
	 * 
	 * @param name
	 */
	public void addServerName(String name) {
		if (!containsServerName(name)) {
			String[] newNames = new String[serverNames.length + 1];
			for (int i = 0; i < serverNames.length; i++) {
				newNames[i] = serverNames[i];
			}
			newNames[newNames.length - 1] = name;
			setServerNames(newNames);
		}
	}

	/**
	 * Removes a username from the list.
	 * 
	 * @param name
	 */
	public void removeServerName(String name) {
		System.err.println("Removing " + name);
		if (containsServerName(name)) {
			String[] newNames = new String[serverNames.length - 1];
			int index = 0;
			for (String s : serverNames) {
				if (!s.equals(name)) {
					newNames[index++] = s;
				}
			}
			setServerNames(newNames);
		}
	}

	/**
	 * @inheritDoc
	 */
	public boolean containsServerName(String name) {
		for (String s : serverNames) {
			if (s.equals(name)) {
				return true;
			}
		}
		return false;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}

}