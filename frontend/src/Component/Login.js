import { useState } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "./Login.css";

const Login = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({ emailid: "", password: "" });
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    toast.dismiss();
    setLoading(true);

    try {
      const response = await axios.post("http://localhost:8080/SwiftBridge/login", formData);
      const userData = response.data?.user; // Ensure we get user data

      if (userData) {
        localStorage.setItem("user", JSON.stringify(userData)); // Store full user data
        localStorage.setItem("userid", userData.userid); // Store only userid separately

        toast.success(`Welcome, ${userData.name}!`);
        setTimeout(() => navigate("/dashboard"), 2000);
      } else {
        toast.error("Login failed. Invalid credentials.");
      }
    } catch (error) {
      const errorMessage = error.response?.data?.message || "Invalid email or password";
      toast.error(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div id="login-container">
      <ToastContainer />
      <div id="login-box">
        <h2 id="login-title">Login to Your Account</h2>
        <form id="login-form" onSubmit={handleSubmit}>
          <input type="email" name="emailid" placeholder="Email Address" onChange={handleChange} required />
          <input type="password" name="password" placeholder="Password" onChange={handleChange} required />
          <button id="login-button" type="submit" disabled={loading}>
            {loading ? "Logging in..." : "Login"}
          </button>
        </form>
        <p id="login-signup-text">
          Don't have an account?{" "}
          <Link id="login-signup-link" to="/signup">Sign up</Link>
        </p>
      </div>
    </div>
  );
};

export default Login;
