import React, { useEffect } from "react";
//import "./index.css";
import App from "./App";
import Login from "./Login";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Box from "@material-ui/core/Box";
import { Typography } from "@material-ui/core";
import SignUp from "./SignUp";
import { ThemeProvider } from "styled-components";

const Copyright = () => {
    return (
        <Typography>
            {"Copyright"}
            fsoftwareenineer ,{new Date().getUTCFullYear()}
            {"."}
        </Typography>
    );
}

const AppRouter = () => {
    //window.location.href = "/login"



    return (

        <BrowserRouter>
                <div>

                    <Routes>
                        <Route path="/login" element={<Login />}></Route>
                        <Route path="/" element={<App />}></Route>
                        <Route path="/signup" element={<SignUp />}></Route>
                    </Routes>
                </div>
                <div>
                    <Box mt={5}>
                        <Copyright />
                    </Box>
                </div>


        </BrowserRouter>
    )
}


export default AppRouter;