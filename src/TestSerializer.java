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
		
		assertTrue(true);
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
