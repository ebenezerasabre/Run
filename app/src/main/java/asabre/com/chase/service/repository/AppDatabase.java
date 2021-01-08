package asabre.com.chase.service.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import asabre.com.chase.service.model.UserDao;
import asabre.com.chase.service.model.UserEntity;

@Database(entities = {UserEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao mUserDao();
}
