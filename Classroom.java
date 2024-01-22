/*
@file Classroom.java
@author Ronak Patel
@date 1/24/2024
@description This class represents the classroom of students and stores Student objects. This classroom also keeps track of the admins who can manage the classroom. There is also an assignment database for all assignments in the classroom.
*/

// Imports
import java.util.ArrayList;

// Classroom objects
class Classroom {

  // Instance variables
  String name;
  int id;
  ArrayList<Student> students;
  AssignmentDatabase ad;
  ArrayList<Admin> admins;

  /*
  Constructor
  @parameters String, int
  */
  public Classroom(String name, int id) {
    // Assign instance variables to arguments
    this.name = name;
    this.id = id;

    // Initialize instance variables
    students = new ArrayList<Student>();
    ad = new AssignmentDatabase(id+"Assignments.txt");
    admins = new ArrayList<Admin>();
  }


  /*
  This method calculates the class average. This is the average of the students' average grades.
  @parameters void
  @returns double
  */
  public double getClassAverage() {
    // Variables
    double classAverage = 0;
    
    // Iterate through the students and add the students' average
    for (Student s : students) {
      classAverage += s.getAverage(this);
    }
    
    // Divide the class average by the number of students
    classAverage /= students.size();
    
    // Return the class average
    return classAverage;
  }


  /*
  This function adds a student object the classroom. The ArrayList is also sorted afterwards.
  @parameters Student
  @returns void
  */
  public void addStudent(Student s) {
    // Add student ArrayList
    students.add(s);
    
    // Add each assignment to the student
    for (Assignment a : ad.getAssignments()) {
      s.addAssignment(new Assignment(a.getName(), a.getID()));
    }
    
    // Sort students arraylist
    sort(0, students.size()-1);
  }

  /*
  This function adds an admin to the classroom. The ArrayList is also sorted afterwards.
  @parameters Admin
  @returns void
  */
  public void addAdmin(Admin a) {
    // Add admin to classroom's admins ArrayList
    admins.add(a);

    // Add classroom to admin's classrooms arraylist
    a.addClassroom(this);
  }

  /*
  This function adds an assignment to the classroom's assignments database.
  @parameters Assignment
  @returns void
  */
  public void addAssignment(Assignment a) {
    // Add admin ArrayList
    ad.getAssignments().add(a);
  }

  /*
  This method returns the name of the classroom
  @parameters void
  @returns String
  */
  public String getName() {
    return name;
  }

   /*
  This method returns the ID of the classroom
  @parameters void
  @returns int
  */
  public int getID() {
    return id;
  }

  /*
  This method returns the admins ArrayList
  @parameters void
  @returns ArrayList<Admin>
  */
  public ArrayList<Admin> getAdmins() {
    return admins;
  }
  
  /*
  This method returns the students ArrayList
  @parameters void
  @returns ArrayList<Students>
  */
  public ArrayList<Student> getStudents() {
    return students;
  }

  /*
  This method returns the assignments ArrayList
  @parameters void
  @returns AssignmentDatabase
  */
  public AssignmentDatabase getAssignmentDatabase() {
    return ad;
  }

  /*
  This method searches for an Student object using id
  @parameters long
  @returns Student
  */
  public Student searchID(long id) {
  
    // Variables
    int left = 0, right = students.size()-1;
    
    // Continue until left and right meet
    while (left <= right) {
    // Get middle index
      int mid = (left + right)/2;
      
      // If match is found, return the admin object
      if (students.get(mid).getID() == id) {
        return students.get(mid);
      }
      // If id is greater than mid, search right half
      else if (students.get(mid).getID() < id) {
        left = mid + 1;
      }
      // If id is less than mid, search left half
      else {
        right = mid - 1;
      }
    }
    
    // Return null if no match is found
    return null;
  }
  
  /*
  This method creates an assignment in the classroom
  @parameters void
  @returns void
  */
  public void createAssignment() {
    
    // Variables
    Keyboard keyboard = new Keyboard();
    String name;
    Assignment a;
    int id = (int)(Math.random()*89999999.0)+10000000;
    
    // Ask for the assignment name
    name = keyboard.getLine("\nEnter the name of the assignment: ");
    
    // Loop through all students and add ungraded assignments
    for (Student s : students) {
      s.addAssignment(new Assignment(name, id));
    }

    // Add assignment to assignment database
    ad.addAssignment(new Assignment(name, id));
    
    // Confirmation message
    keyboard.print(name + " assignment created.");
  }

  /*
  This method deletes an assignment in the classroom
  @parameters int
  @returns void
  */
  public void removeAssignment(int id) {
    // Delete assignment from AssignmentDatabase
    ad.removeAssignment(id);
  
    // Delete assignment from all students
    for (Student s : students) {
      s.removeAssignment(id);
    }
  }
  

  /*
  This function sorts the students arraylist using quick sort.
  @parameters int, int
  @returns void
  */
  private void sort(int low, int high) {
    // Variables
    if (low < high) {
      // Get pivot and sort around pivot
      int pivot = partitionRandomPivot(low, high);
      sort(low, pivot-1);
      sort(pivot+1, high);
    }
  }

  /*
  This function assigns a random pivot value and sorts around the pivot. Another function is called to help with this.
  @parameters int, int
  @returns int
  */
  private int partitionRandomPivot(int low, int high) {
    // Variables
    int pivot = (int) (Math.random() * (high - low + 1)) + low;
    Student temp;
    
    // Swap high value with pivot
    temp = students.get(high);
    students.set(high, students.get(pivot));
    students.set(pivot, temp);
    
    // Return pivot position
    return partition(low, high);
  }

  /*
  This function arranges the arraylist values around the pivot.
  @parameters int, int
  @returns int
  */
  private int partition(int low, int high) {
    // Variables
    long pivot = students.get(high).getID();
    int i = low - 1;
    Student temp;
    
    // Go through all values in array to arrange them around the pivot
    for (int j = low; j <= high - 1; j++) {
      
      // Check if the j value is less than pivot. If so, swap i value with j value
      if (students.get(j).getID() < pivot) {
        // Increment i
        i++;
        
        // Swap values
        temp = students.get(i);
        students.set(i, students.get(j));  
        students.set(j, temp);
      }
    }
    
    // Swap arr[i+1] with arr[high]
    temp = students.get(i+1);
    students.set(i+1, students.get(high));
    students.set(high, temp);
    
    // Return i+1
    return i+1;
  }

  /*
  This method removes a student from the classroom
  @parameters Student
  @returns void
  */
  public void deleteStudent(Student s) {
  
    // Remove all assignments from this classroom from the student
    for (Assignment a : ad.getAssignments()) {
      s.removeAssignment(a.getID());
    }
    
    // Remove student from classroom
    students.remove(s);
  }
}