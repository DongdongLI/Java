//package test;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
public class testDataInputStreamClient {
	static Socket sock=null;
	static ServerSocket client=null;
	static int port=5555;
	static String localhost="127.0.0.1";
	static DataInputStream dis=null;
	static DataOutputStream dos=null;
	
	public static void main(String[] args) throws IOException
	{
		try {
			sock=new Socket(localhost,port);
			dis=new DataInputStream(sock.getInputStream());
			System.out.println(dis.readUTF());//it works
			
			dos=new DataOutputStream(sock.getOutputStream());
			String str="I got it";
			dos.writeUTF(str);//also works: so one socket can have multiple streams
			dos.flush();
			
		} catch (UnknownHostException e) {
			System.out.println("Unknown");
			
		} catch (IOException e) {
			System.out.println("can not listen");
		}
		finally
		{
			if(sock!=null)sock.close();
		}
	}
}
//So far it can receive the String comes from the server
//future work: write something back
