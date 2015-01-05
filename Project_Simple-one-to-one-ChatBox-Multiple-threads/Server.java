import java.io.*;
import java.net.*;
public class Server
{
		static ServerSocket sersock=null;
		static Socket sock=null;
		static BufferedReader keyRead=null;
		
		static OutputStream ostream=null;
		static InputStream istream=null;
		
		static PrintWriter pwrite=null;
		static BufferedReader receiveRead=null;
    public static void main(String[] args) throws Exception {
//        sersock = new ServerSocket(3000);
//        System.out.println("Server ready for chatting");
//        Socket sock = sersock.accept( ); // reading from keyboard (keyRead object)
//        keyRead = new BufferedReader(new InputStreamReader(System.in)); // sending to client (pwrite object)
//        
//        OutputStream ostream = sock.getOutputStream();
//        
//        PrintWriter pwrite = new PrintWriter(ostream, true);   // receiving from server ( receiveRead object)
//        
//        InputStream istream = sock.getInputStream();
//        
//        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
//        
//        String receiveMessage, sendMessage;
//        while(true) {//one in one out
//            if((receiveMessage = receiveRead.readLine()) != null)
//        { System.out.println(receiveMessage);
//        }
//        sendMessage = keyRead.readLine();
//        pwrite.println(sendMessage);
//        pwrite.flush(); 
//        } 
    	createSocket();
        }
    static void createSocket()
    {
    	try {
			sersock=new ServerSocket(3000);
			sock=sersock.accept();
			ostream = sock.getOutputStream();
			istream = sock.getInputStream();
			
			createWriteThread();
			createReadThread();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    static void createReadThread()
    {
    	receiveRead = new BufferedReader(new InputStreamReader(istream));
    	Thread readThread=new Thread(){
    		public void run()
    		{
    			while(sock.isConnected())
    			{
    				char[] b=new char[200];
    				int num=0;
					try {
						num = receiveRead.read(b);
					} catch (IOException e) {
						
						e.printStackTrace();
					}
    				if(num>0)
    				{
    					char[] a=new char[num];
    					System.arraycopy(b, 0, a, 0, num);
    					System.out.println("Received "+String.copyValueOf(a));
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
    			while(sock.isConnected())
    			{
    				keyRead = new BufferedReader(new InputStreamReader(System.in));
    				pwrite = new PrintWriter(ostream, true);
    				try {
						sleep(100);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
    				String typeMessage;
					try {
						typeMessage = keyRead.readLine();
						if(typeMessage!=null && typeMessage.length()!=0)
	    				{
	    					synchronized(sock)
	    					{
	    						pwrite.write(typeMessage);
	    						pwrite.flush();
	    						sleep(100);
	    					}
	    				}
					} catch (IOException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				
    			}
    		}
    	};
    	writeThread.setPriority(Thread.MAX_PRIORITY);
    	writeThread.start();
    }
    }
