package com.example.bananaalbum.viewmodels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bananaalbum.model.Album;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Post implements Serializable {
    private HashMap<String,Album> albums;

    public HashMap<String, Album> getAlbums() {
        return albums;
    }

    public void setAlbums(HashMap<String, Album> albums) {
        this.albums = albums;
    }

    public Post(HashMap<String,Album> Albums) {
        albums=Albums;
    }
    public Post(){
         albums=new HashMap<String, Album>() {
             @Override
             public int size() {
                 return 0;
             }

             @Override
             public boolean isEmpty() {
                 return false;
             }

             @Override
             public boolean containsKey(@Nullable Object o) {
                 return false;
             }

             @Override
             public boolean containsValue(@Nullable Object o) {
                 return false;
             }

             @Nullable
             @Override
             public Album get(@Nullable Object o) {
                 return null;
             }

             @Nullable
             @Override
             public Album put(String s, Album album) {
                 return null;
             }

             @Nullable
             @Override
             public Album remove(@Nullable Object o) {
                 return null;
             }

             @Override
             public void putAll(@NonNull Map<? extends String, ? extends Album> map) {

             }

             @Override
             public void clear() {

             }

             @NonNull
             @Override
             public Set<String> keySet() {
                 return null;
             }

             @NonNull
             @Override
             public Collection<Album> values() {
                 return null;
             }

             @NonNull
             @Override
             public Set<Entry<String, Album>> entrySet() {
                 return null;
             }
         };
     }
}
