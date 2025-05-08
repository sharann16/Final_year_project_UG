import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Signup from "./Component/Signup";
import Login from "./Component/Login";
import Dashboard from "./Component/Dashboard";
import UploadProduct from "./Component/UploadProduct";
import TravelerRegistration from "./Component/TravelerRegistration";
import ParcelSelection from "./Component/ParcelSelection";
import TravellerParcels from "./Component/TravellerParcels";
import SenderHis from "./Component/SenderHis";
import Travelerhis from "./Component/Travelerhis";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/signup" element={<Signup />} />
        <Route path="/" element={<Login />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/upload-product" element={<UploadProduct />} />
        <Route path="/register-traveler" element={<TravelerRegistration />} />
        <Route path="/select-parcel" element={<ParcelSelection />} />
        <Route path="/travellerparcel" element={<TravellerParcels/>}/>
        <Route path="/senderhis" element={<SenderHis/>}/>
        <Route path="/travelerhis" element={< Travelerhis/>}/>
      </Routes>
    </Router>
  );
}

export default App;
