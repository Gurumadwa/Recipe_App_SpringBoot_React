import React, { useContext } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { UserContext } from "../..";

const RecipeCard = ({ recipe, handleUpdateRecipe, handleDeleteRecipe }) => {
  const { user } = useContext(UserContext);
  const location = useLocation();
  const navigate = useNavigate();

  const handleCardClick = () => {
    navigate(`/recipe/${recipe.recipeId}`);
  };

  return (
    <div className="col-md-4 pt-4">
      <div className="card shadow-lg" style={{ cursor: "pointer" }} onClick={handleCardClick}>
        <img
          src={recipe.image}
          className="card-img-top"
          alt={recipe.recipeTitle}
          style={{ height: "200px", objectFit: "cover" }}
        />
        <div className="card-body">
          <h5 className="card-title">{recipe.recipeTitle}</h5>
          <p className="card-text text-truncate">{recipe.recipeDescription}</p>
          <p className="text-muted">Cooking Time: {recipe.cookingTime} mins</p>
          {location.pathname === "/created-recipes" && user.role === "ROLE_ADMIN" && (
            <div className="d-flex justify-content-between">
              <button
                className="btn btn-warning"
                onClick={(e) => {
                  e.stopPropagation();
                  handleUpdateRecipe(recipe.recipeId);
                }}
              >
                Update
              </button>
              <button
                className="btn btn-danger"
                onClick={(e) => {
                  e.stopPropagation();
                  handleDeleteRecipe(recipe.recipeId);
                }}
              >
                Delete
              </button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default RecipeCard;
