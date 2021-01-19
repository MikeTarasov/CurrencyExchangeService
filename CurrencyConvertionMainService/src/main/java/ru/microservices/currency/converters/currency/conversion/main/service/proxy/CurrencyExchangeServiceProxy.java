package ru.microservices.currency.converters.currency.conversion.main.service.proxy;

//import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.microservices.currency.converters.currency.conversion.main.service.model.CurrencyConversionBean;

@FeignClient(name = "exchange-service", url = "${host.exchange.service}")
//@RibbonClient(name = "exchange-service")
public interface CurrencyExchangeServiceProxy {
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    CurrencyConversionBean retrieveExchangeValue
            (@PathVariable("from") String from, @PathVariable("to") String to);
}