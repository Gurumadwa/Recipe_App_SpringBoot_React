import React, { useState } from 'react';
import axios from 'axios';
import toast from 'react-hot-toast';
import { Link } from 'react-router-dom';

const Register = () => {

  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    role: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post('http://localhost:3434/users/save-user', formData);
      console.log(response.data);
      toast.success("Registration Successfull")
    } catch (err) {
      toast.error(err.response.data.message)
    }
  };

  return (
    <div className="container vh-100 d-flex align-items-center justify-content-center">
      <div className="row w-75 shadow-lg rounded overflow-hidden">
        {/* Left Section for 3D Cartoon Image */}
        <div className="col-md-6 d-flex align-items-center justify-content-center bg-light">
          <img
            src="/assets/signup-img.png"
            alt="3D Cartoon"
            className="img-fluid"
          />
        </div>

        {/* Right Section for Login Form */}
        <div className="col-md-6 p-5">
          <h2 className="mb-4 text-center">REGISTER</h2>
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="username" className="form-label">Username</label>
              <input 
                type="text" 
                className="form-control" 
                id="username" 
                name="username"
                placeholder="Enter username" 
                value={formData.username} 
                onChange={handleChange} 
              />
            </div>
            <div className="mb-3">
              <label htmlFor="email" className="form-label">Email</label>
              <input 
                type="text" 
                className="form-control" 
                id="email" 
                name="email"
                placeholder="Enter Email" 
                value={formData.email} 
                onChange={handleChange} 
              />
            </div>
            <div className="mb-3">
              <label htmlFor="password" className="form-label">Password</label>
              <input 
                type="password" 
                className="form-control" 
                id="password" 
                name="password"
                placeholder="Enter password" 
                value={formData.password} 
                onChange={handleChange} 
              />
            </div>
            <div className="mb-3">
              <label htmlFor="role" className="form-label">Role</label>
              <select 
                name="role" 
                className="form-control mb-2" 
                value={formData.role} 
                onChange={handleChange}
              >
                <option value="">Select Role</option>
                <option value="ROLE_USER">USER</option>
                <option value="ROLE_ADMIN">ADMIN</option>
              </select>
            </div>
            <button type="submit" className="btn btn-primary w-100">Register</button>
          </form>
          <div className="mt-3 text-center">
            <span>Already registered? </span>
            <Link to="/login">Login here</Link>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Register;
