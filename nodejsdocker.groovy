job('NodeJS Docker example tassos') {
    scm {
        git('git://github.com/tasostsaxur/thecode.git') {  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('DSL User')
            node / gitConfigEmail('tasosostsaxur@yahoo.com')
        }
    }
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        nodejs('nodejs') // this is the name of the NodeJS installation in 
                         // Manage Jenkins -> Configure Tools -> NodeJS Installations -> Name
    }
    steps {							// we don't have  the 'shell command' to do 'npm install' anymore
        dockerBuildAndPublish {     // cloudbees plugin command (v1.2+ required by JobDSL)
            repositoryName('tasostsaxur/docker-nodejs-demo')
            tag('${GIT_REVISION,length=9}') 	// ! every new container version (when we commit on github) will be the same as the git version
            registryCredentials('dockerhub')	// ! will create credentials with dockerhub as ID later
            forcePull(false)					// from the job dsl API page for docker
            forceTag(false)						// from the job dsl API page for docker
            createFingerprints(false)			// from the job dsl API page for docker
            skipDecorate()						// from the job dsl API page for docker
        }
    }
}