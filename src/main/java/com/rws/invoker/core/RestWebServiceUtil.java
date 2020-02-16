package com.rws.invoker.core;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import com.rws.invoker.annotation.RestWebServiceType;
import com.rws.invoker.model.RestWebServiceEndpoint;
import com.rws.invoker.model.RestWebServiceMethod;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

public class RestWebServiceUtil {

    public static WebClient getWebClient(String url, int connectionTimeout, int readTimeout) {
        TcpClient timeoutClient = TcpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout)
                .doOnConnected(c -> c.addHandlerLast(new ReadTimeoutHandler(readTimeout))
                        .addHandlerLast(new WriteTimeoutHandler(readTimeout)));

        WebClient webClient = WebClient.builder().baseUrl(url)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(timeoutClient)))
//                    .defaultHeader(HttpHeaders.CONTENT_TYPE, OMDB_MIME_TYPE)
//                    .defaultHeader(HttpHeaders.USER_AGENT, USER_AGENT)
                .build();
        return webClient;
    }

    public static RestWebServiceMethod getMethod(Method m) {
        RestWebServiceType[] methodType = m.getAnnotationsByType(RestWebServiceType.class);

        RestWebServiceMethod method = RestWebServiceMethod.GET;
        if (methodType != null && methodType.length > 0) {
            method = methodType[0].value();
        }

        return method;
    }

    public static RestWebServiceEndpoint getEndpoint(Method m, HashMap<String, RestWebServiceEndpoint> endpoints,
            RestWebServiceMethod methodType) {
        RestWebServiceEndpoint enpoint = null;
        if (endpoints != null && endpoints.size() > 0) {
            enpoint = endpoints.get(
                    RestWebServiceEndpoint.getEndpointKey(m.getDeclaringClass().getName(), m.getName(), methodType));
        }

        return enpoint;
    }

}
