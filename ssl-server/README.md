### Spring Boot application config enable ssl config

```yaml
server:
  ssl:
    key-store: "classpath:keystores/server.jks" # keystore with ssl keys
    key-store-password: "password" # key store password
    key-store-type: "JKS" # key store type like JKS, PKCS12
    key-password: "password" # key password
    key-alias: "server" # key alias of ssl key
    enabled: true # by default, the value is true not required to specify this property
  port: 443 # it's recommended to keep 443 for https calls
```