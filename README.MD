### 怎么创建一个Spring Boot项目
1. 在项目的pom.xml中指定父pom（不是依赖）
```
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.4.RELEASE</version>
    </parent>
```

2. 在包的根目录下创建一个类。类上标注@SpringBootApplication。在类的main函数中用SpringApplication启动
```
    @SpringBootApplication
    public class Application {
        public static void main(String... args) {
            SpringApplication.run(Application.class);
        }
    }
```

3. 启动项目，确保项目能够正确的启动

### 怎么创建一个web项目
1. 在pom中指定web的依赖
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
```

2. 在根包下面创建一个controller目录。
```
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello";
    }

}
```

### 怎么和mybatis集成
1. 在pom中指定mybatis和spring的依赖，并加上数据库的驱动
```
        <!-- springboot 集成 mybatis -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.0.1</version>
        </dependency>
        <!-- 我本地使用mysql做测试，所以用mysql驱动 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.13</version>
        </dependency>
```
2. 在application-${profile}.yml中增加数据库连接的配置。
```
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
    username: root
    password: 1234
    driver-class-name: com.mysql.cj.jdbc.Driver
```

3. 编写Mapper和对应的Entity对象。

### Mybatis 怎么创建多数据源
1. 在配置文件中配置多个数据源
```
spring:
  datasource:
    test:
      jdbc-url: jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
      username: root
      password: 1234
      driver-class-name: com.mysql.cj.jdbc.Driver
    learn:
      jdbc-url: jdbc:mysql://localhost:3306/learn?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
      username: root
      password: 1234
      driver-class-name: com.mysql.cj.jdbc.Driver
```
2. 为mybatis创建基于java的Spring配置（数据源配置），@See TestDataSourceConfig
    1. 创建数据源
    2. 创建SqlSessionFactory
    3. 创建SqlSessionTemplate
    4. 创建事务管理器DataSourceTransactionManager
    
3. 在步骤2的配置类上标注扫描@MapperScan
```
@Configuration
@MapperScan(basePackages="com.lucy.mapper.learn", sqlSessionTemplateRef="learnSqlSessionTemplate")
public class LearnDataSourceConfig {

    @Bean("learnDataSource")
    @ConfigurationProperties("spring.datasource.learn")
    public DataSource learnDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("learnSqlSessionFactory")
    public SqlSessionFactory learnSqlSessionFactory(@Qualifier("learnDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionfactoryBean = new SqlSessionFactoryBean();
        sqlSessionfactoryBean.setDataSource(dataSource);

        return sqlSessionfactoryBean.getObject();
    }

    @Bean("learnSqlSessionTemplate")
    public SqlSessionTemplate learnSqlSessionTemplate(@Qualifier("learnSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean("learnDataSourceTransactionManager")
    public DataSourceTransactionManager learnDataSourceTransactionManager(@Qualifier("learnDataSource") DataSource dataSource) {
        return  new DataSourceTransactionManager(dataSource);
    }

}
```