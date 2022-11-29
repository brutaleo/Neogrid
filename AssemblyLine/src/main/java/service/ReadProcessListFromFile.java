package service;

import controller.exceptions.ProcessException;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReadProcessListFromFile {

    private List<String> processList;

    public List<String> getProcessListFromFile(String fileName) throws Exception {
        processList = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);

            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));

            int verifyFileIntegrity = bufferedReader.read();
            validateFileIntegrity(bufferedReader, verifyFileIntegrity);

            String stringLines = bufferedReader.readLine();
            addLines(bufferedReader, stringLines);
            dataInputStream.close();

        } catch (Exception e) {
            validateImportFile();
        }
        return processList;
    }

    private void validateFileIntegrity(BufferedReader bufferedReader, int verifyFileIntegrity) throws Exception {
        if (verifyFileIntegrity == -1) {
            bufferedReader.close();
        }
    }

    private void validateImportFile() throws ProcessException {
        throw new ProcessException("Problem while importing the file. Check if file has data" +
                " or the file path is correct.");
    }

    private void addLines(BufferedReader bufferedReader, String stringLines) throws Exception {
        while (stringLines != null) {
            processList.add(stringLines);
            stringLines = bufferedReader.readLine();
        }
    }

}
