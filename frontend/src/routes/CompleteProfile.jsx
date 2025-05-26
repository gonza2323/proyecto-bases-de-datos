import { useState } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import { useNavigate, useLocation } from "react-router-dom";
import { config } from "../config";


export const CompleteProfile = () => {
  const { user, getAccessTokenSilently } = useAuth0();
  const navigate = useNavigate();
  const location = useLocation();
  
  const originalDestination = location.state?.returnTo || "/";
  
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);
    setError(null);

    const form = e.target;
    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());

    try {
      const token = await getAccessTokenSilently();
      
      const response = await fetch(`${config.API_URL}/users`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({
          email: user.email,
          restaurantName: data.restaurantName,
          legalAddress: data.legalAddress,
        })
      });

      if (!response.ok) {
        throw new Error('Failed to create user profile');
      }

      navigate(originalDestination, { replace: true });
      
    } catch (err) {
      setError(err.message);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="complete-profile">
      <h1>Registro de restaurante</h1>
      <p>Complete el registro de su restaurante para utilizar la plataforma</p>
      
      {error && <div className="error">Error: {error}</div>}
      
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="restaurantName">Nombre del restaurante:</label>
          <input
            type="text"
            id="restaurantName"
            name="restaurantName"
            placeholder="McDonald's"
            required
          />
        </div>

        <div>
          <label htmlFor="legalAddress">Domicilio Legal:</label>
          <input
            type="text"
            id="legalAddress"
            name="legalAddress"
            placeholder="Emilio Civit 256, Mendoza, Argentina"
            required
          />
        </div>

        <button type="submit" disabled={isSubmitting}>
          {isSubmitting ? 'Creando Perfil' : 'Registrar'}
        </button>
      </form>
    </div>
  );
};