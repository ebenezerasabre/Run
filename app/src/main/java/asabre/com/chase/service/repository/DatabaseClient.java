package asabre.com.chase.service.repository;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {
    private Context mContext;
    private static DatabaseClient mInstance;

    // our app database object
    private AppDatabase mAppDatabase;

    private DatabaseClient(Context context){
        mContext = context;
        // creating the app database with Room database builder
        // Chase is the name of the database
        mAppDatabase = Room.databaseBuilder(mContext, AppDatabase.class, "Chase").build();
    }

    public static synchronized DatabaseClient getInstance(Context context){
        if(mInstance == null){
            mInstance = new DatabaseClient(context);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase(){
        return mAppDatabase;
    }

}
