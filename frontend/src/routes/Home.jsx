import { RestaurantCardList } from "../components/RestaurantCardList";
import { Stack, Title, Button } from "@mantine/core";
import { LocationList } from "../components/LocationList";
import { Link } from "react-router-dom";

export const Home = () => (
  <>
    <Stack>
      <Title>Restaurantes Cercanos</Title>
      <LocationList />
    </Stack>
  </>
);
