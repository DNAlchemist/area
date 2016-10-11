# area

## How to run application

```bash
./gradlew appRun
```

open in browser: http://localhost:8080/area

## How to test

```bash
./gradlew integrationTest
```
Result will be placed in path: "build/reports/tests/integrationTest/index.html"

```bash
./gradlew test
```
Result will be placed in path: "build/reports/tests/test/index.html"

## How to build war

```bash
./gradlew war
```

##REST

```
add person: manage/persons/put?id=${id}
            manage/persons/put?id=${id}&x=${x}&y=${y}

remove person: manage/persons/delete?id=${id}
```