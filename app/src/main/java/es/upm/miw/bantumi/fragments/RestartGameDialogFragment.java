package es.upm.miw.bantumi.fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.DialogFragment;

import es.upm.miw.bantumi.MainActivity;
import es.upm.miw.bantumi.R;

public class RestartGameDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {
        final MainActivity main = (MainActivity) requireActivity();

        assert main != null;

        AlertDialog.Builder builder = new AlertDialog.Builder(main);

        builder.setTitle(getString(R.string.txtRestartDialogTitle))
                .setMessage(getString(R.string.txtRestartDialogMessage))
                .setPositiveButton(
                        getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                main.reiniciarPartida();
                            }
                        }
                )
                .setNegativeButton(
                        getString(android.R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }
                );

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
