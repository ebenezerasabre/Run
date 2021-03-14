package asabre.com.chase.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import asabre.com.chase.R;
import asabre.com.chase.service.model.RideRequest;
import asabre.com.chase.view.callback.RequestCallback;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    private List<RideRequest> mRideRequestList;
    private RequestCallback mRequestCallback;

    public RequestAdapter(List<RideRequest> rideRequestList, RequestCallback requestCallback) {
        mRideRequestList = rideRequestList;
        mRequestCallback = requestCallback;
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
        if(mRideRequestList == null || mRideRequestList.size() == 0){
            // when data is not available
        } else {
            RideRequest rideRequest = mRideRequestList.get(position);
            String city = rideRequest.getExitPoint().split("&")[0];
            String exit = rideRequest.getExitPoint().split("&")[1];
            String createdAt = rideRequest.getCreatedAt();
            String date = createdAt.substring(0, 10);
            String time = createdAt.substring(11, 19);
            String rideState = rideRequest.getRideState();

            holder.browseExitPoint.setText(exit);
            holder.browseExitCity.setText(city);
            holder.browseDate.setText(date);
            holder.browseTime.setText(time);
            holder.browseState.setText(rideState);
            holder.itemView.setOnClickListener(requestDetails(rideRequest));
        }
    }

    @Override
    public int getItemCount() {
        return mRideRequestList != null && mRideRequestList.size() != 0 ? mRideRequestList.size() : 0;
    }

    public void loadNewData(List<RideRequest> list){
        mRideRequestList = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView browseExitPoint;
        TextView browseExitCity;
        TextView browseDate;
        TextView browseTime;
        TextView browseState;
        ViewHolder(View view){
            super(view);
            this.browseExitPoint = view.findViewById(R.id.browseExitPoint);
            this.browseExitCity = view.findViewById(R.id.browseExitCity);
            this.browseDate = view.findViewById(R.id.browseDate);
            this.browseTime = view.findViewById(R.id.browseTime);
            this.browseState = view.findViewById(R.id.browseState);
        }
    }

    private View.OnClickListener requestDetails(RideRequest rideRequest){
        return view -> mRequestCallback.requestCallback(rideRequest);
    }


}
