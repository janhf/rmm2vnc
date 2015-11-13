# rmm2vnc
Intel RMM2 VNC Standalone Client - Uses RMM2 jars to build a java Web Start App

Java Applets are currently phased out due to removed browser support. Intel RMM2 modules use Java Applets for accessing the VGA Console of servers. 

This project uses the original jar files from the Intel RMM2 module and puts some code around the applet so that it can be started as a standalone App.

You need to get the following Files:
<table>
 <thead>
  <tr>
   <td>Name</td><td>Folder</td><td>Description</td>
  </tr>
 </thead>
 <tbody>
  <tr>
   <td>rc.jar</td><td>lib</td><td>From Intel RMM2 Module</td>
  </tr>
  <tr>
   <td>rcrclang_en.jar</td><td>lib</td><td>From Intel RMM2 Module</td>
  </tr>
  <tr>
   <td>DrvRedirNative.dll</td><td>.</td><td>From Intel RMM2 Module</td>
  </tr>
  <tr>
   <td>selfsigned.jks</td><td>.</td><td>Keystore for code signing. See build.xml</td>
  </tr>
 </tbody>
</table>

Put these files in their folders and build it.
