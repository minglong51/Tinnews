package com.laioffer.tinnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.laioffer.tinnews.R;
import com.laioffer.tinnews.model.NewsResponse;
import com.laioffer.tinnews.network.NewsApi;
import com.laioffer.tinnews.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController);
        NewsApi api = RetrofitClient.newInstance(this).create(NewsApi.class);
        /*同步操作不可以， 会有NetworkOnMainThreadException
        * try{
        *   newsApi.getTopHeadlines("us").execute();
        * } catch(IOException e){
        *   e.printStackTrace();
        * }
        *   use another thread
        * new Thread(new Runnable(){
        *   @Override
        *   public void run(){
        *       try{
        *           NewResponse newsResponse = newApi.getTopHeadLines("us").execute().body();
        *           log.d("getTopHeadlines", nesResponse.toString());
        *       }catch(IOException e){
        *            e.printStackTrace();
        *       }
        *   }
        * }).start();
        * */
        Log.d("getTopHeadLines", Thread.currentThread().getName());
        api.getTopHeadlines("US").enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("getTopHeadlines", response.body().toString());
                } else {
                    Log.d("getTopHeadlines", response.toString());
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.d("getTopHeadlines", t.toString());
            }
        });
    }

   @Override
   public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }
}