import java.io.FileNotFoundException;
import java.io.IOException;

import com.parse.Parser;
import com.solver.Solver;

public class main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		//Parser parse = new Parser();
		Solver sol = new Solver("./input/busy_day.in");
		sol.Simple();
		sol.print("./output/out.txt");
		
		Solver sol1 = new Solver("./input/mother_of_all_warehouses.in");
		sol.Simple();
		sol1.print("./output/out1.txt");
		
		Solver sol2 = new Solver("./input/redundancy.in");
		sol.Simple();
		sol2.print("./output/out2.txt");
		
	}

}
