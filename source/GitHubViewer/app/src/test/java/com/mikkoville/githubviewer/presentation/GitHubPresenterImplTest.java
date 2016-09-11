package com.mikkoville.githubviewer.presentation;

import com.mikkoville.githubviewer.data.GitHubApi;
import com.mikkoville.githubviewer.model.Commit;
import com.mikkoville.githubviewer.view.activity.CommitsListView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by mikkoville on 11.9.2016.
 */
public class GitHubPresenterImplTest {

    private GitHubPresenterImpl gitHubPresenterImpl;
    private List<Commit> commitList;

    @Mock
    CommitsListView commitsListView;

    @Mock
    GitHubApi gitHubApi;

    @Mock
    Call<List<Commit>> mockCall;

    @Mock
    ResponseBody responseBody;

    @Captor
    ArgumentCaptor<Callback<List<Commit>>> argumentCapture;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        gitHubPresenterImpl = new GitHubPresenterImpl(gitHubApi);
        gitHubPresenterImpl.setView(commitsListView);
        commitList = Collections.singletonList(new Commit());
    }

    @Test
    public void testLoadCommitsFromGithub() throws Exception {
        when(gitHubApi.getCommits(anyString(), anyString())).thenReturn(mockCall);
        Response<List<Commit>> response = Response.success(commitList);

        gitHubPresenterImpl.loadCommitsFromGithub("test", "test");

        verify(mockCall).enqueue(argumentCapture.capture());
        argumentCapture.getValue().onResponse( null, response);

        verify( commitsListView ).addCommitsToRecyclerView( commitList );
    }

    @Test
    public void testLoadCommitsFromGithub_showError_whenBadRequest() throws Exception {
        when(gitHubApi.getCommits(anyString(), anyString())).thenReturn(mockCall);
        Response<List<Commit>> response = Response.error(400, responseBody);

        gitHubPresenterImpl.loadCommitsFromGithub("test", "test");

        verify(mockCall).enqueue(argumentCapture.capture());
        argumentCapture.getValue().onResponse( null, response);

        verify( commitsListView ).showNetworkError();
    }

    @Test
    public void testLoadCommitsFromGithub_showError_whenFailedRequest() throws Exception {
        when(gitHubApi.getCommits(anyString(), anyString())).thenReturn(mockCall);
        Throwable throwable = new Throwable ( new RuntimeException ( ) );

        gitHubPresenterImpl.loadCommitsFromGithub("test", "test");

        verify(mockCall).enqueue(argumentCapture.capture());
        argumentCapture.getValue().onFailure( null, throwable);

        verify( commitsListView ).showNetworkError();
    }

    @Test
    public void testloadCommitsUntilDate() throws Exception {
        when(gitHubApi.getCommitsUntil(anyString(), anyString(), anyString())).thenReturn(mockCall);
        Response<List<Commit>> response = Response.success(commitList);

        gitHubPresenterImpl.loadCommitsUntilDate("test", "test", "test");

        verify(mockCall).enqueue(argumentCapture.capture());
        argumentCapture.getValue().onResponse( null, response);

        verify( commitsListView ).addCommitsToRecyclerView(commitList);
    }

    @Test
    public void testResume() throws Exception {
        testLoadCommitsFromGithub();
    }
}