package malmo.frontend.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import malmo.frontend.dto.Cart;
import malmo.frontend.dto.History;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryAPI {
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    private static String baseURL = "http://localhost:8080/history";
    public static List<History> getHistoryForUser() {
        HttpGet get = new HttpGet(baseURL + "/user");
        get.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + LoginAPI.getJwt());
        try {
            CloseableHttpResponse response = httpClient.execute(get);

            if (response.getCode() != 200) {
                System.out.println("misslyckad med get history " + response.getCode());
            }
            HttpEntity entity = response.getEntity();
            ObjectMapper mapper = new ObjectMapper();

            ArrayList<History> histories = mapper.readValue(EntityUtils.toString(entity), new TypeReference<ArrayList<History>>() {});
            return histories;
        } catch (IOException e) {
            System.out.println("IO exeption" + e.getMessage());
        } catch (ParseException e) {
            System.out.println("Parse exeption " + e.getMessage());
        } return Collections.emptyList();
    }
    public static List<History> getHistoryByArticle(String articlename) {
        HttpGet get = new HttpGet(baseURL + "/article?articleName=" + articlename);
        get.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + LoginAPI.getJwt());
        try {
            CloseableHttpResponse response = httpClient.execute(get);

            if (response.getCode() != 200) {
                System.out.println("misslyckad med get historyByArticle " + response.getCode());
            }
            HttpEntity entity = response.getEntity();
            ObjectMapper mapper = new ObjectMapper();

            ArrayList<History> histories = mapper.readValue(EntityUtils.toString(entity), new TypeReference<ArrayList<History>>() {});
            return histories;
        } catch (IOException e) {
            System.out.println("IO exeption" + e.getMessage());
        } catch (ParseException e) {
            System.out.println("Parse exeption " + e.getMessage());
        } return Collections.emptyList();
    }

}
