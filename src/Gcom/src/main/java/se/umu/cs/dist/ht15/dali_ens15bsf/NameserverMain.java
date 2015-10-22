package se.umu.cs.dist.ht15.dali_ens15bsf;

import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingService;

import java.rmi.RemoteException;
import java.rmi.AlreadyBoundException;

import java.io.IOException;

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
