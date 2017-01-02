# Launch4jPlusSLF4j
Test case for https://github.com/TheBoegl/gradle-launch4j/issues/40

# Building
`gradlew.bat createExe`

# Running
`java -jar build\launch4j\Launch4jPlusSLF4j.exe`

If you see the output:
```
D:\Projects\Java\Repositories\Launch4jPlusSLF4j>java -jar build\launch4j\Launch4jPlusSLF4j.exe
2017-01-03 01:15:42,038 ERROR Error processing element GUILogger: CLASS_NOT_FOUND
2017-01-03 01:15:42,083 ERROR Unable to locate appender JavaFXLogger for logger
[01:15:42] [INFO] MainController: Application initialized
```

Then it obviously failed

When you run the jar like: `java -jar build\libs\Launch4jPlusSLF4j.jar`

The output will be:
```
D:\Projects\Java\Repositories\Launch4jPlusSLF4j>java -jar build\libs\Launch4jPlusSLF4j.jar
[01:16:24] [INFO] MainController: Application initialized
```
