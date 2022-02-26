package com.geektech.lesson2.ui.posts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geektech.lesson2.App;
import com.geektech.lesson2.R;
import com.geektech.lesson2.data.models.Post;
import com.geektech.lesson2.databinding.FragmentPostsBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostsFragment extends Fragment {

    private FragmentPostsBinding binding;
    private PostAdapter adapter;
    private NavHostFragment navHostFragment;
    private NavController controller;
    public PostsFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new PostAdapter();
        navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        controller = navHostFragment.getNavController();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPostsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recycler.setAdapter(adapter);
        App.api.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null){
                    adapter.setPosts(response.body());
                }else {
                    Log.e("TAG", "onResponseError:" + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e("TAG", "onFailure:" + t.getLocalizedMessage());
            }
        });
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.navigate(R.id.action_postsFragment_to_fopmFragment);
            }
        });
        adapter.setOnItemClick(new PostAdapter.onItemClick() {
            @Override
            public void onClick(Post post) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("key",post);
                controller.navigate(R.id.fopmFragment,bundle);
            }
        });

    }
}