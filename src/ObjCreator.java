import java.util.ArrayList;
import java.util.Scanner;

public class ObjCreator {
	private ArrayList<Object> createdObjs;
	private Scanner userIn;
	private static final int MAX_ARRAY_LENGTH = 10;

	public ObjCreator() {
		super();
		createdObjs = new ArrayList<Object>();
		userIn = new Scanner(System.in);
	}

	public Object createObject() {
		int selection;

		try {
			selection = getMenuSelection(new String[] {"Please select an object to create:",
					"0) An object containing primitive fields",
					"1) An object containing an object reference",
					"2) An object containing a array of primitives",
					"3) An object containing a array of object references",
			"4) An object containing a collection of objects"}, 4);
			return getNewObject(selection);
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private Object createSubObject() {
		int selection;
		int targetObj;

		try {
			selection = getMenuSelection(new String[] {"What would you like to refer to in this field:",
					"0) A new object containing primitive fields",
					"1) A new object containing an object reference",
					"2) A new object containing a array of primitives",
					"3) A new object containing a array of object references",
					"4) A new object containing a collection of objects",
			"5) A reference to a previously created object"}, 5);
			if((selection < 5) ) {
				return getNewObject(selection);
			}
			else if(selection == 5) {
				printElements();
				targetObj = getInt(0, createdObjs.size() - 1);
				return createdObjs.get(targetObj);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private PrimObj createPrimObj() {
		PrimObj created = new PrimObj(getInt(Integer.MIN_VALUE, Integer.MAX_VALUE), getInt(Integer.MIN_VALUE, Integer.MAX_VALUE));

		createdObjs.add(created);
		return created;
	}

	private RefObj createRefObj() {
		RefObj created = new RefObj();

		createdObjs.add(created);
		created.ref = createSubObject();
		return created;
	}

	private PrimArrObj createPrimArrObj() {
		PrimArrObj created;
		int[] myArr;
		int len;

		System.out.println("Please enter a length for the array from 1 to " + MAX_ARRAY_LENGTH);
		len = getInt(0, MAX_ARRAY_LENGTH);

		myArr = new int[len];
		for(int i = 0; i < myArr.length; i++)
			myArr[i] = 	getInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
		created =  new PrimArrObj(myArr);
		createdObjs.add(created);

		return created;

	}

	private RefArrObj createRefArrObj() {
		RefArrObj created = new RefArrObj();

		createdObjs.add(created);
		int len;

		System.out.println("Please enter a length for the array from 1 to " + MAX_ARRAY_LENGTH);
		len = getInt(0, MAX_ARRAY_LENGTH);

		created.arrObjects = new Object[len];
		for(int i = 0; i < created.arrObjects.length; i++)
			created.arrObjects[i] = createSubObject();

		return created;
	}

	private CollectionObj createCollectionObj() {
		ArrayList<Object> objects = new ArrayList<Object>();
		Object newObject = null;
		CollectionObj cObj = new CollectionObj(objects);
		createdObjs.add(cObj);
		int selection;
		int targetObj;
		while(true) {
			try {
				selection = getMenuSelection(new String[] {"What object would you like to add to this collection:",
						"0) A new object containing primitive fields",
						"1) A new object containing an object reference",
						"2) A new object containing a array of primitives",
						"3) A new object containing a array of object references",
						"4) A new object containing a collection of objects",
						"5) A reference to a previously created object",
				"6) Quit adding objects"}, 6);
				if((selection < 5) ) {
					newObject = getNewObject(selection);
					objects.add(newObject);
				}
				else if(selection == 5) {
					printElements();
					targetObj = getInt(0, createdObjs.size() - 1);
					newObject = createdObjs.get(targetObj);
					objects.add(newObject);
				}
				else if(selection == 6) 
					return cObj;
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
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
				if(selection <= max && selection >= 0) {
					System.out.println();
					return selection;
				}
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
				if(val <= max && val >= min) {
					System.out.println();
					return val;
				}
				throw new IndexOutOfBoundsException("Integer out of range");
			}
			catch(Exception e)
			{
				e.getMessage();
			}
		}
	}

	private void printElements() {
		System.out.println("Previously created objects:");
		for(Object o: createdObjs)
			System.out.println(createdObjs.indexOf(o) + ") " + o);
	}


	private Object getNewObject(int selection) {
		switch(selection) {
		case 0:
			return createPrimObj();
		case 1:
			return createRefObj();
		case 2:
			return createPrimArrObj();
		case 3:
			return createRefArrObj();
		case 4:
			return createCollectionObj();
		default:
			return null;
		}
	}
}