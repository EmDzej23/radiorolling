package com.nevreme.rolling.configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.SharedCacheMode;
import javax.sql.DataSource;

import org.hibernate.dialect.PostgreSQL9Dialect;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.nevreme.rolling.utils.SiteCachableKeyGenerator;

@Configuration
//@Configurable
@EnableAsync
@EnableCaching
public class WebMvcConfig extends WebMvcConfigurerAdapter implements CachingConfigurer{

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Bean
	public AuthenticationTrustResolver getAuthenticationTrustResolver() {
		return new AuthenticationTrustResolverImpl();
	}

	@Bean(name = "primaryDatasource")
	@ConfigurationProperties(prefix = "spring.primaryDatasource")
	public DataSource primaryDatasource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "secondDatasource")
	@Primary
	@ConfigurationProperties(prefix = "spring.secondDatasource")
	public DataSource secondDatasource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	JpaTransactionManager transactionManager(EntityManagerFactory factory) throws Exception {
		return new JpaTransactionManager(factory);
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
			PersistenceUnitManager persistenceUnitManager, JpaVendorAdapter jpaVendorAdapter,
			DataSource secondDatasource) {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		Properties properties = new Properties();
		properties.put("hibernate.listeners.envers.autoRegister", "false");
		properties.put("hibernate.format_sql", "false");
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.default_batch_fetch_size", "9");
		properties.put("hibernate.max_fetch_depth", "3");
		properties.put("cache.use_second_level_cache", "true");
		properties.put("cache.use_query_cache", "true");
		properties.put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
		properties.put("hibernate.id.new_generator_mappings", "true");
//		properties.put("net.sf.ehcache.configurationResourceName", "ehcache2.xml");
		factory.setJpaProperties(properties);
		factory.setJpaVendorAdapter(jpaVendorAdapter);
		factory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		factory.setPersistenceUnitManager(persistenceUnitManager);
		factory.setDataSource(secondDatasource);

		return factory;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.POSTGRESQL);
		adapter.setDatabasePlatform(PostgreSQL9Dialect.class.getName());
		adapter.setShowSql(true);
		adapter.setGenerateDdl(true);

		return adapter;
	}

	@Bean
	PersistenceUnitManager persistenceUnitManager(DataSource secondDatasource) {
		DefaultPersistenceUnitManager persistenceUnitManager = new DefaultPersistenceUnitManager();
		 persistenceUnitManager.setDefaultPersistenceUnitName("rolling");
		persistenceUnitManager.setDefaultDataSource(secondDatasource);
		persistenceUnitManager.setPackagesToScan("com.nevreme.rolling.*");
		persistenceUnitManager.setSharedCacheMode(SharedCacheMode.DISABLE_SELECTIVE);
		persistenceUnitManager.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
		return persistenceUnitManager;
	}
	
//	@Bean @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//    EntityManager entityManager(EntityManagerFactory factory) throws Exception {
//        return factory.createEntityManager();
//    }

	@Bean
	public net.sf.ehcache.CacheManager ehCacheManager() {
		EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
		cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
		cmfb.setShared(true);
		return cmfb.getObject();
	}
	
	@Bean
	@Override
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheManager());
	}
	
	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		return new SiteCachableKeyGenerator();
	}

	@Bean
	@Override
	public CacheResolver cacheResolver() {
		SimpleCacheResolver cr = new SimpleCacheResolver(cacheManager());
        return cr;
	}

	@Bean
	@Override
	public CacheErrorHandler errorHandler() {
		return new SimpleCacheErrorHandler();
	}


}