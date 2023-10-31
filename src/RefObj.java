
public class RefObj {
	public RefObj() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Object ref;

	public RefObj(Object ref) {
		super();
		this.ref = ref;
	}
	
	public String toString()
	{
		if(this.ref == null)
			return null;
		return this.getClass() + "@" + this.hashCode() + ": \"" + this.ref.getClass() + "@" + this.ref.hashCode() + "\"";
	}
}
