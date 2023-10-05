import React from "react";
import { Button, TextField, Grid, Link, Container, Typography } from "@material-ui/core";
import { signin } from "./service/ApiService";
const Login = () => {


    const handelSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.target);
        const email = data.get("email");
        const password = data.get("password");

        signin({ email: email, password: password });

    }



    return (
        //    <p>로그인 페이지</p>

        <Container component="main" maxWidth="xs" style={{ marginTop: "8%" }}>
            <Grid Container spacing={2}>
                <Typography component="h1" variant="h5">
                    로그인
                </Typography>
            </Grid>
            <form noValidate onSubmit={handelSubmit}>
                {" "}
                <Grid container Spacing={2}>
                    <TextField
                        variant="outlined"
                        required
                        fullWidth
                        id="email"
                        label="이메일주소"
                        name="email"
                        autuComplete="email"
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
                        로그인
                    </Button>
                </Grid>
                <Link href="/signup" variant="body2">
                    <Grid item>계정이 없습니까? 여기서 가입하세요</Grid>
                </Link>

            </form>
        </Container>

    )
}


export default Login;