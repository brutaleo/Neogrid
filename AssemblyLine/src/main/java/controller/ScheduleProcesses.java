package controller;

import model.Process;
import service.CreateAndValidateProcessList;
import service.ReadProcessListFromFile;
import util.SchedulingProcess;

import java.util.List;

public class ScheduleProcesses {

	private final ReadProcessListFromFile readProcessListFromFile;
	private final CreateAndValidateProcessList createAndValidateProcessList;
	private final SchedulingProcess schedulingProcess;

	public ScheduleProcesses() {
		readProcessListFromFile = new ReadProcessListFromFile();
		createAndValidateProcessList = new CreateAndValidateProcessList();
		schedulingProcess = new SchedulingProcess();
	}

	public void schedule(String fileName) throws Throwable {
		List<String> processList = readProcessListFromFile.getProcessListFromFile(fileName);
		this.scheduleAssemblyLine(processList);
	}

	private void scheduleAssemblyLine(List<String> processList) throws Exception {
		List<Process> processesList = createAndValidateProcessList.createValidProcessList(processList);
		schedulingProcess.getScheduleProcessedList(processesList);
	}

}
