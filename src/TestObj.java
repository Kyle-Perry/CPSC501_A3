public class TestObj {
	public TestObj(int val1, short val2, long val3, byte val4, float val5, double val6, char val7, boolean val8,
			String val9) {
		super();
		this.val1 = val1;
		this.val2 = val2;
		this.val3 = val3;
		this.val4 = val4;
		this.val5 = val5;
		this.val6 = val6;
		this.val7 = val7;
		this.val8 = val8;
		this.val9 = val9;
	}

	public TestObj() {
		super();
	}
	
	public int val1;
	public short val2;
	public long val3;
	public byte val4;
	public float val5;
	public double val6;
	public char val7;
	public boolean val8;
	public String val9;

	public String toString()
	{
		return val1 + " " + val2 + " " + val3 + " " + val4 + " " + val5 + " " + val6 + " " + val7 + " " + val8 + " " + val9;
	}
}
