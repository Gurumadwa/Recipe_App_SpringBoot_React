import React, { useState, useEffect, useContext } from "react";
import { useParams, useNavigate } from "react-router-dom";
import toast from "react-hot-toast";
import axios from "axios";
import { UserContext } from "../..";

const UpdateRecipe = () => {
  const { user } = useContext(UserContext);
  const { recipeId } = useParams();
  const navigate = useNavigate();

  // Recipe State
  const [recipe, setRecipe] = useState({
    recipeTitle: "",
    recipeDescription: "",
    ingredients: "", // Added field for ingredients
    cookingTime: "",
    image: "",
    steps: [],
  });

  // Step State
  const [step, setStep] = useState({ stepDescription: "", timeRequired: "" });
  const [editingIndex, setEditingIndex] = useState(-1);

  useEffect(() => {
    const fetchRecipe = async () => {
      const jwtToken = localStorage.getItem("token");
      try {
        const response = await axios.get(`http://localhost:3434/recipes/get-recipe/${recipeId}`, {
          headers: { Authorization: `Bearer ${jwtToken}` }
        });
        console.log(response)
        setRecipe(response.data);
      } catch (error) {
        console.error("Error fetching recipe:", error);
      }
    };

    fetchRecipe();
  }, [recipeId]);

  // Handle Recipe Input Change
  const handleRecipeChange = (e) => {
    setRecipe({ ...recipe, [e.target.name]: e.target.value });
  };

  // Handle Step Input Change
  const handleStepChange = (e) => {
    setStep({ ...step, [e.target.name]: e.target.value });
  };

  // Add or Update Step to Recipe
  const addOrUpdateStep = () => {
    if (!step.stepDescription || !step.timeRequired) {
      toast.error("Please fill both step fields!");
      return;
    }

    if (editingIndex === -1) {
      // Add new step
      setRecipe({ ...recipe, steps: [...recipe.steps, step] });
    } else {
      // Update existing step
      const updatedSteps = recipe.steps.map((s, index) => index === editingIndex ? step : s);
      setRecipe({ ...recipe, steps: updatedSteps });
      setEditingIndex(-1);
    }

    setStep({ stepDescription: "", timeRequired: "" });
  };

  // Remove Step
  const removeStep = (index) => {
    const updatedSteps = recipe.steps.filter((_, i) => i !== index);
    setRecipe({ ...recipe, steps: updatedSteps });
  };

  // Edit Step
  const editStep = (index) => {
    setStep(recipe.steps[index]);
    setEditingIndex(index);
  };

  // Handle Form Submit
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (
      !recipe.recipeTitle ||
      !recipe.recipeDescription ||
      !recipe.ingredients || // Updated validation to include ingredients
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
      await axios.put(
        `http://localhost:3434/recipes/update-recipe/${recipeId}`,
        { ...recipe, userId: user.userId },
        { headers: { Authorization: `Bearer ${jwtToken}` } }
      );

      toast.success("Recipe updated successfully!");
      navigate("/created-recipes");
    } catch (error) {
      console.error("Error updating recipe:", error);
      toast.error("Failed to update recipe!");
    }
  };

  return (
    <div className="container mt-5">
      <div className="card shadow-lg p-4">
        <h2 className="text-center text-primary mb-4">Update Recipe</h2>
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
            <h5 className="text-secondary">Add or Edit Recipe Steps</h5>
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
                  onClick={addOrUpdateStep}
                >
                  {editingIndex === -1 ? "+" : "✓"}
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
                  <div>
                    <button
                      type="button"
                      className="btn btn-warning btn-sm me-2"
                      onClick={() => editStep(index)}
                    >
                      Edit
                    </button>
                    <button
                      type="button"
                      className="btn btn-danger btn-sm"
                      onClick={() => removeStep(index)}
                    >
                      Remove
                    </button>
                  </div>
                </li>
              ))}
            </ul>
          )}

          <button type="submit" className="btn btn-primary w-100">
            Update Recipe
          </button>
        </form>
      </div>
    </div>
  );
};

export default UpdateRecipe;
