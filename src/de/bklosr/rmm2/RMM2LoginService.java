package de.bklosr.rmm2;

import java.util.LinkedList;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.jdesktop.swingx.auth.LoginService;

public class RMM2LoginService extends LoginService {

	private char[] password;
	private static Preferences pref = Preferences
			.userNodeForPackage(RMM2LoginService.class);

	public RMM2LoginService() {
		password = null;
	}

	public String[] makeArgsArray() {
		pref.put("SSL", pref.get("SSL", "off"));
		pref.put("FORENSIC_CONSOLE", pref.get("FORENSIC_CONSOLE", "no"));
		pref.put("BOARD_NAME", pref.get("BOARD_NAME", "Intel(R) RMM2"));
		pref.put("port", pref.get("port", "443"));
		pref.put("SSL_PORT", pref.get("SSL_PORT", "443"));
		pref.remove("server");

		List<String> args = new LinkedList<String>();
		try {
			for (String key : pref.keys()) {
				args.add("-" + key);
				args.add(pref.get(key, "!PREF_ERROR!"));
			}
		} catch (BackingStoreException e) {
			throw new RuntimeException(e);
		}
		if (password != null) {
			args.add("-password");
			args.add(new String(password));
		}
		pref.remove("SESSION_ID");
		return args.toArray(new String[0]);
	}

	@Override
	public boolean authenticate(String name, char[] password, String server)
			throws Exception {
		pref.put("username", name);
		this.password = password;
		return true;
	}

	public void authenticate(String sessionid) {
		pref.put("SESSION_ID", sessionid);
	}

}
