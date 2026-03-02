# decoder-kakafka

Minimal protobuf-like decoder that reads a binary file and prints decoded fields.

## Build

```powershell
javac src\*.java
```

## Generate a sample binary file

```powershell
java -cp src SampleWriter sample.bin
```

## Decode a binary file

```powershell
java -cp src Main sample.bin
```

