package es.upm.miw.bantumi.model.game_result;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class GameResultRepository {

    private final GameResultDAO gameResultDAO;
    private final LiveData<List<GameResult>> bestResults;

    GameResultRepository(Application app) {
        GameResultRoomDatabase db = GameResultRoomDatabase.getDatabase(app);
        gameResultDAO = db.gameResultDAO();
        bestResults = gameResultDAO.getBestResults();
    }

    public LiveData<List<GameResult>> getBestResults() {
        return bestResults;
    }

    void insert(GameResult gameResult) {
        GameResultRoomDatabase.databaseWriteExecutor.execute(() -> {
            gameResultDAO.insert(gameResult);
        });
    }

}
