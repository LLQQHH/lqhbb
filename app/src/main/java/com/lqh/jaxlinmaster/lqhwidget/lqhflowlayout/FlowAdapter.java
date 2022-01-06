package com.lqh.jaxlinmaster.lqhwidget.lqhflowlayout;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linqh on 2018/12/11.
 *
 * @describe:
 */
public abstract class FlowAdapter<T>{
    protected Context context;
    protected List<T> list;
    protected final LqhFlowDataSetObservable mDataSetObservable = new LqhFlowDataSetObservable();

    public FlowAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = (List)(list == null ? new ArrayList() : list);
    }
    public void setList(List<T> list) {
        this.list.clear();
        this.list.addAll(list);
    }
    public List<T> getList() {
        return list;
    }
    public int getCount(){
        return list==null?0:list.size();
    }



    //创建ViewHolder
    public abstract View onCreateView(ViewGroup parent,int position,T itemData);
    //绑定数据
    protected abstract void onBindView(View view,int position,T itemData);
//    public  static class ViewHolder {
//        View mConvertView;
//        private SparseArray<View> mViews;
//        public ViewHolder(View mConvertView) {
//            this.mConvertView = mConvertView;
//            this.mViews=new SparseArray<>();
//        }
//
//        public <T extends View> T getView(int viewId) {
//            View view = mViews.get(viewId);
//            if (view == null) {
//                view = mConvertView.findViewById(viewId);
//                mViews.put(viewId, view);
//            }
//            try {
//                return (T) view;
//            } catch (ClassCastException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        public void setText(int viewId, String text) {
//            TextView view = getView(viewId);
//            view.setText(text);
//        }
//    }
    private  OnItemClickListener mOnItemClickListener;
    private  OnItemLongClickListener mOnItemLongClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return mOnItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(FlowAdapter flowAdapter, View view, int position);
    }
    public interface OnItemLongClickListener {
        boolean onItemLongClick(FlowAdapter flowAdapter, View view, int position);
    }
    //全部更新吧
    public void notifyDataSetChanged() {
        notifyDataSetChanged(true);
    }
    public void notifyDataSetChanged(boolean removeAndAdd) {
        mDataSetObservable.notifyChanged(true);
    }
    //局部刷新
    public void notifyDataSetChanged(int position) {
        mDataSetObservable.notifyChanged(position);
    }

    public void removeDataPosition(int position){
        T remove = list.remove(position);
        if(remove!=null){
            mDataSetObservable.notifyChanged(true);
        }
    }
    public void addDataPosition(T itemData){
        this.addDataPosition(itemData,list.size());
    }
    public void addDataPosition(T itemData,int position){
       list.add(position,itemData);
       mDataSetObservable.notifyChanged(true);
    }
    public void registerDataSetObserver(LqhFlowDataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    public void unregisterDataSetObserver(LqhFlowDataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }
}
