package removeThis2;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.InputStreamReader;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.smartcardio.*;
import javax.xml.bind.DatatypeConverter;
public class removeThis2Main {
	
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	public static String getHash(byte[]inputBytes,String algorithm) {
		String hashValue="";
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(inputBytes);
			byte[] digestBytes=messageDigest.digest();
			hashValue=DatatypeConverter.printHexBinary(digestBytes).toLowerCase();
		}
		catch(Exception e) {
			
			System.out.println("Something Wrong");
		}
		return hashValue;
		
	}
	public static void main(String[] args) throws CardException, NoSuchAlgorithmException, IOException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		/*
		TerminalFactory terminalFactory = TerminalFactory.getDefault();
        List<CardTerminal> cardTerminals = terminalFactory.terminals().list();
        CardTerminal cardTerminal = cardTerminals.get(1);        
        Card card = cardTerminal.connect("*"); 
        CardChannel channel = card.getBasicChannel();
        
		
		
		//select Epassport Applet
		
        
       
        byte[] ePassportAID=  hexStringToByteArray("A0000002471001");
        CommandAPDU command = new CommandAPDU(0x00, 0xA4, 0x04, 0x0C, ePassportAID);
        ResponseAPDU response = channel.transmit(command);
        
        String s1 = response.toString();
        String s2 =DatatypeConverter.printHexBinary(response.getData());
         
        System.out.println("Select AID response: \nSW:"+s1+"Card Response Data:" +s2);
        
		
		
        //GET_CHALLENGE command
        
         command = new CommandAPDU(0x00, 0x84, 0x00, 0x00, 0x08);
         
         response = channel.transmit(command);
         
          s1 = response.toString();
         s2 =DatatypeConverter.printHexBinary(response.getData());
         
        
        System.out.println("GET_CHALLENGE command response: \nSW:"+s1+"Card Response Data:" +s2);
        card.disconnect(true);
        */
		
		
		
		
		
		
		
		
		
		
		
	    //calculate Keyseed
        String documentNumberWithCheckDigit="M00T215973";  
        String dateOfBirthWithCheckDigit="9209149"; 
        String dateOfExpiryCheckDigit="2604181"; 
        String MRZinfo=documentNumberWithCheckDigit+dateOfBirthWithCheckDigit+dateOfExpiryCheckDigit;
        System.out.println(MRZinfo);
        MRZinfo="A05K54181697070812709189";
      
        String Kseed=getHash(MRZinfo.getBytes(),"SHA-1").substring(0,32);//get hash and take first 16 bytes
        
	    System.out.println("Kseed: "+Kseed);
	
	//Compute encryption key (Kenc)
	    	
	    String Kenc=getHash(hexStringToByteArray(Kseed +"00000001"),"SHA-1").substring(0,32);//get hash and take first 16 bytes
	    		
	    System.out.println("Kenc without parity:"+Kenc);
	    
	    
	    //write no parity Kenc 
	    try {
	        FileWriter myWriter = new FileWriter("C:\\Users\\hasan\\source\\repos\\parityICAO\\test.txt");
	        myWriter.write(Kenc);
	        myWriter.close();
	        //System.out.println("Successfully wrote to the file.");
	      } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
	    
	    
	    
	    
	    ProcessBuilder builder = new ProcessBuilder(
	            "cmd.exe", "/c", "cd \"C:\\Users\\hasan\\source\\repos\\parityICAO\" && \"parityICAO.exe");
	        builder.redirectErrorStream(true);
	        Process p = builder.start();
	        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        String line;
	        while (true) {
	            line = r.readLine();
	            if (line == null) { break; }
	            System.out.println(line);
	
	        }
	
	    
	    
	        
	        // File path is passed as parameter
	        File file = new File(
	            "C:\\Users\\hasan\\source\\repos\\parityICAO\\parityResult.txt");
	 
	        // Note:  Double backquote is to avoid compiler
	        // interpret words
	        // like \test as \t (ie. as a escape sequence)
	 
	        // Creating an object of BufferedReader class
	        BufferedReader br
	            = new BufferedReader(new FileReader(file));
	 
	        
	        
	        // Condition holds true till
	        // there is character in a string
	        String a;
	        while ((a = br.readLine()) != null) {
	        	Kenc=a;//a yerine sadece kseed kullansam a null oluyodu 2. islemde
	            //o yuzden a diye bir string ekledim
	            // Print the string
	        //    System.out.println("Kseed With parity operations: "+a);
	          
	        
	        }
	        
	        
	        System.out.println("Kenc With parity operations: "+Kenc);
	        
	        String KencKa=Kenc.substring(0,16);
	        String KencKb=Kenc.substring(16,32);
	          
	        System.out.println("KencKa: "+KencKa);
	        System.out.println("KencKb: "+KencKb);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	      //Compute encryption key (Kmac)
	    	
		    String Kmac=getHash(hexStringToByteArray(Kseed +"00000002"),"SHA-1").substring(0,32);//get hash and take first 16 bytes
		    		
		    System.out.println("Kmac without parity:"+Kmac);
		    
		    
		    //write no parity Kmac 
		    try {
		        FileWriter myWriter = new FileWriter("C:\\Users\\hasan\\source\\repos\\parityICAO\\test.txt");
		        myWriter.write(Kmac);
		        myWriter.close();
		        //System.out.println("Successfully wrote to the file.");
		      } catch (IOException e) {
		        System.out.println("An error occurred.");
		        e.printStackTrace();
		      }
		    
		    
		    
		    
		     builder = new ProcessBuilder(
		            "cmd.exe", "/c", "cd \"C:\\Users\\hasan\\source\\repos\\parityICAO\" && \"parityICAO.exe");
		        builder.redirectErrorStream(true);
		         p = builder.start();
		         r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		        
		        while (true) {
		            line = r.readLine();
		            if (line == null) { break; }
		            System.out.println(line);
		
		        }
		
		    
		    
		        
		        // File path is passed as parameter
		        file = new File(
		            "C:\\Users\\hasan\\source\\repos\\parityICAO\\parityResult.txt");
		 
		        // Note:  Double backquote is to avoid compiler
		        // interpret words
		        // like \test as \t (ie. as a escape sequence)
		 
		        // Creating an object of BufferedReader class
		        br= new BufferedReader(new FileReader(file));
		 
		        
		        
		        // Condition holds true till
		        // there is character in a string
		        
		        while ((a = br.readLine()) != null) {
		        	Kmac=a;//a yerine sadece kseed kullansam a null oluyodu 2. islemde
		            //o yuzden a diye bir string ekledim
		            // Print the string
		        //    System.out.println("Kseed With parity operations: "+a);
		          
		        
		        }
		        
		        
		        System.out.println("Kmac With parity operations: "+Kmac);
		        
		        String KmacKa=Kmac.substring(0,16);
		        String KmacKb=Kmac.substring(16,32);
		          
		        System.out.println("KmacKa: "+KmacKa);
		        System.out.println("KmacKb: "+KmacKb);
	
	
		        System.out.println("KSEED: "+Kseed);
	
	
	
		        
		        
		        
		        
		      String rndIfd="0101010101010101";  
		      String kIfd="01010101010101010101010101010101";
		      String RndIc="D099C62B0C69EBD6";
		      String s=rndIfd+RndIc+kIfd;
		        
		        String random="0101010101010101";
		      //getbytes is wrong check
		        //key accepts only 24 bytes
		    	  KeyGenerator kg=KeyGenerator.getInstance("DESede");
		    	 Cipher cipher=Cipher.getInstance("DESede/ECB/NoPadding");		    	 		    	 		    	 
	              SecretKey key = new SecretKeySpec(random.getBytes(), "DESede");
		          cipher.init(Cipher.ENCRYPT_MODE, key);
		          byte[] result3DES=cipher.doFinal(hexStringToByteArray(s));
		               
		      
		      
		          String result3DESString= DatatypeConverter.printHexBinary(result3DES).toLowerCase();
		          System.out.println(result3DESString);
	
	
	}			
  
}
