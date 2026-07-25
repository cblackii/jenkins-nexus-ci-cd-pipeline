# Jenkins Multibranch Pipeline Troubleshooting

## Overview

This document captures troubleshooting steps and lessons learned while implementing a Jenkins Multibranch CI/CD pipeline integrated with GitHub.

The purpose of this project was to understand Jenkins pipeline execution, branch discovery, reusable Groovy pipeline functions, and CI/CD troubleshooting workflows.

---

# Issue

The Jenkins pipeline failed during execution with the following error:

```
No such DSL method 'buildApp'
```

The pipeline successfully loaded the external Groovy script but failed when attempting to execute custom pipeline functions.

---

# Symptoms

Jenkins successfully completed:

- GitHub repository connection
- Branch discovery
- Jenkinsfile checkout
- Groovy script loading

However, the pipeline failed during the build stage.

Example failure:

```
java.lang.NoSuchMethodError:
No such DSL method 'buildApp'
```

---

# Investigation

The following areas were reviewed:

- Jenkins console output
- Multibranch pipeline branch indexing
- Git branch selection
- Jenkins Script Path configuration
- Repository file structure
- Jenkinsfile location
- Groovy script loading behavior

---

# Root Cause

The repository contained multiple Jenkins pipeline files:

```
jenkins-nexus-ci-cd-pipeline/

├── Jenkinsfile
├── script.groovy
│
└── jenkins-shared-library/
    ├── Jenkinsfile
    └── script.groovy
```

Jenkins was configured to execute:

```
Jenkinsfile
```

from the repository root.

However, pipeline changes were initially being made inside:

```
jenkins-shared-library/Jenkinsfile
```

This caused Jenkins to execute a different pipeline definition than the one being updated.

---

# Resolution

The pipeline was standardized around the root-level Jenkins files:

```
/Jenkinsfile
/script.groovy
```

The Jenkinsfile was updated to properly load and reference the external Groovy functions.

Before:

```groovy
buildApp()
```

After:

```groovy
gv.buildApp()
```

The Groovy script was loaded into a variable:

```groovy
def gv

gv = load 'script.groovy'
```

The `gv` variable represents the loaded Groovy script object and allows Jenkins to execute functions defined inside the script.

---

# Final Pipeline Architecture

```
GitHub Repository
        |
        v
Jenkins Multibranch Pipeline
        |
        v
Jenkinsfile
        |
        v
Load script.groovy
        |
        +--> buildApp()
        |
        +--> testApp()
        |
        +--> deployApp()
```

---

# Successful Pipeline Execution

The final pipeline successfully completed all stages:

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
```

Final result:

```
Finished: SUCCESS
```

---

# Key Lessons Learned

## Jenkins Execution Flow

A Jenkins Multibranch Pipeline execution depends on:

1. Git repository connection
2. Branch indexing
3. Jenkinsfile discovery
4. SCM checkout
5. Pipeline execution

Always verify which branch and commit Jenkins is executing.

---

## Repository Structure Matters

Jenkins does not automatically know which pipeline file to use.

The configured Script Path determines the Jenkinsfile location.

Example:

```
Script Path: Jenkinsfile
```

means Jenkins looks for:

```
/Jenkinsfile
```

not:

```
/jenkins-shared-library/Jenkinsfile
```

---

## Debugging Approach

When troubleshooting Jenkins failures:

1. Review Jenkins console output
2. Verify the branch being built
3. Verify the commit hash Jenkins checked out
4. Confirm the Jenkinsfile path
5. Confirm required files exist in the workspace
6. Validate pipeline syntax and loaded scripts

---

# Future Improvements

Planned enhancements:

- Integrate Maven build automation
- Build Docker images within Jenkins
- Push images to DockerHub
- Publish artifacts to Nexus Repository
- Add security scanning with Trivy
- Add CI/CD quality gates
- Deploy to Kubernetes infrastructure

---

# Technologies Used

- Jenkins 2.555.1
- GitHub
- Groovy
- Docker
- Maven
- Nexus Repository
- Linux
