/*
@file AssignmentDatabase.java
@author Ronak Patel
@date 1/24/2024
@description This Assignment Database class is a collection of Assignment objects. Each student and classroom has an assignment database to manage assignment objects

!Assignment data for each classroom persisted in {classroomid}Assignments.txt file when classroom is created!
*/

// Imports
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

// AssignmentDatabase class
class AssignmentDatabase {

  // Instance variables
  private ArrayList<Assignment> assignments;
  private String file;

  /*
  Constructor that populates with file name
  @date 1/18/2024
  @parameters void
  */
  public AssignmentDatabase(String file) {

    // Initialize instance variables
    assignments = new ArrayList<Assignment>();
    this.file = file;
    
    // Handle IO errors
    try {
      // Create new file. If file does not exist already, write 0 for 0 assignments in classroom
      if (new File(file).createNewFile()) {
        new Keyboard().writeToFile(file, "0");
      }
    }
    // Catch error
    catch (IOException e) {
      e.printStackTrace();
    }

    // Populate data from file
    populate();
  }

  /*
  Constructor which just creates new arraylist of assignments
  @date 1/16/2024
  @parameters void
  */
  public AssignmentDatabase() {
    assignments = new ArrayList<Assignment>();
  }

  /*
  This method populates the AssignmentDatabase with Assignment objects from a file
  @name populate
  @date 1/18/2024
  @parameters void
  @returns void
  */
  private void populate() {
    // Call get assignment info from keyboard class
    new Keyboard().getAssignmentInfo(file, this);
  }

  /*
  This method returns the assignments arraylist
  @name getAssignments
  @date 1/17/2024
  @parameters void
  @returns ArrayList<Assignment>
  */
  public ArrayList<Assignment> getAssignments() {
    return assignments;
  }
  
  /*
  This method searches for an Assignment object using id
  @name searchID
  @date 1/17/2024
  @parameters int
  @returns Assignment
  */
  public Assignment searchID(int id) {
  
    // Variables
    int left = 0, right = assignments.size()-1;
    
    // Continue until left and right meet
    while (left <= right) {
      // Get middle index
      int mid = (left + right)/2;
      
      // If match is found, return the admin object
      if (assignments.get(mid).getID() == id) {
        return assignments.get(mid);
      }
      // If id is greater than mid, search right half
      else if (assignments.get(mid).getID() < id) {
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
  This function takes sorts the assignments arraylist using quick sort.
  @name sort
  @date 1/18/2024
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
  This function assigns a random pivot value and quick sorts around the pivot. Another function is called to help with this.
  @name partitiionRandomPivot
  @date 1/18/2024
  @parameters int, int
  @returns int
  */
  private int partitionRandomPivot(int low, int high) {
    // Variables
    int pivot = (int) (Math.random() * (high - low + 1)) + low;
    Assignment temp;
    
    // Swap high value with pivot
    temp = assignments.get(high);
    assignments.set(high, assignments.get(pivot));
    assignments.set(pivot, temp);
    
    // Return pivot position
    return partition(low, high);
  }

  /*
  This function arranges the arraylist values around the pivot.
  @name partition
  @date 1/18/2024
  @parameters int, int
  @returns int
  */
  private int partition(int low, int high) {
    // Variables
    long pivot = assignments.get(high).getID();
    int i = low - 1;
    Assignment temp;
    
    // Go through all values in array to arrange them around the pivot
    for (int j = low; j <= high - 1; j++) {
      // Check if the j value is less than pivot. If so, swap i value with j value
      if (assignments.get(j).getID() < pivot) {
        // Increment i
        i++;
        
        // Swap values
        temp = assignments.get(i);
        assignments.set(i, assignments.get(j));  
        assignments.set(j, temp);
      }
    }
    // Swap arr[i+1] with arr[high]
    temp = assignments.get(i+1);
    assignments.set(i+1, assignments.get(high));
    assignments.set(high, temp);
    
    // Return i+1
    return i+1;
  }

  /*
  This function adds an assignment to the arraylist.
  @name addAssignment
  @date 1/18/2024
  @parameters Assignment
  @returns void
  */
  public void addAssignment(Assignment assignment) {
    assignments.add(assignment);

    sort(0, assignments.size()-1);
  }

  /*
  This function removes an assignment from the arraylist.
  @name removeAssignment
  @date 1/18/2024
  @parameters Assignment
  @returns void
  */
  public void removeAssignment(int id) {
    assignments.remove(searchID(id));
  }

  /*
  This method returns file
  @name getFile
  @date 1/18/2024
  @parameters void
  @returns String
  */
  public String getFile() {
   return file;
  }
}