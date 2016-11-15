/*
Copyright meixin 2016 All Rights Reserved.
*/
package meixin.cc;

import meixin.entity.CouponBatch;
import meixin.util.CouponUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.java.shim.ChaincodeBase;
import org.hyperledger.java.shim.ChaincodeStub;

/**
 *
 */
public class CouponCC extends ChaincodeBase {
    private static Log log = LogFactory.getLog(CouponCC.class);

    public static void main(String[] args) throws Exception {
        new CouponCC().start(args);
    }

    @Override
    public String run(ChaincodeStub stub, String function, String[] args) {
        log.info("In run, function:" + function);

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

    /**
     * 创建优惠券批次
     */
    private String createCouponBatch(ChaincodeStub stub, String[] args) {
        if(args == null || args.length != 1) {
            return "args cannot be empty";
        }
        CouponBatch cb = CouponUtils.parse(args[0]);
        if (cb == null) {
            return "parsing request json failed";
        }
        if (!checkBudget(cb.getBudgetNo())) {
            return "validating budget  failed";
        }
        generateBatchNo(cb);

        return "couponbatch is created....";

    }

    /**
     * 申请优惠券
     */
    private String applyCoupon(ChaincodeStub stub, String[] args) {
        return "appleCoupon.....";
    }

    /**
     * 发送优惠券
     */
    private String sendCoupon(ChaincodeStub stub, String[] args) {
        return "sendCoupon....";
    }

    /**
     * 消费优惠券
     */
    private String consumeCoupon(ChaincodeStub stub, String[] args) {
        return "consumeCoupon...";
    }

    /**
     * 生成优惠券批次号
     */
    private String generateBatchNo(CouponBatch cb) {
        return "generateBatchNo...";

    }

    private boolean saveBatchInfo(CouponBatch cb) {

        return true;
    }

    /**
     * 生成优惠券序列号
     */
    private String generateCouponNo(ChaincodeStub stub, String[] args) {
        return "generateCouponNo...";
    }

    /**
     * 检查预算编号的合法性
     * @param budgetNo
     * @return
     */
    private boolean checkBudget(String budgetNo) {
        //unimplemented...
        return true;
    }
    public String init(ChaincodeStub stub, String function, String[] args) {
        //nothing to do....
        return null;
    }

    @Override
    public String query(ChaincodeStub stub, String function, String[] args) {
        if (args.length != 1) {
            return "{\"Error\":\"Incorrect number of arguments. Expecting name of the person to query\"}";
        }
        String am = stub.getState(args[0]);
        if (am != null && !am.isEmpty()) {
            try {
                int valA = Integer.parseInt(am);
                return "{\"Name\":\"" + args[0] + "\",\"Amount\":\"" + am + "\"}";
            } catch (NumberFormatException e) {
                return "{\"Error\":\"Expecting integer value for asset holding\"}";
            }
        } else {
            return "{\"Error\":\"Failed to get state for " + args[0] + "\"}";
        }


    }

    /**
     * 根据优惠券批次号查询批次信息
     */
    private String queryCouponBatchByNo(ChaincodeStub stub, String batchNo) {
        return "queryCouponBatchByNo...";
    }

    /**
     * 根据优惠券id查询优惠券信息
     */
    private String queryCouponByID(ChaincodeStub stub, String id) {
        return "queryCouponByID...";
    }

    /**
     * 根据用户id查询用户拥有的优惠券集合
     */
    private String queryCouponsByUID(ChaincodeStub stub, String uid) {
        return "queryCouponsByUID...";

    }

    public String delete(ChaincodeStub stub, String function, String[] args) {
        return "delete.....";
    }

    @Override
    public String getChaincodeID() {
        return "couponcc";
    }
}
