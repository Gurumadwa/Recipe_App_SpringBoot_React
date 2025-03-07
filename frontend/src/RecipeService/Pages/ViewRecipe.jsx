import React, { useContext, useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import toast from "react-hot-toast";
import { UserContext } from "../..";

const ViewRecipe = () => {
  const { recipeId } = useParams();
  const navigate = useNavigate();
  const [recipe, setRecipe] = useState(null);
  const [isBookmarked, setIsBookmarked] = useState(false);
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState("");
  // const [userId, setUserId] = useState(null);
  const jwtToken = localStorage.getItem("token");
  const { user } = useContext(UserContext);

  useEffect(() => {
    const fetchRecipeAndUser = async () => {
      try {
        // Fetch Recipe
        const recipeResponse = await axios.get(
          `http://localhost:3434/recipes/get-recipe/${recipeId}`,
          { headers: { Authorization: `Bearer ${jwtToken}` } }
        );
        setRecipe(recipeResponse.data);

        // Fetch User
        const userResponse = await axios.get(
          "http://localhost:3434/users/get-user",
          { headers: { Authorization: `Bearer ${jwtToken}` } }
        );
        const user = userResponse.data;
        // setUserId(user.userId);

        if (user.bookmarks.includes(Number(recipeId))) {
          setIsBookmarked(true);
        }
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchRecipeAndUser();
  }, [recipeId, jwtToken]);

  useEffect(() => {
    const fetchComments = async () => {
      try {
        const response = await axios.get(
          `http://localhost:3434/comments/get-by-recipe/${recipeId}`,
          { headers: { Authorization: `Bearer ${jwtToken}` } }
        );
        setComments(response.data);
      } catch (error) {
        console.error("Error fetching comments:", error);
      }
    };

    fetchComments();
  }, [recipeId, jwtToken]);

  const handleAddComment = async () => {
    if (!newComment.trim()) {
      toast.error("Comment cannot be empty!");
      return;
    }

    try {
      await axios.post(
        `http://localhost:3434/comments/save/${recipeId}`,
        { content: newComment },
        { headers: { Authorization: `Bearer ${jwtToken}` } }
      );

      toast.success("Comment added successfully!");
      setNewComment("");
      // Fetch updated comments
      const response = await axios.get(
        `http://localhost:3434/comments/get-by-recipe/${recipeId}`,
        { headers: { Authorization: `Bearer ${jwtToken}` } }
      );
      setComments(response.data);
    } catch (error) {
      console.error("Error adding comment:", error);
      toast.error("Failed to add comment.");
    }
  };

  const handleDeleteComment = async (commentId) => {
    try {
      await axios.delete(
        `http://localhost:3434/comments/delete/${commentId}`,
        { headers: { Authorization: `Bearer ${jwtToken}` } }
      );

      toast.success("Comment deleted successfully!");

      // Remove the deleted comment from state
      setComments((prevComments) =>
        prevComments.filter((comment) => comment.commentId !== commentId)
      );
    } catch (error) {
      console.error("Error deleting comment:", error);
      toast.error("Failed to delete comment.");
    }
  };

  const handleBookmark = async () => {
    try {
      await axios.put(
        `http://localhost:3434/users/add-bookmark/${recipeId}`,
        {},
        { headers: { Authorization: `Bearer ${jwtToken}` } }
      );
      setIsBookmarked(true);
      toast.success("Recipe bookmarked successfully!");
    } catch (error) {
      console.error("Error bookmarking recipe:", error);
      toast.error("Failed to bookmark recipe.");
    }
  };

  const handleRemoveBookmark = async () => {
    try {
      await axios.delete(
        `http://localhost:3434/users/delete-bookmark/${recipeId}`,
        { headers: { Authorization: `Bearer ${jwtToken}` } }
      );
      setIsBookmarked(false);
      toast.success("Recipe removed from bookmarks successfully!");
    } catch (error) {
      console.error("Error removing bookmark:", error);
      toast.error("Failed to remove bookmark.");
    }
  };

  if (!recipe) {
    return (
      <div className="text-center mt-5 text-danger">Recipe not found!</div>
    );
  }

  return (
    <div className="container mt-4" style={{ maxWidth: "500px" }}>
      <div className="card shadow-lg border-0 rounded">
        <div className="card-body p-3">
          <div className="d-flex justify-content-between">
            <button
              className="btn btn-dark btn-sm mb-3"
              onClick={() => navigate(-1)}
            >
              Back
            </button>

            {isBookmarked ? (
              <button
                className="btn btn-sm btn-danger mb-3"
                onClick={handleRemoveBookmark}
              >
                Remove Bookmark
              </button>
            ) : (
              <button
                className="btn btn-sm btn-outline-primary mb-3"
                onClick={handleBookmark}
              >
                Bookmark
              </button>
            )}
          </div>

          <img
            src={recipe.image}
            alt={recipe.recipeTitle}
            className="card-img-top rounded-top"
            style={{ height: "300px", width: "100%", objectFit: "cover" }}
          />

          <div className="mt-3">
            <h5 className="card-title text-primary fw-bold">
              {recipe.recipeTitle}
            </h5>
            <p className="card-text text-muted">{recipe.recipeDescription}</p>
            <span className="badge bg-warning text-dark px-2 py-1">
              {recipe.cookingTime} mins
            </span>
          </div>

          <div className="mt-3">
            <h6 className="text-secondary">Ingredients</h6>
            <hr />
            <ul>
              <li>{recipe.ingredients}</li>
            </ul>
          </div>

          <div className="mt-4">
            <h6 className="text-secondary">Steps to Prepare</h6>
            <hr />
            <ol className="mt-2">
              {recipe.steps && recipe.steps.length > 0 ? (
                recipe.steps.map((step, index) => (
                  <li key={index} className="mb-2">
                    <strong>Step {index + 1}:</strong> {step.stepDescription}{" "}
                    <br />
                    <span className="text-muted">
                      Time: {step.timeRequired} mins
                    </span>
                  </li>
                ))
              ) : (
                <p className="text-muted">No steps available.</p>
              )}
            </ol>
          </div>

          {/* CookingSteps Navigation Button */}
          <div className="mt-4">
            <button
              className="btn btn-sm btn-primary"
              onClick={() => navigate(`/cook/${recipeId}`)}
            >
              Start Cooking
            </button>
          </div>

          {/* Comments Section */}
          <div className="mt-4">
            <h6 className="text-secondary">Comments</h6>
            <hr />

            <div className="mb-3 d-flex">
              <input
                type="text"
                className="form-control me-2"
                placeholder="Add a comment..."
                value={newComment}
                onChange={(e) => setNewComment(e.target.value)}
              />
              <button
                className="btn btn-primary btn-sm"
                onClick={handleAddComment}
              >
                Add
              </button>
            </div>

            {comments.length > 0 ? (
              <ul className="list-group">
                {comments.map((comment) => (
                  <li
                    key={comment.commentId}
                    className="list-group-item d-flex justify-content-between align-items-center"
                  >
                    <div>
                      <strong>{comment.commentorsName}:</strong>{" "}
                      {comment.content}
                    </div>
                    {comment.userId === user.userId && (
                      <button
                        className="btn btn-sm btn-danger"
                        onClick={() => handleDeleteComment(comment.commentId)}
                      >
                        Delete
                      </button>
                    )}
                  </li>
                ))}
              </ul>
            ) : (
              <p className="text-muted">No comments yet.</p>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ViewRecipe;
