package com.xiasm.uiloader.pool;

import java.util.LinkedList;

public class ObjectPool<T> {
    public static final int DEFAULT_MAX_OBJECT = 10;

    private int maxLimit = DEFAULT_MAX_OBJECT;

    protected LinkedList<T> _pool;
    protected IObjectPoolFactory<T> _factory;

    public ObjectPool(IObjectPoolFactory<T> factory) {
        this(factory, DEFAULT_MAX_OBJECT);
    }

    public ObjectPool(IObjectPoolFactory<T> factory, int maxLimit) {
        if (factory == null) {
            throw new RuntimeException("factory cannot be null");
        }
        if (maxLimit <= 0) {
            throw new RuntimeException("maxLimit must be greater than zero");
        }
        this.maxLimit = maxLimit;
        _pool = new LinkedList<>();
        _factory = factory;
    }

    public T borrowObject() {
        T ret = null;
        synchronized (this) {
            if (_pool.size() > 0) {
                ret = _factory.activeObject(_pool.poll());
            } else {
                ret = _factory.activeObject(_factory.createObject());
            }
        }
        return ret;
    }

    public void returnObject(T obj) {
        if (obj != null) {
            synchronized (this) {
                if (_pool.size() < maxLimit) {
                    T tmp = _factory.recyclerObject(obj);
                    if (tmp != null) {
                        _pool.offer(tmp);
                    }
                } else {
                    _factory.destoryObject(obj);
                }
            }
        }
    }

    public void clear() {
        synchronized (this) {
            for (T t : _pool) {
                if (t != null) {
                    _factory.destoryObject(t);
                }
            }
            _pool.clear();
        }
    }
}
