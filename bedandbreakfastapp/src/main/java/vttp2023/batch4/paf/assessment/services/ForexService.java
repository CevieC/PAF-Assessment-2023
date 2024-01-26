package vttp2023.batch4.paf.assessment.services;

import java.io.StringReader;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class ForexService {

    public float convert(String from, String to, float amount) {

        String url = "https://api.frankfurter.app/latest?to=SGD,AUD";
        RestTemplate restTemplate = new RestTemplate();

        try {

            String response = restTemplate.getForObject(url, String.class);

            JsonReader jsonReader = Json.createReader(new StringReader(response));
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();

            JsonObject rates = jsonObject.getJsonObject("rates");
            float rateFrom = rates.getJsonNumber(from.toUpperCase()).bigDecimalValue().floatValue();
            float rateTo = rates.getJsonNumber(to.toUpperCase()).bigDecimalValue().floatValue();

            // Convert the amount from 'from' currency to EUR, then to 'to' currency
            float amountInEUR = amount / rateFrom;
            return amountInEUR * rateTo;

        } catch (Exception e) {

            e.printStackTrace();
            return -1000f;
		
        }
    }
}
