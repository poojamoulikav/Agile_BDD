node
{

def mavenHome = tool name: "maven3.6"
    
stage ('CodeCheckOut')    
    {
        git branch: 'pooja', credentialsId: 'GIT', url: 'https://github.com/poojamoulikav/Agile_BDD.git'
    }

stage ('Compile Stage')
    {
        sh "${mavenHome}/bin/mvn clean install -DskipTests"
    }

stage ('Test Stage')
    {
        sh "${mavenHome}/bin/mvn test"
    }

stage ('Cucumber Reports')
    {
       cucumber buildStatus: "UNSTABLE",
              fileIncludePattern: "**/cucumber.json",
              jsonReportDirectory: 'target'
     }
}
