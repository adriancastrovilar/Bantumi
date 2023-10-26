package es.upm.miw.bantumi.model.game_result;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "game_result_table")
public class GameResult {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;

//    @ColumnInfo(name = "player_name")
//    private String playerName;
//    @ColumnInfo(name = "win")
//    private Boolean win;
//    @ColumnInfo(name = "endDate")
//    private LocalDateTime endDate;
//    @ColumnInfo(name = "player_seeds")
//    private Integer playerSeeds;
//    @ColumnInfo(name = "machine_seeds")
//    private Integer machineSeeds;

    @ColumnInfo(name = "winner_name")
    private String winnerName;
    @ColumnInfo(name = "loser_name")
    private String loserName;
    @ColumnInfo(name = "endDate")
    private String endDate;
    @ColumnInfo(name = "winner_seeds")
    private Integer winnerSeeds;
    @ColumnInfo(name = "loser_seeds")
    private Integer loserSeeds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public String getLoserName() {
        return loserName;
    }

    public void setLoserName(String loserName) {
        this.loserName = loserName;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getWinnerSeeds() {
        return winnerSeeds;
    }

    public void setWinnerSeeds(Integer winnerSeeds) {
        this.winnerSeeds = winnerSeeds;
    }

    public Integer getLoserSeeds() {
        return loserSeeds;
    }

    public void setLoserSeeds(Integer loserSeeds) {
        this.loserSeeds = loserSeeds;
    }

}
