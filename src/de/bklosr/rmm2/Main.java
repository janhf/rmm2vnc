package de.bklosr.rmm2;

import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.err.println("Thread " + t + " caused a crash: ");
				e.printStackTrace();
				System.exit(1);
			}
		});

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				} catch (Exception e) {
				}
				Hostchooser chooser = new Hostchooser();
				chooser.setVisible(true);
				chooser.dispose();

			}
		});
		/*
		 * PROXY_HOST PROXY_PORT SSL SSLPORT CHANNEL_ID TARGET_ID DO_INIT_SWITCH
		 * connection SESSION_ID username password REPLAY_SESSION REPLAY_START
		 * FORENSIC_CONSOLE FORENSIC_NOKBD FORENSIC_FILTERKBD CLUSTER_PORT_ID
		 * BOARD_NAME
		 */
	}
}
