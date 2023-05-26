package com.nadia.translator.configuration;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.interceptor.PayloadLoggingInterceptor;
import org.springframework.ws.server.endpoint.mapping.PayloadRootAnnotationMethodEndpointMapping;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.List;

/**
 * @author Yevhenii Filatov
 * @since 5/26/23
 */

@EnableWs
@Configuration
public class SoapConfiguration extends WsConfigurerAdapter {
    private static final String NAMESPACE_URI = "http://example.com/translator";
    private static final String PORT_TYPE_NAME = "Translator";
    private static final String LOCATION_URI = "/ws";
    private static final String XSD_LOCATION = "translator.xsd";
    private static final String URL_MAPPINGS_PATTERN = "/ws/*";

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, URL_MAPPINGS_PATTERN);
    }

    @Bean
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema translatorSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName(PORT_TYPE_NAME);
        wsdl11Definition.setLocationUri(LOCATION_URI);
        wsdl11Definition.setTargetNamespace(NAMESPACE_URI);
        wsdl11Definition.setSchema(translatorSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema translatorSchema() {
        return new SimpleXsdSchema(new ClassPathResource(XSD_LOCATION));
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        interceptors.add(payloadLoggingInterceptor());
    }

    @Bean
    public PayloadLoggingInterceptor payloadLoggingInterceptor() {
        PayloadLoggingInterceptor interceptor = new PayloadLoggingInterceptor();
        interceptor.setLogRequest(true);
        interceptor.setLogResponse(true);
        return interceptor;
    }

    @Bean
    public PayloadRootAnnotationMethodEndpointMapping endpointMapping() {
        PayloadRootAnnotationMethodEndpointMapping endpointMapping = new PayloadRootAnnotationMethodEndpointMapping();
        endpointMapping.setInterceptors(new EndpointInterceptor[]{payloadLoggingInterceptor()});
        return endpointMapping;
    }
}
