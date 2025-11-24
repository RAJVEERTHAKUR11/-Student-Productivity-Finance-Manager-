import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    private int id;
    private String title;
    private String description;
    private String category;
    private Date deadline;
    private int priority;
    private boolean completed;
    private Date createdAt;
    
    public Task(int id, String title, String description, String category, Date deadline) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.deadline = deadline;
        this.priority = calculatePriority();
        this.completed = false;
        this.createdAt = new Date();
    }
    
    private int calculatePriority() {
        long timeDiff = deadline.getTime() - new Date().getTime();
        long daysLeft = timeDiff / (1000 * 60 * 60 * 24);
        
        if (daysLeft <= 1) return 5;
        else if (daysLeft <= 3) return 4;
        else if (daysLeft <= 7) return 3;
        else return 2;
    }
    
    public int getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Date getDeadline() { return deadline; }
    public void setDeadline(Date deadline) { this.deadline = deadline; }
    public int getPriority() { return priority; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public Date getCreatedAt() { return createdAt; }
    
    public void updatePriority() {
        this.priority = calculatePriority();
    }
    
    public String getPriorityString() {
        switch(priority) {
            case 5: return "URGENT";
            case 4: return "HIGH";
            case 3: return "MEDIUM";
            case 2: return "LOW";
            default: return "LOW";
        }
    }
    
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String status = completed ? "COMPLETED" : "PENDING";
        return String.format("ID: %d | %s | %s | Due: %s | Priority: %s | Status: %s",
                id, title, category, sdf.format(deadline), getPriorityString(), status);
    }
    
    public String toFileString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return id + "|" + title + "|" + description + "|" + category + "|" + 
               sdf.format(deadline) + "|" + priority + "|" + completed + "|" + 
               sdf.format(createdAt);
    }
    
    public static Task fromFileString(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length == 8) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                int id = Integer.parseInt(parts[0]);
                String title = parts[1];
                String description = parts[2];
                String category = parts[3];
                Date deadline = sdf.parse(parts[4]);
                int priority = Integer.parseInt(parts[5]);
                boolean completed = Boolean.parseBoolean(parts[6]);
                Date createdAt = sdf.parse(parts[7]);
                
                Task task = new Task(id, title, description, category, deadline);
                task.priority = priority;
                task.completed = completed;
                task.createdAt = createdAt;
                return task;
            }
        } catch (Exception e) {
            System.out.println("Error parsing task: " + e.getMessage());
        }
        return null;
    }
}