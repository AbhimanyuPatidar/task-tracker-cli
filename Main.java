/**
 * Main class to run the Task Tracker Application.
 * 
 * @Author: Abhimanyu Patidar
 */

import java.io.IOException;

import com.task.tracker.TaskService;
import com.task.tracker.exceptions.InvCmdPassedException;
import com.task.tracker.exceptions.InvIdFormatException;
import com.task.tracker.exceptions.InvNumOfArgsException;
import com.task.tracker.exceptions.InvUseOfOptionException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Task Tracker Application running...");

        if (args.length == 0) {
            System.out.println("Please provide the required arguments to run the application.");

            System.out.println("Usage: java -cp classes Main <arg1> <arg2> ... <argN>");
            System.out.println("Various options available:");
            
            System.out.println("1. To add a new task: java -cp classes Main add <description> <status>");
            System.out.println("\tStatus can be one of: todo(default if status argument not provided), in-progress, done");

            System.out.println("2. To update a task: ");
            System.out.println("\tTo update description: java -cp classes Main update <id> -d <new_description>");
            System.out.println("\tTo update status: java -cp classes Main update <id> -s <new_status>");
            System.out.println("\tTo update both: java -cp classes Main update <id> <new_description> <new_status>");

            System.out.println("3. To delete a task: java -cp classes Main delete <id>");

            System.out.println("4. To list all tasks: java -cp classes Main list");
            System.out.println("\tTo list tasks by status: java -cp classes Main list <status>");
            System.out.println("\tIf invalid status is provided, all tasks will be listed.");
        } else {
            TaskService taskService = new TaskService();

            try {
                taskService.execute(args);
            } catch (InvNumOfArgsException | InvUseOfOptionException | InvCmdPassedException | InvIdFormatException | IOException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}