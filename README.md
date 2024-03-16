# Trip Together

### tools

- Spring Framework 2.7.16
- Java 17

- AWS EC2


### 실행 순서

#### 1. project clone

```
$ git clone --recurse-submodules {레포주소}

# 서브모듈 업데이트하는 방법
$ git submodule update --remote --merge

```
#### 2. build.gralde 설정

`./security -> src/main/resouces로의 복사를 위해 필요한 설정`
```
processResources.dependsOn('copySecret')
tasks.register('copySecret', Copy) {
    from './security'
    include '*.yml'
    into 'src/main/resources'
}
```
