# Tree Doc

## How to build jar

#### First
```
mvn -N io.takari:maven:wrapper
```
#### Second
```
./mvnw clean package
```
## How to build docker image
```bash
./mvnw -package -pl tree -am dockerfile:build -Dmaven.test.skip=true
```

## wechat get access token
https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index