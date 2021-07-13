# 启动容器
docker-compose up -d
# 查看redis1节点ip
docker ps -a
docker inspect 容器名
# 进入redis2节点，临时，docker重启失效
redis-cli -p 6380
slaveof redis1ip 6379
# redis2配置文件，可以持久化
replicaof 172.21.0.2 6379
# 备份开始执行,redis2做为从节点，完全同步redis1的数据，并且redis2位只读节点
注意redis1的bind修改为0.0.0.0否则会拒绝redis2连接
