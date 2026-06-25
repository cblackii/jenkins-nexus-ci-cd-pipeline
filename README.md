# Jenkins Nexus CI/CD Pipeline

## Overview

This project demonstrates a complete CI/CD pipeline using Jenkins, Maven, Docker, and Nexus Repository Manager.

## Technologies

- Jenkins
- Git
- GitHub
- Maven
- Docker
- Nexus Repository
- Java

## Architecture

(Add architecture diagram here)

## Pipeline Flow

1. Developer pushes code to GitHub
2. Jenkins detects changes
3. Jenkins checks out the source code
4. Maven builds the application
5. Unit tests execute
6. Artifact is uploaded to Nexus
7. Docker image is built
8. Image is pushed to the registry

## Repository Structure

```text
.
├── Jenkinsfile
├── BasicJenkinsfile
├── Dockerfile
├── pom.xml
├── src/
├── docs/
├── diagrams/
└── screenshots/
```

## Lessons Learned

- Creating Jenkins Pipelines
- Multibranch Pipelines
- Jenkins Credentials
- Nexus Integration
- Docker Builds
- Git Repository Organization
- Troubleshooting Jenkins Git Cache Issues
