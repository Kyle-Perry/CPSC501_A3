import java.io.*;
import java.net.*;

import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Receiver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int portNo = 5000;
		if(args.length > 0) {
			try {	
				portNo = Integer.parseInt(args[0]);
			}
			catch(NumberFormatException e) {
				System.err.println("Improper command-line argument given, expected a port number as an integer for the first argument.");
				System.out.println("Using default port: 5000.");

			}
		}

		try {

			Deserializer deserializer = new Deserializer();
			Inspector inspector = new Inspector();

			ServerSocket receiveSock = new ServerSocket(portNo);

			System.out.println("Server is running, awaiting connection...");

			Socket client = receiveSock.accept();

			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

			SAXBuilder saxBuilder = new SAXBuilder();
			Document aDoc = saxBuilder.build(in);

			Object head = deserializer.deserialize(aDoc);

			System.out.println("======================================================================");
			inspector.inspect(head);
			System.out.println("======================================================================");

			client.close();
			receiveSock.close();
			in.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
