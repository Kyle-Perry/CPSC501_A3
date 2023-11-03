import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

public class Inspector {
	ArrayList<Integer> addressesViewed;
	private int tabbing;

	public Inspector() {
		addressesViewed = new ArrayList<Integer>();
		tabbing = 0;

	}

	public void inspect(Object obj) {
		if(obj == null) {
			return;
		}

		if(addressesViewed.contains(obj.hashCode())) {
			System.out.println(addTabbing() + "Inspection of " + obj.getClass()+"@"+obj.hashCode() + " previously done, skipping.");
			return;	
		}

		tabbing++;
		Class<?> classObj = obj.getClass();

		System.out.println(addTabbing() + "Class: " + classObj + "\n");
		System.out.println(addTabbing() + "Hashcode: " + obj.hashCode() + "\n");

		addressesViewed.add(obj.hashCode());

		if(classObj.isArray()) 
			inspectArray(obj, classObj);

		else if(obj instanceof Collection) 
			inspectCollection(obj);

		else 
			inspectObject(obj, classObj);

		tabbing--;
		System.out.println(addTabbing() + "Inspection of " + obj.getClass()+"@"+obj.hashCode() + " complete.");

	}

	private void inspectObject(Object obj, Class<?> classObj){
		Field[] fields;
		Object cur;

		while(classObj != null) {

			if(classObj == String.class)
			{
				System.out.println(addTabbing() + "String value = " + obj);
			}
			else {
				fields = classObj.getDeclaredFields();
				if(fields.length > 0)
					System.out.println(addTabbing() + "Declared Fields:");

				for(Field f: fields) {
					try {
						f.setAccessible(true);
						cur = f.get(obj);

						if(!f.getType().isPrimitive() && !(f.getType() == java.lang.String.class) && cur != null) {
							System.out.println(addTabbing() + f.toString() + " = " + cur.getClass()+"@"+cur.hashCode());						
							beginSubInspection(cur);
						}
						else
							System.out.println(addTabbing() + f.toString() + " = " + cur);						



					}
					catch(Exception e) {
						e.printStackTrace();
					}

				}

			}

			classObj = classObj.getSuperclass();
			if(classObj != null)
				System.out.println("\n" + addTabbing() + "Superclass:");
		}
	}

	private void inspectArray(Object obj, Class<?> classObj) {
		Object cur;
		for(int i = 0; i < Array.getLength(obj); i++) {
			cur = Array.get(obj, i);
			System.out.println(addTabbing() + "Element " + i + " of array: " + cur);


			if(!classObj.getComponentType().isPrimitive() && !(cur instanceof String) && cur != null) {
				beginSubInspection(cur);

			}
		}
	}

	private void inspectCollection(Object obj){
		int x = 0;

		for(Object o: (Collection<Object>)obj) {
			System.out.println(addTabbing() + "Element " + x + " of collection: " + o);

			if(!o.getClass().isPrimitive() && !(o.getClass() == java.lang.String.class) && o != null) {
				beginSubInspection(o);

			}
			x++;

		}
	}

	private String addTabbing() {
		String tabs= "";
		for(int i = 0; i < tabbing; i++)
			tabs += "\t";
		return tabs;
	}

	public void beginSubInspection(Object cur) {
		System.out.println(addTabbing() + "Object reference found in field, inspecting...");
		System.out.println(addTabbing() + "----------------------------------------------------------------------");

		inspect(cur);
		System.out.println(addTabbing() + "----------------------------------------------------------------------");
	}
}
