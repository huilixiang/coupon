/*
Copyright meixin 2016 All Rights Reserved.
*/
package meixin.cc;

import meixin.entity.Coupon;
import meixin.entity.CouponActionType;
import meixin.entity.CouponBatch;
import meixin.entity.CouponStatus;
import meixin.util.CouponUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.java.shim.ChaincodeBase;
import org.hyperledger.java.shim.ChaincodeStub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *优惠券业务chaincode
 */
public class CouponCC extends ChaincodeBase {
    private static Log log = LogFactory.getLog(CouponCC.class);
    private static boolean isDebug = log.isDebugEnabled();
    //创建者-->优惠券批次 key-value key后缀
    private static final String COUPON_BATCH_CREATOR_SUFFIX = "CRTR";
    //领取类优惠券ID key后缀
    private static final String COUPON_APPLY_ID = "AID";
    //发送类优惠券ID key后缀
    private static final String COUPON_SEND_ID = "SID";
    //领取类优惠券ID value后缀
    private static final String COUPON_APPLY_SUFFIX = "a";
    //发送类优惠券ID value后缀
    private static final String COUPON_SEND_SUFFIX = "s";
    //领取类优惠券已消费数量
    private static final String COUPON_APPLY_CONSUMED_COUNT="ACC";
    //发送优惠券已消费数量
    private static final String COUPON_SEND_CONSUMED_COUNT="SCC";

    public static void main(String[] args) throws Exception {
        new CouponCC().start(args);
    }

    /**
     * chaincode invoke 入口方法
     * @param stub  chaincodestub
     * @param function 要调用的方法名称
     * @param args  function需要的参数
     * @return function的返回值
     */
    @Override
    public String run(ChaincodeStub stub, String function, String[] args) {
        log.info("In run, function:" + function);
        String re = "";
        switch (function) {
            case "init":
                init(stub, function, args);
                break;
            case "createCouponBatch":
                re = createCouponBatch(stub, args);
                if (isDebug) {
                    log.debug("createCouponBatch-response:"+re);
                }
                return re;
            case "applyCoupon":
                re = applyCoupon(stub, args);
                if (isDebug) {
                    log.debug("applyCoupon response:"+re);
                }
                return re;
            case "sendCoupon":
                re = sendCoupon(stub, args);
                if (isDebug) {
                    log.debug("sendCoupon response:"+re);
                }
                return re;
            case "consumeCoupon":
                re = consumeCoupon(stub, args);
                if (isDebug) {
                    log.debug("consumeCoupon response:"+re);
                }
                return re;
            default:
                return "run-default";
        }
        return null;
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


    public String delete(ChaincodeStub stub, String function, String[] args) {
        return "delete.....";
    }

    @Override
    public String getChaincodeID() {
        return "couponcc";
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


    /**
     * 创建优惠券批次
     */
    private String createCouponBatch(ChaincodeStub stub, String[] args) {
        if(args == null || args.length != 1) {
            return "args cannot be empty";
        }
        CouponBatch cb = null;
        try {
            cb = CouponUtils.parse(args[0], CouponBatch.class);
        } catch (Exception e) {
            log.error("failed for parsing request json of createCouponBatch ");
            return "request json error";
        }
        if (!checkBudget(cb.getBudgetNo())) {
            return "budgetNo is noneffective";
        }
        generateBatchNo(cb);
        String jsonCb =  CouponUtils.encode2Json(cb);
        stub.putState(cb.getSerialNo(), jsonCb);
        //TODO..现在并未记录优惠券批次创建者--》优惠券批次的key-value， 可以由辅助系统来实现查询
        return "success";

    }

    /**
     * 申请优惠券
     */
    private String applyCoupon(ChaincodeStub stub, String[] args) {
        if (args == null || args.length != 2) {
            return "{\"Error\":\"Incorrect number of arguments. Expecting [uid, couponBatchNo] to applyCoupon\"}";
        }
        String uid = args[0];
        String couponBatchNo = args[1];
        CouponBatch cb = getCouponBatchByNo(stub, couponBatchNo);
        if (!checkEffectivity4Apply(cb)) {
            return "{\"Error\":\"CouponBatch is noneffective.\"}";
        }
        if (!checkUserLegality(stub, uid, cb)) {
            return "{\"Error\":\"illegal applying .\"}";
        }
        Coupon coupon = generateCoupon(stub, cb, CouponActionType.APPLY);
        markOwned(stub, uid, coupon);
        return "success";
    }

    /**
     * 发送优惠券
     */
    private String sendCoupon(ChaincodeStub stub, String[] args) {
        if (args.length != 4) {
            return "{\"Error\":\"Incorrect number of arguments. " +
                    "Expecting [operator, couponBatchNo, denomination, receiver] to sendCoupon\"}";
        }
        String operatorID = args[0];
        String couponBatchNo = args[1];
        int denomination = Integer.valueOf(args[2]);
        String receiver = args[3];
        CouponBatch cb = getCouponBatchByNo(stub, couponBatchNo);
        //有效性验证
        if (cb == null || !checkEffectivity4Send(cb)) {
            return "{\"Error\":\"CouponBatch is noneffective.\"}";
        }
        //生成优惠券
        Coupon coupon = generateCoupon(stub, cb, CouponActionType.SEND);
        coupon.setCreator(operatorID);
        coupon.setDenomination(denomination);
        //标记优惠券已发送并且存储优惠券信息
        markOwned(stub, receiver, coupon);
        return "success";
    }

    /**
     * 消费优惠券
     */
    private String consumeCoupon(ChaincodeStub stub, String[] args) {
        if (args.length != 5) {
            return "{\"Error\":\"Incorrect number of arguments. " +
                    "Expecting [uid, couponid, orderid, price, sku] to consumeCoupon\"}";
        }
        String uid = args[0];
        String couponId = args[1];
        String orderId = args[2];
        int price = Float.valueOf(args[3]).intValue();
        String sku = args[4];
        String couponBatchNo = extractBatchNo(couponId);
        List<String> coupons = getCouponsOfUser(stub, uid, couponBatchNo);
        if (!coupons.contains(couponId)) {
            return "{\"Error\":\"illegal couponid.\"}";
        }
        CouponBatch cb = getCouponBatchByNo(stub, couponBatchNo);
        if (cb == null) {
            return "{\"Error\":\"illegal couponbatchid.\"}";
        }
        if (System.currentTimeMillis() > cb.getExpiringDate()) {
            return "{\"Error\":\"expired coupon.\"}";
        }
        switch (cb.getCouponType()) {
            case XDLJ:
                if (sku == null || !sku.equals(cb.getSku())) {
                    return "{\"Error\":\"illegal sku.\"}";
                }
                break;
            case MDLJ:
                if (price < cb.getOrderLimit()) {
                    return "{\"Error\":\"the pre-determined price condition is not satisfied.\"}";
                }
                break;
            default:
                return "{\"Error\":\"illegal coupon type\"}";
        }
        return markConsume(stub, cb, uid, couponId);
    }

    /**
     * 标记coupon被消费
     * @param stub chaincodestub
     * @param cb  优惠券批次entity
     * @param uid uid
     * @param couponId 优惠券id
     */
    private String markConsume(ChaincodeStub stub, CouponBatch cb, String uid , String couponId) {
        Coupon coupon = getCouponBySeriaNo(stub, couponId);
        if (coupon == null) {
            return "{\"Error\":\"illegal couponid.\"}";
        }
        coupon.setStatus(CouponStatus.CONSUMED);
        saveCoupon(stub, coupon);
        int consumeCount = getConsumeCount(stub, cb.getSerialNo(), coupon.getType());
        saveConsumeCount(stub, cb.getSerialNo(), coupon.getType(), consumeCount+1);
        return "success";
    }

    /**
     * 生成优惠券entity
     * @param stub  chaincodestub
     * @param cb 优惠券批次对象
     * @param couponActionType 优惠券发放类型
     * @return 优惠券entity, 为Null表示生成失败
     */
    private Coupon generateCoupon(ChaincodeStub stub, CouponBatch cb, CouponActionType couponActionType) {
        String key = cb.getSerialNo();
        boolean isApply = couponActionType == CouponActionType.APPLY;
        if (isApply) {
            key += COUPON_APPLY_ID;
        } else if (couponActionType == CouponActionType.SEND) {
            key += COUPON_SEND_ID;
        } else {
            return null;
        };
        String value = stub.getState(key);
        int increID = 0;
        if (value != null) {
            increID = Integer.valueOf(value);
        }
        ++increID;
        if (isApply) {
            if (increID > cb.getCouponAmount()) {
                log.warn("no free coupon left");
                return null;
            }
            cb.setAppliedAmount(increID);
        } else {
            cb.setSentAmount(increID);
        }
        stub.putState(key, String.valueOf(increID));
        Coupon coupon = new Coupon();
        //优惠券的序列号生成规则由批次号+自增id组成
        String couponNo = generateCouponSerialNo(cb.getSerialNo(), String.valueOf(increID), couponActionType);
        coupon.setSerialNo(couponNo);
        coupon.setBatchSerialNo(cb.getSerialNo());
        coupon.setCreateDate(System.currentTimeMillis());
        coupon.setType(couponActionType);
        coupon.setDenomination(cb.getDenomination());
        return coupon;
    }

    /**
     * 同批次优惠券序列号生成策略
     * @param couponBatchNo 优惠券批次号
     * @param id 当前优惠券在同批次内的自增id
     * @param couponActionType 优惠券发放类型：apply or send
     * @return 优惠券序列号
     */
    private String generateCouponSerialNo(String couponBatchNo, String id, CouponActionType couponActionType) {
        //申请优惠券序列号以a结尾
        if (couponActionType == CouponActionType.APPLY) {
            return couponBatchNo+id+COUPON_APPLY_SUFFIX;
        } else { //赠送优惠券序列号以s结尾
            return couponBatchNo+id+COUPON_SEND_SUFFIX;
        }
    }

    /**
     * 生成优惠券批次号
     */
    private void generateBatchNo(CouponBatch cb) {
        //TODO...待优化
        cb.setSerialNo(String.valueOf(System.currentTimeMillis()));
    }

    /**
     * 根据优惠券序列号提取批次的序列号
     * @param couponSerialNo
     * @return  couponbatch serialno
     */
    private String extractBatchNo(String couponSerialNo) {
        return couponSerialNo.substring(0,13);
    }

    /**
     * 领驭类优惠券所属批次的有效性验证
     * @param cb 优惠券批次
     * @return 是否有效
     */
    private boolean checkEffectivity4Apply(CouponBatch cb) {
        long curTimes = System.currentTimeMillis();
        //对于有领取起止时间限制的优惠券，需要做领取时间判断
        if (curTimes < cb.getApplyStartDate() || curTimes > cb.getApplyEndDate()) {
            return false;
        }
        //有效期判断,不能领取过期的券
        if (curTimes >= cb.getExpiringDate()) {
            return false;
        }
        //已经领取的优惠券数量不能大于批次限制的总量
        if (cb.getAppliedAmount() >= cb.getCouponAmount()) {
            return false;
        }
        return true;
    }

    /**
     * 发送类优惠券的批次有效性验证
     * @param cb 优惠券所属批次信息
     * @return 是否有效
     */
    private boolean checkEffectivity4Send(CouponBatch cb) {
        long curTimes = System.currentTimeMillis();
        //有效期判断,不能领取过期的券
        if (curTimes >= cb.getExpiringDate()) {
            return false;
        }
        return true;
    }

    /**
     * 检测领券用户行为的合法性
     * @param stub chaincodestub
     * @param uid  领取优惠券的用户id
     * @param cb  优惠券批次信息
     * @return  用户的领取行为是否合法
     */
    private boolean checkUserLegality(ChaincodeStub stub, String uid, CouponBatch cb) {
        String cbNo = cb.getSerialNo();
        //用户拥有的优惠券，以"uid+批次no"为key, value是由以逗号分隔的优惠券no组成的字符串
        List<String> coupons = getCouponsOfUser(stub, uid, cb.getSerialNo());
        int count = 0;
        for (String no : coupons) {
            //用户领取的优惠券与发送给用户的优惠券张数作区分，优惠券批次限制用户可领取的张数，不限制可发送给用户的张数
            if (no.endsWith(COUPON_APPLY_SUFFIX)) {
                count ++;
            }
        }
        //每批次不能申领超过指定张数
        if (count >= cb.getApplyLimit()) {
            return false;
        }
        return true;
    }

    /**
     * 标记优惠券已被领取/发送
     * @param stub chaincodestub
     * @param uid  优惠券拥有者id
     * @param coupon  优惠券entity
     */
    private void markOwned(ChaincodeStub stub, String uid, Coupon coupon) {
        coupon.setOwner(uid);
        List<String> coupons = getCouponsOfUser(stub, uid, coupon.getBatchSerialNo());
        coupons.add(coupon.getSerialNo());
        saveCouponsOfUser(stub, uid, coupon.getBatchSerialNo(), coupons);
        saveCoupon(stub, coupon);
    }

    /**
     * 获取用户在同一批次下的所有优惠券serial no集合
     * @param stub chaincodestub
     * @param uid  用户id
     * @param couponBatchNo 优惠券批次号
     * @return 优惠券序列号数组
     */
    private List<String> getCouponsOfUser(ChaincodeStub stub, String uid, String couponBatchNo) {
        String key = uid + couponBatchNo;
        String value = stub.getState(key);
        if (value == null) {
            return new ArrayList<String>();
        }
        return Arrays.asList(value.split(","));
    }

    /**
     * 根据优惠券批次号获取优惠券批次实现
     * @param stub chaincodestub
     * @param couponBatchNo 批次号
     * @return 实体 or null
     */
    private CouponBatch getCouponBatchByNo(ChaincodeStub stub, String couponBatchNo) {
        //获取优惠券批次数据
        String jsonCb = stub.getState(couponBatchNo);
        if (jsonCb == null) {
            log.warn("no couponbatch for no:"+couponBatchNo);
            return null;
            //return "{\"Error\":\"Invalid couponBatchNo.\"}";
        }
        return CouponUtils.parse(jsonCb, CouponBatch.class);

    }

    /**
     * 根据coupon 序列号获取实体对象
     * @param stub chaincodestub
     * @param couponNo 优惠券序列号
     * @return coupon entity or null
     */
    private Coupon getCouponBySeriaNo(ChaincodeStub stub, String couponNo) {
        String json = stub.getState(couponNo);
        if (json == null) {
            return null;
        }
        return CouponUtils.parse(json, Coupon.class);
    }

    /**
     * 保存coupon实体
     * @param stub
     * @param coupon
     */
    private void saveCoupon(ChaincodeStub stub, Coupon coupon) {
        String couponJson = CouponUtils.encode2Json(coupon);
        stub.putState(coupon.getSerialNo(), couponJson);
    }
    /**
     * 保存用户在同一批次下的优惠券记录
     * @param stub chaincodestub
     * @param uid 用户id
     * @param couponBatchNo 优惠券批次no
     * @param coupons 用户在当前批次下拥有的优惠券集合
     */
    private void saveCouponsOfUser(ChaincodeStub stub, String uid, String couponBatchNo, List<String> coupons) {
        String key = uid + couponBatchNo;
        //用户在同一批次下的优惠券id用","分隔
        String value = String.join(",", coupons);
        stub.putState(key, value);
    }

    /**
     * 获取同批次已消费的优惠券数量， 对发送和领取两种类型分开统计
     * @param stub chaincodestub
     * @param couponBatchNo 优惠券批次序列号
     * @param couponActionType 发送 or 领取
     * @return 已消费数量
     */
    private int getConsumeCount(ChaincodeStub stub, String couponBatchNo, CouponActionType couponActionType) {
        String value = stub.getState(generateConsumeCountKey(couponBatchNo, couponActionType));
        if (value == null) {
            return 0;
        }
        return Integer.valueOf(value);
    }

    /**
     * @param stub
     * @param couponBatchNo
     * @param couponActionType
     */
    private void saveConsumeCount(ChaincodeStub stub, String couponBatchNo, CouponActionType couponActionType, int count) {
        stub.putState(generateConsumeCountKey(couponBatchNo, couponActionType), String.valueOf(count));
    }

    private String generateConsumeCountKey(String couponBatchNo, CouponActionType couponActionType) {
        String key = couponBatchNo;
        switch (couponActionType) {
            case APPLY:
                key += COUPON_APPLY_CONSUMED_COUNT;
                break;
            case SEND:
                key += COUPON_SEND_CONSUMED_COUNT;
                break;
        }
        return key;
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
}
