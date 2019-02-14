package delinquency;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class get_del {

	public static void main(String[] args) {
		ArrayList<loan> loans = new ArrayList<loan>();
		//Port_RM rms = new Port_RM();
		
		loans = read("//ecdifileprint01\\redirect$\\aselengut\\Desktop/read.csv");
		
		ArrayList <loan> mloans = new ArrayList<loan>();
		
		mloans = combine(loans);
		

		String s = "";
		for(loan l : mloans) {
			s = s + l.toString() +"\n";
		}
		
		to_write("//ecdifileprint01\\redirect$\\aselengut\\Desktop/write.csv",s);
		//rms = make_rm(loans);
		//double [] x = del(rms.mymap.get("Ahmed"));
		
		ArrayList <loan> small = new ArrayList<loan>();
		for(loan l : mloans) {
			if(l.amount > 1000) {
				small.add(l);
			}
		}
		
		ArrayList <loan> onbook = new ArrayList<loan>();
		ArrayList <loan> offbook = new ArrayList<loan>();
		onbook = split(loans,"on");
		offbook = split(loans,"off");
		
		double [] y = del(onbook);
		double [] z = del(offbook);
		
		
		//System.out.println(rms.mymap.keySet().toString());
		
		//for(int i = 0; i < x.length; i++) {
		//	System.out.println(x[i]);
		//}
		
		String [] x = new String [13]; 
		x[0] = "Total Delinquent:";
		x[1] = "Under 30 days:";
		x[2] = "30 to 60:";
		x[3] = "60 to 90:";
		x[4] = "90 to 120:";
		x[5] = "120 to 150:";
		x[6] = "150 to 180:";
		x[7] = "Over 180:";
		x[8] = "Total portfolio :";
		x[9] = "Total portfolio  at risk:";
		x[10] = "Percent portfolio at risk:";
		x[11] = "Over 90 count: ";
		x[12] = "60 t0 90 count: ";		
		
		for(int i = 0; i < y.length; i++) {
			System.out.println(x[i]+" "+y[i]);
			
		}
		System.out.println("\n");
		for(int i = 0; i < y.length; i++) {
			System.out.println(x[i]+" "+z[i]);	
		}
		
	}
	
	public static ArrayList<loan> split (ArrayList<loan> loan, String onoff){
		ArrayList<loan> x = new ArrayList<loan> ();
		if(onoff.equals("on")) {
			for(loan l : loan) {
				if(!(l.Fund.equals("City Of Columbus"))&& !(l.Fund.equals("Cuyahoga County"))&&!(l.Fund.equals("Franklin County"))) {
					x.add(l);
				}
			}
		}else {
			for(loan l : loan) {
				if(l.Fund.equals("City Of Columbus")||l.Fund.equals("Cuyahoga County")||l.Fund.equals("Franklin County")) {
					x.add(l);
				}
			}
		}
		
		return x;
	}
	public static ArrayList<loan> read(String FileName){
		ArrayList<loan> loans = new ArrayList<loan>();
		
		Path PathToFile = Paths.get(FileName);
		
		try (BufferedReader br = Files.newBufferedReader(PathToFile,
                StandardCharsets.US_ASCII)){
			
			String line = br.readLine();
			
			while (line != null) {
				String[] attributes = line.split(","); 
				
				loan loan = make_loan(attributes);
				
				loans.add(loan);
				
				
				line = br.readLine();
				
			}
			
		}catch (IOException ioe) {
            ioe.printStackTrace();
        }
		
		return loans;
		
	}

	public static loan make_loan(String [] meta) {
		loan x = new loan ();
		x.Fund = meta[0];
	
		String y = meta[5].substring(0, meta[5].length()-1);
		y = y.trim();
		
		//System.out.println(meta[3]);
		//String z = meta[3].substring(2);
		
		x.RM = y;
		x.ID = meta[3];
	
		x.amount = Double.parseDouble(meta[7]);
		x.principal_balance = Double.parseDouble(meta[8]);
		x.del_p1 = Double.parseDouble(meta[9]);
		x.del_p2 = Double.parseDouble(meta[10]);
		x.del_p3 = Double.parseDouble(meta[11]);
		x.del_p4 = Double.parseDouble(meta[12]);
		x.del_p5 = Double.parseDouble(meta[13]);
		x.del_p6 = Double.parseDouble(meta[14]);
		x.del_p7 = Double.parseDouble(meta[15]);
		x.total_del = x.del_p1+x.del_p2+x.del_p3+x.del_p4+x.del_p5+x.del_p6+x.del_p7;
		
		
		return x;
	}

	public static double[] del(ArrayList <loan> loans) {
		
		double [] x = new double[13];
		
		double del = 0;
		for(loan l : loans) {
			if(l.total_del != 0) {
				del = del + l.principal_balance;
			}
		}
		int count90_up = 0;
		int count60 = 0;
		double del_180 =0;
		double del_150 = 0;
		double del_120 = 0;
		double del_90 = 0;
		double del_60 = 0;
		double del_30 = 0;
		double del_1 = 0;
		for(loan l : loans) {
			if(l.del_p7 != 0) {
				del_180 = del_180 + l.principal_balance;
				count90_up += 1;
			}else if(l.del_p6 != 0 && l.del_p7 ==0){
				del_150 = del_150 + l.principal_balance;
				count90_up += 1;
			}else if(l.del_p5 != 0 && l.del_p6 == 0) {
				del_120 = del_120 +l.principal_balance;
				count90_up += 1;
			}else if(l.del_p4 != 0 && l.del_p5 == 0) {
				del_90 = del_90 + l.principal_balance;
				count90_up += 1;
			}else if(l.del_p3 != 0 && l.del_p4 == 0) {
				del_60 = del_60 + l.principal_balance;
				count60 += 1;
			}else if(l.del_p2 != 0 && l.del_p3 == 0) {
				del_30 = del_30+ l.principal_balance;
			}else if (l.del_p1 != 0 && l.del_p2 == 0) {
				del_1 = del_1 + l.principal_balance;
			}
		}
		
		double total_amount = 0;
		for(loan l : loans) {
			total_amount += l.principal_balance;
		}
		double atrisk = del_90 + del_120+del_150+del_180;
		
		x[0] = del;
		x[1] = del_1;
		x[2] = del_30;
		x[3] = del_60;
		x[4] = del_90;
		x[5] = del_120;
		x[6] = del_150;
		x[7] = del_180;
		x[8] = total_amount;
		x[9] = atrisk;
		x[10] = atrisk/total_amount;
		x[11] = count90_up;
		x[12] = count60;
		return x;
	}

	public static Port_RM make_rm(ArrayList<loan> laons) {
		Port_RM x = new Port_RM();
		
		for(loan v : laons) {
			
			if(x.mymap.isEmpty() == true || x.mymap.containsKey(v.RM) == false) {
				ArrayList<loan> z = new ArrayList<loan>();
				z.add(v);
				x.mymap.put(v.RM, z);
			}else{
				x.mymap.get(v.RM).add(v);
			}
		}
		
		return x;
	}
	
	public static loan add(loan l1, loan l2) {
		loan l3 = new loan();
		l3.ID = l1.ID;
		l3.Fund = l1.Fund;
		l3.RM = l1.RM;
		l3.amount = l1.amount + l2.amount;
		l3.principal_balance = l1.principal_balance + l2.principal_balance;
		l3.del_p1 = l1.del_p1 + l2.del_p1;
		l3.del_p2 = l1.del_p2 + l2.del_p2;
		l3.del_p3 = l1.del_p3 + l2.del_p3;
		l3.del_p4 = l1.del_p4 + l2.del_p4;
		l3.del_p5 = l1.del_p5 + l2.del_p5;
		l3.del_p6 = l1.del_p6 + l2.del_p6;
		l3.del_p7 = l1.del_p7 + l2.del_p7;
		l3.total_del = l1.total_del + l2.total_del;
		
		return l3;
	}

	public static ArrayList<loan> combine(ArrayList<loan> x){

		ArrayList<loan> y = new ArrayList<loan> ();
		ArrayList<String> IDs = new ArrayList<String> ();
		
		for(loan l : x ) {
			if (!IDs.contains(l.ID)) {
				IDs.add(l.ID);
			}
		}
		
		for(String s : IDs) {
			loan newl = new loan();
			for(loan l : x) {
				if(l.ID.equals(s)) {
					if(newl.ID == null) {
						newl = l;
					}else {
						newl = add(newl, l);
					}			
				}
			}
			y.add(newl);
		}
		
		return y;
	}

	public static void to_write(String files, String text) {
		
		File file = new File(files);
		
		try(Writer writer = new BufferedWriter(new FileWriter(file))){
			writer.write(text);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

}

