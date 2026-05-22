# Deployment Guide — LibraryManagementSystem

This document explains how to compile, package, and run the Java Console application included in this repository.

**Prerequisites**

- Java Development Kit (JDK) 8 or later installed and `java`, `javac`, and `jar` available on `PATH`.
- A terminal (PowerShell on Windows) and basic command-line familiarity.

**Project layout**

- Source root: `src/` (packages: `model`, `service`, `utility`)
- Compiled classes output: `bin/`
- Optional manifest: `manifest.txt`
- Example JAR: `LibraryManagementSystem.jar`

**Compilation Steps**

1. From the project root, create `bin` and compile sources into it. This preserves package directories so the JVM can find classes by their fully-qualified names.

```powershell
# create output directory (no error if it exists)
mkdir bin -ErrorAction SilentlyContinue

# compile sources into bin
javac -d bin -sourcepath src src\model\*.java src\service\*.java src\utility\*.java
```

- The `-d bin` option places `.class` files in `bin/` following package paths (e.g., `bin/model/Book.class`).

**JAR Creation**

Option A — Use an existing manifest file `manifest.txt` that contains the `Main-Class` entry (recommended when present):

```powershell
# manifest.txt must contain a Main-Class line and a trailing newline, for example:
# Main-Class: utility.Main

jar cfm LibraryManagementSystem.jar manifest.txt -C bin .
```

Option B — Specify the entry class on the command line (no manifest file required):

```powershell
jar cfe LibraryManagementSystem.jar utility.Main -C bin .
```

- `-C bin .` tells `jar` to change into `bin` and package all files there, ensuring package paths inside the JAR match declarations in source files.

**Execution Steps**

- Run as an executable JAR (requires `Main-Class` in manifest):

```powershell
java -jar LibraryManagementSystem.jar
```

- Or run directly from compiled classes without creating a JAR:

```powershell
java -cp bin utility.Main
```

**Verification & Inspection**

- List JAR contents:

```powershell
jar tf LibraryManagementSystem.jar
```

- Extract and view the manifest to confirm `Main-Class`:

```powershell
jar xf LibraryManagementSystem.jar META-INF/MANIFEST.MF
type META-INF\MANIFEST.MF
```

**Troubleshooting (common issues & fixes)**

- ClassNotFoundException / NoClassDefFoundError:
  - Ensure classes are compiled into `bin/` with matching package folders. Re-run `javac -d bin ...`.

- Application doesn't start with `java -jar`:
  - Check `META-INF/MANIFEST.MF` contains the correct fully-qualified `Main-Class` (e.g., `utility.Main`).
  - Ensure the main class declares `public static void main(String[] args)`.

- Manifest ignored or not picked up:
  - Ensure `manifest.txt` ends with a newline after the `Main-Class` line.
  - Use `jar cfe` to set the entry class explicitly when in doubt.

- Resource or path differences when running from a JAR:
  - If your app loads files relative to the working directory, confirm those paths exist when running from a JAR; use resource loading from the classpath where appropriate.

- Java version mismatch:
  - If classes were compiled with a newer JDK than the runtime JRE, either compile with an older target or run with a compatible JRE. Check `java -version`.

**Quick Command Summary**

```powershell
# Compile
mkdir bin -ErrorAction SilentlyContinue
javac -d bin -sourcepath src src\model\*.java src\service\*.java src\utility\*.java

# Create JAR (using manifest)
jar cfm LibraryManagementSystem.jar manifest.txt -C bin .

# Or create JAR and set main explicitly
jar cfe LibraryManagementSystem.jar utility.Main -C bin .

# Run
java -jar LibraryManagementSystem.jar
# or
java -cp bin utility.Main

# Inspect
jar tf LibraryManagementSystem.jar
```

If you want, I can run the compile and JAR commands here and produce `LibraryManagementSystem.jar` in this workspace.
