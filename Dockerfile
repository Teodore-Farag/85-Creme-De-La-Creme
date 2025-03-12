FROM openjdk:25-ea-4-jdk-oraclelinux9
WORKDIR /app
COPY ./ /app
#COPY target/mini1.jar mini1.jar
# Define environment variables for the JSON file path inside the container
#ENV DATA_JSON_PATH=MiniAclProject_1/85-Creme-De-La-Creme/src/main/java/com/example/data

# Create a directory for JSON data
RUN mkdir -p /app/data
# Set environment variables for JSON paths in the container
ENV SPRING_APPLICATION_USERDATAPATH=/app/data/users.json
ENV SPRING_APPLICATION_PRODUCTDATAPATH=/app/data/products.json
ENV SPRING_APPLICATION_ORDERDATAPATH=/app/data/orders.json
ENV SPRING_APPLICATION_CARTDATAPATH=/app/data/carts.json

EXPOSE 8080
#CMD ["java","-jar","mini1.jar "]

CMD ["java","-jar","/app/target/mini1.jar"]
