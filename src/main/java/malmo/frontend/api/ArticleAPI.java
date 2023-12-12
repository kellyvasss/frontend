package malmo.frontend.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import malmo.frontend.dto.Article;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArticleAPI {
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();
    public static List<Article> getArticles(String filter) throws IOException, ParseException {
        HttpGet get = new HttpGet("http://localhost:8080/articles?searchTerm=" + filter);
        CloseableHttpResponse response = httpClient.execute(get);

        if (response.getCode() != 200) {
            System.out.println("fel att hämta artiklar");
            return Collections.emptyList();
        }

        HttpEntity entity = response.getEntity();
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Article> articles = mapper.readValue(EntityUtils.toString(entity), new TypeReference<ArrayList<Article>>() {});

        return articles;

    }
}