// const OktaJwtVerifier = require('@okta/jwt-verifier');

//Authendicate and validate access token
function validationRequired(req, res, next, scopes) {

	//Substract the Access token
  const authHeader = req.headers.authorization || '';
  const match = authHeader.match(/Bearer (.+)/);
  const aud = '';

  if (!match) {
    return res.status(401).end();
  }
  const accessToken = match[1];

	//Initiate OktaJwtVerifier
  // const verifier = new OktaJwtVerifier({
  //   issuer: '',
  //   clientId: 'okta.client.id',
  //   assertClaims: {
  //     aud: aud,
  //     'scp.includes': scopes
  //   }
  // });

	//Verify the access token
  // verifier.verifyAccessToken(accessToken, aud)
  //   .then((jwt) => {
  //     req.jwt = jwt;
  //     next();
  //   })
  //   .catch((err) => {
  //     res.status(401).send(err.message);
  //   });
  // }

module.exports.validationRequired = validationRequired;
