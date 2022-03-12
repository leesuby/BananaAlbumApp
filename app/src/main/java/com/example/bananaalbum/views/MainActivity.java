package com.example.bananaalbum.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.bananaalbum.R;
import com.example.bananaalbum.adapters.TestAdapter;
import com.example.bananaalbum.model.Test;
import com.example.bananaalbum.viewmodels.TestViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvTest;
    private Button btnAddUser;


    private TestAdapter testAdapter;
    private TestViewModel testViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Remove action bar
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        rcvTest=findViewById(R.id.rcv_test);
        btnAddUser=findViewById(R.id.btnAdd);

        // set layout for RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvTest.setLayoutManager(linearLayoutManager);

        // set ViewModel with LiveData
        testViewModel = new ViewModelProvider(this).get(TestViewModel.class);
        testViewModel.getListTestLiveData().observe(this, new Observer<List<Test>>() {
            @Override
            public void onChanged(List<Test> tests) {
                //set Adapter for RecyclerView
                testAdapter= new TestAdapter(tests);
                rcvTest.setAdapter(testAdapter);

            }
        });

        //method for button add User
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Test t = new Test(R.drawable.ic_launcher_background,"Long","Dep trai lam");
                testViewModel.addTest(t);
            }
        });
    }
}