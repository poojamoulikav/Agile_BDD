pipeline{
    
    def mavenHome = tool name: "maven3.6"
    
    agent any
        stage ('Compile Stage') {
                    sh "${mavenHome}/bin/mvn clean install"
        }

    stage ('Test Stage') {
                    sh "${mavenHome}/bin/mvn test"
        }

        stage ('Cucumber Reports') {
                cucumber buildStatus: "UNSTABLE",
                    fileIncludePattern: "*/cucumberReport/cucumber.json",
                    jsonReportDirectory: 'target/cucumberReport', sortingMethod: 'ALPHABETICAL'
        }
    }
