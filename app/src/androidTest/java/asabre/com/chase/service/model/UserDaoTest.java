package asabre.com.chase.service.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;
import asabre.com.chase.service.model.UserDao;
import asabre.com.chase.service.model.UserEntity;
import asabre.com.chase.service.repository.AppDatabase;
import asabre.com.chase.service.repository.DatabaseClient;

import static com.google.common.truth.Truth.assertThat;

@RunWith(JUnit4.class)
@SmallTest
public class UserDaoTest {

    // tell jUnit to execute functions in order

    private AppDatabase mAppDatabase;
    private UserDao mUserDao;


    @Before
    public void setUp(){
        mAppDatabase = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                AppDatabase.class
        ).allowMainThreadQueries().build();
        mUserDao = mAppDatabase.mUserDao();
    }

    @After
    public void tearDown(){
        mAppDatabase.close();
    }

    @Test
    public void insertUser(){
        UserEntity entity = new UserEntity("1", "Konadu", "Agyemang", "0011223344", "konaduagyemang@gmail.com", "user");
        mUserDao.insert(entity);
        List<UserEntity> list = mUserDao.getAll();
        assertThat(list).isNotEmpty();
    }

    @Test
    public void deleteUser(){
        UserEntity entity = new UserEntity("1", "Konadu", "Agyemang", "0011223344", "konaduagyemang@gmail.com", "user");
        mUserDao.insert(entity);

        UserEntity entity2 = new UserEntity("2", "Akosua", "Gyamasi", "0545231215", "gyamasi@gmail.com", "user");
        mUserDao.insert(entity2);

        mUserDao.delete(entity);
        List<UserEntity> entities = mUserDao.getAll();
        assertThat(entities).doesNotContain(entity);
    }

    @Test
    public void deleteAll(){
        UserEntity entity = new UserEntity("1", "Maame", "Twumwaah", "0011223344", "konaduagyemang@gmail.com", "user");
        mUserDao.insert(entity);

        UserEntity entity2 = new UserEntity("2", "Akosua", "Gyamasi", "0545231215", "gyamasi@gmail.com", "user");
        mUserDao.insert(entity2);

        mUserDao.deleteAll();
        List<UserEntity> entities = mUserDao.getAll();
        assertThat(entities).isEmpty();
    }



}

