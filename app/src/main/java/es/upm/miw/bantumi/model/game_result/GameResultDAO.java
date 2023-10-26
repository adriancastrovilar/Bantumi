package es.upm.miw.bantumi.model.game_result;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GameResultDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(GameResult gameResult);

    @Query("DELETE FROM game_result_table")
    void deleteAll();

    @Query("SELECT * FROM game_result_table ORDER BY winner_seeds DESC LIMIT 10")
    LiveData<List<GameResult>> getBestResults();

}
