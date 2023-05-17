package uz.akamiumi.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import uz.akamiumi.models.Continents;
import uz.akamiumi.models.CountriesResponse;
import uz.akamiumi.models.Response;
import uz.akamiumi.services.CountriesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CountriesController {
    private final CountriesService countriesService;
    private final ApplicationContext context;
    @GetMapping("countries/continent/{continent}")
    public Response<List<CountriesResponse>> getCountries(@PathVariable Continents continent) {
        return  Response.ok(countriesService.getCountries(continent));
    }

    @GetMapping("countries/languages/{lang}")
    public Response<List<CountriesResponse>> getCountriesByLang(@PathVariable String lang) {
        return  Response.ok(countriesService.getCountriesByLang(lang));
    }

}
