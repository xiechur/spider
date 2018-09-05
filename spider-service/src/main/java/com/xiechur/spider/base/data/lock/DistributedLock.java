package com.xiechur.spider.base.data.lock;

public interface DistributedLock {

    public boolean lock(String key) throws DistributedLockException;

    public boolean lock(String key, int keyLockTotalTime, int lockMaxLoopCount);

    public boolean lock(String key, int keyLockTotalTime);

    public boolean unlock(String key);

    public boolean isLocked(String key);

}
