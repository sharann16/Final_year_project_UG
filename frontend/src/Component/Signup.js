import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { Link } from "react-router-dom";
import "./Signup.css";

const Signup = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    name: "",
    emailid: "",
    mobileno: "",
    password: "",
    address: "",
    joindate: new Date().toISOString(),
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8080/SwiftBridge/Signup", formData);
      alert("Signup Successful: " + response.data);
      navigate("/");
    } catch (error) {
      alert("Signup Failed: " + (error.response?.data || error.message));
    }
  };

  return (
    <div id="signup-container">
      <div id="signup-box">
        <h2 id="signup-title">Create an Account</h2>
        <form onSubmit={handleSubmit} id="signup-form">
          <input type="text" id="signup-name" name="name" placeholder="Name" onChange={handleChange} required />
          <input type="email" id="signup-email" name="emailid" placeholder="Email Address" onChange={handleChange} required />
          <input type="tel" id="signup-mobile" name="mobileno" placeholder="Mobile No" onChange={handleChange} required />
          <input type="password" id="signup-password" name="password" placeholder="Password" onChange={handleChange} required />
          <input type="text" id="signup-address" name="address" placeholder="Address" onChange={handleChange} required />
          <button type="submit" id="signup-button">Signup</button>
        </form>

        <p id="signup-login-text">
          Already have an account?{" "}
          <Link to="/" id="signup-login-link">Log in</Link>
        </p>
      </div>
    </div>
  );
};

export default Signup;
