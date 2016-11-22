package meixin.entity;

/**
 * 优惠券批次实体类
 */
public class CouponBatch {

    private long id;
    //优惠券批次号
    private String serialNo;
    //优惠券名称
    private String name;
    //优惠券类型
    private CouponType couponType;
    //面额，单位：分
    private int denomination;
    //生效时间对应的毫秒数
    private long effectiveTime;
    //失效时间对应的毫秒数
    private long expiringTime;
    //可以申请的开始时间对应的毫秒数
    private long applyStartTime = 0L;
    //可以申请的截止时间
    private long applyEndTime = 0L;
    private long publishTime = 0L;
    //预算时间
    private String budgetNo;
    //发放渠道
    private String channel;
    //优惠券适用的sku
    private String sku;
    //描述信息
    private String desc;
    //本批次可领取的优惠券数量
    private int couponAmount = 0;
    //本批次已领取优惠券数量
    private int appliedAmount = 0;
    //本批次已发送优惠券数量
    private int sentAmount = 0;
    //优惠券使用的订单总额限制，对于满减类优惠券需要设计此字段，单位:分
    private int orderLimit = 0;
    //每人可申请同批次优惠券的数量上限
    private int applyLimit = 1;
    //创建人
    private String creator;
    //批次创建时间
    private long createTime = 0L;

    private boolean isDisabled = false;

    private long shopID;

    private String picture;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CouponType getCouponType() {
        return couponType;
    }

    public void setCouponType(CouponType couponType) {
        this.couponType = couponType;
    }

    public int getDenomination() {
        return denomination;
    }

    public void setDenomination(int denomination) {
        this.denomination = denomination;
    }

    public long getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(long effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public long getExpiringTime() {
        return expiringTime;
    }

    public void setExpiringTime(long expiringTime) {
        this.expiringTime = expiringTime;
    }

    public long getApplyStartTime() {
        return applyStartTime;
    }

    public void setApplyStartTime(long applyStartTime) {
        this.applyStartTime = applyStartTime;
    }

    public long getApplyEndTime() {
        return applyEndTime;
    }

    public void setApplyEndTime(long applyEndTime) {
        this.applyEndTime = applyEndTime;
    }

    public String getBudgetNo() {
        return budgetNo;
    }

    public void setBudgetNo(String budgetNo) {
        this.budgetNo = budgetNo;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(int couponAmount) {
        this.couponAmount = couponAmount;
    }

    public int getOrderLimit() {
        return orderLimit;
    }

    public void setOrderLimit(int orderLimit) {
        this.orderLimit = orderLimit;
    }

    public int getApplyLimit() {
        return applyLimit;
    }

    public void setApplyLimit(int applyLimit) {
        this.applyLimit = applyLimit;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }


    public int getAppliedAmount() {
        return appliedAmount;
    }

    public void setAppliedAmount(int appliedAmount) {
        this.appliedAmount = appliedAmount;
    }

    public int getSentAmount() {
        return sentAmount;
    }

    public void setSentAmount(int sentAmount) {
        this.sentAmount = sentAmount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CouponBatch that = (CouponBatch) o;

        return serialNo.equals(that.serialNo);

    }

    public long getShopID() {
        return shopID;
    }

    public void setShopID(long shopID) {
        this.shopID = shopID;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public int hashCode() {
        return serialNo.hashCode();
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }
}