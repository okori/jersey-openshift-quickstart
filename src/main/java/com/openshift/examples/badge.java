package com.openshift.examples;
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
 
@Path("/checkBadgeSale")
public class badge {
	
	static String consumerKeyStr       = "pHE3zK2ikhaQ39arE3kz5KVJ0";
	static String consumerSecretStr    = "CHy0pxp5NPY7TVvClfPUbIaFahffNoEGODmjy6DuLLdP0WXoaQ";
	static String accessTokenStr       = "914619321590079489-OGxE9narl0pGVw5RUAh1hcccaPxG5RF";
	static String accessTokenSecretStr = "tXQ7zxGACNTaj1fsVLeIy94ZGtu6uBvrRk5ndBkwCGU9J";
	
    	static String myUrl = "jdbc:mysql://db4free.net:3307/bernard?useSSL=false";
	
	@GET
	@Produces("text/plain")
	public String report() {
 
		//if sale is running and last time it checked sale wasn't running -> send alert
		if(saleStatus && checkDB()) {
			sendAlert();
		}
		
		//log to DB whether a sale is running or not
		if(saleStatus) {
			logStatus(0); //a sale is running
		} else {
			logStatus(1); //no sale running
		}
		return "success!";
	}
 
	public static boolean checkDB() {
		try {
			Connection conn = DriverManager.getConnection(myUrl, "user5107", "4chanLOL");
			Statement st = conn.createStatement();
			
			String select = "SELECT * FROM badge_sale_queries";
			ResultSet rs = st.executeQuery(select);
			rs.last();
			int last = rs.getInt("sale_status");
			if(last == 0) {
				return true;
			}

		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//connect to database and add entry that logs both the current date and the result of checkSale
	public static void logStatus(int bool) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate localDate = LocalDate.now();
				
	    try {
	      Connection conn = DriverManager.getConnection(myUrl, "user5107", "4chanLOL");
	      
	      Statement st = conn.createStatement();

	      st.executeUpdate("INSERT INTO `badge_sale_queries`(sale_status,date) VALUE"
	      		+ " ('"+bool+"','"+dtf.format(localDate)+"')");

	      conn.close();
	      
	    } catch (Exception e) {
	      System.err.println("Got an exception!");
	      System.err.println(e.getMessage());
	    }
	
	}
	
	public static void sendAlert() {
		
		try {
			Twitter twitter = new TwitterFactory().getInstance();

			twitter.setOAuthConsumer(consumerKeyStr, consumerSecretStr);
			AccessToken accessToken = new AccessToken(accessTokenStr,
					accessTokenSecretStr);

			twitter.setOAuthAccessToken(accessToken);

			twitter.updateStatus("a badge sale has started!");

		} catch (TwitterException te) {
		    System.err.println("Got an exception!");
			te.printStackTrace();
		}
		
	}
	
	//need to check if a badge sale was running the last time it was checked
	//if a sale was running last time DON'T send notif -- if it wasn't DO send
	public static boolean checkSale() {
        Document doc;
        try {

            doc = Jsoup.connect("http://www.imvu.com/catalog/newsletter/badges.php").get();
            
            if(doc.select("div:contains(Half-Price Sale)").first() != null) {
            	return true;
            }

            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
	}

}
