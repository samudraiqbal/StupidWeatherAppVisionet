package id.sch.smktelkom_mlg.visionet.project.stupidweatherappvisionet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.sch.smktelkom_mlg.visionet.project.stupidweatherappvisionet.data.model.Query;
import id.sch.smktelkom_mlg.visionet.project.stupidweatherappvisionet.data.model.Weather;
import id.sch.smktelkom_mlg.visionet.project.stupidweatherappvisionet.data.remote.WeatherAPI;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.TextViewCity)
    TextView tvCity;
    @Bind(R.id.TextViewTemperature)
    TextView tvTemperature;
    @Bind(R.id.TextViewLastUpdate)
    TextView tvLastUpdate;
    @Bind(R.id.TextViewCondition)
    TextView tvCondition;
    @Bind(R.id.ButtonRefresh)
    Button bRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ButtonRefresh)
    public void onClick_button_refresh() {
        WeatherAPI.Factory.getIstance().getWeather().enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Response<Weather> response) {
                Query query = response.body().getQuery();
                tvTemperature.setText(query.getResults().getChannel().getItem().getCondition().getTemp());
                tvCity.setText(query.getResults().getChannel().getLocation().getCity());
                tvLastUpdate.setText(query.getResults().getChannel().getLastBuildDate());
                tvCondition.setText(query.getResults().getChannel().getItem().getCondition().getText());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("Failed", t.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        onClick_button_refresh();
    }
}
