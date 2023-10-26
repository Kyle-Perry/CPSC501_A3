import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Serializer {
	private class Test {
		public Test(int val1, String val2, boolean val3, double val4) {
			this.val1 = val1;
			this.val2 = val2;
			this.val3 = val3;
			this.val4 = val4;
		}


		public int val1;
		public String val2;
		public boolean val3;
		public double val4;


	}

	private class Test2 extends Test{
		public int[] val5;
		public Test other;

		public Test2(int val1, String val2, boolean val3, double val4, int[] val5) {
			super(val1, val2, val3, val4);
			this.val5 = val5;
			this.other = this;
		}

	}

	public Serializer() {
		toSerialize = new ArrayList<Object>();
		serialized = new ArrayList<Integer>();
	}
	
	private ArrayList<Object> toSerialize;
	private ArrayList<Integer> serialized;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Serializer ser = new Serializer();
		Test2 a = ser.new Test2(100, "Test1", true, 3.14, new int[] {1, 2 ,3});

		Document aDoc = ser.serialize(a);
		XMLOutputter xOut = new XMLOutputter(Format.getPrettyFormat());
		try {
			//xOut.output(aDoc, System.out);	
			xOut.output(ser.serialize(new Test[] {ser.new Test(1,"aa",false,1.1), ser.new Test(2,"bbb",true,2.22),ser.new Test(3,"cccc",false, 3.333), ser.new Test(4,"ddddd",true, 4.4444)}), System.out);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public Document serialize(Object obj){
		Document myDoc = new Document(new Element("serialized"));
		toSerialize.add(obj);
		Element root = myDoc.getRootElement();
		while(!toSerialize.isEmpty()) {
			if(!serialized.contains(toSerialize.get(0).hashCode())) {
				serialized.add(toSerialize.get(0).hashCode());
				root.addContent(serializeObject(toSerialize.get(0)));
			}
			else
			{
				System.err.println("Object " + obj.getClass().getName()+ "@" + obj.hashCode() + " already serialized, skipping");
			}
			toSerialize.remove(0);
		}
		return myDoc;

	}

	public Element serializeObject(Object obj) {
		if(obj.getClass().isArray())
			return serializeArray(obj);

		Element objElement = new Element("object");
		Field[] fields = obj.getClass().getFields();
		
		objElement.setAttribute("class", obj.getClass().getName());
		objElement.setAttribute("id", obj.hashCode() + "");
		
		for(Field f: fields) {
			objElement.addContent(serializeField(obj, f));
		}
		
		return objElement;
	}

	public Element serializeArray(Object obj) {
		Element objElement = new Element("object");
		objElement.setAttribute("class", obj.getClass().getName());
		objElement.setAttribute("id", obj.hashCode() + "");
		objElement.setAttribute("length", ""+ Array.getLength(obj));

		for(int i = 0; i < Array.getLength(obj); i++) {
			
			if(!obj.getClass().getComponentType().isPrimitive() || obj.getClass().equals(String.class))
			{
				objElement.addContent(serializeRef(Array.get(obj, i)));
				toSerialize.add(Array.get(obj, i));
			}
			else
			{
				objElement.addContent(serializeValue(Array.get(obj, i)));
			}
		}
		
		return objElement;	
	}

	public Element serializeField(Object obj, Field field) {
		Element fieldElement = new Element("field");
		fieldElement.setAttribute("name",field.getName());
		fieldElement.setAttribute("declaringclass", field.getDeclaringClass().getName());
		
		try {
			field.setAccessible(true);
			if(field.getType().isPrimitive() || field.getType().equals(String.class))
				fieldElement.addContent(serializeValue(field.get(obj)));
			else
			{
				fieldElement.addContent(serializeRef(field.get(obj)));	
				toSerialize.add(field.get(obj));
			}
		}
		catch (Exception e) {
			System.err.print(e.getStackTrace());
			return null;
		}
		return fieldElement;
	}

	public Element serializeValue(Object obj) {
		Element valueElement;
	
		valueElement = new Element("value");
		valueElement.setText("" + obj);
		
		return valueElement;
	}

	public Element serializeRef(Object obj) {
		Element valueElement;
		
		valueElement = new Element("reference");
		valueElement.setText("" + obj.hashCode());
		
		return valueElement;
	}
}
