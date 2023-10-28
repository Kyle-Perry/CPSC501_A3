import org.jdom2.Document;
import org.jdom2.Element;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Content;

public class Deserializer {
	IdentityHashMap<String, Object> deserialized;

	public Object deserialize(Document document) {
		Element root = document.getRootElement();
		Object head;
		Element headEle;
		Object cur;
		Class curClass;
		deserialized = new IdentityHashMap<String, Object>();
		
		generateMap(root);
		System.out.println(deserialized);
		try {
			headEle = (Element)root.getContent(0);

			head = deserialized.get(headEle.getAttributes().get(1).getValue());

			for(Content c: root.getContent()) {
				cur = deserialized.get(((Element)c).getAttributes().get(1).getValue());
				curClass = cur.getClass();
				if(curClass.isArray())
				{
					deserializeArray(cur, (Element)c);
				}
				else if(cur instanceof Collection<?>)
				{
					deserializeCollection(cur, (Element)c);
				}
				else
				{
					deserializeObject(cur, (Element)c);
				}
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			head = null;
		}

		System.out.println(head);
		return head;
	}

	private void generateMap(Element root)
	{
		Object dummy;

		for(Content obj: root.getContent()) {
			if(obj instanceof Element) {
				List<Attribute> attributes = ((Element) obj).getAttributes();
				try {					
					Class<?> classObj = Class.forName(attributes.get(0).getValue());

					if(attributes.size() == 3) {
						dummy = Array.newInstance(classObj, attributes.get(2).getIntValue());
					}
					else {
						dummy = classObj.newInstance();

					}
					deserialized.put(attributes.get(1).getValue(), dummy);
				}
				catch(Exception e) {
					e.printStackTrace();
				}

			}
		}
	}

	private void deserializeArray(Object obj, Element objElement) {

		System.out.println(obj + " is an array");
	}

	private void deserializeCollection(Object obj, Element objElement) {
		System.out.println(obj + " is a collection");

	}

	private void deserializeObject(Object obj, Element objElement) {
		Class objClass = obj.getClass();
		Field f;
		Element fValue;
		String valueText;
		
		for(Content c: objElement.getContent())
		{
			try {
				f = objClass.getField(((Element)c).getAttributeValue("name"));
				f.setAccessible(true);
				fValue = ((Element)((Element)c).getContent().get(0));
				valueText = fValue.getContent().get(0).getValue();
				if(fValue.getName() == "reference") {
					System.out.println(valueText + " " + deserialized.containsKey(valueText));
					f.set(obj, deserialized.get(fValue.getText()));
					}
				else if(f.getType() == Integer.TYPE) {
					System.out.println(valueText);
					f.set(obj, Integer.parseInt(valueText));
				}
				else if(f.getType() == Boolean.TYPE) {
					System.out.println(valueText);
					if(valueText.contains("true"))
						f.set(obj, true);
					else
						f.set(obj, false);
				}
				else if(f.getType() == Double.TYPE) {
					System.out.println(valueText);
					f.set(obj, Double.parseDouble(valueText));
				}
				else if(f.getType() == Short.TYPE) {
					System.out.println(valueText);
					f.set(obj, Short.parseShort(valueText));
				}
				else if(f.getType() == Long.TYPE) {
					System.out.println(valueText);
					f.set(obj, Long.parseLong(valueText));
				}
				else if(f.getType() == Byte.TYPE) {
					System.out.println(valueText);
					f.set(obj, Byte.parseByte(valueText));
				}
				else if(f.getType() == Float.TYPE) {
					System.out.println(valueText);
					f.set(obj, Float.parseFloat(valueText));
				}
				else if(f.getType() == Character.TYPE) {
					System.out.println(valueText);
					f.set(obj, valueText.charAt(0));
				}
				else if(f.getType() == java.lang.String.class) {
					f.set(obj, valueText);
				}
			} catch (SecurityException | NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		System.out.println(obj + " is an object");

	}
}
