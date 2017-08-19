package com.creative.customsearch;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private MSearchView mSearchView;

    Boolean toolbarHomeButtonAnimating = false;

    Menu menu;

    Toolbar toolbar;

    DrawerLayout drawer;
    ActionBarDrawerToggle mDrawerToggle;

    //LinearLayout searchContainer;
    FrameLayout searchContainer;
    //EditText toolbarSearchView;
    //ImageView searchClearButton;

     DelayAutoCompleteTextView bookTitle2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initDrawer();

        final DelayAutoCompleteTextView bookTitle = (DelayAutoCompleteTextView) findViewById(R.id.et_book_title);
        bookTitle.setThreshold(3);
        bookTitle.setAdapter(new BookAutoCompleteAdapter(this)); // 'this' is Activity instance
        bookTitle.setLoadingIndicator(
                (android.widget.ProgressBar) findViewById(R.id.pb_loading_indicator));
        bookTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Book book = (Book) adapterView.getItemAtPosition(position);
                bookTitle.setText(book.getTitle());
            }
        });


        searchContainer = (FrameLayout) findViewById(R.id.search_container);
        bookTitle2 = (DelayAutoCompleteTextView) findViewById(R.id.et_book_title2);
        bookTitle2.setThreshold(3);
        bookTitle2.setAdapter(new BookAutoCompleteAdapter(this)); // 'this' is Activity instance
        bookTitle2.setLoadingIndicator(
                (android.widget.ProgressBar) findViewById(R.id.pb_loading_indicator2));
        bookTitle2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Book book = (Book) adapterView.getItemAtPosition(position);
                bookTitle2.setText(book.getTitle());
            }
        });
       // toolbarSearchView = (EditText) findViewById(R.id.search_view);
        //searchClearButton = (ImageView) findViewById(R.id.search_clear);


        // Clear search text when clear button is tapped
        //searchClearButton.setOnClickListener(new View.OnClickListener() {
        //    @Override
         //   public void onClick(View v) {
           //     bookTitle2.setText("");
         //   }
        //});

        // Hide the search view
        searchContainer.setVisibility(View.GONE);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // toolbarHomeButtonAnimating is a boolean that is initialized as false. It's used to stop the user pressing the home button while it is animating and breaking things.
                if (!toolbarHomeButtonAnimating) {

                }

                if (drawer.isDrawerOpen(findViewById(R.id.nav_view)))
                    drawer.closeDrawer(findViewById(R.id.nav_view));
                else
                    drawer.openDrawer(findViewById(R.id.nav_view));
            }
        });


        //searchClearButton.setOnClickListener(new View.OnClickListener() {
         //   @Override
         //   public void onClick(View v) {
         //       displaySearchView(false);
         //   }
        //});


    }

    public void displaySearchView(boolean visible) {
        if (visible) {
            // Stops user from being able to open drawer while searching
            // mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

            // Hide search button, display EditText
            menu.findItem(R.id.action_search).setVisible(false);
            searchContainer.setVisibility(View.VISIBLE);

            // Animate the home icon to the back arrow
            toggleActionBarIcon(ActionDrawableState.ARROW, mDrawerToggle, true);

            // Shift focus to the search EditText
            bookTitle2.requestFocus();

            // Pop up the soft keyboard
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    bookTitle2.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
                    bookTitle2.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
                }
            }, 200);
        } else {
            // Allows user to open drawer again
            //mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            // Hide the EditText and put the search button back on the Toolbar.
            // This sometimes fails when it isn't postDelayed(), don't know why.
            bookTitle2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bookTitle2.setText("");
                    searchContainer.setVisibility(View.GONE);
                    menu.findItem(R.id.action_search).setVisible(true);
                }
            }, 200);

            // Turn the home button back into a drawer icon
            toggleActionBarIcon(ActionDrawableState.BURGER, mDrawerToggle, true);

            // Hide the keyboard because the search box has been hidden
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(bookTitle2.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private enum ActionDrawableState {
        BURGER, ARROW
    }

    /**
     * Modified version of this, https://stackoverflow.com/a/26836272/1692770<br>
     * I flipped the start offset around for the animations because it seemed like it was the wrong way around to me.<br>
     * I also added a listener to the animation so I can find out when the home button has finished rotating.
     */
    private void toggleActionBarIcon(final ActionDrawableState state, final ActionBarDrawerToggle toggle, boolean animate) {
        if (animate) {
            float start = state == ActionDrawableState.BURGER ? 1.0f : 0f;
            float end = Math.abs(start - 1);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                ValueAnimator offsetAnimator = ValueAnimator.ofFloat(start, end);
                offsetAnimator.setDuration(300);
                offsetAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                offsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float offset = (Float) animation.getAnimatedValue();
                        toggle.onDrawerSlide(null, offset);
                    }
                });
                offsetAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        toolbarHomeButtonAnimating = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                toolbarHomeButtonAnimating = true;
                offsetAnimator.start();
            }
        } else {
            if (state == ActionDrawableState.BURGER) {
                toggle.onDrawerClosed(null);
            } else {
                toggle.onDrawerOpened(null);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            displaySearchView(true);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initDrawer() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
}
