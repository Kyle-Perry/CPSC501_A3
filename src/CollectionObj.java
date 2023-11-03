import java.util.ArrayList;

public class CollectionObj {
	public CollectionObj() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Object> collection;

	public CollectionObj(ArrayList<Object> collection) {
		super();
		this.collection = collection;
	}
	
	public String toString() {
		return this.collection.getClass() + "@" +this.collection.hashCode();
	}
	
}
