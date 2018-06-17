package com.wcf.hellohome.common.constant;

import com.wcf.hellohome.common.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WCF
 * @time 2018/4/17
 * @why write something
 **/
public class WCFCache {
    /**
     * 默认存储1024个缓存
     */
    private static final int DEFAULT_CACHES_SIZE = 1024;
    /**
     * 内存池
     */
    private Map<String, CacheObject> pool;

    /**
     * 缓存单例
     */
    private static WCFCache single;

    /**
     *@note 构造单例缓存池
     *@author WCF
     *@time 2018/6/10 19:20
     *@since v1.0
     * @param
     *@return com.wcf.hellohome.common.constant.WCFCache
     **/
    public static WCFCache getInstance() {

        if (single == null) {
            single = new WCFCache();
        }
        return single;
    }

    private WCFCache(int size) {
        pool = new ConcurrentHashMap<>(size);
    }

    private WCFCache() {
        this(DEFAULT_CACHES_SIZE);
    }

    /**
     * 获取键值对应的值
     *
     * @param key
     * @return
     */
    public <T> T select(String key) {
        if (StringUtils.isNotBlank(key) && pool.containsKey(key)) {
            long count = -1;
            if (pool.get(key).getExpired().equals("0")) {
                count = 999;
            } else {
                String timeOut = pool.get(key).getExpired();
                //计算是否超过了过期时间
                count = Duration.between(LocalDateTime.now(), DateUtils.getTime(timeOut)).getSeconds();
            }
            if (count > 0) {
                return (T) pool.get(key).getValue();
            } else {
                //超时进行删除
                pool.remove(key);
                return null;
            }
        }
        return null;
    }

    /**
     * 永不过期的缓存类型
     *
     * @param key
     * @param value
     * @return
     */
    public WCFCache insert(String key, Object value) {
        return insert(key, value, "0");
    }

    /**
     * 往缓存表中插入数据
     *
     * @param key
     * @param value
     * @param expired 过期时间
     * @return
     */
    public WCFCache insert(String key, Object value, String expired) {
        CacheObject object = new CacheObject();
        object.setValue(value);
        object.setExpired(expired);
        pool.put(key, object);
        return single;
    }

    /**
     * 缓存实体类
     */
    class CacheObject {
        /**
         * 超时的时间
         */
        private String expired;
        /**
         * 类内存放值
         */
        private Object value;

        public String getExpired() {
            return expired;
        }

        public void setExpired(String expired) {
            this.expired = expired;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
}
