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

public class RMM2UserNameStore {
	// The key for one of the preferences
	private static final String USER_KEY = "usernames";
	private static final String NUM_KEY = "usernames.length";

	/**
	 * A name that is used when retrieving preferences. By default, the app name
	 * is "default&quot. This should be set by the application if the
	 * application wants it's own list of user names.
	 */
	private static final String DEFAULT_APP_NAME = "default";

	/**
	 * The preferences node
	 */
	private Preferences prefs = Preferences
			.userNodeForPackage(RMM2UserNameStore.class);

	/**
	 * Contains the user names. Since the list of user names is not frequently
	 * updated, there is no penalty in storing the values in an array.
	 */
	private String[] userNames;

	/**
	 * Used for propogating bean changes
	 */
	private PropertyChangeSupport pcs;

	public static RMM2UserNameStore store = new RMM2UserNameStore();

	/**
	 * Creates a new instance of DefaultUserNameStore
	 */

	public RMM2UserNameStore() {
		pcs = new PropertyChangeSupport(this);
		userNames = new String[0];
		loadUserNames();
	}

	/**
	 * Loads the user names from Preferences
	 */
	public void loadUserNames() {
		int n = prefs.getInt(NUM_KEY, 0);
		String[] names = new String[n];
		for (int i = 0; i < n; i++) {
			names[i] = prefs.get(USER_KEY + "." + i, null);
		}
		setUserNames(names);
	}

	/**
	 * Saves the user names to Preferences
	 */
	public void saveUserNames() {
		prefs.putInt(NUM_KEY, userNames.length);
		for (int i = 0; i < userNames.length; i++) {
			prefs.put(USER_KEY + "." + i, userNames[i]);
		}
	}

	/**
	 * @inheritDoc
	 */
	public String[] getUserNames() {
		return userNames;
	}

	/**
	 * @inheritDoc
	 */
	public void setUserNames(String[] userNames) {
		if (this.userNames != userNames) {
			String[] old = this.userNames;
			this.userNames = userNames == null ? new String[0] : userNames;
			pcs.firePropertyChange("userNames", old, this.userNames);
		}
	}

	/**
	 * Add a username to the store.
	 * 
	 * @param name
	 */
	public void addUserName(String name) {
		if (!containsUserName(name)) {
			String[] newNames = new String[userNames.length + 1];
			for (int i = 0; i < userNames.length; i++) {
				newNames[i] = userNames[i];
			}
			newNames[newNames.length - 1] = name;
			setUserNames(newNames);
		}
	}

	/**
	 * Removes a username from the list.
	 * 
	 * @param name
	 */
	public void removeUserName(String name) {
		if (containsUserName(name)) {
			String[] newNames = new String[userNames.length - 1];
			int index = 0;
			for (String s : userNames) {
				if (!s.equals(name)) {
					newNames[index++] = s;
				}
			}
			setUserNames(newNames);
		}
	}

	/**
	 * @inheritDoc
	 */
	public boolean containsUserName(String name) {
		for (String s : userNames) {
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