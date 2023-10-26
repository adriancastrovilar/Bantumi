package es.upm.miw.bantumi;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import es.upm.miw.bantumi.fragments.DeleteGameResultsDialogFragment;
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

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.opcBorrarBestResults) {
            DialogFragment deleteGameResultsDialog = new DeleteGameResultsDialogFragment();
            deleteGameResultsDialog.show(getSupportFragmentManager(), "DeleteGameResultsDialogFragment");
            return true;
        } else {
            Snackbar.make(
                    findViewById(android.R.id.content),
                    getString(R.string.txtSinImplementar),
                    Snackbar.LENGTH_LONG
            ).show();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.best_results_menu, menu);
        return true;
    }

    public void borrarResultados() {
        this.gameResultViewModel.deleteAll();
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(R.string.txtBestResultsBorrados),
                Snackbar.LENGTH_LONG
        ).show();
    }

}
