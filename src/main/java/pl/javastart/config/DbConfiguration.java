package pl.javastart.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.javastart.repository.CarRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = CarRepository.class)
public class DbConfiguration {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(JpaVendorAdapter adapter, DataSource dataSource){
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setPersistenceUnitName("spring-jpa-pu");

        HashMap<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.schema-generation.database.action", "drop-and-create");
        localContainerEntityManagerFactoryBean.setJpaPropertyMap(properties);

        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(adapter);
        localContainerEntityManagerFactoryBean.setPackagesToScan("pl.javastart.model");
        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    public JpaVendorAdapter createVendorAdapter(){
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setShowSql(true);
        return hibernateJpaVendorAdapter;
    }

    @Bean
    public DataSource createDataSource(){
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=UTC");
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("root");
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        basicDataSource.setInitialSize(5);
        return basicDataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }
}
