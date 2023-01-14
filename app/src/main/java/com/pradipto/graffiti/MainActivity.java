package com.pradipto.graffiti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.pradipto.graffiti.models.ApiResponse;
import com.pradipto.graffiti.models.PhotoModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<PhotoModel> modelArrayList;
    private RecyclerView recyclerView;
    RvAdapter rvAdapter;

    private EditText searchText;
    private ImageButton searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchText = findViewById(R.id.etSearch);
        searchBtn = findViewById(R.id.btnSearch);

        recyclerView = findViewById(R.id.rvWallpaper);
        modelArrayList = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        rvAdapter = new RvAdapter(getApplicationContext(), modelArrayList);
        recyclerView.setAdapter(rvAdapter);
        
        fetchWallpapers();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String query = searchText.getText().toString().trim().toLowerCase();
                if(query.isEmpty()){
                    Toast.makeText(MainActivity.this, "Search something valid", Toast.LENGTH_SHORT).show();
                }
                else{
                    getSearchPhotos(query);
                }
            }
        });
    }

    private void fetchWallpapers() {

        modelArrayList.clear();
        RequestManager.getApiInterface().getPhotos(1, 80).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {

                if(response.isSuccessful()){
                    assert response.body() != null;
                    modelArrayList.addAll(response.body().getPhotos());
                    rvAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(MainActivity.this, "Server Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {

            }
        });
    }

    private void getSearchPhotos(String query){

        RequestManager.getApiInterface().getSearchPhotos(query, 1, 80).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {

                modelArrayList.clear();
                if(response.isSuccessful()){
                    modelArrayList.addAll(response.body().getPhotos());
                    rvAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(MainActivity.this, "Sorry! Please try something else", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {

            }
        });
    }
}
