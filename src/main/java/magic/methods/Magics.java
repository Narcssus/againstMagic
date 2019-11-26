package magic.methods;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;

/**
 * @author : Narcssus
 * @date : 2019/11/25 22:12
 */

public class Magics {


    public static void main(String[] args) {
        String json = "[{\"a\":{\"b\":1},\"c\":\"123\",\"d\":123,\"e\":12345678900,\"f\":0.0,\"g\":[\"1\",\"2\"],\"h\":[{\"i\":1,\"j\":2},{\"i\":3,\"j\":4}]}]";
        System.out.println(genJsonCode(json, "myObject"));
        JSONArray myObject = new JSONArray();
        JSONObject myObject0 = new JSONObject();
        JSONObject myObject0A = new JSONObject();
        myObject0A.put("b", 1);
        myObject0.put("a", myObject0A);
        myObject0.put("c", "123");
        myObject0.put("d", 123);
        myObject0.put("e", 12345678900L);
        myObject0.put("f", 0.0);
        JSONArray myObject0G = new JSONArray();
        myObject0G.add("1");
        myObject0G.add("2");
        myObject0.put("g", myObject0G);
        JSONArray myObject0H = new JSONArray();
        JSONObject myObject0H0 = new JSONObject();
        myObject0H0.put("i", 1);
        myObject0H0.put("j", 2);
        myObject0H.add(myObject0H0);
        JSONObject myObject0H1 = new JSONObject();
        myObject0H1.put("i", 3);
        myObject0H1.put("j", 4);
        myObject0H.add(myObject0H1);
        myObject0.put("h", myObject0H);
        myObject.add(myObject0);
        JSONArray jsonObject = JSON.parseArray(json);
        System.out.println(myObject.toJSONString().equals(jsonObject.toJSONString()));
    }

    public static String genJsonCode(String str, String objName) {
        if (str.startsWith("{")) {
            return genJsonCode(JSON.parseObject(str), objName);
        }
        if (str.startsWith("[")) {
            return genJsonCode(JSON.parseArray(str), objName);
        }
        return null;
    }

    private static String genJsonCode(JSONArray jsonArray, String objName) {
        StringBuilder sb = new StringBuilder();
        sb.append("JSONArray ").append(objName).append(" = new JSONArray();\n");
        for (int i = 0; i < jsonArray.size(); i++) {
            Object o = jsonArray.get(i);
            if (o instanceof String) {
                String str = TypeUtils.castToString(o);
                if (str.contains("\"")) {
                    Object o1 = JSON.parse(str);
                    if (o1 instanceof JSONObject) {
                        sb.append(genJsonCode(JSON.parseObject(str), objName + i));
                    }
                    if (o1 instanceof JSONArray) {
                        sb.append(genJsonCode(JSON.parseArray(str), objName + i));
                    }
                    append(sb, objName, objName + i, o, ".toString()");
                } else {
                    sb.append(objName).append(".add(\"").append(o).append("\");\n");
                }
                continue;
            }
            if (o instanceof Long) {
                sb.append(objName).append(".add(").append(o).append(");\n");
                continue;
            }
            if (o instanceof JSONObject) {
                sb.append(genJsonCode(jsonArray.getJSONObject(i), objName + i));
                sb.append(objName).append(".add(").append(objName).append(i).append(");\n");
                continue;
            }
            if (o instanceof JSONArray) {
                sb.append(genJsonCode(jsonArray.getJSONArray(i), objName + i));
                sb.append(objName).append(".add(").append(objName).append(i).append(");\n");
                continue;
            }
            if (o == null) {
                sb.append(objName).append(".add(null);\n");
            }
            sb.append(objName).append(".add(").append(o).append(");\n");
        }


        return sb.toString();
    }

    private static String genJsonCode(JSONObject jsonObject, String objName) {
        StringBuilder sb = new StringBuilder();
        sb.append("JSONObject ").append(objName).append(" = new JSONObject();\n");
        for (String key : jsonObject.keySet()) {
            Object o = jsonObject.get(key);
            if (o instanceof String) {
                String str = TypeUtils.castToString(o);
                if (str.contains("\"")) {
                    Object o1 = JSON.parse(str);
                    if (o1 instanceof JSONObject) {
                        sb.append(genJsonCode(JSON.parseObject(str), objName + upperFirst(key)));
                    }
                    if (o1 instanceof JSONArray) {
                        sb.append(genJsonCode(JSON.parseArray(str), objName + upperFirst(key)));
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
                sb.append(genJsonCode(jsonObject.getJSONObject(key), objName + upperFirst(key)));
                append(sb, objName, key, objName, upperFirst(key));
                continue;
            }
            if (o instanceof JSONArray) {
                sb.append(genJsonCode(jsonObject.getJSONArray(key), objName + upperFirst(key)));
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
