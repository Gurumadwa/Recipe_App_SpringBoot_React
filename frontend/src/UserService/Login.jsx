import axios from 'axios';
import React, { useContext, useState } from 'react';
import { toast } from 'react-hot-toast';
import { UserContext } from '..';
import { useNavigate, Link } from 'react-router-dom';

const Login = () => {
  const { setUser, setIsAuthorized } = useContext(UserContext);
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    username: '',
    password: ''
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:3434/users/authenticate', formData);
      localStorage.setItem('token', response.data.jwtToken);
      setUser(response.data);
      setIsAuthorized(true);
      toast.success('Login Successful');
      navigate('/recipes');
    } catch (error) {
      toast.error(error.response.data.message);
    }

    setFormData({
      username: '',
      password: ''
    });
  };

  return (
    <div className="container vh-100 d-flex align-items-center justify-content-center">
      <div className="row w-75 shadow-lg rounded overflow-hidden">
        {/* Left Section for 3D Cartoon Image */}
        <div className="col-md-6 d-flex align-items-center justify-content-center bg-light">
          <img
            src="/assets/Login-bro.png"
            alt="3D Cartoon"
            className="img-fluid"
          />
        </div>

        {/* Right Section for Login Form */}
        <div className="col-md-6 p-5">
          <h2 className="mb-4 text-center">LOGIN</h2>
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="username" className="form-label">Username</label>
              <input
                type="text"
                value={formData.username}
                onChange={handleInputChange}
                className="form-control"
                id="username"
                name="username"
                placeholder="Enter username"
              />
            </div>
            <div className="mb-3">
              <label htmlFor="password" className="form-label">Password</label>
              <input
                type="password"
                value={formData.password}
                onChange={handleInputChange}
                className="form-control"
                id="password"
                name="password"
                placeholder="Enter password"
              />
            </div>
            <button type="submit" className="btn btn-primary w-100">Login</button>
          </form>
          <div className="mt-3 text-center">
            <span>Not registered yet? </span>
            <Link to="/register">Create an account</Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
