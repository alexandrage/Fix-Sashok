package net.launcher.components;

import java.io.IOException;
import java.net.ServerSocket;

class Socket extends Thread {

	   private final ServerSocket socket;


	   Socket(ServerSocket Sock) {
	      this.socket = Sock;
	   }

	   public void run() {
	      while(true) {
	         try {
	            while(true) {
	               this.socket.accept();
	            }
	         } catch (IOException var2) {}
	      }
	   }
}
