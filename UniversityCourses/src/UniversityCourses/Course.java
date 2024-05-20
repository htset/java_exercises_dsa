package UniversityCourses;

public class Course {
  public int id;
  public String name;
  public int prereqCount;
  public int[] prereqIDs;

  public Course(int id, String name, int prereqCount, int[] prereqIDs) {
    this.id = id;
    this.name = name;
    this.prereqCount = prereqCount;
    this.prereqIDs = prereqIDs;
  }

  public boolean canEnroll(Student student) {
    for (int prereqId : this.prereqIDs) {
      boolean hasPrereq = false;
      for (int courseId : student.courses) {
        if (courseId == prereqId) {
          hasPrereq = true;
          break;
        }
      }
      if (!hasPrereq) {
        return false;
      }
    }
    return true;
  }
}