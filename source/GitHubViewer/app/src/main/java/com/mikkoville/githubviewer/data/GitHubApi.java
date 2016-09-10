package com.mikkoville.githubviewer.data;

import com.mikkoville.githubviewer.model.Commit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ukkeli on 7.9.2016.
 */
public interface GitHubApi {

    /**
     *
     * @param owner owner of repo
     * @param repo name of repo
     * @return list of commits
     */
    @GET("repos/{owner}/{repo}/commits")
    Call<List<Commit>> getCommits(@Path("owner") String owner, @Path("repo") String repo);

    @GET( "repos/{owner}/{repo}/commits")
    Call<List<Commit>> getCommitsUntil(@Path("owner") String owner, @Path("repo") String repo, @Query("until") String until);
}
