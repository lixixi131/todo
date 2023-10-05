import React from "react"
import { TextField,Paper,Button,Grid } from "@material-ui/core"
import { useState } from "react";

const AddTodo = ({add}) =>{

    
    const [item ,setItem] = useState({title : ""});

    const onInputChane = (e) =>{
        const thisItem = item;
        thisItem.title = e.target.value;
        setItem({title : e.target.value});
    }

    const enterKeyEventHandler = (e) =>{
        if(e.key === 'Enter'){
            onButtonClick();
        }
    }

    const onButtonClick = () =>{
        
        add(item);
        setItem({title :""});
    }

    return(
        <Paper style={{margine:16 , padding:16}}>
            <Grid container>
                <Grid xs = {11} md = {11} item style={{PaddingRight:16}}>
                    <TextField
                    placeholder="Add Todo here"
                    fullWidth
                    onChange={onInputChane}
                    value={item.title}
                    onKeyDown={enterKeyEventHandler}
                    />

                </Grid>
                <Grid xs = {1} md = {1} item>
                    <Button 
                    fullWidth 
                    color="secondary"
                    variant="outlined"
                    onClick = {onButtonClick}
                    >
                        +
                    </Button>
                </Grid>

            </Grid>

        </Paper>
    )
}

export default AddTodo;