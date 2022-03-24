package com.example.bananaalbum.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bananaalbum.R;
import com.example.bananaalbum.model.Test;

import java.util.ArrayList;
import java.util.List;

public class TestViewModel extends ViewModel {

    //Live data
    private MutableLiveData<List<Test>> listTestLiveData;
    private List<Test> listTest;

    public TestViewModel(){
        listTestLiveData = new MutableLiveData<>();
        initData();
    }

    //Set data for LiveData
    private void initData() {
        listTest = new ArrayList<>();
        listTest.add(new Test(R.drawable.ic_baseline_favorite,"Long","Dep trai"));

        listTestLiveData.setValue(listTest);
    }

    public MutableLiveData<List<Test>> getListTestLiveData() {
        return listTestLiveData;
    }

    public void addTest(Test test){
        listTest.add(test);
        listTestLiveData.setValue(listTest);
    }
}
