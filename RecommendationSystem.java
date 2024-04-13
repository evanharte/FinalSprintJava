import java.util.ArrayList;
import java.util.List;

public class RecommendationSystem {
    private static final int MIN_HEART_RATE = 50;
    private static final int MAX_HEART_RATE = 100;
    private static final int MIN_STEPS = 10000;

    public List<String> generateRecommendations(HealthData hd) {
        List<String> recommendations = new ArrayList<>();
        
        // Generate recommendations based on user's health data
        if (hd.getWeight() > 200 && hd.getHeight() < 5.5) {
            recommendations.add("Because your weight is over 200lbs and your height is under 5.5, consider watching less TV and eating healthier foods.");
        } else if (hd.getWeight() < 100 && hd.getHeight() > 6.0) {
            recommendations.add("Because your weight is under 100lbs and your height is over 6.0, you need to increase your daily calorie intake to gain some weight.");
        } else {
            recommendations.add("You're maintaining a healthy weight for your height and weight! What's your secret??");
        }

        int StepsToGo = MIN_STEPS - hd.getSteps();

        if (hd.getSteps() < 3000) {
            recommendations.add("You have less than 3000 steps. You should consider going for a walk.");
            recommendations.add("You have " + StepsToGo + " steps to go to reach the daily goal of 10,000 steps.");
        } else if (hd.getSteps() > MIN_STEPS) {
            recommendations.add("Your steps are over 10,000! Way to go, champ.");
        } else {
            recommendations.add("You're doing okay with your steps.");
            recommendations.add("You have " + StepsToGo + " steps to go to reach the daily goal of 10,000 steps.");
        }

        if (hd.getHeartRate() > MAX_HEART_RATE) {
            recommendations.add("Your resting heart rate is very high. Consider seeing your doctor.");
        } else if (hd.getHeartRate() > 80) {
            recommendations.add("Your resting heart rate is a bit high. Consider exercising more frequently and reducing overall stress.");
            recommendations.add("Going for daily 20-30 minute walks is a good place to start!");
        } else if (hd.getHeartRate() < MIN_HEART_RATE) {
            recommendations.add("Your resting heart rate is low. Consider seeing your doctor.");
        } else if (hd.getHeartRate() == 0 || hd.getHeartRate() < 0) {
            recommendations.add("You are dead. Rest in Peace.");
        } else {
            recommendations.add("Your resting heart rate is normal. Keep up the good work!");
        }
        return recommendations;
    }
}
