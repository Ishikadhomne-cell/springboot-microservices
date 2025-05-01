
import React, { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { loginUser } from "../../api/authService";
import { AuthContext } from "../../context/AuthContext";

function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const { setToken } = useContext(AuthContext);

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const res = await loginUser({ email, password });
      localStorage.setItem("token", res.data.token);
      setToken(res.data.token);
      navigate("/dashboard");
    } catch (error) {
      alert("Login failed");
    }
  };

  return (
    <div className="container mt-5">
      <h2>Login</h2>
      <form onSubmit={handleLogin}>
        <input className="form-control mb-2" placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} />
        <input className="form-control mb-2" type="password" placeholder="Password" value={password} onChange={e => setPassword(e.target.value)} />
        <button className="btn btn-primary">Login</button>
      </form>
    </div>
  );
}

export default LoginPage;
