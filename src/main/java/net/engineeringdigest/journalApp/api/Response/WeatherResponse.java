package net.engineeringdigest.journalApp.api.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Getter
@Setter
public class WeatherResponse {

    public Current current;

    @Getter
    @Setter
    public class Current{

        public int temperature;

        @JsonProperty("weather_descriptions")
        public ArrayList<String> weather_descriptions;

        public int feelslike;

    }





}
