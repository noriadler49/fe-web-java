pipeline {
    agent any

    environment {
        TOMCAT_PATH = 'C:\Users\Norial\dev\apache-tomcat-11.0.7'
    }

    stages {
        stage('Clone') {
            steps {
                echo 'Cloning source code from GitHub'
                git branch: 'main', url: 'https://github.com/noriadler49/crossplatform-project-endterm.git'
            }
        }

        stage('Build WAR') {
            steps {
                echo 'Compiling and packaging WAR file'
                bat '''
                    mkdir build
                    javac -d build -cp "%TOMCAT_PATH%\\lib\\servlet-api.jar" -sourcepath src ^
                        src\\dao\\*.java ^
                        src\\model\\*.java ^
                        src\\controller\\*.java ^
                        src\\context\\*.java
                    
                    xcopy Web\\* build /E /I /Y
                    cd build
                    jar -cvf VinfastSystem.war *
                '''
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                echo 'Deploying WAR file to Tomcat'
                bat '''
                    if not exist "%TOMCAT_PATH%\\webapps" (
                        echo "Tomcat webapps folder not found!"
                        exit /b 1
                    )
                    copy build\\VinfastSystem.war "%TOMCAT_PATH%\\webapps\\" /Y
                '''
            }
        }

        stage('Restart Tomcat') {
            steps {
                echo 'Restarting Tomcat server'
                bat '''
                    call "%TOMCAT_PATH%\\bin\\shutdown.bat"
                    timeout /t 5
                    call "%TOMCAT_PATH%\\bin\\startup.bat"
                '''
            }
        }
    }
}