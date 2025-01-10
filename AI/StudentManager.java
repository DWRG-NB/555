package AI;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StudentInfoManager {
    private List<StudentInfo> students = new ArrayList<>();
    private String csvFilePath = "students.csv"; // 默认 CSV 文件路径

    public StudentInfoManager(String csvFilePath) {
        this.csvFilePath = csvFilePath;
        saveCSVHeader(); // 确保文件有表头
    }

    // 添加学生
    public void addStudent(int id, String name, int age, String gender, List<Double> scores) {
        StudentInfo student = new StudentInfo(id, name, age, gender);
        for (double score : scores) {
            student.addScore(score);
        }
        students.add(student);
        saveStudentToCSV(student);
        System.out.println("学生信息已添加并保存到 CSV 文件中！");
    }

    // 删除学生
    public void deleteStudent(int id) {
        StudentInfo student = findStudentById(id);
        if (student != null) {
            students.remove(student);
            System.out.println("学生信息已删除！");
        } else {
            System.out.println("未找到该学生！");
        }
    }

    // 显示所有学生信息（按总分排序）
    public void displayAllStudents() {
        students.sort(Comparator.comparingDouble(StudentInfo::getTotalScore).reversed());
        System.out.println(String.format("%-5s%-15s%-5s%-8s%-10s%-10s%-10s%-10s",
                "ID", "姓名", "年龄", "性别", "语文", "数学", "英语", "总分"));
        for (StudentInfo student : students) {
            System.out.println(student);
        }
    }

    // 修改学生信息
    public void modifyStudent(int id, String newName, int newAge, String newGender) {
        StudentInfo student = findStudentById(id);
        if (student != null) {
            student.setName(newName);
            student.setAge(newAge);
            student.setGender(newGender);
            System.out.println("学生信息已修改！");
        } else {
            System.out.println("未找到该学生！");
        }
    }

    // 查询学生信息
    public void queryStudentById(int id) {
        StudentInfo student = findStudentById(id);
        if (student != null) {
            System.out.println(String.format("%-5s%-15s%-5s%-8s%-10s%-10s%-10s%-10s",
                    "ID", "姓名", "年龄", "性别", "语文", "数学", "英语", "总分"));
            System.out.println(student);
        } else {
            System.out.println("未找到该学生！");
        }
    }

    // 根据 ID 查找学生
    private StudentInfo findStudentById(int id) {
        for (StudentInfo student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    // 保存学生信息到最终 CSV 文件（退出程序时调用）
    public void saveAllStudentsToCSV() {
        try (FileWriter writer = new FileWriter(csvFilePath)) {
            writer.write("ID,Name,Age,Gender,Chinese,Math,English,TotalScore\n");
            for (StudentInfo student : students) {
                writer.write(student.getId() + "," + student.getName() + "," + student.getAge() + "," +
                        student.getGender() + "," + getCSVFormattedScores(student) + "," +
                        student.getTotalScore() + "\n");
            }
            System.out.println("所有学生信息已保存到 CSV 文件中！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 将学生的成绩格式化为 CSV 格式
    private String getCSVFormattedScores(StudentInfo student) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < StudentInfo.getSubjects().length; i++) {
            if (i < student.getScores().size()) {
                sb.append(student.getScores().get(i));
            } else {
                sb.append("0"); // 若成绩为空，用 0 填充
            }
            if (i < StudentInfo.getSubjects().length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    // 从 CSV 文件中加载学生数据
    public void loadStudentsFromCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            boolean isFirstLine = true; // 跳过表头
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // 跳过表头行
                    continue;
                }
                // 解析 CSV 行
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]); // 学生 ID
                String name = parts[1];              // 姓名
                int age = Integer.parseInt(parts[2]); // 年龄
                String gender = parts[3];            // 性别
                double chinese = Double.parseDouble(parts[4]);
                double math = Double.parseDouble(parts[5]);
                double english = Double.parseDouble(parts[6]);

                // 构造学生对象
                StudentInfo student = new StudentInfo(id, name, age, gender);
                student.addScore(chinese);
                student.addScore(math);
                student.addScore(english);

                students.add(student);
            }
            System.out.println("学生数据已从 CSV 文件加载！");
        } catch (IOException e) {
            System.out.println("加载 CSV 文件失败！");
            e.printStackTrace();
        }
    }
}
