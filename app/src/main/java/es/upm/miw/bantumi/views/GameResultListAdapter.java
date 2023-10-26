package es.upm.miw.bantumi.views;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import es.upm.miw.bantumi.model.game_result.GameResult;

public class GameResultListAdapter extends ListAdapter<GameResult, GameResultViewHolder> {


    public GameResultListAdapter(@NonNull DiffUtil.ItemCallback<GameResult> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public GameResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return GameResultViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull GameResultViewHolder holder, int position) {
        GameResult gameResult = getItem(position);
        holder.bind(gameResult);
    }

    public static class GameResultDiff extends DiffUtil.ItemCallback<GameResult> {

        @Override
        public boolean areItemsTheSame(@NonNull GameResult oldItem, @NonNull GameResult newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull GameResult oldItem, @NonNull GameResult newItem) {
            return oldItem.getEndDate().equals(newItem.getEndDate());
        }
    }

}
