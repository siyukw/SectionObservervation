import java.util.*;
import java.io.*;

public class Main {
	public static void main(String args[]) throws IOException {
		List<Person> lines = readCSV("src/input.csv");
		for (Person p : lines) {
			if (p.observer && p.presenter) {
				System.out.println(p.toString());
			}
		}
	}
	
	public static List<Person> readCSV(String file) throws IOException {
	    FileReader fileReader = new FileReader(file);
	    BufferedReader bufferedReader = new BufferedReader(fileReader);
	    String line = bufferedReader.readLine();
	    List<Person> output = new ArrayList<>();
	    while ((line = bufferedReader.readLine()) != null) {
	        	Person cur = new Person(line);
	        	output.add(cur);
	    }
	    bufferedReader.close();
	    return output;

	}
}
