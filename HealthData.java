public class HealthData {
    private int id;
    private int userId;
    private double weight;
    private double height;
    private int steps;
    private int heartRate;
    private String date;

    // Constructor, getters, and setters
    public HealthData(int id, int userId, double weight, double height, int steps, int heartRate, String date) {
        this.id = id;
        this.userId = userId;
        this.weight = weight;
        this.height = height;
        this.steps = steps;
        this.heartRate = heartRate;
        this.date = date;
    }

    public HealthData(int userId, double weight, double height, int steps, int heartRate, String date) {
        this.userId = userId;
        this.weight = weight;
        this.height = height;
        this.steps = steps;
        this.heartRate = heartRate;
        this.date = date;
    }

    public HealthData() {
        this.userId = 0;
        this.weight = 0.0;
        this.height = 0.0;
        this.steps = 0;
        this.heartRate = 0;
        this.date = null;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public int getSteps() {
        return steps;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
