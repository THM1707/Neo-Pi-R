package thm.com.gr2.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import thm.com.gr2.R;
import thm.com.gr2.model.Quiz;
import thm.com.gr2.util.Constants;

public class QuizFragment extends Fragment implements View.OnClickListener {

    private SharedPreferences mSharedPreferences;
    private Quiz mQuiz;
    private int mNo;
    private OnAnswerClickListener mOnAnswerClickListener;

    public static QuizFragment newInstance(Quiz quiz, int no) {
        Bundle args = new Bundle();
        args.putInt(Constants.FRAGMENT_BUNDLE_NO, no);
        args.putSerializable(Constants.FRAGMENT_BUNDLE_QUIZ, quiz);
        QuizFragment fragment = new QuizFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnAnswerClickListener = (OnAnswerClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(((Activity) context).getLocalClassName()
                    + " must implement OnButtonClickListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuiz = getArguments() != null ? (Quiz) getArguments().getSerializable(
                Constants.FRAGMENT_BUNDLE_QUIZ) : null;
        mNo = getArguments() != null ? getArguments().getInt(Constants.FRAGMENT_BUNDLE_NO) : -1;
        mSharedPreferences =
                getActivity().getSharedPreferences(Constants.PREF_NAME_POINT, Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_quiz, container, false);
        rootView.findViewById(R.id.bt_answer_0).setOnClickListener(this);
        rootView.findViewById(R.id.bt_answer_1).setOnClickListener(this);
        rootView.findViewById(R.id.bt_answer_2).setOnClickListener(this);
        rootView.findViewById(R.id.bt_answer_3).setOnClickListener(this);
        rootView.findViewById(R.id.bt_answer_4).setOnClickListener(this);
        TextView textProgress = rootView.findViewById(R.id.tv_progress);
        String progress = "" + (mNo + 1) + "/60";
        textProgress.setText(progress);
        TextView textQuiz = rootView.findViewById(R.id.tv_quiz);
        textQuiz.setText(mQuiz.getContent());
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_answer_0:
                mOnAnswerClickListener.onAnswerClicked(mQuiz.getQuizType(),
                        getPoint(mQuiz.getMode(), 0), mNo);
                break;
            case R.id.bt_answer_1:
                mOnAnswerClickListener.onAnswerClicked(mQuiz.getQuizType(),
                        getPoint(mQuiz.getMode(), 1), mNo);

                break;
            case R.id.bt_answer_2:
                mOnAnswerClickListener.onAnswerClicked(mQuiz.getQuizType(),
                        getPoint(mQuiz.getMode(), 2), mNo);
                break;
            case R.id.bt_answer_3:
                mOnAnswerClickListener.onAnswerClicked(mQuiz.getQuizType(),
                        getPoint(mQuiz.getMode(), 3), mNo);
                break;
            case R.id.bt_answer_4:
                mOnAnswerClickListener.onAnswerClicked(mQuiz.getQuizType(),
                        getPoint(mQuiz.getMode(), 4), mNo);
                break;
            default:
                break;
        }
    }

    private int getPoint(int mode, int answerNo) {
        switch (answerNo) {
            case 0:
                return mode == 0 ? 0 : 4;
            case 1:
                return mode == 0 ? 1 : 3;
            case 2:
                return 2;
            case 3:
                return mode == 0 ? 3 : 1;
            case 4:
                return mode == 0 ? 4 : 0;
            default:
                return -1;
        }
    }

    public interface OnAnswerClickListener {
        void onAnswerClicked(String quizType, int point, int index);
    }
}
