package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import Database.DB;

public class Customer {

	Connection con = null;
		public Customer() {
			
			
		con =DB.getConnection();
			
		}

   	//read Customer
   public String readCustomer() {
			String output = "";
			try {
				
				if (con == null) {
					return "Error while connecting to the database for reading.";
				}

				output = "<table border='1'><tr><th>Customer Name</th>" + "<th>Customer Email</th><th>Customer NIC</th>"
						+ "<th>Customer mobile</th>" + "<th>Update</th><th>Remove</th></tr>";

				String query = "select * from customer";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);

				while (rs.next()) {

					String ID = Integer.toString(rs.getInt("ID"));
					String name = rs.getString("name");
					String email = rs.getString("email");
					String nic = rs.getString("nic");
					String mobile = Integer.toString(rs.getInt("mobile"));

					output += "<tr><td><input id='hidcustomerIDUpdate' name='hidcustomerIDUpdate' type='hidden' value='"
							+ ID + "'>" + name + "</td>";

					output += "<td>" + email + "</td>";
					output += "<td>" + nic + "</td>";
					output += "<td>" + mobile + "</td>";

				
					output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
							+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-customerID='"
							+ ID + "'>" + "</td></tr>";

				}

				output += "</table>";
				
			} catch (Exception e) {
				output = "Error  reading the Database.";
				System.err.println(e.getMessage());
			}
			
			

			return output;
			
			
		}

	//Registerd Customer
   public String RegistertCustomer(String name, String email, String nic,String mobile) {
			String output = "";
			
			try {
				
				if (con == null) {
					return "Error  connecting to the database";
				}

				//insert query
				String query = " insert into customer (`ID`,`name`,`email`,`nic`,`mobile`)"
						+ " values (?, ?, ?, ?, ?)";

				PreparedStatement preparedStmt = con.prepareStatement(query);

				
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, name);
				preparedStmt.setString(3, email);
				preparedStmt.setString(4, nic);
				preparedStmt.setString(5, mobile);
             	preparedStmt.execute();
			
				String RegisterdCustomer = readCustomer();
				output = "{\"status\":\"success\", \"data\": \"" + RegisterdCustomer + "\"}";
			} catch (Exception e) {
				
		
				output = "{\"status\":\"error\", \"data\": \"Error  Inserting Customer.\"}";
				System.err.println(e.getMessage());
			}
			
	

			return output;
		}
// update CUstomer
	public String updateCustomer(String ID, String name, String email, String nic, String mobile) {
		String output = "";

		try {
			

			if (con == null) {
				return "Error  connecting to the database for updating.";
			}

			String query = "update customer set name=?,email=?,nic=?,mobile=? WHERE ID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, email);
			preparedStmt.setString(3, nic);
			preparedStmt.setInt(4, Integer.parseInt(mobile));
			preparedStmt.setInt(5, Integer.parseInt(ID));

			preparedStmt.execute();
			

			String UpdateCustomer = readCustomer();
			output = "{\"status\":\"success\", \"data\": \"" + UpdateCustomer + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while Updating Customer Details.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	// delete Customer
	public String deleteCustomer(String ID) {
		String output = "";
		try {
			
			if (con == null) {
				return "Error  connecting to the database for deleting.";
			}

			String query = "delete from customer where ID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			preparedStmt.setInt(1, Integer.parseInt(ID));

			preparedStmt.execute();
			

			String DeleteCustomer = readCustomer();
			output = "{\"status\":\"success\", \"data\": \"" + DeleteCustomer + "\"}";

		} catch (Exception e) {

			output = "{\"status\":\"error\", \"data\": \"Error while Deleting Customer data.\"}";
			System.err.println(e.getMessage());

		}

		return output;
	}


	}
