import java.util.*;

public class Person implements Comparable<Person>{
	public String email;
	public String name;
	public boolean presenter;
	public boolean observer;
	public List<Integer> avail;
	public int sectionTime;
	public String course;
	public String location;
	
	public Person(String line) {
		String[] temp = line.split(",");
		// TODO: collect email
		this.email = temp[2];
		this.name = temp[1].substring(0, 1).toUpperCase() + temp[1].substring(1).toLowerCase();
		this.presenter = (temp[3].equals("Yes"));
		this.observer = (temp[7].equals("Yes"));
		if (this.presenter) {
			String[] time = temp[6].split("-");
			if (time.length == 2) {
				this.sectionTime = Integer.parseInt(time[1]
						.split(":")[0].replaceAll(" ", "")) - 1;
			} else {
				this.sectionTime = -1;
			}
			this.course = temp[4];
			this.location = temp[5];
		}
		if (this.observer) {
			collectAvail(temp);
		}
	}
	
	public void collectAvail(String[] info) {
		List<Integer> times = new ArrayList<>();
		int index = 8;
		while(index < info.length && info[index].split("-").length == 2) {
			String cur = info[index];
			String[] slot = cur.split("-");
			String endTime = slot[1].split(":")[0].replaceAll(" ", "");
			int time = Integer.parseInt(endTime) - 1;
			times.add(time);
			index++;
		}
		this.avail = times;
	}
	
	public String toString() {
		return this.name;
	}
	
	public int compareTo(Person other) {
		return this.avail.size() - other.avail.size();
	}
	
	public String getSectionTime() {
		int end = this.sectionTime + 1;
		return this.sectionTime + ":30 - " + end + ":20";
	}

}
