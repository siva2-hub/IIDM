package commonUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class SendReportsToGMail {
	public static void main(String[] args) {
        // Step 1: Establish a database connection
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing", "enterpi", "enterpi@1234")) {
            // Step 2: Retrieve data from the table
            String query = "SELECT * FROM buzzworld_automation_logs";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            
            // Step 3: Generate the HTML file
            StringBuilder htmlBuilder = new StringBuilder();
            htmlBuilder.append("<html><body><table border = 1px>");
            
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            
            // Create table headers
            htmlBuilder.append("<tr>");
            for (int i = 1; i <= columnCount; i++) {
                htmlBuilder.append("<th style = \"color: blue; font-size: larger\">").append(metaData.getColumnLabel(i)).append("</th>");
            }
            htmlBuilder.append("</tr>");
            // Create table rows
            while (resultSet.next()) {
                htmlBuilder.append("<tr>");
                for (int i = 1; i <= columnCount; i++) {                	
                    if(resultSet.getString(i).equals("Failed")) {
                    	htmlBuilder.append("<td style = \"color: red;\" >").append(resultSet.getString(i)).append("</td>");
                    }else if(resultSet.getString(i).equals("Passed")) {
                    	htmlBuilder.append("<td style = \"color: green;\" >").append(resultSet.getString(i)).append("</td>");
                    	if(metaData.getColumnLabel(i).equals("test_case_name")) {
                    		htmlBuilder.append("<td style = \"color: green;\" >").append(resultSet.getString(i)).append("</td>");
                    	}
                    }else {
                    	htmlBuilder.append("<td>").append(resultSet.getString(i)).append("</td>");	
                    }
                }
                htmlBuilder.append("</tr>");
            }
            htmlBuilder.append("</table></body></html>");
            
            // Step 4: Save the HTML file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.html"))) {
                writer.write(htmlBuilder.toString());
            }
            
            System.out.println("HTML file generated successfully.");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
