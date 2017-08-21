package com.creative.customsearch;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jubayer on 8/13/2017.
 */

public class BookAutoCompleteAdapter extends BaseAdapter implements Filterable {

    private static final int MAX_RESULTS = 10;
    private Context mContext;
    private List<Book> resultList = new ArrayList<Book>();

    public BookAutoCompleteAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Book getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.simple_dropdown_item_2line, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.text1)).setText(getItem(position).getTitle());
        ((TextView) convertView.findViewById(R.id.text2)).setText(getItem(position).getAuthor());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                Log.d("DEBUG","this is called 1");
                if (results != null && results.count > 0) {
                    resultList = (List<Book>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                Log.d("DEBUG","this is called 2");
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {


                    sendServerRequestToDeleteAttachment(constraint.toString());


                }
                return null;
            }
        };
    }

    /**
     * Returns a search result for the given book title.
     */
    private List<Book> findBooks() {
        // GoogleBooksProtocol is a wrapper for the Google Books API
        //GoogleBooksProtocol protocol = new GoogleBooksProtocol(context, MAX_RESULTS);
       // return protocol.findBooks(bookTitle);

        List<Book> myList = new ArrayList<>();

        myList.add(new Book("Daru chiner deep","HM"));
        myList.add(new Book("Daru chiner deep","HM"));
        myList.add(new Book("Daru chiner deep","HM"));

        return  myList;
    }

    public abstract interface AutoCompleteAdapterListener
    {
        public abstract void onFailure(Throwable paramThrowable);

        public abstract void onResponse(Book paramSuggestionMovies);
    }

    private AutoCompleteAdapterListener listener;
    public void registerListener(AutoCompleteAdapterListener paramAutoCompleteAdapterListener)
    {
        this.listener = paramAutoCompleteAdapterListener;
    }


    private void updateUi(List<Book> books){
        if (books != null && books.size() > 0) {
            resultList.addAll(books);
            notifyDataSetChanged();
        } else {
            notifyDataSetInvalidated();
        }
    }

    public void sendServerRequestToDeleteAttachment(String text) {


        String url = "https://yts.ag/api/v2/list_movies.json?query_term=" + text;
        // TODO Auto-generated method stub

        final StringRequest req = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("DEBUG_RES",response);

                        List<Book> books = findBooks();

                        updateUi(books);

                        // Assign the data to the FilterResults
                        listener.onResponse(new Book("avc","avc"));
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onFailure(error);
                Log.d("DEBUG","error");

            }
        });

        req.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // TODO Auto-generated method stub
        AppController.getInstance().addToRequestQueue(req);
    }

}