import { Outlet } from 'react-router-dom';
import { ProfileCompletionGuard } from '../components/ProfileCompletionGuard';

export const ProfileCompletionGuardLayout = () => (
  <ProfileCompletionGuard>
    <Outlet />
  </ProfileCompletionGuard>
);
