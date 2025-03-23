package app.restgourmet.api.masterdata.dto.address;

import app.restgourmet.api.utils.AppConstants;
import lombok.Data;

@Data
public class ViaCepAddressDto {
  private String cep;
  private String logradouro;
  private String complemento;
  private String unidade;
  private String bairro;
  private String localidade;
  private String uf;
  private String estado;
  private String regiao;
  private String ibge;
  private String gia;
  private String ddd;
  private String siafi;

  public AddressDto toAddressDto() {
    AddressDto addressDto = new AddressDto();
    addressDto.setStreet(logradouro);
    addressDto.setCity(localidade);
    addressDto.setState(uf);
    addressDto.setZipCode(cep);
    addressDto.setCountry(AppConstants.Codes.BRAZIL_COUNTRY_CODE);

    return addressDto;
  }
}

// expected response from ViaCep API
// {
//   "cep": "01001-000",
//   "logradouro": "Praça da Sé",
//   "complemento": "lado ímpar",
//   "unidade": "",
//   "bairro": "Sé",
//   "localidade": "São Paulo",
//   "uf": "SP",
//   "estado": "São Paulo",
//   "regiao": "Sudeste",
//   "ibge": "3550308",
//   "gia": "1004",
//   "ddd": "11",
//   "siafi": "7107"
// }
