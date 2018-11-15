package thm.com.gr2.fragment;

import android.content.Context;
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

public class QuizFragment extends Fragment {

    private Quiz mQuiz;

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
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuiz = getArguments() != null ? (Quiz) getArguments().getSerializable(
                Constants.FRAGMENT_BUNDLE_QUIZ) : null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_quiz, container, false);
        TextView textQuiz = rootView.findViewById(R.id.tv_quiz);
        textQuiz.setText(mQuiz.getContent());
        return rootView;
    }
}
