
package com.openshift.examples;
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

 
@Path("/report")
public class badge {
	
	@GET
	public void report() {
		
		boolean saleStatus = assist.checkSale();
		
		if(saleStatus && assist.checkDB()) {
			assist.sendAlert();
		}
		
		if(saleStatus) {
			assist.logStatus(0);
		} else {
			assist.logStatus(1);
		}
		
	}
  
}
