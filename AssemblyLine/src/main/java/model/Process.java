package model;

public class Process implements Comparable<Object> {
	private final String description;
	private final int duration;

	public String scheduledTime;
	public boolean scheduled = false;

	public Process(String description, int duration) {
		this.description = description;
		this.duration = duration;
	}

	@Override
	public int compareTo(Object obj) {
		Process process = (Process) obj;
		return Integer.compare(process.duration, this.duration);
	}

	public void setScheduled(boolean scheduled) {
		this.scheduled = scheduled;
	}

	public boolean isScheduled() {
		return scheduled;
	}

	public void setSchedulingTime(String scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public int getDuration() {
		return duration;
	}

	public String getDescription() {
		return description;
	}
}
