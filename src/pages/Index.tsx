import { useState } from "react";
import Auth from "./Auth";
import Dashboard from "./Dashboard";

interface User {
  name: string;
  email: string;
}

const Index = () => {
  const [user, setUser] = useState<User | null>(null);

  const handleAuthenticated = (authenticatedUser: User) => {
    setUser(authenticatedUser);
  };

  const handleLogout = () => {
    setUser(null);
  };

  return (
    <>
      {user ? (
        <Dashboard currentUser={user} onLogout={handleLogout} />
      ) : (
        <Auth onAuthenticated={handleAuthenticated} />
      )}
    </>
  );
};

export default Index;
