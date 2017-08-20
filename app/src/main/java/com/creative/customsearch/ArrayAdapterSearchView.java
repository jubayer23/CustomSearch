package com.creative.customsearch;

import android.content.Context;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

/**
 * Created by jubayer on 8/20/2017.
 */

public class ArrayAdapterSearchView extends SearchView {

    private SearchView.SearchAutoComplete mSearchAutoComplete;
    private DelayAutoCompleteTextView delayAutoCompleteTextView;
    Context mContext;
    RelativeLayout search_parent;

    public ArrayAdapterSearchView(Context context) {
        super(context);
        initialize(context);
    }

    public ArrayAdapterSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public void initialize(Context paramContext) {
        this.mContext = paramContext;
        LayoutInflater.from(paramContext).inflate(R.layout.test, this, true);
        initializeTextView();
        //mSearchAutoComplete = (SearchAutoComplete) findViewById(android.support.v7.appcompat.R.id.search_src_text);
        //this.setAdapter(null);
        //this.setOnItemClickListener(null);
    }

    private void initializeTextView()
    {
        search_parent = (RelativeLayout)findViewById(R.id.search_parent);
        search_parent.setVisibility(GONE);
        ProgressBar localProgressBar = (ProgressBar)findViewById(R.id.loading_indicator);
        delayAutoCompleteTextView = (DelayAutoCompleteTextView)findViewById(R.id.menu_search);
        delayAutoCompleteTextView.setThreshold(3);
        delayAutoCompleteTextView.setAdapter(new BookAutoCompleteAdapter(this.mContext));
        delayAutoCompleteTextView.setLoadingIndicator(localProgressBar);
    }

    public void makeVisible(){
        search_parent.setVisibility(VISIBLE);
    }

    @Override
    public void setSuggestionsAdapter(CursorAdapter adapter) {
        // don't let anyone touch this
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mSearchAutoComplete.setOnItemClickListener(listener);
    }

    public void setAdapter(ArrayAdapter<?> adapter) {
        mSearchAutoComplete.setAdapter(adapter);
    }

    public void setText(String text) {
        mSearchAutoComplete.setText(text);
    }

    public void setSelection(int position){
        mSearchAutoComplete.setSelection(position);
    }

    public String getText(){
        return mSearchAutoComplete.getText().toString();
    }
}