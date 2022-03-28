package com.example.bananaalbum.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananaalbum.R;
import com.example.bananaalbum.adapters.AlbumAdapter;
import com.example.bananaalbum.model.Album;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView AlbumRecView;
    Button FavBtn,BinBtn;
    EditText SearchBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View homeFrag =  inflater.inflate(R.layout.fragment_home,container,false);

        FavBtn= homeFrag.findViewById(R.id.btnFav);
        BinBtn= homeFrag.findViewById(R.id.btnBin);
        SearchBar= homeFrag.findViewById(R.id.search_field);
        AlbumRecView= homeFrag.findViewById(R.id.AlbumRecView);


        ArrayList<Album> listAlbum = new ArrayList<>();

        listAlbum.add(new Album("hihi"));
        listAlbum.add(new Album("hoho"));
        listAlbum.add(new Album("huhu"));

        AlbumAdapter albumAdapter = new AlbumAdapter(listAlbum);

        AlbumRecView.setAdapter(albumAdapter);
        AlbumRecView.setLayoutManager(new GridLayoutManager(this.getActivity(),2));
        return homeFrag;
    }
}
