package com.kawakp.kp.kernel.utils;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/10/6
 * 修改人:penghui.li
 * 修改时间:2017/10/6
 * 修改内容:
 *
 * 功能描述:对Realm数据库的单列操作
 */

public class RealmManager {
    private static Realm mRealm;

    private static class SingletonHolder {
        private static RealmManager INSTANCE = new RealmManager(mRealm);
    }

    /**
     * 构造方法，并传入Realm实例
     *
     * @param realm Realm实例
     */
    private RealmManager(Realm realm) {
        this.mRealm = realm;
    }

    /**
     * 获取RealmOperation的单例
     *
     * @param realm 传入realm实例对象
     * @return 返回RealmOperation的单例
     */
    public static RealmManager getInstance(Realm realm) {
        if (realm != null) {
            mRealm = realm;
        }
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取数据库保存路径
     *
     * @return 返回Realm数据库存储路径
     */
    public String getFilePath() {
        return mRealm.getPath();
    }

    /**
     * 增加单条数据到数据库中
     *
     * @param bean 数据对象，必须继承了RealmObject
     */
    public void add(final RealmObject bean) {
        mRealm.executeTransaction( realm -> realm.copyToRealmOrUpdate(bean));
    }

    /**
     * 增加多条数据到数据库中
     *
     * @param beans 数据集合，其中元素必须继承了RealmObject
     */
    public void add(final List<? extends RealmObject> beans) {
        mRealm.executeTransaction(realm -> {
            Log.e("RealmTest", "exe realm is !!!" + (realm == null ? "NULL":"NO NULL, beans size " + beans.size()));
            realm.copyToRealmOrUpdate(beans);
        });
    }

    /**
     * 增加多条数据到数据库中
     *
     * @param beans 数据集合，其中元素必须继承了RealmObject
     */
    public void addAsync(final List<? extends RealmObject> beans) {
        mRealm.executeTransactionAsync(realm -> realm.copyToRealmOrUpdate(beans));
    }

    /**
     * 删除数据库中clazz类所属所有元素
     *
     * @param clazz 需要操作的clazz类(数据表)
     */
    public void deleteAll(Class<? extends RealmObject> clazz) {
        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).findAll();
        mRealm.executeTransaction(realm -> beans.deleteAllFromRealm());
    }

    /**
     * 删除数据库中clazz类所属所有元素
     *
     * @param clazz 需要操作的clazz类(数据表)
     */
    public void deleteAllAsync(Class<? extends RealmObject> clazz) {
        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).findAll();
        mRealm.executeTransactionAsync(realm -> beans.deleteAllFromRealm());
    }

    /**
     * 删除数据库中clazz类所属第一个元素
     *
     * @param clazz 需要操作的clazz类(数据表)
     */
    public void deleteFirst(Class<? extends RealmObject> clazz) {
        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).findAll();
        mRealm.executeTransaction(realm -> beans.deleteFirstFromRealm());
    }

    /**
     * 删除数据库中clazz类所属最后一个元素
     *
     * @param clazz 需要操作的clazz类(数据表)
     */
    public void deleteLast(Class<? extends RealmObject> clazz) {
        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).findAll();
        mRealm.executeTransaction(realm -> beans.deleteLastFromRealm());
    }

    /**
     * 删除数据库中clazz类所属数据中某一位置的元素
     *
     * @param clazz 需要操作的clazz类(数据表)
     * @param position 需要删除的位置
     */
    public void deleteElement(Class<? extends RealmObject> clazz, final int position) {
        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).findAll();
        mRealm.executeTransaction(realm -> beans.deleteFromRealm(position));
    }

    /**
     * 查询数据库中clazz类所属所有数据
     *
     * @param clazz 需要操作的clazz类(数据表)
     * @return 返回查询结果
     */
    public RealmResults<? extends RealmObject> queryAll(Class<? extends RealmObject> clazz) {
        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).findAll();
        return beans;
    }

    /**
     *  查询数据库中clazz类所属所有数据,并把查询数据转换为指定List
     *
     * @param clazz 需要操作的clazz类(数据表)
     * @param result  返回值(此处会清空list里面的数据，以保证List对象全是查询的返回值)
     */
    @SuppressWarnings("unchecked")
    public void queryAllToList(Class<? extends RealmObject> clazz, List result) {
        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).findAll();
        if (null == result){
            result = new ArrayList();
        }
        result.clear();
        result.addAll(mRealm.copyFromRealm(beans));
    }

    /**
     * 查询数据库中clazz类所属所有数据
     *
     * @param clazz 需要操作的clazz类(数据表)
     * @return 返回查询结果
     */
    public RealmResults<? extends RealmObject> queryAllAsync(Class<? extends RealmObject> clazz) {
        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).findAllAsync();
        return  beans;
    }

    /**
     * 查询满足条件的第一个数据
     *
     * @param clazz 需要操作的clazz类(数据表)
     * @param fieldName 查询条件，对应数据表的字段名
     * @param value 查询条件，fieldName需满足的条件( id = 10，id 对应与fileName，value对应于10)
     * @return 返回查询结果
     * @throws NoSuchFieldException 对应字段不存在抛出异常 对应字段不存在抛出异常
     */
    public RealmObject queryByFieldFirst(Class<? extends RealmObject> clazz, String fieldName, String value) throws NoSuchFieldException {
        final RealmObject bean = mRealm.where(clazz).equalTo(fieldName, value).findFirst();
        return bean;
    }

    /**
     * 查询满足条件的所有数据
     *
     * @param clazz 需要操作的clazz类(数据表)
     * @param fieldName 查询条件，对应数据表的字段名
     * @param value 查询条件，fieldName需满足的条件( id = 10，id 对应与fileName，value对应于10)
     * @return 返回查询结果
     * @throws NoSuchFieldException 对应字段不存在抛出异常
     */
    public RealmResults<? extends RealmObject> queryByFieldAll(Class<? extends RealmObject> clazz, String fieldName, String value) throws NoSuchFieldException {
        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).equalTo(fieldName, value).findAll();
        return beans;
    }

    /**
     * 查询满足条件的所有数据
     *
     * @param clazz 需要操作的clazz类(数据表)
     * @param fieldName 查询条件，对应数据表的字段名
     * @param value 查询条件，fieldName需满足的条件( id = 10，id 对应与fileName，value对应于10)
     * @return 返回查询结果
     * @throws NoSuchFieldException 对应字段不存在抛出异常
     */
    public RealmResults<? extends RealmObject> queryByFieldAllAsync(Class<? extends RealmObject> clazz, String fieldName, String value) throws NoSuchFieldException {
        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).equalTo(fieldName, value).findAllAsync();
        return beans;
    }

    /**
     * 查询满足条件的第一个数据
     *
     * @param clazz 需要操作的clazz类(数据表)
     * @param fieldName 查询条件，对应数据表的字段名
     * @param value 查询条件，fieldName需满足的条件( id = 10，id 对应与fileName，value对应于10)
     * @return 返回查询结果
     * @throws NoSuchFieldException 对应字段不存在抛出异常
     */
    public RealmObject queryByFieldFirst(Class<? extends RealmObject> clazz, String fieldName, int value) throws NoSuchFieldException {
        final RealmObject bean = mRealm.where(clazz).equalTo(fieldName, value).findFirst();
        return bean;
    }

    /**
     * 查询满足条件的所有数据
     *
     * @param clazz 需要操作的clazz类(数据表)
     * @param fieldName 查询条件，对应数据表的字段名
     * @param value 查询条件，fieldName需满足的条件( id = 10，id 对应与fileName，value对应于10)
     * @return 返回查询结果
     * @throws NoSuchFieldException 对应字段不存在抛出异常
     */
    public RealmResults<? extends RealmObject> queryByFieldAll(Class<? extends RealmObject> clazz, String fieldName, int value) throws NoSuchFieldException {
        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).equalTo(fieldName, value).findAll();
        return beans;
    }

    /**
     * 查询满足条件的所有数据
     *
     * @param clazz 需要操作的clazz类(数据表)
     * @param fieldName 查询条件，对应数据表的字段名
     * @param value 查询条件，fieldName需满足的条件( id = 10，id 对应与fileName，value对应于10)
     * @return 返回查询结果
     * @throws NoSuchFieldException 对应字段不存在抛出异常
     */
    public RealmResults<? extends RealmObject> queryByFieldAllAsync(Class<? extends RealmObject> clazz, String fieldName, int value) throws NoSuchFieldException {
        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).equalTo(fieldName, value).findAllAsync();
        return beans;
    }

    /**
     * 查询数据，按增量排序
     *
     * @param clazz 需要操作的clazz类(数据表)
     * @param fieldName 排序字段，对应数据表的字段名
     * @return 返回查询结果
     */
    public List<? extends RealmObject> queryAllByAscending(Class<? extends RealmObject> clazz, String fieldName) {
        RealmResults<? extends RealmObject> beans = mRealm.where(clazz).findAll();
        RealmResults<? extends RealmObject> results = beans.sort(fieldName, Sort.ASCENDING);
        return mRealm.copyFromRealm(results);
    }

    /**
     * 查询数据，按降量排序
     *
     * @param clazz 需要操作的clazz类(数据表)
     * @param fieldName 排序字段，对应数据表的字段名
     * @return 返回查询结果
     */
    public List<? extends RealmObject> queryAllByDescending(Class<? extends RealmObject> clazz, String fieldName) {
        RealmResults<? extends RealmObject> beans = mRealm.where(clazz).findAll();
        RealmResults<? extends RealmObject> results = beans.sort(fieldName, Sort.DESCENDING);
        return mRealm.copyFromRealm(results);
    }

    /**
     * 更新满足某个条件的第一个数据的属性值
     *
     * @param clazz 需要操作的clazz类(数据表)
     * @param fieldName 更新字段，对应数据表的字段名
     * @param oldValue 更新前的数据
     * @param newValue 更新后的数据
     * @throws NoSuchMethodException 方法不存在，抛出异常
     * @throws InvocationTargetException 抛出异常
     * @throws IllegalAccessException 非法存储(权限)，抛出异常
     */
    public void updateFirstByField(Class<? extends RealmObject> clazz, String fieldName,String oldValue,String newValue) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final RealmObject bean = mRealm.where(clazz).equalTo(fieldName, oldValue).findFirst();
        mRealm.beginTransaction();
        Method method = clazz.getMethod(fieldName, String.class);
        method.invoke(bean,newValue);
        mRealm.commitTransaction();
    }

    /**
     * 更新满足某个条件的第一个数据的属性值
     * @param clazz 需要操作的clazz类(数据表)
     * @param fieldName 更新字段，对应数据表的字段名
     * @param oldValue 更新前的数据
     * @param newValue 更新后的数据
     * @throws NoSuchMethodException 方法不存在，抛出异常
     * @throws InvocationTargetException 抛出异常
     * @throws IllegalAccessException 非法存储(权限)，抛出异常
     */
    public void updateFirstByField(Class<? extends RealmObject> clazz, String fieldName,int oldValue,int newValue) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final RealmObject bean = mRealm.where(clazz).equalTo(fieldName, oldValue).findFirst();
        mRealm.beginTransaction();
        Method method = clazz.getMethod(fieldName, int.class);
        method.invoke(bean,newValue);
        mRealm.commitTransaction();
    }

    /**
     * 更新满足某个条件的第一个数据的属性值
     * @param clazz 需要操作的clazz类(数据表)
     * @param fieldName 更新字段，对应数据表的字段名
     * @param oldValue 更新前的数据
     * @param newValue 更新后的数据
     * @throws NoSuchMethodException 方法不存在，抛出异常
     * @throws InvocationTargetException 抛出异常
     * @throws IllegalAccessException 非法存储(权限)，抛出异常
     */
    public void updateAllByField(Class<? extends RealmObject> clazz, String fieldName,String oldValue,String newValue) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).equalTo(fieldName, oldValue).findAll();
        mRealm.beginTransaction();
        Method method = clazz.getMethod(fieldName, String.class);
        for(int i=0;i<beans.size();i++){
            RealmObject realmObject = beans.get(i);
            method.invoke(realmObject,newValue);
        }
        mRealm.commitTransaction();
    }

    /**
     * 更新满足某个条件的第一个数据的属性值
     * @param clazz 需要操作的clazz类(数据表)
     * @param fieldName 更新字段，对应数据表的字段名
     * @param oldValue 更新前的数据
     * @param newValue 更新后的数据
     * @throws NoSuchMethodException 方法不存在，抛出异常
     * @throws InvocationTargetException 抛出异常
     * @throws IllegalAccessException 非法存储(权限)，抛出异常
     */
    public void updateAllByField(Class<? extends RealmObject> clazz, String fieldName,int oldValue,int newValue) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).equalTo(fieldName, oldValue).findAll();
        mRealm.beginTransaction();
        Method method = clazz.getMethod(fieldName, int.class);
        for(int i=0;i<beans.size();i++){
            RealmObject realmObject = beans.get(i);
            method.invoke(realmObject,newValue);
        }
        mRealm.commitTransaction();
    }

}
