import { createContext, useContext, useEffect, useMemo, useState } from 'react';
import { useAuth0 } from '@auth0/auth0-react';
import { config } from '../config';

export const UserContext = createContext();

export const UserProvider = ({ children }) => {
  const { user, isAuthenticated, isLoading, getAccessTokenSilently } = useAuth0();
  const [userDetails, setUserDetails] = useState(null);
  const [detailsLoading, setDetailsLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchUserDetails = async () => {
    if (!isAuthenticated || !user) {
      setUserDetails(null);
      return;
    }

    try {
      setDetailsLoading(true);
      setError(null);
      
      const token = await getAccessTokenSilently();
      
      const response = await fetch(`${config.API_URL}/me`, {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) {
        throw new Error('Failed to fetch user details');
      }
      
      const details = await response.json();
      setUserDetails(details);
    } catch (err) {
      console.error('Error fetching user details:', err);
      setError(err.message);
    } finally {
      setDetailsLoading(false);
    }
  };

  // Fetch user details when authentication state changes
  useEffect(() => {
    if (!isLoading) {
      fetchUserDetails();
    }
  }, [isAuthenticated, user, isLoading]);

  // Clear user details on logout
  useEffect(() => {
    if (!isAuthenticated) {
      setUserDetails(null);
      setError(null);
    }
  }, [isAuthenticated]);

  const value = useMemo(() => ({
    user: {
      email: user?.email || null,
      name: userDetails?.name || null,
      address: userDetails?.address || null,
    },
    isAuthenticated,
    error,
    isLoading: isLoading || detailsLoading,
    updateUserDetails: fetchUserDetails
  }), [user, userDetails, isAuthenticated, error, isLoading, detailsLoading]);

  return (
    <UserContext.Provider value={value}>
      {children}
    </UserContext.Provider>
  );
};
