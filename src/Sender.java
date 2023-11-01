import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Sender {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Serializer ser = new Serializer();
		ObjCreator creator = new ObjCreator();
		Document aDoc;
		
		String ipAddress = "localhost";
		int portNo = 5000;
		if(args.length > 0)
			ipAddress = args[0];
		
		if(args.length > 1) {
			try {	
				portNo = Integer.parseInt(args[1]);
			}
			catch(NumberFormatException e) {
				System.err.println("Improper command-line argument given, expected a port number as an integer for the second argument.");
				System.out.println("Using default port: 5000.");

			}
		}
		Object target = creator.createObject();
		
		aDoc = ser.serialize(target);
		XMLOutputter xOut = new XMLOutputter();
		try {
			Socket sendSock = new Socket(ipAddress, portNo);
			System.out.println("Successfully connected to the server...");
			
			OutputStream output = sendSock.getOutputStream();
			BufferedOutputStream buffOutput = new BufferedOutputStream(output);
			
			ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
		
			xOut.output(aDoc, byteOutput);

			byte[] docBytes = byteOutput.toByteArray();
			
			buffOutput.write(docBytes);
			
			buffOutput.flush();
			buffOutput.close();
			byteOutput.close();
			sendSock.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		/*
		head = deser.deserialize(aDoc);
		
		System.out.println("======================================================================");
		
		insp.inspect(head);

		System.out.println("======================================================================");
		*/
	}
}
