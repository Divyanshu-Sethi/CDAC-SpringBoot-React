import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import LeaveMaster from './pages/LeaveMaster';
import QualificationMaster from './pages/QualificationMaster';
import DesignationMaster from './pages/DesignationMaster';
import UserTypeMaster from './pages/UserTypeMaster';
import UniversityTypeMaster from './pages/UniversityMaster';
import InstituteMaster from './pages/InstituteMaster'; // Added Institute Master!

const App = () => {
  return (
    <Router>
      <div className="bg-light min-vh-100 d-flex flex-column">
        {/* Add a navbar here if needed */}
        <Routes>
          <Route path="/" element={<Navigate to="/leave" replace />} />
          <Route path="/leave" element={<LeaveMaster />} />
          <Route path="/qualification" element={<QualificationMaster />} />
          <Route path="/designation" element={<DesignationMaster />} />
          <Route path="/user-type" element={<UserTypeMaster />} />
          <Route path="/university-type" element={<UniversityTypeMaster />} />
          <Route path="/institute" element={<InstituteMaster />} /> {/* Institute Master route! */}

          {/* Fallback 404 */}
          <Route
            path="*"
            element={
              <div className="text-center mt-5">
                <h2>404 - Page Not Found</h2>
              </div>
            }
          />
        </Routes>
      </div>
    </Router>
  );
};

export default App;
