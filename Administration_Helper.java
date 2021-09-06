/* 
                                                     _______________________        
                                                    |                       |
                                                    | Administration_Helper |
                                                    |_______________________|
                                                          @JEEL PATEL

*/

// Takes data from Studentdetail.txt & Collegedetail.txt  file or same type of formatted datafile.
import java.util.*;
import java.io.*;

public class Administration_Helper {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Account ac = new Account();

        ac.login();

    }
  
}

// Student class inherits College class
// Student class has methods like getNstore, get, setPreference
// InsertIntoRanklist, listofallocation, allotment, getMyClg.

class Student extends College {
    String name;
    int id;
    double marks;
    String preference;
    String myclg;

    Student() {
        super();
    }

    Student(int id, String name, double marks) {
        super();
        this.id = id;
        this.name = name;
        this.marks = marks;

        this.preference = getdefault(); // default preference.

    }

    String getdefault() {
        String temp = "";
        for (int i = 1; i <= noc; i++) {
            if (i > 1)
                temp += ",";
            temp += i;
        }

        return temp;

    }

    // to change preference from default to new preference.
    public void setPreference(int myid) {

        try {
            String newPreference;
            while (true) {
                String t1 = s1[0].name, t2 = c1[0].ClgName;
                System.out.println(
                        "Set your preference in this manner : Ex: 2,4,3,1,..  and the numbers between 1 to no. of college and no more than once.");
                newPreference = sc.next();
                newPreference += ',';
                int map[] = new int[noc + 1];
                int i;
                for (i = 1; i <= noc; i++)
                    map[i] = 0;
                String temp = "";
                for (i = 0; i < newPreference.length(); i++) {

                    if (newPreference.charAt(i) == ',') {
                        int t = Integer.parseInt(temp);
                        if (t > noc) {
                            System.out.println("Invalid input type");
                            break;
                        }
                        map[t]++;
                        temp = "";
                    } else {
                        temp += newPreference.charAt(i);
                    }

                }
                if (i == newPreference.length())
                    break;

            }
            // this.preference = newPreference;
            for (int i = 0; i < noc; i++) {
                if (s[i].id == myid) {
                    s[i].preference = newPreference;
                }
            }
            System.out.println("\n~~~Your Preference successfully Changed~~~\n");
        } catch (Exception e) {
            System.out.println("\n***This feature is not available yet.***\n");
        }

    }

    // initilize the data value like Student name,Student id,total marks & store
    // into obj array.
    public void getNstore() {

        File file = new File("C:\\Users\\JEEL\\OneDrive\\Desktop\\testing\\Studentdetail.txt");

        try {

            sc = new Scanner(file);
            int it = 0;
            String temp_name;
            int temp_marks;
            while ((sc.hasNextLine())) {
                String[] ln = sc.nextLine().split(",", 2);
                if (ln[0].length() == 0)
                    break;

                temp_name = ln[0];
                temp_marks = Integer.parseInt(ln[1]);

                s1[it] = new Student(it + 1, temp_name, temp_marks);
                it++;
            }

            N = it;
            username[N] = "admin";
            password[N] = "admin";
            System.out.println("\n~~~Data successfully fatched.~~~\n");

        } catch (Exception e) {
            System.out.println("File is not available.");

        }

        for (int i = 0; i < N; i++) {

            InsertIntoRankList(s1[i]);
        }

        int i = 0;
        while (!Ranklist.isEmpty()) {
            s[i] = new Student(i + 1, "a", 1);
            s[i] = Ranklist.peek();
            Ranklist.poll();
            i++;
        }

    }

    // add obj of Student in Priority Queue.
    public void InsertIntoRankList(Student s) {

        Ranklist.add(s);

    }

    // get detail of student Rankwise.
    public void get() {
        try {

            System.out.println("----------------------------------------------------------------------------");
            System.out.println("Students served in their merit order\n");

            String space = " ";
            System.out.println("Rank   " + "ID  " + "Name              " + "Marks  ");
            for (int i = 0; i < N; i++) {
                System.out.println((i + 1) + space.repeat(6 - (int) Math.log10(i + 1)) + s[i].id
                        + space.repeat(3 - (int) Math.log10(s[i].id)) + s[i].name
                        + space.repeat(18 - s[i].name.length()) + s[i].marks);
            }
            System.out.println("----------------------------------------------------------------------------");
        } catch (NullPointerException exception) {
            System.out.println("\n***Information is not available yet.***\n");
        }

    }

    // get list of allocated college on merit-base.
    // get detail of student Rankwise.
    public void listofallocation() {
        try {
            String temp = s[0].myclg;
            if (temp == null)
                throw new NullPointerException("");

            System.out.println("----------------------------------------------------------------------------");
            System.out.println("College allocated to Students upon merit-base.\n");

            String space = " ";
            System.out.println("Rank   " + "ID  " + "Name              " + "Marks  " + "  College name  ");
            for (int i = 0; i < N; i++) {
                System.out.println((i + 1) + space.repeat(6 - (int) Math.log10(i + 1)) + s[i].id
                        + space.repeat(3 - (int) Math.log10(s[i].id)) + s[i].name
                        + space.repeat(18 - s[i].name.length()) + s[i].marks
                        + space.repeat(6 - (int) Math.log10(s[i].marks)) + s[i].myclg);
            }
            System.out.println("----------------------------------------------------------------------------");
        } catch (NullPointerException exception) {
            System.out.println("\n***Information is not available yet.***\n");
        }

    }

    // College Allotment
    // fills data from file with well formated
    void allotment() {

        try {
            int i = 0, j = 0;
            for (i = 0; i < N; i++) {

                String temp = "";
                for (j = 0; j < s[i].preference.length(); j++) {

                    if (s[i].preference.charAt(j) == ',') {
                        int t = Integer.parseInt(temp);
                        t--;
                        if (c1[t].AvailableSeats > 0) {
                            c1[t].AvailableSeats--;
                            s[i].myclg = c1[t].ClgName;
                            s1[s[i].id - 1].myclg = c1[t].ClgName;
                            break;
                        }
                        temp = "";
                    } else {
                        temp += s[i].preference.charAt(j);
                    }

                }

                if (j >= s[i].preference.length()) {
                    s[i].myclg = "N/A";
                    s1[s[i].id - 1].myclg = "N/A";
                }

            }
            System.out.println("\n~~~Task Completed~~~\n");
            fillClgDetails();
        } catch (Exception e) {
            System.out.println("\n***You have to fill student before.***\n");
        }
    }

    public void getMyClg(int myid) {

        try {
            String temp = s[myid].myclg;
            if (temp == null)
                throw new NullPointerException("");
            System.out.print("\nYour College Name : " + temp + "\n");

        } catch (Exception e) {
            System.out.println("\n***Information is not available yet.***\n");
        }

    }
}

// comparator for priority queue in descanding order of marks.
class DescOrder implements Comparator<Student> {
    public int compare(Student s1, Student s2) {
        if (s1.marks < s2.marks)
            return 1;
        else if (s1.marks > s2.marks)
            return -1;
        return 0;
    }
}

// Account class which inherits Student class.
class Account extends Student {

    Account() {

        super();

        for (int i = 0; i < N; i++) {

            username[i] = "student" + (i + 1);
            password[i] = "student" + (i + 1);

        }

        username[N] = "admin";
        password[N] = "admin";

    }

    // login functionality.
    public void login() {
        while (true) {
            int type = putPassword();
            if (type == 0) {
                System.out.println("\n~~~Successfully login with admin mode !~~~\n");
                boolean loop = true;
                while (loop)
                    loop = adminMenu();

            } else if (type > 0) {
                System.out.println("\n~~~Successfully login with student mode !~~~\n");
                try {
                    System.out.println("Welcome " + s1[type - 1].name + ",");
                } catch (Exception e) {
                    System.out.println("\n***Data is not available.***\n");
                }
                boolean loop = true;
                while (loop)
                    loop = studentMenu(type);

            } else if (type == -1) {
                System.out.println("\n***Wrong password !***\n");
            } else
                System.out.println("\n***Username not found !***\n");

            int WantToExit = 2;
            while (true) {
                WantToExit = Exit();
                if (WantToExit == 0 || WantToExit == 1)
                    break;
            }

            if (WantToExit == 1) {
                System.out.println("\n$$$ Thanks for visit. $$$");
                System.out.println("____________________________________________________________________________\n");
                break;
            }

        }
    }

    // It has 2 face.
    // When logged from student's id.
    static boolean studentMenu(int myid) {
        Student temps = new Student();
        System.out.println("Enter choice :");
        System.out.println("1. get all students detail 2. for get college details 3. for set preference");
        System.out.println("4. My allocated college    5. get list of allocation  6. Logout");
        int x = getInt(1, 6);

        switch (x) {
            case 1:
                temps.get();
                break;
            case 2:
                temps.getClgDetail();
                break;
            case 3:
                temps.setPreference(myid);
                break;
            case 4:
                temps.getMyClg(myid);
                break;
            case 5:
                temps.listofallocation();
                break;
            case 6:
                return false;
        }
        return true;
    }

    // When logged from admin's id.
    static boolean adminMenu() {
        Student temps = new Student();
        System.out.println("Enter choice :");
        System.out.println("1. for fill student detail 2. for get student detail  3. for fill college details ");
        System.out.println("4. for get college details 5. for change No. of seats 6. Allocate seats  ");
        System.out.println("7. get List of allocation  8. Logout ");
        int x = getInt(1, 8);
        switch (x) {
            case 1:
                temps.getNstore();

                break;
            case 2:
                temps.get();

                break;
            case 3:
                temps.fillClgDetails();
                break;

            case 4:
                temps.getClgDetail();
                break;

            case 5:
                temps.changeTotalSeats();
                break;

            case 6:
                temps.allotment();
                break;
            case 7:
                temps.listofallocation();
                break;

            case 8:
                return false;
        }
        return true;

    }

    // Ask for username and password
    public int putPassword() {
        System.out.println("Enter username : ");
        String tempuser = sc.next();
        System.out.println("Enter password : ");
        String temppass = sc.next();
        return varify(tempuser, temppass);

    }

    // Ask for fully Exit from program.
    public int Exit() {
        try {
            System.out.println("\nEnter choice: ");
            System.out.println("Press 0. for Login ");
            System.out.println("Press 1. for close the program \n ");
            int WantToExit = sc.nextInt();
            if (WantToExit < 0 && WantToExit > 1)
                System.out.println("Please Enter valid input 0 or 1");

            return WantToExit;
        } catch (InputMismatchException exception) {
            sc.next();
            System.out.println("Please Enter valid input 0 or 1");
            return 2;
        }
    }

    // Varify valid username and password
    int varify(String u, String p) {
        for (int i = 0; i < N + 1; i++) {
            if (u.compareTo(username[i]) == 0) {
                if (p.compareTo(password[i]) == 0 && i == N)
                    return 0; // 1 for successfully login with admin mode!!
                else if (p.compareTo(password[i]) == 0) {

                    int myid = Integer.parseInt(u.substring(7));
                    System.out.println(myid);
                    return myid; // 2 for successfully login with student mode!!
                } else
                    return -1; // -1 for wrong password
            }
        }
        return -2; // -2 for not found

    }

}

// College class
class College {

    public static int N = 100000, noc = 1000, f = 0; // N = no. of students noc=no. of colleges.
    Scanner sc = new Scanner(System.in);
    public static String[] username = new String[N + 2];
    public static String[] password = new String[N + 2];

    public static PriorityQueue<Student> Ranklist = new PriorityQueue<Student>(new DescOrder());

    // object with inorder to id of students data
    public static Student s1[] = new Student[N];
    // object with order of merit.
    public static Student s[] = new Student[N];

    int ClgIndex;
    String ClgName;
    int AvailableSeats;
    public static College c1[] = new College[noc];

    College(int ClgIndex, String ClgName, int AvailableSeats) {

        this.AvailableSeats = AvailableSeats;
        this.ClgName = ClgName;
        this.ClgIndex = ClgIndex;

    }

    College() {
        ;
    }

    // initilize c1[] obj of college class
    // fills data from file with well formated
    void fillClgDetails() {
        int i = 0;

        File file = new File("C:\\Users\\JEEL\\OneDrive\\Desktop\\testing\\Collegedetail.txt");

        try {

            sc = new Scanner(file);

            String temp_name;
            int temp_seats;
            while ((sc.hasNextLine())) {

                String[] ln = sc.nextLine().split(",", 2);
                if (ln[0].length() == 0)
                    break;

                temp_name = ln[0];
                temp_seats = Integer.parseInt(ln[1]);

                c1[i] = new College(i + 1, temp_name, temp_seats);
                i++;
            }
            noc = i;
            if (f == 0)
                System.out.println("\n~~~Successfully Filled list of colleges.~~~\n");
            f = 1;

        } catch (Exception e) {
            System.out.println("File is not available.");

        }

    }

    // List of colleges with Available seats of Index no.
    void getClgDetail() {
        try {
            int i = 0;
            String space = " ", temp = c1[0].ClgName;
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("Index No. " + "College Name   " + "Available Seats");
            for (i = 0; i < noc; i++) {
                System.out.println(c1[i].ClgIndex + space.repeat(9 - (int) Math.log10(c1[i].ClgIndex)) + c1[i].ClgName
                        + space.repeat(15 - c1[i].ClgName.length()) + c1[i].AvailableSeats);
            }
            System.out.println("----------------------------------------------------------------------------");
        } catch (NullPointerException exception) {
            System.out.println("\n***Information is not available yet***\n");
        }
    }

    // Admin can change No. of Seats of Colleges.
    void changeTotalSeats() {

        try {
            getClgDetail();
            System.out.println("\nType College index which your want to edit :");
            int x = getInt(1, 10);
            System.out.println("\nEnter new available seats :");

            int xx = getInt(0, 500);

            c1[x - 1].AvailableSeats = xx;
            System.out.println("\nSuccessfully changed detail.\n");
        } catch (Exception e) {
            System.out.println("\n***You have to fill college detail before.***\n");
        }

    }

    // for scan valid integer in range
    static int getInt(int l, int r) {

        Scanner sc = new Scanner(System.in);
        int x;

        while (true) {
            try {

                x = sc.nextInt();

                if (x >= l && x <= r)
                    break;
                else
                    System.out.println("Please Enter valid input from " + l + " to " + r);

            } catch (InputMismatchException exception) {
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                sc.next();
                System.out.println("Please Enter valid input from " + l + " to " + r);
            }
        }
        return x;
    }
}

/*
    To compile and run program
    ->    javac Administration_Helper.java   
    ->    java Administration_Helper 

    Login 
    ->  for admin mode :- username & password = admin
    ->  for student mode :- username & password = student?  (where ? is Id of student).

*/