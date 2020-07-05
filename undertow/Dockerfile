FROM azul/zulu-openjdk-alpine:11 AS build
RUN apk add binutils

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

RUN unzip app.jar

#Verificar porque o jdeps não volta todas as dependências
#RUN $JAVA_HOME/bin/jdeps --print-module-deps -recursive --multi-release 11 -classpath "BOOT-INF/lib/*" app.jar > java.modules
#RUN $JAVA_HOME/bin/jlink --module-path $JAVA_HOME/bin/jmods/ --add-modules java.base --compress 2 --no-header-files --output /jdk_minimal

RUN $JAVA_HOME/bin/jlink --module-path $JAVA_HOME/bin/jmods/ --add-modules java.base,java.logging,java.desktop,java.naming,java.management,jdk.unsupported --compress 2 --no-header-files --output /jdk_minimal

FROM alpine

RUN addgroup -S cardoso && adduser -S cardoso -G cardoso
USER cardoso:cardoso

COPY --from=build /jdk_minimal /jdk_minimal

#Copiando e executando o jar completo
#COPY --from=build app.jar app.jar
#CMD [ "/jdk_minimal/bin/java", "-jar", "app.jar" ]

#Copiando e executando o jar descompactado
COPY --from=build /BOOT-INF/lib /app/lib
COPY --from=build /META-INF /app/META-INF
COPY --from=build /BOOT-INF/classes /app
ENTRYPOINT ["/jdk_minimal/bin/java","-cp","app:app/lib/*","br.com.undertow.UndertowApplication"]