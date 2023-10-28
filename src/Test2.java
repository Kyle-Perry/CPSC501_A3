public class Test2 extends Test{
	public int[] val5;
	private Test other;

	public Test2(int val1, String val2, boolean val3, double val4, int[] val5) {
		super(val1, val2, val3, val4);
		this.val5 = val5;
		this.other = this;
	}

	public Test2() {
		super();
	}
	
	public String toString()
	{
		 String s = val1 + " "  + val2 + " " + val3 + " " + val4 + " " + val5 + " ";
		 if(this.other == null)
			 s += null;
		 else
			 s += other.hashCode();
		 return s;
	}
}