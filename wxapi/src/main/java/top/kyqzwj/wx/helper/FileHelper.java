package top.kyqzwj.wx.helper;

import top.kyqzwj.wx.util.StringUtil;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/11 16:15
 */
public class FileHelper {

    public static boolean isFileId(String str){
        return StringUtil.isNotEmpty(str) && !(str.startsWith("https://") || str.startsWith("http://"));
    }

}
