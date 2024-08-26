package com.eoutlet.Eoutlet.listener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationListener extends RecyclerView.OnScrollListener {


    public static String TAG = PaginationListener.class
            .getSimpleName();

    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 0;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private int startingPageIndex = 0;
    public static int current_page = 1;

    private GridLayoutManager mLinearLayoutManager;

    public PaginationListener(
            GridLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading
                && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached

            // Do something
            current_page++;

                onLoadMore(current_page);


            loading = true;
        }



    }




    public abstract void onLoadMore(int current_page);



    // Call whenever performing new searches
    public void resetState() {
        this.current_page = this.startingPageIndex;
        this.previousTotal = 0;
        this.loading = true;
/*
        visibleThreshold=0;
        visibleItemCount=0;
        firstVisibleItem=0;
        this.current_page=1;
        previousTotal=0;
        totalItemCount=0;*/

    }

    public void resetValues(){
        this.current_page=1;
        previousTotal = 0;
        loading = true;
        visibleThreshold = 1;
        firstVisibleItem = 0;
        visibleItemCount = 0;
        totalItemCount = 0;
    }


}
