## Example Kotlin scripts utilizing MIR

### Running scripts

1. Compile MIR to your local Maven repository with

```bash
mvn clean install
```

2. Install kscript (brew/linuxbrew):

```bash
brew install holgerbrandl/tap/kscript
```

3. Check that the following line is present in your Kotlin script:

```kotlin
//DEPS com.milaboratory:mir:{actual version}
```

4. Execute the following command

```bash
kscript File.kts
```

### Setting up IntelliJ

In order to work comfortably:
1. Create a new Java/Kotlin project placed in ``mir/`` folder
2. Open module settings or press ``F4``. Mark ``src/main/java`` as source,
``src/main/resources`` as resources, ``src/test/java`` as test source root,
``src/test/resources`` as test resources roots. Finally mark ``examples/kotlin``
as source root.
3. Click on ``pom.xml`` and select import project from maven

> N.B. You will see some nasty warnings when trying to run scripts, but all
autocomplete will work OK. Unfortunately the "debug" will not work..