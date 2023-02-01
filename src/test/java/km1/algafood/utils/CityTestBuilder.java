package km1.algafood.utils;

import km1.algafood.api.models.CityInput;
import km1.algafood.api.models.CityModel;
import km1.algafood.api.models.CitySummary;
import km1.algafood.api.models.StateModel;
import km1.algafood.api.models.StateIdImput;
import km1.algafood.domain.models.City;
import km1.algafood.domain.models.State;

public class CityTestBuilder {
  private City city =
      City.builder().name("São Paulo").state(State.builder().id(1l).build()).build();

  public static CityTestBuilder aCity() {
    return new CityTestBuilder();
  }

  public CityTestBuilder withValidId() {
    this.city.setId(1l);
    return this;
  }

  public CityTestBuilder withNullName() {
    this.city.setName(null);
    return this;
  }

  public City build() {
    return this.city;
  }

  public CityInput buildInput() {
    return CityInput.builder()
        .name(this.city.getName())
        .state(StateIdImput.builder().id(this.city.getState().getId()).build())
        .build();
  }

  public CityModel buildModel() {
    return CityModel.builder()
        .name(this.city.getName())
        .state(StateModel.builder().name(this.city.getState().getName()).build())
        .build();
  }

  public CitySummary buildSummary() {
    return CitySummary.builder()
        .name(this.city.getName())
        .state(this.city.getState().getName())
        .build();
  }
}
