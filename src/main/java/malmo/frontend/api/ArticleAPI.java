package malmo.frontend.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import malmo.frontend.dto.Article;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
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

import static malmo.frontend.api.LoginAPI.*;
import static malmo.frontend.api.Payload.createPayload;

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
    public static void createArticle(Article article) throws IOException {
        HttpPost post = new HttpPost("http://localhost:8080/articles/create");
        post.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getJwt());
        post.setEntity(createPayload(article));

        CloseableHttpResponse response = httpClient.execute(post);

        if(response.getCode() != 200) {
            System.out.println("fel att lägga till artikel " + response.getCode());

        }
    }
    public static void deleteArticle(Article article) throws IOException {
        HttpDelete delete = new HttpDelete("http://localhost/articles/delete");
        delete.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getJwt());
        delete.setEntity(createPayload(article));

        CloseableHttpResponse response = httpClient.execute(delete);

        if (response.getCode() != 200) {
            System.out.println("fel med att radera artikel " + response.getCode());
        }

    }
}
