# Assignment 1

## Содержание
- MergeSort
- QuickSort
- Deterministic Select
- Closest Pair of Points

## Технологии
- Java 21
- Maven
- JUnit 5
- GitHub Actions (CI)

## Запуск тестов
```bash
mvn test
```

## Примеры запуска cli
```bash
# Для сборки пакета
mvn package
```

```bash
java -jar target/assignment1-1.0-SNAPSHOT.jar mergesort 10000 results.csv
```

```bash
java -jar target/assignment1-1.0-SNAPSHOT.jar quicksort 10000 results.csv
```

```bash
java -jar target/assignment1-1.0-SNAPSHOT.jar select 5000 results.csv
```

```bash
java -jar target/assignment1-1.0-SNAPSHOT.jar closest 2000 results.csv
```