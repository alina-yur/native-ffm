# Native FFM

This repository demonstrates the FFM API support in GraalVM Native Image and collecting required configuration in `reachability-metadata.json`.

## Run:

```shell
java HelloFFM.java
```

## Collect the reachability configuration:

```shell
 java -agentlib:native-image-agent HelloFFM.java
```

## Build and run as Native Image:

```shell
javac HelloFFM.java
native-image -H:+UnlockExperimentalVMOptions -H:+ForeignAPISupport -H:-UnlockExperimentalVMOptions HelloFFM
./helloffm
```