package com.alfred.karela.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alfred on 16-02-2017.
 */

public class Profile extends Fragment {

    public Profile(){

    }
    public static String[] name1 = {"Alfred Skaria"};
    public static String[] place1 = {"Thrissur"};
    public static String[] number1 = {"9656573522"};
    public static String[] interests = {"tech"};
    //public static  Integer[] photo = {R.drawable.al};
    SessionManager session;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profileview, container, false);

        session = new SessionManager(getContext());
        final String USER_ID = session.getUserId();
        final String AUTH_KEY = session.getAuthKey();


        final TextView pname = (TextView) rootView.findViewById(R.id.pname);
        //pname.setText(name1[0]);

        TextView pplace = (TextView) rootView.findViewById(R.id.place);
        pplace.setText(place1[0]);

        final TextView no = (TextView) rootView.findViewById(R.id.number);


        /*ImageView pimg = (ImageView)rootView.findViewById(R.id.profile_image);
        pimg.setImageResource(photo[0]);*/

        Button btnLogout = (Button)rootView.findViewById(R.id.logout);

        ImageButton edit_img = (ImageButton) rootView.findViewById(R.id.edit_pro);
        edit_img.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), EditPro.class);
                startActivity(intent);

            }

        });
        /*
        btnLogout.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View arg0) {
                // Clear the session data
                // This will clear all session data and
                // redirect user to LoginActivity
                session.logoutUser();
                }
            });
        */


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "http://skhedule.tk/api/user/" + USER_ID, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //response.getString('name')
                        try {
                            pname.setText(response.getString("name"));
                            no.setText(response.getString("email"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"Profile Details Not Found",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                headers.put("X-User-Id", USER_ID);
                headers.put("X-Auth-Token",AUTH_KEY);
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);


        return rootView;
    }
}
