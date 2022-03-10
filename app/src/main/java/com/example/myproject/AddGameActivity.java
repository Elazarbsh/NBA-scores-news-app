package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;


public class AddGameActivity extends AppCompatActivity {


    EditText scoreHomeTeam;
    EditText scoreAwayTeam;
    EditText et_date;
    Spinner spinnerHomeTeam;
    Spinner spinnerAwayTeam;
    Button addGame;
    DatabaseReference dbRef;
    TextView msg;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initViews();

        dbRef = FirebaseDatabase.getInstance().getReference().child("Games");

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddGameActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        addGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidInput()){
                    saveGame();
                }
            }
        });
    }

    private void initViews(){
        spinnerHomeTeam = findViewById(R.id.sp_homeTeam);
        spinnerAwayTeam = findViewById(R.id.sp_awayTeam);
        scoreHomeTeam = findViewById(R.id.et_homeTeamScore);
        scoreAwayTeam = findViewById(R.id.et_awayTeamScore);
        et_date = findViewById(R.id.et_date);
        addGame = findViewById(R.id.add_btn);
        msg = findViewById(R.id.msg);
    }

    private void updateLabel(){
        String myFormat="MM/dd/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        et_date.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void saveGame() {
        String homeTeam = spinnerHomeTeam.getSelectedItem().toString();
        int homeTeamScore = Integer.valueOf(scoreHomeTeam.getText().toString());
        String awayTeam = spinnerAwayTeam.getSelectedItem().toString();
        int awayTeamScore = Integer.valueOf(scoreAwayTeam.getText().toString());
        String date = et_date.getText().toString();
        Game g = new Game(createId(), date, homeTeam, awayTeam, homeTeamScore, awayTeamScore);
        dbRef.push().setValue(g);
        finish();
    }

    private int createId() {
        Random random = new Random();
        return random.nextInt(10000);
    }

    private Boolean isValidInput(){
        if(isEmpty(et_date)){
            msg.setText("Please Choose a Date");
            return false;
        }

        if(spinnerHomeTeam.getSelectedItemPosition() == 0 || spinnerAwayTeam.getSelectedItemPosition() == 0){
            msg.setText("Please Choose Teams");
            return false;
        }

        if (isEmpty(scoreHomeTeam) || isEmpty(scoreAwayTeam)){
            msg.setText("Please Enter Scores");
            return false;
        }
        return true;
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}