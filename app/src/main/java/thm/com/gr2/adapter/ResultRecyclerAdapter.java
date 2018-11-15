package thm.com.gr2.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import thm.com.gr2.R;
import thm.com.gr2.activity.ExplainActivity;
import thm.com.gr2.model.Explain;

public class ResultRecyclerAdapter extends RecyclerView.Adapter<ResultRecyclerAdapter.ViewHolder> {
    private List<Explain> mExplainList;
    private Context mContext;

    public ResultRecyclerAdapter(Context context, List<Explain> explainList) {
        mExplainList = explainList;
        mContext = context;
    }

    public void setExplainList(List<Explain> explainList) {
        mExplainList = explainList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mCardItem.setOnClickListener(
                view -> {
                    Intent intent = new Intent(mContext, ExplainActivity.class);
                    intent.putExtra("name", mExplainList.get(position).getName());
                    intent.putExtra("content", mExplainList.get(position).getContent());
                    mContext.startActivity(intent);
                });
        holder.mTextResultName.setText(mExplainList.get(position).getName());
        holder.mTextRate.setText(mExplainList.get(position).getRate());
        switch (position) {
            case 0:
                holder.mImageLetter.setBackgroundResource(R.drawable.ic_letter_a);
                break;
            case 1:
                holder.mImageLetter.setBackgroundResource(R.drawable.ic_letter_c);
                break;
            case 2:
                holder.mImageLetter.setBackgroundResource(R.drawable.ic_letter_o);
                break;
            case 3:
                holder.mImageLetter.setBackgroundResource(R.drawable.ic_letter_n);
                break;
            case 4:
                holder.mImageLetter.setBackgroundResource(R.drawable.ic_letter_e);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mExplainList == null ? 0 : mExplainList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextResultName;
        TextView mTextRate;
        CardView mCardItem;
        ImageView mImageLetter;

        ViewHolder(View itemView) {
            super(itemView);
            mCardItem = itemView.findViewById(R.id.cv_result);
            mImageLetter = itemView.findViewById(R.id.iv_letter);
            mTextResultName = itemView.findViewById(R.id.tv_result_name);
            mTextRate = itemView.findViewById(R.id.tv_rate);
        }
    }
}
