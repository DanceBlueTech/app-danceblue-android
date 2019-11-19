package com.example.danceblue;

import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Finds and creates listener for the bottom navigation bar.
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //Launches home fragment upon app start.
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new home()).commit();

        //Instantiates and creates listener for rave button in toolbar.
        ImageButton raveButton = findViewById(R.id.rave_button);
        raveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, raveHour.class));
            }
        });
    }

    //Changes the toolbar text at the top of the screen
    public void changeToolbarText (String Title){
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        toolbarTitle.setText(Title);
    }

    //This function handles switching between the different fragments that the main
    // activity is displaying. Defaults to home.
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment;

                    switch(menuItem.getItemId()){
                        //Switches to the Event page
                        case R.id.action_events:
                            selectedFragment = new events();
                            changeToolbarText("Events");
                            break;
                        //Switches to the Blog page
                        case R.id.action_blog:
                            selectedFragment = new blog();
                            changeToolbarText("Blog");
                            break;
                        //Switches to the More page
                        case R.id.action_more:
                            selectedFragment = new more();
                            changeToolbarText("More");
                            break;
                        //Defaults to the Home page
                        default:
                            selectedFragment = new home();
                            changeToolbarText("Home");
                            break;
                    }

                    //Commits change, loads new fragment
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}
