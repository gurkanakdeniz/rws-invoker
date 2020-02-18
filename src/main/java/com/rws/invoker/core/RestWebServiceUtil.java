package com.rws.invoker.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rws.invoker.annotation.PathVariable;
import com.rws.invoker.annotation.RequestParam;
import com.rws.invoker.annotation.RestWebServiceType;
import com.rws.invoker.model.RestWebServiceBaseResponse;
import com.rws.invoker.model.RestWebServiceEndpoint;
import com.rws.invoker.model.RestWebServiceMethod;

public class RestWebServiceUtil {

    static Logger logger = LoggerFactory.getLogger(RestWebServiceUtil.class);

    public static String getUrl(String url, Object arg) {
        String response = url + "?";
        if (arg != null) {
            Class<?> cl = arg.getClass();
            if (cl != null) {
                Field[] fields = cl.getDeclaredFields();
                if (fields != null && fields.length > 0) {
                    for (Field field : fields) {
                        response = prepareField(arg, response, field);
                    }
                }
            }
        }

        return response.replaceAll("\\?$", "");
    }

    private static String prepareField(Object arg, String response, Field field) {
        field.setAccessible(true);
        Object fieldValue = null;

        try {
            fieldValue = field.get(arg);
        } catch (Exception e) {
            logger.error("Request Field Value Error : ", e);
        }

        if (fieldValue != null) {
            if (field.isAnnotationPresent(PathVariable.class)) {
                response = preparePathVariable(response, field, fieldValue);
            }

            if (field.isAnnotationPresent(RequestParam.class)) {
                response = prepareRequestParam(response, field, fieldValue);
            }
        }

        return response;
    }

    public static String preparePathVariable(String url, Field field, Object fieldValue) {
        PathVariable pathVariable = field.getAnnotation(PathVariable.class);

        String name = pathVariable.name();
        if (StringUtils.isBlank(name)) {
            name = field.getName();
        }

        return RegExUtils.replaceAll(url, ":" + name, check(fieldValue, pathVariable));
    }

    public static String prepareRequestParam(String url, Field field, Object fieldValue) {
        RequestParam requestParam = field.getAnnotation(RequestParam.class);

        String name = requestParam.name();
        if (StringUtils.isBlank(name)) {
            name = field.getName();
        }

        String value = check(fieldValue, requestParam);
        String lastChar = getLastChar(url);
        if (!("?".equals(lastChar) || "&".equals(lastChar))) {
            url += "&";
        }

        return url + name + "=" + value;
    }

    public static String check(Object fieldValue, PathVariable pathVariable) {
        String value = "";

        if (fieldValue instanceof Date) {
            value = new SimpleDateFormat(pathVariable.dateFormat()).format((Date) fieldValue);
        } else {
            value = String.valueOf(fieldValue);
        }

        return value;
    }

    public static String check(Object fieldValue, RequestParam pathVariable) {
        String value = "";

        if (fieldValue instanceof Date) {
            value = new SimpleDateFormat(pathVariable.dateFormat()).format((Date) fieldValue);
        } else {
            value = String.valueOf(fieldValue);
        }

        return value;
    }

    public static String getLastChar(String value) {
        return value != null ? value.substring(value.length() - 1) : value;
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
    
    public static Object prepareResponse(Object obj, String status, String statusCode) {
        
        if (obj != null) {
            if (obj instanceof RestWebServiceBaseResponse) {
                try {
                    ((RestWebServiceBaseResponse) obj).setInvokeStatus(status);
                    ((RestWebServiceBaseResponse) obj).setInvokeStatusCode(statusCode);
                } catch (Throwable e) {
                    logger.error("Rest Web Service Base Response Error : ", e);
                }
            }
        }
        
        return obj;
    }
    

}
