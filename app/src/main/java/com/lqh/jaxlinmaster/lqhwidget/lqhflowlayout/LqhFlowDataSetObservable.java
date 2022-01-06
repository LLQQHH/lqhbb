package com.lqh.jaxlinmaster.lqhwidget.lqhflowlayout;

import android.database.Observable;

import java.util.ArrayList;

/**
 * Created by Linqh on 2021/11/12.
 *
 * @describe:
 */
//@CreateUidAnnotation(uid = "10100")
public class LqhFlowDataSetObservable extends Observable<LqhFlowDataSetObserver> {
    public void notifyChanged(boolean removeAndAdd) {
        synchronized(mObservers) {
            // since onChanged() is implemented by the app, it could do anything, including
            // removing itself from {@link mObservers} - and that could cause problems if
            // an iterator is used on the ArrayList {@link mObservers}.
            // to avoid such problems, just march thru the list in the reverse order.
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onChanged( removeAndAdd);
            }

        }
    }
    public void notifyChanged(int position) {
        synchronized(mObservers) {
            // since onChanged() is implemented by the app, it could do anything, including
            // removing itself from {@link mObservers} - and that could cause problems if
            // an iterator is used on the ArrayList {@link mObservers}.
            // to avoid such problems, just march thru the list in the reverse order.
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onChanged(position);
            }
        }
    }
    public ArrayList<LqhFlowDataSetObserver> getObservers(){
        return mObservers;
    }
}
