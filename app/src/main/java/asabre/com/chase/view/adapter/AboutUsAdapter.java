package asabre.com.chase.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import asabre.com.chase.R;
import asabre.com.chase.service.model.About;

public class AboutUsAdapter extends RecyclerView.Adapter<AboutUsAdapter.ViewHolder> {
    private List<About> mAboutList;

    public AboutUsAdapter(List<About> aboutList) {
        mAboutList = aboutList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.browse_about_us, parent, false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(mAboutList == null || mAboutList.size() == 0){
            // when data is not available
        } else {
            About about = mAboutList.get(position);
            holder.aboutMsg.setText(about.getMsg());
            holder.aboutTitle.setText(about.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mAboutList != null && mAboutList.size() != 0 ? mAboutList.size() : 0;
    }

    public void loadNewData(List<About> list){
        mAboutList = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView aboutMsg;
        TextView aboutTitle;
        ViewHolder(View view){
            super(view);
            this.aboutMsg = view.findViewById(R.id.about_msg);
            this.aboutTitle = view.findViewById(R.id.about_title);
        }
    }


}
