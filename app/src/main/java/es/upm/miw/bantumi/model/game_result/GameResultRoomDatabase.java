package es.upm.miw.bantumi.model.game_result;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {GameResult.class}, version = 1, exportSchema = false)
public abstract class GameResultRoomDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "game_result_database";
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile GameResultRoomDatabase INSTANCE;

    static GameResultRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GameResultRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    GameResultRoomDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract GameResultDAO gameResultDAO();

}
