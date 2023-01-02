### Spring Boot application config enable mTLS config

```yaml
server:
  ssl:
    key-store: "classpath:keystores/server.jks" # keystore with ssl keys
    key-store-password: "password" # key store password
    key-store-type: "JKS" # key store type like JKS, PKCS12
    key-password: "password" # key password
    key-alias: "server" # key alias of ssl key
    enabled: true # by default, the value is true not required to specify this property
    client-auth: need # to enable mTls, values are need -> mandatory, want -> required but not mandatory, none -> not required
    trust-store-type: "JKS" # key store type like JKS, PKCS12
    trust-store: "classpath:keystores/truststore.jks" # truststore with client cert
    trust-store-password: "password" 
  port: 443
```