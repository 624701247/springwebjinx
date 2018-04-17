# springwebjinx
### file -- new -- maven -- maven-archetype-webapp
### 一个叫春的框架
### 知识点请搜索代码中 kone point 或看下面你的介绍

# 
#### 解决跨域问题
        <!--1. pom.xml 引入-->
        <dependency>
          <groupId>com.thetransactioncompany</groupId>
          <artifactId>cors-filter</artifactId>
          <version>2.6</version>
          <scope>runtime</scope>
        </dependency>
        
        <!--2. web.xml配置 : 解决跨域问题 -->
          <filter>
            <filter-name>CORS</filter-name>
            <filter-class>com.thetransactioncompany.cors.CORSFilter</filter-class>
            <init-param>
              <param-name>cors.allowOrigin</param-name>
              <param-value>*</param-value>
            </init-param>
            <init-param>
              <param-name>cors.supportedMethods</param-name>
              <param-value>GET, POST, HEAD, PUT, DELETE</param-value>
            </init-param>
            <init-param>
              <param-name>cors.supportedHeaders</param-name>
              <param-value>Accept, Origin, X-Requested-With, Content-Type, Last-Modified</param-value>
            </init-param>
            <init-param>
              <param-name>cors.exposedHeaders</param-name>
              <param-value>Set-Cookie</param-value>
            </init-param>
            <init-param>
              <param-name>cors.supportsCredentials</param-name>
              <param-value>true</param-value>
            </init-param>
          </filter>
          <filter-mapping>
            <filter-name>CORS</filter-name>
            <url-pattern>/*</url-pattern>
          </filter-mapping>
          
         
# 
#### kone todo:
    * pom.xml <spring.core.version>3.1.0.RELEASE</spring.core.version> 改成 3.x以上的版本,比如5.0.5 会报错
    
    * 
          
     