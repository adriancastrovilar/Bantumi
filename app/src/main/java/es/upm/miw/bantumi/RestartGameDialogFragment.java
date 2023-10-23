package es.upm.miw.bantumi;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.DialogFragment;

public class RestartGameDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {
        final MainActivity mainActivity = (MainActivity) requireActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);

        builder.setTitle(getString(R.string.txtRestartDialogTitle))
                .setMessage(getString(R.string.txtRestartDialogMessage))
                .setPositiveButton(
                        getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mainActivity.juegoBantumi.inicializar(JuegoBantumi.Turno.turnoJ1);
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
