package meixin.entity;

/**
 * Created by huajie on 2016/11/14.
 */
public enum CouponType {
    XDLJ(1, "下单立减"), //下单立减
    MDLJ(2, "满减");  //满减
    private int value;
    private String desc;

    private CouponType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static CouponType valueOf(int value) {
        switch (value) {
            case 1:
                return XDLJ;
            case 2:
                return MDLJ;
            default:
                return null;
        }
    }

    public int getValue() {
        return this.value;
    }

    public String toString() {
        return this.desc;
    }

}
