package br.fecap.pi.voice;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeocodingUtils {

    public static LatLng getCoordinates(Context context, String locationName) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(locationName, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                return new LatLng(address.getLatitude(), address.getLongitude());
            }
        } catch (IOException e) {
            Log.e("GeocodingUtils", "Erro na geocodificação", e);
        }
        return null;
    }
}
