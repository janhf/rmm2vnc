# rmm2vnc
Intel RMM2 VNC Standalone Client - Uses RMM2 jars to build a java Web Start App

Java Applets are currently phased out due to removed browser support. Intel RMM2 modules use Java Applets for accessing the VGA Console of servers. 

This project uses the original jar files from the Intel RMM2 module and puts some code around the applet so that it can be started as a standalone App.

You need to get the following files from your Intel RMM2:
Name			Folder
--------------------------------
rc.jar			lib
rcrclang_en.jar		lib
DrvRedirNative.dll	.


You need a keystore with a key inside to sign the code. (My is in selfsigned.jks in the root folder) Modify the ant script build.xml accordingly.

Put these files in the lib folder of the project and build it.
