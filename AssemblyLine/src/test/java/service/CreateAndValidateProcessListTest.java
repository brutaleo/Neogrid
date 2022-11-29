package service;

import controller.ScheduleProcesses;
import controller.exceptions.ProcessException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CreateAndValidateProcessListTest {

    final CreateAndValidateProcessList createAndValidateProcessListMock = mock(CreateAndValidateProcessList.class);
    final ReadProcessListFromFile readProcessListFromFileMock = mock(ReadProcessListFromFile.class);
    final ScheduleProcesses scheduleProcesses = new ScheduleProcesses();

    @Test
    void shouldCreateAValidScheduleProcessesList() throws Exception {
        List<String> processList = readProcessListFromFileMock.getProcessListFromFile(
                System.getProperty("user.dir") + "\\input.txt");
        createAndValidateProcessListMock.createValidProcessList(processList);
        verify(createAndValidateProcessListMock, atLeastOnce()).createValidProcessList(processList);
    }

    @Test
    void shouldShowExceptionOfEmptyDataWhenDataFileIsEmpty() throws Exception {
        String expectMessage = "Problem while importing the file. Check if file has data" +
                " or the file path is correct.";
        String path = System.getProperty("user.dir") + "\\test\\inputEmpty.txt";

        List<String> processList = readProcessListFromFileMock.getProcessListFromFile(path);

        doThrow(new ProcessException(expectMessage))
                .when(createAndValidateProcessListMock).createValidProcessList(processList);

        Exception exception = assertThrows(ProcessException.class, () ->
                scheduleProcesses.schedule(path));
        assertEquals(expectMessage, exception.getMessage());
    }

    @Test
    void shouldShowExceptionOfEmptyLineWhenDataFileHasEmptyLine() throws Exception {
        String expectMessage = "Invalid blank line on input data.";
        String path = System.getProperty("user.dir") + "\\test\\inputInvalidBlankLine.txt";

        List<String> processList = readProcessListFromFileMock.getProcessListFromFile(path);

        doThrow(new ProcessException(expectMessage))
                .when(createAndValidateProcessListMock).createValidProcessList(processList);

        Exception exception = assertThrows(ProcessException.class, () ->
                scheduleProcesses.schedule(path));
        assertEquals(expectMessage, exception.getMessage());
    }

    @Test
    void shouldShowExceptionOfProcessTimeSufixWhenDataFileHasInvalidDurationTimeSufix() throws Exception {
        String expectMessage = "Invalid Process duration of 'Monitoring subsystem assembly 30'." +
                " Duration must be in min";
        String path = System.getProperty("user.dir") + "\\test\\inputInvalidMinutesSufix.txt";

        List<String> processList = readProcessListFromFileMock.getProcessListFromFile(path);

        doThrow(new ProcessException(expectMessage))
                .when(createAndValidateProcessListMock).createValidProcessList(processList);

        Exception exception = assertThrows(ProcessException.class, () ->
                scheduleProcesses.schedule(path));
        assertEquals(expectMessage, exception.getMessage());
    }

    @Test
    void shouldShowExceptionOfProcessTimeWhenDataFileHasInvalidDurationTimeNumber() throws Exception {
        String expectMessage = "Unable parsing the duration 'AAmin' for this Process" +
                " 'Monitoring subsystem assembly AAmin'";
        String path = System.getProperty("user.dir") + "\\test\\inputInvalidDurationTime.txt";

        List<String> processList = readProcessListFromFileMock.getProcessListFromFile(path);

        doThrow(new ProcessException(expectMessage))
                .when(createAndValidateProcessListMock).createValidProcessList(processList);

        Exception exception = assertThrows(ProcessException.class, () ->
                scheduleProcesses.schedule(path));
        assertEquals(expectMessage, exception.getMessage());
    }

    @Test
    void shouldShowExceptionOfProcessDescriptionWhenDataFileHasNoDescription() throws Exception {
        String expectMessage = "Invalid Process description, '60min '";
        String path = System.getProperty("user.dir") + "\\test\\inputInvalidDescription.txt";

        List<String> processList = readProcessListFromFileMock.getProcessListFromFile(path);

        doThrow(new ProcessException(expectMessage))
                .when(createAndValidateProcessListMock).createValidProcessList(processList);

        Exception exception = assertThrows(ProcessException.class, () ->
                scheduleProcesses.schedule(path));
        assertEquals(expectMessage, exception.getMessage());
    }
}