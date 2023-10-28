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
						dummy = Array.newInstance(classObj.getComponentType(), attributes.get(2).getIntValue());
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
				f = Class.forName(((Element)c).getAttribute("declaringclass").getValue()).getDeclaredField(((Element)c).getAttributeValue("name"));
				f.setAccessible(true);
				fValue = ((Element)((Element)c).getContent().get(0));
				valueText = fValue.getContent().get(0).getValue();
				if(valueText != "null")
				{
				if(fValue.getName() == "reference") {
					for(String key: deserialized.keySet()) {
						if(key.equals(valueText))
						{
							Object keyObj = deserialized.get(key);
							f.set(obj, keyObj);
							break;
						}
					}
				}
				else if(f.getType() == Integer.TYPE) {
					f.set(obj, Integer.parseInt(valueText));
				}
				else if(f.getType() == Boolean.TYPE) {
					if(valueText.contains("true"))
						f.set(obj, true);
					else
						f.set(obj, false);
				}
				else if(f.getType() == Double.TYPE) {
					f.set(obj, Double.parseDouble(valueText));
				}
				else if(f.getType() == Short.TYPE) {
					f.set(obj, Short.parseShort(valueText));
				}
				else if(f.getType() == Long.TYPE) {
					f.set(obj, Long.parseLong(valueText));
				}
				else if(f.getType() == Byte.TYPE) {
					f.set(obj, Byte.parseByte(valueText));
				}
				else if(f.getType() == Float.TYPE) {
					f.set(obj, Float.parseFloat(valueText));
				}
				else if(f.getType() == Character.TYPE) {
					f.set(obj, valueText.charAt(0));
				}
				else if(f.getType() == java.lang.String.class) {
					f.set(obj, valueText);
				}
				}
				else
					f.set(obj, null);
			} catch (Exception e) {
				e.printStackTrace();
			
		}
		}
		System.out.println(obj + " is an object");

	}
}
