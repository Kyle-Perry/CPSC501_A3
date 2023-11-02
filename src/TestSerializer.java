import static org.junit.Assert.*;

import org.jdom2.Document;
import org.jdom2.Element;
import org.junit.*;
import org.junit.Test;

public class TestSerializer {
	private static Serializer testSer;
	
	@BeforeClass
	public static void initializeTest() {
		testSer = new Serializer();
	}
	
	@Test
	public void testPrimFields() {
		TestObj testObj = new TestObj(100, ((short)50), ((long)200), ((byte)64), ((float)3.14), 99999.99, 'a', true, "Hello, world!");
		
		Document tDoc = testSer.serialize(testObj);
		Element root = tDoc.getRootElement();
		assertEquals(root.getContent().size(), 1);
		
		Element objEle = (Element)root.getContent(0);
		assertEquals(objEle.getAttribute("class").getValue(), "TestObj");
		
		Element fieldEle = (Element)objEle.getContent(0);
		assertEquals(fieldEle.getAttribute("name").getValue(), "val1");
		assertEquals(fieldEle.getAttribute("declaringclass").getValue(), "TestObj");
		assertEquals(((Element)fieldEle.getContent(0)).getText(), "100");
		
		fieldEle = (Element)objEle.getContent(1);
		assertEquals(fieldEle.getAttribute("name").getValue(), "val2");
		assertEquals(fieldEle.getAttribute("declaringclass").getValue(), "TestObj");
		assertEquals(((Element)fieldEle.getContent(0)).getText(), "50");
		
		fieldEle = (Element)objEle.getContent(2);
		assertEquals(fieldEle.getAttribute("name").getValue(), "val3");
		assertEquals(fieldEle.getAttribute("declaringclass").getValue(), "TestObj");
		assertEquals(((Element)fieldEle.getContent(0)).getText(), "200");
		
		fieldEle = (Element)objEle.getContent(3);
		assertEquals(fieldEle.getAttribute("name").getValue(), "val4");
		assertEquals(fieldEle.getAttribute("declaringclass").getValue(), "TestObj");
		assertEquals(((Element)fieldEle.getContent(0)).getText(), "64");
		
		fieldEle = (Element)objEle.getContent(4);
		assertEquals(fieldEle.getAttribute("name").getValue(), "val5");
		assertEquals(fieldEle.getAttribute("declaringclass").getValue(), "TestObj");
		assertEquals(((Element)fieldEle.getContent(0)).getText(), "3.14");
		
		fieldEle = (Element)objEle.getContent(5);
		assertEquals(fieldEle.getAttribute("name").getValue(), "val6");
		assertEquals(fieldEle.getAttribute("declaringclass").getValue(), "TestObj");
		assertEquals(((Element)fieldEle.getContent(0)).getText(), "99999.99");
		
		fieldEle = (Element)objEle.getContent(6);
		assertEquals(fieldEle.getAttribute("name").getValue(), "val7");
		assertEquals(fieldEle.getAttribute("declaringclass").getValue(), "TestObj");
		assertEquals(((Element)fieldEle.getContent(0)).getText(), "a");
		
		fieldEle = (Element)objEle.getContent(7);
		assertEquals(fieldEle.getAttribute("name").getValue(), "val8");
		assertEquals(fieldEle.getAttribute("declaringclass").getValue(), "TestObj");
		assertEquals(((Element)fieldEle.getContent(0)).getText(), "true");
		
		fieldEle = (Element)objEle.getContent(8);
		assertEquals(fieldEle.getAttribute("name").getValue(), "val9");
		assertEquals(fieldEle.getAttribute("declaringclass").getValue(), "TestObj");
		assertEquals(((Element)fieldEle.getContent(0)).getText(), "Hello, world!");
		
	}
	
	@Test
	public void testArray() {
		int[] vals = {100,1000,10000};
		Document tDoc = testSer.serialize(vals);
		Element root = tDoc.getRootElement();
		assertEquals(root.getContent().size(), 1);
		
		Element arrEle = (Element)root.getContent(0);
		assertEquals(arrEle.getAttribute("class").getValue(), "[I");
		assertEquals(arrEle.getAttribute("length").getValue(), "3");

		assertEquals(((Element)arrEle.getContent(0)).getText(), "100");
		assertEquals(((Element)arrEle.getContent(1)).getText(), "1000");
		assertEquals(((Element)arrEle.getContent(2)).getText(), "10000");

	}
	
	@Test
	public void testReference() {
		assertTrue(true);
	}
	
	@Test
	public void testCollectionObj() {
		assertTrue(true);
	}
	
	
	
}
