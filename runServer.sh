# Simple script to unpack the server distribution and execue it with no parameters specified

mkdir -p server-dist
cp target/jetty-jersey-example-dist.tar.gz server-dist
tar -zxf server-dist/jetty-jersey-example-dist.tar.gz -C server-dist/
java -jar server-dist/jetty-jersey-example/jetty-jersey-example.jar 
