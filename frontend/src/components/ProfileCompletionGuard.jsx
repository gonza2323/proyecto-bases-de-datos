import { Outlet } from 'react-router-dom';
import { useAuth0 } from "@auth0/auth0-react";
import { useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { config } from "../config"

export const ProfileCompletionGuard = ({ children }) => {
  const { user, getAccessTokenSilently, isLoading } = useAuth0();
  const navigate = useNavigate();
  const location = useLocation();
  const [isCheckingProfile, setIsCheckingProfile] = useState(true);
  const [hasCompletedProfile, setHasCompletedProfile] = useState(false);

  const checkProfileCompletion = async () => {
    try {
      const token = await getAccessTokenSilently();
      const response = await fetch(`${config.API_URL}/users/signup-completion`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });

      if (response.ok) {
        const data = await response.json();
        return data.isCompleted;
      } else {
        throw new Error('Failed to check profile status');
      }
    } catch (error) {
      console.error('Error checking profile completion:', error);
      return true;
    }
  };

  useEffect(() => {
    const checkProfile = async () => {
      if (isLoading || !user) return;

      setIsCheckingProfile(true);
      
      try {
        const profileCompleted = await checkProfileCompletion();
        setHasCompletedProfile(profileCompleted);
        
        if (!profileCompleted) {
          // Store current location for redirect after profile completion
          navigate('/complete-profile', { 
            state: { returnTo: location.pathname + location.search },
            replace: true 
          });
        }
      } catch (error) {
        console.error('Profile check failed:', error);
        // On error, allow access (fail open)
        setHasCompletedProfile(true);
      } finally {
        setIsCheckingProfile(false);
      }
    };

    checkProfile();
  }, [user, isLoading, navigate, location]);

  // Show loading while checking profile status
  if (isLoading || isCheckingProfile) {
    return null;
  }

  // If profile not completed, navigation to complete-profile already happened
  // This component won't render children in that case
  if (!hasCompletedProfile) {
    return null;
  }

  // Profile completed, render the protected content
  return children ?? <Outlet />;
};