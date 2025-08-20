package com.demo.poc.commons.core.restclient;

import com.demo.poc.commons.core.properties.ConfigurationBaseProperties;
import com.demo.poc.commons.core.properties.restclient.HeaderTemplate;
import com.demo.poc.commons.core.restclient.error.RestClientErrorHandler;
import com.demo.poc.commons.core.restclient.utils.HttpHeadersFiller;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor
public class RestClientTemplate {

  private final RestTemplate restTemplate;
  private final ConfigurationBaseProperties properties;
  private final RestClientErrorHandler restClientErrorHandler;

  public RequestSpec service(String serviceName) {
    Objects.requireNonNull(serviceName, "REST client name cannot be null");
    HeaderTemplate headerTemplate = Optional.ofNullable(properties.getRestClients())
        .map(restClientMap -> restClientMap.get(serviceName))
        .map(restClient -> restClient.getRequest().getHeaders())
        .orElseGet(HeaderTemplate::new);
    return new RequestSpec(serviceName, headerTemplate);
  }

  public final class RequestSpec {
    private final String serviceName;
    private final HeaderTemplate headerTemplate;

    private HttpMethod method;
    private String url;
    private Map<String, ?> uriVariables;
    private Function<UriBuilder, URI> uriFn;
    private Map<String, String> httpHeaders = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private Object body;

    private RequestSpec(String serviceName, HeaderTemplate headerTemplate) {
      this.serviceName = serviceName;
      this.headerTemplate = headerTemplate;
    }

    public RequestSpec get() {
      this.method = HttpMethod.GET;
      return this;
    }

    public RequestSpec post() {
      this.method = HttpMethod.POST;
      return this;
    }

    public RequestSpec put() {
      this.method = HttpMethod.PUT;
      return this;
    }

    public RequestSpec patch() {
      this.method = HttpMethod.PATCH;
      return this;
    }

    public RequestSpec delete() {
      this.method = HttpMethod.DELETE;
      return this;
    }

    public RequestSpec method(HttpMethod method) {
      this.method = Objects.requireNonNull(method, "HTTP method cannot be null");
      return this;
    }

    public RequestSpec uri(String url) {
      this.url = Objects.requireNonNull(url, "URL cannot be null");
      this.uriVariables = null;
      this.uriFn = null;
      return this;
    }

    public RequestSpec uri(String url, Map<String, ?> vars) {
      this.url = Objects.requireNonNull(url, "URL cannot be null");
      this.uriVariables = Optional.ofNullable(vars).orElseGet(Collections::emptyMap);
      this.uriFn = null;
      return this;
    }

    public RequestSpec uri(Function<UriBuilder, URI> uriFunction) {
      this.uriFn = Objects.requireNonNull(uriFunction, "Function cannot be null");
      this.url = null;
      this.uriVariables = null;
      return this;
    }

    public RequestSpec headers(Map<String, String> headers) {
      this.httpHeaders = headers;
      return this;
    }

    public RequestSpec headers(Consumer<Map<String, String>> headersConsumer) {
      Objects.requireNonNull(headersConsumer).accept(this.httpHeaders);
      return this;
    }

    public RequestSpec body(Object body) {
      this.body = body;
      return this;
    }

    public <O> O retrieve(Class<O> responseClass, Class<?> errorWrapperClass) {
      ResponseEntity<O> entity = exchange(responseClass, errorWrapperClass);
      return entity.getBody();
    }

    public <O> ResponseEntity<O> exchange(Class<O> responseClass, Class<?> errorWrapperClass) {
      Objects.requireNonNull(this.method, "HTTP method cannot be null");
      HttpHeaders httpHeaders = HttpHeadersFiller.generateHeaders(headerTemplate, this.httpHeaders);

      if (!httpHeaders.containsKey(HttpHeaders.CONTENT_TYPE)) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
      }
      HttpEntity<?> httpEntity = new HttpEntity<>(this.body, httpHeaders);
      URI uri = buildUri();
      try {
        return restTemplate.exchange(uri, method, httpEntity, responseClass);
      } catch (HttpStatusCodeException ex) {
        throw restClientErrorHandler.handleError(ex, errorWrapperClass, serviceName);
      }
    }

    private URI buildUri() {
      if (Objects.nonNull(uriFn)) {
        UriBuilder uriBuilder = UriComponentsBuilder.newInstance();
        return uriFn.apply(uriBuilder);
      }

      Objects.requireNonNull(url, "URL cannot be null");
      return Optional.ofNullable(uriVariables)
          .filter(variablesMap -> !variablesMap.isEmpty())
          .map(vars -> UriComponentsBuilder.fromUriString(url).buildAndExpand(vars).encode().toUri())
          .orElseGet(() -> UriComponentsBuilder.fromUriString(url).build().encode().toUri());
    }
  }

  public static Function<UriBuilder, URI> uri(String base,
                                              Map<String, ?> pathVars,
                                              Map<String, ?> queries) {
    return ignored -> {
      UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(base);

      Optional.ofNullable(queries)
          .ifPresent(queryMap -> queryMap.forEach((k, v) -> builder.queryParam(k, toQueryValues(v))));

      return Optional.ofNullable(pathVars)
          .filter(pathMap -> !pathMap.isEmpty())
          .map(p -> builder.buildAndExpand(p).encode().toUri())
          .orElseGet(() -> builder.build().encode().toUri());
    };
  }

  private static Object[] toQueryValues(Object v) {
    if (Objects.isNull(v)) return new Object[]{StringUtils.EMPTY};
    if (v instanceof Collection<?> col) {
      return col.stream().map(String::valueOf).toArray();
    }
    if (v instanceof Object[] arr) {
      return Arrays.stream(arr).map(String::valueOf).toArray();
    }
    return new Object[]{String.valueOf(v)};
  }
}
