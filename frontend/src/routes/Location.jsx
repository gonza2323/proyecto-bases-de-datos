import { Container, Stack, Title, Text, Button } from "@mantine/core"
import { useParams } from "react-router-dom";


export const Location = ({isManagementView}) => {

  const { locationId } = useParams();

  return (
    <Container>
      <Stack>
        <Title>Location #{locationId}</Title>
        <Text>Some text</Text>
        {isManagementView && <Button>Add Menu Item</Button>}
      </Stack>
    </Container>
  )
}