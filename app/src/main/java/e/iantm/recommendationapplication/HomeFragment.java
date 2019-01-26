package e.iantm.recommendationapplication;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {

    String places, reviews;
    RequestQueue requestQueue;
    MapFragment mapFragment;
    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;
    View view;
    Resources res;
    ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        res = getResources();
        places = String.format(res.getString(R.string.recommendations), res.getString(R.string.url));
        view =  inflater.inflate(R.layout.fragment_home, null);
        listView = (ListView) view.findViewById(R.id.list);
        loadFragment(new MapFragment());
        requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, places, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray recommendations = response.getJSONArray("recommendations");
                    int length = recommendations.length();
                    HashMap<String, String> item;
                    for(int i = 0; i < length; i++) {
                        JSONObject obj = recommendations.getJSONObject(i);
                        item = new HashMap<String, String>();
                        item.put("title", obj.getString("name"));
                        //item.put("summary", obj.getString("categories"));
                        list.add(item);
                    }

                    adapter = new SimpleAdapter(getContext(), list, R.layout.recommendlistview,
                            new String[] {"title"/*, "summary"*/}, new int []{R.id.title/*, R.id.summary*/});

                    listView.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView selectedItem = (TextView) view.findViewById(R.id.title);
                //String selectedItem = (String) listView.getItemAtPosition(position);

                loadFragment1(new ReviewFragment(), selectedItem.getText().toString());

            }
        });

        return view;
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.child_fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private boolean loadFragment1(Fragment fragment, String title) {
        //switching fragment

        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        if (fragment != null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
       if (requestCode == MapFragment.MY_PERMISSIONS_REQUEST_LOCATION){
            mapFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        else {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}