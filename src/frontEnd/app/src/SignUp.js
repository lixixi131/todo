import React from "react";

import { Paper, List, Container, Button, Toolbar, Grid, Typography, AppBar, TextField } from "@material-ui/core";
import { Link } from "react-router-dom";
import { signup } from "./service/ApiService";

const SignUp = () => {

    const handelSubmit = (e) => {
        e.preventDefault();

        const data = new FormData(e.target);
        const username = data.get("username");
        const email = data.get("email");
        const password = data.get("password");
        signup({ "email": email, "username": username, "password": password }).then(
            (response) => {
                window.location.href = "/login";
                console.log("sigin up ");
            }
        );


        


    }


    return (
        <Container component="main" maxWidth="xs" style={{ marginTop: "8%" }}>

            <form noValidate onSubmit={handelSubmit}>
                <Grid Container spacing={2}>
                    <Grid item xs={12}>
                        <Typography component="h1" variant="h5">
                            계정생성
                        </Typography>
                    </Grid>


                    <Grid item xs={12}>
                        <TextField
                            variant="outlined"
                            required
                            fullWidth
                            id="username"
                            label="사용자이름"
                            name="username"
                            autuComplete="username"
                            autoFocus
                        >

                        </TextField>
                    </Grid>

                    <Grid item xs={12}>
                        <TextField
                            variant="outlined"
                            required
                            fullWidth
                            id="email"
                            label="이메일 주소"
                            name="email"
                            autuComplete="email"
                            autoFocus
                        >

                        </TextField>
                    </Grid>



                    <Grid item xs={12}>
                        <TextField
                            variant="outlined"
                            required
                            fullWidth
                            id="password"
                            label="비밀번호"
                            name="password"
                            autuComplete="password"
                            autoFocus
                        >

                        </TextField>
                    </Grid>

                    <Grid item xs={12}>
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            color="primary"
                        >
                            계정생성
                        </Button>
                    </Grid>
                </Grid>
                <Grid container justifyContent="flex-end">
                    <Grid item>
                        <Link to="/login" variant="body2">
                            <Grid item>이미 계정이 있습니까? 로그인 하세요.</Grid>
                        </Link>
                    </Grid>
                </Grid>


            </form>
        </Container>
        )




}

export default SignUp;