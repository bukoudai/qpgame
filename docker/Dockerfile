FROM gookit/jdk:latest


# 创建工作目录
RUN mkdir -p /usr/local/application/jar /usr/local/application/logs

COPY *.jar /usr/local/application/jar/application.jar

#定义时区参数
ENV TZ=Asia/Shanghai

#设置时区
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo '$TZ' > /etc/timezone


# 工作目录(docker内部目录)
WORKDIR /usr/local/application

# 暴露端口
EXPOSE 6010
# 启动jar命令
ENTRYPOINT exec java   -jar /usr/local/application/jar/application.jar