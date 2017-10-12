package com.kawakp.kp.kernel.utils;

import android.content.Context;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmModel;
import io.realm.RealmObject;

import static com.kawakp.kp.kernel.utils.RealmManager.getInstance;


/**
 * 创建人: qi
 * 创建时间: 2017/10/6
 * 修改人:qi
 * 修改时间:2017/10/6
 * 修改内容:
 * <p>
 * 功能描述:对Realm数据库一次性存储多条
 */
public class SaveUtils {

    /**
     * @param list
     * @return 批量保存是否成功
     */
    public static boolean insert(Realm realm, List<? extends RealmObject> list) {
        int count = list.size();
        if (count >= 1000) {
            try {
                realm.beginTransaction();
                realm.insert(list);
                realm.commitTransaction();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                realm.cancelTransaction();
                return false;
            }
        } else return false;
    }


}
