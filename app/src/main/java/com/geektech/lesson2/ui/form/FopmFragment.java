package com.geektech.lesson2.ui.form;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geektech.lesson2.App;
import com.geektech.lesson2.R;
import com.geektech.lesson2.data.models.Post;
import com.geektech.lesson2.data.remote.RetrofitClient;
import com.geektech.lesson2.databinding.FragmentFopmBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class FopmFragment extends Fragment {

    private FragmentFopmBinding binding;
    private final int USER_ID = 0;
    private final int GROUP_ID = 39;
    private int id;


    public FopmFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFopmBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            Post post;
            post = (Post) getArguments().getSerializable("key");
            binding.etContent.setText(post.getContent());
            binding.etTitle.setText(post.getTitle());
        }

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitClient.provieApi().updatePost(String.valueOf(id+""),new Post(
                        binding.etContent.getText().toString(),null,
                        binding.etTitle.getText().toString(),null

                )).enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        open();
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {

                    }
                });
            }
        });

        binding.btnSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = binding.etTitle.getText().toString();
                String content = binding.etContent.getText().toString();
                Post post = new Post(content,null,title,null);

                App.api.createPost(post).enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        if (response.isSuccessful() ){
                            requireActivity().onBackPressed();
                        }
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {

                    }
                });
            }
        });

    }

    private void open(){
        NavController navController = Navigation.findNavController
                (requireActivity(),R.id.nav_host_fragment);
        navController.navigate(R.id.postsFragment);
    }
}