import java.util.ArrayList;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Runner {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Serializer ser = new Serializer();
		Deserializer deser = new Deserializer();
		Inspector insp = new Inspector();
		
		Object head;
		
		String a = "This is a test.";
		char[] b = new char[] {'t','h','i','s',' ','s','h','o','u','l','d',' ','a','p','p','e','a','r',' ','o','n','c','e'};
		Test2 c = new Test2(100, "Test1", true, 3.14, new int[] {1, 2 ,3});
		Test d = new Test(1000, "This Test should appear once", false, 0.000000013);

		ArrayList<Object> aList = new ArrayList<Object>();
		aList.add("2 is prime");
		aList.add("3 is prime");
		aList.add("5 is prime");
		aList.add("7 is prime");
		aList.add(c);
		
		Document aDoc;
		aDoc = ser.serialize(new Object[] {a, b, d, d, new int[] {6, 5, 4, 3, 2, 1}, "Hello, world!", new char[][] {new char[] {'a','b','c'},new char[] {'d','e','f'},new char[] {'g','h','i'}}, aList});
		XMLOutputter xOut = new XMLOutputter(Format.getPrettyFormat());
		try {
			xOut.output(aDoc, System.out);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		head = deser.deserialize(aDoc);
		
		System.out.println("======================================================================");
		
		insp.inspect(head);

		System.out.println("======================================================================");
				
	}
}
