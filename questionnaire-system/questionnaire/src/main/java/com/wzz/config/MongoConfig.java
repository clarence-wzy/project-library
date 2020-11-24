package com.wzz.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * Created on 2019/8/29 by Tinchi
 *
 * @Description: mongodb配置类，去除_class字段
 **/
@Configuration
public class MongoConfig extends AbstractMongoConfiguration  {

    @Value("${spring.data.mongodb.database}")
    private String dbName;
    @Value("${spring.data.mongodb.uri}")
    private String connectionString;

    @Autowired
    private ApplicationContext appContext;

    @Override
    public MongoClient mongoClient() {
        MongoClientURI mongoClientURI = new MongoClientURI(connectionString);
        return new MongoClient(mongoClientURI);
    }

    @Override
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoDbFactory factory = mongoDbFactory();

        MongoMappingContext mongoMappingContext = new MongoMappingContext();
        mongoMappingContext.setApplicationContext(appContext);

        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(factory), mongoMappingContext);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));

        return new MongoTemplate(factory, converter);

    }

    @Override
    protected String getDatabaseName() {
        return dbName;
    }
}
