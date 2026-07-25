# Jenkins Multibranch CI/CD Pipeline

## Overview

Implemented a Jenkins multibranch CI/CD pipeline integrated with GitHub SCM.

The pipeline automates application lifecycle stages:
- Build
- Test
- Deploy

## Architecture

GitHub Repository
        |
        v
Jenkins Multibranch Pipeline
        |
        v
Jenkinsfile
        |
        v
Groovy Pipeline Functions
        |
        +--> Build
        +--> Test
        +--> Deploy

## Technologies

- Jenkins 2.555.1
- GitHub
- Groovy
- Docker
- Nexus Repository
- Maven

## Key Engineering Lessons

- Jenkins Script Path determines which pipeline file executes
- Multibranch pipelines dynamically discover Git branches
- Shared Groovy functions improve pipeline reuse
