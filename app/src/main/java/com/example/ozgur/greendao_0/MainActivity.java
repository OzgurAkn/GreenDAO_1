package com.example.ozgur.greendao_0;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.daoexample.DaoMaster;
import de.greenrobot.daoexample.DaoSession;
import de.greenrobot.daoexample.User;
import de.greenrobot.daoexample.UserDao;

public class MainActivity extends AppCompatActivity
{

    @Bind(R.id.textview_name)
    TextView name;
    @Bind(R.id.textview_age)
    TextView age;
    @Bind(R.id.textview_adress)
    TextView address;
    @Bind(R.id.textview_zipcode)
    TextView zipcode;
    @Bind(R.id.textview_city)
    TextView city;

    private SQLiteDatabase database;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "users-db", null);
        this.database = helper.getReadableDatabase();
        daoMaster = new DaoMaster(this.database);
        daoSession = this.daoMaster.newSession();
        userDao = this.daoSession.getUserDao();

        this.showDetailsUser();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MainActivity.this, UpdateUserActivity.class));
            }
        });
    }

    private void showDetailsUser()
    {

        Cursor cursor = this.database.query(this.userDao.getTablename(), this.userDao.getAllColumns(), null, null, null, null, null);

        if (cursor.moveToFirst())
        {
            String name = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NAME));
            int age = cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_AGE));
            String address = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_ADDRESS));
            int zipcode = cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_ZIPCODE));
            String city = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_CITY));

            User user = new User(null, name, age, address, zipcode, city);
            this.name.setText(Html.fromHtml("<b>Naam: </b>" + user.getName()));
            this.age.setText(Html.fromHtml("<b>Leeftijd: </b>" + user.getAge()));
            this.address.setText(Html.fromHtml("<b>Adres: </b>" + user.getAddress()));
            this.zipcode.setText(Html.fromHtml("<b>Postcode: </b>" + user.getZipcode()));
            this.city.setText(Html.fromHtml("<b>Gemeente: </b>" + user.getCity()));
        }
        else
        {
            this.name.setText("Nog geen gegevens ingevuld.");
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        this.database.close();
    }
}
