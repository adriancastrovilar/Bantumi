package es.upm.miw.bantumi;

import android.os.Bundle;

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

        final GameResultListAdapter gameResultListAdapter = new GameResultListAdapter(new GameResultListAdapter.GameResultDiff());
        RecyclerView rv = findViewById(R.id.recycler_view_game_result);
        rv.setAdapter(gameResultListAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        gameResultViewModel = new ViewModelProvider(this).get(GameResultViewModel.class);
        gameResultViewModel.getBestResults().observe(this, gameResultListAdapter::submitList);
    }

}
