# angular-spring-boot-microservices

- Conference sessions  
- Blog
- Registration

#### Features 
- JHipster code generator
- Microservice architecture  

## 

## Application Stack

Stack  | version |
--- | --- |  
*Java* | 1.8 
*SpringBoot* |  2.2.4.RELEASE
*Frontend* | Angular 
*DB* | h2-database (in memory)
*Server* | Tomcat (embedded)
*Build Tool* | Maven
*CI* | n/a  
*Code Coverage* | n/a
*Build env* | local

## Application Build 
``` 
1. Create registery (type registery)
2. Create gateway (type gateway)
3. Create conference (type microservice)
4. Create blog (type microservice)
 
> cd ./conference
> jhipster import-jdl ../scritps/conference-jdl.jh

> cd ./blog
> jhipster import-jdl ../scritps/blog-jdl.jh

> cd ./gateway 
> jhipster import-jdl ../scritps/conference-jdl.jh
> jhipster import-jdl ../scritps/blog-jdl.jh

```
  
## Application Run 
``` 
> cd registery 
> ./mvnw
 
> cd ./gateway 
> ./mvnw

> cd ./conference
> ./mvnw
```
  
  
## Application URL
http://localhost:8080/
 
## Application profile

## Further enhancements 
