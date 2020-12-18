package ru.microservices.currency.converters.currency.conversion.main.service.proxy;

import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ProxyApi {

    private final ZuulProperties properties;

    public ProxyApi(ZuulProperties properties) {
        this.properties = properties;
    }

    @Primary
    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider() {
        return () -> {
            List<SwaggerResource> resources = new ArrayList<>();
            properties.getRoutes().values()
                    .forEach(route -> resources.add(createResource(route.getServiceId(), route.getId())));
            return resources;
        };
    }

    private SwaggerResource createResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation("/" + location + "/v2/api-docs");
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }

}