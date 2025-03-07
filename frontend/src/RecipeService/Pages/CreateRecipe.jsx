import React, { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import toast from "react-hot-toast";
import axios from "axios";
import { UserContext } from "../..";

const CreateRecipe = () => {
  const { user } = useContext(UserContext);
  const navigate = useNavigate();

  // Recipe State
  const [recipe, setRecipe] = useState({
    recipeTitle: "",
    recipeDescription: "",
    ingredients:"",
    cookingTime: "",
    image: "",
    steps: [],
  });

  // Step State
  const [step, setStep] = useState({ stepDescription: "", timeRequired: "" });

  // Handle Recipe Input Change
  const handleRecipeChange = (e) => {
    setRecipe({ ...recipe, [e.target.name]: e.target.value });
  };

  // Handle Step Input Change
  const handleStepChange = (e) => {
    setStep({ ...step, [e.target.name]: e.target.value });
  };

  // Add Step to Recipe
  const addStep = () => {
    if (!step.stepDescription || !step.timeRequired) {
      toast.error("Please fill both step fields!");
      return;
    }
    setRecipe({ ...recipe, steps: [...recipe.steps, step] });
    setStep({ stepDescription: "", timeRequired: "" });
  };

  // Remove Step
  const removeStep = (index) => {
    const updatedSteps = recipe.steps.filter((_, i) => i !== index);
    setRecipe({ ...recipe, steps: updatedSteps });
  };

  // Handle Form Submit
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (
      !recipe.recipeTitle ||
      !recipe.recipeDescription ||
      !recipe.ingredients ||
      !recipe.cookingTime ||
      !recipe.image
    ) {
      toast.error("Please fill all required fields!");
      return;
    }
    if (recipe.steps.length === 0) {
      toast.error("Please add at least one step!");
      return;
    }

    try {
      const jwtToken = localStorage.getItem("token");
      await axios.post(
        "http://localhost:3434/recipes/save-recipe",
        { ...recipe, userId: user.userId },
        { headers: { Authorization: `Bearer ${jwtToken}` } }
      );

      toast.success("Recipe created successfully!");
      navigate("/recipes");
    } catch (error) {
      console.error("Error creating recipe:", error);
      toast.error("Failed to create recipe!");
    }
  };

  return (
    <div className="container mt-5">
      <div className="card shadow-lg p-4">
        <h2 className="text-center text-primary mb-4">Create a New Recipe</h2>
        <form onSubmit={handleSubmit}>
          <div className="row">
            <div className="col-md-6 mb-3">
              <label className="form-label">Recipe Title</label>
              <input
                type="text"
                className="form-control"
                name="recipeTitle"
                value={recipe.recipeTitle}
                onChange={handleRecipeChange}
                required
              />
            </div>
            <div className="col-md-6 mb-3">
              <label className="form-label">Cooking Time (minutes)</label>
              <input
                type="number"
                className="form-control"
                name="cookingTime"
                value={recipe.cookingTime}
                onChange={handleRecipeChange}
                required
              />
            </div>
          </div>

          <div className="mb-3">
            <label className="form-label">Recipe Description</label>
            <textarea
              className="form-control"
              name="recipeDescription"
              rows="3"
              value={recipe.recipeDescription}
              onChange={handleRecipeChange}
              required
            ></textarea>
          </div>

          <div className="mb-3">
            <label className="form-label">Ingredients</label>
            <input
              type="text"
              className="form-control"
              name="ingredients"
              value={recipe.ingredients}
              onChange={handleRecipeChange}
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label">Image URL</label>
            <input
              type="text"
              className="form-control"
              name="image"
              value={recipe.image}
              onChange={handleRecipeChange}
              required
            />
          </div>

          <div className="bg-light p-3 rounded mb-3">
            <h5 className="text-secondary">Add Recipe Steps</h5>
            <div className="row">
              <div className="col-md-8 mb-2">
                <input
                  type="text"
                  className="form-control"
                  name="stepDescription"
                  placeholder="Step description"
                  value={step.stepDescription}
                  onChange={handleStepChange}
                />
              </div>
              <div className="col-md-3 mb-2">
                <input
                  type="number"
                  className="form-control"
                  name="timeRequired"
                  placeholder="Time (min)"
                  value={step.timeRequired}
                  onChange={handleStepChange}
                />
              </div>
              <div className="col-md-1 mb-2">
                <button
                  type="button"
                  className="btn btn-success w-100"
                  onClick={addStep}
                >
                  +
                </button>
              </div>
            </div>
          </div>

          {recipe.steps.length > 0 && (
            <ul className="list-group mb-3">
              {recipe.steps.map((s, index) => (
                <li
                  key={index}
                  className="list-group-item d-flex justify-content-between align-items-center"
                >
                  <span>
                    <strong>Step {index + 1}:</strong> {s.stepDescription} (
                    {s.timeRequired} min)
                  </span>
                  <button
                    type="button"
                    className="btn btn-danger btn-sm"
                    onClick={() => removeStep(index)}
                  >
                    Remove
                  </button>
                </li>
              ))}
            </ul>
          )}

          <button type="submit" className="btn btn-primary w-100">
            Create Recipe
          </button>
        </form>
      </div>
    </div>
  );
};

export default CreateRecipe;
