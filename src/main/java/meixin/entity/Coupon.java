package meixin.entity;

/**
 * Created by huajie on 2016/11/14.
 */
public class Coupon {

    private long id;
    //优惠券批次号
    private String batchSerialNo;
    //优惠券序列号
    private String serialNo;
    //优惠券发放类型 1：领取 2：发放
    private CouponActionType  type;
    //创建时间，毫秒
    private long createDate;
    //创建人
    private String creator;
    //拥有者
    private long owner;
    //优惠券状态, 0: 未生效， 1：未消费  2：已消费 3：已失效
    private CouponStatus status;

    private int denomination;

    public int getDenomination() {
        return denomination;
    }

    public void setDenomination(int denomination) {
        this.denomination = denomination;
    }

    public String getBatchSerialNo() {
        return batchSerialNo;
    }

    public void setBatchSerialNo(String batchSerialNo) {
        this.batchSerialNo = batchSerialNo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }


    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    public CouponActionType getType() {
        return type;
    }

    public void setType(CouponActionType type) {
        this.type = type;
    }

    public CouponStatus getStatus() {
        return status;
    }

    public void setStatus(CouponStatus status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coupon coupon = (Coupon) o;

        if (!batchSerialNo.equals(coupon.batchSerialNo)) return false;
        return serialNo.equals(coupon.serialNo);

    }

    @Override
    public int hashCode() {
        int result = batchSerialNo.hashCode();
        result = 31 * result + serialNo.hashCode();
        return result;
    }
}
