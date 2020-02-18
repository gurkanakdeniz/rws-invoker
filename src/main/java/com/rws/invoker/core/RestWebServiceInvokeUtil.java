package com.rws.invoker.core;

import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.rws.invoker.model.RestWebServiceInvokeResponse;

public class RestWebServiceInvokeUtil {

    public static RestWebServiceInvokeResponse get(String url, int connectionTimeout, int readTimeout)
            throws Exception {
        RestWebServiceInvokeResponse response = new RestWebServiceInvokeResponse();

        CloseableHttpClient httpClient = client(connectionTimeout, readTimeout);
        HttpGet request = new HttpGet(url);
        try (CloseableHttpResponse r = httpClient.execute(request)) {
            StatusLine statusLine = r.getStatusLine();
            response.setStatusCode(statusLine.getStatusCode());
            response.setStatus(statusLine.toString());
            response.setResponse(EntityUtils.toString(r.getEntity()));
        }

        return response;
    }

    public static RestWebServiceInvokeResponse patch(String url, String requestJson, int connectionTimeout,
            int readTimeout) throws Exception {
        RestWebServiceInvokeResponse response = new RestWebServiceInvokeResponse();

        CloseableHttpClient httpClient = client(connectionTimeout, readTimeout);
        HttpPatch request = new HttpPatch(url);
        StringEntity requestEntity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
        request.setEntity(requestEntity);
        try (CloseableHttpResponse r = httpClient.execute(request)) {
            StatusLine statusLine = r.getStatusLine();
            response.setStatusCode(statusLine.getStatusCode());
            response.setStatus(statusLine.toString());
            response.setResponse(EntityUtils.toString(r.getEntity()));
        }

        return response;
    }

    public static RestWebServiceInvokeResponse post(String url, String requestJson, int connectionTimeout,
            int readTimeout) throws Exception {
        RestWebServiceInvokeResponse response = new RestWebServiceInvokeResponse();

        CloseableHttpClient httpClient = client(connectionTimeout, readTimeout);
        HttpPost request = new HttpPost(url);
        StringEntity requestEntity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
        request.setEntity(requestEntity);
        try (CloseableHttpResponse r = httpClient.execute(request)) {
            StatusLine statusLine = r.getStatusLine();
            response.setStatusCode(statusLine.getStatusCode());
            response.setStatus(statusLine.toString());
            response.setResponse(EntityUtils.toString(r.getEntity()));
        }

        return response;
    }

    public static RestWebServiceInvokeResponse put(String url, String requestJson, int connectionTimeout,
            int readTimeout) throws Exception {
        RestWebServiceInvokeResponse response = new RestWebServiceInvokeResponse();

        CloseableHttpClient httpClient = client(connectionTimeout, readTimeout);
        HttpPut request = new HttpPut(url);
        StringEntity requestEntity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
        request.setEntity(requestEntity);
        try (CloseableHttpResponse r = httpClient.execute(request)) {
            StatusLine statusLine = r.getStatusLine();
            response.setStatusCode(statusLine.getStatusCode());
            response.setStatus(statusLine.toString());
            response.setResponse(EntityUtils.toString(r.getEntity()));
        }

        return response;
    }

    public static CloseableHttpClient client(int connectionTimeout, int readTimeout) {
        return HttpClientBuilder.create().setDefaultRequestConfig(config(connectionTimeout, readTimeout)).build();
    }

    public static RequestConfig config(int connectionTimeout, int readTimeout) {
        return RequestConfig.custom().setConnectTimeout(connectionTimeout)
                .setConnectionRequestTimeout(connectionTimeout).setSocketTimeout(readTimeout).build();
    }

}
