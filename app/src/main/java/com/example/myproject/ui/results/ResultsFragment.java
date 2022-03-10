package com.example.myproject.ui.results;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.example.myproject.AddGameActivity;
import com.example.myproject.GameListAdapter;
import com.example.myproject.Game;
import com.example.myproject.R;
import com.example.myproject.UpdateGameActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class ResultsFragment extends Fragment {
    ListView listView;
    DatabaseReference dbRef;
    FloatingActionButton addButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_games, container, false);
        listView = (ListView) v.findViewById(R.id.listView);
        addButton = (FloatingActionButton) v.findViewById(R.id.floatingActionButton);
        ArrayList<Game> games = new ArrayList<>();
        GameListAdapter gamesAdapter = new GameListAdapter(getActivity(), R.layout.list_row, games);
        dbRef = FirebaseDatabase.getInstance().getReference("Games");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Game game = ds.getValue(Game.class);
                    games.add(game);
                }
                listView.setAdapter(gamesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddGameActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener((AdapterView.OnItemClickListener) (parent, view, position, id) -> {
            Game game = games.get(position);
            Intent intent = new Intent(getActivity(), UpdateGameActivity.class);
            intent.putExtra("id", game.getId());
            intent.putExtra("date", game.getDate());
            intent.putExtra("homeTeam", game.getHomeTeam());
            intent.putExtra("awayTeam", game.getAwayTeam());
            intent.putExtra("homeTeamScore", game.getHomeTeamScore());
            intent.putExtra("awayTeamScore", game.getAwayTeamScore());
            startActivity(intent);
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder AlertBuilder = new AlertDialog.Builder(getActivity());
                AlertBuilder.setTitle("Warning");
                AlertBuilder.setMessage("Are you sure you want to delete this game?\n");
                AlertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Game game = games.get(position);
                        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
                        Query gamesQuery = dbRef.child("Games").orderByChild("id").equalTo(game.getId());
                        gamesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                    appleSnapshot.getRef().removeValue();
                                }
                                onResume();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.e("onCancelled", String.valueOf(databaseError.toException()));
                            }
                        });

                        gamesAdapter.remove(gamesAdapter.getItem(position));
                        gamesAdapter.notifyDataSetChanged();
                    }
                });
                AlertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertBuilder.show();
                return false;
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        ArrayList<Game> gamesList = new ArrayList<>();
        GameListAdapter gamesAdapter = new GameListAdapter(getActivity(), R.layout.list_row, gamesList);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Game game = ds.getValue(Game.class);
                    gamesList.add(game);

                }
                listView.setAdapter(gamesAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}

