package meixin.util;

import com.google.gson.Gson;
import meixin.dto.CouponBatchDto;
import meixin.dto.CouponInfoDto;
import meixin.entity.*;


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


    public static CouponBatchDto couponBatchEntity2DTO(CouponBatch cb) {
        CouponBatchDto dto = new CouponBatchDto();
        dto.setBatchSn(cb.getSerialNo());
        dto.setBatchName(cb.getName());
        dto.setBatchType(cb.getCouponType().getValue());
        dto.setBatchTypeDesc(cb.getCouponType().toString());
        dto.setDescription(cb.getDesc());
        dto.setEffectiveEndTime(cb.getExpiringTime());
        dto.setEffectiveStartTime(cb.getEffectiveTime());
        dto.setMoney(cb.getDenomination());
        dto.setPublishedTime(cb.getPublishTime());
        dto.setShopId(cb.getShopID());
        dto.setId(cb.getId());
        dto.setPicture(cb.getPicture());
        //dto.setUsageRuleType();
        //dto.setUsageRule();
        return dto;
    }
    public static CouponBatch couponBatchDTO2Entity(CouponBatchDto dto) {
        CouponBatch cb = new CouponBatch();
        cb.setSerialNo(dto.getBatchSn());
        cb.setName(dto.getBatchName());
        cb.setCouponType(CouponType.valueOf(dto.getBatchType()));
        cb.setDesc(dto.getDescription());
        cb.setEffectiveTime(dto.getEffectiveStartTime());
        cb.setExpiringTime(dto.getEffectiveEndTime());
        cb.setDenomination(dto.getMoney());
        cb.setPublishTime(dto.getPublishedTime());
        cb.setShopID(dto.getShopId());
        cb.setId(dto.getId());
        cb.setPicture(dto.getPicture());
        return cb;
    }

    public static Coupon couponInfoDTO2Entity(CouponInfoDto dto) {
        Coupon coupon = new Coupon();
        coupon.setStatus(CouponStatus.valueOf(dto.getStatus()));
        coupon.setId(dto.getId());
        coupon.setType(CouponActionType.valueOf(dto.getReceiveType()));
        coupon.setBatchSerialNo(dto.getBatchSn());
        coupon.setOwner(dto.getUserId());
        coupon.setSerialNo(dto.getCouponSn());
        return coupon;
    }

    public static CouponInfoDto couponEntity2DTO(Coupon coupon) {
        CouponInfoDto dto = new CouponInfoDto();
        dto.setBatchSn(coupon.getBatchSerialNo());
        dto.setStatus(coupon.getStatus().getValue());
        dto.setCouponSn(coupon.getSerialNo());
        dto.setId(coupon.getId());
        dto.setReceiveType(coupon.getType().getValue());
        dto.setStatusDesc(coupon.getStatus().toString());
        dto.setUserId(coupon.getOwner());
        return dto;
    }



}