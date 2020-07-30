package com.alfred.karela.schedule;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alfred.karela.schedule.recyclerview.CustomAdapter;
import com.alfred.karela.schedule.recyclerview.DataModel;
import com.alfred.karela.schedule.recyclerview.MyData;
import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SONU on 25/09/15.
 */
public class RecyclerView_Activity extends Fragment {
    private static RecyclerView recyclerView;
    private static RecyclerView recyclerView1;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static ArrayList<DataModel> data;
    public static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;

    public RecyclerView_Activity(){

    }

    private static String navigateFrom;//String to get Intent Value
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_timeline, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView1 = (RecyclerView) rootView.findViewById(R.id.recyclerView1);
        recyclerView1.setHasFixedSize(true);

/*
       layoutManager = new LinearLayoutManager(getActivity());
*/

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView1.setItemAnimator(new DefaultItemAnimator());

        JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, "http://skhedule.tk/api/event/public/list", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<DataModel> data = new ArrayList<DataModel>();
                        Log.e("JSON",response.toString());


                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                String name = obj.getString("name");
                                String id = obj.getString("_id");
                                Log.e("Name"+i,name);
                                Log.e("Id",id);
                                Log.e("Location",obj.getString("location"));
                                DataModel dm = new DataModel(name, obj.getString("location") ,id,R.mipmap.ic_launcher);

                                data.add(dm);
                            }
                            adapter = new CustomAdapter(data);
                            recyclerView.setAdapter(adapter);
                            recyclerView1.setAdapter(adapter);

                        }
                        catch (JSONException e){
                            Log.d("json_error", response.toString());
                        }







                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        VolleyLog.d("vError", "Error: " + error.getMessage());
                        String errorMsg;
                        if(error instanceof NoConnectionError)
                            errorMsg = "Network Error";
                        else if(error instanceof TimeoutError)
                            errorMsg = "Timeout Error";
                        else
                            errorMsg = "Unknown Error";

                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request1);

        return rootView;
    }


}