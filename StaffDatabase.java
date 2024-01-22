/*
@file StaffDatabase.java
@author Ronak Patel
@date 1/24/2024
@description This Staff Database class is a collection of Admin objects. All admin objects can be managed with this class.
*/

// Imports
import java.util.ArrayList;

// StaffDatabase class
class StaffDatabase {

  // Instance variables
  public ArrayList<Admin> staff;
  private StudentDatabase sd;
  private ClassroomDatabase cd;
  private String file;

  /*
  Constructor with files to populate
  @parameters String, String, String
  */
  public StaffDatabase(String sFile, String cFile, String stFile) {
    // Instantiate staff
    staff = new ArrayList<Admin>();
    sd = new StudentDatabase(stFile);
    cd = new ClassroomDatabase(cFile);
    file = sFile;
    
    // Populate staff database and classroom database
    populate(sFile);
    cd.populate(this);
  }

  /*
  This method adds a new Admin object to the staff ArrayList
  @parameters Admin
  @returns void
  */
  public void add(Admin admin) {
    // Add to staff
    staff.add(admin);
    
    // Sort staff
    sort(0, staff.size()-1);
  }

  /*
  This method gets an Admin object from the index
  @parameters int
  @returns Admin
  */
  public Admin get(int i) {
    // Add to staff
    return staff.get(i);
  }

  /*
  This method searches for an Admin object using username and password. Sequential search is used
  @parameters String, String
  @returns Admin
  */
  public Admin searchNames(String username, String password) {
  
    // Iterate over database until match is found
    for (Admin a : staff) {
      // If match is found, return admin object
      if (a.isMatch(username, password)) {
        return a;
      }
    }
    
    // Return null if not found
    return null;
  }

  /*
  This method searches for an Admin object using id
  @parameters id
  @returns Admin
  */
  public Admin searchID(long id) {
    // Variables
    int left = 0, right = staff.size()-1;
    
    // Continue until left and right meet
    while (left <= right) {
      // Get middle index
      int mid = (left + right)/2;
      
      // If match is found, return the admin object
      if (staff.get(mid).getID() == id) {
        return staff.get(mid);
      }
      // If id is greater than mid, search right half
      else if (staff.get(mid).getID() < id) {
        left = mid + 1;
      }
      // If id is less than mid, search left half
      else {
        right = mid - 1;
      }
    }
    
    // If not found, return null object
    return null;
  }
   
   
  /*
  This method populates the StaffDatabase with Admin objects
  @parameters String
  @returns void
  */
  private void populate(String file) {
    // Variables
    Keyboard keyboard = new Keyboard();
    ArrayList<String[]> accounts;
    
    // Get accounts form file
    accounts = keyboard.getAccounts(file);
    
    // Loop through each acount, make an Admin object, and add it to database
    for (String[] account : accounts) {
      // Add to staff
      staff.add(new Admin(account[0], account[1], account[2], User.decrypt(account[3]), Long.parseLong(account[4])));
    }

    // Sort admins
    sort(0, staff.size()-1);
  }

  /*
  Gets student database
  @parameters void
  @returns StudentDatabase
  */
  public StudentDatabase getStudentDatabase() {
    return sd;
  }

  /*
  Gets staff arraylist
  @parameters void
  @returns ArrayList<Admin>
  */
  public ArrayList<Admin> getAdmins() {
    return staff;
  }

  /*
  Gets classroom database
  @parameters void
  @returns ClassroomDatabase
  */
  public ClassroomDatabase getClassroomDatabase() {
    return cd;
  }

  /*
  This method checks if an id is unique
  @parameters long
  @returns boolean
  */
  private boolean isUnique(long id) {

    // Iterate over all admins
    for (Admin a : staff) {
      // If id is found, return false
      if (a.getID() == id) {
        return false;
      }
    }

    // Return unique
    return true;
  }

  
  /*
  This method undergoes process to create admin. User is prompted to enter all information.
  @parameters void
  @returns Admin
  */
  public Admin createAdmin() {
    // Variables
    String firstName;
    String lastName;
    String username;
    String password;
    long id;
    Admin admin;
    Keyboard keyboard = new Keyboard();
    
    // Prompt for registration
    keyboard.print("\nRegister for an account\n");
    
    // Prompt user for each value
    firstName = keyboard.getLine("\nEnter first name: ");
    lastName = keyboard.getLine("\nEnter last name: ");
    username = keyboard.getWord("\nEnter username (one word only): ");
    keyboard.getLine(""); // Skip error
    password = keyboard.getLine("Enter password: ");
    
    // Generate 10 digit random id
    do {
      id = (long)(Math.random() * 8999999999.0)+1000000000L;
    } while (!isUnique(id));
    
    // Confirmation Message
    keyboard.print("\n\nRegistered user successfully!");
    
    // Create new admin and return it
    admin = new Admin(firstName, lastName, username, password, id);
    staff.add(admin);
    
    // Sort staff
    this.sort(0, staff.size()-1);
    
    // keyboard.persistAdminInfo(file, this);
    
    return admin;
  }

  /*
  This method is where the user choice will be identified and executed
  @parameters String, Admin
  @returns void
  */
  public void execute(String choice, Admin a) {
    // Variables
    Keyboard keyboard = new Keyboard();
    
    // Use switch case to execute choice
    switch (choice) {
      
      // If classroom is added
      case "Add Classroom":
        cd.create(a);
        break;
      
      // If admin needs to be added
      case "Add Admin":
        createAdmin();
        break;
      
      // Add student to database
      case "Add Student to Database":
        sd.create();
        break;
      
      // Delete database
      case "Purge":
        purge(a);
        break;
      
      // Default
      default:
        // If admin needs to deleted
        if (choice.length() >= 12 && choice.substring(0, 12).equals("Delete Admin")) {
          deleteAdmin(Long.parseLong(choice.substring(14, choice.length()-1)));
        }
        
        // If classroom needs to deleted
        else if (choice.length() >= 16 && choice.substring(0, 16).equals("Delete Classroom")) { 
         cd.deleteClassroom(Integer.parseInt(choice.substring(18, choice.length()-1)));
        }
        
        
        // If assignment needs to be created
        else if (choice.length() >= 17 && choice.substring(0, 17).equals("Create Assignment")) {
         cd.searchID(Integer.parseInt(choice.substring(19, choice.length()-1))).createAssignment();
        }
        
        
        // If add students to classroom
        else if (choice.length() >= 24 && choice.substring(0, 24).equals("Add Student to Classroom")) {
         cd.searchID(Integer.parseInt(choice.substring(choice.length()-20, choice.length()-14))).addStudent(sd.searchID(Long.parseLong(choice.substring(choice.length()-11, choice.length()-1))));
        }
        
        
        // If changing assignment grade
        else if (choice.length() >= 23 && choice.substring(0, 23).equals("Change Assignment Grade")) {
          cd.searchID(Integer.parseInt(choice.substring(choice.length()-31, choice.length()-25))).searchID(Long.parseLong(choice.substring(choice.length()-11, choice.length()-1))).searchID(Integer.parseInt(choice.substring(choice.length()-22, choice.length()-14))).grade();
        }
        
        // If changing student username
        else if (choice.length() >= 21 && choice.substring(0, 21).equals("Edit Student Username")) {
          sd.searchID(Long.parseLong(choice.substring(choice.length()-11, choice.length()-1))).editUsername();
        }
        
        // If changing student password
        else if (choice.length() >= 21 && choice.substring(0, 21).equals("Edit Student Password")) {
          sd.searchID(Long.parseLong(choice.substring(choice.length()-11, choice.length()-1))).editPassword();
        }
        
        // If changing student first name
        else if (choice.length() >= 23 && choice.substring(0, 23).equals("Edit Student First Name")) {
          sd.searchID(Long.parseLong(choice.substring(choice.length()-11, choice.length()-1))).editFirstName();
        }
        
        // If changing student last name
        else if (choice.length() >= 22 && choice.substring(0, 22).equals("Edit Student Last Name")) {
          sd.searchID(Long.parseLong(choice.substring(choice.length()-11, choice.length()-1))).editLastName();
        }
        
        // If changing admin username
        else if (choice.equals("Edit Admin Username (" + a.getUsername() + ")")) {
          a.editUsername();
        }
        
        // If changing admin password
        else if (choice.equals("Edit Admin Password")) {
          a.editPassword();
        }
        
        // If changing student first name
        else if (choice.equals("Edit Admin First Name (" + a.getFirstName() + ")")) {
          a.editFirstName();
        }
        
        // If changing admin last name
        else if (choice.equals("Edit Admin Last Name (" + a.getLastName() + ")")) {
          a.editLastName();
        }
        
        // If deleting assignment
        else if (choice.length() >= 17 && choice.substring(0, 17).equals("Delete Assignment")) {
          cd.searchID(Integer.parseInt(choice.substring(19, 25))).removeAssignment(Integer.parseInt(choice.substring(28, choice.length()-1)));
        }
        
        // If deleting student from classroom
        else if (choice.length() >= 29 && choice.substring(0, 29).equals("Delete Student from Classroom")) {
          cd.searchID(Integer.parseInt(choice.substring(31, 37))).deleteStudent(sd.searchID(Long.parseLong(choice.substring(40, choice.length()-1))));
        }

        // If adding admin to classroom
        else if (choice.length() >= 22 && choice.substring(0, 22).equals("Add Admin to Classroom")) {
          cd.searchID(Integer.parseInt(choice.substring(choice.length()-20, choice.length()-14))).addAdmin(searchID(Long.parseLong(choice.substring(choice.length()-11, choice.length()-1))));
        }

        // If getting average grade for an assignment
        else if (choice.length() >= 13 && choice.substring(0, 13).equals("Average Grade")) {
          Classroom c = cd.searchID(Integer.parseInt(choice.substring(15, 21)));
          Assignment assignment = c.getAssignmentDatabase().searchID(Integer.parseInt(choice.substring(24, choice.length()-1)));

          keyboard.print("\nAverage Grade of " + assignment.getName() + ": "+ (assignment.getAverageGrade(c) == -1 ? "No grades have been added" : assignment.getAverageGrade(c) + "%"));
        }
    }
    // Persist all information
    keyboard.persistInfo(this);
  }

  /*
  This method deletes an admin from the database
  @parameters long
  @returns void
  */
  private void deleteAdmin(long id) {
    staff.remove(searchID(id));
  }

  /*
  This method deletes all databases
  @parameters Admin
  @returns void
  */
  private void purge(Admin a) {
    cd.purge();
    sd.getStudents().clear();
    staff = new ArrayList<Admin>() {{add(a);}};
    a.getClassrooms().clear();
  }


  /*
  This function takes sorts the staff arraylist using quick sort.
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
  This method returns the file associated to this database
  @parameters void
  @returns String
  */
  public String getFile() {
    return file;
  }

     /*
  This function assigns a random pivot value and quickSort around the pivot. Another function is called to help with this.
  @parameters int, int
  @returns int
  */
  private int partitionRandomPivot(int low, int high) {
    // Variables
    int pivot = (int) (Math.random() * (high - low + 1)) + low;
    Admin temp;
    
    // Swap high value with pivot
    temp = staff.get(high);
    staff.set(high, staff.get(pivot));
    staff.set(pivot, temp);
    
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
    long pivot = staff.get(high).getID();
    int i = low - 1;
    Admin temp;
    
    // Go through all values in array to arrange them around the pivot
    for (int j = low; j <= high - 1; j++) {
      // Check if the j value is less than pivot. If so, swap i value with j value
      if (staff.get(j).getID() < pivot) {
        // Increment i
        i++;
        
        // Swap values
        temp = staff.get(i);
        staff.set(i, staff.get(j));  
        staff.set(j, temp);
      }
    }
    // Swap arr[i+1] with arr[high]
    temp = staff.get(i+1);
    staff.set(i+1, staff.get(high));
    staff.set(high, temp);
    
    // Return i+1
    return i+1;
  }
}