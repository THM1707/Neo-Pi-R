package thm.com.gr2.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import thm.com.gr2.R;
import thm.com.gr2.model.Explain;

public class ResultRecyclerAdapter extends RecyclerView.Adapter<ResultRecyclerAdapter.ViewHolder> {
    private List<Explain> mExplainList;

    public ResultRecyclerAdapter(List<Explain> explainList) {
        mExplainList = explainList;
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
        holder.mTextResultName.setText(mExplainList.get(position).getName());
        holder.mTextResultContent.setText(mExplainList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mExplainList == null ? 0 : mExplainList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextResultName;
        TextView mTextResultContent;
        public ViewHolder(View itemView) {
            super(itemView);
            mTextResultName = itemView.findViewById(R.id.tv_result_name);
            mTextResultContent = itemView.findViewById(R.id.tv_result_content);
        }
    }
}
