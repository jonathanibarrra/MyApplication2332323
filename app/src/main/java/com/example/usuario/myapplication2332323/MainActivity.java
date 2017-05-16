package com.example.usuario.myapplication2332323;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String[] PYP = {"4 - 5", "6 - 7", "8 - 9", "0 - 1", "2 - 3"};
    private static final Integer[] FESTIVOS = {1, 9, 79, 103, 104, 121, 149, 170, 177, 184, 201, 219, 233, 289, 310, 317, 342, 359};

    String text;
    TextView tv1, tv_placa, tv_proximo_dia;
    private AbstractList calendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String picoYplacaHoy;
        tv1 = (TextView) findViewById(R.id.tv1);
        tv_placa = (TextView) findViewById(R.id.tv_placa);
        tv_proximo_dia = (TextView) findViewById(R.id.tv_proximo_dia);


        SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);
        tv_placa.setText(prefe.getString("placa", "0-1"));

        Calendar localCalendar = Calendar.getInstance();
        int diaAnio = localCalendar.get(Calendar.DAY_OF_YEAR);
        int diaSemana = localCalendar.get(Calendar.DAY_OF_WEEK);
        if (Arrays.asList(FESTIVOS).contains(diaAnio) || diaSemana == 1 || diaSemana == 7) {// si es festivo o sabado o domingo
            picoYplacaHoy = "NO APLICA";
        } else {
            int j = diaAnio;
            while (j > 7) {
                j = localCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY ? j - 11 : j - 6;//si es viernes resta 11 y si es otro dia resta 6
                localCalendar.set(Calendar.DAY_OF_YEAR, j);// cambia la fecha a la fecha restada
            }
            picoYplacaHoy = PYP[localCalendar.get(Calendar.DAY_OF_YEAR) - 2];//se resta dos porque lunes comenzo el dia 2
        }

        tv1.setText(" " + picoYplacaHoy + " ");
        //próximo pico y placa ... solo cuando el mismo dia corresponde con el pico y placa
        localCalendar.set(Calendar.DAY_OF_YEAR, diaAnio);//vuelve al dia actual
        if (picoYplacaHoy.equals(tv_placa.getText().toString())) {
            int j = diaAnio;
            j = localCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY ? j + 11 : j + 6;
            localCalendar.set(Calendar.DAY_OF_YEAR, j);
            Date date = localCalendar.getTime();
            SimpleDateFormat format1 = new SimpleDateFormat("E, dd MMM. yyyy");
            String date1 = format1.format(date);
            tv_proximo_dia.setText("En " + (j - diaAnio) + " días, " + date1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);
        tv_placa.setText(prefe.getString("placa", "0-1"));

        super.onResume();
        SharedPreferences prefe1 = getSharedPreferences("datos", Context.MODE_PRIVATE);
        tv_placa.setText(prefe1.getString("placa", "0-1"));
        String pico = text;
        Date d = new Date();
        String picoYplacaHoy;
        Calendar localCalendar = Calendar.getInstance();
        int diaAnio = localCalendar.get(Calendar.DAY_OF_YEAR);
        int diaSemana = localCalendar.get(Calendar.DAY_OF_WEEK);

        if (Arrays.asList(FESTIVOS).contains(diaAnio) || diaSemana == 1 || diaSemana == 7) {// si es festivo o sabado o domingo
            picoYplacaHoy = "NO";
        } else {
            int j = diaAnio;
            while (j > 7) {
                j = localCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY ? j - 11 : j - 6;//si es viernes resta 11 y si es otro dia resta 6
                localCalendar.set(Calendar.DAY_OF_YEAR, j);// cambia la fecha a la fecha restada
            }
            picoYplacaHoy = PYP[localCalendar.get(Calendar.DAY_OF_YEAR) - 2];//se resta dos porque lunes comenzo el dia 2
        }

        boolean enc1 = false;

        int pos1 = 2;
        for (int i = 0; i < PYP.length && !enc1; i++) {
            if (PYP[i].equals(tv_placa.getText())) {
                enc1 = true;
                pos1 = pos1 + i;
            }
        }

        while (pos1 <= diaAnio) {
            localCalendar.set(Calendar.DAY_OF_YEAR, pos1);
            text = String.valueOf(localCalendar.get(Calendar.DAY_OF_WEEK));


            if (text.equals("2")) {
                pos1 = pos1 + 11;

            } else {
                pos1 = pos1 + 6;
            }

            localCalendar.set(Calendar.DAY_OF_YEAR, pos1);
        }
        if (Arrays.asList(FESTIVOS).contains(pos1)) {
            localCalendar.set(Calendar.DAY_OF_YEAR, pos1);
            text = String.valueOf(localCalendar.get(Calendar.DAY_OF_WEEK));
            if (localCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {

                pos1 = pos1 + 11;
            } else {
                pos1 = pos1 + 6;
            }
            localCalendar.set(Calendar.DAY_OF_YEAR, pos1);
        }

        d = localCalendar.getTime();
        SimpleDateFormat fech = new SimpleDateFormat("EEEE, d' de 'MMMM");
        String fech1 = fech.format(d);
        tv_proximo_dia.setText("En " + (pos1 - diaAnio) + " dias, " + fech1);
    }


    public void config(View v) {
        Intent i = new Intent(this, Configuraciones.class);
        startActivity(i);
    }

    public void acer(View v) {
        Intent i = new Intent(this, AcercaDe.class);
        startActivity(i);
    }
}