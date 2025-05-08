import { useState, useEffect } from "react";
import axios from "axios";
import "./TravellerParcels.css"; // Import the CSS file

const TravellerParcels = () => {
  const [parcels, setParcels] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const travelerId = localStorage.getItem("userid");

  useEffect(() => {
    const fetchParcels = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/SwiftBridge/travellerparcel?travellerid=${travelerId}`
        );

        if (Array.isArray(response.data)) {
          setParcels(response.data);
        } else {
          console.error("API did not return an array:", response.data);
          setParcels([]);
        }
      } catch (err) {
        setError("Failed to fetch parcel details");
      } finally {
        setLoading(false);
      }
    };

    fetchParcels();
  }, [travelerId]); // Add dependency

  // Handle delivery completion
  const completeDelivery = async (productId) => {
    try {
      await axios.put(
        `http://localhost:8080/SwiftBridge/complete?productid=${productId}`
      );

      // Update status of the selected parcel locally
      setParcels((prevParcels) =>
        prevParcels.map((parcel) =>
          parcel.productid === productId ? { ...parcel, status: "Delivered" } : parcel
        )
      );
    } catch (err) {
      setError("Failed to update parcel status");
    }
  };

  if (loading) return <p className="text-center text-gray-600">Loading...</p>;
  if (error) return <p className="text-center text-red-500">{error}</p>;

  return (
    <div id="traveller-container">
      <div id="traveller-box">
        <h2 id="traveller-title">Traveller's Parcels</h2>
        {parcels.length === 0 ? (
          <p className="text-gray-600">No parcels found</p>
        ) : (
          parcels.map((parcel) => (
            <div key={parcel.productid} className="parcel-card">
              <h3 className="parcel-info">
                <strong>Sender:</strong> {parcel.user.name}
              </h3>
              <p className="parcel-info">
                <strong>Mobile:</strong> {parcel.user.mobileno}
              </p>
              <div className="parcel-info">
                <p><strong>Parcel ID:</strong> {parcel.productid}</p>
                <p><strong>Dimensions:</strong> {parcel.dimantion}</p>
                <p><strong>Weight:</strong> {parcel.weight} g</p>
                <p><strong>Price:</strong> ₹{parcel.price}</p>
                <p><strong>From:</strong> {parcel.from_address}</p>
                <p><strong>To:</strong> {parcel.to_address}</p>
                <p className={parcel.status === "Delivered" ? "status-delivered" : "status-pending"}>
                  <strong>Status:</strong> {parcel.status}
                </p>
              </div>
              {parcel.status != "Delivered" ? (
                <button
                  id="upload-product-button"
                  className="dashboard-button"
                  onClick={() => completeDelivery(parcel.productid)}
                >
                  Finish Delivery
                </button>
              ) : null}
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default TravellerParcels;
