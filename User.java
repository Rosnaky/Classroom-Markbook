/*
@file User.java
@author Ronak Patel
@date 1/24/2024
@description This class represents user objects. Subclasses like student and admin extend this class
*/

// Imports
import java.util.ArrayList;

// User class
class User {

  // Instance variables
  private String firstName;
  private String lastName;
  private String username;
  private String password;
  private long id;
  private static final int SHIFT = 11;
  
  /*
  Constructor
  @parameters String, String, String, String, long
  */
  public User(String firstName, String lastName, String username, String password, long id) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.id = id;
  }

  /*
  This method gets the username
  @parameters void
  @returns String
  */
  public String getUsername() {
    return username;
  }

  /*
  This method gets the first name
  @parameters void
  @returns String
  */
  public String getFirstName() {
    return firstName;
  }

  /*
  This method gets the last name
  @parameters void
  @returns String
  */
  public String getLastName() {
    return lastName;
  }

  /*
  This method gets the id
  @parameters void
  @returns long
  */
  public long getID() {
    return id;
  }

  /*
  This method prompts user to edit username
  @parameters void
  @return void
  */
  public void editFirstName() {
    // Variables
    Keyboard keyboard = new Keyboard();
    String newFirstName;
    
    // Get new first name
    newFirstName = keyboard.getWord("Enter new first name:");
    
    // Set new first name
    this.firstName = newFirstName;
    
    // Confirmation message
    keyboard.print("\nFirst name changed to " + newFirstName);
  }

  /*
  This method prompts user to edit username
  @parameters void
  @return void
  */
  public void editLastName() {
  
    // Variables
    Keyboard keyboard = new Keyboard();
    String newLastName;
    
    // Get new first name
    newLastName = keyboard.getWord("Enter new last name:");
    
    // Set new first name
    this.lastName = newLastName;
    
    // Confirmation message
    keyboard.print("\nLast name changed to " + newLastName);
  }
  
  /*
  This method prompts user to edit username
  @parameters void
  @return void
  */
  public void editUsername() {
  
    // Variables
    Keyboard keyboard = new Keyboard();
    String newUsername;
    
    // Get new username
    newUsername = keyboard.getWord("Enter new username (one word only):");
    
    // Set new username
    this.username = newUsername;
    
    // Confirmation message
    keyboard.print("\nUsername changed to " + newUsername);
  }

  /*
  This method prompts user to edit password
  @parameters void
  @return void
  */
  public void editPassword() {
  
    // Variables
    Keyboard keyboard = new Keyboard();
    String newPassword;
    
    // Get new username
    newPassword = keyboard.getLine("Enter new password:");
    
    // Set new username
    this.password = newPassword;
    
    // Confirmation message
    keyboard.print("\nPassword changed to " + newPassword);
  }

  /*
  This method returns the user's password encrypted using caesar shift
  @parameters void
  @returns string
  */
  public String getEncryptedPassword() {
    // Variables
    String result = "";
    char c;
    
    // Iterate over all chars
    for (int i = 0; i < password.length(); i++) {
      c = password.charAt(i);

      // Add shifted char to result
      result += (char) (((int) c + i + SHIFT + 255) % 255);
    }

    // Return result
    return result;
  }

  /*
  This method returns a string decrypted for the user's password
  @parameters String
  @returns String
  */
  public static String decrypt(String password) {
    // Variables
    String result = "";
    char c;

    // Iterate over all chars
    for (int i = 0; i < password.length(); i++) {
      c = password.charAt(i);

      // Add shifted char to result
      result += (char) (((int) c - i - SHIFT + 255) % 255);
    }

    // Return result
    return result;
  }
  

  /*
  This method returns a boolean value depending if a username and password match with the current objects's username and password
  @parameters String, String
  @returns boolean
  */
  public boolean isMatch(String username, String password) {
    // Return if match
    return username.equals(this.username) && password.equals(this.password);
  }
}