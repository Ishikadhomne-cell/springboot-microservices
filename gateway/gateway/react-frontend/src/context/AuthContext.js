
import React, { createContext, useState } from "react";
import jwt_decode from "jwt-decode";

export const AuthContext = createContext();

function AuthProvider({ children }) {
  const [token, setToken] = useState(localStorage.getItem("token"));

  const user = token ? jwt_decode(token) : null;

  return (
    <AuthContext.Provider value={{ token, setToken, user }}>
      {children}
    </AuthContext.Provider>
  );
}

export default AuthProvider;
