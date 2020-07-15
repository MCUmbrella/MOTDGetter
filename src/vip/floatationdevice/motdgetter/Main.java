package vip.floatationdevice.motdgetter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Main {
	public static void main (String[] args) {
		if(args.length!=2) {System.out.println("[X] Too many or too few arguments. Usage: java -jar motdgetter.jar <server address> <port>");System.exit(1);}
		if(!(Integer.parseInt(args[1])>0&&Integer.parseInt(args[1])<65536)) {System.out.println("[X] Port number must >0 and <65536");System.exit(1);}
		String name=args[0];
		int port=Integer.parseInt(args[1]);
		//for(;port<65535;port++) 
		//{
			try {
				Socket sock = new Socket(name, port);
				DataOutputStream out = new DataOutputStream(sock.getOutputStream());
				DataInputStream in = new DataInputStream(sock.getInputStream());
				out.write(0xFE);
				int b;
				StringBuffer str = new StringBuffer();
				while ((b = in.read()) != -1) {
				if (b != 0 && b > 16 && b != 255 && b != 23 && b != 24) {
				// Not sure what use the two characters are so I omit them
				str.append((char) b);
				//System.out.println(b + ":" + ((char) b));
				}
				}
				String[] data = str.toString().split("¡ì");
				String serverMotd = data[0];
				int onlinePlayers = Integer.parseInt(data[1]);
				int maxPlayers = Integer.parseInt(data[2]);
				System.out.println(String.format("[i] %s:%d\n   %s\n    %d/%d",name,port,serverMotd,onlinePlayers,maxPlayers));
				} catch (Throwable e) {
				// TODO Auto-generated catch block
				System.out.println("[X] Ping '"+name+":"+port+"' failed ("+e.toString()+")");
				}
		//}
		
		
		
		System.out.println("[i] Exited");
	}
	public void test() {}
}