package uz.akamiumi.services;

import uz.akamiumi.models.Continents;
import uz.akamiumi.models.CountriesResponse;

import java.util.List;

public interface CountriesService {
    List<CountriesResponse> getCountries(Continents request);
    List<CountriesResponse> allCountries();
    List<CountriesResponse>getCountriesByLang(String language);
}
