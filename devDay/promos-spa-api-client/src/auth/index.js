import router from '../router' //router: required to redirect users
import {OktaAuth} from '@okta/okta-auth-js' //okta authjs: required login in Okta

//constants
const OKTA_ORG = 'https://oktane21####.oktapreview.com';
const AUTHZ_SERVER = OKTA_ORG + '/oauth2/' + 'ausxxxxxxxxxxxxxx';
const AUTHZ_URL = AUTHZ_SERVER + '/v1/authorize';
const CLIENT_ID = okta.client.id; // command line env var: OKTA_CLIENT_ID
const REDIRECT_URL = window.location.origin + '/tokenCallback';
const SCOPES = ['openid', 'profile', 'email'];
const CUSTOM_AUTHN_PAGE = 'http://localhost:8082/osw_complete.html';
const CUSTOM_AUTHN_STATE_URL = CUSTOM_AUTHN_PAGE + '?relayState=' + window.location.origin + '/retrieveTokensXHR';
const WORKFORCE_IDENTITY_MODE = true;

//variables
var state = '/profile'; // default page
var grantType;
var responseType;
var pkceFlag;
if (!OktaAuth.features.isPKCESupported()) {
  console.log('PKCE is not supported in this browser');
  grantType = 'implicit';
  responseType = ['token', 'id_token'];
  pkceFlag = false;
}

//Initiate the Okta Client
const OKTA_AUTH_JS = new OktaAuth({
  tokenManager: {
      storage: 'sessionStorage'
  },
  grantType: grantType,
  url: OKTA_ORG,
  clientId: CLIENT_ID,
  redirectUri: REDIRECT_URL,
  issuer: AUTHZ_SERVER,
  authorizeUrl: AUTHZ_URL,
  pkce: pkceFlag
});

/**
 * TODO: retrieveTokensRedirect
 * Starts the OAuth login process via redirect or
 * request via XHR using the Okta Auth JS
 * access public
 */
export function retrieveTokensRedirect() {
  OKTA_AUTH_JS.token.getWithRedirect({
    responseType: responseType,
    scopes: SCOPES,
    state: state
  });
}

/**
 * TODO: tokenCallback
 * Called by Okta after requesting tokens via redirect.
 * Extract and validate tokens from the redirect and
 * save tokens in session storage
 * storeTokensFromRedirect perfoms the token validation
 * redirects to state param value
 * access public
 */
export function tokenCallback() {
  var url = new URL(window.location.href);
  OKTA_AUTH_JS.storeTokensFromRedirect()
  .then(function() {
    state = url.searchParams.get('state');
    router.push(state);
  });
}

/**
 * TODO: getIdToken
 * Get idToken from tokenManager
 * return Promise Object idToken
 */
export function getIdToken() {
  return OKTA_AUTH_JS.tokenManager.get('idToken');
}

/**
 * TODO: getAccessToken
 * Get access from tokenManager
 * return Promise Object accessToken
 */
export function getAccessToken() {
  return OKTA_AUTH_JS.tokenManager.get('accessToken');
}

/**
 * TODO: validateAccess
 * validates if a user can access protected pages based on ID Token
 * param Object to - info about request destination
 * param Object from - info about request origin
 * param Object next - for triggering the next step in the Vue lifecycle
 * if Workforce mode, redirect to Okta.com,
 * if Customer mode, redirect to OSW page
*/
export function validateAccess(to, from, next) {
  state = to.fullPath;
  getIdToken()
  .then(function (token) {
    if (token && !OKTA_AUTH_JS.tokenManager.hasExpired(token)) {
      next();
    } else {
      OKTA_AUTH_JS.tokenManager.clear();
      if (WORKFORCE_IDENTITY_MODE) {
        router.push('/retrieveTokensRedirect');
      } else { // CIAM MODE
        sessionStorage.setItem('state', state);
        window.location.replace(CUSTOM_AUTHN_STATE_URL);
      }
    }
  });
}

/**
 * TODO: logout
 * Clear the id_token and access_token from tokenManager
 * if Workforce, also redirect to app /home
 * if Customer, also sign out of Okta, redirect to OSW page
 * access public
 */
export function logout() {
  if (WORKFORCE_IDENTITY_MODE) {
    OKTA_AUTH_JS.tokenManager.clear();
    router.push('/home');
  } else { //CIAM mode
    OKTA_AUTH_JS.signOut({
        postLogoutRedirectUri: CUSTOM_AUTHN_PAGE
    });
  }
}

/**
 * TODO: retrieveTokensXHR
 * Starts the OAuth login process via
 * request via XHR using the Okta Auth JS
 * should only be used when the user has
 * already has a session at Okta
 * access public
 */
export function retrieveTokensXHR() {
  OKTA_AUTH_JS.token.getWithoutPrompt({
    responseType: responseType,
    scopes: SCOPES
  })
  .then(function(res) {
    OKTA_AUTH_JS.tokenManager.setTokens(res.tokens);
    router.push(state);
  });
}

/**
 * TODO: setState
 * Set state var equal state from Session Storage
 */
export function setState(to, from, next) {
  state = sessionStorage.getItem('state') || '/profile';
  sessionStorage.removeItem('state');
  next();
}

/**
 * TODO: getAuthHeader
 * get Authorization header for REST requests with OAuth
 * access public
 * return Object headers
 */
export async function getAuthHeader() {

}
