
package com.mycompany.to_do_list;

import java.text.ParseException;
import java.util.Scanner;
import java.util.Vector;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws ParseException {
        Vector<ToDoList> tasks = new Vector<ToDoList>();

        try {
            tasks = File_Reader.checkFileForData();
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            // e1.printStackTrace();
            System.out.println("No Previous Data Availabe\n");
        }

        // *********Importing Data From Text File*********

        Scanner input = new Scanner(System.in);

        String subject, date, time, location, description;
        String senderEmail, password, recieverEmail;
        int removalChoice;
        char choice;
        Boolean dateCheck, timeCheck;

        // *******Menu Presented To User To Perform Operations*******

        while (true) {

            System.out.println("\t\t----------ORGANIZE YOUR TASKS WITH THIS APPLICATION----------\n");
            System.out.println("Press 1 To Enter A New Task.");
            System.out.println("Press 2 To Delete A Task.");
            System.out.println("Press 3 To Search A Task.");
            System.out.println("Press 4 To Update A Task.");
            System.out.println("Press 5 To Mark Task As Done.");
            System.out.println("Press 6 To Check Tasks That Have Been Done.");
            System.out.println("Press 7 To Check Your To Do List.");
            System.out.println("Press 8 To Check Your To Do List History.");
            System.out.println("Press 9 To Email Your To Do List.");
            System.out.println("Press E To Exit The Application.");
            System.out.println();
            System.out.print("Enter Your Choice: ");
            choice = input.next().charAt(0);
            // System.out.print("\033[H\033[2J");
            // System.out.flush();

            switch (choice) {
                case '1': // Creating New Task
                    System.out.println("\nSubject, Date and Time Must Not Be Empty");

                    System.out.println("\nEnter Subject: ");
                    subject = input.nextLine();
                    while (subject.length() == 0)
                        subject = input.nextLine();

                    System.out.println("\nEnter Description: ");
                    description = input.nextLine();

                    System.out.println("\nEnter Location: ");
                    location = input.nextLine();

                    System.out.println("\nEnter Date in Format (dd-MM-yyyy): ");
                    date = input.nextLine();
                    try {
                        ToDoList.dateFormat.parse(date);
                        dateCheck = true;
                    } catch (ParseException e) {
                        dateCheck = false;
                        System.out.println("Wrong Format. Enter Date Again in Format (dd-MM-yyyy)\n");
                    }

                    while (date.length() == 0 || !dateCheck) {
                        date = input.nextLine();

                        try {
                            ToDoList.dateFormat.parse(date);
                            dateCheck = true;
                        } catch (ParseException e) {
                            dateCheck = false;
                            System.out.println("Wrong Format. Enter Date Again in Format (dd-MM-yyyy)\n");
                        }
                    }

                    System.out.println("\nEnter Start Time in Format (HH:mm): ");
                    time = input.nextLine();
                    try {
                        ToDoList.timeFormat.parse(time);
                        timeCheck = true;
                    } catch (ParseException e) {
                        timeCheck = false;
                        System.out.println("Wrong Format. Enter Time Again in Format (HH:mm)\n");
                    }

                    while (time.length() == 0 || !timeCheck) {
                        time = input.nextLine();

                        try {
                            ToDoList.timeFormat.parse(time);
                            timeCheck = true;
                        } catch (ParseException e) {
                            timeCheck = false;
                            System.out.println("Wrong Format. Enter Time Again in Format (HH:mm)\n");
                        }
                    }

                    ToDoList task = new ToDoList(time, date, subject, description, location);

                    tasks.add(task);
                    File_Writer.saveDataToHistory(tasks.lastElement());
                    File_Writer.saveToHistory(tasks.lastElement());
                    tasks = ToDoList.sortSchedule(tasks);
                    File_Writer.saveToFile(tasks);
                    File_Writer.saveData(tasks);

                    break;

                case '2': // Deleting A Task
                    System.out.println("\nEnter Task Number");
                    removalChoice = input.nextInt();
                    tasks.remove((removalChoice - 1));
                    tasks = ToDoList.sortSchedule(tasks);
                    File_Writer.saveToFile(tasks);
                    File_Writer.saveData(tasks);

                    break;

                case '3': // Searching Task According To Subject
                    System.out.println("\nEnter Subject of Task to Search");
                    subject = input.nextLine();
                    subject = input.nextLine();
                    ToDoList.searchTask(tasks, subject);
                    break;

                case '4': // Update task
                    System.out.println("Leave Empty If You Don't Want To Change The Field");
                    System.out.println("Enter Task Number");
                    int taskNum = input.nextInt();
                    System.out.println("Enter Subject.");
                    subject = input.nextLine();
                    subject = input.nextLine();
                    System.out.println("Enter Description.");
                    description = input.nextLine();
                    System.out.println("Enter Location.");
                    location = input.nextLine();
                    System.out.println("Enter Date.");
                    date = input.nextLine();
                    System.out.println("Enter Time.");
                    time = input.nextLine();
                    if (subject.length() != 0)
                        tasks.elementAt(taskNum - 1).setSubject(subject);
                    if (description.length() != 0)
                        tasks.elementAt(taskNum - 1).setSubject(description);
                    if (location.length() != 0)
                        tasks.elementAt(taskNum - 1).setSubject(location);
                    if (date.length() != 0)
                        tasks.elementAt(taskNum - 1).setSubject(date);
                    if (time.length() != 0)
                        tasks.elementAt(taskNum - 1).setSubject(time);

                    tasks = ToDoList.sortSchedule(tasks);
                    File_Writer.saveToFile(tasks);
                    File_Writer.saveData(tasks);

                    break;

                case '5': // Mark Task As Done
                    System.out.println("Enter Task Number");
                    taskNum = input.nextInt();
                    tasks.elementAt(taskNum - 1).setTaskAsDone();
                    File_Writer.saveToFile(tasks);
                    File_Writer.saveData(tasks);
                    break;

                case '6': // Display Finished Tasks
                    ToDoList.displayFinishedTasks(tasks);
                    System.out.println();
                    break;

                case '7': // Display Current To Do List
                    System.out.println();
                    ToDoList.displayToDoList(tasks);
                    break;

                case '8': // Display To Do List History
                    System.out.println();
                    try {
                        ToDoList.displayHistory();
                    } catch (FileNotFoundException e1) {
                        // TODO Auto-generated catch block
                        // e1.printStackTrace();
                        System.out.println(
                                "History Was Not Found!\n");
                    }
                    break;

                case '9': // Emailing Current To Do List and History
                    System.out.print("Enter your gmail address: ");
                    senderEmail = input.next();
                    while (!senderEmail.endsWith("@gmail.com")) {
                        System.out.print("Enter your gmail address: ");
                        senderEmail = input.next();
                    }

                    System.out.print("Enter your Password: ");
                    password = input.next();

                    System.out.print("Enter recievers gmail address: ");
                    recieverEmail = input.next();
                    while (!senderEmail.endsWith("@gmail.com")) {
                        System.out.print("Enter your gmail address: ");
                        senderEmail = input.next();
                    }
                    try {
                        EmailList.send(senderEmail, password, recieverEmail);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        // e.printStackTrace();
                        System.out.println(
                                "Email Was Not Sent! Try Again With Proper Credentials or Wait For Some Time\n");
                    }
                    // EmailList.send("testing11111testing@gmail.com",
                    // "assignment11!","testing00000testing@gmail.com");
                    break;

                case 'E': // Exit Application
                case 'e':
                    input.close();
                    return;
            }

        }

    }

}