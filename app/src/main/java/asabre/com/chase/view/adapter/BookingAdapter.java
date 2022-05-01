package asabre.com.chase.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import asabre.com.chase.R;
import asabre.com.chase.service.model.Booking;
import asabre.com.chase.view.callback.BookingCallback;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    private List<Booking> mBookingList;
    private BookingCallback mBookingCallback;

    public BookingAdapter(List<Booking> bookingList, BookingCallback bookingCallback){
        mBookingList = bookingList;
        mBookingCallback = bookingCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.browse_booked_rides, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(mBookingList == null || mBookingList.size() == 0){
            // when data is not available
        } else {
            Booking booking = mBookingList.get(position);
            String _id = booking.get_id();
            String customerId = booking.getCustomerId();
            String carType = booking.getCarType();
            String date = booking.getDate();
            String time = booking.getTime();
            String pickUp = booking.getPickUp();
            String dropOff = booking.getDropOff();
            String bookUpdate = booking.getBookUpdate();
            String driverId = booking.getDriverId();
            String driverName = booking.getDriverName();

            holder.bookDropOff.setText(dropOff);
            holder.bookDate.setText(date);
            holder.bookTime.setText(time);
            holder.bookPickUp.setText(pickUp);
            holder.bookUpdate.setText(bookUpdate);
            holder.itemView.setOnClickListener(bookingDetails(booking));
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookDropOff;
        TextView bookDate;
        TextView bookTime;
        TextView bookPickUp;
        TextView bookUpdate;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookDropOff = itemView.findViewById(R.id.bookDropOff);
            bookDate = itemView.findViewById(R.id.bookDate);
            bookTime = itemView.findViewById(R.id.bookTime);
            bookPickUp = itemView.findViewById(R.id.bookPickUp);
            bookUpdate = itemView.findViewById(R.id.bookUpdate);

        }
    }

    private View.OnClickListener bookingDetails(Booking booking){
        return view -> mBookingCallback.bookingCallback(booking);
    }
}
