def buildApp() {
    echo "Building application version ${params.VERSION}..."
}

def testApp() {
    echo 'Testing the application...'
}

def deployApp() {
    echo "Deploying application version ${params.VERSION}..."
}

return this
