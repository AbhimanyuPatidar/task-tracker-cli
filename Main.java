// Main class to run the CLI Application

import com.task.tracker.TaskService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Task Tracker Application running...");

        if (args.length == 0) {
            System.out.println("Please provide the required arguments to run the application.");

            System.out.println("Usage: java -cp . Main <arg1> <arg2> ... <argN>");
            System.out.println("Various options available:");
            
            System.out.println("1. To add a new task: java -cp . Main add <description> <status>");
            System.out.println("\tStatus can be one of: pending(default), in_progress, completed");

            System.out.println("2. To update a task: ");
            System.out.println("\tTo update description: java -cp . Main update <id> -d <new_description>");
            System.out.println("\tTo update status: java -cp . Main update <id> -s <new_status>");
            System.out.println("\tTo update both: java -cp . Main update <id> <new_description> <new_status>");

            System.out.println("3. To delete a task: java -cp . Main delete <id>");

            System.out.println("4. To list all tasks: java -cp . Main list");
            System.out.println("\tTo list tasks by status: java -cp . Main list <status>");
        } else {
            TaskService taskService = new TaskService();
            int status = taskService.execute(args);
            
            if (status == 0) {
                System.out.println("Command failed to execute.");
            } else {
                System.out.println("Command executed successfully.");
            }
        }
    }
}