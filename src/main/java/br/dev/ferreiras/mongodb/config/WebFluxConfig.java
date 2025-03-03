package br.dev.ferreiras.mongodb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.codec.ByteBufferEncoder;
import org.springframework.core.codec.CharSequenceEncoder;
import org.springframework.http.codec.EncoderHttpMessageWriter;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.ResourceHttpMessageWriter;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.codec.xml.Jaxb2XmlEncoder;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.accept.RequestedContentTypeResolverBuilder;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityResultHandler;

import java.util.List;

@Configuration
public class WebFluxConfig {

  public WebFluxConfig() {
    // TODO document why this constructor is empty
  }

  @Bean
  @Order(0)
  public static ResponseEntityResultHandler createHandler() {
    List<HttpMessageWriter<?>> writerList = List.of(
        new EncoderHttpMessageWriter<>(new ByteBufferEncoder()),
        new EncoderHttpMessageWriter<>(CharSequenceEncoder.textPlainOnly()),
        new ResourceHttpMessageWriter(),
        new EncoderHttpMessageWriter<>(new Jaxb2XmlEncoder()),
        new EncoderHttpMessageWriter<>(new Jackson2JsonEncoder()),
        new EncoderHttpMessageWriter<>(CharSequenceEncoder.allMimeTypes()));
    RequestedContentTypeResolver resolver = new RequestedContentTypeResolverBuilder().build();

    return new ResponseEntityResultHandler(writerList, resolver);
  }
}

