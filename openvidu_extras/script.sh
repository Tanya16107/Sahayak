# openssl req -newkey rsa:2048 -new -nodes -x509 -days 3650 -subj '/CN=sahayak.github.io/O=MCGroup6/C=IN' -keyout key.pem -out cert.pem
# password:password
# openssl pkcs12 -export -name key1 -in cert.pem -inkey key.pem -out p12keystore.p12
# keytool -importkeystore -srckeystore p12keystore.p12 -srcstoretype pkcs12 -deststoretype pkcs12 -alias key1 -destkeystore openvidu.jks
docker-compose up -d
