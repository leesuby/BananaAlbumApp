package com.example.bananaalbum.viewmodels;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bananaalbum.R;
import com.example.bananaalbum.model.Album;
import com.example.bananaalbum.model.Picture;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Iterator;
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


    public void loadImages(String uid, String albumName) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("data").child(uid).child(albumName).child("Images").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot postSnapshot :snapshot.getChildren()) {
                    //TODO Long: truyền uri Image (postSnapshot.getValue()) vào adapter
                    //Log.e("ViewAlbum", ""+postSnapshot.getValue());
                    listPicture.add(new Picture(""+postSnapshot.getValue()) );

                }

                listPictureLiveData.setValue(listPicture);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}
