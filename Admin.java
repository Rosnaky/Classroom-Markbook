/*
@file Admin.java
@author Ronak Patel
@date 1/24/2024
@description This class represents the admin object. This could be a teacher or another staff member. Allows for the creation of a new admin and the management of databases.

!Data persisted in admins.txt!
*/

// Imports
import java.util.ArrayList;


// Admin class
class Admin extends User {

  ArrayList<Classroom> classrooms;
   
  /*
  Constructor that calls User's constructor. New classrooms arraylist created
  @date 1/15/2024
  @parameters String, String, String, String, long
  */
  public Admin(String firstName, String lastName, String username, String password, long id) {
    super(firstName, lastName, username, password, id);
    classrooms = new ArrayList<Classroom>();
  }
    
  /*
  This method logs the admin in. If there are no admins in the system, it will create a new admin.
  @name login
  @date 1/15/2024
  @parameters StaffDatabase
  @returns Admin
  */
  public static Admin login(StaffDatabase staff) {
  
    // Variables
    ArrayList<String[]> accounts;
    Admin admin;
    String username;
    String password;
    Keyboard keyboard = new Keyboard();
    
    // If no accounts in file, create admin and log in
    if (staff.getAdmins().size() == 0) {
      admin = staff.createAdmin();
      return admin;
    }
    
    // Prompt for login
    keyboard.print("\nLogin to user\n");
    
    // Prompt for username and password
    username = keyboard.getWord("\nEnter Username: ");
    keyboard.getLine(""); // Avoid skipping line
    password = keyboard.getLine("Enter Password: ");
    
    // Search for account that matches username and password
    admin = staff.searchNames(username, password);
    
    // Confirmation message if logged in
    if (admin != null) keyboard.print("\n\nWelcome, " + admin.getFirstName() + " " + admin.getLastName() + "!");
    
    // Return admin
    return admin;
  }
   
  /*
  This method gets the ArrayList of Classrooms
  @name getClassrooms
  @date 1/15/2024
  @parameters void
  @returns ArrayList<Classroom>
  */
  public ArrayList<Classroom> getClassrooms() {
    return classrooms;
  }

  /*
  This method adds a classroom to the ArrayList of classrooms for the admin
  @name addClassroom
  @date 1/16/2024
  @parameters Classroom
  @returns void
  */
  public void addClassroom(Classroom classroom) {
  // Add classroom to classrooms if not in classrooms already
    if (!classrooms.contains(classroom)) classrooms.add(classroom);
  }


  /*
  This method removes a classroom from the ArrayList of classrooms for the admin
  @name deleteClassroom
  @date 1/17/2024
  @parameters Classroom
  @returns void
  */
  public void deleteClassroom(Classroom classroom) {
    classrooms.remove(classroom);
  }
}