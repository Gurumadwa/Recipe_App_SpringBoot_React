import React, { useEffect, useState } from "react";
import axios from "axios";
import RecipeCard from "../Components/RecipeCard";

const BookmarkedRecipes = () => {
  const [recipes, setRecipes] = useState([]);

  useEffect(() => {
    const fetchRecipes = async () => {
      const jwtToken = localStorage.getItem("token");
      try {
        const response = await axios.get("http://localhost:3434/users/bookmarked-recipes", {
          headers: { Authorization: `Bearer ${jwtToken}` }
        });
        setRecipes(response.data);
      } catch (error) {
        console.error("Error fetching recipes:", error);
      }
    };
  
    fetchRecipes();
  }, []);
  

  return (
    <div className="container mt-4">
      <div className="row">
        {recipes.length > 0 ? (
          recipes.map((recipe) => (
            <RecipeCard key={recipe.recipeId} recipe={recipe} />
          ))
        ) : (
          <p className="text-center">No recipes available.</p>
        )}
      </div>
    </div>
  );
};

export default BookmarkedRecipes;
