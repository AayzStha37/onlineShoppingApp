package com.example.aayzstha.pnpapp;

/**
 * Created by Aayz Stha on 11/9/2017.
 */

import java.util.ArrayList;

import android.*;
import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AddressRecord extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private DataBaseHelper db;
    private ProductModule pd;
    private ListView listview1;
    private TextView ll;
    private Button custAdd;
    private Button autoDetect;
    private ProgressDialog pDiag;
    private LocationListener locListner;
    private LocationManager locMgr;
    private RequestQueue rQueue;
    private ArrayList<ProductModule> productList = new ArrayList<>();
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.5F);


    @RequiresApi(api = Build.VERSION_CODES.M)

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_address_layout);


        listview1 = (ListView) findViewById(R.id.listview);
        ll = (TextView) findViewById(R.id.tv_ll);
        custAdd = (Button) findViewById(R.id.bt_custom_add);
        autoDetect = (Button) findViewById(R.id.bt_auto);
        pDiag = new ProgressDialog(this);

        rQueue = Volley.newRequestQueue(this);

        locMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
        locListner = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Double longi = location.getLongitude();
                Double lati = location.getLatitude();
                ll.setText(String.valueOf(lati)+ "," +String.valueOf(longi));
                Log.i("latln1",ll.toString());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
                }, 10);
                return;
            }
        } else {
            configBut();
        }

        custAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                custAdd.startAnimation(buttonClick);
                final Dialog dialogBox = new Dialog(AddressRecord.this);
                dialogBox.setContentView(R.layout.create_address_dialog);
                dialogBox.setTitle("Enter Shipping Details:");
                dialogBox.setCancelable(false);
                final EditText edId = (EditText) dialogBox.findViewById(R.id.entryid);
                final EditText edName = (EditText) dialogBox.findViewById(R.id.diag_name);
                final EditText edContact = (EditText) dialogBox.findViewById(R.id.diag_contact);
                final EditText edStreet = (EditText) dialogBox.findViewById(R.id.diag_strt);
                final EditText edCity = (EditText) dialogBox.findViewById(R.id.diag_city);
                final EditText edDist = (EditText) dialogBox.findViewById(R.id.diag_district);
                final EditText edZone = (EditText) dialogBox.findViewById(R.id.diag_zone);
                Button add = (Button) dialogBox.findViewById(R.id.addentry);
                Button cancel = (Button) dialogBox.findViewById(R.id.cancelentry);
                dialogBox.show();

                add.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (edId.getText().toString().length() == 0 || edName.getText().toString().length() == 0 || edContact.getText().toString().length() == 0 ||
                                edStreet.getText().toString().length() == 0 || edCity.getText().toString().length() == 0 || edDist.getText().toString().length() == 0 || edZone.getText().toString().length() == 0) {
                            Toast.makeText(getApplicationContext(), "Please Fill Up All the Fields", Toast.LENGTH_LONG).show();
                        } else {
                            db = new DataBaseHelper(getApplicationContext());
                            db.getWritableDatabase();
                            pd = new ProductModule();

                            pd.setPid(Integer.parseInt(edId.getText().toString()));
                            pd.setPname(edName.getText().toString());
                            pd.setPcontact(edContact.getText().toString());
                            pd.setPstreet(edStreet.getText().toString());
                            pd.setPcity(edCity.getText().toString());
                            pd.setPdistrict(edDist.getText().toString());
                            pd.setPzone(edZone.getText().toString());


                            db.addData(pd, DataBaseHelper.MANUAL_ADD);
                            Toast.makeText(getApplicationContext(), "Record Successfully Added", Toast.LENGTH_LONG).show();
                            dialogBox.dismiss();
                            onResume();
                        }
                    }
                });

                cancel.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBox.dismiss();
                    }
                });
            }
        });
        autoDetect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                autoDetect.startAnimation(buttonClick);

                locMgr.requestLocationUpdates("gps", 5000, 0, locListner);

                Toast.makeText(getApplicationContext(), "Locating...", Toast.LENGTH_LONG).show();
                ll.setText("29.9490° N, 76.8173° E : Kurukshetra, Thanesar, Haryana 136119");
                String X = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
                String Z = "&key=AIzaSyApMxPc0OcYl01-QfMN50oo1HoFeyVe29E";
                String Y = ll.getText().toString().trim();
                JsonObjectRequest request = new JsonObjectRequest(X + Y + Z,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String address = response.getJSONArray("results").getJSONObject(0).
                                            getString("formatted_address");
                                    ll.setText(address);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new
                        Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                rQueue.add(request);
                pDiag.dismiss();
            }
        });

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configBut();
                return;
        }
    }

    private void configBut() {
        autoDetect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                autoDetect.startAnimation(buttonClick);
                pDiag.setMessage("Locating...");
                pDiag.setCanceledOnTouchOutside(false);
                pDiag.show();
                locMgr.requestLocationUpdates("gps", 5000, 0, locListner);

                String X = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
                String Z = "&key=AIzaSyApMxPc0OcYl01-QfMN50oo1HoFeyVe29E";
                String Y = ll.getText().toString().trim();

                JsonObjectRequest request = new JsonObjectRequest(X + Y + Z,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String address = response.getJSONArray("results").getJSONObject(0).
                                            getString("formatted_address");
                                    ll.setText(address);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new
                        Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                rQueue.add(request);
                pDiag.dismiss();
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();

        productList.clear();
        db = new DataBaseHelper(getApplicationContext());
        db.getWritableDatabase();
        productList = db.getData(DataBaseHelper.MANUAL_ADD);
        listview1.setAdapter(new AdapterClass(getApplicationContext(), productList));
        listview1.setOnItemClickListener(this);
        db.close();
    }

    public void display(final int position) {

        final Dialog dialogBox = new Dialog(AddressRecord.this);
        dialogBox.setContentView(R.layout.update_spec_address);
        dialogBox.setTitle("Enter Updates for Shipping Details:");
        dialogBox.setCancelable(false);

        final EditText updateName = (EditText) dialogBox.findViewById(R.id.updatename);
        final EditText updatePhone  = (EditText) dialogBox.findViewById(R.id.updatephn);
        final EditText updateStreet = (EditText) dialogBox.findViewById(R.id.updatestreet);
        final EditText updateCity = (EditText) dialogBox.findViewById(R.id.updatecity);
        final EditText updateDist = (EditText) dialogBox.findViewById(R.id.updatedist);
        final EditText updateZone = (EditText) dialogBox.findViewById(R.id.updatezone);
        Button update = (Button) dialogBox.findViewById(R.id.update1);
        Button delete = (Button) dialogBox.findViewById(R.id.delete1);
        Button cancel = (Button) dialogBox.findViewById(R.id.cancel1);
        dialogBox.show();

        update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                db = new DataBaseHelper(getApplicationContext());
                db.getWritableDatabase();
                pd = new ProductModule();

                if (updateName.getText().toString().length() == 0 || updatePhone.getText().toString().length() == 0
                        || updateStreet.getText().toString().length() == 0|| updateDist.getText().toString().length() == 0
                        || updateCity.getText().toString().length() == 0|| updateZone.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Update Details", Toast.LENGTH_LONG).show();
                } else {
                    pd.setPid(productList.get(position).getPid());
                    pd.setPname(updateName.getText().toString());
                    pd.setPcontact(updatePhone.getText().toString());
                    pd.setPstreet(updateStreet.getText().toString());
                    pd.setPcity(updateCity.getText().toString());
                    pd.setPdistrict(updateDist.getText().toString());
                    pd.setPzone(updateZone.getText().toString());


                    db.updateData(pd, DataBaseHelper.MANUAL_ADD);
                    db.close();
                    dialogBox.dismiss();
                    AddressRecord.this.onResume();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pd = new ProductModule();
                pd.setPid(productList.get(position).getPid());
                db.removeData(pd, DataBaseHelper.MANUAL_ADD);
                dialogBox.dismiss();
                AddressRecord.this.onResume();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBox.dismiss();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        display(position);
    }
}
