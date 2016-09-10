package com.mikkoville.githubviewer;

import android.app.Application;
import android.content.Context;

import com.mikkoville.githubviewer.data.GitHubApi;
import com.mikkoville.githubviewer.presentation.GitHubPresenter;
import com.mikkoville.githubviewer.presentation.GitHubPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mikkoville on 8.9.2016.
 */

@Module
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides @Singleton
    public Context provideContext(){
        return application;
    }

    @Provides
    public OkHttpClient provideOkHttpWithLogging(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if(BuildConfig.DEBUG){
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    @Provides @Singleton
    public Retrofit provideRetroFit(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl(Constants.GITHUB_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    public GitHubApi provideGitHubApi(Retrofit retrofit){
        return retrofit.create(GitHubApi.class);
    }

    @Provides
    public GitHubPresenter provideGitHubPresenter(GitHubApi gitHubApi){
        return new GitHubPresenterImpl(gitHubApi);
    }
}
