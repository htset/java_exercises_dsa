package UniversityCourses;

public class Student {
  public int id;
  public String name;
  public int[] courses;
  public int courseCount;

  public Student(int id, String name, int courseCount, int[] courses) {
    this.id = id;
    this.name = name;
    this.courseCount = courseCount;
    this.courses = courses;
  }
}
