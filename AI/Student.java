package AI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StudentInfo implements Serializable {
    private int id;                 // 学号
    private String name;            // 姓名
    private int age;                // 年龄
    private String gender;          // 性别
    private List<Double> scores;    // 成绩列表
    private static final String[] subjects = {"语文", "数学", "英语"}; // 学科名称

    public StudentInfo(int id, String name, int age, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.scores = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public List<Double> getScores() {
        return scores;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void addScore(double score) {
        this.scores.add(score);
    }

    public double getTotalScore() {
        return scores.stream().mapToDouble(Double::doubleValue).sum();
    }

    // 获取学科名称
    public static String[] getSubjects() {
        return subjects;
    }

    // 获取学科成绩（按列输出）
    public String getFormattedScores() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < subjects.length; i++) {
            if (i < scores.size()) {
                sb.append(String.format("%-10.1f", scores.get(i))); // 对齐宽度为10
            } else {
                sb.append(String.format("%-10s", "-")); // 若无成绩，用“-”补位
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("%-5d%-15s%-5d%-8s%s%-10.1f", id, name, age, gender, getFormattedScores(), getTotalScore());
    }
}
