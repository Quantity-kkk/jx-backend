package top.kyqzwj.wx.jpa.repository;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/8/27 22:23
 */
@FunctionalInterface
public interface DomainCompileHandler<T> {
    boolean compare(T var1, T var2);
}
