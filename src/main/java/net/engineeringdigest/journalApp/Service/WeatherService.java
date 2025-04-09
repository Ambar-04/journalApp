package net.engineeringdigest.journalApp.Service;
import net.engineeringdigest.journalApp.api.Response.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;
    public WeatherResponse getWeather(String city){
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);//1st param is key, 2nd param is in which class we want to convert

        if (weatherResponse != null){ //if it's in Redis cache
            return weatherResponse;
        }else { // if not in cache
            String  finalApi = appCache.APP_CACHE.get("weather_api") //till this we will get the api
                    .replace("<apiKey>", apiKey).replace("<city>" , city);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if (body != null){ // check if we get any response or not
                // if not set in redis cache then we are setting it here for upcoming further requests
                redisService.set("weather_of_"+city, body, 3000l);
            }
            return body; // This will return obj of WeatherResponse, not current temperature
        }

    }
}
