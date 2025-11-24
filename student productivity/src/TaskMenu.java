import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TaskMenu {
    private TaskManager taskManager;
    private Scanner scanner;
    private SimpleDateFormat dateFormat;
    
    public TaskMenu(TaskManager taskManager, Scanner scanner) {
        this.taskManager = taskManager;
        this.scanner = scanner;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }
    
    public void showMenu() {
        while (true) {
            System.out.println("\n--- TASK MANAGEMENT ---");
            System.out.println("1. Add New Task");
            System.out.println("2. View All Tasks");
            System.out.println("3. View Pending Tasks");
            System.out.println("4. Mark Task as Completed");
            System.out.println("5. Delete Task");
            System.out.println("6. Search Tasks");
            System.out.println("7. Back to Main Menu");
            System.out.print("Choose option: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1: addTask(); break;
                case 2: taskManager.viewAllTasks(); break;
                case 3: taskManager.viewPendingTasks(); break;
                case 4: markTaskCompleted(); break;
                case 5: deleteTask(); break;
                case 6: searchTasks(); break;
                case 7: return;
                default: System.out.println("Invalid choice!");
            }
        }
    }
    
    private void addTask() {
        System.out.println("\n--- ADD NEW TASK ---");
        System.out.print("Enter task title: ");
        String title = scanner.nextLine();
        
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();
        
        System.out.print("Enter category (e.g., Assignment, Study, Personal): ");
        String category = scanner.nextLine();
        
        System.out.print("Enter deadline (dd/mm/yyyy): ");
        String dateStr = scanner.nextLine();
        
        try {
            Date deadline = dateFormat.parse(dateStr);
            taskManager.addTask(title, description, category, deadline);
        } catch (ParseException e) {
            System.out.println("❌ Invalid date format! Please use dd/mm/yyyy");
        }
    }
    
    private void markTaskCompleted() {
        System.out.print("Enter task ID to mark as completed: ");
        int taskId = getIntInput();
        taskManager.markTaskCompleted(taskId);
    }
    
    private void deleteTask() {
        System.out.print("Enter task ID to delete: ");
        int taskId = getIntInput();
        taskManager.deleteTask(taskId);
    }
    
    private void searchTasks() {
        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine();
        taskManager.searchTasks(keyword);
    }
    
    private int getIntInput() {
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}
