import java.io.FileNotFoundException;
import java.io.IOException;

import com.parse.Parser;
import com.solver.Solver;

public class main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		
		Solver sol = new Solver("./input/busy_day.in");
		//sol.Simple();
		sol.multiple();
		sol.print("./output/out.txt");
		
		System.out.println("Sol1: done");
		
		
		/*
		Solver sol1 = new Solver("./input/mother_of_all_warehouses.in");
		sol1.Simple();
		sol1.print("./output/out1.txt");
		
		System.out.println("Sol2: done");
		
		
		Solver sol2 = new Solver("./input/redundancy.in");
		sol2.Simple();
		sol2.print("./output/out2.txt");
		
		System.out.println("Sol3: done");*/
	}
	

}
