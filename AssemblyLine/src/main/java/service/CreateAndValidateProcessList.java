package service;

import java.util.ArrayList;
import java.util.List;

import controller.exceptions.ProcessException;
import model.Process;

public class CreateAndValidateProcessList {

    public List<Process> createValidProcessList(List<String> processList) throws Exception {

        List<Process> validatedProcessList = new ArrayList<>();

        @SuppressWarnings("unused")
        int processCount = -1;
        String minuteSuffix = "min";
        String maintenanceSuffix = "maintenance";

        for (String process : processList) {
            int lastSpaceIndex = process.lastIndexOf(" ");

            validateEmptyLine(lastSpaceIndex);

            String description = process.substring(0, lastSpaceIndex);
            String duration = process.substring(lastSpaceIndex + 1);

            validateProcessDescription(process, description);
            validateProcessTimeSufix(minuteSuffix, maintenanceSuffix, process, duration);

            processCount++;

            int durationInMinutes = 0;
            durationInMinutes = formatTime(minuteSuffix, maintenanceSuffix, process, duration, durationInMinutes);
            validatedProcessList.add(new Process(process, durationInMinutes));
        }
        return validatedProcessList;
    }

    private int formatTime(String minuteSuffix, String maintenanceSuffix, String Process, String duration, int time)
            throws Exception {
        try {
            if (duration.endsWith(minuteSuffix)) {
                time = Integer.parseInt(duration.substring(0, duration.indexOf(minuteSuffix)));
            } else if (duration.endsWith(maintenanceSuffix)) {
                String maintenanceTime = duration.substring(0, duration.indexOf(maintenanceSuffix));
                if ("".equals(maintenanceTime))
                    time = 5;
                else
                    time = Integer.parseInt(maintenanceTime) * 5;
            }
        } catch (NumberFormatException e) {
            validateProcessTime(Process, duration);
        }
        return time;
    }

    private void validateProcessTime(String process, String duration) throws ProcessException {
        throw new ProcessException("Unable parsing the duration " + "'" + duration + "'" + " for this Process "
                + "'" + process + "'");
    }

    private void validateEmptyLine(int lastSpaceIndex) throws ProcessException {
        if (lastSpaceIndex == -1)
            throw new ProcessException("Invalid blank line on input data.");
    }

    private void validateProcessTimeSufix(String minuteSuffix, String maintenanceSuffix, String process, String duration)
            throws ProcessException {
        if (!duration.endsWith(minuteSuffix) && !duration.endsWith(maintenanceSuffix))
            throw new ProcessException("Invalid Process duration of " + "'" + process + "'"
                    + ". Duration must be in min");
    }

    private void validateProcessDescription(String process, String description) throws ProcessException {
        boolean isFirstCharacterANumber = description.matches("^[0-9].*$");
        if (isFirstCharacterANumber)
            throw new ProcessException("Invalid Process description, " + "'" + process + "'");
    }

}
