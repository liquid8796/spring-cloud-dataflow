<p align="center">
  <a href="https://dataflow.spring.io/">
    <img alt="Spring Data Flow Dashboard" title="Spring Data Flow" src="https://i.imgur.com/hpeKaRk.png" width="450" />
  </a>
</p>
__
[![Build Status - CI](https://github.com/spring-cloud/spring-cloud-dataflow/actions/workflows/ci.yml/badge.svg)](https://github.com/spring-cloud/spring-cloud-dataflow/actions/workflows/ci.yml)


*Spring Cloud Data Flow* is a microservices-based toolkit for building streaming and batch data processing pipelines in
Cloud Foundry and Kubernetes.

Data processing pipelines consist of Spring Boot apps, built using the [Spring Cloud Stream](https://github.com/spring-cloud/spring-cloud-stream)
or [Spring Cloud Task](https://github.com/spring-cloud/spring-cloud-task) microservice frameworks. 

This makes Spring Cloud Data Flow ideal for a range of data processing use cases, from import/export to event streaming
and predictive analytics.

----

## Building

### Step 1: Build spring-cloud-dataflow-server to jar file

    ./mvnw install -s .settings.xml -T 1C -am -pl :spring-cloud-dataflow-server,:spring-cloud-dataflow-composed-task-runner

### Step 2:
- Move folder '/scdf-bitnami-container/buildfs' and 'scdf-bitnami-container/rootfs' to spring-cloud-dataflow-server
- Move folder '/csdf-ctr-bitnami-container/buildfs' and 'csdf-ctr-bitnami-container/rootfs' to spring-cloud-dataflow-composed-task-runner

### Step 3:
- If the version of Application is change in pom.xml --> Update version in Dockerfile
-- COPY target/spring-cloud-dataflow-server-<version>.jar /opt/bitnami/spring-cloud-dataflow/spring-cloud-dataflow.jar

### Step 4: 
- Build docker image in folder `scdf-bitnami-container` by command:
    
      docker build --no-cache -t <repository>/spring-cloud-dataflow-server:<tag> ./csdf-ctr-bitnami-container

- Build docker image in folder `scdf-ctr-bitnami-container` by command:

      docker build --no-cache -t <repository>/spring-cloud-dataflow-composed-task-runner:<tag> ./spring-cloud-dataflow-composed-task-runner

## Cấu trúc thư mục spring-cloud-dataflow-server

```
spring-cloud-dataflow-server/
│
├── target/
│   └── spring-cloud-dataflow-server-<version>.jar    
|
├── Dockerfile                                              # Dockerfile to build the container image     
├── src/ 
├── pom.xml
├── prebuildfs/                                             # Prebuilt filesystem components
├── rootfs/                                                 # Root filesystem configurations
├── docker-compose.yml                                      # Docker Compose configuration file
└── tags-info.yaml                                          # YAML file with tag-related information
```

## Cấu trúc thư mục spring-cloud-dataflow-composed-task-runner:

```
spring-cloud-dataflow-composed-task-runner/
│
├── target/
│   └── spring-cloud-dataflow-composed-task-runner-<version>.jar 
├── Dockerfile                                              # Dockerfile to build the container image     
├── src/ 
├── pom.xml
├── prebuildfs/                                             # Prebuilt filesystem components
├── rootfs/                                                 # Root filesystem configurations
├── docker-compose.yml                                      # Docker Compose configuration file
└── tags-info.yaml                                          # YAML file with tag-related information
```

## Notes (chỉ dành cho dev, không dành cho devops)

1. Source scdf gốc của Spring cloud: https://github.com/spring-cloud/spring-cloud-dataflow
2. Source container scdf của bitnami: https://github.com/bitnami/containers/tree/main/bitnami/spring-cloud-dataflow/2/debian-12
3. Source container scdf composed task runner của bitnami: https://github.com/bitnami/containers/tree/main/bitnami/spring-cloud-dataflow-composed-task-runner/2/debian-12
4. Nếu muốn update version mới của scdf, clone source 1 trước, sau đó clone source 2 và move tất cả content source 2 vào folder `scdf-bitnami-container`, cuối cùng build như các step trên (nếu ko có folder `scdf-bitnami-container` thì tự tạo theo cấu trúc thư mục như trên).
5. Nếu muốn update version mới của scdf composed task runner, clone source 1 trước, sau đó clone source 3 và move tất cả content source 3 vào folder `scdf-ctr-bitnami-container`, cuối cùng build như các step trên (nếu ko có folder `scdf-ctr-bitnami-container` thì tự tạo theo cấu trúc thư mục như trên).
6. Hạn chế update source container của bitnami vì cần phải fix một cố code để tương thích với điều kiện hiện có.