import React, { useEffect, useState } from "react";

import { useParams, useNavigate } from "react-router-dom";

import axios from "axios";

import toast from "react-hot-toast";

const CookingSteps = () => {
  const { recipeId } = useParams();
  const navigate = useNavigate();
  const [steps, setSteps] = useState([]);
  const [currentStepIndex, setCurrentStepIndex] = useState(0);
  const [progress, setProgress] = useState(0);
  const [isPaused, setIsPaused] = useState(false);
  const jwtToken = localStorage.getItem("token");

  useEffect(() => {
    const fetchSteps = async () => {
      try {
        const response = await axios.get(
          `http://localhost:3434/recipes/get-recipe/${recipeId}`,

          { headers: { Authorization: `Bearer ${jwtToken}` } }
        );

        setSteps(response.data.steps);
      } catch (error) {
        console.error("Error fetching steps:", error);

        toast.error("Failed to fetch steps.");

        navigate(-1);
      }
    };

    fetchSteps();
  }, [recipeId, jwtToken, navigate]);

  useEffect(() => {
    if (steps.length === 0 || currentStepIndex >= steps.length || isPaused)
      return;

    const stepTime = steps[currentStepIndex].timeRequired * 60000; // Convert minutes to ms

    let elapsedTime = 0;

    setProgress(0);

    const interval = setInterval(() => {
      if (!isPaused) {
        elapsedTime += 100;

        setProgress((elapsedTime / stepTime) * 100);

        if (elapsedTime >= stepTime) {
          clearInterval(interval);

          if (currentStepIndex < steps.length - 1) {
            setCurrentStepIndex((prev) => prev + 1);
          } else {
            toast.success("Cooking completed!");

            navigate(-1);
          }
        }
      }
    }, 100);

    return () => clearInterval(interval);
  }, [currentStepIndex, steps, isPaused, navigate]);

  const skipStep = () => {
    if (currentStepIndex < steps.length - 1) {
      setCurrentStepIndex((prev) => prev + 1);
    } else {
      toast.success("Cooking skipped!");

      navigate(-1);
    }
  };

  const pauseAndExit = () => {
    setIsPaused(true);

    navigate(-1);
  };

  if (steps.length === 0) {
    return <div className="text-center mt-5">Loading steps...</div>;
  }

  return (
    <div className="container mt-5 text-center">
      <h3 className="text-primary">Step {currentStepIndex + 1}</h3>
      <p>{steps[currentStepIndex].stepDescription}</p>
      <h5 className="text-secondary">
        Time Required: {steps[currentStepIndex].timeRequired} minutes
      </h5>
      <div className="progress" style={{ height: "30px" }}>
        <div
          className="progress-bar progress-bar-striped progress-bar-animated"
          role="progressbar"
          style={{ width: `${progress}%` }}
        ></div>
      </div>
      <div className="mt-3">
        <button className="btn btn-danger me-3" onClick={skipStep}>
          Skip
        </button>
        <button className="btn btn-warning" onClick={pauseAndExit}>
          Pause & Exit
        </button>
      </div>
    </div>
  );
};

export default CookingSteps;
