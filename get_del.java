package delinquency;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class get_del {

	public static void main(String[] args) {
		ArrayList<loan> loans = new ArrayList<loan>();
		
		loans = read("/Users/Alex/Desktop/to_read_prot.csv");
		
		double [] x = del(loans);
		
		for(int i = 0; i < x.length; i++) {
			System.out.println(x[i]);
		}
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
		
		String y = meta[5].substring(0, meta[5].length()-1);
		y = y.trim();
		
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
		
		double [] x = new double[] {0,0,0,0,0,0,0,0};
	
		double del = 0;
		for(loan l : loans) {
			if(l.total_del != 0) {
				del = del + l.principal_balance;
			}
		}
		
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
			}else if(l.del_p6 != 0 && l.del_p7 ==0){
				del_150 = del_150 + l.principal_balance;
			}else if(l.del_p5 != 0 && l.del_p6 == 0) {
				del_120 = del_120 +l.principal_balance;
			}else if(l.del_p4 != 0 && l.del_p5 == 0) {
				del_90 = del_90 + l.principal_balance;
			}else if(l.del_p3 != 0 && l.del_p4 == 0) {
				del_60 = del_60 + l.principal_balance;
			}else if(l.del_p2 != 0 && l.del_p3 == 0) {
				del_30 = del_30+ l.principal_balance;
			}else if (l.del_p1 != 0 && l.del_p2 == 0) {
				del_1 = del_1 + l.principal_balance;
			}
		}
		
		x[0] = del;
		x[1] = del_1;
		x[2] = del_30;
		x[3] = del_60;
		x[4] = del_90;
		x[5] = del_120;
		x[6] = del_150;
		x[7] = del_180;
		
		return x;
	}

}
