import { useAuth0 } from "@auth0/auth0-react";
import { useEffect, useState } from "react";
import { config } from "../config";
import { LocationListElement } from "./LocationListElement";
import { Stack } from "@mantine/core";

export const LocationList = ({isManagementView}) => {
    const { getAccessTokenSilently, isLoading } = useAuth0();
    const [ locations, setLocations ] = useState([]);
    const [ loading, setLoading ] = useState(true);
    const [ error, setError ] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                let response;

                if (isManagementView) {
                    const token = await getAccessTokenSilently();
                    response = await fetch(`${config.API_URL}/me/locations`, {
                    headers: {
                        authorization: `Bearer ${token}`,
                        'Content-Type': 'application/json',
                    }
                })} else {
                    response = await fetch(`${config.API_URL}/locations`);
                }

                if (!response.ok) throw new Error(response.statusText);

                const json = await response.json();

                setLocations(json);
            } catch (error) {
                setError(error.message || 'An unknown error occurred');
            } finally {
                setLoading(false);
            }
        }

        if (!isLoading)
            fetchData();
            
    }, [getAccessTokenSilently, isLoading]);


    const removeLocation = (id) => {
        const newLocations = locations.filter(l => l.id !== id)
        setLocations(newLocations);
    }

    if (loading) return <p>Loading...</p>;
    if (error) return <p>Error: {error}</p>;

    if (locations.length === 0) return <div>No tiene sucursales</div>

    return (
        <Stack>
            {locations.map(loc => <LocationListElement
                key={loc.id}
                location={loc}
                isManagementView={isManagementView}
                onDelete={removeLocation}
                />)}
        </Stack>
    );
}