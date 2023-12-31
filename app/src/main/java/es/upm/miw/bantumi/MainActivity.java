package es.upm.miw.bantumi;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

import es.upm.miw.bantumi.fragments.BackupGameDialogFragment;
import es.upm.miw.bantumi.fragments.RestartGameDialogFragment;
import es.upm.miw.bantumi.fragments.SaveGameDialogFragment;
import es.upm.miw.bantumi.model.BantumiViewModel;
import es.upm.miw.bantumi.model.game_result.GameResult;
import es.upm.miw.bantumi.model.game_result.GameResultViewModel;

public class MainActivity extends AppCompatActivity {

    protected static final String LOG_TAG = "MiW";
    JuegoBantumi juegoBantumi;
    BantumiViewModel bantumiVM;
    int numInicialSemillas;

    GameResultViewModel gameResultViewModel;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instancia el ViewModel y el juego, y asigna observadores a los huecos
        this.numInicialSemillas = getResources().getInteger(R.integer.intNumInicialSemillas);
        this.bantumiVM = new ViewModelProvider(this).get(BantumiViewModel.class);
        this.juegoBantumi = new JuegoBantumi(bantumiVM, JuegoBantumi.Turno.turnoJ1, numInicialSemillas);
        this.gameResultViewModel = new ViewModelProvider(this).get(GameResultViewModel.class);
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
        crearObservadores();
    }

    @Override
    protected void onResume() {
        super.onResume();
        prefSet();
    }

    private void prefSet() {
        prefSetPlayerName();
    }

    private void prefSetPlayerName() {
        TextView tvJugador1 = findViewById(R.id.tvPlayer1);
        tvJugador1.setText(getPlayerName());
    }

    private String getPlayerName() {
        if (prefGetPlayerName().isEmpty()) {
            return "Jugador 1";
        } else {
            return prefGetPlayerName();
        }
    }

    private String getMachineName() {
        return "Jugador 2";
    }

    private String prefGetPlayerName() {
        return this.preferences.getString(
                "playername",
                getString(R.string.playername_title)
        );
    }

    /**
     * Crea y subscribe los observadores asignados a las posiciones del tablero.
     * Si se modifica el contenido del tablero -> se actualiza la vista.
     */
    private void crearObservadores() {
        for (int i = 0; i < JuegoBantumi.NUM_POSICIONES; i++) {
            int finalI = i;
            bantumiVM.getNumSemillas(i).observe(    // Huecos y almacenes
                    this,
                    new Observer<Integer>() {
                        @Override
                        public void onChanged(Integer integer) {
                            mostrarValor(finalI, juegoBantumi.getSemillas(finalI));
                        }
                    });
        }
        bantumiVM.getTurno().observe(   // Turno
                this,
                new Observer<JuegoBantumi.Turno>() {
                    @Override
                    public void onChanged(JuegoBantumi.Turno turno) {
                        marcarTurno(juegoBantumi.turnoActual());
                    }
                }
        );
    }

    /**
     * Indica el turno actual cambiando el color del texto
     *
     * @param turnoActual turno actual
     */
    private void marcarTurno(@NonNull JuegoBantumi.Turno turnoActual) {
        TextView tvJugador1 = findViewById(R.id.tvPlayer1);
        TextView tvJugador2 = findViewById(R.id.tvPlayer2);
        switch (turnoActual) {
            case turnoJ1:
                tvJugador1.setTextColor(getColor(R.color.white));
                tvJugador1.setBackgroundColor(getColor(android.R.color.holo_blue_light));
                tvJugador2.setTextColor(getColor(R.color.black));
                tvJugador2.setBackgroundColor(getColor(R.color.white));
                break;
            case turnoJ2:
                tvJugador1.setTextColor(getColor(R.color.black));
                tvJugador1.setBackgroundColor(getColor(R.color.white));
                tvJugador2.setTextColor(getColor(R.color.white));
                tvJugador2.setBackgroundColor(getColor(android.R.color.holo_blue_light));
                break;
            default:
                tvJugador1.setTextColor(getColor(R.color.black));
                tvJugador2.setTextColor(getColor(R.color.black));
        }
    }

    /**
     * Muestra el valor <i>valor</i> en la posición <i>pos</i>
     *
     * @param pos   posición a actualizar
     * @param valor valor a mostrar
     */
    private void mostrarValor(int pos, int valor) {
        String num2digitos = String.format(Locale.getDefault(), "%02d", pos);
        // Los identificadores de los huecos tienen el formato casilla_XX
        int idBoton = getResources().getIdentifier("casilla_" + num2digitos, "id", getPackageName());
        if (0 != idBoton) {
            TextView viewHueco = findViewById(idBoton);
            viewHueco.setText(String.valueOf(valor));
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.opcAcercaDe:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.aboutTitle)
                        .setMessage(R.string.aboutMessage)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
                return true;

            case R.id.opcReiniciarPartida:
                DialogFragment restartGameDialog = new RestartGameDialogFragment();
                restartGameDialog.show(getSupportFragmentManager(), "RestartGameDialogFragment");
                return true;

            case R.id.opcGuardarPartida:
                DialogFragment saveGameDialog = new SaveGameDialogFragment();
                saveGameDialog.show(getSupportFragmentManager(), "SaveGameDialogFragment");
                return true;

            case R.id.opcRecuperarPartida:
                DialogFragment backupGameDialog = new BackupGameDialogFragment();
                backupGameDialog.show(getSupportFragmentManager(), "BackupGameDialogFragment");
                return true;

            case R.id.opcAjustes:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                return true;

            case R.id.opcMejoresResultados:
                startActivity(new Intent(getApplicationContext(), GameResultActivity.class));
                return true;

            default:
                showSnackbarById(R.string.txtSinImplementar);
        }
        return true;
    }

    /**
     * Acción que se ejecuta al pulsar sobre cualquier hueco
     *
     * @param v Vista pulsada (hueco)
     */
    public void huecoPulsado(@NonNull View v) {
        String resourceName = getResources().getResourceEntryName(v.getId()); // pXY
        int num = Integer.parseInt(resourceName.substring(resourceName.length() - 2));
        Log.i(LOG_TAG, "huecoPulsado(" + resourceName + ") num=" + num);
        switch (juegoBantumi.turnoActual()) {
            case turnoJ1:
                juegoBantumi.jugar(num);
                break;
            case turnoJ2:
                juegaComputador();
                break;
            default:    // JUEGO TERMINADO
                finJuego();
        }
        if (juegoBantumi.juegoTerminado()) {
            finJuego();
        }
    }

    /**
     * Elige una posición aleatoria del campo del jugador2 y realiza la siembra
     * Si mantiene turno -> vuelve a jugar
     */
    void juegaComputador() {
        while (juegoBantumi.turnoActual() == JuegoBantumi.Turno.turnoJ2) {
            int pos = 7 + (int) (Math.random() * 6);    // posición aleatoria [7..12]
            Log.i(LOG_TAG, "juegaComputador(), pos=" + pos);
            if (juegoBantumi.getSemillas(pos) != 0 && (pos < 13)) {
                juegoBantumi.jugar(pos);
            } else {
                Log.i(LOG_TAG, "\t posición vacía");
            }
        }
    }

    /**
     * El juego ha terminado. Volver a jugar?
     */
    private void finJuego() {
        String texto = "";
        String winnerName = "";
        int winnerPosition = 0;
        String loserName = "";
        int loserPosition = 0;

        if (juegoBantumi.getSemillas(6) > 6 * numInicialSemillas) {
            texto += "Gana " + getPlayerName();
            winnerName += getPlayerName();
            winnerPosition = 6;
            loserName += getMachineName();
            loserPosition = JuegoBantumi.NUM_POSICIONES - 1;
        } else if (juegoBantumi.getSemillas(6) < 6 * numInicialSemillas) {
            texto += "Gana Jugador 2";
            winnerName += getMachineName();
            winnerPosition = JuegoBantumi.NUM_POSICIONES - 1;
            loserName += getPlayerName();
            loserPosition = 6;
        } else if (juegoBantumi.getSemillas(6) == 6 * numInicialSemillas) {
            texto = "¡¡¡ EMPATE !!!";
            winnerName += "-";
            loserName += "-";
        }
        Snackbar.make(
                        findViewById(android.R.id.content),
                        texto,
                        Snackbar.LENGTH_LONG
                )
                .show();

        // Almacenar el resultado de la partida
        GameResult gameResult = new GameResult();

        gameResult.setWinnerName(winnerName);
        gameResult.setWinnerSeeds(this.juegoBantumi.getSemillas(winnerPosition));

        gameResult.setLoserName(loserName);
        gameResult.setLoserSeeds(this.juegoBantumi.getSemillas(loserPosition));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        gameResult.setEndDate(LocalDateTime.now().format(formatter));

        this.gameResultViewModel.insert(gameResult);

        // terminar
        new FinalAlertDialog().show(getSupportFragmentManager(), "ALERT_DIALOG");
    }

    public void reiniciarPartida() {
        this.juegoBantumi.inicializar(JuegoBantumi.Turno.turnoJ1);
        this.showSnackbarById(R.string.txtRestartGame);
    }

    public void guardarPartida() {
        this.writeSaveFile(this.juegoBantumi.serializa());
    }

    private void writeSaveFile(String serializedGame) {
        try {
            Log.i(LOG_TAG, "Guardando partida...");
            FileOutputStream fOut = openFileOutput(getString(R.string.saveFile), MODE_PRIVATE);
            fOut.write(serializedGame.getBytes());
            fOut.close();
            Log.i(LOG_TAG, "Partida guardada");
            this.showSnackbarById(R.string.txtSavedGame);
        } catch (IOException iex) {
            Log.e(LOG_TAG, Objects.requireNonNull(iex.getMessage()));
            iex.printStackTrace();
        }
    }

    public void recuperarPartida() {
        this.readSaveFile();
    }

    private void readSaveFile() {
        try {
            Log.i(LOG_TAG, "Restaurando partida...");
            FileInputStream fInput = openFileInput(getString(R.string.saveFile));
            BufferedReader reader = new BufferedReader(new InputStreamReader(fInput));
            String endl = "\n";
            String line = reader.readLine();
            StringBuilder builder = new StringBuilder();
            builder.append(line)
                    .append(endl);
            while (line != null) {
                line = reader.readLine();
                builder.append(line)
                        .append(endl);
            }
            this.juegoBantumi.deserializa(builder.toString());
            fInput.close();
            Log.i(LOG_TAG, "Partida restaurada");
            this.showSnackbarById(R.string.txtBackupGame);
        } catch (FileNotFoundException fEx) {
            this.showSnackbarById(R.string.txtNoSaveFile);
        } catch (IOException iex) {
            Log.e(LOG_TAG, Objects.requireNonNull(iex.getMessage()));
            iex.printStackTrace();
        }
    }

    private void showSnackbarById(Integer id) {
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(id),
                Snackbar.LENGTH_LONG
        ).show();
    }
}