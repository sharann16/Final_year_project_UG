import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
// import "./ParcelHistory.css";  // Create a CSS file for styling

const SenderHis = () => {
  const navigate = useNavigate();
  const [parcels, setParcels] = useState([]);

  // Fetch parcel details when the component loads
  useEffect(() => {
    const userid = localStorage.getItem("userid");
    axios
      .get(`http://localhost:8080/SwiftBridge/senhis?senderid=${userid}`)
      .then((response) => setParcels(response.data))
      .catch((error) => console.error("Error fetching parcel history:", error));
  }, []);

  return (
    <div className="parcel-history-container">
      <div className="history-box">
        <h2 className="history-title">Parcel History</h2>
        <button onClick={() => navigate("/dashboard")} className="history-button">
          Back to Dashboard
        </button>

        <div className="parcel-list">
          {parcels.length > 0 ? (
            parcels.map((parcel, index) => (
              <div key={index} className="parcel-card">
                <img
                  src={process.env.PUBLIC_URL + parcel.image}
                  alt="Parcel"
                  onError={(e) => (e.target.src = process.env.PUBLIC_URL + "/default-parcel.png")}
                  height={100}
                  width={100}
                />
                <p><strong>Dimensions:</strong> {parcel.dimantion}</p>
                <p><strong>Weight:</strong> {parcel.weight} kg</p>
                <p><strong>Price:</strong> ${parcel.price}</p>
                <p><strong>From:</strong> {parcel.from_address}</p>
                <p><strong>To:</strong> {parcel.to_address}</p>
                <p className={`parcel-status ${parcel.status === "Delivered" ? "status-delivered" : "status-other"}`}>
                  {parcel.status}
                </p>
              </div>
            ))
          ) : (
            <p className="no-parcels">No parcels found.</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default SenderHis;
