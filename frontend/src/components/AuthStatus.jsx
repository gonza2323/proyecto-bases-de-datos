import { useContext, useState } from 'react';
import { Link } from 'react-router-dom';
import { useMediaQuery } from '@mantine/hooks';
import {
  Menu,
  Button,
  Drawer,
  Burger,
  Stack,
  Group,
} from '@mantine/core';
import { UserContext } from '../contexts/UserContext'; // adjust path as needed
import { LoginButton } from './LoginButton';
import { SignupButton } from './SignupButton';
import { LogoutButton } from './LogoutButton';
import { useAuth0 } from '@auth0/auth0-react';

export const AuthStatus = () => {
  const { isLoading, isAuthenticated, user } = useContext(UserContext);
  const [drawerOpened, setDrawerOpened] = useState(false);
  const isMobile = useMediaQuery('(max-width: 768px)');
  const { logout } = useAuth0();

  const handleLogout = () => {
    logout({
      logoutParams: {
        returnTo: window.location.origin,
      },
    });
  };

  if (isLoading) return <div>Cargando...</div>;

  if (isAuthenticated) {
    return (
      <Menu withArrow position="bottom-end" >
        <Menu.Target>
          <Button variant="default" bg="red" c="white">
            {user?.name || user?.email || 'Account'}
          </Button>
        </Menu.Target>
        <Menu.Dropdown>
          <Menu.Item component={Link} to="/manage">
            Administrar Sucursales
          </Menu.Item>
          {user?.sub === "google-oauth2|107018146032061883735" && <Menu.Item component={Link} to="/admin">
            Administrar Backups
          </Menu.Item>}
          <Menu.Item onClick={handleLogout}>
            Cerrar Sesión
          </Menu.Item>
        </Menu.Dropdown>
      </Menu>
    );
  }

  if (!isAuthenticated) {
    if (isMobile) {
      return (
        <>
          <Burger
            opened={drawerOpened}
            color='white'
            onClick={() => setDrawerOpened((prev) => !prev)}
            aria-label="Toggle menu"
          />
          <Drawer
            opened={drawerOpened}
            onClose={() => setDrawerOpened(false)}
            title="Menu"
            padding="md"
            size="md"
            position="right"
          >
            <Stack>
              <LoginButton />
              <SignupButton />
            </Stack>
          </Drawer>
        </>
      );
    }

    return (
      <Group spacing="md">
        <LoginButton />
        <SignupButton />
      </Group>
    );
  }

  return null; // fallback
};