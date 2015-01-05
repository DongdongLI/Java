import java.io.*;
import java.net.*;
public class Client
{
	static Socket sock=null;
	static BufferedReader receiveRead=null;
	static BufferedReader keyRead=null;
	static PrintWriter pwrite=null;
	static BufferedReader inputReader=null;
	
	static  OutputStream os=null;
	static InputStream istream=null;
    public static void main(String[] args) throws Exception
    {    // reading from keyboard (keyRead object)
//        keyRead = new BufferedReader(new InputStreamReader(System.in)); // sending to client (pwrite object)
//        
//       ostream = sock.getOutputStream();
//        pwrite = new PrintWriter(ostream, true);   
//        
//        // receiving from server ( receiveRead object)
//        istream = sock.getInputStream();
//        receiveRead = new BufferedReader(new InputStreamReader(istream));   
//        
//        System.out.println("Start the chitchat, type and press Enter key");
//        String receiveMessage, sendMessage; while(true) { sendMessage = keyRead.readLine(); // keyboard reading
//        pwrite.println(sendMessage); // sending to server
//        pwrite.flush(); // flush the data
//        if((receiveMessage = receiveRead.readLine()) != null) //receive from server
//        { 
//        	System.out.println(receiveMessage); // displaying at DOS prompt
//        }
        createSocket();
        //}
    }
    static void createSocket()
    {
    	try {
			sock = new Socket("127.0.0.1", 3000);
			System.out.println("Connected.");
			os=sock.getOutputStream();
			istream=sock.getInputStream();
			//receiveRead = new BufferedReader(new InputStreamReader(istream));
			
			createReadThread();
			createWriteThread();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    static void createReadThread()
    {
    	receiveRead = new BufferedReader(new InputStreamReader(istream));//putting it here  
    	Thread readThread=new Thread()
    	{
    		public void run()
    		{
    			while(sock.isConnected())
    			{
    				char[] b=new char[200];
    				try {
						int num=receiveRead.read(b);
						if(num>0)
						{
							char[] a=new char[num];
							System.arraycopy(b, 0, a, 0, num);
							System.out.println("Received "+String.copyValueOf(a));
						}
					} catch (IOException e) {
						
						e.printStackTrace();
					}
    			}
    		}
    	};
    	readThread.setPriority(Thread.MAX_PRIORITY);
    	readThread.start();
    }
    static void createWriteThread()
    {
    	Thread writeThread=new Thread(){
    		public void run()
    		{
    			while(sock.isConnected()){inputReader=new BufferedReader(new InputStreamReader(System.in));
    			pwrite = new PrintWriter(os, true);  
    			try {
					sleep(100);
					String typeMessage=inputReader.readLine();
					if(typeMessage!=null&&typeMessage.length()!=0)
					{
						synchronized(sock)
						{
							pwrite.write(typeMessage);
							pwrite.flush();
							sleep(100);
						}
					}
				} catch (InterruptedException | IOException e) {
					
					e.printStackTrace();
				}
    			}
    			
    		}
    	};
    	writeThread.setPriority(Thread.MAX_PRIORITY);
    	writeThread.start();
    }

}
