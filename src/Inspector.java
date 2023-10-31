import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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
		Class classObj = obj.getClass();
		Field[] fields;
		Object cur;
		
		System.out.println(addTabbing() + "Class: " + classObj + "\n");
		System.out.println(addTabbing() + "Hashcode: " + obj.hashCode() + "\n");

		addressesViewed.add(obj.hashCode());

		if(classObj.isArray()) {
			for(int i = 0; i < Array.getLength(obj); i++) {
				cur = Array.get(obj, i);
				System.out.println(addTabbing() + "Element " + i + " of array: " + cur);


				if(!classObj.getComponentType().isPrimitive() && !(cur instanceof String) && cur != null) {
					System.out.println(addTabbing() + "Object reference found in field, inspecting...");
					System.out.println(addTabbing() + "----------------------------------------------------------------------");

					inspect(cur);
					System.out.println(addTabbing() + "----------------------------------------------------------------------");
				}
			}
		}
		if(obj instanceof Collection) {
			int x = 0;

			for(Object o: (Collection<Object>)obj) {
				System.out.println(addTabbing() + "Element " + x + " of collection: " + o);

				if(!o.getClass().isPrimitive() && !(o.getClass() == java.lang.String.class) && o != null) {

					System.out.println(addTabbing() + "Object reference found in field, inspecting...");
					System.out.println(addTabbing() + "----------------------------------------------------------------------");

					inspect(o);
					System.out.println(addTabbing() + "----------------------------------------------------------------------");
				}
				x++;

			}
		}
		else {
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
							System.out.println(addTabbing() + f.toString() + " = " + cur);						

							if(!f.getType().isPrimitive() && !(f.getType() == java.lang.String.class) && cur != null) {

								System.out.println(addTabbing() + "Object reference found in field, inspecting...");
								System.out.println(addTabbing() + "----------------------------------------------------------------------");

								inspect(cur);
								System.out.println(addTabbing() + "----------------------------------------------------------------------");

							}
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

		tabbing--;
		System.out.println(addTabbing() + "Inspection of " + obj.getClass()+"@"+obj.hashCode() + " complete.");

	}

	private String addTabbing() {
		String tabs= "";
		for(int i = 0; i < tabbing; i++)
			tabs += "\t";
		return tabs;
	}
}
