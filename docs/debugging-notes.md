# Jenkins Multibranch Pipeline Debugging Notes

## Problem

Jenkins pipeline failed during execution with:

```
No such DSL method 'buildApp'
```

The pipeline successfully connected to GitHub, discovered branches, checked out the Jenkinsfile, and loaded `script.groovy`, but failed when executing custom Groovy pipeline functions.

---

# Debugging Process

## 1. Reviewed Jenkins Console Output

Initial failure indicated Jenkins could not find the custom function:

```
No such DSL method 'buildApp'
```

This suggested Jenkins was interpreting `buildApp()` as a native pipeline step instead of a function loaded from a Groovy script.

---

## 2. Verified Groovy Script Loading

Confirmed Jenkins successfully executed:

```
load script.groovy
```

The issue was not that the Groovy file failed to load.

The problem was how the functions were being referenced.

---

## 3. Reviewed Jenkinsfile Function Calls

Initial implementation:

```groovy
buildApp()
```

Jenkins attempted to find `buildApp` as a built-in pipeline command.

Resolution:

```groovy
gv.buildApp()
```

The loaded Groovy script needed to be referenced through the object variable.

---

## 4. Added Groovy Object Reference

Updated Jenkinsfile:

Before:

```groovy
gv = load 'script.groovy'

buildApp()
```

After:

```groovy
def gv

gv = load 'script.groovy'

gv.buildApp()
```

The variable `gv` represents the loaded Groovy script and exposes the reusable functions.

---

## 5. Investigated Branch and Commit Execution

Verified Jenkins was building the expected branch and commit.

Reviewed:

- Branch being executed
- Commit hash checked out by Jenkins
- Jenkins workspace location

Example:

```
Checking out Revision <commit-hash> (main)
```

This confirmed which version of the Jenkinsfile Jenkins was executing.

---

## 6. Discovered Multiple Jenkinsfiles

Repository inspection revealed multiple pipeline files:

```
jenkins-nexus-ci-cd-pipeline/

├── Jenkinsfile
├── script.groovy
│
└── jenkins-shared-library/
    ├── Jenkinsfile
    └── script.groovy
```

The issue was that Jenkins was configured to use:

```
Jenkinsfile
```

while changes were being made in:

```
jenkins-shared-library/Jenkinsfile
```

This created confusion because the edited file was not the file Jenkins executed.

---

## 7. Corrected Jenkins Script Path

Updated Jenkins Multibranch Pipeline configuration to use the correct Jenkinsfile location.

Standardized execution around:

```
/Jenkinsfile
/script.groovy
```

---

## 8. Re-indexed Jenkins Multibranch Pipeline

Ran:

```
Scan Multibranch Pipeline Now
```

This forced Jenkins to:

- Reconnect to GitHub
- Discover branches
- Locate Jenkinsfiles
- Refresh pipeline configuration

---

## 9. Validated Successful Pipeline Execution

Final successful execution:

```
init
 |
 v
Load Groovy Functions

build
 |
 v
Building the application....

test
 |
 v
Testing the application....

deploy
 |
 v
Deploying the application....

Finished: SUCCESS
```

---

# Key Debugging Lessons

1. Always verify which Jenkinsfile Jenkins is actually executing.

2. The Jenkins console output is the source of truth:
   - branch
   - commit hash
   - workspace
   - Jenkinsfile path

3. Repository structure matters in CI/CD systems.

4. Multibranch pipelines depend on:
   - Git branch discovery
   - Script Path configuration
   - Jenkinsfile placement

5. When using external Groovy scripts:
   - load the script
   - assign it to a variable
   - call functions through that object

Example:

```groovy
def gv

gv = load 'script.groovy'

gv.buildApp()
```
