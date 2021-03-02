package com.medication.medicationSystem.databaseConfiguration;

public class MedDbConfig {
  
//     @Bean(name = "mongoEntityManagerFactory")
// @Primary
// public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//     LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//     em.setDataSource(dataSourceWrite());
//     em.setPackagesToScan(new String[]{"yourpackage.mongo.entities"});
//     em.setPersistenceUnitName("trueid");
//     JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//     em.setJpaVendorAdapter(vendorAdapter);
//     em.setJpaProperties(additionalProperties());

//     return em;
// }

// @Bean(name = "dataSourceMongo",destroyMethod = "close")
// public DataSource dataSource() {
//     return dataSource;
// }


// @Bean(name = "mongoTransactionManager")
// public PlatformTransactionManager transactionManager(@Qualifier(value = "mongoEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
//     JpaTransactionManager transactionManager = new JpaTransactionManager();
//     transactionManager.setEntityManagerFactory(entityManagerFactory);

//     return transactionManager;
// }

// @Bean(name = "mongoExceptionTranslation")
// public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
//     return new PersistenceExceptionTranslationPostProcessor();
// }
    
}
