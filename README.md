# decoder-kakafka

Minimal protobuf-like decoder that reads a binary file or hex input and prints decoded fields.

## Build

```powershell
javac src\*.java
```

## Create a runnable JAR

```powershell
@"
Manifest-Version: 1.0
Main-Class: DecoderUi

"@ | Set-Content -Path MANIFEST.MF -Encoding ascii
jar cfm decoder-kakafka.jar MANIFEST.MF -C src .
```

Run the JAR:

```powershell
java -jar decoder-kakafka.jar
```

## Decode hex input (CLI)

```powershell
java -cp src Main "08 03 12 14 6d 61 72 6b 65 74 42 72 69 65 66 49 64 41 42 43 31 31 32 38 18 b1 a8 03"
```

## Decode a binary file

```powershell
java -cp src ProtoDecoder sample.bin
```

## Launch the UI

```powershell
java -cp src DecoderUi
```
