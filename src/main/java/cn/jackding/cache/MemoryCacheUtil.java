package cn.jackding.cache;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Jack
 * @Date 2019/12/12 9:50
 * @Version 1.0.0
 */
public class MemoryCacheUtil {


    //缓存对象
    private static ConcurrentHashMap<String, Object> cacheMap;
    //定时任务存储对象
    private static ConcurrentHashMap<String, CleanTask> timerMap;

    private static Timer timer;

    static {
        cacheMap = new ConcurrentHashMap<>();
        timerMap = new ConcurrentHashMap<>();
        timer = new Timer();
    }

    private MemoryCacheUtil() {
    }

    private static volatile MemoryCacheUtil memoryCacheUtil;

    public static MemoryCacheUtil getInstances() {
        if (null == memoryCacheUtil) {
            synchronized (MemoryCacheUtil.class) {
                if (null == memoryCacheUtil) {
                    return memoryCacheUtil = new MemoryCacheUtil();
                }
            }
        }
        return memoryCacheUtil;
    }

    /**
     * 存储缓存
     *
     * @param key
     * @param value
     */
    public void setCache(String key, Object value) {
        cacheMap.put(key, value);
    }

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    public Object getCache(String key) {
        return cacheMap.get(key);
    }

    /**
     * 设置缓存  并设置过期时间
     *
     * @param key
     * @param value
     * @param existTime 毫秒
     */
    public void setCache(String key, Object value, long existTime) {
        setCache(key, value);
        CleanTask cleanTask = timerMap.get(key);
        if (null != cleanTask) {
            cleanTask.cancel();       //防止上一次的定时任务把缓存删除了
        }
        cleanTask = new CleanTask(key);
        timerMap.put(key, cleanTask);
        timer.schedule(cleanTask, existTime);
    }

    private static class CleanTask extends TimerTask {

        private String key;

        public CleanTask(String key) {
            this.key = key;
        }

        @Override
        public void run() {
            cacheMap.remove(key);
            timerMap.remove(key);
        }
    }

}
