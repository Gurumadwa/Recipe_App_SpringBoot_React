import React, { useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import { UserContext } from "../index";
import toast from "react-hot-toast";

const Navbar = () => {
  const { user, setUser, isAuthorized, setIsAuthorized } = useContext(UserContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("jwtToken");
    setUser({});
    setIsAuthorized(false);
    toast.success("Logged out successfully");
    navigate("/");
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
      <div className="container">
        {/* Brand Name */}
        <Link className="navbar-brand fw-bold" to="/">
          Recipe Blog
        </Link>

        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse justify-content-end" id="navbarNav">
          <ul className="navbar-nav">

            {/* {isAuthorized && ( */}
              <li className="nav-item">
                <Link className="nav-link text-white" to="/recipes">
                  Recipes
                </Link>
              </li>
            {/* )} */}


            {isAuthorized && (
              <li className="nav-item">
                <Link className="nav-link text-white" to="/bookmarks">
                  Bookmarks
                </Link>
              </li>
            )}


            {user?.role === "ROLE_ADMIN" && (
              <>
                <li className="nav-item">
                  <Link className="nav-link text-white" to="/create-recipe">
                    Create Recipe
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link text-white" to="/created-recipes">
                    View Created Recipes
                  </Link>
                </li>
              </>
            )}

            {isAuthorized && (
              <li className="nav-item">
                <button className="btn btn-danger ms-3" onClick={handleLogout}>
                  Logout
                </button>
              </li>
            )}
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
