pipeline {
    agent any
    stages {
        stage ('Git Pull') {
            steps {
                git url: 'https://github.com/budcoded/ConversionMicroservices.git',
                branch: 'main'
            }
        }

        stage ('Maven Build API Gateway') {
            steps {
                sh 'cd api-gateway'
                sh 'mvn clean install'
            }
        }

        stage ('Maven Build Naming Server') {
            steps {
                sh 'cd naming-server'
                sh 'mvn clean install'
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
                sh 'docker build -t budcoded/api-gateway:latest ./api-gateway/'
                sh 'docker build -t budcoded/naming-server:latest ./naming-server/'
                sh 'docker build -t budcoded/currency-exchange-service:latest ./currency-exchange-service/'
                sh 'docker build -t budcoded/currency-conversion-service:latest ./currency-conversion-service/'
            }
        }

        stage ('Push docker images') {
            steps {
                sh 'docker login -u budcoded -p budcodedbudcoded'
                sh 'docker push budcoded/api-gateway:latest'
                sh 'docker push budcoded/naming-server:latest'
                sh 'docker push budcoded/currency-exchange-service:latest'
                sh 'docker push budcoded/currency-conversion-service:latest'
            }
        }
    }
}