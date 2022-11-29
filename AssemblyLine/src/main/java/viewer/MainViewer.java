package viewer;

import controller.ScheduleProcesses;

final class MainViewer {
    public static void main(String[] args) {

        initialize(System.getProperty("user.dir") + "\\input.txt");
    }

    private static void initialize(String path) {
        try {
            executeFile(path);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static void executeFile(String path) {
        ScheduleProcesses scheduleProcesses = new ScheduleProcesses();
        try {
            scheduleProcesses.schedule(path);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
