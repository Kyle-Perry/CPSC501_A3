import java.util.ArrayList;

public class Inspector {
	
	public static void inspect(Object obj) {
		ArrayList<Integer> addressesViewed = new ArrayList();
		
		addressesViewed.add(obj.hashCode());
	}
}
