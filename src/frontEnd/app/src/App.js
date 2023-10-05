import Todo from "./Todo";
import { useState, useEffect } from "react";
import { Paper, List, Container, Button, Toolbar, Grid, Typography, AppBar } from "@material-ui/core";
import AddTodo from "./AddTodo";
import { call, signout } from './service/ApiService';
import Pagination from "react-js-pagination";
import './App.css';


const App = () => {

  const [items, setItems] = useState([''])

  //페이징기능을 위한 변수
  const [page, setPage] = useState(1) // 현재 페이지 번호
  const [currentPost, setCurrentPost] = useState([]) // 게시판 목록에 보여줄 게시글
  const postPerPage = 5 // 페이지 당 게시글 개수
  const indexOfLastPost = page * postPerPage
  const indexOfFirstPost = indexOfLastPost - postPerPage

  const boardLength = items.length

  const handlePageChange = (page) => {
    setPage(page)
  }


  useEffect(() => {
    componentDidMount();
  }, [])

  useEffect(() => {
    setCurrentPost(items.slice(indexOfFirstPost, indexOfLastPost))
    console.log(currentPost);
  }, [items, page])




  const todoList = items.map((item, idx) => {
    //console.log(item);
    <Todo item={item} setItems={setItems} />
  })

  const add = (item) => {

    // let copiedItems = [...items];

    // const maxId = copiedItems.reduce((prev , value) =>{
    //   return prev.id >= value.id ? prev : value
    // })


    // item.id = maxId.id + 1;
    // item.done = false;
    // copiedItems.push(item);
    // setItems(copiedItems);

    call("/todo", "POST", item).then((response) => {
      setItems(response.data);
      //componentDidMount();
    })



  }

  const deleteTodo = (item) => {

    // let copiedItems = [...items];
    // copiedItems = copiedItems.filter(e => e.id !== item.id);
    // setItems(copiedItems);

    call("/todo", "DELETE", item).then((response) => {
      console.log(response.data);
      //setItems(response.data);

      componentDidMount();
    })

  }

  const deleteCheckedTodo = () => {
    //console.log("deleteCheckedTodo pressed");
    call("/todo/checked", "DELETE" , currentPost).then((response) => {
      //setItems(response.data);
      //window.location.reload();
      componentDidMount();
    })
  }

  const checkAllTodo = () =>{
    console.log("check all todo");
    call("/todo/checked" , "PUT" , currentPost).then((response) =>{
      console.log(response);
      setItems(response.data);
    })
  }

  const updateTodo = (item) => {
    call("/todo", "PUT", item).then((response) => {
      setItems(response.data);
      //componentDidMount();

    })
  }

  const componentDidMount = () => {
    call("/todo", "GET", null).then((response) => {
      //console.log(response);
      //console.log(response);
      setItems(response.data);
    })





  }






  return (
    <div className="App">


      <AppBar position="static">
        <Toolbar>
          <Grid justifyContent="space-between" container>
            <Grid item>
              <Typography variant="h6">오늘의 할 일</Typography>
            </Grid>
            <Grid item>
              <Button color="inherit" onClick={signout}>logout</Button>
            </Grid>
          </Grid>
        </Toolbar>
      </AppBar>


      <Container maxWidth="md">
        <AddTodo add={add}></AddTodo>
        <Paper style={{ margin: 16 }}>
          <List>

            {
              currentPost.map((item) => {
                return (
                  <Todo item={item} items={items} setItems={setItems} deleteTodo={deleteTodo} key={item.id} updateTodo={updateTodo} />
                )
              })
            }

          </List>
        </Paper>
          <Button style={{
            backgroundColor: "#3f51b5",
            color: "white"
          }}
            onClick={checkAllTodo}
          >모두 체크</Button>

          <Button style={{
            backgroundColor: "#3f51b5",
            color: "white",
            margin: "10px"
          }}
            onClick={deleteCheckedTodo}
          >선택 삭제</Button>


      </Container>

      {/* 페이징을 위해 추가한 부분 */}
      <Pagination
        activePage={page}
        itemsCountPerPage={postPerPage}
        totalItemsCount={items.length}
        pageRangeDisplayed={5}
        prevPageText={"<"}
        nextPageText={">"}
        onChange={handlePageChange}
      />

      {/* # <Pagination count={10} variant="outlined" /> */}

    </div>
  )
}



export default App;