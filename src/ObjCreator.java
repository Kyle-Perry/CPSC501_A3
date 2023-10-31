import java.util.ArrayList;
import java.util.Scanner;

public class ObjCreator {
	ArrayList<Object> createdObjs;
	Scanner userIn;

	public ObjCreator() {
		super();
		createdObjs = new ArrayList<Object>();
		userIn = new Scanner(System.in);
	}

	public Object createObject() {
		Object newObject = null;
		int selection;

		try {
			selection = getMenuSelection(new String[] {"Please select an object to create:",
					"0) An object containing primitive fields",
					"1) An object containing an object reference",
					"2) An object containing a array of primitives",
					"3) An object containing a array of object references",
			"4) An object containing a collection of objects"}, 5);
			switch(selection) {
			case 0:
				newObject = createPrimObj();
				break;
			case 1:
				newObject = createRefObj();
				break;
			case 2:
				newObject = createPrimArrObj();
				break;
			case 3:
				newObject = createRefArrObj();
				break;
			case 4:
				newObject = createCollectionObj();
				break;
			default:
				break;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return newObject;
	}

	private Object createSubObject() {
		Object newObject = null;
		int selection;
		try {
			selection = getMenuSelection(new String[] {"What would you like to refer to in this field:",
					"0) A new object containing primitive fields",
					"1) A new object containing an object reference",
					"2) A new object containing a array of primitives",
					"3) A new object containing a array of object references",
					"4) A new object containing a collection of objects",
			"5) A reference to a previously created object"}, 6);
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return newObject;
	}

	private PrimObj createPrimObj() {
		int val1 = 0, val2 = 0;

		return new PrimObj(getInt(Integer.MIN_VALUE, Integer.MAX_VALUE), getInt(Integer.MIN_VALUE, Integer.MAX_VALUE));
	}

	private RefObj createRefObj() {
		return null;
	}

	private PrimArrObj createPrimArrObj() {
		return null;
	}

	private RefArrObj createRefArrObj() {
		return null;
	}

	private CollectionObj createCollectionObj() {
		return null;
	}

	private Object getObjectRef(int index) throws IndexOutOfBoundsException{
		if((index >= 0) && (index < createdObjs.size()))
			return createdObjs.get(index);
		throw new IndexOutOfBoundsException(index);
	}

	private int getMenuSelection(String[] menu, int max) {
		int selection = -1;

		for(String line: menu) {
			System.out.println(line);
		}


		while(true) {
			try {
				System.out.print("Please make a selection: ");

				selection = userIn.nextInt();
				if(selection < max && selection >= 0)
					return selection;
				throw new IndexOutOfBoundsException("Invalid choice, please select a choice between 0 and " + max);
			}
			catch(Exception e)
			{
				e.getMessage();
			}
		}
	}

	private int getInt(int min, int max) {
		int val = 0;

		while(true) {
			try {
				System.out.print("Please enter an integer between " + min + " and " + max + ": ");

				val = userIn.nextInt();
				if(val <= max && val >= min)
					return val;
				throw new IndexOutOfBoundsException("Integer out of range");
			}
			catch(Exception e)
			{
				e.getMessage();
			}
		}
	}
}