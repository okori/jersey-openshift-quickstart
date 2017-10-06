
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
		return "<isRunning>" + saleStatus + "</isRunning>";
	}
  
}
