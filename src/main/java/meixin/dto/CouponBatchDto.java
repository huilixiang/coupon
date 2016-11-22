package meixin.dto;


import java.util.Map;

/**
 *
 */
public class CouponBatchDto {

    private long id;
    private String batchSn;
    private int batchType;
    private String batchTypeDesc;
    private long effectiveStartTime;
    private long effectiveEndTime;
    private int money;
    private long shopId;
    private int usageRuleType;
    private Map<String, Object> usageRule;
    private Map<String, Object> quantity;
    private String batchName;
    private int status;
    private String statusDesc;
    private String description;
    private String picture;
    private long publishedTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBatchSn() {
        return batchSn;
    }

    public void setBatchSn(String batchSn) {
        this.batchSn = batchSn;
    }

    public int getBatchType() {
        return batchType;
    }

    public void setBatchType(int batchType) {
        this.batchType = batchType;
    }

    public String getBatchTypeDesc() {
        return batchTypeDesc;
    }

    public void setBatchTypeDesc(String batchTypeDesc) {
        this.batchTypeDesc = batchTypeDesc;
    }

    public long getEffectiveStartTime() {
        return effectiveStartTime;
    }

    public void setEffectiveStartTime(long effectiveStartTime) {
        this.effectiveStartTime = effectiveStartTime;
    }

    public long getEffectiveEndTime() {
        return effectiveEndTime;
    }

    public void setEffectiveEndTime(long effectiveEndTime) {
        this.effectiveEndTime = effectiveEndTime;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public int getUsageRuleType() {
        return usageRuleType;
    }

    public void setUsageRuleType(int usageRuleType) {
        this.usageRuleType = usageRuleType;
    }

    public Map<String, Object> getUsageRule() {
        return usageRule;
    }

    public void setUsageRule(Map<String, Object> usageRule) {
        this.usageRule = usageRule;
    }

    public Map<String, Object> getQuantity() {
        return quantity;
    }

    public void setQuantity(Map<String, Object> quantity) {
        this.quantity = quantity;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public long getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(long publishedTime) {
        this.publishedTime = publishedTime;
    }



}
