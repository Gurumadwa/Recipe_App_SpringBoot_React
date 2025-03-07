import { Toaster } from 'react-hot-toast';
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Navbar from './Layouts/Navbar';
import Register from './UserService/Register'
import Login from './UserService/Login'
import LandingPage from './Layouts/LandingPage';
import RecipesList from './RecipeService/Pages/RecipesList';
import CreateRecipe from './RecipeService/Pages/CreateRecipe';
import AdminRecipes from './RecipeService/Pages/AdminRecipes';
import BookmarkedRecipes from './RecipeService/Pages/BookmarkedRecipes';
import ViewRecipe from './RecipeService/Pages/ViewRecipe';
import UpdateRecipe from './RecipeService/Pages/UpdateRecipe';
import CookingSteps from './RecipeService/Pages/CookingSteps';

function App() {
  return (
    <div>
      <BrowserRouter>
        <Navbar />
        <Routes>
          <Route path='/' element={<LandingPage />}/>
          <Route path='/register' element={<Register />}/>
          <Route path='/login' element={<Login />}/>
          <Route path='/recipes' element={<RecipesList />}/>
          <Route path='/create-recipe' element={<CreateRecipe />}/>
          <Route path='/created-recipes' element={<AdminRecipes />}/>
          <Route path='/update-recipe/:recipeId' element={<UpdateRecipe />}/>
          <Route path='/bookmarks' element={<BookmarkedRecipes />}/>
          <Route path='/recipe/:recipeId' element={<ViewRecipe />}/>
          <Route path='/cook/:recipeId' element={<CookingSteps />}/>
        </Routes>
      </BrowserRouter>

      <Toaster />
    </div>
  );
}

export default App;
