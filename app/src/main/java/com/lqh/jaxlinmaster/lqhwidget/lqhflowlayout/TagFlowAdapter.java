package com.lqh.jaxlinmaster.lqhwidget.lqhflowlayout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LqhCollectionsUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Linqh on 2018/12/11.
 *
 * @describe:
 */
public abstract class TagFlowAdapter<T> extends FlowAdapter<T> {
    //是否多选
    private boolean isMul;
    //默认选中的,这个是有序
    private TreeSet<Integer> selectSet = new TreeSet<Integer>();
    //最大可选个数
    private int maxSelectCount = Integer.MAX_VALUE;
    private boolean isSelectedReverse=false;//单选的时候是否可以反选,也就是可否一个都不选,默认不能反选
    private OnSelectListener mOnSelectListener;
    private OnTagClickListener mOnTagClickListener;
    private boolean isSupportUnableClick=false;//因为enable=false是不能响应事件的,如果非要的话,那得自己处理选中，未选中的，不可选中的背景处理

    public TagFlowAdapter(Context context, List list) {
        super(context, list);
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(FlowAdapter flowAdapter, View view, int position) {
                doSelect(flowAdapter,view,position);
            }
        });
    }
    private void doSelect(FlowAdapter flowAdapter,View view, int position) {
        //在可以被选中的情况下,才来做事情
        boolean isSetChange=false;
        if (isCanSelected(view, position,(T) flowAdapter.getList().get(position))) {
            if (isMul) {
                //多选
                if (selectSet.contains(position)) {
                    //之前选中
                    selectSet.remove(position);
                    performUnSelected(view, position,(T) flowAdapter.getList().get(position),true);
                    isSetChange=true;
                } else {
                    //之前没选中
                    if (maxSelectCount > selectSet.size()) {
                        //可以再加入
                        selectSet.add(position);
                        performOnSelected(view, position,(T) flowAdapter.getList().get(position),true);
                        isSetChange=true;
                    } else {
                        //超过最大数目看要做什么事情
                        clickOverLimitCount(view, position, (T)flowAdapter.getList().get(position),maxSelectCount);
                    }
                }
            } else {
                //单选
                if (selectSet.contains(position)) {
                    //之前选中
                    if (isSelectedReverse) {
                        selectSet.clear();
                        performUnSelected(view, position,(T) flowAdapter.getList().get(position),true);
                        isSetChange=true;
                    }else{
                        //不能反选就不做任何事了
                    }
                } else {
                    //之前没选中
                    Integer preIndex=null;
                    Iterator<Integer> iterator = selectSet.iterator();
                    if(iterator.hasNext()){
                         preIndex = iterator.next();
                    }
                    selectSet.clear();
                    selectSet.add(position);
                    if(preIndex!=null&&preIndex>=0){
                        //这边要获取之前未选中的View
                        ArrayList<LqhFlowDataSetObserver> observers = flowAdapter.mDataSetObservable.getObservers();
                        //这边是为了获取LqhFlowLayout
                        if(observers!=null&&observers.size()>0){
                            for(int i=0;i<observers.size();i++)
                            {
                                if(observers.get(i) instanceof LqhFlowLayout){
                                    LqhFlowLayout lqhFlowLayout = (LqhFlowLayout) observers.get(i);
                                    View preChild = lqhFlowLayout.getChildAt(preIndex);
                                    if(preChild!=null){
                                        performUnSelected(preChild, preIndex,(T) flowAdapter.getList().get(preIndex),false);
                                    }
                                }
                            }
                        }
                    }
                    performOnSelected(view, position,(T) flowAdapter.getList().get(position),true);
                    isSetChange=true;
                }
            }
        } else {
            //点击不能被选中的,可以做你想做的事情
            clickCannotSelectedView(view, position, (T) flowAdapter.getList().get(position),true);
        }
        if(mOnTagClickListener!=null){
            //这边为了允许你点击的时候做其他操作,但是无法操作选中结果
            mOnTagClickListener.onTagClick(this,view, position);
        }
        if(mOnSelectListener!=null&&isSetChange){
            //把已经选中的扔出去
            mOnSelectListener.onSelected(new TreeSet<Integer>(selectSet));
        }

    }

    //默认是可点击的
    protected boolean isCanSelected(View view, int position, T itemData) {
        return true;
    }

    /**
     * @param view
     * @param position
     * @param itemData
     * @param isClick //isClick true表示通过点击的回调,false表示代码中触发这个，比如onBindView里面
     */
    //点击了不能被选中的View的回调
    protected void clickCannotSelectedView(View view, int position, T itemData,boolean isClick) {

    }
    //超过最大个数点击后的回调
    protected void clickOverLimitCount(View view, int position, T itemData, int maxSelectCount) {

    }
    protected void onSelected(View view, int position, T itemData,boolean isClick){

    }
    protected void unSelected(View view, int position, T itemData,boolean isClick){

    }
    @Override
    protected void onBindView(View view, int position, T itemData) {
        if(view!=null){
            //为了让状态能持续往下传递
            if(view instanceof ViewGroup){
                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                    setDuplicateParentStateEnabled(((ViewGroup) view).getChildAt(i));
                }
            }
            setDuplicateParentStateEnabled(view);
            if(isCanSelected(view,position,itemData)){
                if(!isSupportUnableClick){
                    view.setEnabled(true);
                }
                if(selectSet.contains(position)){
                    performOnSelected(view,position,itemData,false);
                }else{
                    performUnSelected(view,position,itemData,false);
                }
            }else{
                if(!isSupportUnableClick){
                    view.setEnabled(false);
                }
                clickCannotSelectedView(view,position,itemData,false);
            }
        }
        onBindTagView(view,position,itemData);
    }
    //绑定数据
    protected abstract void onBindTagView(View view,int position,T itemData);

    public interface OnSelectListener {
        void onSelected(Set<Integer> selectPosSet);
    }
    public interface OnTagClickListener {
        void onTagClick(TagFlowAdapter tagFlowAdapter,View view,int position);
    }
    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }
    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        mOnTagClickListener = onTagClickListener;
    }

    private void performOnSelected(View view,int position,T itemData,boolean isClick) {
        view.setSelected(true);
        onSelected(view,position,itemData,isClick);
    }
    private void performUnSelected(View view,int position,T itemData,boolean isClick) {
        view.setSelected(false);
        unSelected(view,position,itemData,isClick);
    }

    public void setPreSet(TreeSet<Integer> set){
        if(selectSet==set){
            return;
        }
        selectSet.clear();
       if(set!=null){
           if(isMul){
                if(set.size()>maxSelectCount){
                    set.addAll(LqhCollectionsUtil.subTreeSet(set,maxSelectCount));
                }else{
                    selectSet.addAll(set);
                }
           }else{
            if(set.size()>0){
                Iterator<Integer> iterator = set.iterator();
                if(iterator.hasNext()){
                    //单选只拿第一个
                    selectSet.add(iterator.next());
                }
            }
           }
       }
        notifyDataSetChanged(false);
    }
    public void clearSelected(){
        selectSet.clear();
        notifyDataSetChanged(false);
    }
    private void setDuplicateParentStateEnabled(View view) {
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                setDuplicateParentStateEnabled(((ViewGroup) view).getChildAt(i));
            }
        }else{
            view.setDuplicateParentStateEnabled(true);
        }
    }

    public boolean isMul() {
        return isMul;
    }

    public void setMul(boolean mul) {
        isMul = mul;
    }

    public int getMaxSelectCount() {
        return maxSelectCount;
    }

    public void setMaxSelectCount(int maxSelectCount) {
        this.maxSelectCount = maxSelectCount;
    }

    public boolean isSelectedReverse() {
        return isSelectedReverse;
    }

    public void setSelectedReverse(boolean selectedReverse) {
        isSelectedReverse = selectedReverse;
    }
}
