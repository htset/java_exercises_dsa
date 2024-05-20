package UniversityCourses;

public class UniversityCourses {
  public static void main(String[] args) {
    Course[] courses = {
        new Course(0, "Intro to Programming", 0, new int[] { -1 }),
        new Course(1, "Data Structures", 1, new int[] { 0 }),
        new Course(2, "Algorithms", 1, new int[] { 1 }),
        new Course(3, "Database Management", 1, new int[] { 0 }),
        new Course(4, "Web Development", 1, new int[] { 0 }),
        new Course(5, "Operating Systems", 2, new int[] { 1, 2 }),
        new Course(6, "Computer Networks", 2, new int[] { 1, 5 }),
        new Course(7, "Software Engineering", 2, new int[] { 1, 2 }),
        new Course(8, "Machine Learning", 2, new int[] { 1, 2 }),
        new Course(9, "Distributed Systems", 1, new int[] { 5 }),
        new Course(10, "Cybersecurity", 2, new int[] { 2, 3 }),
        new Course(11, "Cloud Computing", 2, new int[] { 2, 3 }),
        new Course(12, "Mobile App Development", 1, new int[] { 4 }),
        new Course(13, "Game Development", 1, new int[] { 0 }),
        new Course(14, "Artificial Intelligence", 2, new int[] { 2, 8 }),
        new Course(15, "Big Data Analytics", 2, new int[] { 2, 3 }),
        new Course(16, "Blockchain Technology", 2, new int[] { 2, 3 }),
        new Course(17, "UI/UX Design", 1, new int[] { 14 }),
        new Course(18, "Embedded Systems", 2, new int[] { 1, 5 }),
        new Course(19, "Computer Graphics", 1, new int[] { 0 })
    };

    Student student = new Student(1, "John Doe", 5, new int[] { 0, 1, 2, 3, 4 });

    Course[] targetCourses = {
        courses[13], // Game Development
        courses[16], // Blockchain Technology
        courses[17], // UI/UX Design (student cannot enroll)
        courses[18]  // Embedded Systems
    };

    System.out.println("Enrollment status for " + student.name + ": ");
    for (int i = 0; i < 4; ++i) {
      if (targetCourses[i].canEnroll(student)) {
        System.out.println("- Can enroll in " + targetCourses[i].name);
      } else {
        System.out.println("- Cannot enroll in " + targetCourses[i].name
            + " due to missing prerequisites.");
      }
    }
  }
}