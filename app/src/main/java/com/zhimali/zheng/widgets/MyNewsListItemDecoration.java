package com.zhimali.zheng.widgets;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Zheng on 2018/4/27.
 */

public class MyNewsListItemDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private int mLinePadding;
    public MyNewsListItemDecoration(int linePadding) {
        mPaint= new Paint();
        mPaint.setAntiAlias(true);
        mLinePadding= linePadding;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view)!= 0)
            outRect.top= 2;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        super.onDraw(c, parent, state);
        int childCount= parent.getChildCount();
        for (int i= 0; i< childCount; i++){
            View view= parent.getChildAt(i);
            int position= parent.getChildAdapterPosition(view);
            if (position== 0) continue;
            mPaint.setColor(Color.WHITE);
            c.drawRect(
                    view.getLeft(),
                    view.getTop()- 2,
                    view.getRight(),
                    view.getTop(),
                    mPaint);
            mPaint.setColor(Color.rgb(239,239,244));
            c.drawRect(
                    view.getLeft() + mLinePadding,
                    view.getTop()- 2,
                    view.getRight() - mLinePadding,
                    view.getTop(),
                    mPaint);
        }
    }
}
