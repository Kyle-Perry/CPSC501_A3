import static org.junit.Assert.*;

import java.lang.reflect.Array;

import java.util.ArrayList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.junit.*;
import org.junit.Test;

public class TestDeserializer {
	private static Deserializer testDeser;
	//private static Serializer testSer;
	
	@BeforeClass
	public static void initializeTest() {
		testDeser = new Deserializer();
	}
	
	@Test
	public void testPrimFields() {
		Object testObj;
		
		Document tDoc = new Document();

		Element root = new Element("serialized");
		tDoc.setRootElement(root);

		Element objElement = new Element("object");		
		root.addContent(objElement);
		
		objElement.setAttribute("class", TestObj.class.getName());
		objElement.setAttribute("id", Integer.toString(1));
		
		Element f1 = new Element("field");
		f1.setAttribute("name", "val1");
		f1.setAttribute("declaringclass", "TestObj");			
		f1.addContent(new Element("value"));
		((Element)f1.getContent(0)).setText("100");
		objElement.addContent(f1);
		
		Element f2 = new Element("field");
		f2.setAttribute("name", "val2");
		f2.setAttribute("declaringclass", "TestObj");			
		f2.addContent(new Element("value"));
		((Element)f2.getContent(0)).setText(Short.toString(Short.MAX_VALUE));
		objElement.addContent(f2);
		
		Element f3 = new Element("field");
		f3.setAttribute("name", "val3");
		f3.setAttribute("declaringclass", "TestObj");			
		f3.addContent(new Element("value"));
		((Element)f3.getContent(0)).setText(Long.toString(Long.MAX_VALUE));
		objElement.addContent(f3);
		
		Element f4 = new Element("field");
		f4.setAttribute("name", "val4");
		f4.setAttribute("declaringclass", "TestObj");			
		f4.addContent(new Element("value"));
		((Element)f4.getContent(0)).setText("64");
		objElement.addContent(f4);
		
		Element f5 = new Element("field");
		f5.setAttribute("name", "val5");
		f5.setAttribute("declaringclass", "TestObj");			
		f5.addContent(new Element("value"));
		((Element)f5.getContent(0)).setText("5001.312");
		objElement.addContent(f5);
		
		Element f6 = new Element("field");
		f6.setAttribute("name", "val6");
		f6.setAttribute("declaringclass", "TestObj");			
		f6.addContent(new Element("value"));
		((Element)f6.getContent(0)).setText(Double.toString(Double.MAX_VALUE));
		objElement.addContent(f6);
		
		Element f7 = new Element("field");
		f7.setAttribute("name", "val7");
		f7.setAttribute("declaringclass", "TestObj");			
		f7.addContent(new Element("value"));
		((Element)f7.getContent(0)).setText("a");
		objElement.addContent(f7);
		
		Element f8 = new Element("field");
		f8.setAttribute("name", "val8");
		f8.setAttribute("declaringclass", "TestObj");			
		f8.addContent(new Element("value"));
		((Element)f8.getContent(0)).setText("true");
		objElement.addContent(f8);
		
		Element f9 = new Element("field");
		f9.setAttribute("name", "val9");
		f9.setAttribute("declaringclass", "TestObj");			
		f9.addContent(new Element("value"));
		((Element)f9.getContent(0)).setText("Hello, world!");	
		objElement.addContent(f9);

		testObj = testDeser.deserialize(tDoc);
		
		System.out.print(testObj);
		assertEquals(((TestObj)testObj).val1, 100);
		assertEquals(((TestObj)testObj).val2, Short.MAX_VALUE);
		assertEquals(((TestObj)testObj).val3, Long.MAX_VALUE);
		assertEquals(((TestObj)testObj).val4, 64);
		assertEquals(((TestObj)testObj).val5, 5001.312, 0.001);
		assertEquals(((TestObj)testObj).val6, Double.MAX_VALUE, 1.0);
		assertEquals(((TestObj)testObj).val7, 'a');
		assertEquals(((TestObj)testObj).val8, true);
		assertTrue(((TestObj)testObj).val9.equals("Hello, world!"));
	}
	
	@Test
	public void testArray() {
		Object testObj;
		
		Document tDoc = new Document();

		Element root = new Element("serialized");
		tDoc.setRootElement(root);

		Element objElement = new Element("object");		
		root.addContent(objElement);
		
		objElement.setAttribute("class", "[I");
		objElement.setAttribute("id", Integer.toString(1));
		objElement.setAttribute("length", Integer.toString(3));
		
		Element valElem = new Element("value");
		valElem.setText(Integer.toString(55));
		objElement.addContent(valElem);
		
		valElem = new Element("value");
		valElem.setText(Integer.toString(-78451335));
		objElement.addContent(valElem);
		
		valElem = new Element("value");
		valElem.setText(Integer.toString(958145668));
		objElement.addContent(valElem);
		
		testObj = testDeser.deserialize(tDoc);
		
		assertEquals(Array.get(testObj, 0), 55);
		assertEquals(Array.get(testObj, 1), -78451335);
		assertEquals(Array.get(testObj, 2), 958145668);
		
	}
	
	@Test
	public void testReference() {
		Object testObj;
		
		Document tDoc = new Document();

		Element root = new Element("serialized");
		tDoc.setRootElement(root);

		Element objElement = new Element("object");		
		root.addContent(objElement);
		
		objElement.setAttribute("class", "RefObj");
		objElement.setAttribute("id", Integer.toString(1));
				
		Element f1 = new Element("field");
		f1.setAttribute("name", "ref");
		f1.setAttribute("declaringclass", "RefObj");			
		f1.addContent(new Element("reference"));
		((Element)f1.getContent(0)).setText(Integer.toString(2));
		objElement.addContent(f1);
		
		Element objElement2 = new Element("object");		
		root.addContent(objElement2);
		
		objElement2.setAttribute("class", "RefObj");
		objElement2.setAttribute("id", Integer.toString(2));
		Element f2 = new Element("field");
		
		f2.setAttribute("name", "ref");
		f2.setAttribute("declaringclass", "RefObj");			
		f2.addContent(new Element("reference"));
		((Element)f2.getContent(0)).setText(Integer.toString(2));
		objElement2.addContent(f2);
		
		testObj = testDeser.deserialize(tDoc);
		Object testRef = ((RefObj)testObj).ref;
		assertEquals(((RefObj)testObj).ref, testRef);
		assertEquals(((RefObj)testRef).ref, testRef);		
	}
	
	
	@Test
	public void testCollectionObj() {
		Object testObj;
		
		Document tDoc = new Document();

		Element root = new Element("serialized");
		tDoc.setRootElement(root);

		Element objElement = new Element("object");		
		root.addContent(objElement);
		
		objElement.setAttribute("class", "java.util.ArrayList");
		objElement.setAttribute("id", Integer.toString(1));
			
		Element f1 = new Element("value");
		f1.setText("This should appear first.");
		objElement.addContent(f1);
		
		Element f2 = new Element("value");
		f2.setText("This should appear second.");
		objElement.addContent(f2);
		
		Element f3 = new Element("reference");
		f3.setText(Integer.toString(2));
		objElement.addContent(f3);
		
		Element objElement2 = new Element("object");		
		root.addContent(objElement2);
		
		objElement2.setAttribute("class", "RefObj");
		objElement2.setAttribute("id", Integer.toString(2));
		Element f4 = new Element("field");
		
		f4.setAttribute("name", "ref");
		f4.setAttribute("declaringclass", "RefObj");			
		f4.addContent(new Element("reference"));
		((Element)f4.getContent(0)).setText(Integer.toString(1));
		objElement2.addContent(f4);
		
		testObj = testDeser.deserialize(tDoc);
		System.out.println(testObj);
		
		assertTrue(((ArrayList)testObj).get(0).equals("This should appear first.") );
		assertTrue(((ArrayList)testObj).get(1).equals("This should appear second.") );
		assertTrue(((ArrayList)testObj).get(2) instanceof RefObj);

	}
	
}
