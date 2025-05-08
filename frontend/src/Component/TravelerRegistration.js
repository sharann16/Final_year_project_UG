import { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./TravellerRegistration.css";

const TravelerRegistration = () => {
  const navigate = useNavigate();
  const [adharNumber, setAdharNumber] = useState("");
  const [license, setLicense] = useState("");
  const [storedUser, setStoredUser] = useState(null);

  useEffect(() => {
    const user = localStorage.getItem("user");
    if (user) {
      setStoredUser(JSON.parse(user));
    }
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!storedUser) {
      alert("User not found. Please log in.");
      return;
    }

    try {
      const response = await axios.post("http://localhost:8080/SwiftBridge/traveler", {
        user: { userid: storedUser.userid },
        adharNumber,
        license,
      });
      console.log("Traveler registered:", response.data);
      navigate("/dashboard");
    } catch (error) {
      console.error("Error registering traveler:", error);
      alert("Failed to register. Please try again.");
    }
  };

  return (
    <div id="traveler-registration-container">
      <div id="traveler-registration-box">
        <h2 id="traveler-registration-title">Register as Traveler</h2>

        {storedUser ? (
          <form onSubmit={handleSubmit}>
            <div className="input-group">
              {/* <label htmlFor="adhar-input" className="input-label" >Aadhar Number</label> */}
              <input 
                id="adhar-input"
                type="text"
                className="registration-input"
                placeholder="Aadhar Number"
                value={adharNumber}
                onChange={(e) => setAdharNumber(e.target.value)}
                required 
              />
            </div>

            <div className="input-group">
              {/* <label htmlFor="license-input" className="input-label">License</label> */}
              <input 
                id="license-input"
                type="text"
                className="registration-input"
                placeholder="License"
                value={license}
                onChange={(e) => setLicense(e.target.value)}
                required 
              />
            </div>

            <button id="submit-button" type="submit">
              Submit
            </button>
          </form>
        ) : (
          <p id="user-error">Error: User not found. Please log in before registering.</p>
        )}
      </div>
    </div>
  );
};

export default TravelerRegistration;
