package com.appointmentbooking.booking.AppointmentService.DatabaseConfiguration;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "appEntityManagerFactory",
        transactionManagerRef = "appTransactionManager",
        basePackages = {"com.appointmentbooking.booking.AppointmentService.Repository"}
)
public class AppointmentServiceConfig{
    @Bean(name = "appDataSource")
    @ConfigurationProperties(prefix = "spring.app.datasource")
    public DataSource userDataSource() {
        return DataSourceBuilder.create().build();
    }

    
    @Bean(name = "appEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("appDataSource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.appointmentbooking.booking.AppointmentService.model")
                .persistenceUnit("Patient")
                .persistenceUnit("Doctor")
                .persistenceUnit("AppointmentType")
                .persistenceUnit("DoctorSpeciality")
                .build();
    }

    @Bean(name = "appTransactionManager")
    public PlatformTransactionManager appTransactionManager(
            @Qualifier("appEntityManagerFactory") EntityManagerFactory appEntityManagerFactory) {
        return new JpaTransactionManager(appEntityManagerFactory);
    }
}