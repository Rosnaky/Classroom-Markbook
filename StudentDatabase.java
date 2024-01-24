/*
@file StudentDatabase.java
@author Ronak Patel
@date 1/24/2024
@description This class represents a database of all students. All student objects can be modified with this class
*/


// Imports
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

// StudentDatabase class
class StudentDatabase {

  // Instance variables
  private ArrayList<Student> students;
  String file;

  /*
  Constructor with file for for each database
  @date 1/18/2024
  @parameters String
  */
  public StudentDatabase(String file) {
    // Instantiate students
    students = new ArrayList<Student>();
    this.file = file;
    
    // Create file
    try {
      // If new file, write 0
      if (new File(file).createNewFile()) {
        new Keyboard().writeToFile(file, "0");
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    // Call populate file to get all information from file
    populate();
  }

  /*
  This program adds a student to the student database.
  @name add
  @date 1/18/2024
  @parameters Student
  @returns void
  */
  public void add(Student s) {
    students.add(s);

    // Sort students
    sort(0, students.size()-1);
  }

  /*
  This method returns file
  @name getFile
  @date 1/15/2024
  @parameters void
  @returns String
  */
  public String getFile() {
    return file;
  }

  /*
  This method checks if an id is unique
  @name isUnique
  @date 1/22/2024
  @parameters long
  @returns boolean
  */
  private boolean isUnique(long id) {

    // Iterate over all students
    for (Student s : students) {
      // If id is found, return false
      if (s.getID() == id) {
        return false;
      }
    }

    // Return unique
    return true;
  }

  /*
  This method creates a new student and adds it to the database.
  @name create
  @date 1/15/2024
  @parameters void
  @returns void
  */
  public void create() {
    // Variables
    Keyboard keyboard = new Keyboard();
    String firstName;
    String lastName;
    long id;
    String username;
    String password;
    
    // Prompt for registration of student
    keyboard.print("\nRegister student for an account\n");
    
    // Prompt user for each value
    firstName = keyboard.getLine("\nEnter first name: ");
    lastName = keyboard.getLine("\nEnter last name: ");
    username = keyboard.getWord("\nEnter username (one word only): ");
    keyboard.getLine(""); // Skip error
    password = keyboard.getLine("Enter password: ");

    // Generate unique id
    do {
      id = (long)(Math.random() * 8999999999.0)+1000000000L;
    } while (!isUnique(id));
    
    // Add student to database
    add(new Student(firstName, lastName, username, password, id));
    
    // Confirmation message
    keyboard.print("\nStudent (" + firstName + " " + lastName + ") has been registered.\n");
  }

  /*
  This method populates the Students arraylist with the information from the file.
  @name populate
  @date 1/16/2024
  @parameters void
  @returns void
  */
  private void populate() {
    // Call keyboard's getStudentsInfo methodw
    new Keyboard().getStudentsInfo(file, this);
  }

  /*
  This method returns the student arraylist.
  @name getStudents
  @date 1/12/2024
  @parameters void
  @returns ArrayList<Student>
  */
  public ArrayList<Student> getStudents() {
    return students;
  }

  /*
  This method searches for an Student object using id
  @name searchID
  @date 1/15/2024
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
  This function takes sorts the staff arraylist using quick sort.
  @name sort
  @date 1/16/2024
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
  This function assigns a random pivot value and quickSort around the pivot. Another function is called to help with this.
  @name partitionRandomPivot
  @date 1/16/2024
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
  @name partition
  @date 1/16/2024
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
}