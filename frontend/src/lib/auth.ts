import Keycloak from "keycloak-js"

async function createAuth() {
  const keycloak = new Keycloak({
    clientId: "ris-norms-local",
    realm: "ris",
    url: "http://localhost:8443",
  })

  let initialized = false
  try {
    initialized = await keycloak.init({
      onLoad: "login-required",
      checkLoginIframe: false,
      pkceMethod: "S256",
      scope: "profile email",
    })
  } catch (e) {
    console.error(e)
  }

  console.log("keycloak initialized successfully: ", initialized)

  return {
    isAuthenticated() {
      return keycloak.authenticated
    },
    getToken() {
      return keycloak.token
    },
  }
}

export const auth = await createAuth()
