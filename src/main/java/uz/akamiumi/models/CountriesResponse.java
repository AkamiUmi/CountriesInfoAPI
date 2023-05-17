package uz.akamiumi.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountriesResponse {
    private Continents continent;
    private String name;
    @JsonAlias(value = {"native", "fNative"})
    private String fNative;
    private List<String> phone;
    private String capital;
    private List<String> currency;
    private List<String> languages;
}
