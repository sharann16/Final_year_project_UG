import { useState, useEffect } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";
import "./Dashboard.css";

const Dashboard = () => {
  const navigate = useNavigate();
  const { senderid } = useParams();
  const [user, setUser] = useState(null);
  const [parcels, setParcels] = useState([]);
  const [isTraveler, setIsTraveler] = useState(false);
  const [travelerDetails, setTravelerDetails] = useState(null);
  const [image, setImage] = useState(null);

  useEffect(() => {
    const storedUser = JSON.parse(localStorage.getItem("user"));
    if (storedUser) {
      setUser(storedUser);

      axios
        .get(`http://localhost:8080/SwiftBridge/istraveler?userid=${storedUser.userid}`)
        .then((response) => setIsTraveler(response.data))
        .catch((error) => console.error("Error checking traveler status:", error));

      axios
        .get(`http://localhost:8080/SwiftBridge/view/${storedUser.userid}`)
        .then((response) => {
          setParcels(response.data);

          response.data.forEach((parcel) => {
            if (parcel.status === "selected") {
              axios
                .get(`http://localhost:8080/SwiftBridge/findTraveller?productid=${parcel.productid}`)
                .then((travelerResponse) => {
                  const travelerId = travelerResponse.data;
                  axios
                    .get(`http://localhost:8080/SwiftBridge/travellerDetail?userid=${travelerId}`)
                    .then((travelerDetailResponse) => {
                      setTravelerDetails(travelerDetailResponse.data);
                    })
                    .catch((error) => console.error("Error fetching traveler details:", error));
                })
                .catch((error) => console.error("Error fetching traveler ID:", error));
            }
          });
        })
        .catch((error) => console.error("Error fetching parcels:", error));
    }
  }, [senderid]);
  useEffect(() => {
    fetch(parcels.image) // Adjust to your backend API
      .then((res) => res.json())
      .then((data) => {
        setImage(data); // Assume it's an array of objects with image URLs
      });
  }, []);

  return (
    <div id="dashboard-container" className="min-h-screen p-6">
      <div id="dashboard-box" className="max-w-4xl mx-auto p-6 rounded-lg shadow-md">

        <h2 id="dashboard-title" className="text-2xl font-bold">User Dashboard</h2>
        {user && (
          <div id="user-info" className="mt-4 p-4 bg-indigo-100 rounded-lg">
            <p><strong>Name:</strong> {user.name}</p>
            <p><strong>Email:</strong> {user.emailid}</p>
            <p><strong>Mobile:</strong> {user.mobileno}</p>
            <p><strong>Address:</strong> {user.address}</p>
            <button
              id="upload-product-button"
              onClick={() => navigate("/upload-product")}
              className="dashboard-button"
            >
              Upload Product
            </button>
            <button
              id="traveler-button"
              onClick={() => {
                if (isTraveler) {
                  navigate("/select-parcel");
                } else {
                  navigate("/register-traveler");
                }
              }}
              className="dashboard-button"
            >
              {isTraveler ? "View Parcel" : "Register"}
            </button>
            <button
              id="upload-product-button"
              onClick={() => navigate("/senderhis")}
              className="dashboard-button"
            >
              History 
            </button>
          </div>
        )}

        <h3 id="parcels-title" className="text-xl font-bold mt-6">Your Parcels</h3>
        <div id="parcel-list" className="mt-4 space-y-4">
          {parcels.length > 0 ? (
            parcels.map((parcel, index) => (
              <div key={index} id={`parcel-${index}`} className="parcel-card">
                <img
                  id={`parcel-image-${index}`}
                  src={process.env.PUBLIC_URL + parcel.image}
                  alt="Parcel"
                  onError={(e) => (e.target.src = process.env.PUBLIC_URL )} height={100} width={100}
                />
                {/* <img src={image.image} alt="Uploaded" style={{ width: "200px" }} /> */}
                <p><strong>Dimensions:</strong><br></br> {parcel.dimantion}</p>
                <p><strong>Weight:</strong><br></br> {parcel.weight} kg</p>
                <p><strong>Price:</strong><br></br> ${parcel.price}</p>
                <p><strong>From:</strong><br></br> {parcel.from_address}</p>
                <p><strong>To:</strong><br></br> {parcel.to_address}</p>
                <p
                  id={`parcel-status-${index}`}
                  className={`parcel-status ${parcel.status === "posted" ? "status-posted" : "status-selected"}`}
                >
                  {parcel.status}
                </p>

                {parcel.status === "selected" && travelerDetails && (
                  <div id="traveler-details" className="mt-4 bg-green-100 p-4 rounded-md">
                    <h4 className="text-xl font-bold">Traveler Details</h4>
                    <p><strong>Name:</strong> {travelerDetails.name}</p>
                    <p><strong>Mobile:</strong> {travelerDetails.mobileno}</p>
                  </div>
                )}
              </div>
            ))
          ) : (
            <p id="no-parcels-message">No parcels found.</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
