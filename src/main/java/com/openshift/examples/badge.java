
package com.openshift.examples;
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

 
@Path("/checkBadgeSale")
public class badge {
	
	@GET
	@Produces("application/xml")
	public String report() {
		
		boolean saleStatus = assist.checkSale();
 
		//if sale is running and last time it checked sale wasn't running -> send alert
		if(saleStatus && assist.checkDB()) {
			assist.sendAlert();
		}
		
		//log to DB whether a sale is running or not
		if(saleStatus) {
			assist.logStatus(0); //a sale is running
		} else {
			assist.logStatus(1); //no sale running
		}
		return "<service>" + "success!" + "</service>";
	}
  
}
