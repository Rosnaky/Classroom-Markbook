/*
@file Main.java
@author Ronak Patel
@date 1/24/2024
@description This program simulates a markbook database for a class of students.

This is option B inspired by option A. You will notice I have a lot more features and functionality to simulate a Google Classroom environment. Currently, the program can only be accessed by admins. A future extension to this project would be to allow students to log in and view their own classroom and submit documents.
*/

class Main {
  public static void main(String[] args) {
    
    // Variables
    Admin admin = null;
    Keyboard keyboard = new Keyboard();
    StaffDatabase sd = new StaffDatabase("admins.txt", "classrooms.txt", "students.txt");
    ClassroomDatabase classrooms = sd.getClassroomDatabase();
    String choice = "";
    
    // Prompt until login
    while (admin == null) {
      admin = Admin.login(sd);
      
      // If invalid login
      if (admin == null) {
        keyboard.print("\nInvalid login. Please try again.");
      }
    }

    // Present initial menu
    sd.execute("Return", admin);
    
    // Present menu until choice is not to exit
    while (!choice.equals("Exit")) {
      // Get choice and execute it
      choice = keyboard.menu(admin, sd);
      sd.execute(choice, admin);
    }
  }
}