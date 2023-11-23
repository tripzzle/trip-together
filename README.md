# Trip Together

### tools

- Spring Framework 2.7.16
- Java 17

- AWS EC2


### 실행 순서

#### 1. project clone

```
$ git clone --recurse-submodules {레포주소}

#### 서브모듈 업데이트하는 방법
$ git submodule update --remote --merge

#### 2. gradle
```
# build.gralde에 아래 내용 없으면 추가

# ./security에서 src/main/resouces로 폴더 copy
```
processResources.dependsOn('copySecret')
tasks.register('copySecret', Copy) {
    from './security'
    include '*.yml'
    into 'src/main/resources'
}
```
