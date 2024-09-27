// ** JWT Service Import
import JwtService from './jwtService';
import { JwtConfig } from './jwtService'; // Make sure to export JwtConfig from jwtService.ts

// ** Export Service as useJwt
export default function useJwt(jwtOverrideConfig: Partial<JwtConfig>) {
  const jwt = new JwtService(jwtOverrideConfig);

  return {
    jwt
  };
}
