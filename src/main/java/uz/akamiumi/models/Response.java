package uz.akamiumi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class Response<T> {
    private T data;
    private String message;
    private HttpStatus status;

    public static <T> Response<T> ok(T data) {
        return new Response<>(data, "", HttpStatus.OK);
    }

    public Response(T data, String message, HttpStatus status) {
        this.data = data;
        this.status = HttpStatus.OK;
        this.message = "";

    }
}
