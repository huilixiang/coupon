package meixin.entity;

/**
 * Created by huajie on 2016/11/15.
 */
public enum CouponActionType {
    APPLY(1, "领取"), //用户主动领取
    SEND(2, "发送");  //运营后台主动发送
    private int value;
    private String name;
    private CouponActionType(int value, String name) {
        this.value = value;
        this.name = name;
    }
    public int getValue() {
        return this.value;
    }

    public static CouponActionType valueOf(int value) {
        switch (value) {
            case 1:
                return APPLY;
            case 2:
                return SEND;
            default:
                return null;
        }
    }
    public String toString() {
        return this.name;
    }

}
