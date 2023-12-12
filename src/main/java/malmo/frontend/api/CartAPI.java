package malmo.frontend.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import malmo.frontend.dto.Cart;
import malmo.frontend.dto.CartItem;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.validation.ObjectError;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartAPI {
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();
    private static String baseURL = "http://localhost:8080/Shoppingcart";

    public static List<Cart> getCart() {
        HttpGet get = new HttpGet(baseURL + "/cart");
        get.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + LoginAPI.getJwt());
        try {
            CloseableHttpResponse response = httpClient.execute(get);

            if (response.getCode() != 200) {
                System.out.println("misslyckad med get artiklar " + response.getCode());
            }
            HttpEntity entity = response.getEntity();
            ObjectMapper mapper = new ObjectMapper();

            ArrayList<Cart> cartDetails = mapper.readValue(EntityUtils.toString(entity), new TypeReference<ArrayList<Cart>>() {});
            return cartDetails;
        } catch (IOException e) {
            System.out.println("IO exeption" + e.getMessage());
        } catch (ParseException e) {
            System.out.println("Parse exeption " + e.getMessage());
        } return Collections.emptyList();
    }
    public static void addToCart(CartItem item) throws IOException, ParseException {
        HttpPost post = new HttpPost(baseURL);
        ObjectMapper mapper = new ObjectMapper();
       // StringEntity payload = new StringEntity(mapper.writeValueAsString(item), ContentType.APPLICATION_JSON);
       //post.setEntity(payload);
        post.setEntity(Payload.createPayload(item));
        post.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + LoginAPI.getJwt());

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getCode() != 200) {
            System.out.println("fel med att add to cart " + response.getCode());
            return;
        }
        HttpEntity entity = response.getEntity();
        Cart cart = mapper.readValue(EntityUtils.toString(entity), new TypeReference<Cart>() {});

        if (cart.getArticle().getName().equals(item.name()) && cart.getQuantity() == item.quantity()) {
            System.out.println("succé!");
        } else System.out.println("inte succes med addToCart.");

    }
    public static void buy() throws IOException {
        HttpPost post = new HttpPost(baseURL + "/checkout");
        post.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + LoginAPI.getJwt());
        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getCode() != 200) {
            System.out.println("fel med att köpa");
        } else System.out.println("lyckat köp!");
    }

    public static void updateCart(CartItem item) throws IOException, ParseException {
        HttpPut put = new HttpPut(baseURL);
        ObjectMapper mapper = new ObjectMapper();
        StringEntity payload = new StringEntity(mapper.writeValueAsString(item), ContentType.APPLICATION_JSON);
        put.setEntity(payload);
        put.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + LoginAPI.getJwt());

        CloseableHttpResponse response = httpClient.execute(put);
        if (response.getCode() != 200) {
            System.out.println("fel med att add to cart" );
            return;
        }
        HttpEntity entity = response.getEntity();
        Cart cart = mapper.readValue(EntityUtils.toString(entity), new TypeReference<Cart>() {});

        if (cart.getArticle().getName().equals(item.name()) && cart.getQuantity() == item.quantity()) { // ksk mst ändra denna med qty...
            System.out.println("succé!");
        } else System.out.println("inte succes med addToCart."); // kolla igenom

    }
    public static void deleteItemFromCart(String articleName) throws IOException {
        HttpDelete delete = new HttpDelete(baseURL +"/?articleName=" + articleName);
        ObjectMapper mapper = new ObjectMapper();
        delete.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + LoginAPI.getJwt());

        CloseableHttpResponse response = httpClient.execute(delete);
        if (response.getCode() != 200) {
            System.out.println("fel med att radera");
            return;
        }
        System.out.println("lyckad radering av " + articleName);
    }
    public static void deleteWholeCart() throws IOException {
        HttpDelete delete = new HttpDelete(baseURL);
        delete.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + LoginAPI.getJwt());
        CloseableHttpResponse response = httpClient.execute(delete);
        if (response.getCode() != 200) {
            System.out.println("fel att radera hela kundkorg");
        } else System.out.println("lyckad radering av kundkorg");
    }

}
