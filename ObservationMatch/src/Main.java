import java.util.*;
import java.io.*;

public class Main {
	public static List<Person> people;
	public static List<Person> unassignedObservers = new ArrayList<>();
	public static Map<Person, List<Person>> presenterToObserver  = new HashMap<>();
	public static Map<Integer, List<Person>> timeToPresenter;
	public static Map<Integer, List<Person>> timeToObserver;
	
	public static void main(String args[]) throws IOException {
		readCSV("src/input.csv");
		fillMaps();
		compute();
		printResult();
	}
	
	
	// read data in from csv and return a list of Person objects
	public static void readCSV(String file) throws IOException {
	    FileReader fileReader = new FileReader(file);
	    BufferedReader bufferedReader = new BufferedReader(fileReader);
	    String line = bufferedReader.readLine();
	    List<Person> output = new ArrayList<>();
	    while ((line = bufferedReader.readLine()) != null) {
	        	Person cur = new Person(line);
	        	output.add(cur);
	    }
	    bufferedReader.close();
	    people = output;
	}
	
	// fill out timeToPresenter and timeToObserver
	public static void fillMaps() {
		timeToPresenter = new HashMap<>();
		timeToObserver = new HashMap<>();
		for (Person p : people) {
			if (p.presenter) {
				if (timeToPresenter.containsKey(p.sectionTime)) {
					timeToPresenter.get(p.sectionTime).add(p);
				} else {
					List<Person> temp = new ArrayList<>();
					temp.add(p);
					timeToPresenter.put(p.sectionTime, temp);
				}
			}
			if (p.observer) {
				for (int time : p.avail) {
					if (timeToObserver.containsKey(time)) {
						timeToObserver.get(time).add(p);
					} else {
						List<Person> temp = new ArrayList<>();
						temp.add(p);
						timeToObserver.put(time, temp);
					}	
				}
			}
		}
	}
	
	public static void compute() {
		List<Person> observers = new ArrayList<>();
		for (Person p : people) {
			if (p.observer) {
				observers.add(p);
			}
		}
		Collections.sort(observers);
		while (!observers.isEmpty()) {
			Person observer = observers.get(0);
			List<Person> presenters = new ArrayList<>();
			for (int time : observer.avail) {
				if (timeToPresenter.containsKey(time)) {
					presenters.addAll(timeToPresenter.get(time));
				}
			}
			
			if (presenters.isEmpty()) {
				unassignedObservers.add(observer);
			} else {
				// sort presenter by number of current observers
				Collections.sort(presenters, new Comparator<Person>() {
					public int compare(Person left, Person right) {
						if (presenterToObserver.get(left) == null) {
							return -1;
						}
						if (presenterToObserver.get(right) == null) {
							return 1;
						}
						return presenterToObserver.get(left).size() - presenterToObserver.get(right).size();
					}
				});
				if (!presenterToObserver.containsKey(presenters.get(0))) {
					List<Person> temp = new ArrayList<>();
					presenterToObserver.put(presenters.get(0), temp);
				}
				presenterToObserver.get(presenters.get(0)).add(observer);
			}
			observers.remove(observer);
		}
	}
	
	public static void printResult() {
		System.out.println("Unassigned observers: ");
		System.out.println(unassignedObservers);
		System.out.println("Assigned observers: ");
		for (Person presenter : presenterToObserver.keySet()) {
			System.out.println(presenterToObserver.get(presenter) + " will observe " 
					+ presenter.name + "\'s CSE" + presenter.course +" in room " + presenter.location
					+ " on Thursday " + presenter.getSectionTime());
		}
	}
	
}

