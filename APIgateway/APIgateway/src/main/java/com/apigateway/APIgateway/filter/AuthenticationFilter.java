package com.apigateway.APIgateway.filter;

import com.apigateway.APIgateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
           if (routeValidator.isSecured.test(exchange.getRequest())){
               //header contains token or not
               if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                   throw new RuntimeException("missing authorization header");
               }

               String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if(authHeader!=null && authHeader.startsWith("Bearer ")){
                    authHeader = authHeader.substring(7);
                }
                try{
//                    //rest call to auth service
//                    String forObject = restTemplate.getForObject("http://IDENTITY-SERVICE/validate?token" + authHeader, String.class);
                        jwtUtil.validateToken(authHeader);
                }catch (Exception ex){
                    System.out.println("invalid access ....");
                    throw new RuntimeException("Un-authorized access to applicaiton");
                }
           }
            return chain.filter(exchange);
        });
    }

    public static class Config{

    }
}
