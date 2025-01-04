def check_out() {
    echo 'Checking out code...'
    checkout scm
}

def setup_java() {
    echo 'Setting up Java 17...'
    sh 'sudo apt update'
    sh 'sudo apt install -y openjdk-17-jdk'
}

def setup_maven() {
    echo 'Setting up Maven...'
    sh 'sudo apt install -y maven'
}

def build_project() {
    echo 'Building project with Maven...'
    sh 'mvn clean package'
}

def upload_artifact(String artifactPath) {
    echo 'Uploading artifact...'
    archiveArtifacts artifacts: artifactPath, allowEmptyArchive: true
}

def run_application() {
    echo 'Running Spring Boot application...'
    sh 'nohup mvn spring-boot:run &'
    sleep(time: 15, unit: 'SECONDS')

    def publicIp = sh(script: "curl -s https://checkip.amazonaws.com", returnStdout: true).trim()
    echo "The application is running and accessible at: http://${publicIp}:8080"
}

