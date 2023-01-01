# http-security-layer

### Setting up as a Certificate Authority

#### Generating root key

```shell
keytool -genkeypair -dname "cn=vinay-ca-root, ou=vinay-ca, o=local, c=SG" -alias root -keypass password -keystore root.jks -storepass password -validity 10000 -keyalg RSA -ext bc:c

keytool -keystore root.jks -alias root -exportcert -rfc --validity 10000 > vinay-ca-root.pem
```

#### Generating intermediate key

```shell
keytool -genkeypair -dname "cn=vinay-ca-intermediate, ou=vinay-ca, o=local, c=SG" -alias intermediate -keypass password -keystore intermediate.jks -storepass password -validity 10000 -keyalg RSA -ext bc:c

keytool -storepass password -keystore intermediate.jks -certreq -alias intermediate --validity 10000 | keytool -storepass password -keystore root.jks -gencert -alias root -ext BC=0 -rfc --validity 10000 >  intermediate.pem
```

### Generating Server SSL certificate

#### Generating server keystore
```shell
keytool -genkeypair -keystore server.jks -alias server -dname "cn=localhost, ou=application, o=local, c=SG" -keypass password -storepass password -validity 365 -keyalg RSA
```

#### Signing the server cert by CA keys
```shell
cat vinay-ca-root.pem vinay-ca-intermediate.pem > vinay-ca-chain.pem

keytool -storepass password -keystore server.jks -certreq -alias server  --validity 365 | keytool -storepass password -keystore intermediate.jks -gencert -alias intermediate -ext ku:c=dig,keyEncipherment  -ext SAN=dns:localhost  -rfc --validity 365 > server.pem

cat vinay-ca-root.pem vinay-ca-intermediate.pem server.pem > serverchain.pem

keytool -keystore server.jks -importcert -alias server -file serverchain.pem
```

Reference Links:

<li><b>CA Setup</b> : https://stackoverflow.com/questions/30634658/how-to-create-a-certificate-chain-using-keytool</li>
