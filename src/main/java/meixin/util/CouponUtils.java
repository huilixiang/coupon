package meixin.util;

import com.google.gson.Gson;
import meixin.entity.Coupon;
import meixin.entity.CouponBatch;

import java.io.IOException;


public class CouponUtils {


    public static <T> T parse(String json, Class<T> clazz) {
        T cb = new Gson().fromJson(json, clazz);
        return cb;
    }

    public static <T> String encode2Json(T cb) {
        Gson gson = new Gson();
        String json = gson.toJson(cb);
        return json;
    }


}