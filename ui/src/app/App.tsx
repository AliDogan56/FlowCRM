import React from 'react';
import './App.css';
import Navbar from "./components/navbar/NavBarComponent";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {appRoutes} from "../routes";
import HomePage from "./pages/home/HomePage";
import UserListPage from "./pages/user/list/UserListPage";
import UserDetailPage from "./pages/user/detail/UserDetailPage";
import 'bootstrap/dist/css/bootstrap.min.css';
import LoginPage from "./pages/auth/LoginPage";

function App() {
    const baseRoute = "/";

    return (
        <BrowserRouter>
            <Navbar/>

            <main className="main-content">
                <Routes>
                    <Route path={baseRoute + appRoutes.main} element={<HomePage/>}/>
                    <Route path={baseRoute + appRoutes.auth.login} element={<LoginPage/>}/>
                    <Route path={baseRoute + appRoutes.user.systemowner.list} element={<UserListPage/>}/>
                    <Route path={baseRoute + appRoutes.user.systemowner.detail} element={<UserDetailPage/>}/>
                    <Route path={baseRoute + appRoutes.user.systemadmin.list} element={<UserListPage/>}/>
                    <Route path={baseRoute + appRoutes.user.systemadmin.detail} element={<UserDetailPage/>}/>
                    <Route path={baseRoute + appRoutes.user.customer.list} element={<UserListPage/>}/>
                    <Route path={baseRoute + appRoutes.user.customer.detail} element={<UserDetailPage/>}/>
                </Routes>
            </main>
        </BrowserRouter>
    );
}

export default App;
