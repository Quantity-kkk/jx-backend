package top.kyqzwj.wx.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.Assert;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/8/27 20:40
 */
public class StringUtil {
    private static final String fullWidthSpace = "　";
    /**
     * 判断是否为空字符串（null或者空串）
     */
    public static boolean isEmpty(Object... objs) {
        if (objs == null) {
            return true;
        } else {
            for (Object obj : objs) {
                if (obj != null && !"".equals(obj)) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 判断是否为非空字符串（不为null或者空串）
     */
    public static boolean isNotEmpty(Object... objs) {
        if (objs == null) {
            return false;
        } else {
            for (Object obj : objs) {
                if (obj == null || "".equals(obj)){
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 重构字符串，如果为null，返回空串
     */
    public static String replaceNull(Object obj) {
        if (isEmpty(obj)) {
            return "";
        } else {
            return obj.toString();
        }
    }

    /**
     * 重构字符串，如果为null，返回strDefault
     */
    public static String replaceNull(Object obj, String strDefault) {
        if (isEmpty(obj)) {
            return strDefault;
        } else {
            return obj.toString();
        }
    }

    /**
     * 全文替换字符串
     */
    public static String replaceAll(String src, String match, String as) {
        if (isEmpty(src) || isEmpty(match)) {
            return src;
        } else {
            return src.replace(match, as);
        }
    }

    /**
     * 替换第一匹配字符
     */
    public static String replaceFirst(String src, String match, String as) {
        if (isEmpty(src) || isEmpty(match)) {
            return src;
        } else {
            int start = src.indexOf(match);
            if (start != -1) {
                return new StringBuilder(src).replace(start, start + match.length(), replaceNull(as)).toString();
            } else {
                return src;
            }
        }
    }

    /**
     * 字符串反转
     */
    public static String reverse(String src) {
        if (isEmpty(src)) {
            return src;
        } else {
            return (new StringBuilder(src).reverse()).toString();
        }
    }

    /**
     * 替换最后一个匹配字符
     */
    public static String replaceLast(String src, String match, String as) {
        if (isEmpty(src) || isEmpty(match)) {
            return src;
        } else {
            src = reverse(src);
            match = reverse(match);
            as = reverse(as);

            return reverse(replaceFirst(src, match, as));
        }
    }

    /**
     * 将多个字符串合并成一个字符串
     */
    public static String join(String delim, String... objs) {
        if (objs == null) {
            return "";
        } else {
            return String.join(delim, objs);
        }
    }

    /**
     * 根据split字符串分割输入字符串到List
     */
    public static List<String> splitAsList(String src, String delim) {
        List lstRet = new ArrayList();

        if (isNotEmpty(src)) {
            if (src.indexOf(delim) == 0) {
                src = StringUtil.replaceFirst(src, delim, "");
            }
            if (src.lastIndexOf(delim) == (src.length() - 1)) {
                src = StringUtil.replaceLast(src, delim, "");
            }

            delim = ".".equals(delim) ? "\\." : delim;
            delim = "|".equals(delim) ? "\\|" : delim;
            lstRet = ArrayUtil.asList(src.split(delim));
        }
        return lstRet;
    }

    /**
     * 根据分隔符合并List到字符串
     */
    public static String joinList(List lstInput, String delim) {
        if (lstInput == null || lstInput.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();

        for (Iterator it = lstInput.iterator(); it.hasNext(); ) {
            sb.append(replaceNull(it.next()));

            if (it.hasNext()) {
                sb.append(delim);
            }
        }
        return sb.toString();
    }


    /**
     * 根据分隔符合并List到字符串
     */
    public static String joinForSqlIn(List lstInput, String delim) {
        if (lstInput == null || lstInput.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();

        for (Iterator it = lstInput.iterator(); it.hasNext(); ) {
            sb.append("'").append(replaceNull(it.next())).append("'");

            if (it.hasNext()) {
                sb.append(delim);
            }
        }

        return sb.toString();
    }

    /**
     * 获取字符串在数据库中的长度
     */
    public static int trueLength(String src) {
        return trueLength(src, "UTF-8");
    }

    public static int trueLength(String src, String encode) {
        int length = 0;
        if (StringUtil.isNotEmpty(src)) {
            try {
                byte[] bytes = src.getBytes(encode);
                length = bytes.length;
            } catch (UnsupportedEncodingException e) {
                length = -1;
            }

        }
        return length;
    }

    /**
     * 根据正则表达式获取匹配的字符串到List
     */
    public static List<String> getMatches(String strInput, String regex) {
        List<String> lstResult = new ArrayList();

        if (StringUtil.isNotEmpty(strInput)) {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(strInput);
            while (m.find()) {
                lstResult.add(m.group());
            }
        }
        return lstResult;
    }

    /**
     * 首字母大写
     */
    public static String capFirst(String str) {
        return capFirst(str, true);
    }

    public static String capFirst(String str, boolean isUpperCase) {
        if (isEmpty(str)) {
            return str;
        } else {
            if (isUpperCase) {
                return str.substring(0, 1).toUpperCase() + str.substring(1);
            } else {
                return str.substring(0, 1).toLowerCase() + str.substring(1);
            }
        }
    }

    /**
     * 去下划线变驼峰
     */
    public static String camelAndRemoveUnderline(String str) {
        if (isEmpty(str)) {
            return str;
        } else {
            String strRet = str.toLowerCase();
            int underlineLoc = str.indexOf("_");
            while (underlineLoc >= 0 && underlineLoc <= (strRet.length() - 1)) {
                if (underlineLoc == (strRet.length() - 1)) {
                    strRet = strRet.substring(0, underlineLoc);
                } else {
                    strRet = strRet.substring(0, underlineLoc)
                            + strRet.substring(underlineLoc + 1, underlineLoc + 2).toUpperCase()
                            + strRet.substring(underlineLoc + 2);
                    underlineLoc = strRet.indexOf("_");
                }
            }
            return strRet;
        }
    }

    /**
     * 去驼峰变下划线
     */
    public static String underlineAndRemoveCamel(String str) {
        if (isEmpty(str)) {
            return str;
        } else {
            String retStr = "";
            for (char c : str.toCharArray()) {
                if (Character.isUpperCase(c)) {
                    retStr = retStr + "_" + (c + "").toLowerCase();
                } else {
                    retStr = retStr + c;
                }
            }
            return retStr;
        }
    }

    /**
     * 根据Map中的参数，将字符串中的${xxx}变量替换成实际的值
     */
    public static String replaceParams(String strMsg, Map<String, Object> params) {
        if (MapUtil.isNotEmpty(params)) {
            for (String strKey : params.keySet()) {
                strMsg = replaceAll(strMsg, "${" + strKey + "}", replaceNull(params.get(strKey)));
            }
        }
        return strMsg;
    }

    /**
     * Change byte array to HEX string
     */
    public static String bytesToHexString(byte[] bArray, boolean upperCase) {
        StringBuilder sb = new StringBuilder(bArray.length * 2);

        String sTemp;
        for (int i : bArray) {
            sTemp = Integer.toHexString(0xFF & i);

            if (sTemp.length() < 2){
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return upperCase ? sb.toString() : sb.toString().toLowerCase();
    }

    public static String bytesToHexString(byte[] bArray) {
        return bytesToHexString(bArray, true);
    }

    /**
     * Change HEX string to byte array
     */
    public static byte[] hexStringToBytes(String hexStr) {
        byte[] byteArr = new byte[hexStr.length() / 2];
        char[] charArr = hexStr.toCharArray();

        for (int i = 0; i < byteArr.length; i++) {
            String item = Character.toString(charArr[i * 2]) + Character.toString(charArr[i * 2 + 1]);
            byteArr[i] = Integer.valueOf(item, 16).byteValue();
        }
        return byteArr;
    }

    /**
     * 将字符串转换为各单词首字母大写形式
     * 注意：方法使用的分割方式为split，为此，对于regex特殊字符（如"."，"|"等）需要进行转义
     *
     * @param strInput   原始字符串
     * @param delimRegex 正则表达式分隔符
     * @return String
     */
    public static String makeClassName(String strInput, String delimRegex) {
        if (StringUtil.isEmpty(strInput)) {
            return strInput;
        } else {
            String[] oriStrList = strInput.split(delimRegex);

            String finalStr = "";
            for (String word : oriStrList) {
                finalStr += capFirst(word.toLowerCase());
            }
            return finalStr;
        }
    }

    /**
     * 返回符合 RFC 6266 要求的附件下载header
     * 文件名按照 RFC 5987 要求进行编码
     *
     * @param filename 文件名
     * @return header
     * @see <a href="https://tools.ietf.org/html/rfc5987">RFC 5987</a>
     * @see <a href="https://tools.ietf.org/html/rfc6266">RFC 6266</a>
     */
    public static String getAttachmentContentDisposition(String filename) throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder("attachment");
        if (filename != null) {
            builder.append("; filename=\"");
            builder.append(new String(filename.getBytes("utf-8"), "iso8859-1")).append('\"');
            builder.append("; filename*=");
            builder.append(StringUtil.encodeHeaderFieldParam(filename, StandardCharsets.UTF_8));
        }
        return builder.toString();
    }

    /**
     * Encode the given header field param as describe in RFC 5987.
     *
     * @param input   the header field param
     * @param charset the charset of the header field param string
     * @return the encoded header field param
     * @see <a href="https://tools.ietf.org/html/rfc5987">RFC 5987</a>
     */
    public static String encodeHeaderFieldParam(String input, Charset charset) {
        Assert.notNull(input, "Input String should not be null");
        Assert.notNull(charset, "Charset should not be null");
        if (StandardCharsets.US_ASCII.equals(charset)) {
            return input;
        }
        Assert.isTrue(StandardCharsets.UTF_8.equals(charset) || StandardCharsets.ISO_8859_1.equals(charset),
                "Charset should be UTF-8 or ISO-8859-1");
        byte[] source = input.getBytes(charset);
        int len = source.length;
        StringBuilder sb = new StringBuilder(len << 1);
        sb.append(charset.name());
        sb.append("''");
        for (byte b : source) {
            if (isRFC5987AttrChar(b)) {
                sb.append((char) b);
            } else {
                sb.append('%');
                char hex1 = Character.toUpperCase(Character.forDigit((b >> 4) & 0xF, 16));
                char hex2 = Character.toUpperCase(Character.forDigit(b & 0xF, 16));
                sb.append(hex1);
                sb.append(hex2);
            }
        }
        return sb.toString();
    }

    private static boolean isRFC5987AttrChar(byte c) {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') ||
                c == '!' || c == '#' || c == '$' || c == '&' || c == '+' || c == '-' ||
                c == '.' || c == '^' || c == '_' || c == '`' || c == '|' || c == '~';
    }

    /**
     * 生成指定长度的随机字符串，使用所有字符集
     */
    public static String getRandomStr(int count) {
        return RandomStringUtils.random(count);
    }

    /**
     * 生成指定长度的随机字符串，包含数字和字母
     */
    public static String getRandomNumStr(int count) {
        return RandomStringUtils.randomAlphanumeric(count);
    }

    /**
     * 生成指定长度的随机字符串，仅包含数字
     */
    public static String getRandomNum(int count) {
        return RandomStringUtils.randomNumeric(count);
    }

    /**
     * 生成指定长度的随机字符串，使用ASCII字符集
     */
    public static String getRandomAscii(int count) {
        return RandomStringUtils.randomAscii(count);
    }


    /**
     * 替换全角空格
     *
     * @param str    原始字符串
     * @param toStrs 目标字符，若省略则替换为半角空格
     * @return 替换后的字符串
     */
    public static String replacefullWidthSpace(String str, String... toStrs) {
        String toStr = toStrs.length == 1 && isNotEmpty(toStrs[0]) ? toStrs[0] : " ";
        return replaceAll(str, fullWidthSpace, toStr);
    }

    public static String toSafeBase64(String encodeBase64) {
        String safeBase64Str = encodeBase64.replace('+', '-');
        safeBase64Str = safeBase64Str.replace('/', '_');
        safeBase64Str = safeBase64Str.replaceAll("=", "");
        return safeBase64Str;
    }

    public static String fromSafeBase64(String safeBase64Str) {
        String base64Str = safeBase64Str.replace('-', '+');
        base64Str = base64Str.replace('_', '/');
        int mod4 = base64Str.length() % 4;
        if (mod4 > 0) {
            base64Str = base64Str + "====".substring(mod4);
        }
        return base64Str;
    }



    /**
     * 对content的内容进行转换后，在作为oracle查询的条件字段值。使用/作为oracle的转义字符,比较合适。<br>
     * 既能达到效果,而且java代码相对容易理解，建议这种使用方式<br>
     * "%'" + content + "'%  ESCAPE '/' "这种拼接sql看起来也容易理解<br>
     */
    private static String escapeOracleSpecialChars(String content) {
        // 单引号是oracle字符串的边界,oralce中用2个单引号代表1个单引号
        String afterDecode = content.replaceAll("'", "''");
        // 由于使用了/作为ESCAPE的转义特殊字符,所以需要对该字符进行转义
        // 这里的作用是将"a/a"转成"a//a"
        afterDecode = afterDecode.replaceAll("/", "//");
        // 使用转义字符 /,对oracle特殊字符% 进行转义,只作为普通查询字符，不是模糊匹配
        afterDecode = afterDecode.replaceAll("%", "/%");
        // 使用转义字符 /,对oracle特殊字符_ 进行转义,只作为普通查询字符，不是模糊匹配
        afterDecode = afterDecode.replaceAll("_", "/_");
        return afterDecode;
    }

    private static String escapeMySqlSpecialChars(String content) {
        String afterDecode = content.replaceAll("'", "''");
        afterDecode = content.replaceAll("\"", "\\\"");
        afterDecode = afterDecode.replaceAll("/", "\\/");
        // 使用转义字符 /,对oracle特殊字符% 进行转义,只作为普通查询字符，不是模糊匹配
        afterDecode = afterDecode.replaceAll("%", "\\%");
        // 使用转义字符 /,对oracle特殊字符_ 进行转义,只作为普通查询字符，不是模糊匹配
        afterDecode = afterDecode.replaceAll("_", "\\_");
        return afterDecode;
    }


}
