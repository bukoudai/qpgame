FROM openjdk:11


# 创建工作目录
RUN mkdir -p /usr/local/application /usr/local/application/logs

COPY *.jar /usr/local/application/api.jar

#定义时区参数
ENV TZ=Asia/Shanghai

#设置时区
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo '$TZ' > /etc/timezone


# 工作目录(docker内部目录)
WORKDIR /usr/local/application
# 挂载卷
# VOLUME [ "/usr/local/application" ]
# 暴露端口
EXPOSE 8483
# 启动jar命令
# ENTRYPOINT ["docker-entrypoint.sh"]
# ENTRYPOINT exec java   -jar /usr/local/application/application.jar -p 8483 > logs/log.out 2>&1 &
ENTRYPOINT ["sh","-c","java  -jar  /usr/local/application/api.jar "]
