public class Main {
    public static void main(String[] args) {
        System.out.println("🚀 Starting Student Productivity & Finance Manager...");
        System.out.println("📅 Developed by: [Your Name]");
        System.out.println("🎓 VIT Bhopal - Java Programming Project");
        
        try {
            Dashboard dashboard = new Dashboard();
            dashboard.showMainMenu();
        } catch (Exception e) {
            System.out.println("❌ An error occurred: " + e.getMessage());
            System.out.println("Please check your installation and try again.");
        }
    }
}