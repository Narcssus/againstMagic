package magic.utils;


import org.springframework.util.StringUtils;


/**
 * @author : Narcssus
 * @date : 2019/11/27 22:04
 */
public class MyStringUtils extends StringUtils {

    /**
     * 给字符串中的数字添加千分符
     *
     * @param str 字符串
     * @return 处理后的字符串
     */
    static String addThousandMark(String str) {
        if (isEmpty(str)) {
            return str;
        }
        char[] chars = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        StringBuilder tmp = new StringBuilder();
        for (char c : chars) {
            if (c >= 48 && c <= 57) {
                tmp.append(c);
            } else {
                sb.append(tmp.toString().replaceAll("(?<=\\d)(?=(?:\\d{3})+$)", ","))
                        .append(c);
                tmp = new StringBuilder();
            }
        }
        if (tmp.length() > 0) {
            sb.append(tmp.toString().replaceAll("(?<=\\d)(?=(?:\\d{3})+$)", ","));
        }
        return sb.toString();
    }

}
