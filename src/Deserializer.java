import org.jdom2.Document;
import org.jdom2.Element;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Content;

public class Deserializer {
	HashMap<Integer, Object> deserialized;

	public Object deserialize(Document document) {
		Element root = document.getRootElement();
		Object head = null;
		Element headEle;
		Object cur;
		Class curClass;
		deserialized = new HashMap<Integer, Object>();

		if(root.getContent().size() > 0) {
			generateMap(root);
			try {
				headEle = (Element)root.getContent(0);

				head = deserialized.get(headEle.getAttributes().get(1).getIntValue());

				for(Content c: root.getContent()) {
					cur = deserialized.get((Integer.parseInt(((Element)c).getAttributeValue("id"))));

					curClass = cur.getClass();
					if(curClass.isArray())
					{
						deserializeArray(cur, (Element)c);
					}
					else if(cur instanceof Collection)
					{
						deserializeCollection((Collection)cur, (Element)c);
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
		}
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
					deserialized.put(attributes.get(1).getIntValue(), dummy);
				}
				catch(Exception e) {
					e.printStackTrace();
				}

			}
		}
	}

	private void deserializeArray(Object obj, Element objElement) {
		Class<?> arrayType = obj.getClass().getComponentType();
		List<Content> contents = objElement.getContent();
		Element e;

		for(int i = 0; i < contents.size(); i++) {
			e = (Element)contents.get(i);
			Array.set(obj, i, getObject(e, arrayType));
		}

	}

	private void deserializeCollection(Collection<Object> obj, Element objElement) {
		List<Content> contents = objElement.getContent();

		Element e;

		for(int i = 0; i < contents.size(); i++) {
			e = (Element)contents.get(i);
			if(e.getName().equals("reference"))
			{
				if(e.getContent(0).getValue().equals("null")) 
					obj.add(null);
				else
					obj.add(deserialized.get(Integer.parseInt(e.getContent(0).getValue())));

			}
			else
			{
				obj.add(e.getContent(0).getValue());
			}
		}

	}

	private void deserializeObject(Object obj, Element objElement) {
		Field f;
		Element fValue;
		String valueText;

		for(Content c: objElement.getContent())
		{
			try {
				f = Class.forName(((Element)c).getAttribute("declaringclass").getValue()).getDeclaredField(((Element)c).getAttributeValue("name"));
				f.setAccessible(true);
				fValue = ((Element)((Element)c).getContent().get(0));
				f.set(obj, getObject(fValue, f.getType()));

			} catch (Exception e) {
				e.printStackTrace();

			}
		}

	}

	private Object getObject(Element e, Class type) {
		String valueText = e.getContent().get(0).getValue();

		if(e.getName().equals("reference")) {
			if(valueText.equals("null"))
				return null;
			else
				return deserialized.get(Integer.parseInt(e.getContent(0).getValue()));

		}

		if(type == Integer.TYPE) 
			return Integer.parseInt(valueText);

		if(type == Boolean.TYPE) {
			if(valueText.contains("true"))
				return true;
			else return false;
		}

		if(type == Double.TYPE) 
			return Double.parseDouble(valueText);

		if(type == Short.TYPE) 
			return Short.parseShort(valueText);

		if(type == Long.TYPE) 
			return Long.parseLong(valueText);

		if(type == Byte.TYPE) 
			return Byte.parseByte(valueText);

		if(type == Float.TYPE) 
			return Float.parseFloat(valueText);

		if(type == Character.TYPE) 
			return valueText.charAt(0);

		return valueText;	


	}
}
