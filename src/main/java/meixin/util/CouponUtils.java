package meixin.util;

import com.google.gson.Gson;
import meixin.entity.Coupon;
import meixin.entity.CouponBatch;

import java.io.IOException;


public class CouponUtils {


    public static CouponBatch parse(String json) {
        CouponBatch cb = new Gson().fromJson(json, CouponBatch.class);
        return cb;
    }

    public static String encode2Json(CouponBatch cb) {
        Gson gson = new Gson();
        String json = gson.toJson(cb);
        return json;
    }

}