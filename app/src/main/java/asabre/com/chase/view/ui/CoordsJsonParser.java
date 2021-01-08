package asabre.com.chase.view.ui;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CoordsJsonParser {
    private static final String TAG = CoordsJsonParser.class.getSimpleName();

    public List<List<HashMap<String, String>>> parse(JSONObject jsonObject){
        List<List<HashMap<String, String>>> cords = new ArrayList<>();

        JSONObject jRoutes = null;
        JSONObject jLatLng = null;
        JSONObject jLegs = null;
        JSONObject jSteps = null;

        try {
            Log.d(TAG, "parse: trying two ");
            List path = new ArrayList<HashMap<String, String>>();

            jRoutes = jsonObject.getJSONObject("result");
            Log.d(TAG, "jRoutes " + jRoutes);
            jLegs = jRoutes.getJSONObject("geometry");
            jSteps = jLegs.getJSONObject("location");
            HashMap<String, String> theLat = new HashMap<>();

            double lat =  jSteps.getDouble("lat");
            double lng =  jSteps.getDouble("lng");
            theLat.put("lng", String.valueOf(lng));
            theLat.put("lat",  String.valueOf(lat));
            path.add(theLat);
//
            cords.add(path);
        } catch (Exception e){

        }
        return cords;
    }

}
