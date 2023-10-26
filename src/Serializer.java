import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Serializer {
	public Serializer() {
		toSerialize = new ArrayList<Object>();
		serialized = new ArrayList<Integer>();
	}

	private ArrayList<Object> toSerialize;
	private ArrayList<Integer> serialized;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Serializer ser = new Serializer();
		String a = "This is a test.";
		char[] b = new char[] {'t','h','i','s',' ','s','h','o','u','l','d',' ','a','p','p','e','a','r',' ','o','n','c','e'};
		Test2 c = new Test2(100, "Test1", true, 3.14, new int[] {1, 2 ,3});
		Test d = new Test(1000, "This Test should appear once", true, 0.000000013);

		Document aDoc;
		aDoc = ser.serialize(new Object[] {d, c, d, new int[] {6, 5, 4, 3, 2, 1}, "Hello, world!", new char[][] {new char[] {'a','b','c'},new char[] {'d','e','f'},new char[] {'g','h','i'}}});
		XMLOutputter xOut = new XMLOutputter(Format.getPrettyFormat());
		try {
			xOut.output(aDoc, System.out);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public Document serialize(Object obj){
		Document myDoc = new Document(new Element("serialized"));
		if(obj != null) {
			toSerialize.add(obj);
			Element root = myDoc.getRootElement();
			while(!toSerialize.isEmpty()) {
				if(!serialized.contains(toSerialize.get(0).hashCode())) {
					serialized.add(toSerialize.get(0).hashCode());
					root.addContent(serializeObject(toSerialize.get(0)));
				}
				else
				{
					System.err.println("Object " + toSerialize.get(0).getClass().getName()+ "@" + toSerialize.get(0).hashCode() + " already serialized, skipping");
				}
				toSerialize.remove(0);
			}
		}
		return myDoc;

	}

	public Element serializeObject(Object obj) {
		Class objClass = obj.getClass();
		if(objClass.isArray())
			return serializeArray(obj);

		Element objElement = new Element("object");
		Field[] fields;

		objElement.setAttribute("class", objClass.getName());
		objElement.setAttribute("id", obj.hashCode() + "");
		while(objClass!= null)
		{
			fields = objClass.getDeclaredFields();
			for(Field f: fields) {
				objElement.addContent(serializeField(obj, f));
			}
			objClass = objClass.getSuperclass();
		}
		return objElement;
	}

	public Element serializeArray(Object obj) {
		Element objElement = new Element("object");
		objElement.setAttribute("class", obj.getClass().getName());
		objElement.setAttribute("id", obj.hashCode() + "");
		objElement.setAttribute("length", ""+ Array.getLength(obj));
		Object indexObj;

		for(int i = 0; i < Array.getLength(obj); i++) {
			indexObj = Array.get(obj, i);

			if(obj.getClass().getComponentType().isPrimitive())
			{
				objElement.addContent(serializeValue(Array.get(obj, i)));
			}
			else if(indexObj.getClass().equals(String.class))
			{
				objElement.addContent(serializeValue(Array.get(obj, i)));
			}
			else
			{

				objElement.addContent(serializeRef(Array.get(obj, i)));
				toSerialize.add(indexObj);

			}
		}

		return objElement;	
	}

	public Element serializeField(Object obj, Field field) {
		Element fieldElement = new Element("field");
		Object fieldObj;
		fieldElement.setAttribute("name",field.getName());
		fieldElement.setAttribute("declaringclass", field.getDeclaringClass().getName());

		try {
			field.setAccessible(true);
			fieldObj = field.get(obj);
			if(field.getType().isPrimitive() || field.getType().equals(String.class))
				fieldElement.addContent(serializeValue(fieldObj));
			else
			{
				fieldElement.addContent(serializeRef(fieldObj));	
				if(fieldObj != null)
					toSerialize.add(field.get(obj));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
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
