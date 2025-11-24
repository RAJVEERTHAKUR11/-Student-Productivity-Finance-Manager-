import java.text.SimpleDateFormat;
import java.util.*;

public class Dashboard {
    private TaskManager taskManager;
    private financemanager financeManager;
    private studymanager studyManager;
    private Scanner scanner;
    
    public Dashboard() {
        this.taskManager = new TaskManager();
        this.financeManager = new financemanager();
        this.studyManager = new studymanager();
        this.scanner = new Scanner(System.in);
    }
    
    public void showMainMenu() {
        while (true) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("           STUDENT PRODUCTIVITY & FINANCE MANAGER");
            System.out.println("=".repeat(60));
            System.out.println("1. 📝 Task Management");
            System.out.println("2. 💰 Finance Tracking");
            System.out.println("3. 📚 Study Tracker");
            System.out.println("4. 📊 View Dashboard");
            System.out.println("5. ⚙️  Settings");
            System.out.println("6. ❌ Exit");
            System.out.print("Choose an option (1-6): ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1: showTaskMenu(); break;
                case 2: showFinanceMenu(); break;
                case 3: showStudyMenu(); break;
                case 4: showDashboard(); break;
                case 5: showSettingsMenu(); break;
                case 6: 
                    System.out.println("\nThank you for using Student Productivity Manager! 👋");
                    System.out.println("Stay productive and manage your finances wisely! 💪");
                    return;
                default:
                    System.out.println("❌ Invalid choice! Please try again.");
            }
        }
    }
    
    private void showTaskMenu() {
        TaskMenu taskMenu = new TaskMenu(taskManager, scanner);
        taskMenu.showMenu();
    }
    
    private void showFinanceMenu() {
        financemenu financeMenu = new financemenu(financeManager, scanner);
        financeMenu.showMenu();
    }
    
    private void showStudyMenu() {
        while (true) {
            System.out.println("\n--- STUDY TRACKER ---");
            System.out.println("1. Add Study Session");
            System.out.println("2. View All Sessions");
            System.out.println("3. Study Analytics");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose option: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    System.out.print("Enter subject: ");
                    String subject = scanner.nextLine();
                    System.out.print("Enter duration (minutes): ");
                    int duration = getIntInput();
                    System.out.print("Enter topic (optional): ");
                    String topic = scanner.nextLine();
                    studyManager.addStudySession(subject, duration, topic);
                    break;
                    
                case 2:
                    studyManager.viewAllSessions();
                    break;
                    
                case 3:
                    showStudyAnalytics();
                    break;
                    
                case 4:
                    return;
                    
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    private void showDashboard() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                     PRODUCTIVITY DASHBOARD");
        System.out.println("=".repeat(60));
        
        int pendingTasks = taskManager.getPendingTaskCount();
        int urgentTasks = taskManager.getUrgentTaskCount();
        System.out.println("📝 TASK OVERVIEW:");
        System.out.println("   • Pending Tasks: " + pendingTasks);
        System.out.println("   • Urgent Tasks: " + urgentTasks);
        
        double totalSpent = financeManager.getTotalSpentThisMonth();
        double remainingBudget = financeManager.getRemainingBudget();
        double dailyAverage = financeManager.getDailyAverageSpending();
        System.out.println("\n💰 FINANCE OVERVIEW:");
        System.out.println("   • Monthly Spending: ₹" + String.format("%.2f", totalSpent));
        System.out.println("   • Remaining Budget: ₹" + String.format("%.2f", remainingBudget));
        System.out.println("   • Daily Average: ₹" + String.format("%.2f", dailyAverage));
        
        int weeklyStudyTime = studyManager.getTotalStudyTimeThisWeek();
        double avgProductivity = studyManager.getAverageProductivityScore();
        String topSubject = studyManager.getMostStudiedSubject();
        System.out.println("\n📚 STUDY OVERVIEW:");
        System.out.println("   • Weekly Study Time: " + (weeklyStudyTime/60) + "h " + (weeklyStudyTime%60) + "m");
        System.out.println("   • Average Productivity: " + String.format("%.1f", avgProductivity) + "/10");
        System.out.println("   • Most Studied Subject: " + topSubject);
        
        List<Task> upcomingTasks = taskManager.getUpcomingTasks();
        if (!upcomingTasks.isEmpty()) {
            System.out.println("\n⏰ UPCOMING DEADLINES:");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            for (Task task : upcomingTasks) {
                System.out.println("   • " + task.getTitle() + " (Due: " + sdf.format(task.getDeadline()) + ")");
            }
        }
        
        System.out.println("\n💡 RECOMMENDATIONS:");
        if (urgentTasks > 0) {
            System.out.println("   ⚠️  Complete your urgent tasks first!");
        }
        if (remainingBudget < 1000) {
            System.out.println("   💸 You're running low on budget this month!");
        }
        if (weeklyStudyTime < 10 * 60) {
            System.out.println("   📖 Consider increasing your study time this week!");
        }
        if (pendingTasks == 0 && weeklyStudyTime > 15 * 60) {
            System.out.println("   🎉 Great job! You're on top of everything!");
        }
        
        System.out.println("=".repeat(60));
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }
    
    private void showStudyAnalytics() {
        System.out.println("\n--- STUDY ANALYTICS ---");
        
        int totalTime = studyManager.getTotalStudyTimeThisWeek();
        System.out.println("Total study time this week: " + (totalTime/60) + "h " + (totalTime%60) + "m");
        
        double avgScore = studyManager.getAverageProductivityScore();
        System.out.println("Average productivity score: " + String.format("%.1f", avgScore) + "/10");
        
        Map<String, Integer> subjectTime = studyManager.getSubjectWiseTime();
        if (!subjectTime.isEmpty()) {
            System.out.println("\nSubject-wise breakdown:");
            for (Map.Entry<String, Integer> entry : subjectTime.entrySet()) {
                int hours = entry.getValue() / 60;
                int minutes = entry.getValue() % 60;
                System.out.println("  " + entry.getKey() + ": " + hours + "h " + minutes + "m");
            }
        }
    }
    
    private void showSettingsMenu() {
        while (true) {
            System.out.println("\n--- SETTINGS ---");
            System.out.println("1. Set Monthly Budget");
            System.out.println("2. Back to Main Menu");
            System.out.print("Choose option: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    System.out.print("Enter new monthly budget: ₹");
                    double budget = getDoubleInput();
                    financeManager.setMonthlyBudget(budget);
                    break;
                    
                case 2:
                    return;
                    
                default:
                    System.out.println("Invalid choice!");
            }
        }
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
    
    private double getDoubleInput() {
        while (true) {
            try {
                double value = Double.parseDouble(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}