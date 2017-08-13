package com.creative.customsearch;

import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jubayer on 8/13/2017.
 */

public class CompositeOnItemEventListener
        implements AdapterView.OnItemClickListener
{
    private CompositeOnItemEventListener instance;
    private List<AdapterView.OnItemClickListener> listeners = new ArrayList();

    public void addOnClickListener(AdapterView.OnItemClickListener paramOnItemClickListener)
    {
        this.listeners.add(paramOnItemClickListener);
    }

    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
    {
        Iterator localIterator = this.listeners.iterator();
        while (localIterator.hasNext()) {
            ((AdapterView.OnItemClickListener)localIterator.next()).onItemClick(paramAdapterView, paramView, paramInt, paramLong);
        }
    }
}