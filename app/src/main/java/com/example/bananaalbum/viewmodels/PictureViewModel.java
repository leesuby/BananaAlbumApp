package com.example.bananaalbum.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bananaalbum.R;
import com.example.bananaalbum.model.Picture;

import java.util.ArrayList;
import java.util.List;

public class PictureViewModel extends ViewModel {
    private final MutableLiveData<List<Picture>> listPictureLiveData;
    private List<Picture> listPicture;
    private boolean isEditMode = false;
    private String albumname;

    String[] name = {"1", "2", "3", "4", "5"};
    int[] picture = {R.drawable.test_ava, R.drawable.test_ava2, R.drawable.test_ava3, R.drawable.test_ava4, R.drawable.app_name2};

    public PictureViewModel() {
        this.listPictureLiveData = new MutableLiveData<>();
        initData();
    }


    public void initData(){
        this.listPicture = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            listPicture.add(new Picture(picture[i % 5]));
        }

        listPictureLiveData.setValue(listPicture);
    }

    public MutableLiveData<List<Picture>> getListPictureLiveData() {
        return listPictureLiveData;
    }

    public void setListPictureLiveData(ArrayList<Picture> list){
        listPictureLiveData.setValue(list);
    }

    public void addPicture(Picture picture){
        listPicture.add(picture);
        listPictureLiveData.setValue(listPicture);
    }

    public void editMode(boolean mode){
        for(int i=0;i<listPicture.size();i++){
            listPicture.get(i).setEditMode(mode);
        }
        listPictureLiveData.setValue(listPicture);
    }

    public boolean IsDeletePicture(){
        int i,flag;
        for(i=0;i<listPicture.size();i++){
            if(listPicture.get(i).isChoosen())
            {
                flag=1;
                return true;
            }
        }
        return false;
    }

    public void deletePicture(){
        int numRemove=0;
        int i;
        for(i = 0; i<listPicture.size(); i++){
            if(listPicture.get(i).isChoosen())
            {
                listPicture.remove(i);
                i--;
            }
        }
        listPictureLiveData.setValue(listPicture);

    }


}
