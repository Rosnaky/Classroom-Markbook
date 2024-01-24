/*
@file Student.java
@author Ronak Patel
@date 1/24/2024
@description This Student class defines a student object that has a name, id, and all assignments assigned to the student.
*/

// Imports
import java.util.ArrayList;

// Student class
class Student extends User {

  // Instance variables
  AssignmentDatabase assignments;
     
  /*
  Constructor that creates a new user object with parameters
  @parameters String, String, String, String, long
  */
  public Student(String firstName, String lastName, String username, String password, long id) {
    super(firstName, lastName, username, password, id);
  assignments = new AssignmentDatabase();
  }

  /*
  This method calculates the average of the assignments in a class
  @name getCourseGrade
  @date 1/17/2024
  @parameters Classroom
  @return double
  */
  public double getCourseGrade(Classroom c) {
    // Variables
    double total = 0;
    int count = 0;
    Assignment a;

    // Iterate through the classroom's assignments
    for (Assignment assignment : c.getAssignmentDatabase().getAssignments()) {
      // Get student's assignment matching classroom assignment id
      a = getAssignmentDatabase().searchID(assignment.getID());
      
      // If assignment is ungraded, skip
      if (a.getGrade() == -1) continue;

      // Add to counters
      total += a.getGrade();
      count++;
    }

    // If there are no assignments, return -1
    if (count == 0) return -1;

    // Return average
    return total/count;
  }

  /*
  This method adds an assignment to the student's list of assignments
  @name addAssignment
  @date 1/15/2024
  @parameters Assignment
  @returns void
  */
  public void addAssignment(Assignment a) {
    // Check if assignment already in list
    if (searchID(a.getID()) != null) return;
    
    assignments.addAssignment(a);
  }

  /*
  This method gets the assignments arraylist
  @name getAssignments
  @date 1/16/2024
  @parameters void
  @returns ArrayList<Assignment>
  */
  public ArrayList<Assignment> getAssignments() {
    return assignments.getAssignments();
  }

  /*
  This method gets the assignmentdatabase
  @name getAssignmentDatabase
  @date 1/16/2024
  @parameters void
  @returns AssignmentDatabase
  */
  public AssignmentDatabase getAssignmentDatabase() {
    return assignments;
  }
  
  /*
  This method searches for an assignment by id and deletes that assignment
  @name removeAssignment
  @date 1/16/2024
  @parameters int
  @returns void
  */
  public void removeAssignment(int id) {
    // Call AssignmentDatabase removeAssignment method
    assignments.removeAssignment(id);
  }

  /*
  This method searches for an assignment by id and returns that assignment
  @name searchID
  @date 1/16/2024
  @parameters int
  @returns Assignment
  */
  public Assignment searchID(int id) {
    return assignments.searchID(id);
  }
}