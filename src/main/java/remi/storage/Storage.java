package remi.storage;

import remi.model.Deadline;
import remi.model.Event;
import remi.model.StoredTaskList;
import remi.model.ToDo;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class for handling storing and getting saved data. This saved data is in the form of a StoredTaskList.
 */
public class Storage {
    private static final String FILE_PATH = "tasks.txt";

    private static StoredTaskList parseData(ArrayList<ArrayList<String>> data) {
        StoredTaskList res = new StoredTaskList();
        for (ArrayList<String> line: data) {
            if (line.size() < 3) {
                // early exit
                return res;
            }
            String type = line.get(0);
            boolean isDone = (line.get(1).equals("X"));
            String description = line.get(2);
            List<String> params = line.subList(3, line.size());
            switch(type) {
            case "T":
                res.addTask(new ToDo(description));
                break;
            case "E":
                if (params.size() < 2) {
                    return res;
                }
                String from = params.get(0);
                String to = params.get(1);
                res.addTask(new Event(description, from, to));
                break;
            case "D":
                if (params.isEmpty()) {
                    return res;
                }
                String by = params.get(0);
                res.addTask(new Deadline(description, by));
                break;
            default:
                return res;
            }

            if (isDone) {
                res.markTask(res.size());
            }
        }
        return res;
    }

    /**
     * Stores the string representation of the taskList in a designated file.
     */
    public static void store(StoredTaskList taskList) {
        try (FileWriter out = new FileWriter(FILE_PATH)){
            out.write(taskList.parsableString());
            out.close();
        } catch (IOException err) {
            // do nothing
            System.out.println(err.toString());
        }
    }

    /**
     * Retrieves a previously stored data file and returns its representation as a TaskList object.
     *
     * @return the stored TaskList object
     */
    public static StoredTaskList get() {
        StoredTaskList output = new StoredTaskList();
        File f = new File(FILE_PATH);
        try {
            // only creates file if it doesn't exist yet
            f.createNewFile();
        } catch (IOException err) {
            System.out.println(err.toString());
        }

        boolean corruptedFile = false;

        try (BufferedReader in = new BufferedReader(new FileReader(f))) {
            Stream<String> allLines = in.lines();
            Stream<ArrayList<String>> splitParams = allLines.map((line) ->
                    new ArrayList<>(Arrays.asList(line.split("\\|"))));
            ArrayList<ArrayList<String>> data = splitParams.collect(Collectors.toCollection(ArrayList::new));

            return parseData(data);
        } catch (IOException err) {
            System.out.println(err.toString());
        }

        return output;
    }
}
