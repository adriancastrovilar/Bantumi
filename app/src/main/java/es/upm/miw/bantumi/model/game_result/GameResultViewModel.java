package es.upm.miw.bantumi.model.game_result;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class GameResultViewModel extends AndroidViewModel {

    private final LiveData<List<GameResult>> bestResults;
    private final GameResultRepository gameResultRepository;

    public GameResultViewModel(Application application) {
        super(application);
        gameResultRepository = new GameResultRepository(application);
        bestResults = gameResultRepository.getBestResults();
    }

    public LiveData<List<GameResult>> getBestResults() {
        return bestResults;
    }

    public void insert(GameResult gameResult) {
        gameResultRepository.insert(gameResult);
    }

    public void deleteAll() {
        gameResultRepository.deleteAll();
    }
}
