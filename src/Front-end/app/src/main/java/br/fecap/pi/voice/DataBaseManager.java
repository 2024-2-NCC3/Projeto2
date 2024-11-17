package br.fecap.pi.voice;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.heatmaps.WeightedLatLng;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;

public class DataBaseManager {

    private static final String BASE_URL = "https://3hdf9d-3000.csb.app/";
    private final RequestQueue requestQueue;
    private final Context context;

    public DataBaseManager(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public void fetchHeatmapData(final DataCallback<List<WeightedLatLng>> callback) {
        String url = BASE_URL + "locations";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<WeightedLatLng> heatmapData = parseHeatmapData(response);
                    callback.onSuccess(heatmapData);
                },
                error -> {
                    Log.e("DataBaseManager", "Erro ao obter dados para o mapa de calor", error);
                    callback.onFailure(error);
                });

        requestQueue.add(request);
    }

    public void fetchMostFrequentRegions(final DataCallback<List<String>> callback) {
        String url = BASE_URL + "mostFrequent";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<String> regions = parseMostFrequentRegions(response);
                    callback.onSuccess(regions);
                },
                error -> {
                    Log.e("DataBaseManager", "Erro ao obter regiões mais frequentes", error);
                    callback.onFailure(error);
                });

        requestQueue.add(request);
    }

    private List<WeightedLatLng> parseHeatmapData(JSONArray response) {
        List<WeightedLatLng> heatmapData = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            try {
                String region = response.getJSONObject(i).getString("region");
                LatLng latLng = GeocodingUtils.getCoordinates(context, region);
                if (latLng != null) {
                    heatmapData.add(new WeightedLatLng(latLng, 1.0));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return heatmapData;
    }

    private List<String> parseMostFrequentRegions(JSONArray response) {
        List<String> regions = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            try {
                String region = response.getJSONObject(i).getString("region");
                regions.add(region);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return regions;
    }

    public interface DataCallback<T> {
        void onSuccess(T data);
        void onFailure(Exception e);
    }
}
