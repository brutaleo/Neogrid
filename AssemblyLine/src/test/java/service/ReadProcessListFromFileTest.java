package service;

import controller.ScheduleProcesses;
import controller.exceptions.ProcessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ReadProcessListFromFileTest {

    private final ReadProcessListFromFile readProcessListFromFileMock = mock(ReadProcessListFromFile.class);
    private final ScheduleProcesses scheduleProcesses = new ScheduleProcesses();

    @Test
    void shouldReadDataFileFromCorrectPathToInput() throws Exception {
        readProcessListFromFileMock.getProcessListFromFile("C:\\input.txt");
        verify(readProcessListFromFileMock, atLeastOnce()).getProcessListFromFile("C:\\input.txt");
    }

    @Test
    void shouldShowExceptionOfImportFileProblemWhenFileLoadFromInorrectPath() throws Throwable {
        String expectMessage = "Problem while importing the file. Check if file has data" +
                " or the file path is correct.";
        doThrow(new ProcessException(expectMessage))
                .when(readProcessListFromFileMock).getProcessListFromFile(anyString());

        Exception exception = assertThrows(ProcessException.class, () ->
                scheduleProcesses.schedule("C:\\input.txt"));
        assertEquals(expectMessage, exception.getMessage());
    }

}