import React from "react";
import { Link } from "react-router-dom";

const LandingPage = () => {
  return (
    <div className="vh-100 d-flex align-items-center justify-content-center text-center text-md-start bg-light">
      <div className="container">
        <div className="row align-items-center">
          {/* Left Section - Image */}
          <div className="col-md-6 text-center">
            <img
              src="/assets/homepage.png"
              className="img-fluid rounded mb-4 w-75"
              alt="Delicious Food"
            />
          </div>
          
          {/* Right Section - Title & Description */}
          <div className="col-md-6">
            <h1 className="display-4 fw-bold">Welcome to Recipe Blog</h1>
            <p className="lead">Discover, cook, and share delicious recipes from around the world.</p>
            
            {/* Buttons */}
            <div className="mt-4">
              <Link to="/login" className="btn btn-outline-dark btn-lg me-3">
                Login
              </Link>
              <Link to="/register" className="btn btn-primary btn-lg">
                Register
              </Link>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LandingPage;
