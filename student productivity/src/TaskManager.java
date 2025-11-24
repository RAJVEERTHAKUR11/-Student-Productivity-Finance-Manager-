import java.util.*;
import java.util.stream.Collectors;

public class TaskManager {
    private List<Task> tasks;
    private FileHandler fileHandler;
    private int nextId;
    
    public TaskManager() {
        this.fileHandler = new FileHandler();
        this.tasks = new ArrayList<>();
        this.nextId = 1;
        loadTasks();
    }
    
    private void loadTasks() {
        List<String> taskData = fileHandler.readData("tasks.dat");
        for (String line : taskData) {
            Task task = Task.fromFileString(line);
            if (task != null) {
                tasks.add(task);
                if (task.getId() >= nextId) {
                    nextId = task.getId() + 1;
                }
            }
        }
    }
    
    private void saveTasks() {
        List<String> taskData = tasks.stream()
                .map(Task::toFileString)
                .collect(Collectors.toList());
        fileHandler.writeData("tasks.dat", taskData);
    }
    
    public void addTask(String title, String description, String category, Date deadline) {
        Task task = new Task(nextId++, title, description, category, deadline);
        tasks.add(task);
        saveTasks();
        System.out.println("✅ Task added successfully! (ID: " + task.getId() + ")");
    }
    
    public void viewAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        
        System.out.println("\n=== ALL TASKS ===");
        tasks.stream()
                .sorted(Comparator.comparingInt(Task::getPriority).reversed())
                .forEach(System.out::println);
    }
    
    public void viewPendingTasks() {
        List<Task> pendingTasks = tasks.stream()
                .filter(task -> !task.isCompleted())
                .sorted(Comparator.comparingInt(Task::getPriority).reversed())
                .collect(Collectors.toList());
                
        if (pendingTasks.isEmpty()) {
            System.out.println("No pending tasks. Great job! 🎉");
            return;
        }
        
        System.out.println("\n=== PENDING TASKS ===");
        pendingTasks.forEach(System.out::println);
    }
    
    public void markTaskCompleted(int taskId) {
        for (Task task : tasks) {
            if (task.getId() == taskId) {
                task.setCompleted(true);
                saveTasks();
                System.out.println("✅ Task marked as completed!");
                return;
            }
        }
        System.out.println("❌ Task not found with ID: " + taskId);
    }
    
    public void deleteTask(int taskId) {
        Task taskToRemove = null;
        for (Task task : tasks) {
            if (task.getId() == taskId) {
                taskToRemove = task;
                break;
            }
        }
        
        if (taskToRemove != null) {
            tasks.remove(taskToRemove);
            saveTasks();
            System.out.println("✅ Task deleted successfully!");
        } else {
            System.out.println("❌ Task not found with ID: " + taskId);
        }
    }
    
    public void searchTasks(String keyword) {
        List<Task> matchingTasks = tasks.stream()
                .filter(task -> task.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                               task.getDescription().toLowerCase().contains(keyword.toLowerCase()) ||
                               task.getCategory().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
        
        if (matchingTasks.isEmpty()) {
            System.out.println("No tasks found matching: " + keyword);
            return;
        }
        
        System.out.println("\n=== SEARCH RESULTS ===");
        matchingTasks.forEach(System.out::println);
    }
    
    public int getPendingTaskCount() {
        return (int) tasks.stream().filter(task -> !task.isCompleted()).count();
    }
    
    public int getUrgentTaskCount() {
        return (int) tasks.stream()
                .filter(task -> !task.isCompleted() && task.getPriority() >= 4)
                .count();
    }
    
    public List<Task> getUpcomingTasks() {
        return tasks.stream()
                .filter(task -> !task.isCompleted())
                .sorted(Comparator.comparing(Task::getDeadline))
                .limit(5)
                .collect(Collectors.toList());
    }
}