
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
	@Produces("application/xml")
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
  
}
