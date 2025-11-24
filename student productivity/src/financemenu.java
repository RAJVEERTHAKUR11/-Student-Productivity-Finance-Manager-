import java.util.Map;
import java.util.Scanner;

public class financemenu {
    private financemanager financeManager;
    private Scanner scanner;
    
    public financemenu(financemanager financeManager, Scanner scanner) {
        this.financeManager = financeManager;
        this.scanner = scanner;
    }
    
    public void showMenu() {
        while (true) {
            System.out.println("\n--- FINANCE TRACKING ---");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. View Expenses by Category");
            System.out.println("4. Financial Analytics");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose option: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1: addExpense(); break;
                case 2: financeManager.viewAllExpenses(); break;
                case 3: viewExpensesByCategory(); break;
                case 4: showFinancialAnalytics(); break;
                case 5: return;
                default: System.out.println("Invalid choice!");
            }
        }
    }
    
    private void addExpense() {
        System.out.println("\n--- ADD EXPENSE ---");
        System.out.print("Enter category (Food, Transport, Entertainment, etc.): ");
        String category = scanner.nextLine();
        
        System.out.print("Enter amount: ₹");
        double amount = getDoubleInput();
        
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        
        System.out.print("Enter payment method (Cash, Card, UPI): ");
        String paymentMethod = scanner.nextLine();
        
        financeManager.addExpense(category, amount, description, paymentMethod);
    }
    
    private void viewExpensesByCategory() {
        System.out.print("Enter category to view: ");
        String category = scanner.nextLine();
        financeManager.viewExpensesByCategory(category);
    }
    
    private void showFinancialAnalytics() {
        System.out.println("\n--- FINANCIAL ANALYTICS ---");
        
        double totalSpent = financeManager.getTotalSpentThisMonth();
        double remainingBudget = financeManager.getRemainingBudget();
        double dailyAverage = financeManager.getDailyAverageSpending();
        
        System.out.println("Monthly Spending: ₹" + String.format("%.2f", totalSpent));
        System.out.println("Remaining Budget: ₹" + String.format("%.2f", remainingBudget));
        System.out.println("Daily Average: ₹" + String.format("%.2f", dailyAverage));
        
        Map<String, Double> categorySpending = financeManager.getCategoryWiseSpending();
        if (!categorySpending.isEmpty()) {
            System.out.println("\nCategory-wise Breakdown:");
            for (Map.Entry<String, Double> entry : categorySpending.entrySet()) {
                System.out.println("  " + entry.getKey() + ": ₹" + String.format("%.2f", entry.getValue()));
            }
        }
        
        double budgetUsage = (totalSpent / financeManager.getRemainingBudget()) * 100;
        System.out.println("\nBudget Health: " + String.format("%.1f", budgetUsage) + "% of budget used");
        
        if (budgetUsage > 80) {
            System.out.println("⚠️  You're close to exceeding your monthly budget!");
        } else if (budgetUsage > 50) {
            System.out.println("ℹ️  You've used half of your monthly budget.");
        } else {
            System.out.println("✅ You're within your budget limits.");
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
