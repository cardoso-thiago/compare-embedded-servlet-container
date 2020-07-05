FROM adoptopenjdk/openjdk11-openj9 as build

#Verificar porque o jdeps não volta todas as dependências
#RUN $JAVA_HOME/bin/jdeps --print-module-deps -recursive --multi-release 11 -classpath "/app/lib/*" > java.modules
#RUN $JAVA_HOME/bin/jlink --module-path $JAVA_HOME/bin/jmods/ --add-modules java.base --compress 2 --no-header-files --output /jdk_minimal

RUN $JAVA_HOME/bin/jlink --module-path $JAVA_HOME/bin/jmods/ --add-modules java.base,java.logging,java.naming,java.management,jdk.unsupported,java.desktop --compress 2 --no-header-files --output /jdk_minimal

FROM alpine

RUN addgroup -S cardoso && adduser -S cardoso -G cardoso
USER cardoso:cardoso

COPY --from=build /jdk_minimal /jdk_minimal
COPY target/*jar app.jar

ENTRYPOINT ["/jdk_minimal/bin/java", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]