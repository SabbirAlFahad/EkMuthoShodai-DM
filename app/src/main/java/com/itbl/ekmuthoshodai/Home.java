package com.itbl.ekmuthoshodai;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity {

    Button btnMenu;
    TextView txtMenu;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        btnMenu = findViewById(R.id.btnMenu);
        txtMenu = findViewById(R.id.textTitle);
        TextView textTitle = findViewById(R.id.textTitle);

        sharedPrefManager = new SharedPrefManager(getApplicationContext());


        //Notify user API higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("New Notification","You've Logged out!",
                    NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);

        txtMenu.setTypeface(ResourcesCompat.getFont(this, R.font.amaranth));

        btnMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationUI.setupWithNavController(navigationView,navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                textTitle.setText(destination.getLabel());
            }
        });

        //logout for drawer menu item
        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(menuItem -> {
            goToLogout();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(Home.this,"New Notification");
            builder.setContentTitle("New Notification");
            builder.setContentText("You've Logged out!");
            builder.setSmallIcon(R.drawable.notifications_24dp);
            builder.setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Home.this);

            managerCompat.notify(1,builder.build());

            return true;
        });

        navigationView.getMenu().findItem(R.id.nav_cPass).setOnMenuItemClickListener(menuItem -> {
            goToChangePassword();
            return true;
        });

        navigationView.getMenu().findItem(R.id.nav_entry).setOnMenuItemClickListener(menuItem -> {
            goToNewEntry();
            return true;
        });

        navigationView.getMenu().findItem(R.id.nav_myProduct).setOnMenuItemClickListener(menuItem -> {
            goToMyProduct();
            return true;
        });

        navigationView.getMenu().findItem(R.id.nav_order).setOnMenuItemClickListener(menuItem -> {
            goToProductOrder();
            return true;
        });

    }

    private void goToLogout() {

        sharedPrefManager.logout();
        Intent intent=new Intent(this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        Toast.makeText( this, "You have been logged out", Toast.LENGTH_SHORT).show();

    }

    private void goToProductOrder() {
        Intent intent = new Intent(this, ProductOrderList.class);
        startActivity(intent);
    }

    private void goToMyProduct() {
        Intent intent = new Intent(this, MyProductList.class);
        startActivity(intent);
    }

    private void goToChangePassword() {
        Intent intent = new Intent(this, ChangePassword.class);
        startActivity(intent);
    }

    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void goToNewEntry() {
        Intent intent = new Intent(this, NewProductEntry.class);
        startActivity(intent);
    }

}
