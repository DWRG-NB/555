package AI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentInfoManager manager = new StudentInfoManager("students.csv");

        // 加载 CSV 文件中的学生数据
        manager.loadStudentsFromCSV();

        while (true) {
            System.out.println("\n=== 学生管理系统 ===");
            System.out.println("1. 添加学生");
            System.out.println("2. 显示所有学生信息（按总分排序）");
            System.out.println("3. 修改学生信息");
            System.out.println("4. 删除学生信息");
            System.out.println("5. 查询学生信息");
            System.out.println("6. 退出并保存数据");
            System.out.print("请选择操作：");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> {
                        System.out.print("输入学生 ID：");
                        int id = Integer.parseInt(scanner.nextLine());
                        System.out.print("输入学生姓名：");
                        String name = scanner.nextLine();
                        System.out.print("输入学生年龄：");
                        int age = Integer.parseInt(scanner.nextLine());
                        System.out.print("输入学生性别：");
                        String gender = scanner.nextLine();
                        System.out.print("输入学生成绩（用空格分隔）：");
                        String[] scoreStrings = scanner.nextLine().split(" ");
                        List<Double> scores = new ArrayList<>();
                        for (String scoreString : scoreStrings) {
                            scores.add(Double.parseDouble(scoreString));
                        }
                        manager.addStudent(id, name, age, gender, scores);
                    }
                    case 2 -> manager.displayAllStudents();
                    case 3 -> {
                        System.out.print("输入要修改的学生 ID：");
                        int id = Integer.parseInt(scanner.nextLine());
                        System.out.print("输入新的姓名：");
                        String newName = scanner.nextLine();
                        System.out.print("输入新的年龄：");
                        int newAge = Integer.parseInt(scanner.nextLine());
                        System.out.print("输入新的性别：");
                        String newGender = scanner.nextLine();
                        manager.modifyStudent(id, newName, newAge, newGender);
                    }
                    case 4 -> {
                        System.out.print("输入要删除的学生 ID：");
                        int id = Integer.parseInt(scanner.nextLine());
                        manager.deleteStudent(id);
                    }
                    case 5 -> {
                        System.out.print("输入要查询的学生 ID：");
                        int id = Integer.parseInt(scanner.nextLine());
                        manager.queryStudentById(id);
                    }
                    case 6 -> {
                        manager.saveAllStudentsToCSV();
                        System.out.println("退出系统并保存数据！");
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("无效的选择！");
                }
            } catch (Exception e) {
                System.out.println("输入错误，请重新输入！");
            }
        }
    }
}
