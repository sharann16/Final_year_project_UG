import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./ParcelSelection.css";

const ParcelSelection = () => {
  // Retrieve user from localStorage
  const storedUser = JSON.parse(localStorage.getItem("user")) || {};
  const userid = storedUser.userid;

  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [selectedParcel, setSelectedParcel] = useState(null);
  const navigate = useNavigate();
  const [image, setImage] = useState(null);

  useEffect(() => {
    if (userid) {
      axios
        .get(`http://localhost:8080/SwiftBridge/getproducts/${userid}`)
        .then((response) => {
          console.log("API Response:", response.data);
          setProducts(response.data);
          setLoading(false);
        })
        .catch((error) => {
          console.error("Error fetching products:", error);
          setError("Failed to load products");
          setLoading(false);
        });
    }
  }, [userid]); // Runs when `userid` changes
  useEffect(() => {
    fetch(products.image) // Adjust to your backend API
      .then((res) => res.json())
      .then((data) => {
        setImage(data); // Assume it's an array of objects with image URLs
      });
  }, []);

  // Function to handle parcel selection
  const handleSelectParcel = (parcelid) => {
    const requestBody = {
      userid: userid,
      parcelid: parcelid,
    };

    axios
      .post("http://localhost:8080/SwiftBridge/parcelchoose", requestBody)
      .then((response) => {
        console.log("Parcel selected successfully:", response.data);
        setSelectedParcel(parcelid);
        alert(`Parcel ${parcelid} selected successfully!`);
      })
      .catch((error) => {
        console.error("Error selecting parcel:", error);
        alert("Failed to select parcel. Please try again.");
      });
  };

  if (loading) return <p id="loading-text">Loading products...</p>;
  if (error) return <p id="error-text">{error}</p>;

  return (
    <div id="parcel-selection-container">
      <div id="parcel-selection-box">
        <h2 id="parcel-selection-title">Parcel Selection</h2>
        <button
          onClick={() => navigate("/travellerparcel")}
          id="selected-parcels-button"
        >
          Selected Parcels
        </button>
        <button
              id="upload-product-button"
              onClick={() => navigate("/travelerhis")}
              className="dashboard-button"
            >
              History 
            </button>

        {products.length === 0 ? (
          <p id="no-products-text">No products available.</p>
        ) : (
          <ul id="parcel-list">
            {products.map((product) => (
              <li key={product.productid} className="parcel-item">
                {/* <img
                  src={`http://localhost:8080/SwiftBridge/images/${product.image}`}
                  alt="Product"
                  className="parcel-image"
                /> */}
                 <img
                  // id={`parcel-image-${index}`}
                  src={process.env.PUBLIC_URL + product.image}
                  alt="Parcel"
                  onError={(e) => (e.target.src = process.env.PUBLIC_URL)} height={100} width={100}
                />
                <div className="parcel-details">
                  <p><strong>Product ID:</strong> {product.productid}</p>
                  <p><strong>Weight:</strong> {product.weight} kg</p>
                  <p><strong>Price:</strong> ${product.price}</p>
                  <p><strong>From:</strong> {product.from_address}</p>
                  <p><strong>To:</strong> {product.to_address}</p>
                  <p><strong>Status:</strong> {product.status}</p>
                </div>
                <button
                  onClick={() => handleSelectParcel(product.productid)}
                  disabled={selectedParcel === product.productid}
                  className="select-button"
                  id={`select-button-${product.productid}`}
                >
                  {selectedParcel === product.productid ? "Selected" : "Select"}
                </button>
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
};

export default ParcelSelection;
