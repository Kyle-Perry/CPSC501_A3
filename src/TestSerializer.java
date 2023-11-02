import static org.junit.Assert.*;

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
		
		System.out.println(testObj);
		assertTrue(true);
	}
	
	@Test
	public void testPrimArrObj() {
		assertTrue(true);
	}
	
	@Test
	public void testRefObj() {
		assertTrue(true);
	}
	
	@Test
	public void testRefArrObj() {
		assertTrue(true);
	}
	
	@Test
	public void testCollectionObj() {
		assertTrue(true);
	}
	
	
	
}
