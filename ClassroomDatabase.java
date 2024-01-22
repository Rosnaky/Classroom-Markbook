/*
@file ClassroomDatabase.java
@author Ronak Patel
@date 1/24/2024
@description This class represents the database of all classrooms. This is where classrooms are managed in the database.
*/

// Imports
import java.util.ArrayList;
import java.io.File;

// ClassroomDatabase class
class ClassroomDatabase {
  
  // Instance variables
  private ArrayList<Classroom> classrooms;
  String file;
  
  /*
  Constructor
  @parameters String
  */
  public ClassroomDatabase(String file) {
    // Instantiate instance variables
    classrooms = new ArrayList<Classroom>();
    this.file = file;
  }

  /*
  This method deletes content of this database
  @parameters void
  @returns void
  */
  public void purge() {
    // For each classroom in the classrooms arraylist, delete the associated assignments file
    for (Classroom c : classrooms) {
      new File(c.getID() + "Assignments.txt").delete();
    }
    
    // Clear the classroom database
    classrooms.clear();
  }

  /*
  This method gets the ArrayList of Classrooms
  @parameters void
  @returns ArrayList<Classroom>
  */
  public ArrayList<Classroom> getClassrooms() {
    return classrooms;
  }
  
  /*
  This program adds a classroom to the classrooms arraylist, and sorts it.
  @parameters Classroom
  @returns void
  */
  public void add(Classroom c) {
    // Add to arraylist
    classrooms.add(c);

    // Sort arraylist
    sort(0, classrooms.size()-1);
  }
   
  /*
  This method populates the classrooms arraylist with the information from the file.
  @parameters StaffDatabase
  @returns void
  */
  public void populate(StaffDatabase sd) {
    // Get classroom info using method in keyboard class
    new Keyboard().getClassroomInfo(file, sd);
  }

  /*
  This method checks if the classroom id is unique
  @parameters int
  @returns boolean
  */
  private boolean isUnique(int id) {
    // Iterate over all classrooms
    for (Classroom c : classrooms) {
      // If id is found, returns false
      if (c.getID() == id) {
        return false;
      }
    }

    // Return unique
    return true;
  }
  
  
  /*
  This method creates a classroom, adds it to the database, and returns the object.
  @parameters voidInteger.parseInt(word)
  @returns void
  */
  public void create(Admin a) {
    // Variables
    Keyboard keyboard = new Keyboard();
    Classroom c;
    String name;
    int id;
    
    // Prompt for course name
    name = keyboard.getLine("\nEnter the name of the classroom: ");

    // Get unique id
    do {
      id = (int)(Math.random()*899999.0)+100000;
    } while (!isUnique(id));
    
    // Create new classroom with name
    c = new Classroom(name, id);
    a.addClassroom(c);
    
    // Add admin to classroom
    c.addAdmin(a);
    
    // Add classroom to database
    add(c);
    
    // Confirmation message
    keyboard.print(name + " classroom created");
  }

  /*
  This method deletes a classroom from the database. The admins association with the classroom, and all assignments are deleted.
  @parameters void
  @returns void
  */
  public void deleteClassroom(int id) {
    // Variables
    Classroom c = searchID(id);
    
    // Delete all instances of classroom in admin objects
    for (Admin a : c.getAdmins()) {
      a.deleteClassroom(c);
    }
    
    // Delete all instances of classroom assignments in student objects
    for (Student s : c.getStudents()) {
      for (Assignment a : c.getAssignmentDatabase().getAssignments()) {
        // Remove assignment in student
        s.removeAssignment(a.getID());
      }
    }
    
    // Delete assignments file for that classroom
    new File(c.getID() + "Assignments.txt").delete();
    
    // Delete classroom from database
    classrooms.remove(c);
  }

  /*
  This method searches for an Admin object using id
  @parameters id
  @returns Classroom
  */
  public Classroom searchID(int id) {
    // Variables
    int left = 0, right = classrooms.size()-1;
    
    // Continue until left and right meet
    while (left <= right) {
      // Get middle index
      int mid = (left + right)/2;
      
      // If match is found, return the admin object
      if (classrooms.get(mid).getID() == id) {
        return classrooms.get(mid);
      }
      // If id is greater than mid, search right half
      else if (classrooms.get(mid).getID() < id) {
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
  This function takes sorts the classrooms arraylist using quick sort.
  @parameters int, int
  @returns void
  */
  private void sort(int low, int high) {
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
    Classroom temp;
    
    // Swap high value with pivot
    temp = classrooms.get(high);
    classrooms.set(high, classrooms.get(pivot));
    classrooms.set(pivot, temp);
    
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
    long pivot = classrooms.get(high).getID();
    int i = low - 1;
    Classroom temp;
    
    // Go through all values in array to arrange them around the pivot
    for (int j = low; j <= high - 1; j++) {
      // Check if the j value is less than pivot. If so, swap i value with j value
      if (classrooms.get(j).getID() < pivot) {
        // Increment i
        i++;
        
        // Swap values
        temp = classrooms.get(i);
        classrooms.set(i, classrooms.get(j));  
        classrooms.set(j, temp);
      }
    }
    // Swap arr[i+1] with arr[high]
    temp = classrooms.get(i+1);
    classrooms.set(i+1, classrooms.get(high));
    classrooms.set(high, temp);
    
    // Return i+1
    return i+1;
  }

  /*
  Gets the file name associated with this classroom database
  @parameters void
  @returns String
  */
  public String getFile() {
    return file;
  }
}