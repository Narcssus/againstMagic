package magic.methods;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * @author : Narcssus
 * @date : 2019/11/25 22:12
 */

public class Magics {


    public static String genCode(JSONArray jsonArray, String objName) {
        StringBuilder sb = new StringBuilder();
        sb.append("JSONArray ").append(objName).append(" = new JSONArray();\n");
        for (int i = 0; i < jsonArray.size(); i++) {
            Object o = jsonArray.get(i);
// TODO: 2019/11/25 咏唱中
        }


        return sb.toString();
    }

    public static void main(String[] args) {
        String json = "{\"a\":{\"b\":1},\"c\":\"123\",\"d\":123,\"e\":12345678900,\"f\":0.0}";
        JSONObject jsonObject = JSON.parseObject(json);
        System.out.println(genCode(jsonObject, "myObject"));

        JSONObject myObject = new JSONObject();
        JSONObject myObjectA = new JSONObject();
        myObjectA.put("b", 1);
        myObject.put("a", myObjectA);
        myObject.put("c", "123");
        myObject.put("d", 123);
        myObject.put("e", 12345678900L);
        myObject.put("f", 0.0);
        System.out.println(myObject.toJSONString().equals(jsonObject.toJSONString()));
    }


    public static String genCode(JSONObject jsonObject, String objName) {
        StringBuilder sb = new StringBuilder();
        sb.append("JSONObject ").append(objName).append(" = new JSONObject();\n");
        for (String key : jsonObject.keySet()) {
            Object o = jsonObject.get(key);
            if (o instanceof String) {
                String str = TypeUtils.castToString(o);
                if (str.contains("\"")) {
                    Object o1 = JSON.parse(str);
                    if (o1 instanceof JSONObject) {
                        sb.append(genCode(JSON.parseObject(str), objName + upperFirst(key)));
                    }
                    if (o1 instanceof JSONArray) {
                        sb.append(genCode(JSON.parseArray(str), objName + upperFirst(key)));
                    }
                    append(sb, objName, key, o, ".toString()");

                } else {
                    append(sb, objName, key, o, "\"", "\"");
                }
                continue;
            }
            if (o instanceof Long) {
                append(sb, objName, key, o, "L");
                continue;
            }
            if (o instanceof JSONObject) {
                sb.append(genCode(jsonObject.getJSONObject(key), objName + upperFirst(key)));
                append(sb, objName, key, objName, upperFirst(key));
                continue;
            }
            if (o instanceof JSONArray) {
                sb.append(genCode(jsonObject.getJSONArray(key), objName + upperFirst(key)));
                append(sb, objName, key, objName, upperFirst(key));
                continue;
            }
            if (o == null) {
                append(sb, objName, key, "null");
            }
            append(sb, objName, key, o);
        }
        return sb.toString();
    }


    private static void append(StringBuilder sb, String objName, String key, Object object) {
        append(sb, objName, key, object, "");
    }


    private static void append(StringBuilder sb, String objName, String key, Object object, String suffix) {
        append(sb, objName, key, object, suffix, "");
    }

    private static void append(StringBuilder sb, String objName, String key, Object object, String suffix, String prefix) {
        sb.append(objName).append(".put(\"").append(key)
                .append("\", ").append(prefix).append(object).append(suffix).append(");\n");
    }


    private static String upperFirst(String str) {
        char[] chars = str.toCharArray();
        chars[0] -= 32;
        return new String(chars);
    }


}
