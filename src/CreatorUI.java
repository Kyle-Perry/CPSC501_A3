import java.util.ArrayList;
import java.util.Scanner;

public class CreatorUI {
	private Scanner userIn;

	public CreatorUI() {
		super();
		userIn = new Scanner(System.in);
	}

	public int getMenuSelection(String[] menu, int max) {
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

	public int getInt(String message, int min, int max) {
		int val = 0;

		while(true) {
			try {
				System.out.print(message);

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

	public void printElements(ArrayList<Object> objects) {
		System.out.println("Previously created objects:");
		for(Object o: objects)
			System.out.println(objects.indexOf(o) + ") " + o);
	}
}
