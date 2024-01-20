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
				withAWS(credentials:'VennyAWSAccount'){
					//sh 'aws ecr get-login-password --region region | docker login --username InfraUser --password-stdin 905418163709.dkr.ecr.us-east-1.amazonaws.com'
					sh 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 905418163709.dkr.ecr.us-east-1.amazonaws.com'
					sh 'docker tag helloworld:latest 905418163709.dkr.ecr.us-east-1.amazonaws.com/hello-world'
				}
				withAWS(credentials:'VennyAWSAccount'){
					sh 'docker push 905418163709.dkr.ecr.us-east-1.amazonaws.com/hello-world'
				}
			}
		}
	}
}
