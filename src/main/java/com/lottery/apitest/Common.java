package com.lottery.apitest;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
public class Common {
    /**
     * 解析Json内容
     * 
     * @author Findyou
     * @version 1.0 2015/3/23
     * @return JsonValue 返回JsonString中JsonId对应的Value
     **/
    public static String getJsonValue(String JsonString, String JsonId) {
        String JsonValue = "";
        if (JsonString == null || JsonString.trim().length() < 1) {
            return null;
        }
        try {
            JSONObject obj1 = new JSONObject(JsonString);
            JsonValue = (String) obj1.getString(JsonId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return JsonValue;
    }
}
