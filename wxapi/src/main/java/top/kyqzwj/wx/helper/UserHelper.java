package top.kyqzwj.wx.helper;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/11 10:47
 */
public class UserHelper {

    public static String getUserId(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
