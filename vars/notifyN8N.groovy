def call(String status, String details) {
    withCredentials([
        string(credentialsId: 'n8n-webhook', variable: 'N8N_WEBHOOK_URL')
    ]) {
        sh(
            script: """curl -s -X POST -H "Content-Type: application/json" \
              -d '{
                "status": "${status}",
                "project": "${env.PROJECT_NAME}",
                "branch": "${env.PROJECT_BRANCH}",
                "commit": "${env.GIT_COMMIT ?: "manual-trigger"}",
                "build_url": "${env.BUILD_URL}",
                "details": "${details}"
              }' "$N8N_WEBHOOK_URL"
            """,
            mask: true
        )
    }
}
