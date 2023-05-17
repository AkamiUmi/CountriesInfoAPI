package uz.akamiumi.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CountriesRequest {
  private Continents continents;
  private List<String> languages;
}
