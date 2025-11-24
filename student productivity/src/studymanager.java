import java.util.*;
import java.util.stream.Collectors;

public class studymanager {
    private List<studysessions> studySessions;
    private FileHandler fileHandler;
    private int nextId;
    
    public studymanager() {
        this.fileHandler = new FileHandler();
        this.studySessions = new ArrayList<>();
        this.nextId = 1;
        loadStudySessions();
    }
    
    private void loadStudySessions() {
        List<String> sessionData = fileHandler.readData("study_sessions.dat");
        for (String line : sessionData) {
            studysessions session = studysessions.fromFileString(line);
            if (session != null) {
                studySessions.add(session);
                if (session.getId() >= nextId) {
                    nextId = session.getId() + 1;
                }
            }
        }
    }
    
    private void saveStudySessions() {
        List<String> sessionData = studySessions.stream()
                .map(studysessions::toFileString)
                .collect(Collectors.toList());
        fileHandler.writeData("study_sessions.dat", sessionData);
    }
    
    public void addStudySession(String subject, int duration, String topic) {
        studysessions session = new studysessions(nextId++, subject, duration, topic);
        studySessions.add(session);
        saveStudySessions();
        System.out.println("✅ Study session recorded! (ID: " + session.getId() + ")");
        System.out.println("📊 Productivity Score: " + String.format("%.1f", session.getProductivityScore()) + "/10");
    }
    
    public void viewAllSessions() {
        if (studySessions.isEmpty()) {
            System.out.println("No study sessions recorded.");
            return;
        }
        
        System.out.println("\n=== ALL STUDY SESSIONS ===");
        studySessions.stream()
                .sorted(Comparator.comparing(studysessions::getDate).reversed())
                .forEach(System.out::println);
    }
    
    public int getTotalStudyTimeThisWeek() {
        Calendar cal = Calendar.getInstance();
        int currentWeek = cal.get(Calendar.WEEK_OF_YEAR);
        int currentYear = cal.get(Calendar.YEAR);
        
        return studySessions.stream()
                .filter(session -> {
                    cal.setTime(session.getDate());
                    return cal.get(Calendar.WEEK_OF_YEAR) == currentWeek && 
                           cal.get(Calendar.YEAR) == currentYear;
                })
                .mapToInt(studysessions::getDuration)
                .sum();
    }
    
    public Map<String, Integer> getSubjectWiseTime() {
        return studySessions.stream()
                .collect(Collectors.groupingBy(
                    studysessions::getSubject,
                    Collectors.summingInt(studysessions::getDuration)
                ));
    }
    
    public double getAverageProductivityScore() {
        if (studySessions.isEmpty()) return 0.0;
        return studySessions.stream()
                .mapToDouble(studysessions::getProductivityScore)
                .average()
                .orElse(0.0);
    }
    
    public String getMostStudiedSubject() {
        Map<String, Integer> subjectTime = getSubjectWiseTime();
        return subjectTime.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("None");
    }
}