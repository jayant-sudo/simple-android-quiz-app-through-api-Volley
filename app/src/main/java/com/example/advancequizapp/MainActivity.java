package com.example.advancequizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.advancequizapp.Model.QuizData;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String URL_DATA="https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
 private Button true_button,false_button;
 private ImageButton prev_button,next_button;
 private TextView current_score,highest_score,no_of_question,data;
 private int count=0;
 private int score=0;
 private List<QuizData>  quizDataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        true_button=findViewById(R.id.true_button);
        false_button=findViewById(R.id.false_button);
         prev_button=(ImageButton)findViewById(R.id.prev_button);
         next_button=(ImageButton)findViewById(R.id.next_button);
         current_score=findViewById(R.id.score);
         highest_score=findViewById(R.id.highest_score);
         no_of_question=findViewById(R.id.no_of_question);
         data=findViewById(R.id.data);
        quizDataList=new ArrayList<>();

         true_button.setOnClickListener(this);
         false_button.setOnClickListener(this);
         prev_button.setOnClickListener(this);
         next_button.setOnClickListener(this);



         api_calling();

    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.true_button:
                false_button.setEnabled(false);
                true_button.setEnabled(false);
                  check_answer(true);
                  break;
            case R.id.false_button:
                false_button.setEnabled(false);
                true_button.setEnabled(false);
                check_answer(false);
                 break;
            case R.id.next_button:
                 update_next();
                  break;
            case R.id.prev_button:
                update_prev();
                  break;
            default:
                   break;
        }

    }

    private void update_prev() {

        if(count !=0){
            current_score.setText("Score: "+(score-10));
            count=(count-1)%quizDataList.size();
            no_of_question.setText((count+1)+"/"+quizDataList.size());
            data.setText(quizDataList.get(count).getQuestions());
        }else{

        }
    }

    private void update_next() {
        if(true_button.isEnabled() || false_button.isEnabled())
        {
            Toast.makeText(getApplicationContext(),"Please select one option...",Toast.LENGTH_SHORT).show();
        }
        else {
            true_button.setEnabled(true);
            false_button.setEnabled(true);
            count = (count + 1) % quizDataList.size();
            no_of_question.setText((count + 1) + "/" + quizDataList.size());
            data.setText(quizDataList.get(count).getQuestions());
        }
    }

    private void check_answer(boolean check) {

        boolean ans=quizDataList.get(count).isAnswer();
        if(check == ans)
        {
            score+=10;
            current_score.setText("Score: "+score);
            Toast.makeText(getApplicationContext(),"Correct",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Wrong",Toast.LENGTH_SHORT).show();
        }
    }

    private void api_calling() {
        StringRequest stringRequest=new StringRequest(StringRequest.Method.GET, URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            for(int i=0;i<=jsonArray.length();i++)
                            {
                                QuizData quizData=new QuizData(jsonArray.getJSONArray(i).getString(0),jsonArray.getJSONArray(i).getBoolean(1));

                                quizDataList.add(quizData);
                                data.setText(quizDataList.get(0).getQuestions());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        no_of_question.setText((count+1)+"/"+quizDataList.size());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
