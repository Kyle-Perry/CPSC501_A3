import java.util.ArrayList;
import java.util.Scanner;

public class ObjCreator {
	private ArrayList<Object> createdObjs;
	private static final int MAX_ARRAY_LENGTH = 10;
	private CreatorUI userInterface= new CreatorUI();

	public ObjCreator() {
		super();
		createdObjs = new ArrayList<Object>();
	}

	public Object createObject() {
		int selection;

		try {
			selection = userInterface.getMenuSelection(new String[] {"Please select an object to create:",
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
			selection = userInterface.getMenuSelection(new String[] {"What would you like to refer to in this field:",
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
				userInterface.printElements(createdObjs);
				targetObj = userInterface.getInt("Please enter an integer between " + 0 + " and " + (createdObjs.size() - 1) + ": ", 0, createdObjs.size() - 1);
				return createdObjs.get(targetObj);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private PrimObj createPrimObj() {
		PrimObj created = new PrimObj(userInterface.getInt("Please enter an integer between " + Integer.MIN_VALUE + " and " + Integer.MAX_VALUE + ": ", Integer.MIN_VALUE, Integer.MAX_VALUE), 
															userInterface.getInt("Please enter an integer between " + Integer.MIN_VALUE + " and " + Integer.MAX_VALUE + ": ", Integer.MIN_VALUE, Integer.MAX_VALUE));

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

		len = userInterface.getInt("Please enter a length for the array from 0 to " + MAX_ARRAY_LENGTH + ": ", 0, MAX_ARRAY_LENGTH);

		myArr = new int[len];
		for(int i = 0; i < myArr.length; i++)
			myArr[i] = 	userInterface.getInt("Please enter an integer between " + Integer.MIN_VALUE + " and " + Integer.MAX_VALUE + ": ", Integer.MIN_VALUE, Integer.MAX_VALUE);
		created =  new PrimArrObj(myArr);
		createdObjs.add(created);

		return created;

	}

	private RefArrObj createRefArrObj() {
		RefArrObj created = new RefArrObj();

		createdObjs.add(created);
		int len;

		len = userInterface.getInt("Please enter a length for the array from 0 to " + MAX_ARRAY_LENGTH + ": ", 0, MAX_ARRAY_LENGTH);

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
				selection = userInterface.getMenuSelection(new String[] {"What object would you like to add to this collection:",
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
					userInterface.printElements(createdObjs);
					targetObj = userInterface.getInt("Please enter an integer between " + 0 + " and " + (createdObjs.size() - 1) + ": ", 0, createdObjs.size() - 1);
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