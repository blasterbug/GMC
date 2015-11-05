package se.umu.cs.dist.ht15.dali_ens15bsf;

import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingService;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

public class NameserverMain {

	public static void main(String [] args) {
		try {
		 	NamingService ns = new NamingService();
		}catch(RemoteException | AlreadyBoundException e) {
			System.out.println("Couldn't start naming service");	
		}

		try {
			System.in.read();
		}catch(IOException e) {

		}
	}
}
