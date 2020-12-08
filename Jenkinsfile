pipeline{
    agent any
        stage ('Compile Stage') {
                    sh 'mvn clean install'
        }

    stage ('Test Stage') {
                    sh 'mvn test'
        }

        stage ('Cucumber Reports') {
                cucumber buildStatus: "UNSTABLE",
                    fileIncludePattern: "*/cucumberReport/cucumber.json",
                    jsonReportDirectory: 'target/cucumberReport', sortingMethod: 'ALPHABETICAL'
        }
    }