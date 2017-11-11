package com.yidu.sevensecondmall.views.widget.itemdecorator;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
  private int space;
  private int top;

  public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

  public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

  public SpaceItemDecoration(int space) {
    this.space = space;
  }

  public SpaceItemDecoration(int space, int top){
    this.space = space;
    this.top = top;
  }

  private int getOrientation(RecyclerView parent) {
    LinearLayoutManager layoutManager;
    try {
      layoutManager = (LinearLayoutManager) parent.getLayoutManager();
    } catch (ClassCastException e) {
      throw new IllegalStateException("DividerDecoration can only be used with a " +
              "LinearLayoutManager.", e);
    }
    return layoutManager.getOrientation();
  }

  @Override
  public void getItemOffsets(Rect outRect, View view,
                             RecyclerView parent, RecyclerView.State state) {
   // outRect.left = space;
   // outRect.right = space;

    int orientation = getOrientation(parent);
    if(orientation == VERTICAL_LIST) {
      outRect.bottom = space;
    }else{
      outRect.right = space;
    }
    //  outRect.top = space;
    // Add top margin only for the first item to avoid double space between items
    if(parent.getChildLayoutPosition(view) == 0) {
      if(top>0) {
        if(orientation == VERTICAL_LIST) {
          outRect.top = top;
        }else{
          outRect.left = top;
        }
      }

    }
  }
}