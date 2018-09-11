package com.crazy.dashboard.service;

import com.crazy.code.httpclient.HttpClientException;
import com.crazy.code.httpclient.HttpClientUtils;
import com.crazy.code.util.GsonUtils;
import com.crazy.dashboard.model.Weather;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by developer2 on 2018/2/27.
 */
@Service
public class WeatherService {

    public List<Weather> getWeather(String city) throws HttpClientException {
        String result = HttpClientUtils
                .getString("https://www.sojson.com/open/api/weather/json.shtml?city="+city);
        Map<String,Object> resultMap = GsonUtils.toStrObjMap(result);
        Map<String,Object> data = (Map<String, Object>) resultMap.get("data");
        Type type = new TypeToken<List<Weather>>(){}.getType();
        return GsonUtils.fromJson(GsonUtils.toJson(data.get("forecast")),type);
    }

    public static void main(String[] args) throws HttpClientException {
        WeatherService weatherService = new WeatherService();
        weatherService.getWeather("汕头");
    }
}
