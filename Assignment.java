/*
@file Assignment.java
@author Ronak Patel
@date 1/24/2024
@description This class represents an assignment object. Student objects have assignment objects. A classroom also has assignment objects to track how many assignments are in the classroom. Each assignment has a grade, name, and id.
*/

// Assignment class
public class Assignment {

  // Instance variable
  private double grade;
  private String name;
  private int id;

  /*
  Constructor that assigns name and id to assignment
  @date 1/19/2024
  @parameters String, int
  */
  public Assignment(String name, int id) {
    // Assign instance variables to arguments
    this.name = name;
    this.id = id;

    // Default grade for ungraded
    grade = -1;
  }

  /*
  This method gets the assignment name
  @name getName
  @date 1/19/2024
  @parameters void
  @returns String
  */
  public String getName() {
    return name;
  }

  /*
  This method grades an assignment
  @name grade
  @date 1/19/2024
  @parameters Double
  @returns void
  */
  public void grade(double grade) {
    this.grade = grade;
  }

  /*
  This method prompts admin to change the grade of an assignment
  @name grade
  @date 1/19/2024
  @parameters void
  @returns void
  */
  public void grade() {
    // Variables
    Keyboard keyboard = new Keyboard();
    double grade;
    
    // Prompt for new grade
    grade = keyboard.getDouble("Enter the new grade (decimal) for " + name + " (-1 = ungraded): ");
    
    // Assign grade
    this.grade = grade;
  }
  
  /*
  This method gets the assignment id
  @name getID
  @date 1/16/2024
  @parameters void
  @returns int
  */
  public int getID() {
    return id;  
  }

  /*
  This method gets the assignment grade
  @name getGrade
  @date 1/16/2024
  @parameters void
  @returns double
  */
  public double getGrade() {
    return grade;
  }

  /*
  This method gets the average grade of an assignment in a classroom
  @name getAverageGrade
  @date 1/16/2024
  @parameters Classroom
  @returns double
  */
  public double getAverageGrade(Classroom c) {
    // Variables
    double ans = 0;
    int count = 0;

    // For each student in classroom, add their grade to the answer
    for (Student s : c.getStudents()) {
      // If assignment is graded
      if (s.getAssignmentDatabase().searchID(id).getGrade() != -1) {
        ans += s.getAssignmentDatabase().searchID(id).getGrade();
        count++;
      }
    }

    // If no assignments graded, return -1
    if (count == 0) return -1;

    // Return average
    return ans/count;
  }
}