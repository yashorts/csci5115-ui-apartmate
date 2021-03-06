package com.csci5115.group8;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.csci5115.group8.data.DataManager;
import com.csci5115.group8.data.Notification;
import com.csci5115.group8.data.Thread;
import com.csci5115.group8.ui.chatThread.ChatThreadFragment;
import com.csci5115.group8.ui.dummy.DummyContent;
import com.csci5115.group8.ui.notificationList.NotificationsListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Timer;
import java.util.TimerTask;

public class DashboardActivity extends AppCompatActivity implements ChatThreadFragment.OnListFragmentInteractionListener, NotificationsListFragment.OnListFragmentInteractionListener {

    private boolean firstExitTry = true;

    @Override
    public void onListFragmentInteraction(Thread item) {
        Intent threadIntent = new Intent(DashboardActivity.this, ThreadActivity.class);
        threadIntent.putExtra("email", item.user.email);
        startActivity(threadIntent);
    }

    @Override
    public void onListFragmentInteraction(Notification item) {
        item.read = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        final ConstraintLayout layout = findViewById(R.id.activity_dashboard);
        final BottomNavigationView navView = layout.findViewById(R.id.dashboard_nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.dashboard_apt_search, R.id.dashboard_user_search, R.id.dashboard_chat)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.dashboard_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        // refresh notification badge
        // this is a bad way to do this but... I don't android much
        Timer timer = new Timer();
        TimerTask myTask = new TimerTask() {
            @Override
            public void run() {
                int unread = DataManager.getUnreadNotifications();
                if (unread > 0) {
                    navView.getOrCreateBadge(R.id.dashboard_notifications).setNumber(unread);
                    navView.getOrCreateBadge(R.id.dashboard_notifications).setVisible(true);
                } else {
                    navView.getOrCreateBadge(R.id.dashboard_notifications).setVisible(false);
                }
            }
        };

        timer.schedule(myTask, 2000, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_activity_top_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.create_apartment_lisiting:
                intent = new Intent(this, CreateApartmentListingActivity.class);
                startActivity(intent);
                return true;
            case R.id.edit_your_profile:
                intent = new Intent(this, EditYourProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.customize_apt_srl:
                intent = new Intent(this, AptSRLCustomizationActivity.class);
                startActivity(intent);
                return true;
            case R.id.customize_user_srl:
                intent = new Intent(this, UserSRLCustomizationActivity.class);
                startActivity(intent);
                return true;
            case R.id.help:
                intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (firstExitTry) {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            firstExitTry = false;
            return;
        }
        firstExitTry = true;
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
