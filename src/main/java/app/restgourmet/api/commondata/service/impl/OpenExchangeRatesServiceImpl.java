package app.restgourmet.api.commondata.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpServerErrorException.BadGateway;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import app.restgourmet.api.commondata.dto.exchange.ExchangeRateDto;
import app.restgourmet.api.commondata.dto.exchange.OpenExchangeRatesResponseDto;
import app.restgourmet.api.commondata.models.Currency;
import app.restgourmet.api.commondata.service.spec.ExchangeRateService;
import app.restgourmet.api.shared.exceptions.InvalidSecretException;
import app.restgourmet.api.shared.exceptions.ResourceNotFoundException;
import app.restgourmet.api.utils.AppConstants;
import app.restgourmet.api.utils.AppConstants.ErrorMessages;

@Service
public class OpenExchangeRatesServiceImpl implements ExchangeRateService {

  private final RestTemplate restTemplate;
  private static final Logger log = LoggerFactory.getLogger(OpenExchangeRatesServiceImpl.class);

  @Value("${OPEN_EXCHANGE_RATES_APP_ID}")
  private String appId;

  public OpenExchangeRatesServiceImpl(
      RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  @SuppressWarnings("null")  // TODO: remove?
  public ExchangeRateDto getRate(Currency from, Currency to) {
    if (!StringUtils.hasText(appId)) {
      throw new InvalidSecretException();
    }

    // TODO: validate when limit is exceeded
    
    try {
      OpenExchangeRatesResponseDto response = restTemplate.getForObject(
          AppConstants.ExternalServices.Urls.OPEN_EXCHANGE_RATES_CONVERT,
          OpenExchangeRatesResponseDto.class, 1, from.getCode(), to.getCode(), appId);
      return response != null ? response.toExchangeRateDto(from, to) : null;

    } catch (HttpClientErrorException.BadRequest ex) {

      // Specific handling for 400
      if (ex.getMessage() == "invalid_currency") {
        log.error("From currency and/or to currency not found ({}/{})", from.getCode(), to.getCode());
        throw new ResourceNotFoundException(ErrorMessages.CURRENCY_NOT_FOUND);
      }

    } catch (HttpServerErrorException ex) {

      // Handle 5xx errors
      log.error("Server error from openexchangerates.org when fetching rate for currencies {}/{}: Status {}",
          from.getCode(), to.getCode(), ex.getStatusCode());
      throw BadGateway.create(HttpStatus.BAD_GATEWAY, ex.getStatusCode().toString(),
          null, null, null);

    } catch (RestClientException ex) {

      // Handle other REST client exceptions (timeouts, etc.)
      log.error("Communication error with openexchangerates.org service for currencies {}/{}: {}", from.getCode(),
          to.getCode(), ex.getMessage());
      throw BadGateway.create(HttpStatus.SERVICE_UNAVAILABLE,
          "", null, null, null);
    }
    
    return null;
  }
}
