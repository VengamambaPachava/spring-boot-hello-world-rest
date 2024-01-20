pipeline {
	agent none
	stages {
		stage('Get Code'){
			agent any
			steps {
				checkout scm
			}
		}	
		stage('Create Package'){
			agent any
			steps {
				sh 'mvn clean install'
			}	
		}	
		stage('Build Image') {
			agent any
			steps {
				sh 'docker build -t helloworld:${BUILD_NUMBER} .'
				sh 'docker tag helloworld:${BUILD_NUMBER} helloworld:latest'
			}
		}
		stage('Deploy Image') {
			agent any
			steps {
				sh 'docker tag helloworld:latest 905418163709.dkr.ecr.us-east-1.amazonaws.com/hello-world'
				sh 'docker push 905418163709.dkr.ecr.us-east-1.amazonaws.com/hello-world'
			}
		}
	}
}
