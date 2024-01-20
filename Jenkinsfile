pipeline {
    agent any
    stages {
        stage ('Git Pull') {
            steps {
                git url: 'https://github.com/budcoded/ConversionMicroservices.git',
                branch: 'kubernetes-deployment'
            }
        }

        stage ('Maven Build Currency Exchange Service') {
            steps {
                sh 'cd currency-exchange-service'
                sh 'mvn clean install'
            }
        }

        stage ('Maven Currency Conversion Service') {
            steps {
                sh 'cd currency-conversion-service'
                sh 'mvn clean install'
            }
        }

        stage ('Build docker image') {
            steps {
                sh 'docker build -t budcoded/currency-exchange-service-k8s:latest ./currency-exchange-service/'
                sh 'docker build -t budcoded/currency-conversion-service-k8s:latest ./currency-conversion-service/'
            }
        }

        stage ('Push docker images') {
            steps {
                sh 'docker login -u budcoded -p budcodedbudcoded'
                sh 'docker push budcoded/currency-exchange-service-k8s:latest'
                sh 'docker push budcoded/currency-conversion-service-k8s:latest'
            }
        }
    }
}