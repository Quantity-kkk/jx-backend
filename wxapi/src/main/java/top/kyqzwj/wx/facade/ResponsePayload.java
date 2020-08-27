package top.kyqzwj.wx.facade;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/8/25 13:46
 */

public class ResponsePayload implements Serializable {
    private Object data = null;
    private int code;
    private Boolean success = false;
    private String message;
    private String exceptionKey = "";
    private static int OK = 200;
    private static int INTERNAL_SERVER_ERROR = 500;

    public ResponsePayload() {
    }

    public ResponsePayload(boolean success, int code, String msg, Object data){
        this.success = success;
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public static ResponsePayload success(Object data) {
        ResponsePayload responsePayload = new ResponsePayload();
        responsePayload.setSuccess(true);
        responsePayload.setData(data);
        responsePayload.setCode(OK);
        return responsePayload;
    }

    public static ResponsePayload success() {
        return success((Object)null);
    }

    public static ResponsePayload fail(Exception ex) {
        ResponsePayload responsePayload = new ResponsePayload();
        responsePayload.setSuccess(false);
        responsePayload.setCode(INTERNAL_SERVER_ERROR);
        responsePayload.setMessage(ex.getMessage());
        return responsePayload;
    }

    private static String printStackTrace(Throwable t) {
        if (t == null) {
            return "";
        } else {
            StringWriter sw = new StringWriter();
            t.printStackTrace(new PrintWriter(sw, true));
            return sw.getBuffer().toString();
        }
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExceptionKey() {
        return this.exceptionKey;
    }

    public void setExceptionKey(String exceptionKey) {
        this.exceptionKey = exceptionKey;
    }
}
