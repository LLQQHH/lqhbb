package com.lqh.jaxlinmaster.lqhwidget.lqhflowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.internal.FlowLayout;
import com.lqh.jaxlinmaster.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Linqh on 2018/12/10.
 *
 * @describe:
 */
public class LqhFlowLayout extends ViewGroup implements LqhFlowDataSetObserver{
    private static final String TAG = FlowLayout.class.getSimpleName();
    //水平间隔
    int horizontalSpace;
    //竖直间隔
    int verticalSpace;
    /**
     * 代表每一行的集合
     */
    private final List<Line> mLines = new ArrayList<>();
    //左对齐
    private static final int START = 0;
    //居中
    private static final int CENTER = 1;
    //右对齐
    private static final int END = 2;
    //两端对齐
    private static final int TWO_SIDE = 3;
    private int align_model=0;
    private int align_item_model=0;
    //最多几行
    private int maxLine=Integer.MAX_VALUE;
    private int tempMaxLine;
    //是否收起
    private boolean isCollapsed=true;
    //适配器
    private FlowAdapter mFlowAdapter;
    //private LqhFlowDataSetObserver mDataSetObserver;

    public LqhFlowLayout(Context context) {
        this(context,null);
    }

    public LqhFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LqhFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initArr(context,attrs);

    }
    private void initArr(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LqhFlowLayout);
        horizontalSpace=ta.getDimensionPixelSize(R.styleable.LqhFlowLayout_lqhflow_horizontalSpace,0);
        verticalSpace=ta.getDimensionPixelSize(R.styleable.LqhFlowLayout_lqhflow_verticalSpace,0);
        align_model = ta.getInt(R.styleable.LqhFlowLayout_lqhflow_align_model, 0);
        align_item_model = ta.getInt(R.styleable.LqhFlowLayout_lqhflow_align_item_model, 0);
        maxLine = ta.getInt(R.styleable.LqhFlowLayout_lqhflow_maxLine, Integer.MAX_VALUE);
    }
    //确定大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        tempMaxLine=Integer.MAX_VALUE;
        beginMeasure(widthMeasureSpec,heightMeasureSpec);
        if(getCurrentLineSize()>maxLine){
            if(mOnOverLineChangeListener!=null){
                mOnOverLineChangeListener.onOverLineChanged(true);
            }
            if (isCollapsed) {
                tempMaxLine=maxLine;//这样会导致再次调用beginMeasure,影响性能，先这么做吧
                beginMeasure(widthMeasureSpec,heightMeasureSpec);
            }else{
                tempMaxLine=Integer.MAX_VALUE;
            }
            if(mOnExpandStateChangeListener!=null){
                mOnExpandStateChangeListener.onExpandStateChanged(isCollapsed);
            }
        }else{
            if(mOnOverLineChangeListener!=null){
                mOnOverLineChangeListener.onOverLineChanged(false);
            }
        }
    }
    public void beginMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int remainWidth=sizeWidth - getPaddingLeft() - getPaddingRight();
        int lineWidth = 0;//记录每一行的宽度
        int lineHeight = 0;//记录每一行的高度
        int totalHeight = 0;//记录整个FlowLayout所占高度
        int totalWidth = 0;//记录整个FlowLayout所占宽度
        int childCount = getChildCount();//获取个数
        mLines.clear();
        Line tempLine=new Line();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);//获取子View
            if (child.getVisibility() == GONE) {
                continue;
            }
            measureChild(child, widthMeasureSpec, heightMeasureSpec);//测量子View
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();//强制成mar
            int childWidth = lp.leftMargin + child.getMeasuredWidth() + lp.rightMargin;//获取子View所占宽度
            int childHeight = lp.topMargin + child.getMeasuredHeight() + lp.bottomMargin;//获取子View所占高度
            //判断要不要换行,大于表示要换行
            int tempLineWidth=lineWidth + childWidth;
            if (tempLineWidth<=remainWidth) {
                //不需要换行
                lineWidth += childWidth+verticalSpace;//行宽度增加,宽度得加上verticalSpace
                lineHeight = Math.max(lineHeight, childHeight);//行高取最大
                //不换行直接添加
                tempLine.addView(child);
            } else {
                //需要换行
                //计算当前View上一行的高度
                totalWidth = Math.max(lineWidth, totalWidth);//获取两者最大的当宽度
                mLines.add(tempLine);//先添加上一行的
                if(tempMaxLine<=mLines.size()){
                    break;
                }
                //===当前View所在的行
                //因为由于盛不下当前控件，而将此控件调到下一行，所以将此控件的高度和宽度初始化给lineHeight、lineWidth
                lineWidth = childWidth+verticalSpace;//从新赋值lineWidth
                lineHeight = childHeight;
                tempLine=new Line();//产生下一行
                tempLine.addView(child);//把这个控件添加到下一行,但是最后这一行还没有加入到list,在最后一个的时候得判断要不要添加
            }
        }
        //最后一行是不会超出width范围的，即mLines可能没有把最后一行加进去，所以要单独处理，要判断有没有加过
        totalWidth = Math.max(lineWidth, totalWidth);
        if(getChildCount()>0){
            totalWidth-=verticalSpace;//因为每行最后都多了一个verticalSpace
        }
        if(mLines.size()<tempMaxLine&&tempLine.getViewCount()>0&&!mLines.contains(tempLine)){
            mLines.add(tempLine);
        }
        final int linesCount = mLines.size();
        // 加上所有行的高度
        if(linesCount!=0){
            for (int i = 0; i < linesCount; i++) {
                totalHeight += mLines.get(i).mHeight;
            }
            totalHeight+=(linesCount-1)*horizontalSpace;
        }
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : totalWidth + getPaddingLeft() + getPaddingRight(),
                //不是确定的记得加pading值
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : totalHeight + getPaddingTop() + getPaddingBottom());
    }



    //布局
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
            int top = getPaddingTop();//当前坐标的top坐标
            int left = getPaddingLeft();//left坐标
         for(int i=0;i<mLines.size();i++)
          {
              Line line = mLines.get(i);
              line.layoutView(left,top);
              top += line.mHeight + horizontalSpace;//为下一行的top赋值
          }
            //ViewGroup控件宽度
//            int width = getMeasuredWidth();
//            int remainWidth=width - getPaddingLeft() - getPaddingRight();
//            int lineWidth = 0;//累加当前行的行宽
//            int lineHeight = 0;//当前行的行高
//        int childCount = getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View child = getChildAt(i);
//            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
//            int childWidth=lp.leftMargin+child.getMeasuredWidth()+lp.rightMargin;
//            int childHeight = lp.topMargin+child.getMeasuredHeight()+lp.bottomMargin;
//            //viewGroup要扣去padding
//            int tempLineWidth=lineWidth + childWidth;
//            if(tempLineWidth<=remainWidth){
//                //不换行
//                lineHeight=Math.max(lineHeight,childHeight);//行高度取最大
//                lineWidth+=childWidth+verticalSpace; //行宽累加
//            }else{
//                //如果换行
//                top+=lineHeight+horizontalSpace;//位置累加
//                left=getPaddingLeft();//left从新赋值
//                lineHeight=childHeight;//行宽重新赋值
//                lineWidth=childWidth;//行高从新赋值
//            }
//            //计算childView的left,top,right,bottom
//            int lc=left+lp.leftMargin;
//            int tc=top+lp.topMargin;
//            int rc =lc + child.getMeasuredWidth();
//            int bc = tc + child.getMeasuredHeight();
//            child.layout(lc, tc, rc, bc);
//            //将left加上width后作为为下一子控件的起始点
//            left+=childWidth+(verticalSpace);
//        }
    }


    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return super.generateLayoutParams(p);
    }

    // addView(View child, int index)发现子类没有LayoutParams的时候调用这个方法
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    //addView(View child, int index, LayoutParams params)的时候发现子类没有LayoutParams的时候调用这个方法
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onChanged(boolean removeAndAdd) {
        //全部更新
        notifyItemViewAll(removeAndAdd);
    }

    @Override
    public void onChanged(int position) {
        notifyItemView(position);
    }

    class Line {
        int mWidth = 0;// 该行中所有的子View累加的宽度,不包含分割线
        int mHeight = 0;// 该行中所有的子View中高度的那个子View的高度,不包含分割线
        List<View> views = new ArrayList<View>();

        public void addView(View view) {// 往该行中添加一个
            views.add(view);
            MarginLayoutParams lp = (MarginLayoutParams) view.getLayoutParams();//强制成mar
            int childWidth = lp.leftMargin + view.getMeasuredWidth() + lp.rightMargin;//获取子View所占宽度
            int childHeight = lp.topMargin + view.getMeasuredHeight() + lp.bottomMargin;//获取子View所占高度
            mWidth += childWidth;
            mHeight = Math.max(mHeight, childHeight);//高度等于一行中最高的View
        }
        public void layoutView(int left, int top){
            int count = getViewCount();
            int parentWidth = getMeasuredWidth();
            //剩余的宽度，是除了View和间隙的剩余空间
            int surplusWidth = parentWidth - mWidth - verticalSpace * (count - 1);
            int tempVerticalSpace=verticalSpace;
            for(int i=0;i<count;i++)
              {
                  View child = views.get(i);
                  MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                  int childWidth=lp.leftMargin+child.getMeasuredWidth()+lp.rightMargin;
                  int childHeight = lp.topMargin+child.getMeasuredHeight()+lp.bottomMargin;
                  int tempTop=top;
                  if(i==0){
                      switch (align_model){
                          case START:
                            //默认，不需要做
                              break;
                          case CENTER:
                              left+=surplusWidth/2.0f;
                              break;
                          case END:
                              left+=surplusWidth;
                              break;
                          case TWO_SIDE:
                              //两边对齐
                              if(count>1){
                                  tempVerticalSpace=(int)((parentWidth - mWidth)*1.0f/(count - 1));
                              }
                              break;
                          default:
                              break;
                      }
                  }
                  //item在行里面的对齐方式
                  switch (align_item_model){
                      case START:
                          //默认，不需要做
                          break;
                      case CENTER:
                          tempTop+=(mHeight-childHeight)/2;
                          break;
                      case END:
                          tempTop+=(mHeight-childHeight);
                          break;
                      default:
                          break;
                  }
                  int lc=left+lp.leftMargin;
                  int tc=tempTop+lp.topMargin;
                  int rc =lc + child.getMeasuredWidth();
                  int bc = tc + child.getMeasuredHeight();
                  child.layout(lc, tc, rc, bc);
                  //将left加上width后作为为下一子控件的起始点,因为这是每一行的所以无需去计算下一个高度
                  left+=childWidth+(tempVerticalSpace);
              }
        }

        public int getViewCount() {
            return views.size();
        }
    }

    public int getMaxLine() {
        return maxLine;
    }

    public void setMaxLine(int maxLine) {
        this.maxLine = maxLine;
        //重新设置的时候
        requestLayout();
    }
    //获取当前是几行，受MaxLine显示
    public int getCurrentLineSize() {
        return mLines.size();
    }

    public void setAdapter(FlowAdapter flowAdapter){
        if(mFlowAdapter!=null){
            mFlowAdapter.unregisterDataSetObserver(this);
        }
        if (flowAdapter == null) {
            return;
        }
        mFlowAdapter=flowAdapter;
//        mDataSetObserver=new LqhFlowDataSetObserver() {
//            @Override
//            public void onChanged(boolean removeAndAdd) {
//                //全部更新
//                notifyItemViewAll(removeAndAdd);
//            }
//
//            @Override
//            public void onChanged(int position) {
//                notifyItemView(position);
//            }
//        };
        mFlowAdapter.registerDataSetObserver(this);
        notifyItemViewAll(true);
    }
    private void notifyItemViewAll(boolean removeAndAdd){
        if(removeAndAdd){
            removeAllViews();
            if(mFlowAdapter!=null){
                for (int i = 0; i < mFlowAdapter.getCount(); i++) {
                    View child = mFlowAdapter.onCreateView(this, i, mFlowAdapter.getList().get(i));
                    mFlowAdapter.onBindView(child,i,mFlowAdapter.getList().get(i));
                    bindClickView(child,i);
                    //addView会调用 requestLayout();//下面这个方法不会,但是最后要调用requestLayout();
                    addViewInLayout(child,i,child.getLayoutParams(),true);
                }
                    requestLayout();
            }
        }else{
            for (int i = 0; i < this.getChildCount(); i++) {
                View child = this.getChildAt(i);
                if(mFlowAdapter!=null&&mFlowAdapter.getCount()>i){
                    mFlowAdapter.onBindView(child,i,mFlowAdapter.getList().get(i));
                }
                bindClickView(child,i);
            }
            requestLayout();
        }
    }
    private void notifyItemView(int position){
        //局部刷新
        if(LqhFlowLayout.this.getChildCount()>position){
            View child = LqhFlowLayout.this.getChildAt(position);
            if(child!=null){
                if(mFlowAdapter!=null&&mFlowAdapter.getCount()>position){
                    mFlowAdapter.onBindView(child,position,mFlowAdapter.getList().get(position));
                }
                bindClickView(child,position);
            }
        }
    }

    private void bindClickView(View child,int i) {
        if(mFlowAdapter!=null){
            if(mFlowAdapter.getOnItemClickListener()!=null){
                int finalI = i;
                child.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFlowAdapter.getOnItemClickListener().onItemClick(mFlowAdapter,v,finalI);
                    }
                });
            }
            if(mFlowAdapter.getOnItemLongClickListener()!=null){
                int finalI = i;
                child.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return    mFlowAdapter.getOnItemLongClickListener().onItemLongClick(mFlowAdapter,v,finalI);
                    }
                });
            }
        }
    }

    //用来监听是否溢出
    public OnOverLineChangeListener mOnOverLineChangeListener;
    //用来监听展开与收缩的变化
    public OnExpandStateChangeListener mOnExpandStateChangeListener;

    public OnOverLineChangeListener getOnOverLineChangeListener() {
        return mOnOverLineChangeListener;
    }

    public void setOnOverLineChangeListener(OnOverLineChangeListener mOnOverLineChangeListener) {
        this.mOnOverLineChangeListener = mOnOverLineChangeListener;
    }

    public OnExpandStateChangeListener getOnExpandStateChangeListener() {
        return mOnExpandStateChangeListener;
    }

    public void setOnExpandStateChangeListener(OnExpandStateChangeListener mOnExpandStateChangeListener) {
        this.mOnExpandStateChangeListener = mOnExpandStateChangeListener;
    }

    public interface OnExpandStateChangeListener {
        void onExpandStateChanged(boolean isExpanded);
    }

    public interface OnOverLineChangeListener {
        void onOverLineChanged(boolean isOverLine);
    }

    public boolean isCollapsed() {
        return isCollapsed;
    }

    public void setCollapsed(boolean collapsed) {
        isCollapsed = collapsed;
        requestLayout();
    }
}
