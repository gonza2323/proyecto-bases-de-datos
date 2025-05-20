import React, { useEffect, useState } from 'react';
import RestaurantCard from './RestaurantCard';
import config from '../config';


function RestaurantCardList() {
  const [restaurants, setRestaurants] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetch(`${config.API_URL}/restaurants`)
      .then(res => {
        if (!res.ok) throw new Error('Network error');
        return res.json();
      })
      .then(data => {
        setRestaurants(data);
        setLoading(false);
      })
      .catch(err => {
        setError(err.message);
        setLoading(false);
      });
  }, []);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error}</p>;
  if (restaurants.length === 0) return (
    <>
      <h1>Restaurantes</h1>
      <div>
        No se encontraron restaurantes
      </div>
    </>
  )

  return (
    <>
      <h1>Restaurantes</h1>
      <div>
        {restaurants.map(r => (
          <RestaurantCard key={r.id} restaurant={r} />
        ))}
      </div>
    </>
  )
}

export default RestaurantCardList;