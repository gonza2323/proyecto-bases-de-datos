import { useUser } from "./UserHook";

export const useAuthenticatedUser = () => {
  const { isAuthenticated, userDetails, isFullyLoaded } = useUser();
  
  if (!isAuthenticated) {
    throw new Error('User must be authenticated to use this hook');
  }
  
  return { userDetails, isFullyLoaded };
};
