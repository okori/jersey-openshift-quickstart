
package com.openshift.examples;
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

 
@Path("/checkBadgeSale")
public class badge {
	
	static String consumerKeyStr       = "pHE3zK2ikhaQ39arE3kz5KVJ0";
        static String consumerSecretStr    = "CHy0pxp5NPY7TVvClfPUbIaFahffNoEGODmjy6DuLLdP0WXoaQ";
        static String accessTokenStr       = "914619321590079489-OGxE9narl0pGVw5RUAh1hcccaPxG5RF";
        static String accessTokenSecretStr = "tXQ7zxGACNTaj1fsVLeIy94ZGtu6uBvrRk5ndBkwCGU9J";
	
	@GET
	@Produces("application/xml")
	public String report() {
		
		try {
			Twitter twitter = new TwitterFactory().getInstance();

			twitter.setOAuthConsumer(consumerKeyStr, consumerSecretStr);
			AccessToken accessToken = new AccessToken(accessTokenStr,
					accessTokenSecretStr);

			twitter.setOAuthAccessToken(accessToken);

			twitter.updateStatus("a badge sale has started!");

		} catch (TwitterException e) {
		    System.err.println("Got an exception!");
		    e.printStackTrace();
		}
		
		boolean saleStatus = assist.checkSale();
		return "<isRunning>" + saleStatus + "</isRunning>";
	}
  
}
