pipeline {

    agent any

    stages {
        stage('Build') {
            agent {
                docker {
                    image 'maven:3-openjdk-17'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            steps {
                sh 'mvn clean'
                sh 'mvn compile'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn -Dtest=UserDetailsServiceTest#should_return_valid_company_matching_email_and_type test'
                sh 'mvn -Dtest=UserDetailsServiceTest#should_return_valid_agent_matching_email_and_type test'
                sh 'mvn -Dtest=UserDetailsServiceTest#should_throw_UsernameNotFoundException test'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
