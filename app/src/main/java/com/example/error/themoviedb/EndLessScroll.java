package com.example.error.themoviedb;
import android.content.Context;
import android.widget.AbsListView;
import com.example.error.themoviedb.service.ServiceHelper;

public class EndLessScroll implements AbsListView.OnScrollListener {
    private Context mContext;
    private int visibleItemCount;



    public EndLessScroll(Context context) {
        mContext = context;
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            this.visibleItemCount = visibleItemCount;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                if (view.getFirstVisiblePosition() + visibleItemCount >= view.getCount() ) {
                    ServiceHelper.getInstance().listViewNextPage(mContext);
                }
            }
    }
}
