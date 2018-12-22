import java.util.*;

public class Person {
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
		this.email = "email";
		this.name = temp[1];
		this.presenter = (temp[2].equals("Yes"));
		this.observer = (temp[6].equals("Yes"));
		if (this.presenter) {
			String[] time = temp[5].split("-");
			if (time.length == 2) {
				this.sectionTime = Integer.parseInt(time[1]
						.split(":")[0].replaceAll(" ", "")) - 1;
			} else {
				this.sectionTime = -1;
			}
			this.course = temp[3];
			this.location = temp[4];
		}
		if (this.observer) {
			collectAvail(temp);
		}
	}
	
	public void collectAvail(String[] info) {
		List<Integer> times = new ArrayList<>();
		int index = 7;
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
		return this.name + " " + this.email + " " + this.course + " " 
				+ this.location + " " + this.sectionTime;
	}

}
