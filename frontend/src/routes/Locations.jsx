import { useState } from "react";
import { LocationList } from "../components/LocationList";
import { Link } from "react-router-dom";
import { Button, Stack, Title } from "@mantine/core";

export const Locations = () => {
  return (
    <Stack>
      <Stack>
        <Title>Mis Sucursales</Title>
        <LocationList isManagementView/>
      </Stack>
      <Button component={Link} to="/manage/locations/new">Agregar Sucursal</Button>
    </Stack>
  )
};
