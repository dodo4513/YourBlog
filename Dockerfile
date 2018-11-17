FROM ubuntu:latest
MAINTAINER YourBlog <yourblog@gmail.com>
RUN apt-get update

# blow is openjdk
RUN apt-get install -y openjdk-8-jdk
RUN apt-get install -y curl
RUN apt-get clean

ENV JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
ENV CLASSPATH=$JAVA_HOME/lib/*:.
ENV PATH=$PATH:$JAVA_HOME/bin

RUN apt-cache search openjdk
RUN apt-get clean
# make blew variable
ADD ./build/libs/ /webapp
# make blew variable
EXPOSE 8081

CMD java -jar /webapp/your-blog.jar

#gradle tasks
#gradle bootJar
#docker run -d -p 8081:8081 --name=yourblog yourblog/yourblog

