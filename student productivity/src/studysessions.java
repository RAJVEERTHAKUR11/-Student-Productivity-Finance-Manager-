import java.text.SimpleDateFormat;
import java.util.Date;

public class studysessions {
    private int id;
    private String subject;
    private int duration;
    private String topic;
    private Date date;
    private double productivityScore;
    
    public studysessions(int id, String subject, int duration, String topic) {
        this.id = id;
        this.subject = subject;
        this.duration = duration;
        this.topic = topic;
        this.date = new Date();
        this.productivityScore = calculateProductivityScore();
    }
    
    private double calculateProductivityScore() {
        double baseScore = Math.min(duration / 60.0, 10.0);
        if (topic != null && !topic.isEmpty()) {
            baseScore += 2.0;
        }
        return Math.min(baseScore, 10.0);
    }
    
    public int getId() { return id; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public int getDuration() { return duration; }
    public void setDuration(int duration) { 
        this.duration = duration;
        this.productivityScore = calculateProductivityScore();
    }
    public String getTopic() { return topic; }
    public void setTopic(String topic) { 
        this.topic = topic;
        this.productivityScore = calculateProductivityScore();
    }
    public Date getDate() { return date; }
    public double getProductivityScore() { return productivityScore; }
    
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return String.format("ID: %d | %s | %d mins | Topic: %s | Score: %.1f/10 | %s",
                id, subject, duration, topic, productivityScore, sdf.format(date));
    }
    
    public String toFileString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return id + "|" + subject + "|" + duration + "|" + topic + "|" + 
               productivityScore + "|" + sdf.format(date);
    }
    
    public static studysessions fromFileString(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length == 6) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                int id = Integer.parseInt(parts[0]);
                String subject = parts[1];
                int duration = Integer.parseInt(parts[2]);
                String topic = parts[3];
                double productivityScore = Double.parseDouble(parts[4]);
                Date date = sdf.parse(parts[5]);
                
                studysessions session = new studysessions(id, subject, duration, topic);
                session.productivityScore = productivityScore;
                session.date = date;
                return session;
            }
        } catch (Exception e) {
            System.out.println("Error parsing study session: " + e.getMessage());
        }
        return null;
    }
}
