package com.lqh.jaxlinmaster.lqhcommon.lqhutils;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Linqh on 2021/11/24.
 *
 * @describe:
 */
//@CreateUidAnnotation(uid = "10100")
public class LqhCollectionsUtil {
    public static<T> Set<T> subTreeSet(Set<T> objSet, int size) {
        Set<T> objSubSet = new TreeSet();
        if (LqhCollectionsUtil.isNullOrEmpty(objSet)) {
            return objSubSet;
        }
        int realSize = objSet.size() < size ? objSet.size() : size;
        Iterator<T> iterator = objSet.iterator();
        for (int i = 0; i < realSize; i++) {
            objSubSet.add(iterator.next());
        }
        return objSubSet;
    }
    public static boolean isNullOrEmpty(Set set){
        if(set==null||set.size()==0){
            return true;
        }
        return false;
    }
}
