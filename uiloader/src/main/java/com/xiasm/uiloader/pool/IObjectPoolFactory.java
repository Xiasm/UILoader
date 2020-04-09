package com.xiasm.uiloader.pool;

public interface IObjectPoolFactory<T> {

    /**
     * 创建一个对象池中的对象
     * @return
     */
    T createObject();

    /**
     * 回收一个对象到对象池
     */
    T recyclerObject(T obj);

    /**
     * 激活一个对象，保证对象可以被再次使用
     * @return
     */
    T activeObject(T obj);

    /**
     * 销毁一个对象，释放其内存，当对象数量超过对象池最大限制时会调用此方法
     * @param obj
     */
    void destoryObject(T obj);

}
