import React, { useEffect, useState } from "react";
import axios from "axios";
import RecipeCard from "../Components/RecipeCard";
import toast from "react-hot-toast";
import { useNavigate } from "react-router-dom";

const AdminRecipes = () => {
  const [recipes, setRecipes] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchRecipes = async () => {
      const jwtToken = localStorage.getItem("token");
      try {
        const response = await axios.get("http://localhost:3434/recipes/getRecipeByUserId", {
          headers: { Authorization: `Bearer ${jwtToken}` }
        });
        setRecipes(response.data);
      } catch (error) {
        console.error("Error fetching recipes:", error);
      }
    };
  
    fetchRecipes();
  }, []);

  // Delete Recipe
  const handleDeleteRecipe = async (recipeId) => {
    const jwtToken = localStorage.getItem("token");
    try {
      await axios.delete(`http://localhost:3434/recipes/${recipeId}`, {
        headers: { Authorization: `Bearer ${jwtToken}` }
      });
      toast.success("Recipe deleted successfully!");
      setRecipes(recipes.filter((recipe) => recipe.recipeId !== recipeId));
    } catch (error) {
      console.error("Error deleting recipe:", error);
      toast.error("Failed to delete recipe.");
    }
  };

  const handleUpdateRecipe = (recipeId) => {
    navigate(`/update-recipe/${recipeId}`)
  }

  return (
    <div className="container mt-4">
      <div className="row">
        {recipes.length > 0 ? (
          recipes.map((recipe) => (
            <RecipeCard key={recipe.recipeId} recipe={recipe} handleDeleteRecipe={handleDeleteRecipe} handleUpdateRecipe={handleUpdateRecipe}/>
          ))
        ) : (
          <p className="text-center">No recipes available.</p>
        )}
      </div>
    </div>
  );
};

export default AdminRecipes;
