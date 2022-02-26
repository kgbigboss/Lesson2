package com.geektech.lesson2.ui.posts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geektech.lesson2.data.models.Post;
import com.geektech.lesson2.data.remote.RetrofitClient;
import com.geektech.lesson2.databinding.ItemPostBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {


    private List<Post> posts = new ArrayList<>();

    private  onItemClick onItemClick;
    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding binding = ItemPostBinding.inflate(LayoutInflater.from
                        (parent.getContext()),parent,false);
        return new PostViewHolder(binding);
    }

    public void setOnItemClick(PostAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.onBind(posts.get(position));

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    protected class PostViewHolder extends RecyclerView.ViewHolder{

        private ItemPostBinding binding;

        public PostViewHolder(@NonNull ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(Post post) {

            HashMap<Integer,String> hashMap = new HashMap<>();
            hashMap.put(1,"Адилет");
            hashMap.put(2,"Мунара");
            hashMap.put(3,"Бермет");
            hashMap.put(4,"Максим");
            hashMap.put(5,"Султанкул");
            hashMap.put(6,"Актан");
            hashMap.put(7,"Арлен");
            hashMap.put(8,"Темирлан");
            hashMap.put(9,"Азим");
            hashMap.put(10,"Медер");

            ArrayList<String> arrayList = new ArrayList<>(hashMap.values());

            binding.tvUserId.setText(arrayList.get(post.getId()));
            binding.tvTitle.setText(post.getTitle());
            binding.tvDescription.setText(post.getContent());




            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.onClick(post);
                }
            });

            binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder aler = new AlertDialog.Builder(view.getContext()).
                            setMessage("удалить?").setNegativeButton("нет",null)
                            .setPositiveButton("да", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    RetrofitClient.provieApi().deletePost(post.getId()).enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            posts.remove(getAdapterPosition());
                                            notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                                        }
                                    });
                                }
                            });
                    aler.create().show();
                    return false;
                }
            });
        }
    }
    public interface onItemClick{
        void onClick(Post post);
    }


}
