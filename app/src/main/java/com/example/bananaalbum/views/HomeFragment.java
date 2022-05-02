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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananaalbum.R;
import com.example.bananaalbum.adapters.AlbumAdapter;
import com.example.bananaalbum.model.Album;
import com.example.bananaalbum.viewmodels.AlbumViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView AlbumRecView;
    Button FavBtn, BinBtn;
    EditText SearchBar;
    AlbumViewModel albumViewModel;
    AlbumAdapter albumAdapter;
    FirebaseUser user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View homeFrag = inflater.inflate(R.layout.fragment_home, container, false);

        FavBtn = homeFrag.findViewById(R.id.btnFav);
        BinBtn = homeFrag.findViewById(R.id.btnBin);
        SearchBar = homeFrag.findViewById(R.id.search_field);
        AlbumRecView = homeFrag.findViewById(R.id.AlbumRecView);
        //firebase
        user =  FirebaseAuth.getInstance().getCurrentUser();

        albumViewModel = new ViewModelProvider(this.getActivity()).get(AlbumViewModel.class);
        //TODO mama: lấy album của người dùng về
        albumViewModel.loadData(user.getUid());

        albumViewModel.getListAlbumLiveData().observe(this.getActivity(), new Observer<List<Album>>() {
            @Override
            public void onChanged(List<Album> albums) {
                //set Adapter for RecyclerView
                albumAdapter = new AlbumAdapter(albums, HomeFragment.this.getActivity());
                AlbumRecView.setAdapter(albumAdapter);

            }
        });

        AlbumRecView.setLayoutManager(new GridLayoutManager(this.getActivity(), 2));
        return homeFrag;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (albumAdapter != null) {
            albumAdapter.release();
        }
    }
}

//        // set ViewModel with LiveData
//        testViewModel = new ViewModelProvider(this).get(TestViewModel.class);
//        testViewModel.getListTestLiveData().observe(this, new Observer<List<Test>>() {
//            @Override
//            public void onChanged(List<Test> tests) {
//                //set Adapter for RecyclerView
//                testAdapter= new TestAdapter(tests);
//                rcvTest.setAdapter(testAdapter);
//
//            }
//        });
//
//        //method for button add User
//        btnAddUser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Test t = new Test(R.drawable.ic_launcher_background,"Long","Dep trai lam");
//                testViewModel.addTest(t);
//            }
//        });
