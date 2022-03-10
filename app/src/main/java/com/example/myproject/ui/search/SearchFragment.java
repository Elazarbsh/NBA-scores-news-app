package com.example.myproject.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.myproject.GameListAdapter;
import com.example.myproject.Game;
import com.example.myproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class SearchFragment extends Fragment {

    SearchView searchView;
    RadioGroup radioGroup;
    RadioButton radioButton;
    ListView listView;
    View v;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_search, container, false);
        listView = (ListView) v.findViewById(R.id.listView);
        searchView = (SearchView) v.findViewById(R.id.sv);
        radioGroup = (RadioGroup) v.findViewById(R.id.radio_group);
        radioGroup.check(R.id.radio_team);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return true;
            }
        });
        return v;
    }

    private void search(String query){
        int SelectedFilter = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) v.findViewById(SelectedFilter);

        if (radioButton.getText().toString().equals("Team")) {
            filterByTeam(query);
        }

        if (radioButton.getText().toString().equals("Date")) {
            filterByDate(query);
        }
    }


    private void filterByTeam(String query) {
        if(isEmpty(query)){
            return;
        }

        ArrayList<Game> games = new ArrayList<>();
        GameListAdapter gamesAdapter = new GameListAdapter(getActivity(), R.layout.list_row, games);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Games");
        ref.orderByChild("homeTeam").startAt(query).endAt(query+"\uf8ff").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    games.add(userSnapshot.getValue(Game.class));
                }
                listView.setAdapter(gamesAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        ref.orderByChild("awayTeam").startAt(query).endAt(query+"\uf8ff").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    games.add(userSnapshot.getValue(Game.class));
                }
                listView.setAdapter(gamesAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }


    public void filterByDate(String query){
        ArrayList<Game> games = new ArrayList<>();
        GameListAdapter gameAdapter = new GameListAdapter(getActivity(), R.layout.list_row, games);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Games");
        ref.orderByChild("date").startAt(query).endAt(query+"\uf8ff").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    games.add(userSnapshot.getValue(Game.class));
                }
                listView.setAdapter(gameAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }

    private boolean isEmpty(String text) {
        return text.trim().length() == 0;
    }
}