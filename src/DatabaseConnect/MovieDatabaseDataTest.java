package DatabaseConnect;

//import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;



import java.util.ArrayList;
import java.util.Scanner;

public class MovieDatabaseDataTest 
{
    private Connection conn = null;
    public static void main(final String args[])
    {
        final MovieDatabaseDataTest conn = new MovieDatabaseDataTest();
        conn.Menu();
        if (conn != null)
        {
            conn.close();            
        }            
    }

    public void Menu()
    {
        final Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        boolean loop = true;
        while(loop == true)
        {
            
            System.out.println("1) Customer Maintenance\n2) Movie Maintenence\n3) Download Maintenence\n4) Rental Maintenence\n5) Display Overdue Fees\n6) Quit");
            int userInput = scanner.nextInt();
            switch(userInput)
            {
                case 1:
                    System.out.println("1) Add Customer\n2) Modify Customer\n3) Delete Customer\n4) Display Purchase History\n5) Display Overdue Fees\n6) Calculate customer collection route and distance");
                    userInput = scanner.nextInt();
                    switch(userInput)
                    {
                        case 1:
                            addCustomer();
                        break;
                        case 2:
                            ModifyCustomer();
                        break;
                        case 3:
                            DeleteCustomer();
                        break;
                        case 5:
                            DisplayOverdueFees();
                        break;
                        case 6:
                            DoTheDijkstraThing();
                        break;
                    }
                break;
                case 2:
                    System.out.println("1)Add Movie\n2) Modify Movie\n3) Delete Movie");
                break;
                case 3:
                    System.out.println("1)Add Download\n2) Modify Download\n3) Delete Download");
                break;
                case 4:
                    System.out.println("1) Add Rental\n2) Modify Rental\n3) Delete Rental");
                    userInput = scanner.nextInt();
                    switch(userInput)
                    {
                        case 1:
                            AddRental();
                        break;
                    }
                break;
                case 5:
                    DisplayOverdueFees();
                break;
                case 6:
                    loop = false;
                break;
            }
            
        }
        scanner.close();
    }

    public boolean addCustomer()
    {
        boolean bInsert = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement();
            String sql = "INSERT INTO CustomerAddress (AddressID,HouseNo,Street,Town,County,PostCode) "
                    + "VALUES (6, 10, 'Downing Street', 'Westminster', 'London', 'SW1A 2AA');";
            stmt.executeUpdate(sql);

            stmt = conn.createStatement();
            sql = "INSERT INTO Customer (CustomerID,CustomerName,CustomerAddressID,PhoneNumber,DateofBirth) "
                    + "VALUES (6, 'Bo Jo', 6, 02076669999, '1966-6-6' );";
            stmt.executeUpdate(sql);

            stmt.close();
            conn.commit();
            bInsert = true;

        } 
        catch (final Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return bInsert;
    }

    public boolean ModifyCustomer()
    {
        boolean bModify = false;
        Statement stmt = null;
        ResultSet rs = null;

        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT CustomerAddressID FROM Customer WHERE CustomerName = 'John Doe';");
            final int CustomerAddressID = rs.getInt("CustomerAddressID");
            stmt.close();
            
            stmt = conn.createStatement();
            final String sql = "UPDATE CustomerAddress set HouseNo = 25, Street = 'West Street', Town = 'Sutton', County = 'Surrey', PostCode = 'SM5 2AS' where AddressID="+ CustomerAddressID + ";";
            stmt.executeUpdate(sql);
            conn.commit();
            bModify = true;
        } 
        catch (final Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return bModify;
    }

    public boolean DeleteCustomer()
    {
        boolean  bDelete = false;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSet rs2 = null;

        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT CustomerID FROM Customer WHERE CustomerName = 'Becky Jane';");
            final int CustomerID = rs.getInt("CustomerID");
            stmt.close(); 
            
            stmt = conn.createStatement();
            rs2 = stmt.executeQuery("SELECT CustomerAddressID FROM Customer WHERE CustomerName = 'Becky Jane';");
            final int CustomerAddressID = rs2.getInt("CustomerAddressID");
            stmt.close();
            
            stmt = conn.createStatement();
            String sql = "DELETE from CustomerAddress where AddressID="+ CustomerAddressID + ";";
            stmt.executeUpdate(sql);
            sql = "DELETE from Customer where CustomerName = 'Becky Jane';";
            stmt.executeUpdate(sql);

            System.out.println("\nOUTSTANDING RENTAL ID's IN  THE NAME OF THE DELETED USER:\n");
            final ResultSet newrs = stmt.executeQuery( "SELECT * FROM Rental WHERE CustomerID ="+ CustomerID + ";");
         
            while ( newrs.next() ) 
            {
                final int RentalID = newrs.getInt("RentalID");
                System.out.println( "RentalID = " + RentalID + "\n");
            }
            conn.commit();
            bDelete = true;
        } 
        catch (final Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        
        return bDelete;
    }

    public boolean AddRental()
    {
        boolean bAddRental = false;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSet rs2 = null;

        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT CustomerID FROM Customer WHERE CustomerName = 'Mary Jones';");
            final int CustomerID = rs.getInt("CustomerID");
            stmt.close(); 

            stmt = conn.createStatement();
            rs2 = stmt.executeQuery("SELECT MovieID FROM Movie WHERE Title = 'Dragon';");
            final int MovieID = rs2.getInt("MovieID");
            stmt.close();

            stmt = conn.createStatement();
            final String sql = "INSERT INTO Rental (RentalID, CustomerID, MovieID, RentalDate, ReturnDate) "
                    + "VALUES (8, " + CustomerID +", "+ MovieID +", '2020-6-22', '2020-6-25' );";
            stmt.executeUpdate(sql);

            stmt.close();
            conn.commit();
            bAddRental = true;

        } 
        catch (final Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return bAddRental;
    }

    public boolean DisplayOverdueFees()
    {
        boolean bDisplay = false;
        Statement stmt = null;
        ResultSet rs = null;

        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT CustomerName, RentalID FROM vwOverdueFees;");

            while (rs.next())
            {
                String CustomerName = rs.getString("CustomerName");
                int RentalID = rs.getInt("RentalID");

                System.out.println("Customer Name = " + CustomerName);
                System.out.println("\nRentalID = " + RentalID + "\n");
            }
            
            stmt.close();   
            bDisplay = true;
        } 
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return bDisplay;
    }

    public boolean DoTheDijkstraThing()
    {
        boolean bDijkstra = false;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        ArrayList<Node> Customers = new ArrayList<Node>();

        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT CustomerName, CustomerID FROM Customer;");

            while (rs.next())
            {
                String CustomerName = rs.getString("CustomerName");
                int CustomerID = rs.getInt("CustomerID");
                Node node = new Node(CustomerName);
                node.setID(CustomerID);
                Customers.add(node);
            }
            rs.close();
            stmt.close();   

            stmt = conn.createStatement();
            rs2 = stmt.executeQuery("SELECT RootCustomerID, ChildCustomerID, Distance FROM AdjacencyList;");

            while (rs2.next())
            {
                int RootCustomerID = rs2.getInt("RootCustomerID");
                int ChildCustomerID = rs2.getInt("ChildCustomerID");
                double distance = rs2.getInt("Distance");

                Customers.get(RootCustomerID-1).addNeighbour(new Edge(distance,Customers.get(RootCustomerID-1),Customers.get(ChildCustomerID-1)));
            }

            DijkstraShortestPath shortestPath = new DijkstraShortestPath();
            shortestPath.computeShortestPaths(Customers.get(0));

            //Then, we print out all the wonderful data we compiled with some nice lines to make it look pretty.
            System.out.println("--------------------------------------");
            System.out.println("Calculating minimum distance to Customer!");
            System.out.println("--------------------------------------");
            
            for(int i = 0; i < Customers.size(); i++)
            {
                System.out.println("Minimum distance from Shop to Customer ID " + (i+1) + ": "+((Customers.get(i).getDistance()+5)*10) + "km");
            }
            bDijkstra = true;
        } 
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return bDijkstra;
    }

    public MovieDatabaseDataTest()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");//Specify the SQLite Java driver
            conn = DriverManager.getConnection("jdbc:sqlite:Aqa Movie Database Developed.db");//Specify the database, since relative in the main project folder
            conn.setAutoCommit(false);// Important as you want control of when data is written
            System.out.println("Opened database successfully");
        } 
        catch (final Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void close() 
    {
        try
        {
            conn.close();
        } 
        catch (final SQLException ex)
        {
            Logger.getLogger(DatabaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}