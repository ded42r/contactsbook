FROM adoptopenjdk/openjdk11:jdk-11.0.8_10-alpine as build

RUN mkdir -p /usr/local/src/contactsbook
WORKDIR /usr/local/src/contactsbook
ADD . /usr/local/src/contactsbook
RUN ./gradlew bootJar

FROM adoptopenjdk/openjdk11:jdk-11.0.8_10-alpine

RUN mkdir -p /usr/local/bin/contactsbook
WORKDIR /usr/local/bin/contactsbook
COPY --from=build /usr/local/src/contactsbook/build/libs/contactsbook-0.0.1.jar contactsbook.jar
ENTRYPOINT exec java -jar contactsbook.jar


