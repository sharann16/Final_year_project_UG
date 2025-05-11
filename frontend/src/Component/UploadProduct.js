import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./UploadProduct.css";

const UploadProduct = () => {
  const navigate = useNavigate();
  const formDta = new FormData();
  const [formData, setFormData] = useState({
    dimantion: "",
    weight: "",
    price: "",
    from_address: "",
    to_address: "",
    image: null,
    user: { userid: "" },
    travellerid: null,
  });

  useEffect(() => {
    const storedUserId = localStorage.getItem("userid");
    if (storedUserId) {
      setFormData((prevData) => ({
        ...prevData,
        user: { userid: Number(storedUserId) },
      }));
    }
  }, []);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleImageUpload = (e) => {
    const file = e.target.files[0];
    if (!file) return;

    setFormData((prevData) => ({
      ...prevData,
      image: file,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    formDta.append("file", formData.image);
    formDta.append("upload_preset", "my_unsigned_preset");
  
    const res = await fetch("https://api.cloudinary.com/v1_1/dyyc5zv2u/image/upload", {
      method: "POST",
      body: formDta,
    });
    const data = await res.json();
    console.log(data);

    if (!formData.image) {
      alert("Please select an image before submitting.");
      return;
    }

    const formDataToSend = new FormData();
    formDataToSend.append("image", data.secure_url);
    formDataToSend.append("dimantion", formData.dimantion);
    formDataToSend.append("weight", formData.weight);
    formDataToSend.append("price", formData.price);
    formDataToSend.append("from_address", formData.from_address);
    formDataToSend.append("to_address", formData.to_address);
    formDataToSend.append("userId", formData.user.userid);

    if (formData.travellerid !== null && formData.travellerid !== "") {
      formDataToSend.append("travellerid", formData.travellerid);
    }

    try {
      const response = await axios.post("http://localhost:8080/SwiftBridge/addproduct", formDataToSend, {
        headers: { "Content-Type": "multipart/form-data" },
      });

      console.log("Response from server:", response.data);
      alert("Product Uploaded Successfully!");
      navigate("/dashboard");
    } catch (error) {
      console.error("Upload Failed:", error.response ? error.response.data : error);
      alert("Upload failed. Check console for errors.");
    }
  };

  return (
    <div id="upload-container" className="min-h-screen flex items-center justify-center">
      <div id="upload-box" className="p-6 rounded-lg shadow-md w-full max-w-md">
        <h2 id="upload-title" className="text-2xl font-bold text-center">Upload Product</h2>
        <form id="upload-form" onSubmit={handleSubmit} className="mt-4 space-y-4">
          <input id="dimension" type="text" name="dimantion" placeholder="Dimensions" className="upload-input" onChange={handleChange} required />
          <input id="weight" type="text" name="weight" placeholder="Weight" className="upload-input" onChange={handleChange} required />
          <input id="price" type="number" name="price" placeholder="Price" className="upload-input" onChange={handleChange} required />
          <input id="from-address" type="text" name="from_address" placeholder="From Address" className="upload-input" onChange={handleChange} required />
          <input id="to-address" type="text" name="to_address" placeholder="To Address" className="upload-input" onChange={handleChange} required />

          <input id="image-upload" type="file" accept="image/*" onChange={handleImageUpload} required />
          {formData.image && <p id="selected-file">Selected File: {formData.image.name}</p>}

          <button id="upload-button" type="submit" className="upload-button">
            Upload
          </button>
        </form>
      </div>
    </div>
  );
};

export default UploadProduct;
