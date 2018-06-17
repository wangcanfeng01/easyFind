package com.wcf.hellohome.common.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * @author WCF
 * @time 2018/4/21
 * @why 对象工具类
 **/
public class ObjectUtils {
    /**
     *@note 判断对象是否为空
     *@author WCF
     *@time 2018/6/10 20:51
     *@since v1.0
     * @param obj
     *@return boolean
     **/
    public static boolean isEmpty(Object obj) {
        if (null == obj) {
            return true;
        } else if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }else if(obj instanceof Collection){
            return ((Collection)obj).isEmpty();
        }else if(obj instanceof Map){
            return ((Map)obj).isEmpty();
        }
        //可能还存在其他情况？
        return false;
    }

    /**
     *@note 判断两个对象是否相等
     *@author WCF
     *@time 2018/6/10 20:51
     *@since v1.0
     * @param obj1
    * @param obj2
     *@return boolean
     **/
    public static boolean equal(Object obj1,Object obj2){
        if(obj1==obj2){
            return true;
        }else if(null==obj1||null==obj2){
            return false;
        }
        return obj1.equals(obj2);
    }
}
