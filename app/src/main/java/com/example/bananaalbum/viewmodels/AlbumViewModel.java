package com.example.bananaalbum.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.bananaalbum.model.Album;


import java.util.ArrayList;
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

        listAlbum.add(new Album("hihi"));
        listAlbum.add(new Album("hoho"));
        listAlbum.add(new Album("huhu"));

        listAlbumLiveData.setValue(listAlbum);
    }

    public MutableLiveData<List<Album>> getListAlbumLiveData() {
        return listAlbumLiveData;
    }

    public void setListAlbumLiveData(ArrayList<Album> list) {
        listAlbumLiveData.setValue(list);
    }
    public void addAlbum(Album al) {
        listAlbum.add(al);
        listAlbumLiveData.setValue(listAlbum);
    }
}

