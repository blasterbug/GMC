package se.umu.cs.dist.ht15.dali_ens15bsf;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.BasicReliableMulticast;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.ComMember;
import se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement.Member;
import se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement.MemberImpl;
import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingServiceUnavailableException;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.CausalOrderer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.RemoteException;

public class MemberMain {
		public static void main(String [] args) {
			System.out.println("Initiating member");	
			try {
				ComMember comMember = new ComMember( new BasicReliableMulticast(), "id" );
				Member m = new MemberImpl(new CausalOrderer(), comMember);
				comMember.addObserver( m );
				System.out.println("Connecting to server");	
				m.connectToNameserver();
				System.out.println("Joining group Bingo");
				m.updateIdFromNameServer();
				m.joinGroup("Bingo");
				System.out.println("Joined group");

				int inChar = 'a';
				while(inChar != 'q') {
					try {
						InputStreamReader reader = new InputStreamReader(System.in);
						BufferedReader in = new BufferedReader(reader);
						String input = in.readLine();
						Message msg = new Message(m.getId(), input);
						m.sendMessage(input);
						inChar = System.in.read();
					} catch (Exception e) {
						System.out.println("Couldn't read message input: "+e.getMessage());
						e.printStackTrace();
					}
				}
			} catch (RemoteException | NamingServiceUnavailableException e ) {
				System.out.println("Couldn't connect to ns");	
				System.out.println(e.getMessage());
			}

		}
}
