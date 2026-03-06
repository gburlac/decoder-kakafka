# decoder-kakafka

Minimal protobuf-like decoder that reads a binary file or hex input and prints decoded fields.

## Build

```powershell
mkdir out -ErrorAction SilentlyContinue
javac -cp src -d out src\*.java
```

## Create a runnable JAR

```powershell
jar cfm decoder-kakafka-1.3.jar MANIFEST.MF -C out .
```

Run the JAR (UI):

```powershell
java -jar decoder-kakafka.jar
```

## Decode hex input (CLI)

```powershell
java -cp out Main "08 03 12 14 6d 61 72 6b 65 74 42 72 69 65 66 49 64 41 42 43 31 31 32 38 18 b1 a8 03"
```

## Decode a binary file

```powershell
java -cp out ProtoDecoder sample.bin
```

## Launch the UI (no JAR)

```powershell
java -cp out DecoderUi
```
