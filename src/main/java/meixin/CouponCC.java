/*
Copyright meixin 2016 All Rights Reserved.

*/

package meixin;

import org.hyperledger.java.shim.ChaincodeBase;
import org.hyperledger.java.shim.ChaincodeStub;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 */
public class CouponCC extends ChaincodeBase {
	 private static Log log = LogFactory.getLog(CouponCC.class);

	@Override
	public String run(ChaincodeStub stub, String function, String[] args) {
		log.info("In run, function:"+function);
		
		switch (function) {
		case "init":
			init(stub, function, args);
			break;
		case "createCouponBatch":
			String re = createCouponBatch(stub, args);	
			System.out.println(re);
			return re;					
		case "put":
			for (int i = 0; i < args.length; i += 2)
				stub.putState(args[i], args[i + 1]);
			break;
		case "del":
			for (String arg : args)
				stub.delState(arg);
			break;
		default: 
			return createCouponBatch(stub, args);
		}
	 
		return null;
	}


    private String createCouponBatch(ChaincodeStub stub, String[] args) {
        return "couponbatch is created....";
    
    }

	public String init(ChaincodeStub stub, String function, String[] args) {
        //nothing to do....
		return null;
	}

	
	@Override
	public String query(ChaincodeStub stub, String function, String[] args) {
		if(args.length!=1){
			return "{\"Error\":\"Incorrect number of arguments. Expecting name of the person to query\"}";
		}
		String am =stub.getState(args[0]);
		if (am!=null&&!am.isEmpty()){
			try{
				int valA = Integer.parseInt(am);
				return  "{\"Name\":\"" + args[0] + "\",\"Amount\":\"" + am + "\"}";
			}catch(NumberFormatException e ){
				return "{\"Error\":\"Expecting integer value for asset holding\"}";		
			}		}else{
			return "{\"Error\":\"Failed to get state for " + args[0] + "\"}";
		}
		

	}

	@Override
	public String getChaincodeID() {
		return "couponcc";
	}

	public static void main(String[] args) throws Exception {
		new CouponCC().start(args);
	}


}
