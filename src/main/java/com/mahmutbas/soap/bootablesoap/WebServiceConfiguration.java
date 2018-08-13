package com.mahmutbas.soap.bootablesoap;

import com.sun.xml.ws.transport.http.servlet.WSSpringServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:jaxwsconfig.xml")
public class WebServiceConfiguration
{
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        return new ServletRegistrationBean(new WSSpringServlet(),"/services/*");
    }
}