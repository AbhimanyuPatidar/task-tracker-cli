# Task Tracker CLI

Task Tracker CLI is a command-line interface application designed to help you create and manage your tasks efficiently.

## Features

- Add new tasks 
- Update task's description and/or status
- List all tasks
- List tasks by status
- Delete tasks

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/AbhimanyuPatidar/task-tracker-cli.git
    ```
2. Navigate to the project directory:
    ```sh
    cd task-tracker-cli
    ```
3. Compile the project:
    ```sh
    javac -cp . -d class Main.java
    ```
    You can replace class with other names.
    
4. Run the application
    ```sh
    java -cp class Main <command> [arguments]

## Usage
```sh
1. Adding a new Task
# With only description (by default 'todo' will be applied if status is not provided or is invalid)
java -cp class Main add "Buy groceries"

# With both description and status (todo, in-progress, done)
java -cp class Main add "Buy groceries and cook dinner" "todo"

2. Updating task description and/or status
# Update only the description
java -cp class Main update 1 -d "Exercise 30 mins"

# Update only the status
java -cp class Main update 2 -s "in-progress"

# Update both description and status
java -cp class Main update 3 "Run 5 kms" "done"

3. Deleting a task
java -cp class Main delete 1

4. Listing all tasks
java -cp class Main list

5. Listing tasks according to status
java -cp class Main list "done"
```

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request.

## License

This project is licensed under the MIT License.

## Contact

For any questions or suggestions, please open an issue or contact abhimanyupatidar2511@gmail.com
