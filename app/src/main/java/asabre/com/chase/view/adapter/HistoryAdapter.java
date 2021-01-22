package asabre.com.chase.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import asabre.com.chase.R;
import asabre.com.chase.service.model.History;
import asabre.com.chase.view.callback.HistoryCallback;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<History> mHistoryList;
    private HistoryCallback mHistoryCallback;

    public HistoryAdapter(List<History> historyList, HistoryCallback historyCallback) {
        mHistoryList = historyList;
        mHistoryCallback = historyCallback;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.browse_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(mHistoryList == null || mHistoryList.size() == 0){
            // when historyList is empty
            // do something
        } else {
            History history = mHistoryList.get(position);
            // set texts
            holder.browseExitPoint.setText(history.getExitPoint().split("&")[1]);
            holder.browseExitCity.setText(history.getExitPoint().split("&")[0]);

            holder.itemView.setOnClickListener(historyDetails(history));
        }
    }

    @Override
    public int getItemCount() {
        return mHistoryList != null && mHistoryList.size() != 0 ? mHistoryList.size() : 0;
    }

    public void loadNewData(List<History> list){
        mHistoryList = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView browseExitPoint;
        TextView browseExitCity;
        ViewHolder(View view){
            super(view);
            this.browseExitPoint = view.findViewById(R.id.browseExitPoint);
            this.browseExitCity = view.findViewById(R.id.browseExitCity);
        }
    }

    private View.OnClickListener historyDetails(History history){
        return view -> mHistoryCallback.historyDetails(history);
    }


}
