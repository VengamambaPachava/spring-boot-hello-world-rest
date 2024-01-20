pipeline {
	agent none
	stages {
		stage('Get Code'){
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
				sh 'docker build -t venny/helloworld:${BUILD_NUMBER} .'
				sh 'docker tag venny/helloworld:${BUILD_NUMBER} venny/helloworld:latest'
			}
		}
		stage('Deploy Image') {
			agent any
			steps {
				sh 'docker push venny/helloworld:latest'
			}
		}
	}
}
