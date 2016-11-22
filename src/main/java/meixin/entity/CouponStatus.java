package meixin.entity;

/**
 * Created by huajie on 2016/11/15.
 */
public enum CouponStatus {

    BEFORE_EFFECT(0, "未生效"),//未到生效开始时间
    CONSUMABLE(1,"可消费"), //可消费
    CONSUMED(2,"已消费"), //已消费
    EXPIRED(3,"已过期");  //已过期

    private int value;
    private String name;

    private CouponStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }
    public int getValue() {
        return this.value;
    }

    public static CouponStatus valueOf(int value) {
        switch (value) {
            case 0:
                return BEFORE_EFFECT;
            case 1:
                return CONSUMABLE;
            case 2:
                return CONSUMED;
            case 3:
                return EXPIRED;
            default:
                return null;
        }
    }

    public String toString() {
        return this.name;
    }
}
