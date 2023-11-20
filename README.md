# Trip Together

### tools

- Spring Framework 2.7.16
- Java 11

- aws ec2 instance : ubuntu


### 실행 순서

#### 1. project clone

```
$ git clone [repository HTTP or SSH]

# submodule 초기화
$ git submodule init

# submodule update
$ git submodule update
```

#### 2. gradle
```
# build.gralde에 아래 내용 추가

# ./security에서 src/main/resouces로 폴더 copy
processResources.dependsOn('copySecret')
tasks.register('copySecret', Copy) {
    from './security'
    include '*.yml'
    into 'src/main/resources'
}
```
