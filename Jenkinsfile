pipeline {
  agent any

  environment {
    IMAGE_NAME = 'letmehear/blog-app'
    IMAGE_TAG = 'latest'
  }

  stages {
    stage('Clone source') {
      steps {
        git 'https://github.com/letmehear159/OOSE_Blog_Comic_BackEnd.git'
      }
    }

    stage('Load Env') {
      steps {
        script {
          def props = readFile('.env').split('\n')
          for (line in props) {
            if (line && line.contains('=')) {
              def (key, value) = line.split('=', 2)
              env."${key.trim()}" = value.trim()
            }
          }
        }
      }
    }

  stage('Build Native Image') {
    steps {
      sh './mvnw -Pnative native:compile -DskipTests'
    }
  }

    stage('Build Docker Image') {
      steps {
        sh 'docker build -t $IMAGE_NAME:$IMAGE_TAG .'
      }
    }

    stage('Push to Docker Hub') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'docker-hub-creds', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
          sh '''
            echo "$PASSWORD" | docker login -u "$USERNAME" --password-stdin
            docker push $IMAGE_NAME:$IMAGE_TAG
          '''
        }
      }
    }

    stage('Notify or Trigger Render') {
      steps {
        echo 'Image pushed. Go to Render to update (or trigger deploy via API).'
      }
    }
  }
}
