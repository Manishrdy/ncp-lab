package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;
import java.util.regex.Matcher; 
import java.util.regex.Pattern; 
import java.sql.PreparedStatement; 

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Register {
	
	public static boolean isValid(String email) 
    { 
		// Validation checking for email input.
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) 
            return false; 
        return pat.matcher(email).matches(); 
    }

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		Connection con = null;
        PreparedStatement pstmt = null;
        
        //File reading..
        String filename = "C:\\Users\\d6mr0\\Desktop\\Resume\\Manish_Reddy_Resume.docx";
        File file = new File(filename);
        FileInputStream input = new FileInputStream(file);
        
     // SQL Query with table and attribute names.
        String query = "insert into form (first_name,last_name,email,password,uname,date,phone,"
        		+ "blood,address,file_pdf) VALUES (?,?,?,?,?,?,?,?,?,?)";
		
        // Taking input from user.
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter first your name: ");
		String first_name = sc.nextLine();
		System.out.println("Enter last your name: ");
		String last_name = sc.nextLine();
		System.out.println("Enter your email: ");
		String email = sc.nextLine();
		System.out.println("Enter your password: ");
		String password = sc.nextLine();
		System.out.println("Enter your username: ");
		String uname = sc.nextLine();
		System.out.println("Enter your date of birth: ");
		String date = sc.nextLine();
		System.out.println("Enter your phone number: ");
		long phone = sc.nextLong();
		System.out.println("Enter your blood group: ");
		String blood;  sc.nextLine();
		blood = sc.nextLine();
		System.out.println("Enter your address: ");
		String address = sc.nextLine();
		
		// If email input is validated then connection will establish.
		if (isValid(email)) {
			
			try {
				
				// Class.forName() : Here we load the driver’s class file into memory at the runtime.
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.out.println("Connecting to database.");
				
				//This class is used to register driver for a specific database type 
				//and to establish a database connection with the server via its getConnection() method.
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/manish",
						"root","root");
				
				//Executing sql query using Prepared Statment.
				pstmt = connection.prepareStatement(query);
				
				//Pushing user inputs into database using Prepared Statment.
				pstmt.setString(1, first_name);
				pstmt.setString(2, last_name);
				pstmt.setString(3, email);
				pstmt.setString(4, password);
				pstmt.setString(5, uname);
				pstmt.setString(6, date);
				pstmt.setLong(7, phone);
				pstmt.setString(8, blood);
				pstmt.setString(9, address);
				pstmt.setBinaryStream(10, input);
				
				System.out.println("Inserting into database.");
				
				int affectedRows = pstmt.executeUpdate();
				System.out.println("Reading file " + file.getAbsolutePath());
				System.out.print("");
				System.out.println("Store file in the database.");
				System.out.print("");
	            System.out.println(affectedRows + " row(s) affected !!");
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.print("Enter Proper email.");
			System.out.print("");
			System.out.print("Program terminated...");
		}

	}

}
