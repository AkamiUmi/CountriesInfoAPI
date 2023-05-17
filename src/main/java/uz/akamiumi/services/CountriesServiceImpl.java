package uz.akamiumi.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.akamiumi.models.Continents;
import uz.akamiumi.models.CountriesResponse;

import java.util.*;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
@Slf4j
public class CountriesServiceImpl implements CountriesService{

    private final RestTemplate restTemplate;


    public CountriesServiceImpl(@Qualifier("rest") RestTemplate restTemplate) {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        this.restTemplate = restTemplate;

    }


    private final ObjectMapper objectMapper = new ObjectMapper();
    static HttpHeaders headers = new HttpHeaders();

    {

    }

    @Value("${countries.url}")
    private String url;

    static  {
        headers.setContentType(MediaType.APPLICATION_JSON);
    }


    @Override
    public List<CountriesResponse> getCountries(Continents continent) {
        log.info("Request is getCountries() with value = " + continent);

        List<CountriesResponse> countries = new ArrayList<>();

        try {
            var result = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    });



            List<CountriesResponse> mappedResult = result
                    .getBody()
                    .values()
                    .stream()
                    .map(o -> objectMapper.convertValue(o , CountriesResponse.class))
                    .collect(Collectors.toList());

                    for(CountriesResponse country: mappedResult) {
                        if (Objects.equals(country.getContinent(), continent)) {
                            countries.add(country);
                            log.info("Result is " + country.getName());
                        }

                    }
            return countries;

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

    }
    @Override
    public List<CountriesResponse> getCountriesByLang(String language) {
        log.info("Request is getCountriesByLang() with value = " + language);
        List<CountriesResponse> countries = new ArrayList<>();
        try {
            var result = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    });


            List<CountriesResponse> mappedResult = result
                    .getBody()
                    .values()
                    .stream()
                    .map(o -> objectMapper.convertValue(o , CountriesResponse.class))
                    .collect(Collectors.toList());

            for (CountriesResponse country: mappedResult) {
                if (country.getLanguages().contains(language)) {
                    countries.add(country);
                    log.info("Response is getCountriesByLang() " + country.getName());
                }
            }

            return countries;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    @Override
    public List<CountriesResponse> allCountries() {
        try {
            ResponseEntity<List<CountriesResponse>> result = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(headers),
                    new ParameterizedTypeReference<List<CountriesResponse>>() {}
            );
            log.info("Result AllCountries() is " + result.getBody());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
