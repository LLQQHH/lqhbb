package com.lqh.jaxlinmaster.lqhwidget.lqhflowlayout;

import android.database.DataSetObserver;
import android.database.Observable;

/**
 * Created by Linqh on 2021/11/12.
 *
 * @describe:
 */
//@CreateUidAnnotation(uid = "10100")
public interface  LqhFlowDataSetObserver  {
     void onChanged(boolean removeAndAdd);
     void onChanged(int position);
}
