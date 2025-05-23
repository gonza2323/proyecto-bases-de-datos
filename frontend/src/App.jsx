import { Routes, Route } from "react-router-dom";
import { RootLayout } from "./layouts/RootLayout";
import { Home } from "./routes/Home";
import { Restaurants } from "./routes/Restaurants";
import { Profile } from "./routes/Profile";
import { About } from "./routes/About";
import { NotFound } from "./routes/NotFound";
import { Callback } from "./routes/Callback";
import { AuthenticationGuard } from "./components/AuthenticationGuard";


export const App = () => (
  <Routes>
    <Route path="/" element={<RootLayout />}>
      <Route index element={<Home />} />
      <Route path="/restaurants" element={<Restaurants />} />
      <Route
        path="/profile"
        element={<AuthenticationGuard component={Profile} />}
      />
      <Route path="/about" element={<About />} />
      <Route path="*" element={<NotFound />} />
    </Route>
    <Route path="/callback" element={<Callback />} />
  </Routes>
);
