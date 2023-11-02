public class Test2 extends Test{
	private Test other;

	public Test2(int val1, short val2, long val3, byte val4, float val5, double val6, char val7, boolean val8,
			String val9, Test other) {
		super(val1, val2, val3, val4, val5, val6, val7, val8, val9);
		this.other = other;
	}

	public Test2() {
		super();
	}
	
	public String toString()
	{
		 String s = "(" + val1 + " "  + val2 + " " + val3 + " " + val4 + " " + val5 + " ";
		 if(this.other == null)
			 s += null;
		 else
			 s += other.hashCode();
		 return s + ")";
	}
	
	public void setOther(Test other) {
		this.other = other;
	}
}