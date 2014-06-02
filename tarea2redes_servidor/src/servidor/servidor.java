package servidor;

import java.io.*; 
import java.net.*; 
import java.util.StringTokenizer;

class TCPServer {    
	
	public static void main(String argv[]) throws Exception {    
		
		String entrada_cliente;            
		ServerSocket socket = new ServerSocket(6666);      
		
		while(true) {             
			
			Socket socket_servidor = socket.accept();             
			BufferedReader de_cliente = new BufferedReader(new InputStreamReader(socket_servidor.getInputStream()));             
			DataOutputStream a_cliente = new DataOutputStream(socket_servidor.getOutputStream());     
			
			entrada_cliente = de_cliente.readLine();    

			StringTokenizer tokens = new StringTokenizer(entrada_cliente);
			String accion = tokens.nextToken();
			
			if(accion.equals("guardar")) {
				
				System.out.println("guardar");   
				
				String emisor = tokens.nextToken();
				String receptor = tokens.nextToken();
				String mensaje = tokens.nextToken();
			
				FileWriter fichero = null;
				PrintWriter pw = null;
			
				try {   	
				
					fichero = new FileWriter("Mensajes.txt",true);
					pw = new PrintWriter(fichero); 
					pw.println(emisor + " " + receptor + " " + mensaje + '\n'); }       
			
				catch(Exception e) {
					e.printStackTrace(); }
			
				finally {    	 
				
					try {      
					
						if (null != fichero)
							fichero.close(); }           
				
					catch (Exception e2) { 
						e2.printStackTrace(); } } } 
			
			if(accion.equals("ver")) {
				
				int i = 0;
				String receptor = tokens.nextToken();
				
				FileInputStream leer = new FileInputStream("Mensajes.txt");
		        DataInputStream entrada = new DataInputStream(leer);
		        BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));

		        String datos;
		        String retornar = "";

		        while ((datos = buffer.readLine()) != null) {
		        			        
		        	StringTokenizer div = new StringTokenizer(datos, " "); 
		        	
		        	while (div.hasMoreTokens()) {
		        		 
		        		 String em = div.nextToken();
		        		 String re = div.nextToken();
		        		 String me = div.nextToken();
		        	 
		        		 if(receptor.equals(re)) {
		        		 
		        			 if(i == 0) {
		        				 
		        				 i++;
		        				 retornar = em + " " + me + " "; }	
		        		 
		        			 else	 
		        			 	 retornar = retornar + em + " " + me + " "; } } }
		        
		        a_cliente.writeBytes(retornar + '\n');
		        buffer.close(); } } } }
