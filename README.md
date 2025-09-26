# Jenkins Shared Library

Jenkins Shared Library for deploying to Coolify platform

## 📋 Features

- **deployToCoolify**: Function to trigger deployment to Coolify
- **notifyN8N**: Function to send notifications to N8N webhook

## 🚀 Installation

### 1. Add Shared Library in Jenkins

1. Go to **Manage Jenkins** > **Configure System**
2. Scroll down to **Global Pipeline Libraries**
3. Add new library:
   - **Name**: `jenkins-shared-lib`
   - **Default version**: `main`
   - **Source Code Management**: Git
   - **Project Repository**: URL of this repository

### 2. Configure Credentials in Jenkins

Create the following credentials in Jenkins:

1. **COOLIFY_TOKEN**: Token for Coolify API
2. **COOLIFY_UUID**: UUID of the project in Coolify
3. **COOLIFY_URL**: URL of the Coolify instance
4. **n8n-webhook**: N8N webhook URL for notifications

## 📖 Usage

### deployToCoolify

This function is used to trigger deployment to Coolify

#### Parameters

- `projectName` (String): Name of the project
- `uuidCredId` (String): Jenkins Credential ID for COOLIFY_UUID
- `tokenCredId` (String): Jenkins Credential ID for COOLIFY_TOKEN
- `urlCredId` (String): Jenkins Credential ID for COOLIFY_URL

#### Usage Example

```groovy
@Library('jenkins-shared-lib') _

pipeline {
    agent any
    
    stages {
        stage('Deploy to Coolify') {
            steps {
                deployToCoolify(
                    'my-awesome-project',
                    'coolify-uuid-cred',
                    'coolify-token-cred',
                    'coolify-url-cred'
                )
            }
        }
    }
}
```

### notifyN8N

This function is used to send notifications to N8N webhook

#### Parameters

- `status` (String): Status of the build/deployment (e.g., "success", "failure", "started")
- `details` (String): Additional details about the build/deployment

#### Usage Example

```groovy
@Library('jenkins-shared-lib') _

pipeline {
    agent any
    
    stages {
        stage('Build') {
            steps {
                echo 'Building application...'
            }
            post {
                success {
                    notifyN8N('success', 'Build completed successfully')
                }
                failure {
                    notifyN8N('failure', 'Build failed')
                }
            }
        }
    }
}
```

## 🔧 Coolify Setup

1. Go to Coolify dashboard
2. Select the project you want to deploy
3. Go to **Settings** > **API**
4. Create API token
5. Copy Project UUID

## 📝 Jenkinsfile Example

```groovy
@Library('jenkins-shared-lib') _

pipeline {
    agent any
    
    environment {
        // Set environment variables if needed
    }
    
    stages {
        stage('Build') {
            steps {
                echo 'Building application...'
                // Add build steps as needed
            }
        }
        
        stage('Deploy to Coolify') {
            steps {
                deployToCoolify(
                    'my-project',
                    'coolify-uuid-cred',
                    'coolify-token-cred', 
                    'coolify-url-cred'
                )
            }
        }
    }
    
    post {
        success {
            echo 'Deployment successful! 🎉'
            notifyN8N('success', 'Deployment completed successfully')
        }
        failure {
            echo 'Deployment failed! ❌'
            notifyN8N('failure', 'Deployment failed')
        }
    }
}
```

## 🛠️ Development

### File Structure

```
jenkins-shared-lib/
├── vars/
│   ├── deployToCoolify.groovy    # Function for deploying to Coolify
│   └── notifyN8N.groovy          # Function for sending N8N notifications
└── README.md                     # This documentation
```

### Adding New Functions

1. Create a new `.groovy` file in the `vars/` folder
2. Use the filename as the function name
3. Write the function following Jenkins Shared Library convention

## 📞 Support

If you have any issues or questions, please create an issue in this repository

## 📄 License

MIT License
