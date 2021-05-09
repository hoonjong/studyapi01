package com.jongdroid.studyapi01;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity  extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getApi();
            }
        });

    }

    private void getApi(){

//        new AsyncTask<Void, Void, String>() {
//            ProgressDialog progress;
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                progress = new ProgressDialog(MainActivity.this);
//                progress.setTitle("다운로드");
//                progress.setMessage("download");
//                progress.setProgressStyle((ProgressDialog.STYLE_SPINNER));
//                progress.setCancelable(false);
//                progress.show();
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                StringBuffer sb = new  StringBuffer();
//                try {
//                    JSONObject json = new JSONObject(s);
//
//                    JSONArray rows = json.getJSONArray("realtimeArrivalList");
//
//                    int length = rows.length();
//                    for(int i=0; i < length; i ++){
//                        JSONObject result = (JSONObject) rows.get(i);
//                        String trainName = result.getString("trainLineNm");
//                        sb.append(trainName + "\n");
//
//                    }
//
//                }catch (Exception e ){}
//
//                textView.setText(sb.toString());
//                progress.dismiss();
//            }
//
//            @Override
//            protected String doInBackground(Void... params) {
//                String result = "";
//                try {
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                return result;
//            }
//        }.execute();

        new Thread(new Runnable() {
            @Override
            public void run() {
                //서울시 오픈 API 제공(샘플 주소 json으로 작업)
                try {
                    String sResult = Remote.getData("http://swopenAPI.seoul.go.kr/api/subway/6d596b5450716b72383073574f7652/json/realtimeStationArrival/0/5/지행");
                    StringBuilder sb = new StringBuilder();
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        JSONArray jsonArray = jsonObject.getJSONArray("realtimeArrivalList");

                        for(int i = 0; i < jsonArray.length(); i ++){
                            JSONObject jsonResult = (JSONObject) jsonArray.get(i);
                            String trainName = jsonResult.getString("updnLine");
                            String nexttrain = jsonResult.getString("trainLineNm");
                            String arrive = jsonResult.getString("arvlMsg2");
                            sb.append("상하행선구분:" + trainName).append("\n");
                            sb.append("도착지방면:" + nexttrain).append("\n");
                            sb.append("도착예정:" + arrive).append("\n\n");

                        }

                    } catch (Exception e ) {
                        e.printStackTrace();
                    }

                    // run UI thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // set result to TextView
                            textView.setText(sb.toString());
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}


