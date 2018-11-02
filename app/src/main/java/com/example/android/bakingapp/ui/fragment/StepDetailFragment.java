package com.example.android.bakingapp.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.databinding.FragmentStepDetailBinding;
import com.example.android.bakingapp.room.Step;
import com.example.android.bakingapp.util.ListUtils;
import com.example.android.bakingapp.util.ui.FragmentUtils;
import com.example.android.bakingapp.util.ui.IntentUtils;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.util.Util;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class StepDetailFragment extends Fragment {
    @Inject
    ExtractorMediaSource.Factory factory;

    private FragmentStepDetailBinding binding;
    private Step step;
    private long currentPosition;
    private boolean playWhenReady = true;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStepDetailBinding.inflate(inflater, container, false);
        step = ListUtils.coalesce(FragmentUtils.getArguments(this), IntentUtils.getParcelableExtra(this));
        binding.stepDescriptionTextView.setText(step.description);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if ((Util.SDK_INT < 24 || binding.playerView.getPlayer() == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }

    private void initializePlayer() {
        String url = ListUtils.coalesceString(step.videoURL, step.thumbnailURL);

        if (url == null) {
            binding.playerView.setVisibility(View.GONE);
            return;
        }

        binding.playerView.setVisibility(View.VISIBLE);

        Player player = binding.playerView.getPlayer();

        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(getContext());
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentPosition);
            binding.playerView.setPlayer(player);
        }

        ((ExoPlayer) player).prepare(factory.createMediaSource(Uri.parse(url)), false, true);
    }

    private void releasePlayer() {
        Player player = binding.playerView.getPlayer();

        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            currentPosition = player.getCurrentPosition();
            player.release();
            binding.playerView.setPlayer(null);
        }
    }
}