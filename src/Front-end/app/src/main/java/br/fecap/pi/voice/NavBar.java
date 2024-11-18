package br.fecap.pi.voice;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

public class NavBar {
    public static boolean handleNavigation(Context context, MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            context.startActivity(new Intent(context, MainActivity.class));
            return true;
        } else if (itemId == R.id.nav_warning) {
            context.startActivity(new Intent(context, Complaint.class));
            return true;
        } else if (itemId == R.id.nav_image) {
            context.startActivity(new Intent(context, MapsActivity.class));
            return true;
        } else {
            return false;
        }
    }

}

