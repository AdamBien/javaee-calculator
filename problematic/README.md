# Build
mvn clean package && docker build -t com.airhacks/erroneous .

# RUN

docker rm -f erroneous || true && docker run -d -p 8080:8080 -p 4848:4848 --name erroneous com.airhacks/erroneous 