pipeline{
	agent any
	stages{
		stage('---clean---'){
			steps{
				bat "mvn clean"
			}
		}
		stage('---test---'){
			steps{
				bat "mvn test"
			}
		}
		stage('---package--0'){
			steps{
				sh "mvn package"
			}
		}
		
	}
}