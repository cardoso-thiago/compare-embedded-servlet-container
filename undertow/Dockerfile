FROM azul/zulu-openjdk-alpine:11 AS build
RUN apk add binutils

ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

#Verificar porque o jdeps não volta todas as dependências
#RUN $JAVA_HOME/bin/jdeps --print-module-deps -recursive --multi-release 11 -classpath "/app/lib/*" > java.modules
#RUN $JAVA_HOME/bin/jlink --module-path $JAVA_HOME/bin/jmods/ --add-modules java.base --compress 2 --no-header-files --output /jdk_minimal

RUN $JAVA_HOME/bin/jlink --module-path $JAVA_HOME/bin/jmods/ --add-modules java.base,java.datatransfer,java.desktop,java.instrument,\
java.logging,java.management,java.management.rmi,java.rmi/sun.rmi.registry,\
java.naming,java.prefs,java.scripting,java.security.jgss,java.security.sasl,\
java.sql,java.xml,jdk.httpserver,jdk.management,jdk.unsupported \
--compress 2 --no-header-files --output /jdk_minimal

FROM alpine

RUN addgroup -S cardoso && adduser -S cardoso -G cardoso
USER cardoso:cardoso

COPY --from=build /jdk_minimal /jdk_minimal
COPY --from=build /app /app

ENTRYPOINT ["/jdk_minimal/bin/java","-cp","app:app/lib/*","br.com.undertow.UndertowApplication"]