import java.text.SimpleDateFormat;
import java.util.Date;

public class Expense {
    private int id;
    private String category;
    private double amount;
    private String description;
    private Date date;
    private String paymentMethod;
    
    public Expense(int id, String category, double amount, String description, String paymentMethod) {
        this.id = id;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.date = new Date();
    }
    
    public int getId() { return id; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Date getDate() { return date; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("ID: %d | %s | ₹%.2f | %s | %s | %s",
                id, category, amount, description, paymentMethod, sdf.format(date));
    }
    
    public String toFileString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return id + "|" + category + "|" + amount + "|" + description + "|" + 
               paymentMethod + "|" + sdf.format(date);
    }
    
    public static Expense fromFileString(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length == 6) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                int id = Integer.parseInt(parts[0]);
                String category = parts[1];
                double amount = Double.parseDouble(parts[2]);
                String description = parts[3];
                String paymentMethod = parts[4];
                Date date = sdf.parse(parts[5]);
                
                Expense expense = new Expense(id, category, amount, description, paymentMethod);
                expense.date = date;
                return expense;
            }
        } catch (Exception e) {
            System.out.println("Error parsing expense: " + e.getMessage());
        }
        return null;
    }
}
