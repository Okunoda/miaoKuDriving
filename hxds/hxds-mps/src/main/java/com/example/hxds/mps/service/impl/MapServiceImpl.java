package com.example.hxds.mps.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.hxds.common.exception.HxdsException;
import com.example.hxds.mps.service.MapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Okunoda 2023/12/29
 */
@Service
@Slf4j
public class MapServiceImpl implements MapService {
    @Value("${tencent.map.key}")
    private String key;

    //预估里程和时间
    private final String distanceUrl = "https://apis.map.qq.com/ws/distance/v1/matrix";

    //规划行进路线URL
    private final String directionUrl = "https://apis.map.qq.com/ws/direction/v1/driving/";




    @Override
    public HashMap<String, Object> estimateOrderMileageAndMinute(String mode,
                                                                 String startPlaceLatitude,
                                                                 String startPlaceLongitude,
                                                                 String endPlaceLatitude,
                                                                 String endPlaceLongitude) {

        HttpRequest request = new HttpRequest(distanceUrl);
        request.form("key", key)
                .form("from", startPlaceLatitude + "," + startPlaceLongitude)
                .form("to", endPlaceLatitude + "," + endPlaceLongitude)
                .form("mode", mode);
        //发送请求并获取返回值
        HttpResponse response = request.execute();
        JSONObject jsonObject = JSONUtil.parseObj(response.body());
        Long status = jsonObject.getLong("status");
        if (!status.equals(0L)) {
            log.error(jsonObject.getStr("message"));
            throw new HxdsException("获取距离里程数异常。" + jsonObject.getStr("message"));
        }

        JSONObject result = jsonObject.getJSONObject("result");
        JSONObject object = result.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0);
        Long distance = object.getLong("distance");
        Long duration = object.getLong("duration");

        String mileage = new BigDecimal(distance).divide(BigDecimal.valueOf(1000)).toString();
        String minute = new BigDecimal(duration).divide(BigDecimal.valueOf(60), RoundingMode.CEILING).toString();

        return new HashMap<>(2) {{
            put("mileage", mileage);
            put("minute", minute);
        }};
    }

    @Override
    public HashMap calculateDrivingLine(Map<String, Object> paramMap) {
        HttpRequest request = new HttpRequest(directionUrl);
        request.form("from", paramMap.get("startPlaceLatitude") + "," + paramMap.get("startPlaceLongitude"))
                .form("to", paramMap.get("endPlaceLatitude") + "," + paramMap.get("endPlaceLongitude"))
                .form("key", key);
        HttpResponse response = request.execute();
        JSONObject responseBody = JSONUtil.parseObj(response.body());

        String requestId = responseBody.getStr("request_id");
        Long status = responseBody.getLong("status");
        if (!status.equals(0L)) {
            log.error(responseBody.getStr("message") + "此次请求id为：" + requestId);
            throw new HxdsException(responseBody.getStr("message") + "此次请求id为：" + requestId);
        }

        JSONObject result = responseBody.getJSONObject("result");
        return result.toBean(HashMap.class);
    }
}
