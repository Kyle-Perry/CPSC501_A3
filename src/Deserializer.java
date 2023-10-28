import org.jdom2.Document;
import org.jdom2.Element;

import java.lang.reflect.Array;
import java.util.IdentityHashMap;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Content;

public class Deserializer {
	IdentityHashMap<Integer, Object> deserialized = new IdentityHashMap<Integer, Object>();

	public Object deserialize(Document document) {
		
		Element root = document.getRootElement();
		generateMap(root);
		
		System.out.println(deserialized);
		return new Object();
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
					deserialized.put(attributes.get(1).getIntValue(), dummy);
				}
				catch(Exception e) {
					e.printStackTrace();
				}

			}
		}
	}
}
