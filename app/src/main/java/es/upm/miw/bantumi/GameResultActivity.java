package es.upm.miw.bantumi;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.upm.miw.bantumi.model.game_result.GameResultViewModel;
import es.upm.miw.bantumi.views.GameResultListAdapter;

public class GameResultActivity extends AppCompatActivity {

    GameResultViewModel gameResultViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_result_activity);
        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_revert);
        gameResultViewModel = new ViewModelProvider(this).get(GameResultViewModel.class);
        RecyclerView rv = findViewById(R.id.recycler_view_game_result);

        final GameResultListAdapter gameResultListAdapter = new GameResultListAdapter(new GameResultListAdapter.GameResultDiff());
        rv.setAdapter(gameResultListAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        gameResultViewModel.getBestResults().observe(this, gameResultListAdapter::submitList);
    }

}
