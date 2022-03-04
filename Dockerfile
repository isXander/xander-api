FROM alpine as actions
ADD ./actions/metrics-api.tar /src/api/

FROM openjdk:17
WORKDIR /src/api
COPY --from=actions /src/api/metrics-api-* /src/api
EXPOSE 8080
ENTRYPOINT [ "sh", "/src/api/bin/metrics-api" ]
