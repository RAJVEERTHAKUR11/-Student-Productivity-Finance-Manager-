import java.util.*;
import java.util.stream.Collectors;

public class financemanager {
    private List<Expense> expenses;
    private FileHandler fileHandler;
    private int nextId;
    private double monthlyBudget;
    
    public financemanager() {
        this.fileHandler = new FileHandler();
        this.expenses = new ArrayList<>();
        this.nextId = 1;
        this.monthlyBudget = 10000.0;
        loadExpenses();
    }
    
    private void loadExpenses() {
        List<String> expenseData = fileHandler.readData("expenses.dat");
        for (String line : expenseData) {
            Expense expense = Expense.fromFileString(line);
            if (expense != null) {
                expenses.add(expense);
                if (expense.getId() >= nextId) {
                    nextId = expense.getId() + 1;
                }
            }
        }
    }
    
    private void saveExpenses() {
        List<String> expenseData = expenses.stream()
                .map(Expense::toFileString)
                .collect(Collectors.toList());
        fileHandler.writeData("expenses.dat", expenseData);
    }
    
    public void addExpense(String category, double amount, String description, String paymentMethod) {
        Expense expense = new Expense(nextId++, category, amount, description, paymentMethod);
        expenses.add(expense);
        saveExpenses();
        System.out.println("✅ Expense added successfully! (ID: " + expense.getId() + ")");
    }
    
    public void viewAllExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }
        
        System.out.println("\n=== ALL EXPENSES ===");
        expenses.stream()
                .sorted(Comparator.comparing(Expense::getDate).reversed())
                .forEach(System.out::println);
    }
    
    public void viewExpensesByCategory(String category) {
        List<Expense> categoryExpenses = expenses.stream()
                .filter(expense -> expense.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
                
        if (categoryExpenses.isEmpty()) {
            System.out.println("No expenses found in category: " + category);
            return;
        }
        
        System.out.println("\n=== EXPENSES - " + category.toUpperCase() + " ===");
        categoryExpenses.forEach(System.out::println);
        
        double total = categoryExpenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
        System.out.println("Total spent on " + category + ": ₹" + String.format("%.2f", total));
    }
    
    public void setMonthlyBudget(double budget) {
        this.monthlyBudget = budget;
        System.out.println("✅ Monthly budget set to: ₹" + String.format("%.2f", budget));
    }
    
    public double getTotalSpentThisMonth() {
        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH);
        int currentYear = cal.get(Calendar.YEAR);
        
        return expenses.stream()
                .filter(expense -> {
                    cal.setTime(expense.getDate());
                    return cal.get(Calendar.MONTH) == currentMonth && 
                           cal.get(Calendar.YEAR) == currentYear;
                })
                .mapToDouble(Expense::getAmount)
                .sum();
    }
    
    public Map<String, Double> getCategoryWiseSpending() {
        return expenses.stream()
                .collect(Collectors.groupingBy(
                    Expense::getCategory,
                    Collectors.summingDouble(Expense::getAmount)
                ));
    }
    
    public double getRemainingBudget() {
        return monthlyBudget - getTotalSpentThisMonth();
    }
    
    public double getDailyAverageSpending() {
        double total = getTotalSpentThisMonth();
        Calendar cal = Calendar.getInstance();
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return total / daysInMonth;
    }
}
