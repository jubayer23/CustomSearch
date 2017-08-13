package com.creative.customsearch;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

/**
 * Created by jubayer on 8/13/2017.
 */

public class mSearchView extends RelativeLayout {
    CompositeOnItemEventListener mCompositeOnItemEventListener;
    Context mContext;
    public mSearchView(Context context) {
        super(context);
    }

    public mSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public mSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public mSearchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context paramContext)
    {
        this.mContext = paramContext;
        LayoutInflater.from(paramContext).inflate(R.layout.test, this, true);
        this.mCompositeOnItemEventListener = new CompositeOnItemEventListener();
        initializeTextView();
    }

    private void initializeTextView()
    {
        ProgressBar localProgressBar = (ProgressBar)findViewById(R.id.loading_indicator);
        final DelayAutoCompleteTextView localDelayAutoCompleteTextView = (DelayAutoCompleteTextView)findViewById(R.id.menu_search);
        localDelayAutoCompleteTextView.setThreshold(3);
        localDelayAutoCompleteTextView.setAdapter(new BookAutoCompleteAdapter(this.mContext));
        localDelayAutoCompleteTextView.setLoadingIndicator(localProgressBar);
        localDelayAutoCompleteTextView.setOnItemClickListener(this.mCompositeOnItemEventListener);
        this.mCompositeOnItemEventListener.addOnClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
            {
               // paramAnonymousAdapterView = (SuggestionMovie)paramAnonymousAdapterView.getItemAtPosition(paramAnonymousInt);
              //  localDelayAutoCompleteTextView.setText(paramAnonymousAdapterView.b());
            }
        });
    }

    public CompositeOnItemEventListener getCompositeOnItemEventListener()
    {
        return this.mCompositeOnItemEventListener;
    }

    public AutoCompleteTextView getTextView()
    {
        return (AutoCompleteTextView)findViewById(R.id.menu_search);
    }
}
