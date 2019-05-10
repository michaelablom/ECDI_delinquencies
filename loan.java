package delinquency;

public class loan {
	public String ID; 
	public String RM;
	public String Fund;
	public double amount;
	public double principal_balance;
	public double del_p1;
	public double del_p2;
	public double del_p3;
	public double del_p4;
	public double del_p5;
	public double del_p6;
	public double del_p7;
	public double total_del;

	@Override
	public String toString() {
		String s = Fund +","+ID +","+ RM +","+ amount +","+principal_balance+","+del_p1+","+del_p2+","+del_p3+
				","+del_p4+","+del_p5+","+del_p6+","+del_p7+","+total_del;
		return s;
	}
}