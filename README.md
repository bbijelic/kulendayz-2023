# KulenDayz 2023 Workshop Demo Application

## Local Setup

### Infobip Integration Config

After checkout, configure Infobip Integration app with information obtained on `portal.infobip.com` after registering free account.

```yaml
infobip:
  registration:
    subject: New registration at Friday Morning Workshop @ Kulendayz 2023
    sender: <sender-email>
    recipient: <recipient-email>
  api:
    url: <infobip-api-base-url>
    key: <infobip-api-key>
```

### Install and package modules
```bash
./mvnw clean install package
```
Output:
```
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary for Infobip KulenDayz 2023 0.0.1-SNAPSHOT:
[INFO] 
[INFO] Infobip KulenDayz 2023 ............................. SUCCESS [  0.120 s]
[INFO] Discovery Service .................................. SUCCESS [  0.005 s]
[INFO] Discovery Service Implementation ................... SUCCESS [  2.287 s]
[INFO] API Gateway Service ................................ SUCCESS [  0.010 s]
[INFO] API Gateway Service Implementation ................. SUCCESS [  0.753 s]
[INFO] User Management Service ............................ SUCCESS [  0.032 s]
[INFO] User Management Service Integration ................ SUCCESS [  0.434 s]
[INFO] Authentication Service ............................. SUCCESS [  0.005 s]
[INFO] Authentication Service Integration ................. SUCCESS [  0.351 s]
[INFO] Authentication Service Tokens ...................... SUCCESS [  0.385 s]
[INFO] User Management Service Implementation ............. SUCCESS [  1.074 s]
[INFO] Authentication Service Implementation .............. SUCCESS [  0.859 s]
[INFO] Infobip Integration Service ........................ SUCCESS [  0.004 s]
[INFO] Infobip Integration Service Integration ............ SUCCESS [  0.211 s]
[INFO] Infobip Integration Service Implementation ......... SUCCESS [  0.836 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

### Build and run docker containers
```bash
cd docker
docker compose up -d --build
```
Output:
```
NAME                         IMAGE                                          COMMAND                  SERVICE               CREATED             STATUS                    PORTS
ibkd-api-gateway-1           ibkd/api-gateway:latest                        "java -Djava.securit…"   api-gateway           35 minutes ago      Up 35 minutes (healthy)   
ibkd-api-gateway-2           ibkd/api-gateway:latest                        "java -Djava.securit…"   api-gateway           35 minutes ago      Up 35 minutes (healthy)   
ibkd-auth-service-1          infobip-kulendayz/auth-service:latest          "java -Djava.securit…"   auth-service          35 minutes ago      Up 35 minutes (healthy)   
ibkd-discovery-1             infobip-kulendayz/discovery-service:latest     "java -Djava.securit…"   discovery             35 minutes ago      Up 35 minutes (healthy)   127.0.0.1:8761->8761/tcp
ibkd-elasticsearch-1         elasticsearch:8.6.0                            "/bin/tini -- /usr/l…"   elasticsearch         35 minutes ago      Up 35 minutes (healthy)   127.0.0.1:9200->9200/tcp, 127.0.0.1:9300->9300/tcp
ibkd-grafana-1               grafana/grafana:latest                         "/run.sh"                grafana               35 minutes ago      Up 35 minutes (healthy)   127.0.0.1:9000->3000/tcp
ibkd-haproxy-1               haproxy:lts-alpine                             "docker-entrypoint.s…"   haproxy               35 minutes ago      Up 35 minutes             0.0.0.0:80->80/tcp, :::80->80/tcp
ibkd-infobip-integration-1   infobip-kulendayz/infobip-integration:latest   "java -Djava.securit…"   infobip-integration   35 minutes ago      Up 35 minutes (healthy)   
ibkd-kibana-1                kibana:8.6.0                                   "/bin/tini -- /usr/l…"   kibana                35 minutes ago      Up 35 minutes (healthy)   127.0.0.1:5601->5601/tcp
ibkd-logstash-1              logstash:8.6.0                                 "/usr/local/bin/dock…"   logstash              35 minutes ago      Up 35 minutes (healthy)   127.0.0.1:5000->5000/tcp, 127.0.0.1:5044->5044/tcp, 127.0.0.1:9600->9600/tcp, 127.0.0.1:5000->5000/udp
ibkd-pgadmin-1               dpage/pgadmin4:7.5                             "/entrypoint.sh"         pgadmin               35 minutes ago      Up 35 minutes (healthy)   443/tcp, 127.0.0.1:15432->80/tcp
ibkd-postgres-1              postgres:15.4-alpine                           "docker-entrypoint.s…"   postgres              35 minutes ago      Up 35 minutes (healthy)   127.0.0.1:5432->5432/tcp
ibkd-prometheus-1            prom/prometheus:v2.38.0                        "/bin/prometheus --c…"   prometheus            35 minutes ago      Up 35 minutes (healthy)   9090/tcp
ibkd-rabbitmq-1              rabbitmq:3.12.2-management-alpine              "docker-entrypoint.s…"   rabbitmq              35 minutes ago      Up 35 minutes (healthy)   4369/tcp, 5671/tcp, 127.0.0.1:5672->5672/tcp, 15671/tcp, 15691-15692/tcp, 25672/tcp, 127.0.0.1:15672->15672/tcp
ibkd-user-management-1       infobip-kulendayz/user-management:latest       "java -Djava.securit…"   user-management       35 minutes ago      Up 35 minutes (healthy)   
ibkd-zipkin-1                openzipkin/zipkin:2                            "start-zipkin"           zipkin                35 minutes ago      Up 35 minutes (healthy)   9410/tcp, 127.0.0.1:9411->9411/tcp
```

## Endpoints
### RabbitMq Management (http://localhost:15672)
| Hostname  | Port | Username   | Password   |
|-----------|----|------------|------------|
| localhost | 15672   | ibkd_admin | ibkd_admin |

### Zikin Tracing Server (http://localhost:9411/)
| Hostname  | Port | Username | Password |
|-----------|------|----------|----------|
| localhost | 9411 | n/a      | n/a      |

### PgAdmin (http://localhost:15432/)
| Hostname  | Port | Email             | Password |
|-----------|------|-------------------|----------|
| localhost | 15432 | admin@infobip.com | admin    |

### Eureka Discovery (http://localhost:8761/)
| Hostname  | Port | Username | Password |
|-----------|------|----------|----------|
| localhost | 8761 | n/a      | n/a      |

### Grafana (http://localhost:9000/)
| Hostname  | Port | Username | Password |
|-----------|------|----------|----------|
| localhost | 9000 | admin    | admin    |

### HAProxy Reverse Proxy / Load Balancer (http://localhost/haproxy)
| Hostname  | Port | Username | Password |
|-----------|------|----------|----------|
| localhost | 80   | n/a      | n/a      |

### Kibana (http://localhost:5601/)
| Hostname  | Port | Username | Password      |
|-----------|------|----------|---------------|
| localhost | 5601 | elastic  | elasticsearch |

