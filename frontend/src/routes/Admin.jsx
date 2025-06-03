import { useAuth0 } from "@auth0/auth0-react"
import { Button, Card, Group, Modal, Stack, Title, Text } from "@mantine/core"
import { config } from "../config";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";



export const Admin = () => {
  const { getAccessTokenSilently, isLoading, isAuthenticated, user } = useAuth0();
  const [shouldUpdate, triggerUpdate] = useState(false);
  const navigate = useNavigate();

  const createBackup = async () => {
    try {
      const token = await getAccessTokenSilently();
      const response = await fetch(`${config.API_URL}/database/dump`, {
        method: 'POST',
        headers: {
          authorization: `Bearer ${token}`,
          'Content-Type': 'application/json',
        }
      })

      if (!response.ok) throw new Error(response.statusText);
      triggerUpdate(!shouldUpdate);

    } catch (error) {
      console.error(error.message || 'An unknown error occurred');
    }
  }

  useEffect(() => {
    if (isAuthenticated && user.sub !== "google-oauth2|107018146032061883735") {
      navigate("/", replace);
    }
  }, [isLoading, isAuthenticated])

  return (
    <Stack>
      <Group>
        <Title>Backups</Title>
        <Button onClick={createBackup}>Crear Backup</Button>
      </Group>
      <BackupList shouldUpdate={shouldUpdate} />
    </Stack>
  )
}


const BackupList = ({shouldUpdate}) => {
  const { isLoading, getAccessTokenSilently } = useAuth0();
  const [backups, setBackups] = useState([]);
  const [loadingData, setLoadingData] = useState(true);
  const [modalOpen, setOpenModal] = useState(false);
  const [modalVariant, setModalVariant] = useState();
  const [selectedBackup, setSelectedBackup] = useState();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = await getAccessTokenSilently();
        const response = await fetch(`${config.API_URL}/database/backups`, {
          headers: {
            authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          }
        })

        if (!response.ok) throw new Error(response.statusText);

        const backupsResponse = await response.json();

        setBackups(backupsResponse);
      } catch (error) {
        setError(error.message || 'An unknown error occurred');
      } finally {
        setLoadingData(false);
      }
    }

    if (!isLoading)
      fetchData();

  }, [isLoading, getAccessTokenSilently, shouldUpdate]);

  const handleButtonPress = (index, action) => {
    setOpenModal(true);
    setModalVariant(action);
    setSelectedBackup(index);
  }

  const restoreBackup = async () => {
    try {
      setLoadingData(true);
      const params = new URLSearchParams({backupFile: backups[selectedBackup]});
      const token = await getAccessTokenSilently();
      const response = await fetch(`${config.API_URL}/database/restore?${params}`, {
        method: 'POST',
        headers: {
          authorization: `Bearer ${token}`,
          'Content-Type': 'application/json',
        }
      })

      if (!response.ok) throw new Error(response.statusText);
      setOpenModal(false);

    } catch (error) {
      console.error(error.message || 'An unknown error occurred');
    } finally {
      setLoadingData(false);
    }
  }

  const deleteBackup = async () => {
    try {
      setLoadingData(true);
      const token = await getAccessTokenSilently();
      
      const response = await fetch(`${config.API_URL}/database/backups/${backups[selectedBackup]}`, {
        method: 'DELETE',
        headers: {
          authorization: `Bearer ${token}`,
          'Content-Type': 'application/json',
        }
      })

      if (!response.ok) throw new Error(response.statusText);

      setOpenModal(false);
      setBackups(backups => backups.filter((_, i) => i !== selectedBackup));

    } catch (error) {
      console.error(error.message || 'An unknown error occurred');
    } finally {
      setLoadingData(false);
    }
  }

  const downloadBackup = async (backup) => {
    try {
      const token = await getAccessTokenSilently();
      const response = await fetch(`${config.API_URL}/database/backups/${encodeURIComponent(backup)}`, {
        headers: {
          authorization: `Bearer ${token}`,
          'Content-Type': 'application/json',
        }
      });
      if (response.ok) {
        const blob = await response.blob();
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = backup;
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
        document.body.removeChild(a);
      } else {
        console.error('Error downloading backup', response.status);
      }
    } catch (error) {
      console.error('Error downloading backup: ' + error.message);
    }
  };

  const handleConfirm = () => {
    if (modalVariant === 'restore') {
      console.log('restore')
      restoreBackup();
    } else if (modalVariant === 'delete') {
      console.log('delete')
      deleteBackup();
    }
  }

  if (isLoading || loadingData) return null;

  if (backups.length === 0) return <p>No se han realizado backups</p>;

  return (
    <>
      {backups.map((name, index) => <BackupCard
        key={name}
        index={index}
        name={name}
        isLoading={false}
        onButtonPress={handleButtonPress}
        download={downloadBackup}
      />)}
      <BackupModal
        opened={modalOpen}
        closeModal={() => setOpenModal(false)}
        variant={modalVariant}
        backup={backups[selectedBackup]}
        loading={loadingData}
        confirm={handleConfirm}
      />
    </>
  )
}


const BackupCard = ({ index, name, onButtonPress, download, isLoading }) => {
  return (
    <Card>
      <Group justify="space-between">
        <Title>{name}</Title>
        <Group>
          <Button onClick={() => download(name)} loading={isLoading}>Descargar</Button>
          <Button onClick={() => onButtonPress(index, 'delete')} loading={isLoading}>Eliminar</Button>
          <Button onClick={() => onButtonPress(index, 'restore')} loading={isLoading}>Restaurar</Button>
        </Group>
      </Group>
    </Card>
  )
}


const BackupModal = ({ variant, backup, closeModal, confirm, loading, ...rest }) => {

  const action = variant === 'restore' ? 'restaurar' : 'borrar'

  return (
    <Modal {...rest}
      title={variant === 'restore' ? 'Restaurar backup' : 'Borrar backup'}
      onClose={closeModal}
    >
      <Stack>
        <Text>Está seguro de que desea {action} el backup {backup} </Text>
        <Group>
          <Button onClick={closeModal} loading={loading}>Cancelar</Button>
          <Button onClick={confirm} loading={loading}>Aceptar</Button>
        </Group>
      </Stack>
    </Modal>
  )
}