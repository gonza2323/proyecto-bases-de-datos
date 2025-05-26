import { Routes, Route, Navigate } from "react-router-dom";
import { HeaderAndFooterLayout } from "./layouts/HeaderAndFooterLayout";
import { Home } from "./routes/Home";
import { Locations } from "./routes/Locations";
import { Profile } from "./routes/Profile";
import { About } from "./routes/About";
import { NotFound } from "./routes/NotFound";
import { Callback } from "./routes/Callback";
import { CompleteProfile } from "./routes/CompleteProfile";
import { AuthenticationGuardLayout } from "./layouts/AuthenticationGuardLayout";
import { ProfileCompletionGuardLayout } from "./layouts/ProfileCompletionGuardLayout";


export const App = () => (
  <Routes>
    {/* agregamos un header y footer a todas las rutas */}
    <Route path="/" element={<HeaderAndFooterLayout />}>
      
      <Route index element={<Home />} />

      {/* las siguientes rutas requieren autenticación */}
      <Route element={<AuthenticationGuardLayout />}>
        
        {/* con esta guarda, obligamos al usuario a completar el registro */}
        <Route path="manage" element={<ProfileCompletionGuardLayout />}>
          <Route index element={<Navigate to="/manage/locations" replace />} />
          <Route path="locations" element={<Locations />} />
          <Route path="profile" element={<Profile />} />
        </Route>

        {/* ruta para completar el registro */}
        <Route path="complete-profile" element={<CompleteProfile />} />
      </Route>

      <Route path="about" element={<About />} />
      <Route path="callback" element={<Callback />} />
      <Route path="*" element={<NotFound />} />
    </Route>
  </Routes>
);
