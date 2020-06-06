package com.example.baking;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.baking.models.Constants;
import com.example.baking.models.Steps;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.Serializable;
import java.util.List;

public class RecipeDetailFragment extends Fragment {
    private static String mVideoUrl;
    private static String mThumbnailUrl;
    private static String mDescription;
    private static String mShortDescription;
    static TextView descriptionTv;
    private boolean playWhenReady = true;
    private long position = -1;
    SimpleExoPlayer mExoPlayer;
    static SimpleExoPlayerView playerView;
    static ImageView fullscreenButton;
    static TextView shortDescriptionTv;
    boolean fullScreen = false;
    private final String SELECTED_POSITION = "selected_position";
    private final String PLAY_WHEN_READY = "play_when_ready";
    static Button nextBtn;
    List<Steps> stepsList;
int id;
    public RecipeDetailFragment() {

    }

    public RecipeDetailFragment(int id,String videoUrl, String thumbnailUrl, String description, String shortDescription, List<Steps> steps) {
        this.id = id;
        this.mVideoUrl = videoUrl;
        this.mThumbnailUrl = thumbnailUrl;
        this.mDescription = description;
        this.mShortDescription = shortDescription;
        this.stepsList = steps;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            this.mVideoUrl = bundle.getString(Constants.VIDEO);
            this.mThumbnailUrl = bundle.getString(Constants.THUMBNAIL);
            this.mDescription = bundle.getString(Constants.DESCRIPTION);
            this.mShortDescription = bundle.getString(Constants.SHORT_DESCRIPTION);
            this.stepsList = (List<Steps>) bundle.getSerializable(Constants.INGREDIENTS);
        }
        if (savedInstanceState != null) {
            this.mVideoUrl = savedInstanceState.getString(Constants.VIDEO);
            this.mThumbnailUrl = savedInstanceState.getString(Constants.THUMBNAIL);
            this.mDescription = savedInstanceState.getString(Constants.DESCRIPTION);
            this.mShortDescription = savedInstanceState.getString(Constants.SHORT_DESCRIPTION);
            position = savedInstanceState.getLong(SELECTED_POSITION, C.TIME_UNSET);
            playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY);
        }
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        playerView = rootView.findViewById(R.id.player);
        descriptionTv = rootView.findViewById(R.id.description_tv);
        shortDescriptionTv = rootView.findViewById(R.id.short_description);
        nextBtn = rootView.findViewById(R.id.next_btn);

        fullScreenmode();
        return rootView;
    }

    private void initializerPlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            playerView.setPlayer(mExoPlayer);

            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);

            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            if (position > 0)
                mExoPlayer.seekTo(position);
            mExoPlayer.setPlayWhenReady(playWhenReady);
        }
    }

    private void fullScreenmode() {
        fullscreenButton = playerView.findViewById(R.id.exo_fullscreen_icon);
        fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fullScreen) {
                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_fullscreen_open));

                    getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                    if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
                        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
                    }

                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) playerView.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = (int) (200 * getActivity().getApplicationContext().getResources().getDisplayMetrics().density);
                    playerView.setLayoutParams(params);

                    fullScreen = false;
                } else {
                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_fullscreen_exit));

                    getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                    if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
                        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
                    }

                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) playerView.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = params.MATCH_PARENT;
                    playerView.setLayoutParams(params);
                    fullScreen = true;
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        descriptionTv.setText(mDescription);
        shortDescriptionTv.setText(mShortDescription);

            for (int i = 0; i < stepsList.size(); i++) {
                final Steps steps = stepsList.get(i);
                if(steps.getId() == id){
                    Log.e("Id before",""+steps.getId());
                    if(++i < stepsList.size()){
                        --i;
                    final Steps stepsNext = stepsList.get(++i);
                        nextBtn.setText(stepsNext.getShortDescription());
                        nextBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
                                intent.putExtra(Constants.VIDEO, stepsNext.getVideoURL());
                                intent.putExtra(Constants.THUMBNAIL, stepsNext.getThumbnailURL());
                                intent.putExtra(Constants.DESCRIPTION, stepsNext.getDescription());
                                intent.putExtra(Constants.SHORT_DESCRIPTION, stepsNext.getShortDescription());
                                intent.putExtra(Constants.INGREDIENTS, (Serializable) stepsList);
                                intent.putExtra(Constants.ID, stepsNext.getId());
                                getContext().startActivity(intent);
                            }
                        });
                    }else {
                        nextBtn.setVisibility(View.GONE);
                    }
                }



            }

        if (!TextUtils.isEmpty(mVideoUrl))
            initializerPlayer(Uri.parse(mVideoUrl));
        else if (!TextUtils.isEmpty(mThumbnailUrl)) {
            initializerPlayer(Uri.parse(mThumbnailUrl));
        }
        else if(TextUtils.isEmpty(mVideoUrl) && TextUtils.isEmpty(mThumbnailUrl)){
            playerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null){
            position = mExoPlayer.getCurrentPosition();
            playWhenReady = mExoPlayer.getPlayWhenReady();
            releasePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
            releasePlayer();
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.VIDEO, mVideoUrl);
        outState.putString(Constants.THUMBNAIL, mThumbnailUrl);
        outState.putString(Constants.DESCRIPTION, mDescription);
        outState.putString(Constants.SHORT_DESCRIPTION, mShortDescription);
        outState.putLong(SELECTED_POSITION, position);
        outState.putBoolean(PLAY_WHEN_READY , playWhenReady);
    }


}
