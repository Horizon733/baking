package com.example.baking;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.baking.models.Constants;
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

    public RecipeDetailFragment() {

    }

    public RecipeDetailFragment(String videoUrl, String thumbnailUrl, String description, String shortDescription) {
        this.mVideoUrl = videoUrl;
        this.mThumbnailUrl = thumbnailUrl;
        this.mDescription = description;
        this.mShortDescription = shortDescription;
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
        }
        if (savedInstanceState != null) {
            this.mVideoUrl = savedInstanceState.getString(Constants.VIDEO);
            this.mThumbnailUrl = savedInstanceState.getString(Constants.THUMBNAIL);
            this.mDescription = savedInstanceState.getString(Constants.DESCRIPTION);
            this.mShortDescription = savedInstanceState.getString(Constants.SHORT_DESCRIPTION);
        }
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        playerView = rootView.findViewById(R.id.player);
        descriptionTv = rootView.findViewById(R.id.description_tv);
        shortDescriptionTv = rootView.findViewById(R.id.short_description);
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mExoPlayer != null) {
            position = mExoPlayer.getCurrentPosition();
            playWhenReady = mExoPlayer.getPlayWhenReady();
            releasePlayer();
        }
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
    }
}
