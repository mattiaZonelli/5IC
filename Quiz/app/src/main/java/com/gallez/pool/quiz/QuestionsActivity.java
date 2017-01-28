package com.gallez.pool.quiz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class QuestionsActivity extends AppCompatActivity {
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    int[] answers;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private static Object[]questions;

    public ViewPager getmViewPager() {
        return mViewPager;
    }

    public int[] getAnswers() {
        return answers;
    }

    public void setCurrentPage(int page, boolean smoothscroll){
        mViewPager.setCurrentItem(page,smoothscroll);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        answers = new int[10];
        setContentView(R.layout.activity_questions);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        questions = (Object[])getIntent().getExtras().get("questions");
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        Button next = (Button)findViewById(R.id.next);
        Button previous = (Button)findViewById(R.id.previous);
        Button finish = (Button)findViewById(R.id.finish);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mViewPager.getCurrentItem()<=9)
                setCurrentPage(mViewPager.getCurrentItem()+1,true);
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mViewPager.getCurrentItem()>=0)
                setCurrentPage(mViewPager.getCurrentItem()-1,true);
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putExtra("result",getAnswers());
                QuestionsActivity.this.setResult(RESULT_OK,i);
                QuestionsActivity.this.finish();
            }
        });
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();

            args.putSerializable("question",(Question)questions[sectionNumber-1]);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_questions, container, false);
            Question q = (Question)getArguments().get("question");
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            ((RadioButton)rootView.findViewById(R.id.first)).setText(q.getAnswers()[0]);
            ((RadioButton)rootView.findViewById(R.id.second)).setText(q.getAnswers()[1]);
            ((RadioButton)rootView.findViewById(R.id.three)).setText(q.getAnswers()[2]);
            ((RadioButton)rootView.findViewById(R.id.four)).setText(q.getAnswers()[3]);
            View.OnClickListener cl = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean checked = ((RadioButton) view).isChecked();


                    QuestionsActivity qa=  ((QuestionsActivity)getActivity());
                    ViewPager vp = qa.getmViewPager();
                    int [] answers = qa.getAnswers();
                    // Check which radio button was clicked
                    switch(view.getId()) {
                        case R.id.first:
                            if (checked)
                                answers[vp.getCurrentItem()] = 0;
                            break;
                        case R.id.second:
                            if (checked)
                                answers[vp.getCurrentItem()] = 1;
                            break;
                        case R.id.three:
                            if (checked)
                                answers[vp.getCurrentItem()] = 2;
                            break;
                        case R.id.four:
                            if (checked)
                                answers[vp.getCurrentItem()] = 3;
                            break;
                    }
                }
            };
            rootView.findViewById(R.id.first).setOnClickListener(cl);
            rootView.findViewById(R.id.second).setOnClickListener(cl);
            rootView.findViewById(R.id.three).setOnClickListener(cl);
            rootView.findViewById(R.id.four).setOnClickListener(cl);
            textView.setText(q.getQuestion());
            return rootView;
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public static final int COUNTPAGE =Question.MAX_QUESTION_PER_QUIZ;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {

            return COUNTPAGE;
        }

    }
}
