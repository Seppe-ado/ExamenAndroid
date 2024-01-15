package com.example.examenandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import com.example.examenandroid.model.CountriesInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.example.examenandroid.Adapters.CountriesAdapter;
import com.example.examenandroid.viewmodel.CountriesViewModel;

public class MainActivity extends AppCompatActivity {
    private static MainActivity instance;
    private static CountriesViewModel viewModel;
    private ArrayList<CountriesInfo> dataForList= new ArrayList<>();
    private ArrayList<CountriesInfo> dataForList2= new ArrayList<>();
    public static MainActivity getInstance() {
            return instance;
    }


    //dit word gerunt wanneer de pagina aangemaakt wordt
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance=this;

       // TextView textView= findViewById(R.id.textViewtest);
        RecyclerView recyclerView= findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CountriesViewModel viewModel2= new CountriesViewModel(getApplication());
        viewModel=viewModel2;
        SearchView searchView=findViewById(R.id.searchview);




        // zware dingen mogen niet op de main thread gedaan worden dus word dit op een andere thread gedaan, dit is voor de data op te halen online en te saven in de
        //locale database
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://restcountries.com/v3.1/all?fields=name,currencies,capital,region,languages,landlocked,maps,population,timezones")
                            .get()
                            .build();
                    Response response = client.newCall(request).execute();
                    System.out.println("test");
                    String responseString = response.body().string();
                    JSONArray countriesJson= new JSONArray(responseString);
                    int length= countriesJson.length();
                    ArrayList<CountriesInfo> finalInfo= new ArrayList<>(length);
                    //loopt door elk element dat word mee gegeven en saved het dan in de ROOM database in de volgende for loop
                    for (int i=0; i <length;i++){
                        JSONObject value= countriesJson.getJSONObject(i);
                        String fullname= value.getString("name");
                        int placement= fullname.indexOf("native");
                        String test2= fullname.substring(11,placement-3);
                        int placement2= test2.indexOf(",");
                        String commonName= test2.substring(0,placement2-1);
                        String officialName = test2.substring(placement2+13);
                        CountriesInfo current= new CountriesInfo(
                                commonName,
                                officialName,
                                value.getString("currencies"),
                                value.getString("capital"),
                                value.getString("region"),
                                value.getString("languages"),
                                value.getString("landlocked"),
                                value.getString("maps"),
                                value.getInt("population"),
                                value.getString("timezones")
                        );
                        finalInfo.add(current);
                        /*
                        String test =current.getName().toString();

                        JSONArray testingArray = new JSONArray(test.substring(1));
                        int length2= testingArray.length();
                        ArrayList<String> finaltest= new ArrayList<>(length2);
                        */

                    }



                    for (int i=0;i<finalInfo.size();i++){
                        viewModel.addCountries(finalInfo.get(i));
                        dataForList.add(finalInfo.get(i));
                       CountriesInfo temp= viewModel.getCountry(finalInfo.get(i).getName());
                       dataForList2.add(temp);

                    }
                    // threads mogen niet de UI aanpassen dus met dit kan je dat wel, hier word de recyclerview gepopuleerd
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            recyclerViewUpdate(dataForList2);

                        }
                    });



                }catch (IOException e) {
                    Log.d("error","IOE");
                    throw new RuntimeException(e);

                } catch (JSONException e) {
                    Log.d("error","JSON");
                    throw new RuntimeException(e);

                }
            }
        });
        thread.start();

       /* ArrayList<CountriesInfo> dataForList= new ArrayList(250);
        List<CountriesInfo> temp= viewModel.getAllCountries();
        dataForList.addAll(temp);
        String test="";
        CountriesAdapter adapter= new CountriesAdapter(dataForList);
        recyclerView.setAdapter(adapter);
        */






        // dit is voor de search bar functie te geven, wat het doet is gewoon de landen vinden die de naam hebben en dan opniuew de recyclerview populeren
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<CountriesInfo> filter= new ArrayList<>();


                for (int i=0;i<dataForList2.size();i++){
                    if( dataForList2.get(i).getName().toLowerCase().contains(newText.toLowerCase())){
                        filter.add(dataForList2.get(i));
                    }

                }

                recyclerViewUpdate(filter);
                return false;
            }
        });
    }
    /*public void setText(final String name){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                changetext(name);
            }
        });
    }
    public void changetext(String name){
        TextView textView= findViewById(R.id.textViewtest);
        textView.setText(name);
    }*/
    // dit is de functie die word opgeroepen om de adapter aan te maken en de recyclerview te populeren
    public void recyclerViewUpdate(ArrayList<CountriesInfo> dataForList){
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        CountriesAdapter adapter= new CountriesAdapter(dataForList);
        recyclerView.setAdapter(adapter);
    }
    //deze functie is om de naam te lezen van de item waar op geclickt is en dan door te gaan naar de detailspagine
    public void goDetails(String name){

        Intent intent = new Intent(MainActivity.this,DetailsActivity.class);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CountriesInfo data = viewModel.getCountry(name);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                CountriesInfo data = viewModel.getCountry(name);



                intent.putExtra("details", data);
                startActivity(intent);

            }

        });
        thread.start();
    }
}