import { useState } from "react";
import { LocationList } from "../components/LocationList";

export const Locations = () => {
  const [addLocationFormVisible, setLocationFormVisible] = useState(false);

  return (
    <>
      <h1>Sucursales</h1>
      <LocationList />
      <button onClick={() => setLocationFormVisible(true)}>Agregar Sucursal</button>
      { addLocationFormVisible && <AddLocationForm />}
    </>
  )
};


const AddLocationForm = () => {
  return (
    <h2>Some Form</h2>
  )
}