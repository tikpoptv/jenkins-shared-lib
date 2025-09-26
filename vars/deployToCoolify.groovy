def call(String projectName, String uuidCredId, String tokenCredId) {
    withCredentials([
        string(credentialsId: tokenCredId, variable: 'COOLIFY_TOKEN'),
        string(credentialsId: uuidCredId, variable: 'COOLIFY_UUID')
    ]) {
        sh """
        echo "🚀 Triggering deploy on Coolify for ${projectName}..."
        RESPONSE=\$(curl -s -o response.json -w "%{http_code}" \\
          -X GET "https://coolify.phitik.com/api/v1/deploy?uuid=\$COOLIFY_UUID&force=true" \\
          -H "Authorization: Bearer \$COOLIFY_TOKEN")

        echo "HTTP Status: \$RESPONSE"
        cat response.json

        if [ "\$RESPONSE" -ne 200 ]; then
          echo "❌ Deploy failed (HTTP \$RESPONSE)"
          exit 1
        fi

        echo "✅ Deploy triggered successfully!"
        """
    }
}