public class Test {
	public Test(int val1, String val2, boolean val3, double val4) {
		this.val1 = val1;
		this.val2 = val2;
		this.val3 = val3;
		this.val4 = val4;
	}

	public Test() {
		
	}
	
	public int val1;
	public String val2;
	public boolean val3;
	public double val4;

	public String toString()
	{
		return val1 + " " + val2 + " " + val3 + " " + val4;
	}
}
