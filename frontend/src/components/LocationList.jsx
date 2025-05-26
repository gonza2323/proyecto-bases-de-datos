import { useAuth0 } from "@auth0/auth0-react";
import { useEffect, useState } from "react";
import { config } from "../config";
import { div } from "framer-motion/client";
import { LocationListElement } from "./LocationListElement";

export const LocationList = () => {
    const { getAccessTokenSilently, isLoading } = useAuth0();
    const [ locations, setLocations ] = useState([]);
    const [ loading, setLoading ] = useState(true);
    const [ error, setError ] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const token = await getAccessTokenSilently();
                const response = await fetch(`${config.API_URL}/locations`, {
                    headers: {
                        authorization: `Bearer ${token}`,
                        'Content-Type': 'application/json',
                    }
                })

                if (!response.ok) throw new Error(response.statusText);

                const json = await response.json();

                setLocations(json.locations);
            } catch (error) {
                setError(error.message || 'An unknown error occurred');
            } finally {
                setLoading(false);
            }
        }

        if (!isLoading)
            fetchData();
            
    }, [getAccessTokenSilently, isLoading]);

    if (loading) return <p>Loading...</p>;
    if (error) return <p>Error: {error}</p>;

    return (
        <div>
            {locations.map(loc => <LocationListElement key={loc.id} location={loc} />)}
        </div>
    );
}