
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class testDataInputStreamServer {
	static Socket sock=null;
	static ServerSocket client=null;
	static int port=5555;
	
	static DataInputStream dis=null;
	static DataOutputStream dos=null;
	
	public static void main(String[] args) throws IOException
	{
		try {
			client=new ServerSocket(port);
			sock=client.accept();
			
			dos=new DataOutputStream(sock.getOutputStream());
			String str="test from server";
			dos.writeUTF(str);//it works
			dos.flush();
			
			dis=new DataInputStream(sock.getInputStream());
			System.out.println(dis.readUTF());//works
			
		} catch (IOException e) {
			System.out.println("can not start...");
		}
		finally
		{
			if(sock!=null)sock.close();
			if(client!=null)client.close();
		}
	}
}
//It can now send a String to the client
//future work: readsomething; thread
