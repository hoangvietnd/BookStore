package com.caotrinh;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Bean(name = "localeResolver")
	public LocaleResolver getLocaleResolver() {
		CookieLocaleResolver resolver = new CookieLocaleResolver();
		resolver.setCookieDomain("myAppLocalCookie");
		resolver.setDefaultLocale(Locale.ENGLISH);
		resolver.setCookieMaxAge(3600);
		return resolver;
	}

	@Bean(name = "messageSource")
	public MessageSource getMessageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:static/i18n/messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("language");
		registry.addInterceptor(localeChangeInterceptor).addPathPatterns("/*");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/403").setViewName("403");
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/login").setViewName("login");
		// registry.addViewController("/home").setViewName("userhome");
		registry.addViewController("/user/home").setViewName("user");
	}

//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		Path imageUpploadDir = Paths.get("./images/books");
//		String imageUploadPath = imageUpploadDir.toFile().getAbsolutePath();
//		registry.addResourceHandler("./images/books/**").addResourceLocations("file:/" + imageUploadPath + "/");
//	}

}
