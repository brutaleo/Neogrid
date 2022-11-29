package util;

import model.Process;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PrintProcessList {

	private final List<List<Process>> processesList;
	@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
	private final List<String> finalList;

	public PrintProcessList() {
		processesList = new ArrayList<>();
		finalList = new ArrayList<>();
	}
	
	public void getScheduledProcessList(List<List<Process>> morningProcesses,
                                        List<List<Process>> afternoonProcesses) {
		int totalDays = morningProcesses.size();

		for (int i = 0; i < totalDays; i++) {
			List<Process> processes = new ArrayList<>();

			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm ");

			calendar.set(Calendar.HOUR_OF_DAY, 9);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			Date date = calendar.getTime();

			int assemblyLineCount = i + 1;
			String scheduledTime = dateFormat.format(date);

			printAssemblyLines(assemblyLineCount);

			List<Process> morningProcessesList = morningProcesses.get(i);
			scheduledTime = printMorningList(processes, date, scheduledTime, morningProcessesList);

			int lunchTimeInterval = printLunchInterval(processes, scheduledTime);
			scheduledTime = getNextScheduledTime(date, lunchTimeInterval);

			List<Process> afternoonProcessesList = afternoonProcesses.get(i);
			scheduledTime = printAfternoonList(processes, date, scheduledTime, afternoonProcessesList);

			printGymnasticsActivities(processes, scheduledTime);

			processesList.add(processes);
		}
    }

	private void printAssemblyLines(int assemblyLineCount) {
		System.out.println("Linha de montagem " + assemblyLineCount + ":");
		finalList.add("\n Linha de montagem " + assemblyLineCount + ": \n");
	}

	private String printMorningList(List<Process> processList, Date date, String scheduledTime, List<Process> morningProcessesList) {
		for (Process Process : morningProcessesList) {
			Process.setSchedulingTime(scheduledTime);

			System.out.println(scheduledTime + Process.getDescription());
			finalList.add(scheduledTime + Process.getDescription() + "\n");

			scheduledTime = getNextScheduledTime(date, Process.getDuration());
			processList.add(Process);
		}
		return scheduledTime;
	}

	private String printAfternoonList(List<Process> processList, Date date, String scheduledTime, List<Process> afternoonProcessesList) {
		for (Process Process : afternoonProcessesList) {
			Process.setSchedulingTime(scheduledTime);
			processList.add(Process);
			System.out.println(scheduledTime + Process.getDescription());
			finalList.add(scheduledTime + Process.getDescription() + "\n");

			scheduledTime = getNextScheduledTime(date, Process.getDuration());
		}
		return scheduledTime;
	}

	private int printLunchInterval(List<Process> processList, String scheduledTime) {
		int lunchTimeInterval = 60;
		Process lunchProcess = new Process("Almoço",  60);
		lunchProcess.setSchedulingTime(scheduledTime);
		processList.add(lunchProcess);

		System.out.println(scheduledTime + "Almoço");
		finalList.add(scheduledTime + "Almoço \n");
		return lunchTimeInterval;
	}

	private void printGymnasticsActivities(List<Process> processes, String scheduledTime) {
		Process gymnasticsActivities = new Process("Ginástica Laboral",  60);
		gymnasticsActivities.setSchedulingTime(scheduledTime);
		processes.add(gymnasticsActivities);
		System.out.println(scheduledTime + "Ginástica Laboral \n");
		finalList.add(scheduledTime + "Ginástica Laboral \n\n");
	}

	public String getNextScheduledTime(Date date, int timeDuration) {
		long timeInLong = date.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm ");

		long timeDurationInLong = (long) timeDuration * 60 * 1000;
		long newTimeInLong = timeInLong + timeDurationInLong;

		date.setTime(newTimeInLong);
		return dateFormat.format(date);
	}
}
