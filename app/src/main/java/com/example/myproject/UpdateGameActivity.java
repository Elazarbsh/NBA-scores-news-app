package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateGameActivity extends AppCompatActivity {

    EditText etDate;
    Spinner spHomeTeam;
    EditText etScoreHomeTeam;
    Spinner spAwayTeam;
    EditText etScoreAwayTeam;
    Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_game);
        initViews();

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        etDate.setText(intent.getStringExtra("date"));
        spHomeTeam.setPrompt(intent.getStringExtra("homeTeam"));
        spAwayTeam.setPrompt(intent.getStringExtra("awayTeam"));
        etScoreHomeTeam.setText(String.valueOf(intent.getIntExtra("homeTeamScore", 0)));
        etScoreAwayTeam.setText(String.valueOf(intent.getIntExtra("awayTeamScore", 0)));

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Games");

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.orderByChild("id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot entries : dataSnapshot.getChildren()) {
                                String key = entries.getKey();
                                ref.child(key).child("homeTeam").setValue(spHomeTeam.getSelectedItem().toString());
                                ref.child(key).child("homeTeamScore").setValue(Integer.valueOf(etScoreHomeTeam.getText().toString()));
                                ref.child(key).child("awayTeam").setValue(spAwayTeam.getSelectedItem().toString());
                                ref.child(key).child("awayTeamScore").setValue(Integer.valueOf(etScoreAwayTeam.getText().toString()));
                                ref.child(key).child("date").setValue(etDate.getText().toString());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                finish();
            }
        });

    }
    public void initViews(){
        etDate = (EditText) findViewById(R.id.etDate);
        spHomeTeam = (Spinner) findViewById(R.id.sp_updateHomeTeam);
        etScoreHomeTeam = (EditText) findViewById(R.id.et_updateScoreHomeTeam);
        spAwayTeam = (Spinner) findViewById(R.id.sp_updateAwayTeam);
        etScoreAwayTeam = (EditText) findViewById(R.id.et_updateScoreAwayTeam);
        updateButton = (Button) findViewById(R.id.updateButton);
    }
}