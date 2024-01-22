/*
@file Keyboard.java
@author Ronak Patel
@date 1/24/2024
@description This class is used to get user input. Several methods related to input/output are also included. 
*/

// Imports
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.PrintWriter;

// Keyboard class
public class Keyboard {

  // Instance variables
  Scanner keyboard;
  HashMap<String, ArrayList<String>> keyMap;
   
  // Constructor
  public Keyboard() {
    // Initialize Scanner object
    keyboard = new Scanner(System.in);
  }

  /*
  Method to get a double user input
  @parameters String
  @returns double
  */
  public double getDouble(String prompt) {
    // Prompt user
    System.out.print(prompt + "\n");
    
    // Return result
    return keyboard.nextDouble();
  }

  /*
  Method to get an integer user input
  @parameters String
  @returns int
  */
  public int getInt(String prompt) {
    // Prompt user
    System.out.print(prompt + "\n");
    
    // Return result
    return keyboard.nextInt();
  }

  /*
  Method to get a String user input
  @parameters String
  @returns String
  */
  public String getWord(String prompt) {
    // Prompt user
    System.out.print(prompt + "\n");
    
    // Return result
    return keyboard.next();
  }

  /*
  Method to get a line user input
  @parameters String
  @returns String
  */
  public String getLine(String prompt) {
    // Prompt user 
    System.out.print(prompt + "\n");
    
    // Return result
    return keyboard.nextLine();
  }

  /*
  Method to get usernames and passwords
  @parameters String
  @returns ArrayList<String[]>
  */
  public ArrayList<String[]> getAccounts(String file) { 
    // Variables
    BufferedReader br = null;
    String word;
    int index = 0;
    final int N = 5;
    String[] data = new String[N];
    ArrayList<String[]> list = new ArrayList<String[]>();
    
    // Handle errors
    try {
      // Open file
      br = new BufferedReader(new FileReader(file));
      
      // Read words until the end of the file is reached
      while ((word = br.readLine()) != null) {
        // Get username, password, and id. Once getting id, add to list
        if (index != N-1) {
          data[index] = word;
        }
        else {
          data[N-1] = word;
          list.add(data);
          
          // Create new string and reset index
          data = new String[N];
          index = -1;
        }
        
        // Increment index
        index++;
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    // After try catch
    finally {
      // Try to close BufferReader
      try {
        br.close();
      }
      catch (IOException ex) {
        ex.printStackTrace();
      }   
    }
    
    // Return data
    return list;
  }

  /*
  This method writes a string to a file
  @parameters String, String
  @returns void
  */
  public void writeToFile(String file, String data) {
    // Try catch for error handling
    try {
      // Create writer instance for the file
      FileWriter fw = new FileWriter(file, true);
      BufferedWriter bw = new BufferedWriter(fw);
      
      // Write data in a line
      bw.write(data + "\n");
      
      // Close writers
      bw.close();
      fw.close();
      } catch (IOException e) {
      
      // Error handling
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  /*
  This method prints message to user
  @parameters String
  @returns void
  */
  public void print(String data) {
    // Print data
    System.out.println(data);
  }
  

  /*
  This method prints the menus and returns a String indicating which option was chosen
  @parameters Admin, StaffDatabase
  @returns String
  */
  public String menu(Admin a, StaffDatabase sd) {
    // Variables
    String choice;
    
    // Initialize the options
    initializeOptions(a, sd);
    
    // Create line break
    System.out.println("----------------------------");
    
    // Use recursive function to get menu choice
    return choice = getMenuChoice("");
  }

  /*
  This method has a recursive call until a leaf choice is chosen
  @parameters String
  @returns String
  */
  private String getMenuChoice(String node) {
    // Variables
    int choice;
    ArrayList<String> options;
    Keyboard keyboard = new Keyboard();
    
    // If node is leaf node, then return node
    if (!keyMap.containsKey(node)) {
      return node;
    }
    
    // Get all options available
    options = keyMap.get(node);
    
    // Print all options after a prompt
    keyboard.print("\n\nMenu:");
    for (int i = 1; i <= options.size(); i++) {
      keyboard.print(i + ") " + options.get(i-1));
    }
    
    // Get choice
    choice = keyboard.getInt("\nEnter choice number (1-" + options.size() + "): ");
    
    // If choice is not valid, then try again. Otherwise, proceed to next
    if (choice < 1 || choice > options.size()) {
      // Print error message
      keyboard.print("\nInvalid choice. Try again.");
      return getMenuChoice(node);
    }
    else {
      return getMenuChoice(options.get(choice-1));
    }
  }
   

  /*
  This function populates keymap with the admin's options
  @parameters Admin, StaffDatabase
  @returns void
  */
  private void initializeOptions(Admin a, StaffDatabase sd) {
    // Initialize hashmap
    keyMap = new HashMap<String, ArrayList<String>>();
    
    // Initial
    keyMap.put("", new ArrayList<String>() {
      {
        add("Classrooms");
        add("Account Settings");
        add("Database");
        add("Exit");
      }
    }
              );
    
    // Classrooms
    keyMap.put("Classrooms", new ArrayList<String>() {
      {
        add("Return");
        add("Add Classroom");
        
        // Get classrooms
        ArrayList<Classroom> classrooms = a.getClassrooms();
        
        // If classrooms is not null, add each classroom
        if (classrooms != null) {
          for (Classroom classroom : classrooms) {
            add(classroom.getName() + " (" + classroom.getID() + ")");
            
            // For each classroom, add menu
            keyMap.put(classroom.getName() + " (" + classroom.getID() + ")", new ArrayList<String>() {
              {
                add("Return");
                add("Assignments (" + classroom.getID() + ")");
                add("Students (" + classroom.getID() + ")");
                add("Admins (" + classroom.getID() + ")");
                add("Delete Classroom (" + classroom.getID() + ")");
              }
            }
                      );
            
            // For each classroom, add Assignments menu
            keyMap.put("Assignments (" + classroom.getID() + ")", new ArrayList<String>() {
              {
                add("Return");
                add("Create Assignment (" + classroom.getID() + ")");
                
                // Add each assignment
                for (Assignment a : classroom.getAssignmentDatabase().getAssignments()) {
                  add(a.getName() + " (" + classroom.getID() + ") (" + a.getID() + ")");
                  
                  // For each assignment, add menu to change grade
                  keyMap.put(a.getName() + " (" + classroom.getID() + ") (" + a.getID() + ")", new ArrayList<String>() {
                    {
                      add("Return");
                      add("Delete Assignment (" + classroom.getID() + ") (" + a.getID() + ")");
                      add("Average Grade (" + classroom.getID() + ") (" + a.getID() + ")");
                      
                      // Add option to change grade for each student
                      for (Student s : classroom.getStudents()) {
                        add("Change Assignment Grade: " + s.getFirstName() + " " + s.getLastName() + ": " + (s.searchID(a.getID()).getGrade() != -1 ? s.searchID(a.getID()).getGrade() : "Ungraded") + " (" + classroom.getID() + ") (" + a.getID() + ") (" + s.getID() + ")");
                      }
                    }
                  }
                            );
                }
              }
            }
                      );
            
            
            // For each classroom, add Students menu
            keyMap.put("Students (" + classroom.getID() + ")", new ArrayList<String>() {
              {
                add("Return");
                add("Add Student (" + classroom.getID() + ")");
                
                // Add each student
                for (Student s : classroom.getStudents()) {
                  add("Classroom Student " + s.getFirstName() + " " + s.getLastName() + " (" + classroom.getID() + ") (" + s.getID() + ")");
                  
                  keyMap.put("Classroom Student " + s.getFirstName() + " " + s.getLastName() + " (" + classroom.getID() + ") (" + s.getID() + ")", new ArrayList<String>() {
                    {
                    add("Return");
                    add("Delete Student from Classroom (" + classroom.getID() + ") (" + s.getID() + ")");
                    add("View Assignments (" + classroom.getID() + ") (" + s.getID() + ")");
                    }
                  }
                            );
                  
                  keyMap.put("View Assignments (" + classroom.getID() + ") (" + s.getID() + ")", new ArrayList<String>() {
                    {
                      add("Return");
                      
                      // Show each assignment
                      for (Assignment a : s.getAssignments()) {
                        if (classroom.getAssignmentDatabase().searchID(a.getID()) == null) continue;
                        add("Change Assignment Grade: " + a.getName() + ": " + (a.getGrade() != -1 ? a.getGrade() : "Ungraded") + " (" + classroom.getID() + ") (" + a.getID() + ") (" + s.getID() + ")");
                      }
                    }
                  }
                            );
                }
              }
            }
                      );

            // For each classroom, add admins menu
            keyMap.put("Admins (" + classroom.getID() + ")", new ArrayList<String>() {
              {
                add("Return");
                add("Add Admin to Classroom");

                // Add each admin
                for (Admin admin : classroom.getAdmins()) {
                  add(admin.getFirstName() + " " + admin.getLastName() + " (" + admin.getID() + ")");
                }
                
              }
            }
                      );

            // Add admin to classroom
            keyMap.put("Add Admin to Classroom", new ArrayList<String>() {
              {
                add("Return");

                // Add each admin
                for (Admin admin : sd.getAdmins()) {
                  // Add admin if not already in classroom
                  if (classroom.getAdmins().contains(admin)) continue;

                  add("Add Admin to Classroom: " + admin.getFirstName() + " " + admin.getLastName() + " (" + classroom.getID() + ") (" + admin.getID() + ")");
                }
              }
            }
                       );
            
            
            // Add student to classroom
            keyMap.put("Add Student (" + classroom.getID() + ")", new ArrayList<String>() {
              {
                add("Return");
                
                // Add each student
                for (Student s : sd.getStudentDatabase().getStudents()) {

                  // If classroom already contains the student, do not add
                  if (classroom.getStudents().contains(s)) continue;

                  // Add student option  
                  add("Add Student to Classroom (" + s.getFirstName() + " " + s.getLastName() + ") (" + classroom.getID() + ") (" + s.getID() + ")");
                }
              }
            }
                      );
          }
        }
      }
    }
              );
    
    // Account Settings
    keyMap.put("Account Settings", new ArrayList<String>() {
      {
        add("Return");
        add("Edit Admin First Name (" + a.getFirstName() + ")");
        add("Edit Admin Last Name (" + a.getLastName() + ")");
        add("Edit Admin Username (" + a.getUsername() + ")");
        add("Edit Admin Password");
      }
    }
              );
    
    // Database
    keyMap.put("Database", new ArrayList<String>() {
      {
        add("Return");
        add("Admins");
        add("Students");
        add("Purge");
      }
    }
              );
    
    // Students
    keyMap.put("Students", new ArrayList<String>() {
      {
        add("Return");
        add("Add Student to Database");
        
        // Get students
        for (Student s : sd.getStudentDatabase().getStudents()) {
          add("Student: " + s.getFirstName() + " " + s.getLastName() + " (" + s.getID() + ")");
          
          // For each student, add menu to delete student or edit username/password
          keyMap.put("Student: " + s.getFirstName() + " " + s.getLastName() + " (" + s.getID() + ")", new ArrayList<String>() {
            {
              add("Return");
              add("Edit Student First Name (" + s.getFirstName() + ") (" + s.getID() + ")");
              add("Edit Student Last Name (" + s.getLastName() + ") (" + s.getID() + ")");
              add("Edit Student Username (" + s.getUsername() + ") (" + s.getID() + ")");
              add("Edit Student Password (" + s.getID() + ")");
              add("Delete Student from Database (" + s.getID() + ")");
            }
          }
                    );
        }
      }
    }
              );
    
    
    // Database >> Admins
    keyMap.put("Admins", new ArrayList<String>() {
      {
        add("Return");
        add("Add Admin");
        
        // Get admins
        for (Admin admin : sd.getAdmins()) {
          // If admin is current Admin, continue
          if (admin == a) continue;
          
          // Add each admin
          add(admin.getUsername() + " (" + admin.getID() + ")");
          
          // For each admin, give option to delete
          keyMap.put(admin.getUsername() + " (" + admin.getID() + ")", new ArrayList<String>() {
            {
              add("Return");
              add("Delete Admin (" + admin.getID() + ")");
            }
          }
                    );
        }
      }
    }
              );
  }

  /*
  This method reads the information in the file and populates the database
  @parameters String, AssignmentDatabase
  @returns void
  */
  public void getAssignmentInfo(String file, AssignmentDatabase ad) {
    // Variables
    BufferedReader br = null;
    int n;
    String word;
    
    try {
      // Open file
      br = new BufferedReader(new FileReader(file));
      n = Integer.parseInt(br.readLine());
      
      // Iterate over all assignments
      for (int i = 0; i < n; i++) {
        // Create assignment with id and name
        word = br.readLine();
        Assignment a = new Assignment(br.readLine(), Integer.parseInt(word));
        
        // Add assignment to database
        ad.addAssignment(a);
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    // After try catch
    finally {
      // Try to close BufferReader
      try {
        br.close();
      }
      catch (IOException ex) {
        ex.printStackTrace();
      }   
    }
  }
  
  /*
  This method reads the information in the file and populates the database
  @parameters String, StaffDatabase
  @returns void
  */
  public void getClassroomInfo(String file, StaffDatabase sd) {
  
    // Variables
    BufferedReader br = null;
    String word;
    Classroom c;
    Student s;
    Assignment a;
    StudentDatabase studentDatabase = sd.getStudentDatabase();
    ClassroomDatabase cd = sd.getClassroomDatabase();
    int nCourses, nAdmins, nStudents, nAssignments;
    
    // Handle errors
    try {
      // Open file
      br = new BufferedReader(new FileReader(file));
      
      nCourses = Integer.parseInt(br.readLine());
      // Go through all courses
      for (int i = 0; i < nCourses; i++) {
        // Get name and id
        c = new Classroom(br.readLine(), Integer.parseInt(br.readLine()));
        
        // Get number of admins
        nAdmins = Integer.parseInt(br.readLine());
        // Go through all admins
        for (int j = 0; j < nAdmins; j++) {
        c.addAdmin(sd.searchID(Long.parseLong(br.readLine())));
        }
        
        // Get number of students
        nStudents = Integer.parseInt(br.readLine());
        // Go through all students
        for (int j = 0; j < nStudents; j++) {
          s = studentDatabase.searchID(Long.parseLong(br.readLine()));
          
          // Get number of assignments
          nAssignments = Integer.parseInt(br.readLine());
          // Go through all assignments
          for (int k = 0; k < nAssignments; k++) {
            a = new Assignment(br.readLine(), Integer.parseInt(br.readLine()));
            a.grade(Double.parseDouble(br.readLine()));
            s.addAssignment(a);
          }
          // Add student to classroom
          c.addStudent(s);
        }
        // Add classroom to classroom database
        cd.add(c);
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    // After try catch
    finally {
      // Try to close BufferReader
      try {
        br.close();
      }
      catch (IOException ex) {
        ex.printStackTrace();
      }   
    }
  }

  /*
  This method persists the classroom info in a file
  @parameters String, ClassroomDatabase
  @returns void
  */
  private void persistClassroomInfo(String file, ClassroomDatabase cd) {
    
    // Variables
    Classroom c;
    ArrayList<Student> students;
    ArrayList<Assignment> assignments;
    ArrayList<Admin> admins;
    
    // Erase file
    eraseFile(file);
    
    writeToFile(file, cd.getClassrooms().size()+"");
    
    // Loop through all courses
    for (int i = 0; i < cd.getClassrooms().size(); i++) {
      c = cd.getClassrooms().get(i);

      // Write name and id of course
      writeToFile(file, c.getName());
      writeToFile(file, c.getID()+"");  

      // Write number of admins. Then, for all admins in the classroom, write id of admin
      admins = c.getAdmins();
      writeToFile(file, admins.size()+"");
      for (int j = 0; j < admins.size(); j++) {
        writeToFile(file, admins.get(j).getID()+"");
      }
    
      // Write number of students. Then, for all students, write id of students and assignment info
      students = c.getStudents();
      writeToFile(file, students.size()+"");
      for (int j = 0; j < students.size(); j++) {
        writeToFile(file, students.get(j).getID()+"");

        // Write number of assignments. Then, for all assignments, write name, id, and grade
        assignments = students.get(j).getAssignments();
        writeToFile(file, assignments.size()+"");
        for (int k = 0; k < assignments.size(); k++) {
          writeToFile(file, assignments.get(k).getName()+"");
          writeToFile(file, assignments.get(k).getID()+"");
          writeToFile(file, assignments.get(k).getGrade()+"");
        }
      }
    }
  }

  /*
  This method persists the admin info in a file
  @parameters String, StaffDatabase
  @returns void
  */
  private void persistAdminInfo(String file, StaffDatabase sd) {
  
    // Erase content in file
    eraseFile(file);
    
    // Iterate over all admins
    for (Admin a : sd.getAdmins()) {
      // Write information in file
      writeToFile(file, a.getFirstName());
      writeToFile(file, a.getLastName());
      writeToFile(file, a.getUsername());
      writeToFile(file, a.getEncryptedPassword());
      writeToFile(file, a.getID()+"");
    }
  }

  /*
  This method persists the assignment info of a class in a file
  @parameters String, AssignmentDatabase
  @returns void
  */
  private void persistAssignmentsInfo(String file, AssignmentDatabase ad) {
    // Erase content in file
    eraseFile(file);
    
    // Write number of assignments
    writeToFile(file, ad.getAssignments().size()+"");
    
    // Iterate over all admins
    for (Assignment a : ad.getAssignments()) {
      // Write information in file
      writeToFile(file, a.getID()+"");
      writeToFile(file, a.getName()+"");
    }
  }

  /*
  This method persists all information in the respective file
  @parameters StaffDatabase
  @returns void
  */
  public void persistInfo(StaffDatabase sd) {
    persistAdminInfo(sd.getFile(), sd);
    persistClassroomInfo(sd.getClassroomDatabase().getFile(), sd.getClassroomDatabase());
    persistStudentsInfo(sd.getStudentDatabase().getFile(), sd.getStudentDatabase());
    
    for (Classroom c : sd.getClassroomDatabase().getClassrooms()) {
      persistAssignmentsInfo(c.getAssignmentDatabase().getFile(), c.getAssignmentDatabase());
    }
  }

  /*
  This method persists the students info of a class in a file
  @parameters String, StudentDatabase
  @returns void
  */
  public void persistStudentsInfo(String file, StudentDatabase sd) {
    // Erase content in file
    eraseFile(file);
    
    // Write number of students
    writeToFile(file, sd.getStudents().size()+"");
    
    // Iterate over all students
    for (Student s : sd.getStudents()) {
      // Write information in file
      writeToFile(file, s.getID()+"");
      writeToFile(file, s.getFirstName()+"");
      writeToFile(file, s.getLastName()+"");
      writeToFile(file, s.getUsername()+"");
      writeToFile(file, s.getEncryptedPassword()+"");
    }
  }

  /*
  This method reads the information in the file and populates the database
  @parameters String, StudentDatabase
  @returns void
  */
  public void getStudentsInfo(String file, StudentDatabase sd) {
    // Variables
    BufferedReader br = null;
    int n;
    Student s;
    Assignment a;
    String word;
    
    try {
      // Open file
      br = new BufferedReader(new FileReader(file));
      word = br.readLine();
      
      // If file is empty, cancel
      if (word == null) {
        return;
      }

      // Get number of students
      n = Integer.parseInt(word);
      
      // Iterate over all students
      for (int i = 0; i < n; i++) {
        // Create Student with id, first name, last name, username, password
        word = br.readLine();
        s = new Student(br.readLine(), br.readLine(), br.readLine(), User.decrypt(br.readLine()), Long.parseLong(word));
        
        // Add student to database
        sd.add(s);
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    // After try catch
    finally {
      // Try to close BufferReader
      try {
        br.close();
      }
      catch (IOException ex) {
        ex.printStackTrace();
      }   
    }
  }

  /*
  This is a helper function that erases a file's contents
  @parameters String
  @returns void
  */
  private void eraseFile(String file) {
    
    // Variables
    FileWriter fw = null;
    PrintWriter pw = null;
    
    // Catch error
    try {
      // Clear file contents
      fw = new FileWriter(file);
      pw = new PrintWriter(fw);
      pw.write("");
      pw.flush(); 
      pw.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    // After try catch
    finally {
      // Try to close BufferReader
      try {
        fw.close();
        pw.close();
      }
      catch (IOException ex) {
        ex.printStackTrace();
      }   
    }
  }
}