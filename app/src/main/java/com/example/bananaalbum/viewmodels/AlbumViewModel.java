package com.example.bananaalbum.viewmodels;

import android.support.annotation.NonNull;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.bananaalbum.model.Album;
import com.example.bananaalbum.views.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class AlbumViewModel extends ViewModel {
    private final MutableLiveData<List<Album>> listAlbumLiveData;
    private List<Album> listAlbum;

    public AlbumViewModel() {
        listAlbumLiveData = new MutableLiveData<>();
        initData();
    }

    private void initData() {
        listAlbum = new ArrayList<>();

        listAlbumLiveData.setValue(listAlbum);
    }

    public MutableLiveData<List<Album>> getListAlbumLiveData() {
        return listAlbumLiveData;
    }
    public void loadData(String userID){

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("data").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listAlbum = new ArrayList<>();
                Iterable<DataSnapshot> snapshotIterator = snapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                while (iterator.hasNext()) {
                    DataSnapshot next = (DataSnapshot) iterator.next();
                    if(next.child("name").getValue()!=null &&next.child("date_created").getValue()!= null && next.child("location_created").getValue()!=null){
                        listAlbum.add(new Album(next.child("name").getValue().toString(),next.child("location_created").getValue().toString(),next.child("date_created").getValue().toString()));
                    }
                }
                listAlbumLiveData.setValue(listAlbum);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
    public void setListAlbumLiveData(ArrayList<Album> list) {
        listAlbumLiveData.setValue(list);
    }
    public void addAlbum(Album al) {
        listAlbum.add(al);
        listAlbumLiveData.setValue(listAlbum);
    }
}

