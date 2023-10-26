package es.upm.miw.bantumi.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import es.upm.miw.bantumi.R;
import es.upm.miw.bantumi.model.game_result.GameResult;

public class GameResultViewHolder extends RecyclerView.ViewHolder {

    TextView tvWinnerName;
    TextView tvWinnerSeeds;
    TextView tvLoserName;
    TextView tvLoserSeeds;
    TextView tvEndDate;

    public GameResultViewHolder(@NonNull View itemView) {
        super(itemView);
        this.tvWinnerName = itemView.findViewById(R.id.tvWinnerName);
        this.tvWinnerSeeds = itemView.findViewById(R.id.tvWinnerSeeds);
        this.tvLoserName = itemView.findViewById(R.id.tvLoserName);
        this.tvLoserSeeds = itemView.findViewById(R.id.tvLoserSeeds);
        this.tvEndDate = itemView.findViewById(R.id.tvEndDate);
    }

    static GameResultViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_result_item, parent, false);
        return new GameResultViewHolder(view);
    }

    public void bind(GameResult gameResult) {
        this.tvWinnerName.setText(gameResult.getWinnerName());
        this.tvWinnerSeeds.setText(gameResult.getWinnerSeeds().toString());
        this.tvLoserName.setText(gameResult.getLoserName());
        this.tvLoserSeeds.setText(gameResult.getLoserSeeds().toString());
        this.tvEndDate.setText(gameResult.getEndDate());
    }

}
