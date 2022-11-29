package util;

import model.Process;
import controller.exceptions.ProcessException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static model.enums.PeriodEnum.AFTERNOON_TIME_MINUTES;
import static model.enums.PeriodEnum.MORNING_TIME_MINUTES;

public class SchedulingProcess {

    private List<List<Process>> morningPeriodCombinations;
    private List<List<Process>> afternoonPeriodCombinations;
    private final PrintProcessList printProcessList;

    public SchedulingProcess() {
        morningPeriodCombinations = new ArrayList<>();
        afternoonPeriodCombinations = new ArrayList<>();
        printProcessList = new PrintProcessList();
    }

    public void getScheduleProcessedList(List<Process> processList) throws Exception {

        int totalProcessTime = getTotalProcessesTime(processList);
        int totalTimePerDay = totalProcessTime / 360;

        List<Process> periodsList = new ArrayList<>(processList);
        Collections.sort(periodsList);

        morningPeriodCombinations = getCombinationPeriodEvent(periodsList, totalTimePerDay, true);
        cleanMorningCombinations(periodsList);
        afternoonPeriodCombinations = getCombinationPeriodEvent(periodsList, totalTimePerDay, false);
        cleanAfternoonCombinations(periodsList);

        fillPeriodsCombinations(periodsList);

        if (!periodsList.isEmpty()) {
            throw new ProcessException("Unable to schedule the whole processes for this Assembly Line.");
        }

        printProcessList.getScheduledProcessList(morningPeriodCombinations, afternoonPeriodCombinations);
    }

    private void fillPeriodsCombinations(List<Process> periodsList) {
        if (!periodsList.isEmpty()) {
            List<Process> scheduledProcessList = new ArrayList<>();

            for (List<Process> processes : afternoonPeriodCombinations) {
                int totalTime = getTotalProcessesTime(processes);

                for (Process Process : periodsList) {
                    int processDuration = Process.getDuration();

                    if (processDuration + totalTime <= AFTERNOON_TIME_MINUTES.getMinutes()) {
                        processes.add(Process);
                        Process.setScheduled(true);
                        scheduledProcessList.add(Process);
                    }
                }

                periodsList.removeAll(scheduledProcessList);
                if (periodsList.isEmpty())
                    break;
            }
        }
    }

    private void cleanAfternoonCombinations(List<Process> processArrayList) {
        for (List<Process> processes : afternoonPeriodCombinations) {
            processArrayList.removeAll(processes);
        }
    }

    private void cleanMorningCombinations(List<Process> processArrayList) {
        for (List<Process> processes : morningPeriodCombinations) {
            processArrayList.removeAll(processes);
        }
    }

    public List<List<Process>> getCombinationPeriodEvent(List<Process> periodsList, int totalPossibleLines,
                                                         boolean morningProcesses) {
        int MORNING = MORNING_TIME_MINUTES.getMinutes();
        int AFTERNOON = AFTERNOON_TIME_MINUTES.getMinutes();

        if (morningProcesses) {
            AFTERNOON = MORNING;
        }

        int processesCount = periodsList.size();
        List<List<Process>> combinationsProcesses = new ArrayList<>();

        int combinationCount = 0;
        for (int i = 0; i < processesCount; i++) {
            int totalTime = 0;
            List<Process> combinationList = new ArrayList<>();

            totalTime = getTotalTime(periodsList, morningProcesses, MORNING, AFTERNOON, processesCount,
                    i, totalTime, combinationList);

            boolean validProcess;
            if (morningProcesses)
                validProcess = (totalTime == AFTERNOON);
            else
                validProcess = (totalTime >= MORNING && totalTime <= AFTERNOON);

            if (validProcess) {
                combinationsProcesses.add(combinationList);
                for (Process Process : combinationList) {
                    Process.setScheduled(true);
                }
                combinationCount++;
                if (combinationCount == totalPossibleLines)
                    break;
            }
        }
        return combinationsProcesses;
    }

    private int getTotalTime(List<Process> periodsList, boolean morningPeriod, int MORNING, int AFTERNOON,
                             int processesCount, int startPoint, int totalTime, List<Process> combinationList) {
        while (startPoint != processesCount) {
            int currentCount = startPoint;
            startPoint++;

            Process currentProcess = periodsList.get(currentCount);
            if (currentProcess.isScheduled())
                continue;
            int duration = currentProcess.getDuration();

            if (duration > AFTERNOON || duration + totalTime > AFTERNOON) {
                continue;
            }

            combinationList.add(currentProcess);
            totalTime += duration;

            if (morningPeriod) {
                if (totalTime == AFTERNOON)
                    break;
            } else if (totalTime >= MORNING)
                break;
        }
        return totalTime;
    }

    public static int getTotalProcessesTime(List<Process> processList) {
        if (processList == null || processList.isEmpty())
            return 0;

        int totalTime = 0;
        for (Process Process : processList) {
            totalTime += Process.getDuration();
        }
        return totalTime;
    }

}
