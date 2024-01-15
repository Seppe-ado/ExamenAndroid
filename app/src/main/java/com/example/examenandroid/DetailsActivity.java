package com.example.examenandroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.examenandroid.model.CountriesInfo;

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity {
    TextView tName;
    TextView tOfficial;
    TextView tCap;
    TextView tRegion;
    TextView tTime;
    TextView tLandLocked;
    TextView tGoogleMaps;
    TextView tPop;
    TextView tLang;
    TextView tCurrency;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_detail);
        Intent intent = getIntent();

        CountriesInfo data= (CountriesInfo)intent.getSerializableExtra("details");

        tName= findViewById(R.id.textViewcommon);
        tName.setText(data.getName());
        tOfficial=findViewById(R.id.textViewOfficial);
        tOfficial.setText(data.getOfficialName());
        tCap=findViewById(R.id.textViewCapital);
        String capital= data.getCapital().substring(2,data.getCapital().length()-2);
        tCap.setText(capital);
        tRegion=findViewById(R.id.textViewRegions);
        String region= "Region: "+data.getRegion();
        tRegion.setText(region);
        tTime= findViewById(R.id.textViewTime);
        String timezone="Timezone(s): ";
        if (data.getTimezone().contains(",")){
          String temp= data.getTimezone();
          temp= temp.replace('"',' ');
          temp=temp.substring(1,temp.length()-1);
          timezone+=temp;
        }else {
             timezone+= data.getTimezone().substring(2,data.getTimezone().length()-2);
        }
        tTime.setText(timezone);
        tLandLocked=findViewById(R.id.textViewLandLocked);
        String landlocked= "Landlocked: "+data.getLandlocked();
        tLandLocked.setText(landlocked);
        tGoogleMaps=findViewById(R.id.textViewGoogleMaps);
        String maps= data.getMaps();
        int placementM= maps.indexOf("openStreetMaps");
        maps= maps.substring(15,placementM-3);
        maps=maps.replace("\\","");

        tGoogleMaps.setText(maps);
        tPop=findViewById(R.id.textViewPop);
        String pop= "Population: "+String.valueOf(data.getPopulation());
        tPop.setText(pop);
        tLang= findViewById(R.id.textViewLanguages);
        String languages= "Language(s): ";
        if (data.getLanguages().contains(",")){
            String arrayLang[]= data.getLanguages().split(",");
            for (int i=0;i<arrayLang.length;i++){
                int placementL= arrayLang[i].indexOf(":");

                if (i==arrayLang.length-1){
                    arrayLang[i]= arrayLang[i].substring(placementL+2,arrayLang[i].length()-2);
                    languages += arrayLang[i];
                }else {
                    arrayLang[i]= arrayLang[i].substring(placementL+2,arrayLang[i].length()-1);
                    languages += arrayLang[i] + ", ";
                }
            }
        }else {
            String temp= data.getLanguages();
            int placementL= temp.indexOf(":");
            temp= temp.substring(placementL+2,temp.length()-2);
            languages+=temp;
        }
        tLang.setText(languages);
        tCurrency=findViewById(R.id.textViewCurrency);
        String currency = "Currency: ";
        String temp= data.getCurrencies();
        int placementC= temp.indexOf("name");
        int placementC2= temp.indexOf("symbol");
        String cName= temp.substring(placementC+7,placementC2-3);
        String cSymbol= temp.substring(placementC2+9,temp.length()-3);
        currency+= cName+" ("+cSymbol+")";
        tCurrency.setText(currency);

        Button button;
        button = findViewById(R.id.buttonReturn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tGoogleMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string= data.getMaps().substring(15,placementM-3);
                string=string.replace("\\","");
                Uri link= Uri.parse(string);
                Intent intents = new Intent(Intent.ACTION_VIEW,link);
                //if (intents.resolveActivity(getPackageManager())!=null){
                    startActivity(intents);
                //}
            }
        });





    }
}
