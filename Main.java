/*
@file Main.java
@author Ronak Patel
@date 1/24/2024
@description This program simulates a markbook database for a class of students.
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
    sd.execute("Return", admin);
    // Present menu until choice is not to exit
    while (!choice.equals("Exit")) {
      // Get choice and execute it
      choice = keyboard.menu(admin, sd);
      sd.execute(choice, admin);
    }
  }
}