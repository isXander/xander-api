FROM alpine as actions
ADD ./actions/xander-api.tar /src/api/

FROM openjdk:17
WORKDIR /src/api
COPY --from=actions /src/api/xander-api-* /src/api
EXPOSE 8080
ENTRYPOINT [ "sh", "/src/api/bin/xander-api" ]
