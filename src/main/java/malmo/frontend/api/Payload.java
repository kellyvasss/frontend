package malmo.frontend.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;

public class Payload {
    public static StringEntity createPayload(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        StringEntity payload = new StringEntity(mapper.writeValueAsString(object), ContentType.APPLICATION_JSON);
        return payload;
    }
}
