package jetbrains.hub.oauth2.client

import jetbrains.hub.oauth2.client.loader.ClientAuthTransport
import jetbrains.hub.oauth2.client.source.RefreshableTokenSource
import org.jetbrains.spek.api.Spek
import java.net.URI

class ResourceOwnerFlowSpek : Spek({
    describe("Resource Owner Flow") {
        val tokenEndpoint = URI.create("https://hub.jetbrains.com/api/rest/oauth2/token")
        val clientID = "1234-3213-3123"
        val clientSecret = "topsecret"
        val username = "user"
        val password = "secret"
        val scopeElement = "0-0-0-0-0"
        val scope = listOf(scopeElement, clientID)


        val getFlow: OAuth2Client.(ClientAuthTransport) -> RefreshableTokenSource = {
            resourceOwnerFlow(tokenEndpoint, username, password, clientID, clientSecret, scope, it)
        }

        itShouldBeValidTokenSource(tokenEndpoint, clientID, clientSecret, mapOf(
                "grant_type" to "password",
                "username" to username,
                "password" to password,
                "scope" to "$scopeElement $clientID"
        ), getFlow)

        itShouldBeRefreshableTokenSource(getFlow)
    }
})