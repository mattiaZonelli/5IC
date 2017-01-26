package com.quiz;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SummaryQuestion.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SummaryQuestion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SummaryQuestion extends android.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TITLE = "TITLE";
    private static final String ANSWER1 = "ANSWER1";
    private static final String ANSWER2 = "ANSWER2";
    private static final String ANSWER3 = "ANSWER3";
    private static final String ANSWER4 = "ANSWER4";
    private static final String GIVEN_ANSWER = "GIVEN_ANSWER";
    private static final String CORRECT_ANSWER = "CORRECT_ANSWER";


    // TODO: Rename and change types of parameters
    private String title;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String givenAnswer;
    private String correctAnswer;
    private View view;
    private OnFragmentInteractionListener mListener;

    public SummaryQuestion() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title   Parameter 1.
     * @param answer1 Parameter 2.
     * @return A new instance of fragment SummaryQuestion.
     */
    // TODO: Rename and change types and number of parameters
    public static SummaryQuestion newInstance(String title, String answer1, String answer2, String answer3, String answer4, String correctAnswer, String givenAnswer) {
        SummaryQuestion fragment = new SummaryQuestion();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(ANSWER1, answer1);
        args.putString(ANSWER2, answer2);
        args.putString(ANSWER3, answer3);
        args.putString(ANSWER4, answer4);
        args.putString(CORRECT_ANSWER, correctAnswer);
        args.putString(GIVEN_ANSWER, givenAnswer);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("ON_CREATE", "ON_CREATE");
        if (getArguments() != null) {
            title = getArguments().getString(TITLE);
            answer1 = getArguments().getString(ANSWER1);
            answer2 = getArguments().getString(ANSWER2);
            answer3 = getArguments().getString(ANSWER3);
            answer4 = getArguments().getString(ANSWER4);
            givenAnswer = getArguments().getString(GIVEN_ANSWER);
            correctAnswer = getArguments().getString(CORRECT_ANSWER);
        }
    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.fragment_summary_question, container, false);

        ((TextView) getView().findViewById(R.id.question)).setText(title);

        ((RadioButton) getView().findViewById(R.id.answer1)).setText(answer1);

        ((RadioButton) getView().findViewById(R.id.answer2)).setText(answer2);

        ((RadioButton) getView().findViewById(R.id.answer3)).setText(answer3);

        ((RadioButton) getView().findViewById(R.id.answer4)).setText(answer4);

        System.out.println("GIVEN ANSWER: " + givenAnswer);
        System.out.println("CORRECT ANSWER: " + correctAnswer);
        if (givenAnswer == null || givenAnswer.equals(" ")) {
            setNotAnsweredColor(Color.GRAY);
        } else if (!correctAnswer.equals(givenAnswer)) {
            setAnswerColor(givenAnswer, Color.RED);
        }

        setAnswerColor(correctAnswer, Color.GREEN);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v("ATTACH", "ON_ATTACH");
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void setParam(int index, String value) {
        Log.v("MESSAGE", getView() == null ? "NULL" : getView().toString());
        switch (index) {
            case 1:
                ((TextView) getView().findViewById(R.id.question)).setText(value);
                break;
            case 2:
                ((RadioButton) getView().findViewById(R.id.answer1)).setText(value);
                break;
            case 3:
                ((RadioButton) getView().findViewById(R.id.answer2)).setText(value);
                break;
            case 4:
                ((RadioButton) getView().findViewById(R.id.answer3)).setText(value);
                break;
            case 5:
                ((RadioButton) getView().findViewById(R.id.answer4)).setText(value);
                break;

        }
    }

    public void setNotAnsweredColor(int color) {
        ((TextView) getView().findViewById(R.id.question)).setTextColor(color);
        for (int i = 2; i < 6; i++) {
            RadioButton radioButton = getRadioButtonByIndex(i);
            radioButton.setTextColor(color);
        }

    }

    public void setAnswerColor(String answer, int color) {
        boolean found = false;
        for (int i = 2; i < 6 && !found; i++) {
            RadioButton radioButton = getRadioButtonByIndex(i);
            if (radioButton.getText().equals(answer)) {
                radioButton.setTextColor(color);
                found = true;
            }
        }
    }

    private RadioButton getRadioButtonByIndex(int index) {
        RadioButton radioButton = null;
        switch (index) {
            case 2:
                radioButton = ((RadioButton) getView().findViewById(R.id.answer1));
                break;
            case 3:
                radioButton = ((RadioButton) getView().findViewById(R.id.answer2));
                break;
            case 4:
                radioButton = ((RadioButton) getView().findViewById(R.id.answer3));
                break;
            case 5:
                radioButton = ((RadioButton) getView().findViewById(R.id.answer4));
                break;
        }
        return radioButton;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
