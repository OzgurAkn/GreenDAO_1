package com.example.ozgur.greendao_0;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.daoexample.DaoMaster;
import de.greenrobot.daoexample.DaoSession;
import de.greenrobot.daoexample.User;
import de.greenrobot.daoexample.UserDao;

public class UpdateUserActivity extends AppCompatActivity
{

    @Bind(R.id.edit_name)
    EditText name;
    @Bind(R.id.edit_age)
    EditText age;
    @Bind(R.id.edit_adress)
    EditText adress;
    @Bind(R.id.edit_zipcode)
    EditText zipcode;
    @Bind(R.id.edit_city)
    EditText city;

    private SQLiteDatabase database;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        ButterKnife.bind(this);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "users-db", null);
        this.database = helper.getWritableDatabase();
        daoMaster = new DaoMaster(this.database);
        daoSession = this.daoMaster.newSession();
        userDao = this.daoSession.getUserDao();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateUserActivity.this.saveUser();
                startActivity(new Intent(UpdateUserActivity.this, MainActivity.class));
            }
        });
    }

    private void saveUser() {
        this.database.execSQL("Delete from " + userDao.getTablename());

        User user = new User();
        user.setId(null);
        user.setName(name.getText().toString());
        user.setAge(Integer.parseInt(age.getText().toString()));
        user.setAddress(adress.getText().toString());
        user.setZipcode(Integer.parseInt(zipcode.getText().toString()));
        user.setCity(city.getText().toString());

        this.userDao.insert(user);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.database.close();
    }

}
