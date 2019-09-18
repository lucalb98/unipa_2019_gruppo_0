import { KeycloakService } from 'keycloak-angular';
import { environment } from '../environments/environment';

export function initializer(keycloak: KeycloakService): () => Promise<any> {
  return (): Promise<any> => {
    return new Promise(async (resolve, reject) => {
      try {
        await keycloak.init({
          config: {
            url: environment.keycloak.url,
            realm: environment.keycloak.realm,
            clientId: environment.keycloak.clientId,
            // credentials: {
            //   secret: 'f67915d2-e90d-4e53-a670-09741f2f6bc5'
            // }
          },
          initOptions: {
            onLoad: 'check-sso',
            checkLoginIframe: false
          },
          enableBearerInterceptor:true,
          bearerExcludedUrls: ['/api/otp','/otp']
        });
        resolve();
      } catch (error) {
        reject(error);
      }
    });
  };
}
